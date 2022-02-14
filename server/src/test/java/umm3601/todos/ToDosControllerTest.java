package umm3601.todos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.NotFoundResponse;
import umm3601.Server;

public class ToDosControllerTest {

  private Context ctx = mock(Context.class);

  private ToDosController toDosController;
  private static ToDosDatabase db;

  @BeforeEach
  public void setUp() throws IOException {
    ctx.clearCookieStore();

    db = new ToDosDatabase(Server.TODOS_DATA_FILE);
    toDosController = new ToDosController(db);
  }

  @Test
  void testGetAllToDos() {
    // Call the method on the mock context, which doesn't
    // include any filters, so we should get all the users
    // back.
    toDosController.getAllToDos(ctx);

    // Confirm that `json` was called with all the ToDos.
    ArgumentCaptor<ToDos[]> argument = ArgumentCaptor.forClass(ToDos[].class);
    verify(ctx).json(argument.capture());
    assertEquals(db.size(), argument.getValue().length);

  }

  @Test
  void testGetToDoByID() throws IOException {
    String id = "5889598585bda42fb8388ba1";
    ToDos todo = db.getToDos(id);

    when(ctx.pathParam("id")).thenReturn(id);

    toDosController.getToDoByID(ctx);

    verify(ctx).json(todo);
    verify(ctx).status(HttpCode.OK);
  }

  @Test
  public void errorHandleFailedID() {
    when(ctx.pathParam("id")).thenReturn(null);
    Assertions.assertThrows(NotFoundResponse.class, () -> {
      toDosController.getToDoByID(ctx);
    });

  }

  @Test
  public void listByCategory() throws IOException {
    Map<String, List<String>> queryParams = new HashMap<>();
    queryParams.put("category", Arrays.asList(new String[] {"homework"}));
    when(ctx.queryParamMap()).thenReturn(queryParams);

    toDosController.getAllToDos(ctx);

    // Confirm that all the ToDos passed to `json` work for 'homework'.
    ArgumentCaptor<ToDos[]> argument = ArgumentCaptor.forClass(ToDos[].class);
    verify(ctx).json(argument.capture());
    for (ToDos todos : argument.getValue()) {
      assertEquals("homework", todos.category);
    }
  }
}
