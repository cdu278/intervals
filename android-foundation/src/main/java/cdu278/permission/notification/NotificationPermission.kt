package cdu278.permission.notification

import android.Manifest
import android.os.Build
import androidx.activity.ComponentActivity
import cdu278.permission.ActivityRuntimePermission
import cdu278.permission.InstallTimePermission
import cdu278.permission.Permission

fun ComponentActivity.notificationPermission(): Permission {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ActivityRuntimePermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS,
        )
    } else {
        InstallTimePermission()
    }
}