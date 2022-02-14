package umm3601.todos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "MagicNumber", "LineLength" })
public class ToDosDatabaseTest {

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

    @Test
    void testFilterToDosByOwner() throws IOException {
      ToDosDatabase db = new ToDosDatabase("/todos.json");
      ToDos[] allToDos = db.listToDos(new HashMap<>());

      ToDos[] todosBarry = db.filterToDosByOwner(allToDos, "Barry");
        assertEquals(51, todosBarry.length, "Incorrect number of To-Dos with owner \"Barry\". 51 results should be found.");

      //Check results are 0 if given a nonexistent owner
      ToDos[] todosFooBar = db.filterToDosByOwner(allToDos, "FooBar");
      assertEquals(0, todosFooBar.length, "Incorrect number of To-Dos with owner \"FooBar\". No results should be found.");

      //Check first result is correct
      ToDos firstBarryToDo = todosBarry[0];
        assertEquals("588959856f0b82ee93cd93eb", firstBarryToDo._id, "Incorrect name");
        assertEquals("Barry", firstBarryToDo.owner, "Incorrect category");

    }

    @Test
    void testFilterToDosByCategory() throws IOException {
      ToDosDatabase db = new ToDosDatabase("/todos.json");
      ToDos[] allToDos = db.listToDos(new HashMap<>());

      ToDos[] todosHomework = db.filterToDosByCategory(allToDos, "homework");
        assertEquals(79, todosHomework.length, "Incorrect number of To-Dos with category \"Homework\". 79 results should be found.");

      //Check results are 0 if given a nonexistent category
      ToDos[] todosFooBar = db.filterToDosByCategory(allToDos, "FooBar");
      assertEquals(0, todosFooBar.length, "Incorrect number of To-Dos with category \"FooBar\". No results should be found.");

      //Check first result is correct
      ToDos firstHomeworkToDo = todosHomework[0];
        assertEquals("58895985ae3b752b124e7663", firstHomeworkToDo._id, "Incorrect name");
        assertEquals("homework", firstHomeworkToDo.category, "Incorrect category");

    }

    @Test
    void testFilterToDosByBodyContains() throws IOException {
      ToDosDatabase db = new ToDosDatabase("/todos.json");
      ToDos[] allToDos = db.listToDos(new HashMap<>());

      ToDos[] todosSunt = db.filterToDosByBodyContains(allToDos, "sunt");
        assertEquals(85, todosSunt.length, "Incorrect number of To-Dos with contains \"sunt\". 85 results should be found.");

      //Check results are 0 if given a nonexistent category
      ToDos[] todosNicGood = db.filterToDosByCategory(allToDos, "NicGood");
      assertEquals(0, todosNicGood.length, "Incorrect number of To-Dos with category \"NicGood\". No results should be found.");

    }

    @Test
    void testFilterToDosByLimit() throws IOException {
      ToDosDatabase db = new ToDosDatabase("/todos.json");
      ToDos[] allToDos = db.listToDos(new HashMap<>());

      ToDos[] todosLimits = db.filterToDosByLimit(allToDos, 3);
        assertEquals(3, todosLimits.length, "The value '3' can not be parsed to integer.");

      //Check results if given a wrong input
      ToDos[] todosFalseLimits = db.filterToDosByLimit(allToDos, 1000);
      assertEquals(300, todosFalseLimits.length, "The number of limit is incorrect.");

      //Check first result is correct
      ToDos firstThreeLimitToDo = todosLimits[0];
        assertEquals("58895985a22c04e761776d54", firstThreeLimitToDo._id, "Incorrect name");
        assertEquals("software design", firstThreeLimitToDo.category, "Incorrect category");

    }

}
