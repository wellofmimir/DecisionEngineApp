package com.molokosoft.decisionengine.settingsscreen.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.molokosoft.decisionengine.R
import com.molokosoft.decisionengine.theme.DecisionBlue
import com.molokosoft.decisionengine.theme.LocalAppTypography

@Composable
fun AboutDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    val typography =
        LocalAppTypography.current

    val verticalScroll =
        rememberScrollState()

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = modifier
                .verticalScroll(verticalScroll)
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .height(16.dp)
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
                text = "About DecisionEngine",
                textAlign = TextAlign.Center,
                fontSize = typography.titleMedium.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            AboutSection(
                title = "What is DecisionEngine?",
                text =
                    """
                    DecisionEngine is designed to help you make better and more informed decisions.
                    
                    Whether you are choosing a university, career, apartment, product or another important option, DecisionEngine helps you structure your thoughts and compare your alternatives.
                    """.trimIndent()
            )

            AboutSection(
                title = "How does it work?",
                text =
                    """
                    You define your decision, add the available options and identify the criteria that matter to you.
                    
                    DecisionEngine helps you evaluate and compare your options based on the priorities and information you provide.
                    
                    This allows you to better understand what matters most to you and see how your alternatives compare.
                    """.trimIndent()
            )

            AboutSection(
                title = "AI-powered analysis",
                text =
                    """
                    DecisionEngine can provide AI-powered insights to help you look at your decision from different perspectives.
                    
                    AI-generated analyses are intended to support your thinking, highlight considerations and provide additional perspectives.
                    
                    The final decision is always yours.
                    """.trimIndent()
            )

            AboutSection(
                title = "Built around your decisions",
                text =
                    """
                    DecisionEngine is built around the decisions that matter to you.
                    
                    Your decisions, options, criteria and other decision-related information are primarily stored locally on your device.
                    
                    You remain in control of the information you choose to provide.
                    """.trimIndent()
            )

            AboutSection(
                title = "Our mission",
                text =
                    """
                    We believe that better decisions start with clearer thinking.
                    
                    DecisionEngine was created to help people structure complex choices, understand their priorities and make decisions with greater confidence.
                    
                    Sometimes the right answer is not obvious. DecisionEngine is here to help you think through the possibilities.
                    """.trimIndent()
            )

            AboutSection(
                title = "Important notice",
                text =
                    """
                    DecisionEngine provides decision-support tools and AI-generated information.
                    
                    It does not provide professional legal, medical, financial or other professional advice.
                    
                    Important decisions should be made using your own judgment and, where appropriate, with the support of qualified professionals.
                    """.trimIndent()
            )

            AboutSection(
                title = "Contact",
                text =
                    """
                    If you have questions, feedback or suggestions, please contact us:
                    
                    mleczko.patryk.roman@gmail.com
                    """.trimIndent()
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Text(
                text = "Close",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = typography.titleSmall.fontSize,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        onDismissRequest()
                    }
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
        }
    }
}

@Composable
private fun AboutSection(
    title: String,
    text: String
) {
    val typography = LocalAppTypography.current

    Text(
        text = title,
        textAlign = TextAlign.Center,
        fontSize = typography.titleSmall.fontSize,
        color = Color.Black,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .padding(horizontal = 4.dp)
    )

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        color = DecisionBlue
    )

    Spacer(
        modifier = Modifier
            .height(6.dp)
    )

    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = typography.titleSmall.fontSize,
        color = Color.Black,
        fontWeight = FontWeight.Light,
        modifier = Modifier
            .padding(horizontal = 12.dp)
    )

    Spacer(
        modifier = Modifier
            .height(16.dp)
    )
}