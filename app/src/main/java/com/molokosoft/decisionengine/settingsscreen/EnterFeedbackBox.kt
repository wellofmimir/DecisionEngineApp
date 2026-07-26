package com.molokosoft.decisionengine.settingsscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.molokosoft.decisionengine.theme.LocalAppTypography
import androidx.compose.ui.Modifier
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import kotlinx.coroutines.delay

@Composable
fun EnterFeedbackBox(
    modifier: Modifier = Modifier,
    feedbackLimitReached: Boolean,
    onDismissRequest: () -> Unit,
    onSend: (feedback: String) -> Unit
) {
    val typography = LocalAppTypography.current
    var feedbackText by remember { mutableStateOf("") }
    var feedbackSent by remember { mutableStateOf(false) }

    LaunchedEffect(feedbackSent) {
        if (feedbackSent) {
            delay(1500)
            onSend(feedbackText)
        }
    }

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
            if (feedbackLimitReached) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = DecisionBlue,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                    )

                    Text(
                        text = "Thanks for helping us improve!",
                        fontSize = typography.titleMedium.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )

                    Spacer(
                        Modifier
                            .height(8.dp)
                    )

                    Text(
                        text = "You've reached the feedback limit for now. We'd love to hear more from you later.",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                }

                return@Dialog
            }

            if (feedbackSent) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = DecisionBlue,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Thanks for your feedback!",
                        fontSize = typography.titleMedium.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "We really appreciate you taking the time.",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                }
            } else {
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
                        text = "Tell us your thoughts!",
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
                                text = "I really love DecisionEngine! ;D",
                                textAlign = TextAlign.Center,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.ExtraLight,
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        },
                        value = feedbackText,
                        onValueChange = {
                            feedbackText = it
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
                            .fillMaxHeight(0.75f)
                            .fillMaxWidth(0.9f)
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
                            .clickable {
                                feedbackSent = true
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Send",
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
}