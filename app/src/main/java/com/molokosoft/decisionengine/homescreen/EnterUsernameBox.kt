package com.molokosoft.decisionengine.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography
import androidx.compose.ui.Modifier

@Composable
fun EnterUsernameBox(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onAdd: (username: String) -> Unit
) {
    val typography = LocalAppTypography.current
    var newUsername by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ){
        Box(
            modifier = Modifier
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
                modifier =Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )

                Text(
                    text = "Hi!\nWhat is your name?",
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
                            text = "Ex: John Doe",
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.ExtraLight,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    },
                    value = newUsername,
                    onValueChange = {
                        newUsername = it
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
                            onAdd(newUsername)
                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Save",
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