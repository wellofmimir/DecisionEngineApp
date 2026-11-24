package com.molokosoft.decisionengine.newdecisionscreen


import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier

import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.molokosoft.decisionengine.billing.model.SubscriptionProduct
import com.molokosoft.decisionengine.commonclasses.ProductTypes

import com.molokosoft.decisionengine.newdecisionscreen.dialogs.EnterOptionDialog
import com.molokosoft.decisionengine.newdecisionscreen.dialogs.EnterCriterionDialog
import com.molokosoft.decisionengine.newdecisionscreen.screens.EnterComparisonCriteriaScreen
import com.molokosoft.decisionengine.newdecisionscreen.screens.EnterDecisionNameScreen
import com.molokosoft.decisionengine.newdecisionscreen.screens.EnterDecisionOptionsScreen
import com.molokosoft.decisionengine.newdecisionscreen.screens.RateComparisonCriteriaScreen
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.NewDecisionViewModel
import com.molokosoft.decisionengine.newdecisionscreen.screens.ChooseComparisonCriteriaScreen
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model.Criterion
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model.Option
import com.molokosoft.decisionengine.commonuielements.dialogs.ErrorDialog
import com.molokosoft.decisionengine.commonuielements.screens.WaitingScreen
import com.molokosoft.decisionengine.decisionresultscreen.DecisionResultScreen
import com.molokosoft.decisionengine.newdecisionscreen.screens.ForbiddenInputDataScreen
import com.molokosoft.decisionengine.newdecisionscreen.screens.NoMoreUsagesScreen
import com.molokosoft.decisionengine.newdecisionscreen.screens.NotAllowedScreen
import com.molokosoft.decisionengine.newdecisionscreen.screens.AnalysisErrorScreen
import com.molokosoft.decisionengine.newdecisionscreen.screens.quiz.ArrangeScreen
import com.molokosoft.decisionengine.newdecisionscreen.screens.quiz.MultipleChoiceScreen
import com.molokosoft.decisionengine.newdecisionscreen.screens.quiz.SliderScreen
import com.molokosoft.decisionengine.newdecisionscreen.screens.quiz.TextAnswerScreen
import com.molokosoft.decisionengine.newdecisionscreen.screens.quiz.YesNoScreen
import com.molokosoft.decisionengine.commonuielements.screens.WaitingScreenWithRotatingLogo
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model.ErrorCodes
import kotlinx.coroutines.launch

sealed class DecisionScreen {
    data object EnterDecisionName : DecisionScreen()
    data object EnterDecisionOptions : DecisionScreen()
    data object EnterDecisionCriteria : DecisionScreen()
    data object ChooseDecisionCriteria : DecisionScreen()
    data object RateComparisonCriteria : DecisionScreen()

    data object QuizGeneratingScreen : DecisionScreen()

    data object TextAnswerScreen : DecisionScreen()

    data object YesNoScreen : DecisionScreen()

    data object MultipleChoiceScreen : DecisionScreen()

    data object ArrangeScreen : DecisionScreen()

    data object SliderScreen : DecisionScreen()

    data object NotAllowedScreen : DecisionScreen()
    data object ForbiddenInputScreen : DecisionScreen()

    data object NoMoreUsagesScreen : DecisionScreen()

    data object WaitingForAnalysisScreen : DecisionScreen()

    data object AnalysisErrorScreen : DecisionScreen()
}

fun DecisionScreen.next(yesOrNoDecision: Boolean = false): DecisionScreen =
    when (this) {
        DecisionScreen.EnterDecisionName -> {
            if (yesOrNoDecision)
                DecisionScreen.ChooseDecisionCriteria
            else
                DecisionScreen.EnterDecisionOptions
        }

        DecisionScreen.EnterDecisionOptions -> DecisionScreen.ChooseDecisionCriteria
        DecisionScreen.ChooseDecisionCriteria -> DecisionScreen.EnterDecisionCriteria
        DecisionScreen.EnterDecisionCriteria -> DecisionScreen.RateComparisonCriteria
        DecisionScreen.RateComparisonCriteria -> DecisionScreen.EnterDecisionName
        DecisionScreen.NotAllowedScreen -> DecisionScreen.EnterDecisionName
        DecisionScreen.ForbiddenInputScreen -> DecisionScreen.EnterDecisionName
        DecisionScreen.NoMoreUsagesScreen -> DecisionScreen.EnterDecisionName
        DecisionScreen.WaitingForAnalysisScreen -> DecisionScreen.EnterDecisionName
        DecisionScreen.AnalysisErrorScreen -> DecisionScreen.EnterDecisionName

        DecisionScreen.MultipleChoiceScreen -> DecisionScreen.MultipleChoiceScreen
        DecisionScreen.QuizGeneratingScreen -> DecisionScreen.QuizGeneratingScreen
        DecisionScreen.TextAnswerScreen -> DecisionScreen.EnterDecisionName
        DecisionScreen.YesNoScreen -> DecisionScreen.YesNoScreen
        DecisionScreen.ArrangeScreen -> DecisionScreen.ArrangeScreen
        DecisionScreen.SliderScreen -> DecisionScreen.SliderScreen
    }

