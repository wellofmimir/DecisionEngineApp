package com.molokosoft.decisionengine.newdecisionscreen.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.newdecisionscreen.dialogs.DecisionInputInfoDialog
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun EnterDecisionNameScreen(
    modifier: Modifier = Modifier,
    currentDecisionText: String,
    yesOrNoDecision: Boolean,
    showDecisionInfoDialog: Boolean,
    onBackClicked: () -> Unit,
    onNextClicked: (name: String, yesOrNoDecision: Boolean) -> Unit
){
    val typography =
        LocalAppTypography.current

    val focusManager =
        LocalFocusManager.current

    val keyboardController =
        LocalSoftwareKeyboardController.current

    var decisionText by remember { mutableStateOf(currentDecisionText) }
    var checked by remember { mutableStateOf(yesOrNoDecision) }
    var inputTextFieldFocused by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(showDecisionInfoDialog) }
    var isReadyToContinue by remember { mutableStateOf(!showDecisionInfoDialog) }

    Column(
        modifier = modifier
            .background(
                color = Color.White
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(
            modifier = Modifier
                .height(64.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.decisionenginelogonew),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "What decision do",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Text(
            text = "you need help with?",
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        OutlinedTextField(
            placeholder = {
                Text(
                    text = "Ex: Which Car should I buy?",
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Thin,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            value = decisionText,
            onValueChange = {
                decisionText = it
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = DecisionBlueLight,
                unfocusedContainerColor = DecisionBlueLight,

                focusedBorderColor = DecisionBlueLight,
                unfocusedBorderColor = DecisionBlueLight,

                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = DecisionBlueLight,
                    shape = RoundedCornerShape(12.dp)
                )
                .onFocusChanged { focusState ->
                    if (focusState.isFocused && !inputTextFieldFocused) {
                        inputTextFieldFocused = true
                        keyboardController?.hide()
                    }
                }
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    checked = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = DecisionBlueLight,
                    uncheckedColor = Color.Black
                )
            )

            Text(
                text = "This is a yes-or-no decision",
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Box(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(0.75f)
                .border(
                    width = 1.dp,
                    color = DecisionBlue,
                    shape = RoundedCornerShape(64.dp)
                )
                .background(
                    color = DecisionBlue,
                    shape = RoundedCornerShape(64.dp)
                )
                .clickable() {
                    if (isReadyToContinue) {
                        onNextClicked(decisionText, checked)
                    }
                },
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Next",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "Back",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = typography.titleSmall.fontSize,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable() {
                    onBackClicked()
                }
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }

    if (showInfoDialog && inputTextFieldFocused) {
        DecisionInputInfoDialog(
            onDismissRequest = {
                showInfoDialog = false
                isReadyToContinue = true
            }
        )
    }
}