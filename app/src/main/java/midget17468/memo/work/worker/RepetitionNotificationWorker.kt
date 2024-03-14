package midget17468.memo.work.worker

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import midget17468.activity.dependent.DependentActivity
import midget17468.memo.Memo
import midget17468.memo.MemoDb
import midget17468.memo.activity.RepetitionActivity
import midget17468.memo.application.MemoApplication
import midget17468.memo.model.domain.MemoType.Password
import midget17468.memo.repository.RepetitionNotificationRepository
import midget17468.notification.AndroidNotifications
import midget17468.notification.channel.config.RepetitionsChannelConfig
import midget17468.notification.identity.RepetitionNotificationIdentity
import midget17468.passs.MainActivity
import midget17468.passs.R
import midget17468.permission.ContextRuntimePermission
import midget17468.permission.InstallTimePermission
import midget17468.memo.android_foundation.R as FoundationR

class RepetitionNotificationWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    companion object {

        const val KEY_MEMO_ID = "memoId"
    }

    private val module
        get() = (applicationContext as MemoApplication).module

    private val resources
        get() = applicationContext.resources

    private fun notificationMessage(memo: Memo): String {
        return resources.getString(
            R.string.repetitionNotification_messageFmt,
            memo.typeText,
            memo.label
        )
    }

    private val Memo.typeText: String
        get() = resources.getString(
            when (type) {
                Password -> FoundationR.string.password
            }
        ).lowercase()

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        val memoId = inputData.getInt(KEY_MEMO_ID, -1)

        val notifications =
            AndroidNotifications(
                applicationContext,
                permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextRuntimePermission(
                        applicationContext,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    )
                } else {
                    InstallTimePermission()
                },
                channelConfig = RepetitionsChannelConfig(applicationContext),
            )

        val repository = RepetitionNotificationRepository(module.memoDb.memoQueries)

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main.immediate)

        coroutineScope.launch {
            val memo = repository.findById(memoId)
            notifications.show(RepetitionNotificationIdentity(memoId)) { builder ->
                builder
                    .setContentText(notificationMessage(memo))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(
                        PendingIntent.getActivity(
                            applicationContext,
                            0,
                            repetitionIntent(memoId).apply {
                                flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                            },
                            PendingIntent.FLAG_IMMUTABLE,
                        )
                    )
            }
        }

        return Result.success()
    }

    private fun repetitionIntent(memoId: Int): Intent {
        return DependentActivity.intent(
            applicationContext,
            RepetitionActivity::class,
            RepetitionDeps(memoId)
        )
    }

    @Parcelize
    private class RepetitionDeps(
        override val memoId: Int
    ) : RepetitionActivity.Deps {

        override val memoDb: MemoDb
            get() = MemoApplication.module.memoDb

        override val close: (Activity) -> () -> Unit
            get() = { activity ->
                {
                    if (MainActivity.instance == null) {
                        activity.startActivity(Intent(activity, MainActivity::class.java))
                    }
                    activity.finish()
                }
            }
    }
}