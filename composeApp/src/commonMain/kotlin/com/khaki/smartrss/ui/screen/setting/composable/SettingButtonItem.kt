package com.khaki.smartrss.ui.screen.setting.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ButtonType {
    NORMAL,
    DESTRUCTIVE
}

@Composable
fun SettingButtonItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonType: ButtonType = ButtonType.NORMAL,
) {
    val textColor = when (buttonType) {
        ButtonType.NORMAL -> Color.Unspecified
        ButtonType.DESTRUCTIVE -> MaterialTheme.colorScheme.error
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewSettingButtonItemDestructive() {
    MaterialTheme {
        SettingButtonItem(
            title = "Title",
            onClick = {},
            buttonType = ButtonType.DESTRUCTIVE
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewSettingButtonItem() {
    MaterialTheme {
        SettingButtonItem(
            title = "Title",
            onClick = {}
        )
    }
}
