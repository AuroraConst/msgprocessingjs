package com.axiommd.webview.utils

object Nonce {
      def apply() = 
        val possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        (1 to 32).map(_ => possible.charAt(Math.floor(Math.random() * possible.length).toInt)).mkString

}