fun DecisionScreen.previous(yesOrNoDecision: Boolean = false): DecisionScreen =
    when (this) {
        DecisionScreen.MultipleChoiceScreen -> DecisionScreen.MultipleChoiceScreen
        DecisionScreen.QuizGeneratingScreen -> DecisionScreen.QuizGeneratingScreen
        DecisionScreen.TextAnswerScreen -> DecisionScreen.TextAnswerScreen
        DecisionScreen.YesNoScreen -> DecisionScreen.YesNoScreen
        DecisionScreen.ArrangeScreen -> DecisionScreen.ArrangeScreen
        DecisionScreen.SliderScreen -> DecisionScreen.SliderScreen

        DecisionScreen.EnterDecisionOptions -> DecisionScreen.EnterDecisionName
        DecisionScreen.EnterDecisionCriteria -> DecisionScreen.ChooseDecisionCriteria
        DecisionScreen.RateComparisonCriteria -> DecisionScreen.EnterDecisionName
        DecisionScreen.EnterDecisionName -> DecisionScreen.EnterDecisionName
        DecisionScreen.ChooseDecisionCriteria -> DecisionScreen.EnterDecisionName
        DecisionScreen.NotAllowedScreen -> DecisionScreen.EnterDecisionName
        DecisionScreen.ForbiddenInputScreen -> DecisionScreen.EnterDecisionName
        DecisionScreen.NoMoreUsagesScreen -> DecisionScreen.EnterDecisionName
        DecisionScreen.WaitingForAnalysisScreen -> DecisionScreen.EnterDecisionName
        DecisionScreen.AnalysisErrorScreen -> DecisionScreen.EnterDecisionName
    }

