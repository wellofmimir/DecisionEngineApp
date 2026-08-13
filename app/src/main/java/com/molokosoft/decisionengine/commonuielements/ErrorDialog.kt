package com.molokosoft.decisionengine.commonuielements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.compose.material3.AlertDialog
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    errorTitle: String,
    errorMessage: String,
    errorButtonText: String = "Okay",
    onDismissRequest: () -> Unit,
    onAcceptRequest: () -> Unit
){
    val typography = LocalAppTypography.current

    AlertDialog (
        modifier = Modifier
            .background (
                color = DecisionBlueLight,
                shape = RoundedCornerShape(12.dp)
            ),
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            Text (
                text = errorTitle,
                color = Color.Black
            )
        },
        text = {
            Text (
                text = errorMessage,
                color = Color.Black
            )
        },
        confirmButton = {
            TextButton (
                modifier = Modifier
                    .border (
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background (
                        color = DecisionBlue,
                        shape = RoundedCornerShape(12.dp)
                    ),
                onClick = {
                    onAcceptRequest()
                }
            ) {
                Text (
                    text = errorButtonText,
                    color = Color.White
                )
            }
        },
        containerColor = DecisionBlueLight
    )
}