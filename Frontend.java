// --== CS400 File Header Information ==--
// Name: Hailey Park
// Email: hpark353@wisc.edu
// Team: FB blue
// Role: Frontend
// TA: Daniel Finer
// Lecturer: Gary
// Notes to Grader:

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;
import java.util.zip.DataFormatException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.List;

/**
 * Frontend interface for the user to explore the solarsystem dataset by prompting user inputs.
 * There is one file screen and 7 modes in the main screen, and users can find the shortest path,
 * print all planets,print all edges, check if two planets are connected, add a new edge, remove all
 * edges, add a new planet, and remove an existing planet from the dataset.
 * 
 * @author Hailey
 *
 */
public class Frontend {
  private static BackEnd backend;
  private static Scanner scnr;

  /**
   * Calls the run method to start the Planet Navigator.
   * 
   * @param command line argument
   * @throws IOException, DataFormatException
   */
  public static void main(String[] args) throws IOException, DataFormatException {
    Frontend.run(backend);
  }

  /**
   * Runs the Planet navigator app. Prints out app functionalities and prompts the user to provide a
   * file path to the dataset, either by a file path or by manually.
   * 
   * @param args String array from the command prompt
   * @throws DataFormatException
   * @throws IOException
   */
  public static void run(BackEnd backEnd) throws IOException, DataFormatException {
    String filePath; // String object that contains the filePath to the csvFile
    String csvAsAString; // String object that contains the csv inputted as a string
    String userChoice; // Defines the userChoice given a prompt
    Reader filePathInput; // ReaderObject to read the filePathInput
    boolean running = true;

    String[] csvArray = new String[1];
    scnr = new Scanner(System.in);
    System.out.println("☆☆☆☆☆☆☆WELCOME TO THE PLANET NAVIGATOR☆☆☆☆☆☆"
        + "\n\nThis program lets you explore planets in our solar system.\n");

    String enterFile =
        "This is the FILE SCREEN.\nWe need you to provide a planet dataset you wish to explore.\n"
            + "Enter 'x' to quit, 'f' to enter a filepath, or 's' to enter a string:\n";

    String modeOptions =
        "This is the MAIN SCREEN.\nThere are 6 options to choose from in this app.\n"
            + "Enter the letter corresponding to the mode you'd like to go to."
            + "\n\tA. Find the shortest path between two planets and calculate the total fuel cost"
            + "\n\tB. Print a list of all the planets"
            + "\n\tC. Print a list of all edges connecting planets"
            + "\n\tD. Check if two planets are connected" + "\n\tE. Add an edge between planets"
            + "\n\tF. Remove an edge between planets" + "\n\tG. Add a new planet"
            + "\n\tH. Remove an existing planet"
            + "\nEnter 'r' to go back to the file screen and 'x' to quit.\n";

    String promptChar = "Please enter a letter: ";

    // while look to run app
    while (running) {
      boolean modeRunning = true;
      System.out.println(enterFile);
      userChoice = scnr.next();

      // ASKS USER FOR FILE
      try {
        // if user enters x
        if (userChoice.equalsIgnoreCase("x")) {
          // exits the while loop
          running = false;
          break;
        }

        // if user enters f
        else if (userChoice.equalsIgnoreCase("f")) {
          System.out.println("Please enter the filepath to the planet explorer csv file:\t");
          scnr.nextLine();
          filePath = scnr.nextLine();
          try {
            filePathInput = new FileReader(filePath);
            // initializes the backend with a reader object as the argument
            backend = new BackEnd(filePathInput);

          } catch (FileNotFoundException e) {
            System.out.println("This file does not exist.\n");
            // e.printStackTrace();// handle FileNotFoundException if file is not found
            modeRunning = false;
            continue;
          }
        } else {
          // if user enters s
          if (userChoice.equalsIgnoreCase("s")) {
            System.out.println("Please enter a string to the planet explorer csv file:\t");
            csvAsAString = scnr.next();
            // intializes backend with a String as the object
            backend = new BackEnd(csvAsAString);
          }
          // if input is null
          else if (userChoice.isEmpty() || userChoice.trim().isEmpty()) {
            throw new NullPointerException("Invalid input. Please try again.\n");
          }
          // if input doesn't match any of the options
          else {
            // restarts the while loop
            System.out.println("Invalid input. Please enter a valid option.\n");
            continue;
          }

        }
      } catch (NullPointerException e) {
        System.out.println(e.getMessage());
        continue;
      }

      // while loop to keep modes running
      while (modeRunning) {
        System.out.println(modeOptions);
        System.out.println(promptChar);
        userChoice = scnr.next();

        // if user wants to go back to file screen
        if (userChoice.equalsIgnoreCase("r")) {
          modeRunning = false;
          continue;
        }
        // if user wants to exit app
        else if (userChoice.equalsIgnoreCase("x")) {
          modeRunning = false;
          running = false;
          break;
        }
        // A: if user chooses to find the shortest path
        else if (userChoice.equalsIgnoreCase("a")) {
          findShortestPath(backend);
        }
        // B. Print a list of all the planets"
        else if (userChoice.equalsIgnoreCase("b")) {
          printAllPlanets(backend);

        }
        // C. Print all edges
        else if (userChoice.equalsIgnoreCase("c")) {
          printAllEdges(backend);
        }
        // D. Check if two planets are connected"
        else if (userChoice.equalsIgnoreCase("d")) {
          isConnected(backend);
        }
        // E. Add an edge
        else if (userChoice.equalsIgnoreCase("e")) {
          addEdge(backend);
        }
        // F. Remove an edge
        else if (userChoice.equalsIgnoreCase("f")) {
          removeEdge(backend);
        }
        // G. Add a planet
        else if (userChoice.equalsIgnoreCase("g")) {
          addPlanet(backend);
        }
        // H. Remove a planet
        else if (userChoice.equalsIgnoreCase("h")) {
          removePlanet(backend);
        } else {
          // restarts the while loop
          System.out.println("Invalid input. Please enter a valid option.");
          continue;
        }
      }
    }
    System.out.println("☾✯☾✯☾✯Thank you for using the Planet Navigator!★☽★☽★☽");
  }

