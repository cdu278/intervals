package cdu278.repetition.root.main.ui.composasble

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cdu278.intervals.main.ui.R
import cdu278.repetition.deletion.dialog.ui.composable.RepetitionsDeletionDialog
import cdu278.repetition.list.ui.composable.RepetitionList
import cdu278.repetition.new.flow.ui.UiNewRepetitionFlowState.AddButton
import cdu278.repetition.new.flow.ui.UiNewRepetitionFlowState.Editor
import cdu278.repetition.new.flow.ui.UiNewRepetitionFlowState.TypeSelection
import cdu278.repetition.new.flow.ui.composable.NewRepetitionEditor
import cdu278.repetition.new.flow.ui.composable.NewRepetitionFab
import cdu278.repetition.new.flow.ui.composable.NewRepetitionTypeSelection
import cdu278.repetition.root.main.ui.MainTabConfig
import cdu278.repetition.root.main.ui.MainTabConfig.Active
import cdu278.repetition.root.main.ui.MainTabConfig.Actual
import cdu278.repetition.root.main.ui.MainTabConfig.Archive
import cdu278.repetition.root.main.ui.UiMainDialog.Deletion
import cdu278.repetition.root.main.ui.component.MainComponent
import cdu278.ui.composable.defaultMargin
import cdu278.foundation.android.R as FoundationR
import cdu278.intervals.core.ui.R as CoreR
import cdu278.repetition.root.main.ui.UiMainMode.Default as ModeDefault
import cdu278.repetition.root.main.ui.UiMainMode.Selection as ModeSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    val model by component.uiModelFlow.collectAsState()
    Box(modifier = modifier) {
        Scaffold(
            topBar = topBar@{
                val selection = model.mode as? ModeSelection ?: return@topBar
                TopAppBar(
                    title = {
                        Text(
                            stringResource(
                                R.string.main_selection_selectedFmt,
                                selection.selectedCount
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = selection.quitSelectionModel) {
                            Icon(
                                painterResource(FoundationR.drawable.ic_back),
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = selection.delete) {
                            Icon(
                                painterResource(FoundationR.drawable.ic_delete),
                                contentDescription = null
                            )
                        }
                    },
                )
            },
            bottomBar = {
                NavigationBar {
                    model.tabs.forEach {
                        NavigationBarItem(
                            selected = it.active,
                            onClick = it.activate,
                            icon = {
                                Icon(
                                    painter = it.config.iconPainter,
                                    contentDescription = null,
                                )
                            },
                            label = { Text(it.config.title) },
                            enabled = model.mode is ModeDefault,
                        )
                    }
                }
            },
            floatingActionButton = {
                if (model.mode is ModeDefault) {
                    val newRepetitionModel
                            by component.newRepetitionFlowComponent.stateFlow.collectAsState()
                    when (val m = newRepetitionModel) {
                        is AddButton -> NewRepetitionFab(m.component)
                        else -> {}
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            modifier = modifier
        ) { paddings ->
            Box(
                modifier = Modifier
                    .padding(paddings)
                    .consumeWindowInsets(paddings)
            ) {
                val activeTab = remember(model.tabs) {
                    model.tabs.find { it.active }!!
                }
                RepetitionList(
                    activeTab.listComponent!!,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )

                val dialog =
                    (model.mode as? ModeSelection)
                        ?.dialog
                        ?: return@Scaffold
                when (dialog) {
                    is Deletion -> RepetitionsDeletionDialog(dialog.component)
                }
            }
        }
        if (model.mode is ModeDefault) {
            val newRepetitionFlowState
                    by component.newRepetitionFlowComponent.stateFlow.collectAsState()
            when (val m = newRepetitionFlowState) {
                is TypeSelection ->
                    NewRepetitionTypeSelection(
                        m.component,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                is Editor ->
                    NewRepetitionEditor(
                        m.component,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                else -> {}
            }
        }
    }
}

private val MainTabConfig.iconPainter: Painter
    @Composable
    get() = painterResource(
        id = when (this) {
            Actual -> CoreR.drawable.ic_test
            Active -> FoundationR.drawable.ic_intervals
            Archive -> R.drawable.ic_archive
        }
    )

private val MainTabConfig.title: String
    @Composable
    get() = stringResource(
        id = when (this) {
            Actual -> R.string.main_tab_actual
            Active -> R.string.main_tab_active
            Archive -> R.string.main_tab_archive
        }
    )