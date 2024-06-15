package midget17468.repetition.new.flow.ui.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import kotlinx.coroutines.flow.StateFlow
import midget17468.decompose.util.asStateFlow
import midget17468.hash.algorithm.HashAlgorithm
import midget17468.repetition.spaced.SpacedRepetitions
import midget17468.repetition.new.editor.ui.component.NewRepetitionEditorComponent
import midget17468.repetition.new.error.NewRepetitionValidationErrors
import midget17468.repetition.new.flow.ui.NewRepetitionFlowStateConfig
import midget17468.repetition.new.flow.ui.NewRepetitionFlowStateConfig.AddButton
import midget17468.repetition.new.flow.ui.NewRepetitionFlowStateConfig.Editor
import midget17468.repetition.RepetitionType.Password
import midget17468.repetition.new.flow.ui.UiNewRepetitionFlowState
import midget17468.repetition.notification.s.RepetitionsNotifications
import midget17468.repetition.s.repository.RepetitionsRepository

class NewRepetitionFlowComponent(
    context: ComponentContext,
    private val errors: NewRepetitionValidationErrors,
    private val repetitionNotifications: RepetitionsNotifications,
    private val repository: RepetitionsRepository,
    private val hashAlgorithm: HashAlgorithm,
    private val spacedRepetitions: SpacedRepetitions = SpacedRepetitions(),
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
                        errors,
                        spacedRepetitions,
                        repetitionNotifications,
                        repository,
                        hashAlgorithm,
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
                            child.instance as NewRepetitionEditorComponent<*>
                        )
                }
            }
            .asStateFlow(lifecycle)
}