  /**
   * Calls getFuelCost() method in the Backend class to find the shortest path between two planets
   * as well as to calculate its total fuel cost.
   * 
   * @param backend Backend class
   */
  public static void findShortestPath(BackEnd backend) {

    boolean run = true;
    boolean ask = true;
    String askPlanet1 = "What is the name of the planet you'd like to travel from?";
    String askPlanet2 = "What is the name of the planet you'd like to travel to?";
    while (run) {
      System.out.println(askPlanet1);
      String planet1 = scnr.next();
      System.out.println(askPlanet2);
      String planet2 = scnr.next();
      planet1 = standardize(planet1);
      planet2 = standardize(planet2);

      try {
        int fuelCost = backend.getFuelCost(planet1, planet2);
        System.out.println("It takes " + fuelCost + " fuel cost to travel from " + planet1 + " to "
            + planet2 + ".");
      } catch (NoSuchElementException nsee) {
        System.out.println("We couldn't find the fuel cost to travel from " + planet1 + " to "
            + planet2 + ". Please try again with valid planets.");
      } catch (IllegalArgumentException iae) {
        System.out.println("The planets you have entered are not existent in the graph.");
      }

      while (ask) {
        System.out.println("Would you like to search fuel costs to travel between other planets?");
        System.out.println("Enter 'y' for yes, 'n' for no.");
        String userChoice = scnr.next();
        if (userChoice.equalsIgnoreCase("y")) {
          break;
        } else if (userChoice.equalsIgnoreCase("n")) {
          run = false;
          break;
        } else {
          System.out.println("Your input is not one of the options. Please enter a valid option.");
          continue;
        }

      }
    }


  }

  /**
   * Calls getAllPlanets() method in the Backend class to print all planets in the dataset.
   * 
   * @param backend
   */
  public static void printAllPlanets(BackEnd backend) {

    System.out.println("Here are the list of all planets in the current dataset: ");
    System.out.println(backend.getAllPlanets());
  }

