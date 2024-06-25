package cdu278.repetition.item.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
internal fun RepetitionItemColumn(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
    titleColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
) {
   Column(modifier = modifier) {
       Text(
           text = title,
           color = titleColor,
           fontSize = 14.sp,
       )
       Text(
           text = content,
           fontSize = 12.sp,
       )
   }
}