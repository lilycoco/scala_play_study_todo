package controllers

import scala.concurrent.Future
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import org.scalatest.mock.MockitoSugar
import services._

import org.scalatestplus.play.guice._

class ExampleControllerSpec extends PlaySpec with Results {

  "Example Page#index" should {
    "should be valid" in {
      val controller             = new ExampleController(Helpers.stubControllerComponents())
      val result: Future[Result] = controller.index().apply(FakeRequest())
      val bodyText: String       = contentAsString(result)
      bodyText mustBe "ok"
    }
  }
}

class TodoControllerSpec extends PlaySpec with Results with MockitoSugar with GuiceOneAppPerTest {

  val mockDataService = mock[TodoService]
  val mockDataComponent = mock[MessagesControllerComponents]
  val controller             = new TodoController(mockDataService, stubMessagesControllerComponents())

  "HelloController GET" must {
    "「/todo」にGETメソッドでアクセスできる" in {
      val request  = FakeRequest(GET, "/todo")
      val response = route(app, request).get

      status(response) mustBe OK
    }
  }

  "TodoController#helloworld" should {
    "should be valid" in {
      System.out.println("bodyText")
      val result: Future[Result] = controller.helloworld().apply(FakeRequest())
      val bodyText: String       = contentAsString(result)
      System.out.println(bodyText)

      bodyText mustBe "Hello World"
    }
  }
}
