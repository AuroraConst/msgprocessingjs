package com.axiommd.webview
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import io.laminext.fetch._


object ModelFetch :
  import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
  import zio.json._

  import io.laminext.fetch._
  import org.scalajs.dom.AbortController

  val abortController = new AbortController()

  def fetchPatients = 
    import java.time._ //cross scalajs and jvm compatible
    import com.axiom.model.shared.dto.Patient 

    Fetch.get("http://localhost:8080/patientsjson").future.text(abortController)
      .map(s => s.data.fromJson[List[Patient]])
      .map(r => r.toOption.getOrElse(Nil))





