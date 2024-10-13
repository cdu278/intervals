package cdu278.repetition.item.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cdu278.repetition.info.ui.composable.RepetitionInfo
import cdu278.repetition.state.ui.composable.RepetitionState
import cdu278.ui.composable.halfMargin
import cdu278.repetition.list.ui.RepetitionListUi.State.NonEmpty.Mode.Default.Item as DefaultRepetitionItem

@Composable
internal fun DefaultRepetitionItem(
    item: DefaultRepetitionItem,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    RepetitionItemCard(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        onLongClick = item.select,
        modifier = modifier
    ) {
        if (item.progressRatio != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                InfoAndState(
                    item,
                    accentColor,
                )
                Spacer(Modifier.height(halfMargin))
                RepetitionProgress(
                    item.progressRatio,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(RepetitionProgressHeight)
                )
            }
        } else {
            InfoAndState(
                item,
                accentColor,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun InfoAndState(
    item: DefaultRepetitionItem,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        RepetitionInfo(
            item.info,
            accentColor = accentColor,
            modifier = Modifier
                .weight(1f)
                .padding(end = halfMargin)
        )
        RepetitionState(
            item.state,
            titleColor = accentColor,
            modifier = Modifier
                .width(125.dp)
        )
    }
}