package umm3601.todos;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A fake "database" of user info
 * <p>
 * Since we don't want to complicate this lab with a real database, we're going
 * to instead just read a bunch of user data from a specified JSON file, and
 * then provide various database-like methods that allow the `UserController` to
 * "query" the "database".
 */
public class ToDosDatabase {

  private ToDos[] allToDos;

  public ToDosDatabase(String todosDataFile) throws IOException {
    InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(todosDataFile));
    ObjectMapper objectMapper = new ObjectMapper();
    allToDos = objectMapper.readValue(reader, ToDos[].class);
  }

  public int size() {
    return allToDos.length;
  }

  /**
   * Get the single To-DO specified by the given ID. Return `null` if there is no
   * user with that ID.
   *
   * @param id the ID of the desired To-DO
   * @return the To-DO with the given ID, or null if there is no To-DO with that
   *         ID
   */
  public ToDos getToDos(String id) {
    return Arrays.stream(allToDos)
        .filter(todos -> todos._id.equals(id))
        .findFirst()
        .orElse(null);
  }

  /**
   * Get an array of all the users satisfying the queries in the params.
   *
   * @param queryParams map of key-value pairs for the query
   * @return an array of all the users matching the given criteria
   */
  public ToDos[] listToDos(Map<String, List<String>> queryParams) {
    ToDos[] filteredToDos = allToDos;

    // Filter owner if defined
    if (queryParams.containsKey("owner")) {
      String targetOwner = queryParams.get("owner").get(0);
      filteredToDos = filterToDosByOwner(filteredToDos, targetOwner);
    }

    // Filter status if defined
    if (queryParams.containsKey("status")) {
      String targetStatus = queryParams.get("status").get(0);
      filteredToDos = filterToDosByStatus(filteredToDos, targetStatus);
    }

    // Filter category if defined
    if (queryParams.containsKey("category")) {
      String targetCategory = queryParams.get("category").get(0);
      filteredToDos = filterToDosByCategory(filteredToDos, targetCategory);
    }
    // Process other query parameters here...
    if (queryParams.containsKey("orderBy")) {
      String targetOrderBy = queryParams.get("orderBy").get(0);
      filteredToDos = filterToDosByOrder(filteredToDos, targetOrderBy);
    }

    return filteredToDos;
  }

  /**
   * Get an array of all the To-DOs having the target owner.
   *
   * @param todos       the list of To-DOs to filter by owner
   * @param targetOwner the target owner to look for
   * @return an array of all the To-DOs from the given list that have the target
   *         owner
   */
  public ToDos[] filterToDosByOwner(ToDos[] todos, String targetOwner) {
    return Arrays.stream(todos)
        .filter(x -> x.owner.equals(targetOwner))
        .toArray(ToDos[]::new);
  }

  /**
   * Get an array of all the To-DOs having the target company.
   *
   * @param todos          the list of To-DOs to filter by company
   * @param targetCategory the target company to look for
   * @return an array of all the users from the given list that have the target
   *         company
   */
  public ToDos[] filterToDosByCategory(ToDos[] todos, String targetCategory) {
    return Arrays.stream(todos)
        .filter(x -> x.category.equals(targetCategory))
        .toArray(ToDos[]::new);
  }

  public ToDos[] filterToDosByStatus(ToDos[] todos, String targetStatus) {
    boolean requestedStatus;

    if (targetStatus.equals("complete")) {
      requestedStatus = true;
    } else if (targetStatus.equals("incomplete")) {
      requestedStatus = false;
    } else {
      throw new IllegalArgumentException("Invalid status: " + targetStatus);
    }
    return Arrays.stream(todos)
        .filter(x -> x.status == requestedStatus)
        .toArray(ToDos[]::new);

  }

  public ToDos[] filterToDosByOrder(ToDos[] todos, String targetOrderBy) {
    List<ToDos> todosAsList = Arrays.asList(todos);

    if (targetOrderBy.equals("owner")) {
      todosAsList.sort(Comparator.comparing(ToDos::getOwner));
    } else if (targetOrderBy.equals("category")) {
      todosAsList.sort(Comparator.comparing(ToDos::getCategory));
    } else if (targetOrderBy.equals("body")) {
      todosAsList.sort(Comparator.comparing(ToDos::getBody));
    } else if (targetOrderBy.equals("status")) {
      todosAsList.sort(Comparator.comparing(ToDos::getStatus));
    } else {
      throw new IllegalArgumentException("Invalid status: " + targetOrderBy);
    }

    ToDos[] todosBackToArray = todosAsList.toArray(ToDos[]::new);

    return todosBackToArray;

  }

}
