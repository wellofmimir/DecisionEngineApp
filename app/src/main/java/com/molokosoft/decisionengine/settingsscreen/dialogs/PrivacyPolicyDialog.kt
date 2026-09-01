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
fun PrivacyPolicyDialog(
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
                text = "Privacy Policy",
                textAlign = TextAlign.Center,
                fontSize = typography.titleMedium.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            Text(
                text = "Last updated: 25.08.2026",
                textAlign = TextAlign.Center,
                fontSize = typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.Light
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            PrivacyPolicySection(
                title = "Who is responsible for your data?",
                text =
                    """
                    DecisionEngine is operated by:
                    
                    Patryk Roman Mleczko
                    Dr.-Robert-Koch-Str. 1
                    19260 Vellahn
                    Germany
                    
                    Email: mleczko.patryk.roman@gmail.com
                    
                    We are responsible for the processing of personal data in connection with the DecisionEngine application.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "Do I need to create an account?",
                text =
                    """
                    No. DecisionEngine does not require you to create an account.
                    
                    You do not need to provide an email address or password to use the application.
                    
                    DecisionEngine uses a pseudonymous technical identifier in the form of an API key. Our backend stores only a cryptographic hash of this identifier for authentication and service functionality.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "What data is stored on my device?",
                text =
                    """
                    DecisionEngine primarily stores your data locally on your device.
                    
                    This may include your decisions, options, criteria, priorities, additional inputs, AI-generated analyses, settings and other decision-related information.
                    
                    You may also optionally provide a name to personalize your experience. Your name is stored locally on your device and is not sent to our servers or to OpenAI.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "What data is stored on our servers?",
                text =
                    """
                    DecisionEngine stores only limited data on its backend.
                    
                    This currently includes a cryptographic hash of your API key and your Google Play purchase token.
                    
                    We do not maintain a server-side history of your decisions or AI analyses.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "How is AI used?",
                text =
                    """
                    When you request an AI-powered analysis, the information necessary to provide the requested analysis is transmitted from your device to our backend and then to OpenAI.
                    
                    This may include your decision question, options, criteria, priorities, weights, free-text inputs and other information you voluntarily provide.
                    
                    Our backend processes this information to provide the requested result but does not permanently store your decision inputs, AI prompts or AI-generated analyses.
                    
                    The generated analysis is returned to your device and may be stored locally by the application.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "Can I enter sensitive information?",
                text =
                    """
                    DecisionEngine does not require you to provide sensitive personal information.
                    
                    However, because you can enter free-form information, you may voluntarily include information relating to health, mental health, religion, political opinions, sexual orientation, financial matters, legal matters or other highly personal topics.
                    
                    If you choose to include such information in an AI analysis request, the information necessary to generate the requested analysis may be transmitted to OpenAI for processing.
                    
                    Please avoid submitting unnecessary sensitive or confidential information.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "Which third-party services are used?",
                text =
                    """
                    DecisionEngine currently uses the following service providers:
                    
                    Amazon Web Services (AWS)
                    Used to host our backend infrastructure on AWS EC2.
                    
                    OpenAI
                    Used to provide AI-powered decision analyses and related AI functionality.
                    
                    Google Play Billing
                    Used to process in-app purchases and provide purchase-related functionality.
                    
                    We do not use advertising SDKs, analytics SDKs, behavioral tracking or third-party crash reporting services.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "Is data processed internationally?",
                text =
                    """
                    DecisionEngine is available worldwide.
                    
                    Our backend infrastructure is hosted in the United States, and AI processing may involve processing outside the country in which you are located.
                    
                    Where required by applicable law, appropriate safeguards are used for international data transfers.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "How are purchases processed?",
                text =
                    """
                    In-app purchases are processed through Google Play Billing.
                    
                    DecisionEngine does not receive or store your payment card details or bank account information.
                    
                    We process your Google Play purchase token to verify purchases, provide purchased functionality, maintain relevant consumables or entitlements and prevent duplicate processing or abuse.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "Do you use tracking or advertising?",
                text =
                    """
                    No.
                    
                    DecisionEngine does not sell your personal data.
                    
                    We do not use your data for targeted advertising or behavioral advertising.
                    
                    We do not use advertising profiles, analytics SDKs or similar tracking technologies.
                    
                    We do not share your personal data with third parties for their independent marketing purposes.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "What server logs are collected?",
                text =
                    """
                    Our web server may process technical information generated when you communicate with our service.
                    
                    This may include your IP address, date and time of the request, requested endpoint, HTTP method, response status, data transfer information, user-agent information and other technical request metadata.
                    
                    Standard server logs are retained for a maximum of 7 days and are then automatically deleted, unless longer retention is necessary to investigate a specific security incident, technical issue or comply with a legal obligation.
                    
                    DecisionEngine does not intentionally include your decision content in standard server access logs.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "How are notifications used?",
                text =
                    """
                    DecisionEngine may request notification permission to provide you with a daily quote.
                    
                    These notifications are scheduled locally on your device.
                    
                    We do not use server-side push notifications, device push tokens or third-party push notification providers.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "How is my data protected?",
                text =
                    """
                    We use appropriate technical and organizational measures designed to protect your data.
                    
                    Communication between the DecisionEngine application and our backend is encrypted using HTTPS/TLS.
                    
                    Sensitive local application values may be protected using secure local storage.
                    
                    No method of internet transmission or electronic storage is completely secure, and absolute security cannot be guaranteed.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "How long is my data retained?",
                text =
                    """
                    Decision content and AI analyses are primarily stored locally on your device.
                    
                    DecisionEngine does not permanently store your decision history or AI analysis history on its backend.
                    
                    API key hashes and purchase-related information are retained for as long as reasonably necessary to provide relevant functionality, verify purchases, prevent abuse and comply with applicable legal obligations.
                    
                    Standard server logs are normally retained for a maximum of 7 days.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "Can I delete my data?",
                text =
                    """
                    Yes.
                    
                    DecisionEngine provides data deletion functionality through the application settings.
                    
                    You may delete locally stored application data and server-side data associated with your pseudonymous identifier.
                    
                    You may also choose to delete all available data.
                    
                    Deleted data may not be recoverable. Before deleting server-side data, please carefully consider the possible impact on purchased functionality and future recovery options.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "What are my privacy rights?",
                text =
                    """
                    Depending on applicable law, you may have rights relating to your personal data, including the right to request access, correction, deletion, restriction of processing, objection to certain processing and data portability where applicable.
                    
                    You may also withdraw consent for future processing where processing is based on consent.
                    
                    You may contact us using the contact information provided in this Privacy Policy to exercise applicable rights.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "Who can use DecisionEngine?",
                text =
                    """
                    DecisionEngine is intended for users aged 16 and older.
                    
                    We do not knowingly seek to collect personal data from children under the age of 16.
                    
                    If we become aware that personal data from a child has been processed in violation of applicable requirements, we may take appropriate action, including deletion where required.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "What are the legal bases for processing?",
                text =
                    """
                    Where the GDPR applies, we process personal data using an appropriate legal basis.
                    
                    This may include processing necessary to provide requested services, verify purchases and provide purchased functionality.
                    
                    We may also process limited technical information where necessary for security, fraud prevention and the operation of our services.
                    
                    Where required for special categories of personal data voluntarily provided by you, we will rely on an appropriate legal basis and, where applicable, obtain the necessary consent.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "How can I contact us?",
                text =
                    """
                    If you have questions about this Privacy Policy or wish to exercise your privacy rights, please contact us:
                    
                    mleczko.patryk.roman@gmail.com
                    
                    We will handle requests in accordance with applicable data protection law.
                    """.trimIndent()
            )

            PrivacyPolicySection(
                title = "Changes to this Privacy Policy",
                text =
                    """
                    We may update this Privacy Policy from time to time.
                    
                    The latest version will be available within the DecisionEngine application and through the applicable Privacy Policy link.
                    
                    The date of the latest update will be shown at the top of this Privacy Policy.
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
private fun PrivacyPolicySection(
    title: String,
    text: String
) {
    val typography =
        LocalAppTypography.current

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