import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

// --== CS400 File Header Information ==--
// Name: Jessica Xu
// Email: jxxu2@wisc.edu
// Team: FB blue
// Role: Backend developer
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader:

/**
 * Backend has methods that will allow frontend to interact with the data provided. Backend takes
 * data from csv file and puts it into a graph.
 * 
 * @author xujessica
 *
 */
public class BackEnd {
  protected List<Planets> planetsList;
  protected List<Paths> pathsList;
  private int size; // size of paths in graph
  protected CS400Graph<String> solarSystem;

  /**
   * Constructor for BackEnd object which takes in string from the user to put as elements in the
   * graph
   * 
   * @param input the data specified by the user to input the reservation
   * @throws FileNotFoundException if the csv file is not found
   */
  public BackEnd(String input) throws FileNotFoundException, IOException {
    SolarSystemDataReader dataReader = new SolarSystemDataReader();
    planetsList = new ArrayList<Planets>();
    pathsList = new ArrayList<Paths>();
    try {

      List<String> list = dataReader.read(new StringReader(input));
      helper(list);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      initializeGraph();
    }
  }

  /**
   * Constructor for BackEnd object which takes a filepath from the user (console)
   * 
   * @param filepath Reader object that contains the filepath
   */
  public BackEnd(Reader filepath) throws FileNotFoundException, IOException {
    SolarSystemDataReader dataReader = new SolarSystemDataReader();
    planetsList = new ArrayList<Planets>();
    pathsList = new ArrayList<Paths>();
    try {

      List<String> list = dataReader.read(filepath);
      helper(list);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      initializeGraph();
    }
  }

  /**
   * Helper method for constructor to sort list of strings returned by SolarSystemDataReader and add
   * to either pathsList or planetsList based on String object
   * 
   * @param list
   */
  public void helper(List<String> list) {
    for (int i = 0; i < list.size(); i++) {
      String[] entry = list.get(i).split(", ");

      // adds planet to planetsList
      boolean foundPlanet = false;
      if (entry[0].equalsIgnoreCase("planet")) {
        for (int x = 0; x < planetsList.size(); x++) {
          if (planetsList.get(x).getName().equalsIgnoreCase(entry[1])) {
            foundPlanet = true;
          }
        }
        if (!foundPlanet) {
          planetsList.add(new Planets(entry[1]));
        }
      }

      // adds path to pathsList
      boolean foundStart = false;
      boolean foundEnd = false;
      if (entry[0].equalsIgnoreCase("edge")) {
        pathsList.add(
            new Paths(new Planets(entry[1]), new Planets(entry[2]), Integer.parseInt(entry[3])));

        // adding to planets list if not already there
        for (int j = 0; j < planetsList.size(); j++) {
          if (planetsList.get(j).getName().equalsIgnoreCase(entry[1])) {
            foundStart = true;
          }
          if (planetsList.get(j).getName().equalsIgnoreCase(entry[2])) {
            foundEnd = true;
          }
        }
        if (!foundStart) {
          planetsList.add(new Planets(entry[1]));
        }
        if (!foundEnd) {
          planetsList.add(new Planets(entry[2]));
        }

      }
    }
  }

  /**
   * Private helper method to initialize the graph once the BackEnd Object is created and the add
   * all the Planet data to the graph
   */
  public void initializeGraph() {

    solarSystem = new CS400Graph<String>();

    // add all the planets to the graph
    for (int i = 0; i < planetsList.size(); i++) {
      solarSystem.insertVertex(planetsList.get(i).getName());
    }

    // add all the paths to the graph
    for (int i = 0; i < pathsList.size(); i++) {
      solarSystem.insertEdge(pathsList.get(i).getStart().getName(),
          pathsList.get(i).getEnd().getName(), pathsList.get(i).getFuelCost());
      size++;
    }
  }

  /**
   * Method that adds planet to graph
   * 
   * @param planet
   * @return true if added successfully, false otherwise
   */
  public boolean addPlanet(String planet) throws IllegalArgumentException {
    if (!solarSystem.containsVertex(planet)) {
      solarSystem.insertVertex(planet);
      // adds to planetsList
      planetsList.add(new Planets(planet));

      return true;
    }
    return false;
  }

