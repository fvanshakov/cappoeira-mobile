package com.franshizastr.linkopener

import android.content.Intent
import android.net.Uri
import ru.cappoeira.app.androidApp.CapoAndroidApplication

actual class LinkOpener actual constructor() {
    actual fun openUrl(url: String) {
        val context = CapoAndroidApplication.appInstance
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        context.startActivity(intent)
    }
}
