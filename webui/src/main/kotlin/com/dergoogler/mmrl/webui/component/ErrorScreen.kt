package com.dergoogler.mmrl.webui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dergoogler.mmrl.ext.isNotNullOrEmpty
import com.dergoogler.mmrl.ext.nullable
import com.dergoogler.mmrl.ui.component.text.BBCodeText
import com.dergoogler.mmrl.webui.R

@Composable
fun ErrorScreen(
    title: String,
    description: String,
    @DrawableRes icon: Int? = null,
    suggestions: List<String> = emptyList(),
    errorCode: String,
    onRefresh: () -> Unit = {},
    @StringRes moreInfoText: Int = R.string.more_info,
    onMoreInfo: () -> Unit = {},
) {
    val colorScheme = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState()),
        color = colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 600.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                icon.nullable {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        tint = colorScheme.error,
                        modifier = Modifier.size(72.dp)
                    )
                }

                Text(
                    text = title,
                    color = colorScheme.onBackground,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                Surface(
                    color = colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(12.dp),
                    tonalElevation = 0.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BBCodeText(
                        text = description,
                        modifier = Modifier
                            .padding(16.dp),
                        color = colorScheme.onBackground
                    )
                }

                if (suggestions.isNotNullOrEmpty()) {
                    Surface(
                        color = colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(stringResource(R.string.please_try_the_following), color = colorScheme.onBackground)
                            Spacer(Modifier.height(8.dp))
                            Column {
                                suggestions.forEach {
                                    Text("• $it", color = colorScheme.onBackground)
                                }
                            }
                        }
                    }
                }

                Text(
                    text = errorCode,
                    color = colorScheme.outline,
                    fontSize = 13.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        onClick = onRefresh,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primary,
                            contentColor = colorScheme.onPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.refresh),
                            contentDescription = null,
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.refresh))
                    }

                    OutlinedButton(
                        onClick = onMoreInfo,
                        border = BorderStroke(1.dp, colorScheme.outline),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = colorScheme.primary
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.info_circle),
                            contentDescription = null,
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(moreInfoText))
                    }
                }
            }
        }
    }
}
