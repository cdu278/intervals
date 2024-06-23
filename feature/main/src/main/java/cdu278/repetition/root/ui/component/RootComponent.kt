package cdu278.repetition.root.ui.component

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
import cdu278.decompose.context.coroutineScope
import cdu278.decompose.util.asStateFlow
import cdu278.flow.uiModelSharingStarted
import cdu278.repetition.matching.RepetitionDataMatching
import cdu278.repetition.root.main.ui.MainTabConfig
import cdu278.repetition.root.main.ui.component.MainComponent
import cdu278.repetition.root.ui.ScreenConfig
import cdu278.repetition.root.ui.ScreenConfig.Main
import cdu278.repetition.root.ui.ScreenConfig.Repetition
import cdu278.repetition.root.ui.UiRootScreen
import cdu278.repetition.s.repository.RepetitionsRepository
import cdu278.repetition.s.repository.RepetitionsRepositoryInstance
import cdu278.repetition.ui.component.RepetitionComponent
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.MutableSharedFlow
import cdu278.repetition.root.main.ui.MainTabConfig.Active as ConfigActive
import cdu278.repetition.root.main.ui.MainTabConfig.Archive as ConfigArchive

class RootComponent(
    context: IntervalsComponentContext,
    private val repetitionsRepositoryFactory: () -> RepetitionsRepository,
    initialStack: () -> List<ScreenConfig> = { listOf(Main) },
) : IntervalsComponentContext by context {

    private val repetitionsRepository =
        instanceKeeper.getOrCreate {
            RepetitionsRepositoryInstance(
                repository = { repetitionsRepositoryFactory.invoke() },
            )
        }

    private val navigation = StackNavigation<ScreenConfig>()

    private val requestedMainTabFlow = MutableSharedFlow<MainTabConfig>()

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
                        repetitionsRepository,
                        goToRepetition = { navigation.push(Repetition(id = it)) },
                        requestedMainTabFlow,
                    )
                is Repetition ->
                    RepetitionComponent(
                        newContext(componentContext),
                        repetitionsRepository.repetitionRepository(config.id),
                        dataMatching = RepetitionDataMatching(hashes),
                        close = { navigation.pop() },
                        onChecked = {
                            requestedMainTabFlow.emit(ConfigActive)
                        },
                        onArchived = {
                            requestedMainTabFlow.emit(ConfigArchive)
                        },
                    )
            }
        }.map { entry ->
            entry.active
        }.asStateFlow(lifecycle)

    internal val screenFlow: StateFlow<UiRootScreen?> =
        childStack.map { entry ->
            when (entry.configuration) {
                is Main -> UiRootScreen.Main(entry.instance as MainComponent)
                is Repetition -> UiRootScreen.Repetition(entry.instance as RepetitionComponent)
            }
        }.stateIn(coroutineScope(), uiModelSharingStarted, initialValue = null)
}