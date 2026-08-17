package com.molokosoft.decisionengine.newdecisionscreen.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model.*

import com.molokosoft.decisionengine.repositories.FactorAnalysisRepository
import com.molokosoft.decisionengine.repositories.UserDataRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.android.billingclient.api.BillingClient
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.listOf

import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.molokosoft.decisionengine.commonclasses.EMail
import com.molokosoft.decisionengine.commonclasses.ProductTypes
import com.molokosoft.decisionengine.billing.BillingManager
import com.molokosoft.decisionengine.billing.model.BillingProduct
import com.molokosoft.decisionengine.billing.model.SubscriptionProduct
import com.molokosoft.decisionengine.network.backend.DecisionEngineClient
import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionAnalysisResult
import com.molokosoft.decisionengine.network.backend.model.dto.decision.SafetyClassification
import com.molokosoft.decisionengine.network.backend.model.dto.security.dto.PromptReconnaissanceResult
import com.molokosoft.decisionengine.repositories.DecisionRepository
import com.molokosoft.decisionengine.repositories.model.OptionAnalysis
import com.molokosoft.decisionengine.repositories.model.CriterionAnalysis
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlin.String

class NewDecisionViewModel(
    private val factorAnalysisRepository: FactorAnalysisRepository,
    private val userDataRepository: UserDataRepository,
    private val decisionRepository: DecisionRepository,
    private val billingManager: BillingManager,
    private val decisionEngineClient: DecisionEngineClient
) : ViewModel() {

    private val _draft =
        MutableStateFlow(DecisionDraft())

    val draft =
        _draft.asStateFlow()

    private val _subscriptionProducts =
        MutableStateFlow(emptyList<SubscriptionProduct>())

    val subscriptionProducts =
        _subscriptionProducts.asStateFlow()

    fun startOnboarding() {
        isOnboarding = true
    }

    var isOnboarding: Boolean = false
        private set

    val showNotAllowedScreen: StateFlow<Boolean> =
        draft
            .map { draft ->
                draft.safetyClassification != null && draft.safetyClassification.classification == "NOT_ALLOWED"
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                false
            )

    val showIsPromptScreen: StateFlow<Boolean> =
        draft
            .map { draft ->
                draft.promptReconnaissanceResult?.isPrompt ?: false
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                false
            )

    private val _showNoMoreUsagesScreen =
        MutableStateFlow(false)

    val showNoMoreUsagesScreen =
        _showNoMoreUsagesScreen.asStateFlow()

    private val _showWaitingForAnalysisScreen =
        MutableStateFlow(false)

    val showWaitingForAnalysisScreen =
        _showWaitingForAnalysisScreen.asStateFlow()

    private var _analysisDone =
        MutableStateFlow(false)

    val analysisDone =
        _analysisDone.asStateFlow()

    val criteriaNames: StateFlow<List<String>> =
        draft
            .map { draft ->
                draft.criteria.map {
                    criterion -> criterion.name
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun resetDraft() {
        viewModelScope.launch {
            _draft.value = DecisionDraft()
            _analysisDone.value = false
            _showWaitingForAnalysisScreen.value = false
            _showNoMoreUsagesScreen.value = false
        }
    }

    suspend fun isInputHarmfulOrAPrompt(content: String): Boolean =
        coroutineScope {
            val safetyDeferred = async {
                getSafetyClassification(content)
            }

            val reconnaissanceDeferred = async {
                getPromptReconnaissance(content)
            }

            val safetyResult =
                safetyDeferred.await()

            val reconnaissanceResult =
                reconnaissanceDeferred.await()

            if (safetyResult == null || reconnaissanceResult == null)
                return@coroutineScope true

            if (safetyResult.classification == "NOT_ALLOWED")
                return@coroutineScope true

            if (reconnaissanceResult.isPrompt)
                return@coroutineScope true

            return@coroutineScope false
        }

    suspend fun setDraftToOldDecision(oldDecisionID: Long) {
        resetDraft()

        val oldDecision =
            decisionRepository.decisionHistory
                .map { decisions ->
                    decisions.firstOrNull() {
                        it.decision.id == oldDecisionID
                    }
                }
                .firstOrNull()

        if (oldDecision == null)
            return

        _draft.update {
            it.copy(
                title = oldDecision.decision.title,
            )
        }

        oldDecision.options.forEach { option ->
            val criteria = listOf(
                Criterion(
                    name = option.criteria.first().name,
                    importance = option.criteria.first().importance,
                    score = option.criteria.first().score
                )
            )

            setOption(
                Option(
                    name = option.option.name,
                    reversibility = option.option.reversibility,
                    criteria = criteria
                )
            )
        }

        _draft.update {
            it.copy(
                decisionAnalysisResult =
                    DecisionAnalysisResult(
                        summary = oldDecision.decision.summary,
                        recommendedOption = oldDecision.decision.recommendedOption,
                        whyItStandsOut = oldDecision.decision.whyItStandsOut,
                        reversibility = oldDecision.decision.reversibility,
                        blindSpots = oldDecision.decision.blindSpots,
                        roadmapToSuccess = oldDecision.decision.roadmapToSuccess,
                        conclusion = oldDecision.decision.conclusion,
                        category = oldDecision.decision.category
                    )
            )
        }

        _draft.update {
            it.copy(
                optionAnalyses = oldDecision.options.map { option ->
                    OptionAnalysis(
                        name = option.option.name,
                        reversibility = option.option.reversibility,
                        weightedScore = option.option.confidence / 10.0,
                        analyses = option.criteria.map { criterion ->
                            CriterionAnalysis(
                                name = criterion.name,
                                importance = criterion.importance,
                                score = criterion.score,
                                contribution = criterion.contribution,
                                percentage = criterion.percentage
                            )
                        }
                    )
                }
            )
        }
    }

    fun setTitle(title: String) {
        if (_draft.value.title.isBlank()) {
            viewModelScope.launch {
                val isHarmful =
                    isInputHarmfulOrAPrompt(title)

                if (!isHarmful) {
                    getCriteriaSuggestions(title)
                }
            }
        }

        _draft.update {
            it.copy(title = title)
        }
    }

    fun setDecisionType(yesOrNoDecision: Boolean) {
        _draft.update {
            it.copy(
                yesOrNoDecision =
                    yesOrNoDecision
            )
        }

        if (yesOrNoDecision) {
            setOption(Option(name = "Yes", reversibility = 5))
            setOption(Option(name = "No", reversibility = 5))
        }

        deleteOption("Default")
    }
    fun setOption(option: Option) {
        viewModelScope.launch {
            isInputHarmfulOrAPrompt(option.name)
        }

        _draft.update {
            it.copy(
                options =
                    it.options + option
            )
        }
    }

    fun deleteOption(name: String) {
        _draft.update { it ->
            it.copy(
                options = it.options.filter { option ->
                    option.name != name
                }
            )
        }
    }

    fun setCriteria(criterion: Criterion) {
        viewModelScope.launch {
            isInputHarmfulOrAPrompt(criterion.name)
        }

        _draft.update {
            it.copy(
                criteria =
                    it.criteria + criterion
            )
        }
    }

    fun deleteCriterion(name: String) {
        _draft.update {
            it.copy(
                criteria = it.criteria.filter { criterion ->
                    criterion.name != name
                }
            )
        }
    }

    fun setRatedCriteriaToOption(optionName: String, criteria: List<Criterion>){
        _draft.update { draft ->
            val updatedOptions = draft.options.map { option ->
                if (option.name == optionName){
                    option.copy(criteria = criteria)
                } else {
                    option
                }
            }

            draft.copy(
                options =
                    updatedOptions
            )
        }
    }

    fun finishOnboarding() {
        if (isOnboarding)
            startAnalysis()

        isOnboarding = false
    }

    fun onComparisonCompleted() {
        if (isOnboarding)
            return

        viewModelScope.launch {
            _showWaitingForAnalysisScreen.update {
                true
            }

            val remainingUsages =
                decisionEngineClient.getRemainingUsages()

            if (remainingUsages == null) {
                //zeig irgendeinen fehlerscreen
                return@launch
            }

            _showNoMoreUsagesScreen.value =
                remainingUsages.accessStatus.remainingUsages == 0

            if (remainingUsages.accessStatus.remainingUsages > 0)
                startAnalysis()
        }
    }

    fun getNextOption(): String? {
        return draft.value.options
            .firstOrNull {
                it.criteria.isEmpty()
            }?.name
    }

    fun startAnalysis() {
        _draft.update {
            it.copy(
                optionAnalyses =
                    factorAnalysisRepository.analyzeOptions(it.options)
            )
        }

        viewModelScope.launch {
            _draft.update {
                it.copy(
                    decisionAnalysisResult =
                        factorAnalysisRepository.getAiAnalysis(it.optionAnalyses)
                )
            }

            _showWaitingForAnalysisScreen.value = false
            _analysisDone.value = true

            saveDecision()
        }
    }

    fun saveEMail(eMail: EMail) {
        viewModelScope.launch {
          //"Save the E-Mail in case the sendEmail-Method fails.")

            val success =
                userDataRepository.sendEmail(eMail.toString())

            //"If the sendEMail-Method fails, try it again, until it doesn't fail anymore")
        }
    }
    suspend fun getSafetyClassification(title: String): SafetyClassification? {

        val result =
            factorAnalysisRepository.safetyClassification(title)

        _draft.update {
            it.copy(
                safetyClassification = result
            )
        }

        return result
    }

    suspend fun getPromptReconnaissance(input: String): PromptReconnaissanceResult? {
        val result =
            factorAnalysisRepository.promptReconnaissance(input)

        _draft.update {
            it.copy(
                promptReconnaissanceResult = result
            )
        }

        return result
    }

    suspend fun getCriteriaSuggestions(title: String) {
        val result =
            factorAnalysisRepository.getCriteriaSuggestions(title)

        _draft.update {
            it.copy(
                criteriaSuggestions = result
            )
        }
    }

    private suspend fun saveDecision() {
        val fullDraft =
            _draft.value

        decisionRepository.insertDecision(fullDraft)
    }

    private fun verifyPurchaseAndContinue(
        purchase: Purchase,
        productId: String,
        apiKey: String?,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            val verified = userDataRepository.verifyPurchase(
                purchase.purchaseToken,
                productId = productId,
                apiKey = apiKey
            )

            if (verified) {
                onSuccess()
            } else {
                onFailure()
            }
        }
    }

    fun checkAccess(
        onAccessGranted: () -> Unit,
        onAccessDenied: () -> Unit,
        onReAccess: () -> Unit,
        onError: () -> Unit
    ) {
        billingManager.clearListener()

        billingManager.setListener(object : BillingManager.Listener {

            override fun onBillingReady() {
                Log.d("Billing", "Checking active subscriptions.")
                billingManager.queryActiveSubscriptions()
            }

            override fun onActivePurchasesLoaded(purchases: List<Purchase>) {
                Log.d("Billing", "Found ${purchases.size} active subscriptions")

                var hasSubscription =
                    false

                //If the user has an API Key, they already used
                //DecisionEngine once and need to be shown a different screen

                val hasApiKey =
                    userDataRepository.apiKey().isNotBlank()

                purchases.forEach {
                    Log.d("Billing", "Products = ${it.products}")
                    Log.d("Billing", "State = ${it.purchaseState}")

                    hasSubscription =
                        purchases.any {
                            it.purchaseState == Purchase.PurchaseState.PURCHASED
                        }

                    Log.d("Billing", "Has subscription = $hasSubscription")
                }

                if (!hasApiKey) {
                    onAccessDenied()
                    return
                }

                if (hasSubscription) {
                    isOnboarding = false
                    onAccessGranted()
                } else {
                    checkCredits(
                        onAccessGranted = {
                            isOnboarding = false
                            onAccessGranted()
                        },
                        onAccessDenied = {
                            isOnboarding = userDataRepository.apiKey().isBlank()
                            onReAccess()
                        },
                        onError = {
                            onError()
                        }
                    )
                }
            }

            override fun onProductsLoaded() {
                val products =
                    ProductTypes.entries
                        .filter {
                            it != ProductTypes.Undefined && it.productType == BillingClient.ProductType.SUBS
                        }
                        .mapNotNull { type ->
                            billingManager.getFormattedPrice(type.value)
                                ?.let { price ->
                                    SubscriptionProduct(
                                        productId = type.value,
                                        formattedPrice = price,
                                        hasFreeTrial =
                                            billingManager.hasFreeTrial(type.value)
                                    )
                                }
                        }

                Log.d("Billing", "Subscription products: $products")

                _subscriptionProducts.value =
                    products
            }

            override fun onPurchaseAcknowledged(purchase: Purchase) {
            }

            override fun onPurchaseFailure(billingResult: BillingResult) {
                Log.e("Billing", "Purchase failure: " + "$billingResult.responseCode " + billingResult.debugMessage)
            }

            override fun onError(billingResult: BillingResult) {
                Log.e("Billing", "Billing error: " + "${billingResult.responseCode} " + billingResult.debugMessage)
                onAccessDenied()
            }
        })

        billingManager.connect()
    }

    private fun checkCredits(
        onAccessGranted: () -> Unit,
        onAccessDenied: () -> Unit,
        onError: () -> Unit
    ) {
        val apiKey =
            userDataRepository.apiKey()

        apiKey.ifBlank {
            Log.d("Billing", "No API Key found")
            onAccessDenied()
            return
        }

        viewModelScope.launch {
            try {
                val result =
                    decisionEngineClient.getRemainingUsages()

                if (result == null) {
                    Log.e("Billing", "Could not retrieve access status.")
                    onError()
                    return@launch
                }

                Log.d("Billing", "Remaining usages: ${result.accessStatus.remainingUsages}")

                if (result.accessStatus.remainingUsages > 0) {
                    onAccessGranted()
                } else {
                    onAccessDenied()
                }
            } catch (e: Exception) {
                Log.e("Billing", "Credit check failed.", e)
                onError()
            }
        }
    }

    fun startBillingProcess(
        productType: ProductTypes,
        activity: Activity,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
        apiKey: String?
    ) {
        Log.d(
            "Billing",
            "START BILLING PROCESS: ${productType.value}"
        )

        billingManager.clearListener()

        billingManager.setListener(object: BillingManager.Listener {
            override fun onBillingReady() {
                Log.d("Billing", "Billing ready")

                if (productType.productType == BillingClient.ProductType.SUBS) {
                    billingManager.queryActiveSubscriptions()
                } else {
                    Log.d(
                        "Billing",
                        "Consumable product: skipping subscription check."
                    )

                    billingManager.loadProducts(
                        ProductTypes.entries
                            .filter {
                                it != ProductTypes.Undefined && it.productType == BillingClient.ProductType.INAPP
                            }.map {
                                BillingProduct(
                                    it.value,
                                    it.productType
                                )
                            }
                    )
                }
            }

            override fun onProductsLoaded() {
                Log.d("Billing", "Products loaded")
                Log.d("Billing", "Buying: ${productType.value}")

                billingManager.buyProduct(activity, productType.value)
            }

            override fun onPurchaseAcknowledged(purchase: Purchase) {
                viewModelScope.launch {
                    userDataRepository.verifyPurchase(
                        purchase.purchaseToken,
                        productId = productType.value,
                        apiKey = apiKey
                    )
                }
            }

            override fun onPurchaseFailure(billingResult: BillingResult) {
                Log.e(
                    "Billing",
                    "Purchase failure: " +
                            "responseCode=${billingResult.responseCode}, " +
                            "debugMessage=${billingResult.debugMessage}"
                )

                onFailure()
            }

            override fun onError(billingResult: BillingResult) {
                Log.e(
                    "Billing",
                    "Billing error: " +
                            "responseCode=${billingResult.responseCode}, " +
                            "debugMessage=${billingResult.debugMessage}"
                )
                onFailure()
            }

            override fun onActivePurchasesLoaded(purchases: List<Purchase>) {
                Log.d(
                    "Billing",
                    "Active purchases: ${purchases.map { it.products }}"
                )

                val purchase = purchases.firstOrNull {
                    it.purchaseState == Purchase.PurchaseState.PURCHASED &&
                            productType.value in it.products
                }

                if (purchase != null) {
                    Log.d("Billing", "Existing purchase found")

                    verifyPurchaseAndContinue(
                        purchase,
                        productType.value,
                        apiKey = apiKey,
                        onSuccess,
                        onFailure
                    )
                } else {
                    Log.d("Billing", "No existing purchase, loading products")

                    if (productType.productType == BillingClient.ProductType.SUBS) {
                        billingManager.loadProducts(
                            ProductTypes.entries
                                .filter {
                                    it != ProductTypes.Undefined && it.productType == BillingClient.ProductType.SUBS
                                }
                                .map {
                                    BillingProduct(
                                        it.value,
                                        it.productType
                                    )
                                }
                        )
                    } else {
                        billingManager.loadProducts(
                            ProductTypes.entries
                                .filter {
                                    it != ProductTypes.Undefined && it.productType == BillingClient.ProductType.INAPP
                                }
                                .map {
                                    BillingProduct(
                                        it.value,
                                        it.productType
                                    )
                                }
                        )
                    }
                }
            }
        })

        billingManager.connect()
    }
}