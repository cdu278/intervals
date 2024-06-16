package midget17468.repetition.root.ui.component

import cdu278.intervals.ui.component.context.IntervalsComponentContext
import cdu278.intervals.ui.component.context.newContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.operator.map
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import midget17468.decompose.context.coroutineScope
import midget17468.decompose.util.asStateFlow
import midget17468.flow.uiModelSharingStarted
import midget17468.repetition.matching.RepetitionDataMatching
import midget17468.repetition.new.error.NewRepetitionValidationErrors
import midget17468.repetition.new.error.owner.EmptyPasswordErrorOwner
import midget17468.repetition.root.main.ui.component.MainComponent
import midget17468.repetition.root.ui.ScreenConfig
import midget17468.repetition.root.ui.ScreenConfig.Main
import midget17468.repetition.root.ui.ScreenConfig.Repetition
import midget17468.repetition.root.ui.UiRootScreen
import midget17468.repetition.ui.component.RepetitionComponent

class RootComponent(
    context: IntervalsComponentContext,
    private val errors: NewRepetitionValidationErrors,
    initialStack: () -> List<ScreenConfig> = { listOf(Main) },
) : IntervalsComponentContext by context {

    private val navigation = StackNavigation<ScreenConfig>()

    private val childStack =
        childStack(
            source = navigation,
            initialStack,
            handleBackButton = true,
        ) { config, componentContext ->
            when (config) {
                is Main ->
                    MainComponent(
                        newContext(componentContext),
                        errors,
                        repeat = { navigation.push(Repetition(id = it)) },
                    )
                is Repetition ->
                    RepetitionComponent(
                        newContext(componentContext),
                        repetitionId = config.id,
                        errors = errors,
                        dataMatching = RepetitionDataMatching(hashes),
                        close = { navigation.pop() },
                    )
            }
        }.map { entry ->
            entry.active
        }.asStateFlow(lifecycle)

    val screenFlow: StateFlow<UiRootScreen?> =
        childStack.map { entry ->
            @Suppress("UNCHECKED_CAST")
            when (entry.configuration) {
                is Main -> UiRootScreen.Main(entry.instance as MainComponent)
                is Repetition ->
                    UiRootScreen.Repetition(
                        entry.instance as RepetitionComponent<EmptyPasswordErrorOwner>
                    )
            }
        }.stateIn(coroutineScope(), uiModelSharingStarted, initialValue = null)
}