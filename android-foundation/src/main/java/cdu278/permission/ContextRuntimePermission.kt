package cdu278.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.checkSelfPermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContextRuntimePermission(
    private val context: Context,
    private val permissionName: String,
) : Permission {

    override suspend fun isGranted(): Boolean {
        return withContext(Dispatchers.Main.immediate) {
            checkSelfPermission(context, permissionName) == PackageManager.PERMISSION_GRANTED
        }
    }
}