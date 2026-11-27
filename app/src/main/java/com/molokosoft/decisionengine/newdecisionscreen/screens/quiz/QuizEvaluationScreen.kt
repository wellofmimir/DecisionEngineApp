package com.molokosoft.decisionengine.newdecisionscreen.screens.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.newdecisionscreen.viewmodel.model.QuizCriterion
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun QuizEvaluationScreen(
    criteria: List<QuizCriterion>,
    personalityDescription: String,
    onImportanceChanged: (criterionId: String, importance: Int) -> Unit,
    onContinue: (List<QuizCriterion>) -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val editableCriteria = remember(criteria) {
        mutableStateListOf<QuizCriterion>().apply { addAll(criteria) }
    }
    val typography = LocalAppTypography.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            Image(
                painter = painterResource(id = R.drawable.decisionenginelogonew),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your estimated decision\nprofile",
                textAlign = TextAlign.Center,
                fontSize = typography.titleLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Text(
                text = "The app estimated these priorities and importance levels from your quiz answers. Feel free to adjust them to better reflect what matters to you.",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .background(DecisionBlueLight, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                editableCriteria.forEachIndexed { index, criterion ->
                    Text(
                        text = criterion.name,
                        fontSize = typography.titleMedium.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        textDecoration = TextDecoration.Underline
                    )

                    if (criterion.description.isNotBlank()) {
                        Text(
                            text = criterion.description,
                            fontSize = typography.titleSmall.fontSize,
                            color = Color.Black
                        )
                    }

                    val importance = criterion.initialWeight.coerceIn(1, 10)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = {
                                val updatedImportance = (importance - 1).coerceAtLeast(1)
                                editableCriteria[index] = criterion.copy(initialWeight = updatedImportance)
                                onImportanceChanged(criterion.id, updatedImportance)
                            },
                            enabled = importance > 1
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Decrease importance",
                                tint = DecisionBlue
                            )
                        }
                        Text(
                            text = "Importance: $importance/10",
                            fontSize = typography.titleSmall.fontSize,
                            color = Color.Black
                        )
                        IconButton(
                            onClick = {
                                val updatedImportance = (importance + 1).coerceAtMost(10)
                                editableCriteria[index] = criterion.copy(initialWeight = updatedImportance)
                                onImportanceChanged(criterion.id, updatedImportance)
                            },
                            enabled = importance < 10
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increase importance",
                                tint = DecisionBlue
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(importance / 10f)
                                .fillMaxHeight()
                                .background(DecisionBlue)
                        )
                    }

                    Text(
                        text = "Remove criterion",
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { editableCriteria.removeAt(index) },
                        fontSize = typography.titleSmall.fontSize,
                        color = DecisionBlue
                    )

                    if (index != editableCriteria.lastIndex) {
                        HorizontalDivider(color = Color.LightGray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .background(DecisionBlueLight, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Personality estimate",
                    fontSize = typography.titleMedium.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = personalityDescription,
                    fontSize = typography.titleSmall.fontSize,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth(0.75f)
                    .background(DecisionBlue, RoundedCornerShape(64.dp))
                    .clickable {
                        onContinue(editableCriteria.filter { it.name.isNotBlank() })
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Continue", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Back to quiz",
                modifier = Modifier.clickable(onClick = onBackClicked),
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black
            )
        }
    }
}
