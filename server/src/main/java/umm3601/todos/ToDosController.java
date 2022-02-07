package umm3601.todos;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.NotFoundResponse;

public class ToDosController {

  private ToDosDatabase database;

  /**
   * Construct a controller for To-Dos.
   * <p>
   * This loads the "database" of To-Dos info from a JSON file and stores that
   * internally so that (subsets of) To-Dos can be returned in response to
   * requests.
   *
   * @param database the `Database` containing To-Dos data
   */
  public ToDosController(ToDosDatabase database) {
    this.database = database;
  }


  /**
   * Get the single To-Do specified by the `id` parameter in the request.
   *
   * @param ctx a Javalin HTTP context
   */
  public void getToDoByID(Context ctx) {
    String id = ctx.pathParam("id");
    ToDos todo = database.getToDos(id);
    if (todo != null) {
      ctx.json(todo);
      ctx.status(HttpCode.OK);
    } else {
      throw new NotFoundResponse("No To-Do with id " + id + " was found.");
    }
  }

    /**
   * Get a JSON response with a list of all the To-Dos in the "database".
   *
   * @param ctx a Javalin HTTP context
   */
  public void getAllToDos(Context ctx) {
    ToDos[] todos = database.listToDos(ctx.queryParamMap());
    ctx.json(todos);
  }

}
