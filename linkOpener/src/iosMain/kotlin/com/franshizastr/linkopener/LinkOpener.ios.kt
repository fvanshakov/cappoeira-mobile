package com.franshizastr.linkopener

import platform.UIKit.UIApplication
import platform.Foundation.NSURL

actual class LinkOpener actual constructor() {
    actual fun openUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url) ?: return

        val application = UIApplication.sharedApplication
        if (application.canOpenURL(nsUrl)) {
            application.openURL(
                nsUrl,
                options = emptyMap<Any?, Any>(),
                completionHandler = null
            )
        } else {
            println("Cannot open URL: $url")
        }
    }
}