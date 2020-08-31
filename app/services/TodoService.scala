package services

import javax.inject.Inject
import anorm.SqlParser._
import anorm._
import play.api.db.DBApi
import scala.language.postfixOps

@javax.inject.Singleton
class TodoService @Inject() (dbapi: DBApi) {

  private val db = dbapi.database("default")

  private val simple = {
    get[Option[Long]]("todo.id") ~
      get[String]("todo.name") map {
      case id~name => Todo(id, name)
    }
  }

  def list(): Seq[Todo] = {
    db.withConnection { implicit connection =>
      SQL(
        """
          SELECT * FROM todo
        """
      ).as(simple *)
    }
  }

  def insert(todo: Todo) = {
    db.withConnection { implicit connection =>
      SQL(
        """
        INSERT INTO todo
        VALUES ((SELECT NEXT VALUE FOR todo_seq), {name})
        """
      ).on(
        'name -> todo.name
      ).executeUpdate()
    }
  }

  def findById(id: Long): Option[Todo] = {
    db.withConnection { implicit connection =>
      SQL("SELECT * FROM todo WHERE id = {id}")
        .on('id -> id)
        .as(simple.singleOpt)
    }
  }

  def update(id: Long, todo: Todo) = {
    db.withConnection { implicit connection =>
      SQL(
        """
          UPDATE todo
          SET name = {name}
          WHERE id = {id}
        """
      ).on(
        'id -> id,
        'name -> todo.name
      ).executeUpdate()
    }
  }

  def delete(id: Long) = {
    db.withConnection { implicit connection =>
      SQL(
        "DELETE FROM todo WHERE id = {id}"
      ).on('id -> id).executeUpdate()
    }
  }

}