package com.molokosoft.decisionengine.newdecisionscreen.dialogs

import androidx.compose.foundation.border
import com.molokosoft.decisionengine.theme.LocalAppTypography

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Slider
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction

import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight

@Composable
fun EnterCriterionDialog(
    modifier: Modifier = Modifier,
    onCriterionEntered: (name: String, importance: Int) -> Unit,
    onDismissRequest: () -> Unit
){
    val typography = LocalAppTypography.current
    val focusManager = LocalFocusManager.current

    var criteriaName by remember { mutableStateOf("") }
    var score by remember { mutableFloatStateOf(5f) }

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ){
        Box(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = DecisionBlueLight,
                    shape = RoundedCornerShape(12.dp)
                )
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    color = DecisionBlueLight,
                    shape = RoundedCornerShape(12.dp)
                )
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )

                Text(
                    text = "Enter your criterion",
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
                            text = "Ex: Price",
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.ExtraLight,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    },
                    value = criteriaName,
                    onValueChange = {
                        criteriaName = it
                    },
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
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
                )

                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )

                Text(
                    text = "How important is",
                    textAlign = TextAlign.Center,
                    fontSize = typography.titleMedium.fontSize,
                    color = Color.Black
                )

                Text(
                    text = "this criterion?",
                    textAlign = TextAlign.Center,
                    fontSize = typography.titleMedium.fontSize,
                    color = Color.Black
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Slider(
                        value = score,
                        onValueChange = { score = it },
                        valueRange = 1f..10f,
                        steps = 8,
                        colors = SliderDefaults.colors(
                            thumbColor = DecisionBlue,
                            activeTrackColor = DecisionBlue,
                            inactiveTrackColor = DecisionBlueLight,
                            activeTickColor = Color.White,
                            inactiveTickColor = DecisionBlue
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        (1..10).forEach {
                            Text(
                                text = it.toString(),
                                color = Color.Black
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )

                Text(
                    text = "1 - Unimportant\n10 - Very important",
                    textAlign = TextAlign.Center,
                    fontSize = typography.titleSmall.fontSize,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraLight
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
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(64.dp)
                        )
                        .background(
                            color = DecisionBlue,
                            shape = RoundedCornerShape(64.dp)
                        )
                        .clickable() {
                            onCriterionEntered(criteriaName, score.toInt())
                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Add",
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
            }
        }
    }
}