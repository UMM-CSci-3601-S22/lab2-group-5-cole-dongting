package umm3601.todos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "MagicNumber", "LineLength" })
public class ToDoFiltersTest {

  // Test category filter for ToDosDatabase
  @Test
  public void categoryFilters() throws IOException {
    ToDosDatabase db = new ToDosDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    // Test real category
    queryParams.put("category", Arrays.asList(new String[] {"software design"}));
    ToDos[] softwareDesignToDos = db.listToDos(queryParams);
    assertEquals(74, softwareDesignToDos.length,
        "Incorrect number of To-Dos with category \"software design\". There are 74 total.");
    // Test nonexistent category
    queryParams.clear();
    queryParams.put("category", Arrays.asList(new String[] {"foobar"}));
    ToDos[] foobarToDos = db.listToDos(queryParams);
    assertEquals(0, foobarToDos.length, "Incorrect number of To-Dos with category \"foobar\". There are 0 total.");
  }

  // Test category status for ToDosDatabase
  @Test
  public void statusFilters() throws IOException {
    ToDosDatabase db = new ToDosDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    // Test real status
    queryParams.put("status", Arrays.asList(new String[] {"complete"}));
    ToDos[] completeToDos = db.listToDos(queryParams);
    assertEquals(143, completeToDos.length,
        "Incorrect number of To-Dos with status \"complete\". There are 143 total.");

    // Test nonexistent status
    queryParams.clear();
    queryParams.put("status", Arrays.asList(new String[] {"foobar"}));
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      db.listToDos(queryParams);
    });
  }

    // Test category filter for ToDosDatabase
    @Test
    public void ownerFilters() throws IOException {
      ToDosDatabase db = new ToDosDatabase("/todos.json");
      Map<String, List<String>> queryParams = new HashMap<>();

      // Test real owner
      queryParams.put("owner", Arrays.asList(new String[] {"Fry"}));
      ToDos[] softwareDesignToDos = db.listToDos(queryParams);
      assertEquals(61, softwareDesignToDos.length,
          "Incorrect number of To-Dos with owner \"Fry\". There are 61 total.");

      // Test nonexistent owner
      queryParams.clear();
      queryParams.put("owner", Arrays.asList(new String[] {"foobar"}));
      ToDos[] foobarToDos = db.listToDos(queryParams);
      assertEquals(0, foobarToDos.length, "Incorrect number of To-Dos with owner \"foobar\". There are 0 total.");
    }

    // Test Category orderBy for ToDosDatabase
    @Test
    public void orderByCategory() throws IOException {
      ToDosDatabase db = new ToDosDatabase("/todos.json");
      Map<String, List<String>> queryParams = new HashMap<>();

      // Test real orderBy Category
      queryParams.put("orderBy", Arrays.asList(new String[] {"owner"}));
      ToDos[] totalToDos = db.listToDos(queryParams);
      assertEquals(300, totalToDos.length,
          "Incorrect Input.");

      // Test nonexistent orderBy Category
      queryParams.clear();
      queryParams.put("orderBy", Arrays.asList(new String[] {"number"}));
      Assertions.assertThrows(IllegalArgumentException.class, () -> {
        db.listToDos(queryParams);
      });
    }
}
