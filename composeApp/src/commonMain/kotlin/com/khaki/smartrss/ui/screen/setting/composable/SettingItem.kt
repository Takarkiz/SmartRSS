package com.khaki.smartrss.ui.screen.setting.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SettingSwitchItem(
    title: String,
    isChecked: Boolean,
    onChangeSwitchValue: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Switch(
            checked = isChecked,
            onCheckedChange = onChangeSwitchValue,
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewSettingSwitchItem_sub() {
    MaterialTheme {
        SettingSwitchItem(
            title = "テスト",
            isChecked = true,
            subtitle = "これはサブタイトルです",
            onChangeSwitchValue = {}
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewSettingSwitchItem() {
    MaterialTheme {
        SettingSwitchItem(
            title = "テスト",
            isChecked = false,
            onChangeSwitchValue = {}
        )
    }
}
