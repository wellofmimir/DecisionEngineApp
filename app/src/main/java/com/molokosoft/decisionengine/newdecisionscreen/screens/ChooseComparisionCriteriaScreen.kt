package com.molokosoft.decisionengine.newdecisionscreen.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.DecisionBlueLight
import com.molokosoft.decisionengine.theme.LocalAppTypography

import com.molokosoft.decisionengine.commonuielements.ErrorDialog
import com.molokosoft.decisionengine.commonuielements.WaitingScreen
import com.molokosoft.decisionengine.newdecisionscreen.dialogs.EnterCriterionImportanceDialog

@Composable
fun ChooseComparisonCriteriaScreen(
    criterionAndDescription: List<Pair<String, String>>,
    alreadySelectedCriteria: List<String>,
    onCriterionClicked: (name: String, importance: Int) -> Unit,
    onCriterionDeleted: (name: String) -> Unit,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    criterionAndDescription.ifEmpty {
        WaitingScreen(
            text = "Thinking of useful criteria...\n" + "This usually takes just a few seconds."
        )

        return
    }

    val typography = LocalAppTypography.current

    var showInfoDialog by remember { mutableStateOf(false) }
    var showEnterCriterionDialog by remember { mutableStateOf(false) }
    var criterionToAdd by remember { mutableStateOf<String?>(null) }
    var infoTextAndDescription by remember { mutableStateOf("" to "") }
    var criterionToImportance by remember { mutableStateOf("" to 5) }

    var selectedCriteria by remember {
        mutableStateOf(setOf<String>())
    }

    if (showInfoDialog) //ErrorDialog wird hier als InfoDialog verwendet
        ErrorDialog(
            errorTitle = infoTextAndDescription.first,
            errorMessage = infoTextAndDescription.second,
            errorButtonText = "Add",
            onDismissRequest = {
                showInfoDialog = false
                criterionToAdd = null
            },
            onAcceptRequest = {
                criterionToAdd?.let { criterion ->
                    if (!selectedCriteria.contains(criterion)) {
                        selectedCriteria = selectedCriteria + criterion
                        criterionToImportance = criterion to 5
                    }
                }

                showInfoDialog = false
                showEnterCriterionDialog = true
                criterionToAdd = null
            }
        )

    if (showEnterCriterionDialog)
        EnterCriterionImportanceDialog(
            criterionName = criterionToImportance.first,
            onCriterionEntered = { name, importance ->
                criterionToImportance = name to importance
                showEnterCriterionDialog = false
                onCriterionClicked(criterionToImportance.first, criterionToImportance.second)
            },
            onDismissRequest = {
                showEnterCriterionDialog = false
                selectedCriteria = selectedCriteria - criterionToImportance.first
                onCriterionDeleted(criterionToImportance.first)
            }
        )

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

        if (criterionAndDescription.isEmpty()) {
            Text(
                text = "Reasoning about criteria\nthat may fit your decision...",
                textAlign = TextAlign.Center,
                fontSize = typography.titleMedium.fontSize,
                color = Color.Black
            )
        } else {
            Text(
                text = "Here are a few criteria that\nmay be relevant to your decision.",
                textAlign = TextAlign.Center,
                fontSize = typography.titleMedium.fontSize,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            Text(
                text = "Tap to add a criterion." +
                        "\nDouble Tap to remove a criterion",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black
            )
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(0.75f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(criterionAndDescription){ criterion ->

                val isSelected =
                    criterion.first in alreadySelectedCriteria

                val backGroundColor =
                    if (isSelected)
                        DecisionBlue
                    else
                        DecisionBlueLight

                val textColor =
                    if (isSelected)
                        Color.White
                    else
                        Color.Black

                Box(
                    modifier = Modifier
                        .heightIn(min = 48.dp, max = 64.dp)
                        .fillMaxWidth()
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .background(
                            color = backGroundColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = DecisionBlueLight,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .pointerInput(isSelected) {
                            detectTapGestures(
                                onTap = {
                                    if (!isSelected) {
                                        criterionToAdd = criterion.first
                                        infoTextAndDescription = criterion.first to criterion.second
                                        showInfoDialog = true
                                    }
                                },
                                onDoubleTap = {
                                    if (isSelected) {
                                        selectedCriteria = selectedCriteria - criterion.first
                                        onCriterionDeleted(criterion.first)
                                    }
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = criterion.first,
                        textAlign = TextAlign.Center,
                        color = textColor,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                )
            }
        }

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
                .clickable(){
                    onNextClicked()
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
}