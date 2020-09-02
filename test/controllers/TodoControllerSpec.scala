package controllers

import scala.concurrent.Future
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import org.scalatest.mock.MockitoSugar
import services._

class TodoControllerSpec extends PlaySpec with Results with MockitoSugar {

  "TodoController#helloworld" should {
    "should be valid" in {
      val mockDataService = mock[TodoService]
      val mockDataComponent = mock[MessagesControllerComponents]
      val controller             = new TodoController(mockDataService, mockDataComponent)
      val result: Future[Result] = controller.helloworld().apply(FakeRequest())
      val bodyText: String       = contentAsString(result)
      print(bodyText)
      bodyText mustBe "Hello World"
    }
  }
}