  /**
   * Calls getPlanetPaths() method in the Backend class to print the list of all planet paths.
   * 
   * @param backend
   */
  public static void printAllEdges(BackEnd backend) {
    // public List<Planet> getPlanetPaths(Planet planet); // returns list of all planets and the
    // fuel cost
    boolean run = true;
    boolean ask = true;
    String askPlanet = "Which planet would you like to see the list of paths for? : ";

    while (run) {
      System.out.println(askPlanet);
      String planetInput = scnr.next();
      planetInput = standardize(planetInput);
      try {
        List<Paths> paths = backend.getPlanetPaths(planetInput);
        System.out.println("Search results: ");
        System.out.println(Arrays.toString(paths.toArray()));
      } catch (IllegalArgumentException e) {

        System.out.println("This planet is not in the current planet navigator dataset.");
      }

      while (ask) {
        System.out.println("Would you like to search for other planet paths?");
        System.out.println("Enter 'y' for yes, 'n' for no.");
        String userChoice = scnr.next();
        if (userChoice.equalsIgnoreCase("y")) {
          break;
        } else if (userChoice.equalsIgnoreCase("n")) {
          run = false;
          break;
        } else {
          System.out.println("Your input is not one of the options. Please enter a valid option.");
          continue;
        }

      }
    }
  }

  /**
   * Checks if two planets are connectred by comparing the ending planets extending from the path
   * from the planet.
   * 
   * @param backend
   */
  public static void isConnected(BackEnd backend) {
    // TODO: check the output of the getPlanetPaths which returns a list of Planet
    String askFirst = "What is the FIRST planet you would like to search for?";
    String askSecond = "What is the SECOND planet you would like to search for?";

    boolean run = true;
    boolean ask = true;
    while (run) {


      try {
        System.out.println(askFirst);
        String planet1 = scnr.next();
        System.out.println(askSecond);
        String planet2 = scnr.next();
        planet1 = standardize(planet1);
        planet2 = standardize(planet2);

        List<Paths> connectedPlanets = backend.getPlanetPaths(planet1);
        for (Paths p : connectedPlanets) {
          if (p.getEnd().toString().equals("This planet is called " + planet2.toString())) {
            System.out.println(planet1 + " and " + planet2 + " are connected!");
            return;
          }
        }
        System.out.println(planet1 + " and " + planet2 + " are NOT connected.");

      } catch (IllegalArgumentException e) {
        System.out.println(
            "Either one of the provided planets or both planets are invalid. Please try agaoin.");
      }

      while (ask) {
        System.out.println("\nWould you like to look for whether other two planets are connected? "
            + "\nEnter 'y' for yes and 'n' for no");
        String userChoice = scnr.next();
        if (userChoice.equalsIgnoreCase("y")) {
          break;
        } else if (userChoice.equalsIgnoreCase("n")) {
          run = false;
          break;
        } else {
          System.out.println("Your input is not one of the options. Please enter a valid option.");
          continue;
        }
      }
    }
  }

  /**
   * Calls addPath() method in the Backend class to add a path to the specified planet. public
   * boolean addPath(String planet, int path); // adds path to specified planet
   * 
   * @param backend
   */
  public static void addEdge(BackEnd backend) {
    boolean run = true;
    boolean ask = true;

    String askPlanet =
        "What is the name of the planet you would like to add the starting edge from?: ";
    String askPathCost =
        "What is the path cost you would like to add to the corresponding planet?: ";
    String askEndPlanet =
        "What is the name of the planet you would like to add the ending edge to?: ";

    while (run) {

      try {
        System.out.println(askPlanet);
        String planet = scnr.next();
        System.out.println(askPathCost);
        int path = Integer.parseInt(scnr.next());
        System.out.println(askEndPlanet);
        String planetEnd = scnr.next();
        planet = standardize(planet);
        planetEnd = standardize(planetEnd);


        if (backend.addPath(planet, planetEnd, path)) {
          System.out.println("A new planet is successfully added to the dataset!");
        } else {
          System.out.println("OOPS something must have gone wrong. Please try again.");
        }
      } catch (Exception e) {
        System.out.println("Please enter valid planets and costs. Please try again.");
      }

      while (ask) {
        System.out.println(
            "Would you like to add another path to the dataset?\nEnter 'y' for yes and 'n' for no");
        String userChoice = scnr.next();
        if (userChoice.equalsIgnoreCase("y")) {
          break;
        } else if (userChoice.equalsIgnoreCase("n")) {
          run = false;
          break;
        } else {
          System.out.println("Your input is not one of the options. Please enter a valid option.");
          continue;
        }
      }
    }
  }

