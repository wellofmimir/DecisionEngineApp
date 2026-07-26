package com.molokosoft.decisionengine.settingsscreen.model

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molokosoft.decisionengine.repositories.UserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SettingsScreenViewModel(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    val username = userDataRepository.username

    fun setUsername(username: String) {
        userDataRepository.setUsername(username)
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