package midget17468.permission.notification

import android.Manifest
import android.os.Build
import androidx.activity.ComponentActivity
import midget17468.permission.ActivityRuntimePermission
import midget17468.permission.InstallTimePermission
import midget17468.permission.Permission

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