  /**
   * Calls removePath() method in the Backend class to remove all paths extending from the specified
   * planet.
   * 
   * @param backend
   */
  public static void removeEdge(BackEnd backend) {
    boolean run = true;
    boolean ask = true;

    String askPlanet =
        "What is the name of the starting planet whose path you would like to remove from this dataset?: ";
    String askEndPlanet =
        "What is the name of the ending planet whose path you would like to remove from this dataset?: ";

    while (run) {
      System.out.println(
          "CAUTION: ALL edges from the starting planet to the ending planet will be removed from the dataset in this operation.");
      System.out.println(askPlanet);
      String planet = scnr.next();
      System.out.println(askEndPlanet);
      String endPlanet = scnr.next();
      planet = standardize(planet);
      endPlanet = standardize(endPlanet);

      try {
        if (backend.removePath(planet, endPlanet)) {
          System.out.println("All paths successfully removed from the dataset!");
        } else {
          System.out.println("OOPS something must have gone wrong. Please try again.");
        }
      } catch (Exception e) {
        System.out.println("OOPS something must have gone wrong. Please try again.");
      }

      while (ask) {
        System.out.println(
            "\nWould you like to remove another path from the dataset? (enter 'y' for yes and 'n' for no):\t");
        String userChoice = scnr.next();
        if (userChoice.equalsIgnoreCase("y")) {
          break;
        } else if (userChoice.equalsIgnoreCase("n")) {
          run = false;
          break;
        } else {
          System.out.println("Your input is not one of the options. Please enter a valid option.");
          continue;
        }
      }
    }
  }

  /**
   * Calls addPlanet() method in the Backend class to add a planet to existing sets of planets.
   * public boolean addPlanet(String planet);
   * 
   * @param backend
   */
  public static void addPlanet(BackEnd backend) {
    boolean run = true;
    boolean ask = true;
    String askPlanet = "What is the name of the planet you would like to add to this dataset?: ";

    while (run) {
      System.out.println(askPlanet);
      String planet = scnr.next();
      planet = standardize(planet);

      try {
        if (backend.addPlanet(planet)) {
          System.out.println("A new planet is successfully added to the dataset!");
        } else {
          System.out.println("OOPS something must have gone wrong. Please try again.");
        }
      } catch (Exception e) {
        System.out.println("OOPS something must have gone wrong. Please try again.");
      }

      while (ask) {
        System.out.println(
            "\nWould you like to add another planet to the dataset? (enter 'y' for yes and 'n' for no):\t");
        String userChoice = scnr.next();
        if (userChoice.equalsIgnoreCase("y")) {
          break;
        } else if (userChoice.equalsIgnoreCase("n")) {
          run = false;
          break;
        } else {
          System.out.println("Your input is not one of the options. Please enter a valid option.");
          continue;
        }
      }
    }

  }

  /**
   * Calls removePlanet() method in the Backend class to remove the specified planet from the
   * dataset, including its paths public boolean removePlanet(String planet); // removes planet’s
   * paths too
   * 
   * @param backend
   */
  public static void removePlanet(BackEnd backend) {
    boolean run = true;
    boolean ask = true;
    String askPlanet =
        "What is the name of the planet you would like to remove from this dataset?: ";

    while (run) {
      System.out.println(askPlanet);
      String planet = scnr.next();
      planet = standardize(planet);

      try {
        if (backend.removePlanet(planet)) {
          System.out.println("A new planet is successfully removed from the dataset!");
        } else {
          System.out.println("OOPS something must have gone wrong. Please try again.");
        }
      } catch (Exception e) {
        System.out.println("OOPS something must have gone wrong. Please try again.");
      }

      while (ask) {
        System.out.println(
            "\nWould you like to remove another planet from the dataset? (enter 'y' for yes and 'n' for no):\t");
        String userChoice = scnr.next();
        if (userChoice.equalsIgnoreCase("y")) {
          break;
        } else if (userChoice.equalsIgnoreCase("n")) {
          run = false;
          break;
        } else {
          System.out.println("Your input is not one of the options. Please enter a valid option.");
          continue;
        }
      }
    }
  }

  /**
   * Helper method which converts all planet names to correct format (ex: jupiter => Jupiter)
   * 
   * @param planet String of inputed planet name
   * @return String of correctly formatted planet name
   */
  private static String standardize(String planet) {
    if (planet.length() > 1) {
      String first = planet.substring(0, 1);
      String rest = planet.substring(1);

      return first.toUpperCase() + rest.toLowerCase();
    } else {
      return planet.toUpperCase();
    }
  }



}