@Composable
fun EnterDecisionScreen(
    newDecisionViewModel: NewDecisionViewModel,
    productInformation: List<SubscriptionProduct>,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onContinueClicked: (ErrorCodes, ProductTypes) -> Unit
){
    val context =
        LocalContext.current

    val scope =
        rememberCoroutineScope()

    val showAnalysisErrorScreen by newDecisionViewModel.showAnalysisErrorScreen.collectAsStateWithLifecycle()
    val showNotAllowedScreen by newDecisionViewModel.showNotAllowedScreen.collectAsStateWithLifecycle()
    val showForbiddenInputScreen by newDecisionViewModel.showIsPromptScreen.collectAsStateWithLifecycle()
    val showNoMoreUsagesScreen by newDecisionViewModel.showNoMoreUsagesScreen.collectAsStateWithLifecycle()
    val analysisDone by newDecisionViewModel.analysisDone.collectAsStateWithLifecycle()
    val showWaitingForAnalysisScreen by newDecisionViewModel.showWaitingForAnalysisScreen.collectAsStateWithLifecycle()
    val decisionInfoDialogSeen by newDecisionViewModel.decisionInfoDialogSeen.collectAsStateWithLifecycle()
    val optionInfoDialogSeen by newDecisionViewModel.optionInfoDialogSeen.collectAsStateWithLifecycle()
    val importanceInfoDialogSeen by newDecisionViewModel.importanceInfoDialogSeen.collectAsStateWithLifecycle()
    val ratingInfoDialogSeen by newDecisionViewModel.ratingInfoDialogSeen.collectAsStateWithLifecycle()

    var currentScreen by remember {
        mutableStateOf(
            value = if (showNoMoreUsagesScreen)
                DecisionScreen.NoMoreUsagesScreen
            else
                DecisionScreen.EnterDecisionName
        )
    }

    LaunchedEffect(Unit) {
        newDecisionViewModel.generateQuiz()
        return@LaunchedEffect
    }

    var enterOptionDialogOpen by remember { mutableStateOf(false) }
    var enterCriterionDialogOpen by remember { mutableStateOf(false) }
    var errorMessageDialogOpen by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("" to "") }

    LaunchedEffect(analysisDone) {
        if (analysisDone)
            onContinueClicked(ErrorCodes.SUCCESS, ProductTypes.Undefined)
    }

    LaunchedEffect(showNotAllowedScreen) {
        if (showNotAllowedScreen)
            currentScreen = DecisionScreen.NotAllowedScreen
    }

    LaunchedEffect(showForbiddenInputScreen) {
        if (showForbiddenInputScreen)
            currentScreen = DecisionScreen.ForbiddenInputScreen
    }

    LaunchedEffect(showNoMoreUsagesScreen) {
        if (showNoMoreUsagesScreen)
            currentScreen = DecisionScreen.NoMoreUsagesScreen
    }

    LaunchedEffect(showWaitingForAnalysisScreen) {
        if (showWaitingForAnalysisScreen && !showNoMoreUsagesScreen)
            currentScreen = DecisionScreen.WaitingForAnalysisScreen
    }

    LaunchedEffect(showAnalysisErrorScreen) {
        if (showAnalysisErrorScreen)
            currentScreen = DecisionScreen.AnalysisErrorScreen
    }

    val draft by newDecisionViewModel.draft.collectAsState()

    val optionNames =
        remember(draft.options) {
            draft.options.map {
               it.name
            }
        }

    val criteriaNames by newDecisionViewModel.criteriaNames.collectAsState()

    val yesOrNoDecision =
        draft.yesOrNoDecision

    val nextOptionName =
        newDecisionViewModel.getNextOption()

    when (currentScreen) {
        DecisionScreen.QuizGeneratingScreen -> WaitingScreenWithRotatingLogo(
            modifier = modifier
                .fillMaxHeight(),
            texts = listOf(
                "Finding questions that fit you...",
                "Matching questions to your situation...",
                "Looking for questions about what matters to you...",
                "Putting together a quiz just for you..."
            )
        )

        DecisionScreen.TextAnswerScreen -> TextAnswerScreen(
            modifier = modifier
                .fillMaxHeight(),
            question = "What decision would you like help with?",
            onNextClicked = {
                currentScreen = DecisionScreen.EnterDecisionName
            },
            onBackClicked = {
                newDecisionViewModel.resetDraft()
                onBackClicked()
            }
        )

        DecisionScreen.YesNoScreen -> YesNoScreen(
            modifier = modifier
                .fillMaxHeight(),
            question = "Is this the right decision for you?",
            onNextClicked = {
                currentScreen = DecisionScreen.EnterDecisionName
            },
            onBackClicked = {
                newDecisionViewModel.resetDraft()
                onBackClicked()
            }
        )

        DecisionScreen.EnterDecisionName -> EnterDecisionNameScreen(
            modifier = modifier
                .fillMaxHeight(),
            currentDecisionText = draft.title,
            yesOrNoDecision = yesOrNoDecision,
            showDecisionInfoDialog = !decisionInfoDialogSeen,
            onBackClicked = {
                Log.d("DecisionEngine", "resetDraft() in onBackClicked() in EnterDecisionNameScreen")

                newDecisionViewModel.resetDraft()
                onBackClicked()
            },
            onNextClicked = { name, yesOrNoDecision ->
                if (name.isBlank()) {
                    errorMessage = "A bit more please..." to "Please enter a decision name."
                    errorMessageDialogOpen = true
                    return@EnterDecisionNameScreen
                }

                newDecisionViewModel.setTitle(name)
                newDecisionViewModel.setDecisionType(yesOrNoDecision)
                newDecisionViewModel.setDecisionInfoDialogSeen()

                currentScreen = currentScreen.next(yesOrNoDecision)
            }
        )

        DecisionScreen.SliderScreen -> SliderScreen(
            modifier = modifier
                .fillMaxHeight(),
            question = "How important is it that the university's location and surrounding lifestyle feel naturally compatible with the life you want?",
            onNextClicked = {},
            onBackClicked = {}
        )

        DecisionScreen.ArrangeScreen -> ArrangeScreen(
            modifier = modifier
                .fillMaxHeight(),
            question = "Rank these foundations of your university decision from most to least important.",
            options = listOf(
                "An academic experience that fits me",
                "A financially sustainable choice.",
                "Strong preparation for life after graduation.",
                "A campus where I feel I belong."
            ),
            onNextClicked = {},
            onBackClicked = {}
        )

        DecisionScreen.MultipleChoiceScreen -> MultipleChoiceScreen(
            modifier = modifier
                .fillMaxHeight(),
            question = "During your first month at university, which experience would most reassure you that you chose the right place?",
            options = listOf(
                "I am already meeting people who make me feel included.",
                "The surrounding environment feels energizing and suits my lifestyle.",
                "I know exactly where to find helpful guidance and support.",
                "I get good grades an am well read into my classes."
            ),
            onBackClicked = {},
            onNextClicked = {}
        )

        DecisionScreen.EnterDecisionOptions -> EnterDecisionOptionsScreen(
            modifier = modifier
                .fillMaxHeight(),
            options = optionNames,
            onEnterOptionClicked = {
                enterOptionDialogOpen = true
                newDecisionViewModel.setOptionInfoDialogSeen()
            },
            onDeleteOption = {
                newDecisionViewModel.deleteOption(it)
            },
            onBackClicked = {
                Log.d("DecisionEngine", "resetDraft() in onBackClicked() in EnterDecisionOptionsScreen")

                newDecisionViewModel.resetDraft()
                currentScreen = currentScreen.previous(yesOrNoDecision)
            },
            onNextClicked = {
                if (optionNames.isEmpty() || optionNames.size == 1) {
                    errorMessage = "A bit more please..." to "Please enter at least two options."
                    errorMessageDialogOpen = true
                    return@EnterDecisionOptionsScreen
                }

                currentScreen = currentScreen.next()
            },
            showOptionInfoDialog = !optionInfoDialogSeen
        )

        DecisionScreen.EnterDecisionCriteria -> EnterComparisonCriteriaScreen(
            modifier = modifier
                .fillMaxHeight(),
            criteria = criteriaNames,
            onEnterCriteriaClicked = {
                enterCriterionDialogOpen = true
            },
            onDeleteCriteria = {
                newDecisionViewModel.deleteCriterion(it)
            },
            onBackClicked = {
                currentScreen = currentScreen.previous(yesOrNoDecision)
            },
            onNextClicked = {
                if (criteriaNames.isEmpty()) {
                    errorMessage = "A bit more please..." to "Please enter at least one comparison criterion."
                    errorMessageDialogOpen = true
                    return@EnterComparisonCriteriaScreen
                }

                currentScreen = currentScreen.next()
            }
        )

        DecisionScreen.RateComparisonCriteria -> {
            if (nextOptionName == null) {
                LaunchedEffect(Unit) {
                    if (newDecisionViewModel.isOnboarding) {
                        onContinueClicked(ErrorCodes.SUCCESS, ProductTypes.Undefined)
                        return@LaunchedEffect
                    }

                    scope.launch {
                        newDecisionViewModel.onComparisonCompleted()
                    }
                }

                return
            }

            RateComparisonCriteriaScreen(
                modifier = modifier
                    .fillMaxHeight(),
                showRatingInfoDialog = !ratingInfoDialogSeen,
                onInfoDialogClicked = {
                    newDecisionViewModel.setRatingInfoDialogSeen()
                },
                option = nextOptionName,
                criterionAndDescription = draft.criteriaSuggestions
                    .filter {
                        it.name in criteriaNames
                    }
                    .map {
                        it.name to it.description
                    },
                onBackClicked = {
                    currentScreen = DecisionScreen.ChooseDecisionCriteria
                },
                onNextClicked = { list ->
                    val listOfCriteria = list.map { (name, rating) ->
                        val originalCriterion = draft.criteria.first {
                            it.name == name
                        }

                        originalCriterion.copy(
                            score = rating.toInt()
                        )
                    }

                    newDecisionViewModel.setRatedCriteriaToOption(
                        optionName = nextOptionName,
                        criteria = listOfCriteria
                    )
                }
            )
        }

        DecisionScreen.ChooseDecisionCriteria -> {
            ChooseComparisonCriteriaScreen(
                modifier = modifier
                    .fillMaxHeight(),
                alreadySelectedCriteria = criteriaNames,
                criterionAndDescription = draft.criteriaSuggestions.map {
                    it.name to it.description
                },
                showImportanceInfoDialog = !importanceInfoDialogSeen,
                onImportanceDialogClicked = {
                    newDecisionViewModel.setImportanceInfoDialogSeen()
                },
                onCriterionClicked = { name, importance ->
                    newDecisionViewModel.setCriteria(Criterion(name, importance))
                },
                onCriterionDeleted = { name ->
                    newDecisionViewModel.deleteCriterion(name)
                },
                onBackClicked = {
                    Log.d("DecisionEngine", "resetDraft() in onBackClicked() in ChooseComparisonCriteriaScreen")

                    newDecisionViewModel.resetDraft()
                    currentScreen = currentScreen.previous()
                },
                onNextClicked = {
                    currentScreen = currentScreen.next()
                }
            )
        }

        DecisionScreen.NotAllowedScreen -> {
            NotAllowedScreen(
                modifier = modifier
                    .fillMaxHeight(),
                context = context,
                onBackClicked = {
                    Log.d("DecisionEngine", "resetDraft() in onBackClicked() in NotAllowedScreen")

                    newDecisionViewModel.resetDraft()
                    currentScreen = DecisionScreen.EnterDecisionName
                }
            )
        }

        DecisionScreen.ForbiddenInputScreen -> {
            ForbiddenInputDataScreen(
                modifier = modifier
                    .fillMaxHeight(),
                onBackClicked = {
                    Log.d("DecisionEngine", "resetDraft() in onBackClicked() in ForbiddenInputDataScreen")

                    newDecisionViewModel.resetDraft()
                    currentScreen = DecisionScreen.EnterDecisionName
                }
            )
        }

        DecisionScreen.NoMoreUsagesScreen -> {
            NoMoreUsagesScreen(
                modifier = modifier
                    .fillMaxHeight(),
                productInformation = productInformation,
                onAccepted = { productType ->
                    onContinueClicked(ErrorCodes.NO_MORE_USAGES, productType)
                },
                onDeclined = {
                    Log.d("DecisionEngine", "resetDraft() in onDeclined() in NoMoreUsagesScreen")

                    newDecisionViewModel.resetDraft()
                    onBackClicked()
                }
            )
        }

        DecisionScreen.AnalysisErrorScreen -> {
            AnalysisErrorScreen(
                modifier = modifier
                    .fillMaxHeight(),
                errorMessage = draft.errorMessage.toString(),
                onDismiss = {
                    newDecisionViewModel.resetDraft()
                    currentScreen = DecisionScreen.EnterDecisionName
                }
            )
        }

        DecisionScreen.WaitingForAnalysisScreen -> {
            WaitingScreen(
                modifier = modifier
                    .fillMaxHeight(),
                texts = listOf(
                    "Analyzing your decision...\n" + "This usually takes just a few seconds.",
                    "Weighing the important factors...\n" + "This won't take long.",
                    "Working through your decision...\n" + "Almost there.",
                    "Finding the key factors in your decision...\n" + "Just a moment."
                )
            )
        }
    }

    if (enterOptionDialogOpen)
        EnterOptionDialog(
            onOptionEntered = { name, reversibility ->
                if (name.isBlank()) {
                    errorMessage = "A bit more please..." to "Please enter an option name."
                    errorMessageDialogOpen = true
                    return@EnterOptionDialog
                }

                newDecisionViewModel.setOption(Option(name = name, reversibility = reversibility))
                enterOptionDialogOpen = false
            },
            onDismissRequest = {
                enterOptionDialogOpen = false
            }
        )
    else if (enterCriterionDialogOpen)
        EnterCriterionDialog(
            onCriterionEntered = { name, importance ->
                if (name.isBlank()) {
                    errorMessage = "A bit more please..." to "Please enter an criterion name."
                    errorMessageDialogOpen = true
                    return@EnterCriterionDialog
                }

                newDecisionViewModel.setCriteria(Criterion(name = name, importance = importance))
                enterCriterionDialogOpen = false
            },
            onDismissRequest = {
                enterCriterionDialogOpen = false
            }
        )
    else if (errorMessageDialogOpen)
        ErrorDialog(
            errorTitle = errorMessage.first,
            errorMessage = errorMessage.second,
            onDismissRequest = {
                errorMessageDialogOpen = false
            },
            onAcceptRequest = {
                errorMessageDialogOpen = false
            }
        )
}
