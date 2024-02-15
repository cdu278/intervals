package midget17468.passs.decompose.context.extended

import android.content.Context
import com.arkivanov.decompose.ComponentContext

private class ExtendedComponentContextImpl(
    componentContext: ComponentContext,
    override val context: Context,
) : ExtendedComponentContext,
    ComponentContext by componentContext

fun ExtendedComponentContext(
    componentContext: ComponentContext,
    context: Context,
): ExtendedComponentContext {
    return ExtendedComponentContextImpl(componentContext, context)
}