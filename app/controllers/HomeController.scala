package controllers

import javax.inject._
import play.api.mvc._
import service._
import form._
import java.nio.file.Files

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val fileUploadService = new FileUploadService("test/")

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def upload = Action(parse.multipartFormData) { implicit request =>
    Forms.fileUploadInfoForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(formWithErrors.errors.mkString("\n"))
      },
      fileUploadInfo => {
        request.body.file("file") match {
          case None => {
            BadRequest("No file")
          }
          case Some(file) =>
            val bytes = Files.readAllBytes(file.ref.path)
            fileUploadService.savePartialFile(bytes, fileUploadInfo)
            Ok
        }
      }
    )
  }
}
