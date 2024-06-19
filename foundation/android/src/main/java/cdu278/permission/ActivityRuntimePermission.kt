package cdu278.permission

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle.State.CREATED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ActivityRuntimePermission(
    private val activity: ComponentActivity,
    private val permissionName: String,
) : Permission {

    init {
        if (activity.lifecycle.currentState.isAtLeast(CREATED)) {
            error("Should be created before the activity reached created state")
        }
    }

    private var continuation: Continuation<Boolean>? = null

    private val launcher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            continuation?.resume(granted)
                ?: error("Permission request has not been launched")
            continuation = null
        }

    override suspend fun isGranted(): Boolean {
        return withContext(Dispatchers.Main.immediate) {
            if (ContextRuntimePermission(activity, permissionName).isGranted()) {
                return@withContext true
            }
            suspendCoroutine { cont ->
                continuation = cont
                launcher.launch(permissionName)
            }
        }
    }
}