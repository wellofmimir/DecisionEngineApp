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
import kotlinx.coroutines.flow.map

import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.molokosoft.decisionengine.commonclasses.EMail
import com.molokosoft.decisionengine.commonclasses.SubscriptionTypes
import com.molokosoft.decisionengine.billing.BillingManager
import com.molokosoft.decisionengine.network.backend.model.dto.decision.DecisionAnalysisResult
import com.molokosoft.decisionengine.repositories.DecisionRepository
import com.molokosoft.decisionengine.repositories.model.OptionAnalysis
import com.molokosoft.decisionengine.repositories.model.CriterionAnalysis
import kotlinx.coroutines.flow.firstOrNull
import kotlin.String

class NewDecisionViewModel(
    private val factorAnalysisRepository: FactorAnalysisRepository,
    private val userDataRepository: UserDataRepository,
    private val decisionRepository: DecisionRepository,
    private val billingManager: BillingManager
) : ViewModel() {

    private val _draft =
        MutableStateFlow(DecisionDraft())

    val draft =
        _draft.asStateFlow()

    private val _showBottomBar =
        MutableStateFlow(true)

    val showBottomBar =
        _showBottomBar.asStateFlow()

    fun showBottomBar() {
        _showBottomBar.value = true
    }

    fun hideBottomBar() {
        _showBottomBar.value = false
    }

    fun resetDraft() {
        viewModelScope.launch {
            _draft.value = DecisionDraft()
        }
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
        _draft.value.title.ifBlank {
            getCriteriaSuggestions(title)
        }

        if (title != _draft.value.title && _draft.value.title.isNotBlank())
            getCriteriaSuggestions(title)

        _draft.update {
            it.copy(
                title = title
            )
        }
    }

    fun setDecisionType(yesOrNoDecision: Boolean) {
        _draft.update {
            it.copy(
                yesOrNoDecision = yesOrNoDecision
            )
        }

        if (yesOrNoDecision) {
            setOption(Option(name = "Yes", reversibility = 5))
            setOption(Option(name = "No", reversibility = 5))
        }

        deleteOption("Default")
    }
    fun setOption(option: Option) {
        _draft.update {
            it.copy(
                options = it.options + option
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
        _draft.update {
            it.copy(
                criteria = it.criteria + criterion
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
                options = updatedOptions
            )
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
                optionAnalyses = factorAnalysisRepository.analyzeOptions(it.options)
            )
        }

        hideBottomBar()

        viewModelScope.launch {
            _draft.update {
                it.copy(
                    decisionAnalysisResult = factorAnalysisRepository.getAiAnalysis(it.optionAnalyses)
                )
            }

            saveDecision()
        }
    }

    fun saveEMail(eMail: EMail) {
        viewModelScope.launch {
          //"Save the E-Mail in case the sendEmail-Method fails.")
            val success = userDataRepository.sendEmail(eMail.toString())
            //"If the sendEMail-Method fails, try it again, until it doesn't fail anymore")
        }
    }

    fun getCriteriaSuggestions(title: String) {
        viewModelScope.launch {
            val result = factorAnalysisRepository.getCriteriaSuggestions(title)

            _draft.update {
                it.copy(
                    criteriaSuggestions = result
                )
            }
        }
    }

    private fun saveDecision() {
        val fullDraft = _draft.value

        viewModelScope.launch {
            decisionRepository.insertDecision(fullDraft)
        }
    }

    fun checkSubscription(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        billingManager.clearListener()

        billingManager.setListener(object : BillingManager.Listener {
            override fun onBillingReady() {
                billingManager.queryActiveSubscriptions()
            }

            override fun onActivePurchasesLoaded(purchases: List<Purchase>) {
                Log.d("Billing", "Found ${purchases.size} purchases")

                purchases.forEach {
                    Log.d("Billing", "Products = ${it.products}")
                    Log.d("Billing", "State = ${it.purchaseState}")
                }

                val hasSubscription = purchases.any {
                    it.purchaseState == Purchase.PurchaseState.PURCHASED && "test_weekly_subscription" in it.products
                }

                Log.d("Billing", "Has subscription = $hasSubscription")

                if (hasSubscription)
                    onSuccess()
                else
                    onFailure()
            }

            override fun onProductsLoaded() {
            }

            override fun onPurchaseAcknowledged(purchase: Purchase) {
            }

            override fun onPurchaseFailure(billingResult: BillingResult) {
                onFailure()
            }

            override fun onError(billingResult: BillingResult) {
                onFailure()
            }
        })

        billingManager.connect()
    }

    fun startBillingProcess(
        subscriptionType: SubscriptionTypes,
        activity: Activity,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        billingManager.clearListener()

        billingManager.setListener(object: BillingManager.Listener {
            override fun onBillingReady() {
                billingManager.queryActiveSubscriptions()
            }

            override fun onProductsLoaded() {
                billingManager.buySubscription(activity, "test_weekly_subscription")

                //TODO: raus damit
                onSuccess()
            }

            override fun onPurchaseAcknowledged(purchase: Purchase) {
                onSuccess()
            }

            override fun onPurchaseFailure(billingResult: BillingResult) {
                onFailure()
            }

            override fun onError(billingResult: BillingResult) {
                onFailure()
            }

            override fun onActivePurchasesLoaded(purchases: List<Purchase>) {
                val hasSubscription = purchases.any {
                    "test_weekly_subscription" in it.products
                }

                if (hasSubscription) {
                    onSuccess()
                }
                else {
                    billingManager.loadProducts(listOf("test_weekly_subscription"))
                }
            }
        })

        billingManager.connect()
    }
}