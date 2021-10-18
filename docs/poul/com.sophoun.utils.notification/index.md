[poul](../index.md) / [com.sophoun.utils.notification](./index.md)

## Package com.sophoun.utils.notification

### Functions

| Name | Summary |
|---|---|
| [cancelNotification](cancel-notification.md) | `fun <ERROR CLASS>.cancelNotification(vararg notificationId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = intArrayOf()): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Cancel notification base on notification id |
| [createNotificationChannel](create-notification-channel.md) | `fun <ERROR CLASS>.createNotificationChannel(channelId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, channelName: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, channelDescription: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, importance: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = NotificationManager.IMPORTANCE_DEFAULT): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Create notification channel |
| [createNotificationChannelGroup](create-notification-channel-group.md) | `fun <ERROR CLASS>.createNotificationChannelGroup(groupId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, groupName: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Create notification group |
| [getNotificationBuilder](get-notification-builder.md) | `fun <ERROR CLASS>.getNotificationBuilder(channelId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, title: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, text: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, icon: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, groupId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, importance: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = NotificationCompat.PRIORITY_DEFAULT, pendingIntent: <ERROR CLASS>? = null): <ERROR CLASS>`<br>Build a notification |
| [getNotificationChannel](get-notification-channel.md) | `fun <ERROR CLASS>.getNotificationChannel(channelId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): <ERROR CLASS>?`<br>Get notification channel |
| [getNotificationChannelGroup](get-notification-channel-group.md) | `fun <ERROR CLASS>.getNotificationChannelGroup(groupId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): <ERROR CLASS>?`<br>Get notification channel group |
| [notifyNotification](notify-notification.md) | `fun <ERROR CLASS>.notifyNotification(notificationId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, notification: <ERROR CLASS>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Update notification |