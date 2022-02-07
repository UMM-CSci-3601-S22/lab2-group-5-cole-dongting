package umm3601.todos;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
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
   * Get the single user specified by the given ID. Return `null` if there is no
   * user with that ID.
   *
   * @param id the ID of the desired user
   * @return the user with the given ID, or null if there is no user with that ID
   */
  public ToDos getToDos(String id) {
    return Arrays.stream(allToDos).filter(x -> x._id.equals(id)).findFirst().orElse(null);
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
    //if (queryParams.containsKey("status")) {

    //}
    // Filter category if defined
    if (queryParams.containsKey("category")) {
      String targetCategory = queryParams.get("category").get(0);
      filteredToDos = filterToDosByCategory(filteredToDos, targetCategory);
    }
    // Process other query parameters here...

    return filteredToDos;
  }

  /**
   * Get an array of all the users having the target age.
   *
   * @param users     the list of users to filter by age
   * @param targetAge the target age to look for
   * @return an array of all the users from the given list that have the target
   *         age
   */
  public ToDos[] filterToDosByOwner(ToDos[] todos, String targetOwner) {
    return Arrays.stream(todos).filter(x -> x.owner.equals(targetOwner)).toArray(ToDos[]::new);
  }

  /**
   * Get an array of all the users having the target company.
   *
   * @param users         the list of users to filter by company
   * @param targetCompany the target company to look for
   * @return an array of all the users from the given list that have the target
   *         company
   */
  public ToDos[] filterToDosByCategory(ToDos[] todos, String targetCategory) {
    return Arrays.stream(todos).filter(x -> x.category.equals(targetCategory)).toArray(ToDos[]::new);
  }

}
