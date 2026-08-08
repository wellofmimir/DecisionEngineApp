package com.molokosoft.decisionengine.newdecisionscreen


import androidx.compose.ui.Modifier

import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

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
import com.molokosoft.decisionengine.commonuielements.ErrorDialog
import com.molokosoft.decisionengine.newdecisionscreen.screens.NotAllowedScreen

sealed class DecisionScreen {
    data object EnterDecisionName : DecisionScreen()
    data object EnterDecisionOptions : DecisionScreen()
    data object EnterDecisionCriteria : DecisionScreen()

    data object ChooseDecisionCriteria : DecisionScreen()
    data object RateComparisonCriteria : DecisionScreen()
    data object NotAllowedScreen : DecisionScreen()
}

fun DecisionScreen.next(yesOrNoDecision: Boolean = false): DecisionScreen =
    when (this) {
        DecisionScreen.EnterDecisionName -> {
            if (yesOrNoDecision)
                DecisionScreen.EnterDecisionCriteria
            else
                DecisionScreen.EnterDecisionOptions
        }

        DecisionScreen.EnterDecisionOptions -> DecisionScreen.ChooseDecisionCriteria
        DecisionScreen.ChooseDecisionCriteria -> DecisionScreen.EnterDecisionCriteria
        DecisionScreen.EnterDecisionCriteria -> DecisionScreen.RateComparisonCriteria
        DecisionScreen.RateComparisonCriteria -> DecisionScreen.EnterDecisionName
        DecisionScreen.NotAllowedScreen -> DecisionScreen.EnterDecisionName
    }

fun DecisionScreen.previous(yesOrNoDecision: Boolean = false): DecisionScreen =
    when (this) {
        DecisionScreen.EnterDecisionOptions -> DecisionScreen.EnterDecisionName

        DecisionScreen.EnterDecisionCriteria -> {
            if (yesOrNoDecision)
                DecisionScreen.EnterDecisionName
            else
                DecisionScreen.EnterDecisionOptions
        }

        DecisionScreen.RateComparisonCriteria -> DecisionScreen.EnterDecisionName
        DecisionScreen.EnterDecisionName -> DecisionScreen.EnterDecisionName
        DecisionScreen.ChooseDecisionCriteria -> DecisionScreen.EnterDecisionName
        DecisionScreen.NotAllowedScreen -> DecisionScreen.EnterDecisionName
    }

@Composable
fun EnterDecisionScreen(
    newDecisionViewModel: NewDecisionViewModel,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onContinueClicked: () -> Unit
){
    val context = LocalContext.current

    var currentScreen by remember {
        mutableStateOf<DecisionScreen>(DecisionScreen.EnterDecisionName)
    }

    var enterOptionDialogOpen by remember { mutableStateOf(false) }
    var enterCriterionDialogOpen by remember { mutableStateOf(false) }
    var errorMessageDialogOpen by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("" to "") }

    val showNotAllowedScreen by newDecisionViewModel.showNotAllowedScreen.collectAsState()

    if (showNotAllowedScreen)
        currentScreen = DecisionScreen.NotAllowedScreen

    val draft by newDecisionViewModel.draft.collectAsState()

    val optionNames = remember(draft.options) {
        draft.options.map {
            it.name
        }
    }

    val criteriaNames = remember(draft.criteria) {
        draft.criteria.map {
            it.name
        }
    }

    val yesOrNoDecision = draft.yesOrNoDecision
    val nextOptionName = newDecisionViewModel.getNextOption()

    when (currentScreen) {
        DecisionScreen.EnterDecisionName -> EnterDecisionNameScreen(
            modifier = modifier,
            currentDecisionText = draft.title,
            yesOrNoDecision = yesOrNoDecision,
            onBackClicked = {
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
                currentScreen = currentScreen.next(yesOrNoDecision)
            }
        )

        DecisionScreen.EnterDecisionOptions -> EnterDecisionOptionsScreen(
            modifier = modifier,
            options = optionNames,
            onEnterOptionClicked = {
                enterOptionDialogOpen = true
            },
            onDeleteOption = {
                newDecisionViewModel.deleteOption(it)
            },
            onBackClicked = {
                currentScreen = currentScreen.previous(yesOrNoDecision)
            },
            onNextClicked = {
                if (optionNames.isEmpty() || optionNames.size == 1) {
                    errorMessage = "A bit more please..." to "Please enter at least two options."
                    errorMessageDialogOpen = true
                    return@EnterDecisionOptionsScreen
                }

                currentScreen = currentScreen.next()
            }
        )

        DecisionScreen.EnterDecisionCriteria -> EnterComparisonCriteriaScreen(
            modifier = modifier,
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
                    newDecisionViewModel.startAnalysis()
                    onContinueClicked()
                }

                return
            }

            RateComparisonCriteriaScreen(
                modifier = modifier,
                option = nextOptionName,
                criteria = criteriaNames,
                onBackClicked = {

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
                modifier = modifier,
                criterionAndDescription = draft.criteriaSuggestions.map {
                    it.name to it.description
                },
                onCriterionClicked = { name, importance ->
                    newDecisionViewModel.setCriteria(Criterion(name, importance))
                },
                onCriterionDeleted = { name ->
                    newDecisionViewModel.deleteCriterion(name)
                },
                onBackClicked = {

                },
                onNextClicked = {
                    currentScreen = currentScreen.next()
                }
            )
        }

        DecisionScreen.NotAllowedScreen -> {
            NotAllowedScreen(
                modifier = modifier,
                context = context
            )
        }
    }

    if (enterOptionDialogOpen)
        EnterOptionDialog(
            onOptionEntered = { name, reversibility ->
                newDecisionViewModel.setOption(
                    Option(
                        name = name,
                        reversibility = reversibility
                    )
                )

                enterOptionDialogOpen = false
            },
            onDismissRequest = {
                enterOptionDialogOpen = false
            }
        )
    else if (enterCriterionDialogOpen)
        EnterCriterionDialog(
            onCriterionEntered = { name, importance ->
                newDecisionViewModel.setCriteria(
                    Criterion(
                        name = name,
                        importance = importance
                    )
                )

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
            }
        )
}
