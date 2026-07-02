package com.molokosoft.decisionengine.newdecisionscreen.viewmodel

import androidx.lifecycle.ViewModel
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model.*

import com.molokosoft.decisionengine.repositories.FactoryAnalysisRepository
import com.molokosoft.decisionengine.repositories.UserDataRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.molokosoft.decisionengine.commonclasses.EMail

class NewDecisionViewModel(
    private val factorAnalysisRepository: FactoryAnalysisRepository,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _draft = MutableStateFlow(DecisionDraft())
    val draft = _draft.asStateFlow()

    private val _showBottomBar = MutableStateFlow(true)
    val showBottomBar = _showBottomBar.asStateFlow()

    fun showBottomBar() {
        _showBottomBar.value = true
    }

    fun hideBottomBar() {
        _showBottomBar.value = false
    }

    fun resetDraft() {
        _draft.value = DecisionDraft()
    }

    fun setTitle(title: String) {
        _draft.update {
            it.copy(
                title = title
            )
        }

        getCriteriaSuggestions(title)
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
                optionAnalyses = factorAnalysisRepository.analyzeOptions(draft.value.options)
            )
        }

        hideBottomBar()

        viewModelScope.launch {
            _draft.update {
                it.copy(
                    decisionAnalysisResult = factorAnalysisRepository.getAiAnalysis(draft.value.optionAnalyses)
                )
            }
        }
    }

    fun saveEMail(eMail: EMail) {
        viewModelScope.launch {
          //"Save the E-Mail in case the sendEmail-Method fails.")
            val success = userDataRepository.sendEmail(eMail.toString())
            //"If the sendEMail-Method fails, try it again, untils it doesn't fail anymore")
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
}