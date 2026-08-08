package com.molokosoft.decisionengine.settingsscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun DecisionTextInput(
    username: String,
    modifier: Modifier = Modifier,
    onNewUsernameEntered: (newUsername: String) -> Unit
) {
    val typography = LocalAppTypography.current
    val focusManager = LocalFocusManager.current

    var newUsername by rememberSaveable { mutableStateOf(username) }
    var hasChanged by remember { mutableStateOf(false) }
    var showCheckmark by remember { mutableStateOf(false) }

    LaunchedEffect(newUsername) {
        if (!hasChanged)
            return@LaunchedEffect

        showCheckmark = true
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = DecisionBlue
            )

            Spacer(
                modifier = Modifier
                    .width(16.dp)
            )

            BasicTextField(
                value = newUsername,
                onValueChange = {
                    hasChanged = true
                    newUsername = it
                },
                modifier = Modifier
                    .weight(1f),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (username.isBlank()) {
                        Text(
                            text = "Ex: John Doe",
                            color = DecisionBlueLight
                        )
                    }

                    innerTextField()
                }
            )

            AnimatedVisibility(showCheckmark) {
                Box(
                    modifier = Modifier
                        .background(
                            color = DecisionBlue,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .size(26.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .clickable {
                                onNewUsernameEntered(newUsername)
                                showCheckmark = false
                                focusManager.clearFocus()
                            }
                    )
                }
            }
        }
    }
}