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
fun TermsOfUseDialog(
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
                text = "Terms Of Use",
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

            TermSection(
                title = "1. Introduction and Acceptance",
                text =
                    """
                    Welcome to DecisionEngine. These Terms of Use (“Terms”) govern your access to and use of the DecisionEngine application, website, and related services (collectively, the “Service”).
                    
                    By accessing or using the Service, you agree to be bound by these Terms. If you do not agree with these Terms, you must not use the Service.
                
                    DecisionEngine provides tools and features designed to help users structure, analyze, compare, and evaluate decisions. The Service is intended to support users in their decision-making process and does not replace personal judgment or professional advice.
                
                    By using DecisionEngine, you acknowledge that you remain solely responsible for the decisions you make and for any actions you take based on information, analyses, recommendations, or other content provided through the Service.
                
                    We may update these Terms from time to time. Continued use of the Service after updated Terms become effective constitutes your acceptance of the revised Terms.
                    """.trimIndent()
            )

            TermSection(
                title = "2. Description of the Service",
                text =
                    """
                    DecisionEngine is a decision-support platform designed to help users make more informed decisions.

                    The Service may provide tools and features that allow users to define decisions, identify and compare options, evaluate relevant criteria, assign priorities or weights, analyze potential outcomes, and receive structured insights or recommendations.

                    Certain features of the Service may use artificial intelligence, algorithms, automated systems, or other technologies to process information and generate analyses, suggestions, comparisons, or other decision-support content.
                    
                    The features and functionality of DecisionEngine may evolve over time. We may add, modify, suspend, or remove features or functionality at any time.
                    
                    DecisionEngine is intended to assist users in organizing and evaluating information. The Service does not make decisions on behalf of users and does not guarantee that any analysis, recommendation, suggestion, or outcome provided through the Service is complete, accurate, suitable, or optimal for a user's individual circumstances.
                    
                    The user remains solely responsible for evaluating the information provided by the Service and for making their own final decisions.
                    """.trimIndent()
            )

            TermSection(
                title = "3. Eligibility and Access Credentials",
                text =
                    """
                    To use DecisionEngine, you must be legally permitted to enter into these Terms under the laws applicable to you. If you are under the age required to independently enter into a legally binding agreement in your jurisdiction, you may only use the Service with the consent of a parent or legal guardian, where required by applicable law.
                    
                    DecisionEngine does not require the creation of a traditional user account. Instead, access to certain features of the Service may be associated with technical access credentials, such as an API key or recovery code.
                    
                    You are responsible for keeping your access credentials secure and confidential. You must not share, sell, transfer, or provide your credentials to unauthorized third parties.
                    
                    Your recovery code may be required to restore access to your data or continue using certain features of the Service. You are responsible for storing this code securely. If you lose your recovery code, we may be unable to restore access to your data.
                    
                    You are responsible for all activity performed through access credentials associated with your use of the Service. If you believe that your credentials have been compromised or used without authorization, you should take appropriate steps to secure your access and contact us without undue delay.
                    
                    We reserve the right to restrict, suspend, or terminate access to the Service where necessary to protect the security, integrity, or lawful operation of DecisionEngine.
                    """.trimIndent()
            )

            TermSection(
                title = "4. User Responsibilities",
                text =
                    """
                    When using DecisionEngine, you are responsible for the information you provide to the Service and for how you use the analyses, recommendations, insights, and other content generated by or through the Service.

                    You agree to provide information that is accurate to the best of your knowledge and to ensure that you have the necessary rights or permissions to submit any information, content, or data to DecisionEngine.
                    
                    You are responsible for reviewing and evaluating the results provided by the Service before relying on them. DecisionEngine is intended to support your decision-making process and should not be treated as a substitute for your own judgment.
                    
                    You are solely responsible for any decisions you make and for any actions you take or do not take based on information or content provided through the Service. You acknowledge that the suitability of any decision may depend on personal circumstances, preferences, information, and factors that may not be known to or considered by DecisionEngine.
                    
                    You must use the Service in accordance with these Terms and all applicable laws and regulations. You must not use DecisionEngine in a manner that could harm the Service, other users, or third parties.
                    
                    You are responsible for keeping your access credentials and recovery code secure in accordance with Section 3.
                    """.trimIndent()
            )

            TermSection(
                title = "5. AI-Generated Content and Decision Support",
                text =
                    """
                    DecisionEngine may use artificial intelligence, algorithms, automated systems, and other technologies to generate analyses, insights, comparisons, suggestions, recommendations, or other decision-support content (“Generated Content”).
                    
                    Generated Content is provided for informational and decision-support purposes only. It is generated based on available information, user inputs, underlying models, algorithms, and other data sources and may not reflect all relevant facts, circumstances, or possible outcomes.
                    
                    Artificial intelligence and automated systems may produce inaccurate, incomplete, outdated, biased, or otherwise unsuitable results. DecisionEngine does not guarantee that Generated Content is accurate, complete, reliable, or appropriate for your individual circumstances.
                    
                    You should independently review and evaluate Generated Content before relying on it. Where a decision may have significant personal, financial, legal, medical, educational, professional, or other consequences, you should consider obtaining additional information or advice from a qualified professional where appropriate.
                    
                    DecisionEngine is designed to support, not replace, human judgment. The Service does not make decisions on your behalf, and you remain solely responsible for all decisions you make and for any actions you take or do not take based on Generated Content or other information provided through the Service.
                    
                    You must not treat Generated Content as a guarantee of a particular result or outcome. Past analyses, recommendations, or assessments generated through the Service do not guarantee future results.
                    
                    We may modify, improve, replace, or discontinue the artificial intelligence models, algorithms, or other technologies used by DecisionEngine at any time. As a result, Generated Content may vary over time, including when similar or identical information is submitted to the Service.
                    """.trimIndent()
            )

            TermSection(
                title = "6. No Professional Advice",
                text =
                    """
                    DecisionEngine does not provide professional advice. Nothing provided through the Service, including analyses, recommendations, insights, suggestions, Generated Content, or other information, should be considered legal, financial, medical, psychological, educational, professional, or other regulated advice.

                    The Service is intended solely to support users in structuring and evaluating their own decisions. Any information or content provided through DecisionEngine is general in nature and may not take into account your individual circumstances, objectives, needs, risks, or applicable legal requirements.
                    
                    You should not rely solely on the Service when making decisions that may have significant legal, financial, medical, educational, professional, or other consequences. Where appropriate, you should seek advice from a qualified professional before making such decisions.
                    
                    DecisionEngine does not establish any professional-client, advisor-client, fiduciary, or similar relationship between you and DecisionEngine or its operators.
                    
                    Any decisions you make based on information provided through the Service remain your sole responsibility.
                    """.trimIndent()
            )

            TermSection(
                title = "7. User Content and Data",
                text =
                    """
                    You may provide, submit, or otherwise make available information, preferences, criteria, options, descriptions, and other content or data through DecisionEngine (“User Content”).
                    
                    You retain ownership of your User Content. We do not claim ownership of the information or content you provide to the Service.
                    
                    By using DecisionEngine, you grant us a limited, non-exclusive, worldwide, royalty-free right to process your User Content solely to the extent necessary to provide, maintain, secure, and improve the Service, in accordance with applicable law and our Privacy Policy.
                    
                    You are responsible for ensuring that you have the necessary rights, permissions, and legal basis to provide any User Content to DecisionEngine. You must not submit content that violates applicable law or infringes the rights of third parties.
                    
                    You are responsible for maintaining backups of any information that is important to you, unless DecisionEngine explicitly provides a backup or recovery feature for that information.
                    
                    We may process, store, and delete User Content as necessary to operate the Service and in accordance with our Privacy Policy and applicable legal requirements.
                    
                    If you choose to delete your data through the functionality provided within the Service, the relevant data may be permanently removed and may no longer be recoverable. Some data may be retained for a limited period where required for technical, security, backup, fraud prevention, or legal purposes.
                    
                    For further information about how we collect, use, store, and delete personal data, please refer to our Privacy Policy.
                    """.trimIndent()
            )

            TermSection(
                title = "8. Acceptable Use",
                text =
                    """
                    You may use DecisionEngine only for lawful purposes and in accordance with these Terms.
                    
                    You must not use the Service in any manner that could damage, disable, overburden, interfere with, or compromise the security, integrity, or normal operation of DecisionEngine or its underlying systems.
                    
                    In particular, you must not:
                    
                    -use the Service in violation of applicable laws or regulations;
                    -use the Service to infringe, misappropriate, or otherwise violate the rights of others;
                    -submit malicious code, malware, viruses, or other harmful material;
                    -attempt to gain unauthorized access to the Service, its systems, servers, databases, API infrastructure, or other users' data;
                    -interfere with, disrupt, or circumvent security measures, access controls, rate limits, or other technical restrictions;
                    -reverse engineer, decompile, disassemble, or otherwise attempt to derive the source code or underlying technology of the Service, except to the extent permitted by applicable law;
                    -exploit, abuse, share, sell, transfer, or misuse access credentials or recovery codes;
                    -use automated systems, scripts, bots, or other methods to access or interact with the Service in a manner that places an unreasonable burden on the Service or bypasses intended usage restrictions;
                    -use the Service for fraudulent, deceptive, harmful, or abusive purposes;
                    -submit information that you do not have the right or permission to provide;
                    -use DecisionEngine in a manner that could harm other users, third parties, or the Service itself.
                    
                    We may investigate suspected violations of this Section and may take appropriate action, including restricting, suspending, or terminating access to the Service where reasonably necessary.
                    """.trimIndent()
            )

            TermSection(
                title = "9. Availability and Changes to the Service",
                text =
                    """
                    We aim to provide DecisionEngine in a reliable and continuous manner. However, we do not guarantee that the Service will be available at all times or without interruption.

                    The Service may be temporarily unavailable due to maintenance, updates, technical issues, security measures, infrastructure failures, or circumstances beyond our reasonable control.
                    
                    We may modify, update, improve, replace, suspend, or discontinue any part of the Service or its features at any time. This may include changes to the functionality, availability, user interface, underlying technologies, artificial intelligence models, algorithms, or data processing methods used by DecisionEngine.
                    
                    Where reasonably possible, we may provide advance notice of significant changes that materially affect the availability or functionality of the Service. However, immediate changes may be necessary for security, legal, technical, or operational reasons.
                    
                    We do not guarantee that any specific feature, functionality, analysis, recommendation, or technology will remain available permanently.
                    
                    We may also perform maintenance, updates, or other work that temporarily restricts or interrupts access to the Service. We will use reasonable efforts to minimize significant disruptions where practical.
                    
                    To the extent permitted by applicable law, we are not liable for temporary unavailability, interruptions, delays, modifications, or discontinuation of the Service.
                    """.trimIndent()
            )

            TermSection(
                title = "10. Intellectual Property",
                text =
                    """
                    Unless otherwise stated, DecisionEngine and all materials, features, functionality, software, designs, interfaces, logos, trademarks, text, graphics, and other content provided through the Service are owned by or licensed to us and are protected by applicable intellectual property laws.

                    Subject to your compliance with these Terms, we grant you a limited, personal, non-exclusive, non-transferable, non-sublicensable, and revocable right to access and use DecisionEngine for its intended purpose.
                    
                    You may not copy, reproduce, modify, distribute, sell, lease, license, publicly display, publicly perform, or otherwise exploit any part of the Service except as expressly permitted by these Terms or applicable law.
                    
                    Nothing in these Terms transfers ownership of any intellectual property rights in DecisionEngine or the Service to you.
                    
                    The name “DecisionEngine”, its logo, and any associated branding may not be used without our prior written permission, except where such use is permitted by applicable law.
                    
                    You retain ownership of your User Content as described in Section 7. Nothing in this Section affects your rights in the content or data you provide to the Service.
                    
                    Any feedback, suggestions, ideas, or recommendations you voluntarily provide regarding DecisionEngine may be used by us to improve the Service without obligation or compensation to you.
                    """.trimIndent()
            )

            TermSection(
                title = "11. Disclaimer of Warranties",
                text =
                    """
                    To the maximum extent permitted by applicable law, DecisionEngine is provided on an “as is” and “as available” basis.

                    We do not guarantee that the Service will be uninterrupted, error-free, secure, accurate, complete, reliable, or available at all times. We do not warrant that the Service will meet your individual requirements, expectations, or intended outcomes.
                    
                    Any analyses, recommendations, suggestions, insights, Generated Content, or other information provided through DecisionEngine may contain errors, inaccuracies, omissions, outdated information, or results that are unsuitable for your particular circumstances.
                    
                    We make no warranty or representation regarding the accuracy, completeness, reliability, suitability, or availability of any content or information provided through the Service.
                    
                    To the extent permitted by applicable law, we disclaim all warranties, whether express, implied, statutory, or otherwise, including any implied warranties of merchantability, fitness for a particular purpose, non-infringement, and satisfactory quality.
                    
                    Nothing in these Terms excludes or limits any warranty, right, or protection that cannot lawfully be excluded or limited under applicable law.
                    """.trimIndent()
            )

            TermSection(
                title = "12. Limitation of Liability",
                text =
                    """
                    Nothing in these Terms excludes or limits liability where such liability cannot be excluded or limited under applicable law.

                    To the maximum extent permitted by applicable law, DecisionEngine and its operators shall not be liable for any indirect, incidental, special, consequential, or punitive damages, or for any loss of profits, revenue, business, opportunities, goodwill, data, or other intangible losses arising out of or in connection with your use of, or inability to use, the Service.
                    
                    DecisionEngine is a decision-support tool. We are not liable for decisions you make, actions you take or do not take, or outcomes that result from your reliance on analyses, recommendations, suggestions, Generated Content, or other information provided through the Service.
                    
                    You remain solely responsible for evaluating the information provided by the Service and for making your own decisions.
                    
                    To the maximum extent permitted by applicable law, our total liability arising out of or relating to the Service or these Terms shall be limited to the amount you have paid to us for the use of the Service during the twelve (12) months immediately preceding the event giving rise to the claim. If you have not paid any amount to use the Service, our total liability shall be limited to the minimum amount permitted by applicable law.
                    
                    Nothing in these Terms shall exclude or limit liability for intentional misconduct, gross negligence, death or personal injury caused by negligence where applicable law does not permit such limitation, or any other liability that cannot legally be excluded or limited.
                    """.trimIndent()
            )

            TermSection(
                title = "13. Third-Party Services",
                text =
                    """
                    DecisionEngine may rely on or integrate with third-party services, technologies, infrastructure providers, artificial intelligence providers, analytics services, or other external services in order to provide, operate, maintain, or improve the Service.

                    The availability and functionality of such third-party services are outside our direct control. We do not guarantee that any third-party service will remain available, uninterrupted, error-free, or unchanged.
                    
                    Where you access, use, or interact with third-party services through or in connection with DecisionEngine, your use of those services may also be subject to the terms, conditions, and privacy policies of the relevant third party.
                    
                    We are not responsible for the content, availability, security, functionality, or practices of third-party services, except where liability cannot be excluded or limited under applicable law.
                    
                    The use of third-party services may involve the processing or transfer of data where necessary to provide the requested functionality. Further information about the processing of personal data and the use of third-party service providers is provided in our Privacy Policy.
                    
                    We may add, replace, or remove third-party service providers where reasonably necessary to operate, maintain, secure, or improve DecisionEngine.
                    """.trimIndent()
            )

            TermSection(
                title = "14. Termination",
                text =
                    """
                    You may stop using DecisionEngine at any time.

                    DecisionEngine does not require a traditional user account and does not store your personal decision data on our servers. Accordingly, stopping your use of the Service does not require the deletion of a user account or server-stored decision data.
                    
                    We may restrict, suspend, or terminate access to all or part of the Service where reasonably necessary, including if:
                    
                    -you violate these Terms or applicable law;
                    -your use of the Service creates a security risk or threatens the integrity or availability of DecisionEngine;
                    -we reasonably suspect fraudulent, abusive, or unauthorized use;
                    -we are required to do so by law or a competent authority; or
                    -continued provision of the Service is no longer reasonably possible for technical, legal, security, or operational reasons.
                    
                    Where reasonably possible, we may provide notice before restricting or terminating access. However, we may take immediate action where necessary to protect the Service, users, third parties, or our legal obligations.
                    
                    Upon termination or restriction of access, your right to use the affected parts of the Service will cease. The anonymous technical access credentials used by the Service may remain technically valid or may become unusable, depending on the circumstances of the restriction or termination.
                    
                    Provisions of these Terms that by their nature should survive termination, including provisions relating to intellectual property, disclaimers, limitation of liability, and applicable law, will continue to apply after termination.
                    """.trimIndent()
            )

            TermSection(
                title = "15. Changes to These Terms",
                text =
                    """
                    We may update or modify these Terms from time to time where reasonably necessary, including to reflect changes to the Service, applicable law, security requirements, technical developments, or our business operations.

                    When we make material changes to these Terms, we will take reasonable steps to inform users, where appropriate. This may include providing notice through the Service or by other suitable means.
                    
                    The updated Terms will become effective on the date specified when they are published or otherwise made available, unless a different effective date is stated.
                    
                    By continuing to use DecisionEngine after the updated Terms become effective, you agree to be bound by the revised Terms.
                    
                    If you do not agree to the updated Terms, you must stop using the Service.
                    
                    Nothing in this Section affects any rights you may have under applicable law.
                    """.trimIndent()
            )

            TermSection(
                title = "16. Governing Law and Jurisdiction",
                text =
                    """
                    These Terms and any dispute or claim arising out of or in connection with these Terms or the use of DecisionEngine shall be governed by and construed in accordance with the laws of the Federal Republic of Germany, excluding its conflict of law rules.

                    If you are a consumer and have your habitual residence in a country other than Germany, you may also benefit from mandatory provisions of the law of your country of residence that cannot be derogated from by agreement.
                    
                    To the extent permitted by applicable law, the courts of Germany shall have jurisdiction over disputes arising out of or in connection with these Terms and the use of DecisionEngine.
                    
                    If you are a consumer, the statutory rules regarding jurisdiction shall apply.
                    """.trimIndent()
            )

            TermSection(
                title = "17. Contact Information",
                text =
                    """
                    If you have any questions, concerns, or requests regarding these Terms or your use of DecisionEngine, you may contact us using the contact information provided within the Service or through our designated support channels.

                    We may provide additional contact information, including a support email address, within the DecisionEngine application, on our website, or in other official communications.
                    
                    For questions relating specifically to the processing of personal data, please refer to our Privacy Policy and the contact information provided there.
                    
                    These Terms are intended to govern your use of DecisionEngine. If any provision of these Terms is found to be invalid, unlawful, or unenforceable, the remaining provisions shall remain in effect to the extent permitted by applicable law.
                    """.trimIndent()
            )

            TermSection(
                title = "18. Severability and Entire Agreement",
                text =
                    """
                    If any provision of these Terms is found to be invalid, unlawful, or unenforceable, that provision shall be enforced to the maximum extent permitted by applicable law, and the remaining provisions shall remain in full force and effect.

                    The failure of DecisionEngine to enforce any right or provision of these Terms shall not constitute a waiver of that right or provision.
                    
                    These Terms, together with the Privacy Policy and any additional terms or notices expressly incorporated by reference, constitute the entire agreement between you and DecisionEngine regarding your use of the Service and supersede any prior agreements, communications, or understandings relating to the same subject matter.
                    
                    Nothing in this Section limits or excludes any rights that cannot be waived under applicable law.
                    """.trimIndent()
            )

            TermSection(
                title = "19. Accessibility",
                text =
                    """
                    We aim to make DecisionEngine accessible to as many users as reasonably possible, including users with disabilities.

                    We are committed to considering accessibility in the design, development, and improvement of the Service and to reducing barriers that may prevent users from accessing or using its features.
                    
                    However, we do not guarantee that every part of the Service will be fully accessible to every user or compatible with every assistive technology, device, operating system, or individual accessibility requirement.
                    
                    Accessibility may depend on factors outside our control, including the user's device, operating system, screen reader, accessibility settings, internet connection, or third-party technologies used in connection with the Service.
                    
                    We may continue to improve the accessibility of DecisionEngine over time. If you experience an accessibility barrier or encounter difficulties using the Service, you may contact us through the support channels provided within the Service so that we can review the issue.
                    
                    Nothing in this Section limits any rights or protections available to users under applicable accessibility laws.
                    """.trimIndent()
            )

            TermSection(
                title = "20. Software License",
                text =
                    """
                    Subject to your compliance with these Terms, we grant you a limited, personal, non-exclusive, non-transferable, non-sublicensable, and revocable license to download, install, and use the DecisionEngine application on a compatible device for your personal and lawful use.

                    The application is licensed to you, not sold. You do not acquire any ownership rights in the application, its software, source code, design, functionality, or underlying technologies.
                    
                    You may not:
                    
                    -copy, modify, distribute, sell, lease, rent, sublicense, or otherwise transfer the application or any part of it, except as expressly permitted by applicable law;
                    -reverse engineer, decompile, disassemble, or attempt to derive the source code of the application, except to the extent that such restrictions are prohibited by applicable law;
                    -remove, alter, or obscure any proprietary notices, trademarks, or other intellectual property notices contained in the application;
                    -use the application for any unlawful or unauthorized purpose;
                    -circumvent, disable, or interfere with security features, technical limitations, or access controls.
                    
                    The license granted under this Section remains in effect until terminated by you or us in accordance with these Terms.
                    
                    Upon termination of the license, you must stop using the application and delete it from your devices.
                    
                    Updates, upgrades, or additional features made available for the application may be subject to these Terms or additional terms, where applicable.
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
private fun TermSection(
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