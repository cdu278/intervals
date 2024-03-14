package midget17468.memo.decompose.component

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
import midget17468.memo.decompose.navigation.config.NewMemoFlowStateConfig
import midget17468.memo.decompose.navigation.config.NewMemoFlowStateConfig.AddButton
import midget17468.memo.decompose.navigation.config.NewMemoFlowStateConfig.Editor
import midget17468.memo.model.ui.UiNewMemoFlowState
import midget17468.memo.repetitions.notifications.RepetitionsNotifications
import midget17468.memo.repository.MemoRepository
import midget17468.memo.model.domain.MemoType
import midget17468.memo.model.domain.NewMemoValidationErrors
import midget17468.memo.repetitions.SpacedRepetitions

class NewMemoFlowComponent(
    context: ComponentContext,
    private val errors: NewMemoValidationErrors,
    private val repetitionNotifications: RepetitionsNotifications,
    private val repository: MemoRepository,
    private val hashAlgorithm: HashAlgorithm,
    private val spacedRepetitions: SpacedRepetitions = SpacedRepetitions(),
) : ComponentContext by context {

    private val stateNavigation = StackNavigation<NewMemoFlowStateConfig>()

    private val stateStackValue: Value<ChildStack<NewMemoFlowStateConfig, *>> =
        childStack(
            source = stateNavigation,
            initialStack = { listOf(AddButton) },
            handleBackButton = true,
        ) { config, componentContext ->
            when (config) {
                is AddButton ->
                    NewMemoButtonComponent(
                        openEditor = { stateNavigation.push(Editor(MemoType.Password)) }
                    )

                is Editor ->
                    NewMemoEditorComponent(
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

    val stateFlow: StateFlow<UiNewMemoFlowState> =
        stateStackValue
            .map { stack ->
                val child = stack.active
                when (child.configuration) {
                    is AddButton ->
                        UiNewMemoFlowState.AddButton(child.instance as NewMemoButtonComponent)

                    is Editor ->
                        UiNewMemoFlowState.Editor(child.instance as NewMemoEditorComponent<*>)
                }
            }
            .asStateFlow(lifecycle)
}
