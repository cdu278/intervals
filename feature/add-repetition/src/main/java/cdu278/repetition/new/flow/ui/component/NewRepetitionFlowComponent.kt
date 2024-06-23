package cdu278.repetition.new.flow.ui.component

import cdu278.decompose.util.asStateFlow
import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.intervals.ui.component.context.newContext
import cdu278.repetition.new.editor.ui.component.NewRepetitionEditorComponent
import cdu278.repetition.new.flow.type.selection.ui.component.NewRepetitionTypeSelectionComponent
import cdu278.repetition.new.flow.ui.NewRepetitionFlowStateConfig
import cdu278.repetition.new.flow.ui.NewRepetitionFlowStateConfig.AddButton
import cdu278.repetition.new.flow.ui.NewRepetitionFlowStateConfig.Editor
import cdu278.repetition.new.flow.ui.NewRepetitionFlowStateConfig.TypeSelection
import cdu278.repetition.new.flow.ui.UiNewRepetitionFlowState
import cdu278.repetition.s.repository.RepetitionsRepository
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import kotlinx.coroutines.flow.StateFlow

class NewRepetitionFlowComponent(
    context: IntervalsComponentContext,
    private val repository: RepetitionsRepository,
) : IntervalsComponentContext by context {

    private val stateNavigation = StackNavigation<NewRepetitionFlowStateConfig>()

    private val stateStackValue: Value<ChildStack<NewRepetitionFlowStateConfig, *>> =
        childStack(
            source = stateNavigation,
            initialStack = { listOf(AddButton) },
            handleBackButton = true,
        ) { config, componentContext ->
            when (config) {
                is AddButton ->
                    NewRepetitionButtonComponent(
                        openEditor = { stateNavigation.push(TypeSelection) },
                    )
                is TypeSelection ->
                    NewRepetitionTypeSelectionComponent(
                        componentContext,
                        onSelected = { type ->
                            stateNavigation.push(configuration = Editor(type))
                        },
                        close = { stateNavigation.pop() },
                    )
                is Editor ->
                    NewRepetitionEditorComponent(
                        newContext(componentContext),
                        config.type,
                        repository,
                        close = {
                            stateNavigation.popWhile { it !is AddButton }
                        },
                    )
            }
        }

    val stateFlow: StateFlow<UiNewRepetitionFlowState> =
        stateStackValue
            .map { stack ->
                val child = stack.active
                when (child.configuration) {
                    is AddButton ->
                        UiNewRepetitionFlowState.AddButton(
                            child.instance as NewRepetitionButtonComponent
                        )
                    is TypeSelection ->
                        UiNewRepetitionFlowState.TypeSelection(
                            child.instance as NewRepetitionTypeSelectionComponent
                        )
                    is Editor ->
                        UiNewRepetitionFlowState.Editor(
                            child.instance as NewRepetitionEditorComponent
                        )
                }
            }
            .asStateFlow(lifecycle)
}
