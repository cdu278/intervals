package cdu278.repetition.new.flow.ui.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import kotlinx.coroutines.flow.StateFlow
import cdu278.decompose.util.asStateFlow
import cdu278.hash.s.Hashes
import cdu278.repetition.RepetitionType.Password
import cdu278.repetition.new.editor.ui.component.NewRepetitionEditorComponent
import cdu278.repetition.new.flow.ui.NewRepetitionFlowStateConfig
import cdu278.repetition.new.flow.ui.NewRepetitionFlowStateConfig.AddButton
import cdu278.repetition.new.flow.ui.NewRepetitionFlowStateConfig.Editor
import cdu278.repetition.new.flow.ui.UiNewRepetitionFlowState
import cdu278.repetition.notification.s.RepetitionsNotifications
import cdu278.repetition.s.repository.RepetitionsRepository
import cdu278.repetition.spaced.SpacedRepetitions

class NewRepetitionFlowComponent(
    context: ComponentContext,
    private val repetitionNotifications: RepetitionsNotifications,
    private val repository: RepetitionsRepository,
    private val hashes: Hashes,
    private val spacedRepetitions: SpacedRepetitions,
) : ComponentContext by context {

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
                        openEditor = { stateNavigation.push(Editor(type = Password)) }
                    )
                is Editor ->
                    NewRepetitionEditorComponent(
                        componentContext,
                        config.type,
                        spacedRepetitions,
                        repetitionNotifications,
                        repository,
                        hashes,
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

                    is Editor ->
                        UiNewRepetitionFlowState.Editor(
                            child.instance as NewRepetitionEditorComponent
                        )
                }
            }
            .asStateFlow(lifecycle)
}
