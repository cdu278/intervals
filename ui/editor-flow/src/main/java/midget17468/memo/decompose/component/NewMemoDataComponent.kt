package midget17468.memo.decompose.component

import kotlinx.coroutines.flow.StateFlow
import midget17468.model.ui.Validated

interface NewMemoDataComponent {

    val dataFlow: StateFlow<Validated<String>>
}