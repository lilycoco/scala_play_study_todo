package controllers

import javax.inject.Inject

import scala.concurrent.Future
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import org.scalatestplus.mockito.MockitoSugar
import services._
import org.scalatestplus.play.guice._

class ExampleControllerSpec extends PlaySpec with Results {

  "Example Page#index" should {
    val controller   = new ExampleController(Helpers.stubControllerComponents())

    "be valid" in {
      val result: Future[Result] = controller.index().apply(FakeRequest())
      val bodyText: String       = contentAsString(result)
      bodyText mustBe "ok"
    }
  }
}

class TodoControllerSpec @Inject()(val todoService: TodoService)
  extends PlaySpec with Results with MockitoSugar with GuiceOneAppPerTest {

  val mockDataService = mock[TodoService]
  val controller      = new TodoController(mockDataService, stubMessagesControllerComponents())

  "TodoController#helloworld" should {
    "access '/todo/helloworld' with GET method" in {
      val request  = FakeRequest(GET, "/todo/helloworld")
      val response = route(app, request).get
      status(response) mustBe OK
    }

    "be valid" in {
      val result: Future[Result] = controller.helloworld().apply(FakeRequest())
      val bodyText: String       = contentAsString(result)
      bodyText mustBe "Hello World"
    }
  }

  "TodoController#list" should {
    "access '/todo' with GET method" in {
      val controller2      = new TodoController(todoService, stubMessagesControllerComponents())
      val result: Future[Result] = controller2.list().apply(FakeRequest())
      status(result) mustBe OK
    }
  }

  "TodoController#todoNew" should {
    "be valid" in {
      val result: Future[Result] = controller.todoNew().apply(FakeRequest())
      System.out.println(result)
      status(result) mustBe OK
    }
  }
}
