package com.axiommd.webview.utils
import typings.vscode.mod as vscode
import scala.collection.immutable
object WebviewOptions {

  def getWebviewOptions(extensionUri: vscode.Uri) = 
    val localResourceRoots = immutable.Seq(vscode.Uri.joinPath(extensionUri, "media"))
    new vscode.WebviewOptions{
      override val enableScripts = true
      override val localResourceRoots = localResourceRoots
    }


}