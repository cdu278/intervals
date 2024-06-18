package cdu278.notification.identity

@Suppress("FunctionName")
fun RepetitionNotificationIdentity(repetitionId: Long) =
    NotificationIdentity(
        tag = "repetition",
        id = repetitionId
    )