  /**
   * Method that adds path from a planet to another
   * 
   * @param planet
   * @param path
   * @return true if added successfully, false otherwise
   */
  public boolean addPath(String start, String end, int path) throws IllegalArgumentException {

    // adds start planet if solarSystem doesn't contain it
    if (!solarSystem.containsVertex(start)) {
      addPlanet(start);
    }

    // adds end planet if solarSystem doesn't contain it
    if (!solarSystem.containsVertex(end)) {
      addPlanet(end);
    }

    // adds edge if solarSystem doesn't contain it
    if (!solarSystem.containsEdge(start, end)) {
      solarSystem.insertEdge(start, end, path);
      // adds to pathsList
      pathsList.add(new Paths(new Planets(start), new Planets(end), path));
      size++;
      return true;
    }

    return false;
  }

  /**
   * Method that removes a planet
   * 
   * @param planet
   * @return true if removed successfully, false otherwise
   */
  public boolean removePlanet(String planet) throws IllegalArgumentException {
    if (solarSystem.containsVertex(planet)) {
      solarSystem.removeVertex(planet);

      // removes from planetsList
      for (int i = 0; i < planetsList.size(); i++) {
        if (planetsList.get(i).getName().equalsIgnoreCase(planet)) {
          planetsList.remove(i);
        }
      }

      // removes all edges connected to planet
      for (int i = 0; i < pathsList.size(); i++) {
        if (pathsList.get(i).getStart().getName().equalsIgnoreCase(planet)
            || pathsList.get(i).getEnd().getName().equalsIgnoreCase(planet)) {
          pathsList.remove(i);
          i = i - 1;
        }
      }
      return true;
    }
    return false;
  };

  /**
   * Method that removes a path
   * 
   * @param start, end
   * @return true if removed successfully, false otherwise
   */
  public boolean removePath(String start, String end) throws IllegalArgumentException {
    if (solarSystem.containsEdge(start, end)) {
      solarSystem.removeEdge(start, end);

      // removes from pathsList
      for (int i = 0; i < pathsList.size(); i++) {
        if (pathsList.get(i).getStart().getName().equalsIgnoreCase(start)
            || pathsList.get(i).getEnd().getName().equalsIgnoreCase(end)) {
          pathsList.remove(i);

          i = i - 1;
        }
      }
      return true;
    }
    return false;
  };

  /**
   * Method that returns list of all the planets and their paths in string format will format like
   * this: Paths from <Planet> : <Planet> takes <fuelCost> to go to <OtherPlanet>
   * 
   * @return List<Planet>
   */
  public String getAllPlanets() {
    String output = "";
    for (int i = 0; i < planetsList.size(); i++) {
      output += "\nPaths from " + planetsList.get(i).getName() + ":\n";

      for (int j = 0; j < pathsList.size(); j++) {
        if (pathsList.get(j).getStart().getName().equalsIgnoreCase(planetsList.get(i).getName())) {
          output += "\t" + pathsList.get(j).toString() + "\n";
        }
      }
    }

    return output;
  }

  /**
   * Method that calculates the shortest path between two specified planets and then the fuel cost.
   * return -1 if it does not exist.
   * 
   * @param start
   * @param end
   * @return fuel cost
   */
  public int getFuelCost(String start, String end)
      throws NoSuchElementException, IllegalArgumentException {

    int fuel = -1;

    if (!solarSystem.containsVertex(start) || !solarSystem.containsVertex(end)) {
      throw new IllegalArgumentException("Start or end planet does not exist");
    }

    try {
      fuel = solarSystem.getPathCost(start, end);
      return fuel;
    } catch (NoSuchElementException e) {
      throw e;
    }
  }

  /**
   * Method that returns list of all planets that the specified planet can go to
   * 
   * @param planet
   * @return
   */
  public List<Paths> getPlanetPaths(String planet) throws IllegalArgumentException {

    List<Paths> output = new ArrayList<Paths>();
    for (int i = 0; i < pathsList.size(); i++) {
      if (pathsList.get(i).getStart().getName().equals(planet)) {
        output.add(pathsList.get(i));
      }
    }

    return output;
  }

  /**
   * To get the total number of paths in the graph
   * 
   * @return number of paths
   */
  public int getSize() {
    return size;
  }

}