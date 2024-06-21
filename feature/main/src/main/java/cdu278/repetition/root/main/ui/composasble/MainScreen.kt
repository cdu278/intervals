package cdu278.repetition.root.main.ui.composasble

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cdu278.intervals.main.ui.R
import cdu278.repetition.deletion.dialog.ui.composable.RepetitionsDeletionDialog
import cdu278.repetition.list.ui.composable.RepetitionList
import cdu278.repetition.new.flow.ui.composable.NewRepetitionFlow
import cdu278.repetition.root.main.ui.UiMain
import cdu278.repetition.root.main.ui.UiMainDialog.Deletion
import cdu278.repetition.root.main.ui.component.MainComponent
import cdu278.ui.composable.defaultMargin
import cdu278.foundation.android.R as FoundationR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    val modelState = component.uiModelFlow.collectAsState()
    Scaffold(
        topBar = topBar@ {
            val model = modelState.value as? UiMain.Selection ?: return@topBar
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.main_selection_selectedFmt, model.selectedCount)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = model.quitSelectionModel) {
                        Icon(
                            painterResource(FoundationR.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = model.delete) {
                        Icon(
                            painterResource(FoundationR.drawable.ic_delete),
                            contentDescription = null
                        )
                    }
                },
            )
        },
        modifier = modifier
    ) { paddings ->
        Box(
            modifier = Modifier
                .padding(paddings)
                .consumeWindowInsets(paddings)
        ) {
            RepetitionList(
                component.repetitionListComponent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(defaultMargin)
                    .align(Alignment.TopCenter)
            )
            NewRepetitionFlow(
                component.newRepetitionFlowComponent,
                modifier = Modifier
                    .fillMaxSize()
            )

            val dialog =
                (modelState.value as? UiMain.Selection)
                    ?.dialog
                    ?: return@Scaffold
            when (dialog) {
                is Deletion -> RepetitionsDeletionDialog(dialog.component)
            }
        }
    }
}