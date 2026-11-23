package com.molokosoft.decisionengine.newdecisionscreen.screens.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextDecoration

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.DecisionGreen
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun OptionTile(
    text: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean
) {
    val typography = LocalAppTypography.current

    Box(
        modifier = modifier
            .heightIn(128.dp, 256.dp)
            .fillMaxWidth()
            .border(
                color = if (isSelected) Color.Black else DecisionBlueLight,
                width = 1.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = if (isSelected) DecisionGreen else DecisionBlue,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize * 1.1f,
            color = if (isSelected) Color.Black else Color.White
        )
    }
}

@Composable
fun MultipleChoiceScreen(
    question: String,
    options: List<String>,
    onNextClicked: (optionIndex: Int) -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalAppTypography.current
    var selectedOptionIndex by remember { mutableIntStateOf(-1) }

    Column(
        modifier = modifier
            .background(
                color = Color.White
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(64.dp)
        )

        Image(
            painter = painterResource(
                id = R.drawable.decisionenginelogonew
            ),
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
                .height(64.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 2.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                OptionTile(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedOptionIndex = 0
                        },
                    text = options[0],
                    isSelected = selectedOptionIndex == 0
                )

                OptionTile(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedOptionIndex = 1
                        },
                    text = options[1],
                    isSelected = selectedOptionIndex == 1
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                OptionTile(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedOptionIndex = 2
                        },
                    text = options[2],
                    isSelected = selectedOptionIndex == 2
                )

                OptionTile(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedOptionIndex = 3
                        },
                    text = options[3],
                    isSelected = selectedOptionIndex == 3
                )
            }
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
                .clickable {
                    onNextClicked(selectedOptionIndex)
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