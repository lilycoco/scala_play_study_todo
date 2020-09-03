package controllers

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services._

import play.api.http.HttpFilters
import play.filters.csrf.CSRFFilter

class Filters @Inject() (csrfFilter: CSRFFilter) extends HttpFilters {
  def filters = Seq(csrfFilter)
}

class ExampleController(val controllerComponents: ControllerComponents) extends BaseController {
  def index() = Action {
    Ok("ok")
  }
}

class TodoController @Inject()(val todoService: TodoService, mcc: MessagesControllerComponents)
  extends MessagesAbstractController(mcc) {
  def helloworld() = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok("Hello World")
  }

  def list() = Action { implicit request: MessagesRequest[AnyContent] =>
    val items: Seq[Todo] = todoService.list()
    Ok(views.html.list(items))
  }

  val todoForm: Form[String] = Form("name" -> nonEmptyText)

  def todoNew = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.createForm(Form("name" -> nonEmptyText)))
  }

  def todoAdd() = Action { implicit request: MessagesRequest[AnyContent] =>
    val name: String = todoForm.bindFromRequest().get
    todoService.insert(Todo(id = None, name))
    Redirect(routes.TodoController.list())
  }

  def todoEdit(todoId: Long) = Action { implicit request: MessagesRequest[AnyContent] =>
    todoService.findById(todoId).map { todo =>
      Ok(views.html.editForm(todoId, todoForm.fill(todo.name)))
    }.getOrElse(NotFound)
  }

  def todoUpdate(todoId: Long) = Action { implicit request: MessagesRequest[AnyContent] =>
    val name: String = todoForm.bindFromRequest().get
    todoService.update(todoId, Todo(Some(todoId), name))
    Redirect(routes.TodoController.list())
  }

  def todoDelete(todoId: Long) = Action { implicit request: MessagesRequest[AnyContent] =>
    todoService.delete(todoId)
    Redirect(routes.TodoController.list())
  }
}
