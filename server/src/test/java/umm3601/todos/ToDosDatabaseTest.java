package umm3601.todos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class ToDosDatabaseTest {
    @Test
    void testFilterToDosByCategory() {

    }

    @Test
    void testFilterToDosByOwner() {

    }

    @Test
    void testGetToDos() throws IOException {
      ToDosDatabase db = new ToDosDatabase("/todos.json");
      ToDos todo = db.getToDos("58895985a22c04e761776d54");
      assertEquals("58895985a22c04e761776d54", todo._id, "Incorrect name");
      assertEquals("Blanche", todo.owner, "Incorrect owner");
      assertEquals(false, todo.status, "Incorrect status");
      assertEquals("In sunt ex non tempor cillum commodo amet incididunt anim qui commodo quis. Cillum non labore ex sint esse.",
      todo.body, "Incorrect body");
      assertEquals("software design", todo.category, "Incorrect category");

    }

    @Test
    void testListToDos() throws IOException {
      ToDosDatabase db = new ToDosDatabase("/todos.json");
      ToDos[] allToDos = db.listToDos(new HashMap<>());
      ToDos firstToDo = allToDos[0];
      assertEquals("58895985a22c04e761776d54", firstToDo._id, "Incorrect name");
      assertEquals("Blanche", firstToDo.owner, "Incorrect owner");
      assertEquals(false, firstToDo.status, "Incorrect status");
      assertEquals("In sunt ex non tempor cillum commodo amet incididunt anim qui commodo quis. Cillum non labore ex sint esse.",
      firstToDo.body, "Incorrect body");
      assertEquals("software design", firstToDo.category, "Incorrect category");

    }

    @Test
    void testSize() throws IOException {
      ToDosDatabase db = new ToDosDatabase("/todos.json");
      ToDos[] allToDos = db.listToDos(new HashMap<>());
      assertEquals(300, allToDos.length, "Incorrect total number of To-Dos");
    }
}
