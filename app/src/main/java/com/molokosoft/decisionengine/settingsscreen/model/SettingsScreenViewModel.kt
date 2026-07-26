package com.molokosoft.decisionengine.settingsscreen.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molokosoft.decisionengine.repositories.UserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SettingsScreenViewModel(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _username =
        MutableStateFlow(username())

    val username =
        _username.asStateFlow()

    fun setUsername(username: String) {
        userDataRepository.setUsername(username)
        _username.value = username
    }

    private fun username(): String {
        return userDataRepository.username()
    }

    fun saveFeedback(feedback: String) {
        viewModelScope.launch {
            userDataRepository.sendFeedback(feedback)
            userDataRepository.setFeedbackLimitReached()
        }
    }

    fun feedbackLimitReached(): Boolean {
        return userDataRepository.feedbackLimitReached()
    }

    fun resetFeedbackLimitReached() {
        return userDataRepository.resetFeedbackLimitReached()
    }

}