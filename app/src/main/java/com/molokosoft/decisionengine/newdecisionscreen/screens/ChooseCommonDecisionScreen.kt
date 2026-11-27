package com.molokosoft.decisionengine.newdecisionscreen.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.commonclasses.DecisionCategory
import com.molokosoft.decisionengine.commonuielements.getColorToLogo
import com.molokosoft.decisionengine.commonuielements.getLogo
import com.molokosoft.decisionengine.theme.LocalAppTypography

data class CommonDecision(
    val label: String,
    val title: String,
    val category: DecisionCategory,
    val explanation: String,
    val exampleOptions: List<String>
)

@Composable
fun ChooseCommonDecisionScreen(
    onDecisionSelected: (CommonDecision) -> Unit,
    onOwnDecisionClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = LocalAppTypography.current
    val hexagonShape = remember {
        GenericShape { size, _ ->
            moveTo(size.width * 0.25f, 0f)
            lineTo(size.width * 0.75f, 0f)
            lineTo(size.width, size.height * 0.5f)
            lineTo(size.width * 0.75f, size.height)
            lineTo(size.width * 0.25f, size.height)
            lineTo(0f, size.height * 0.5f)
            close()
        }
    }

    val choices = listOf(
        CommonDecision(
            "Choose a\nuniversity", "Which university should I choose?", DecisionCategory.EDUCATION,
            "Compare universities by the subjects they offer, cost, location, campus life, and the support available to you.",
            listOf("University of Munich", "Berlin Tech", "University of Vienna")
        ),
        CommonDecision(
            "Choose a\ncareer", "Which career should I pursue?", DecisionCategory.CAREER,
            "Explore career paths by considering your interests, strengths, work environment, and long-term goals.",
            listOf("Software engineer", "Product designer", "Teacher")
        ),
        CommonDecision(
            "Find a\nplace to live", "Where should I live?", DecisionCategory.HOME,
            "Weigh rent, commute, space, neighborhood, and the kind of daily life you want.",
            listOf("Stay where I live", "Move closer to work", "Share an apartment")
        ),
        CommonDecision(
            "Choose a\ncar", "Which car should I buy?", DecisionCategory.SHOPPING,
            "Compare your options by price, reliability, running costs, practicality, and environmental impact.",
            listOf("Buy a new car", "Buy a used car", "Keep my current car")
        ),
        CommonDecision(
            "Choose a\ncourse", "What should I study?", DecisionCategory.EDUCATION,
            "Find a subject that fits your interests, strengths, preferred learning style, and future plans.",
            listOf("Computer science", "Psychology", "Business")
        ),
        CommonDecision(
            "Travel", "Where should I travel?", DecisionCategory.TRAVEL,
            "Choose a destination by balancing your budget, travel time, activities, climate, and travel style.",
            listOf("Japan", "Portugal", "Iceland")
        ),
        CommonDecision(
            "Other", "Which option should I choose?", DecisionCategory.OTHER,
            "Use this for a decision that does not fit the other categories. Compare the alternatives based on what matters most to you.",
            listOf("Option A", "Option B", "Option C")
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 72.dp),
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
                text = "What are you deciding?",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
                fontSize = typography.titleLarge.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = "Choose a common decision to get started.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
                fontSize = typography.bodyLarge.fontSize,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(20.dp))

            CommonDecisionRow(
                choices = choices.take(2),
                hexagonShape = hexagonShape,
                trailingEmptySlots = 1,
                onDecisionSelected = onDecisionSelected
            )
            CommonDecisionRow(
                choices = choices.drop(2).take(3),
                hexagonShape = hexagonShape,
                onDecisionSelected = onDecisionSelected
            )
            CommonDecisionRow(
                choices = choices.drop(5),
                hexagonShape = hexagonShape,
                leadingEmptySlots = 1,
                onDecisionSelected = onDecisionSelected
            )
        }

        Text(
            text = "I have my own decision",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .clickable(onClick = onOwnDecisionClicked),
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize,
            color = Color.Black,
            textDecoration = TextDecoration.Underline
        )
    }

}

@Composable
private fun CommonDecisionRow(
    choices: List<CommonDecision>,
    hexagonShape: Shape,
    leadingEmptySlots: Int = 0,
    trailingEmptySlots: Int = 0,
    onDecisionSelected: (CommonDecision) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        if (leadingEmptySlots > 0) {
            Spacer(modifier = Modifier.weight(leadingEmptySlots.toFloat()))
        }
        choices.forEach { choice ->
            CommonDecisionTile(
                choice = choice,
                shape = hexagonShape,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 1.dp)
                    .aspectRatio(1.1f),
                onClick = { onDecisionSelected(choice) }
            )
        }
        if (trailingEmptySlots > 0) {
            Spacer(modifier = Modifier.weight(trailingEmptySlots.toFloat()))
        }
    }
}

@Composable
private fun CommonDecisionTile(
    choice: CommonDecision,
    shape: Shape,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val typography = LocalAppTypography.current
    val categoryColor = getColorToLogo(choice.category)
    val contentColor = if (categoryColor.luminance() > 0.45f) Color.Black else Color.White

    Column(
        modifier = modifier
            .shadow(4.dp, shape)
            .clip(shape)
            .background(categoryColor)
            .border(2.dp, Color.White, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = getLogo(choice.category)),
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            colorFilter = ColorFilter.tint(contentColor)
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = choice.label,
            textAlign = TextAlign.Center,
            fontSize = typography.titleSmall.fontSize * 0.82f,
            lineHeight = typography.titleSmall.fontSize * 0.86f,
            fontWeight = FontWeight.SemiBold,
            color = contentColor
        )
    }
}
