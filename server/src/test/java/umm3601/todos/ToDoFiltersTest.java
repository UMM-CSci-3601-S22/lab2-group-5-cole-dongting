package umm3601.todos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "MagicNumber", "LineLength" })
public class ToDoFiltersTest {

  // Test category filter for ToDosDatabase
  @Test
  public void categoryFilters() throws IOException {
    ToDosDatabase db = new ToDosDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    // Test real category
    queryParams.put("category", Arrays.asList(new String[] { "software design" }));
    ToDos[] softwareDesignToDos = db.listToDos(queryParams);
    assertEquals(74, softwareDesignToDos.length,
        "Incorrect number of To-Dos with category \"software design\". There are 74 total.");

    queryParams.clear();
    queryParams.put("category", Arrays.asList(new String[] { "foobar" }));
    ToDos[] foobarToDos = db.listToDos(queryParams);
    assertEquals(0, foobarToDos.length, "Incorrect number of To-Dos with category \"foobar\". There are 0 total.");
  }
}
