package umm3601.todos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import umm3601.Server;

public class ToDosControllerTest {

  private Context ctx = mock(Context.class);

  private ToDosController userController;
  private static ToDosDatabase db;

  @BeforeEach
  public void setUp() throws IOException {
    ctx.clearCookieStore();

    db = new ToDosDatabase(Server.TODOS_DATA_FILE);
    userController = new ToDosController(db);
  }

  @Test
  void testGetAllToDos() {
    // Call the method on the mock context, which doesn't
    // include any filters, so we should get all the users
    // back.
    userController.getAllToDos(ctx);

    // Confirm that `json` was called with all the users.
    ArgumentCaptor<ToDos[]> argument = ArgumentCaptor.forClass(ToDos[].class);
    verify(ctx).json(argument.capture());
    assertEquals(db.size(), argument.getValue().length);

  }

  @Test
  void testGetToDoByID() throws IOException {
    String id = "5889598585bda42fb8388ba1";
    ToDos todo = db.getToDos(id);

    when(ctx.pathParam("id")).thenReturn(id);

    userController.getToDoByID(ctx);

    verify(ctx).json(todo);
    verify(ctx).status(HttpCode.OK);

  }
}
