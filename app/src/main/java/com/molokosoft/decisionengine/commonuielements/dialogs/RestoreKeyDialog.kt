package com.molokosoft.decisionengine.commonuielements.dialogs

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
fun RestoreKeyDialog(
    restoreKey: String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    val typography = LocalAppTypography.current
    val verticalScroll = rememberScrollState()

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
                text = "Restore Key",
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

            SubSection(
                title = "Your Restore Key",
                text = restoreKey.ifBlank { "-" }
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            SubSection(
                title = "Important Information",
                text = """
                    Your Restore Key allows you to restore access to DecisionEngine if you change devices or reinstall the app.
                    
                    Please keep this key safe and private. Do not share it with anyone, as anyone with access to your Restore Key may be able to restore access to DecisionEngine.
                    
                    We value your privacy. Your Restore Key is intended to give you control over restoring your own access without requiring unnecessary personal information.
                    
                    Store your Restore Key in a secure place where you can find it again when needed.
                """.trimIndent()
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
private fun SubSection(
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