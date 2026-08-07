package com.molokosoft.decisionengine.homescreen

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import com.molokosoft.decisionengine.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import com.molokosoft.decisionengine.database.relation.DecisionCompleteRelation


import com.molokosoft.decisionengine.theme.LocalAppTypography
import com.molokosoft.decisionengine.homescreen.buttons.NewDecisionButton
import com.molokosoft.decisionengine.homescreen.viewmodel.HomeScreenViewModel
import com.molokosoft.decisionengine.theme.DecisionBlueLight

@Composable
fun Home(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit,
    onViewHistoryRequested: () -> Unit,
    onSettingsHistoryClicked: () -> Unit,
    onShowOldDecisionClicked: (oldDecision: DecisionCompleteRelation) -> Unit,
    homeScreenViewModel: HomeScreenViewModel,
    showMotivationalQuote: Boolean = false
){
    val typography = LocalAppTypography.current
    val verticalScroll = rememberScrollState()

    val article by homeScreenViewModel.article.collectAsState()
    val decisionHistory by homeScreenViewModel.historyItems.collectAsState()
    val username by homeScreenViewModel.username.collectAsState()
    val averageConfidence by homeScreenViewModel.averageConfidence.collectAsState()
    val amountOfDecisions by homeScreenViewModel.amountOfDecisions.collectAsState()
    val averageOptionsPerDecision by homeScreenViewModel.averageOptionsPerDecision.collectAsState()

    var showUsernameEntryBox by remember { mutableStateOf(false) }
    var showMotivationalQuoteBox by remember { mutableStateOf(false) }
    var readArticleBoxExpanded by remember { mutableStateOf(false) }

    val listOfGreetings = listOf(
        "Hey there,\n$username.",
        "It's great to have you back!",
        "Welcome back,\n$username!",
        "Hello, $username!",
        "Hi there!",
        "Good to see you again!",
        "Glad you're here!",
        "Ready to get started?",
        "Let's get started!",
        "Let's make today count.",
        "Time to get things done.",
        "Hope you're having a great day!",
        "Everything is ready for you.",
        "Great to see you, $username!",
        "Let's do this!",
        "You've got this!",
        "One step at a time.",
        "Every day is a fresh start.",
        "Ready when you are.",
        "Let's keep the momentum going!",
        "Welcome back.\nLet's continue.",
        "Nice to see you again, $username!",
        "Hope you're doing well, $username!",
        "Let's achieve something great today.",
        "Your next step starts here.",
        "Another day,\nanother opportunity.",
        "Success starts with a single step.",
        "Let's make some progress!",
        "Happy to see you back, $username!"
    )

    val greetingText by rememberSaveable { mutableStateOf(if (username.isBlank()) "Welcome, Stranger!" else listOfGreetings.random()) }

    val notificationPermissionLauncher = rememberLauncherForActivityResult (
        contract = ActivityResultContracts.RequestPermission()
    ) {}

    LaunchedEffect(Unit) {
        homeScreenViewModel.getDailyArticle()

        if (username.isBlank()) {
            delay(1000)
            showUsernameEntryBox = true
        } else if (showMotivationalQuote) {
            showMotivationalQuoteBox = true
        }
    }

    LaunchedEffect(showMotivationalQuote) {
        showMotivationalQuoteBox = showMotivationalQuote
    }

    Column(
        modifier = modifier
            .verticalScroll(verticalScroll)
            .background(
                color = Color.White
            )
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(80.dp)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                Text(
                    text = greetingText,
                    fontSize = typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    softWrap = true,
                    maxLines = 3,
                    lineHeight = 28.sp,
                    modifier = Modifier
                        .padding(bottom = 2.dp)
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = "What decision are you working on today?",
                    fontSize = typography.titleMedium.fontSize * 0.75f,
                    fontWeight = FontWeight.Light,
                    color = Color.Black
                )
            }

            Spacer(
                modifier = Modifier
                    .width(16.dp)
            )

            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        DecisionBlueLight,
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        1.dp,
                        Color.Transparent,
                        RoundedCornerShape(12.dp)
                    )
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(DecisionBlueLight)
                    .clickable(){
                        onSettingsHistoryClicked()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.settings_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(26.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        NewDecisionButton(
            onClicked = {
                onClicked()
            }
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        if (decisionHistory.isNotEmpty()) {
            RecentDecisionsSection(
                decisions = decisionHistory.first().decisions,
                onViewAllClicked = {
                    onViewHistoryRequested()
                },
                onShowOldDecisionClicked = { oldDecision ->
                    onShowOldDecisionClicked(oldDecision)
                }
            )
        }

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        LearnMoreAboutSection(
            title = article.title.ifBlank { "Loading A New Topic" },
            shortText = article.summary,
            onReadArticleClicked = {
                readArticleBoxExpanded = true
            }
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        YourDecisionJourneySection(
            averageConfidence = averageConfidence,
            amountOfDecisions = amountOfDecisions,
            averageOptionsPerDecision = averageOptionsPerDecision
        )
        
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
    }

    if (showUsernameEntryBox) {
        EnterUsernameBox(
            onDismissRequest = {
                showUsernameEntryBox = false
            },
            onAdd = { newUsername ->
                showUsernameEntryBox = false
                homeScreenViewModel.setUsername(newUsername)

                if (Build.VERSION.SDK_INT >= 33) {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        )
    }

    if (readArticleBoxExpanded) {
        ReadArticleBox(
            title = article.title,
            text = article.content,
            modifier = modifier,
            onCloseArticleClicked = {
                readArticleBoxExpanded = false
            }
        )

        return
    }

    if (showMotivationalQuoteBox) {
        val motivationalQuote = homeScreenViewModel.motivationalQuote()

        MotivationalQuoteBox(
            quote = motivationalQuote.first,
            person = motivationalQuote.second,
            onDismissRequest = {
                showMotivationalQuoteBox = false
            }
        )
    }
}