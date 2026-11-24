package com.molokosoft.decisionengine.newdecisionscreen.screens.quiz

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.LocalAppTypography

private const val MAX_ANSWER_LENGTH = 500

@Composable
fun TextAnswerScreen(
    question: String,
    onNextClicked: (answer: String) -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography =
        LocalAppTypography.current

    var answer by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            text = question,
            textAlign = TextAlign.Center,
            fontSize = typography.titleMedium.fontSize,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier
                .height(32.dp)
        )

        OutlinedTextField(
            value = answer,
            onValueChange = { newValue ->
                answer = newValue.take(MAX_ANSWER_LENGTH)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            minLines = 5,
            maxLines = 8,
            placeholder = {
                Text(
                    text = "Type your answer here...",
                    color = Color.LightGray,
                    fontStyle = FontStyle.Italic
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = DecisionBlue,
                unfocusedBorderColor = DecisionBlue,
                cursorColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text
            )
        )

        Text(
            text = "${answer.length}/$MAX_ANSWER_LENGTH",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.End,
            color = Color.DarkGray
        )

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
                .clickable {
                    onNextClicked(answer)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Done",
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
                .clickable {
                    onBackClicked()
                }
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
    }
}
