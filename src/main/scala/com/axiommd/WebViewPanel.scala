package com.axiommd

import typings.vscode.mod as vscode
import typings.vscode.anon.Dispose
import typings.vscode.Thenable

import scala.collection.immutable
import scala.util.*
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.scalajs.js.UndefOr
import javax.swing.table.TableColumn
// import upickle.default._
import zio.json._
import javax.xml.catalog.CatalogResolver
import com.axiommd.webview.utils.*


class WebViewPanel (private var _panel: vscode.WebviewPanel,private val _extensionUri: vscode.Uri) :
  // Set the webview's initial html content
  _update()
  // Listen for when the panel is disposed
	// This happens when the user closes the panel or when the panel is closed programmatically
  _panel.onDidDispose((a:Unit) => dispose(), (), this._disposables) 


  _panel.onDidChangeViewState((e) => {
      if(_panel.visible){
        _update()
      }
    }, null, _disposables)

  _panel.webview.onDidReceiveMessage(processageMsg, null, _disposables)  

  def processageMsg(m:Any):Any =
    val message = m.asInstanceOf[Message]
    message.typ match {
      case "alert" => vscode.window.showErrorMessage(message.value)
      case _ => ()
    }


  private val _disposables: js.Array[vscode.Disposable] = new js.Array()

  private def  _update() = 
    val webview = this._panel.webview
    _panel.viewColumn match {
      case vscode.ViewColumn.Two => 
        _updateWebView(webview,"Column 2")
      case vscode.ViewColumn.Three =>
        _updateWebView(webview,"Column 3")
      case vscode.ViewColumn.One  =>    
        _updateWebView(webview,"Column 1")
      case _ => _updateWebView(webview,"Column beyond 3")

    }


  private def _updateWebView(webview: vscode.Webview, title:String)  =
    _panel.title = title
    _panel.webview.html = _getHtmlForWebview(webview)

  private def _getHtmlForWebview(webview: vscode.Webview) = 
    val scriptPathOnDisk = vscode.Uri.joinPath(_extensionUri, "media", "main.js")
    val scriptUri = webview.asWebviewUri(scriptPathOnDisk)
    val styleResetPath = vscode.Uri.joinPath(_extensionUri, "media", "reset.css")
    val stylesPathMainPath = vscode.Uri.joinPath(_extensionUri, "media", "vscode.css")

    val stylesResetUri = webview.asWebviewUri(styleResetPath)
    val stylesMainUri = webview.asWebviewUri(stylesPathMainPath)
    val nonce = Nonce()

    s"""
      <!DOCTYPE html>
			<html lang="en">
			<head>
				<meta charset="UTF-8">

				<!--
					Use a content security policy to only allow loading images from https or from our extension directory,
					and only allow scripts that have a specific nonce.
				-->
				<meta http-equiv="Content-Security-Policy" content="default-src 'none'; style-src ${webview.cspSource}; img-src ${webview.cspSource} https:; script-src 'nonce-${nonce}'; connect-src 'self' http://localhost:8080;">

				<meta name="viewport" content="width=device-width, initial-scale=1.0">

				<link href="${stylesResetUri}" rel="stylesheet">
				<link href="${stylesMainUri}" rel="stylesheet">
			</head>
			<body>
        <div id="app"></div>

				<script nonce="${nonce}" src="${scriptUri}"></script>
			</html>

    """


  def doRefactor() = 
    this._panel.webview.postMessage(Message("refactor","refactor" ).toJson)  

  def dispose() =
    WebViewPanel.currentPanel = None

    this._panel.dispose()
    while(this._disposables.length > 0){
      val x = this._disposables.pop()
      x.dispose()
    }




object WebViewPanel:
  var currentPanel: Option[WebViewPanel] = None
  val viewType= "catCoding"


  def createOrShow(extensionUri: vscode.Uri) =
    val column = vscode.window.activeTextEditor.toOption.flatMap{editor => editor.viewColumn.toOption}

    // If we already have a panel, show it.
    val resultExists =  for{
      panel <- WebViewPanel.currentPanel
      col <- column
    }
    yield {
        Console.log("REVEALING PANEL THAT ALREADY EXISTS")
        panel._panel.reveal(col)
    } 

    resultExists.getOrElse{
      val newPanel = createNewPanel()
      currentPanel = Some(new WebViewPanel(newPanel, extensionUri))
    }

    /** 
      orElse{
        Console.log("CREATING NEW PANEL")
        createNewPanel
      }
    */

    // Otherwise, create a new panel.
    def createNewPanel() =  
      vscode.window.createWebviewPanel(
        viewType,
        "Cat Coding",
        column.getOrElse( vscode.ViewColumn.One),
        WebviewOptions.getWebviewOptions(extensionUri).asInstanceOf[vscode.WebviewOptions & vscode.WebviewPanelOptions]
      );



  def revive(panel: vscode.WebviewPanel, extensionUri: vscode.Uri) =
    currentPanel = Some(new WebViewPanel(panel, extensionUri))




