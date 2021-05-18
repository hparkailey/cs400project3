// --== CS400 File Header Information ==--
// Name: Hailey Park
// Email: hpark353@wisc.edu
// Team: FB blue
// Role: Frontend
// TA: Daniel Finer
// Lecturer: Gary
// Notes to Grader:

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.zip.DataFormatException;

/**
 * This class tests whether methods in Frontend.java are working as expected.
 * 
 * @author Hailey
 *
 */
public class FrontendTester {

  String greeting = "☆☆☆☆☆☆☆WELCOME TO THE PLANET NAVIGATOR☆☆☆☆☆☆"
      + "\n\nThis program lets you explore planets in our solar system.\n";

  String enterFile =
      "This is the FILE SCREEN.\nWe need you to provide a planet dataset you wish to explore.\n"
          + "Enter 'x' to quit, 'f' to enter a filepath, or 's' to enter a string:\n";

  String optionF = "Please enter the filepath to the planet explorer csv file:\t";

  String modeOptions = "This is the MAIN SCREEN.\nThere are 6 options to choose from in this app.\n"
      + "Enter the letter corresponding to the mode you'd like to go to."
      + "\n\tA. Find the shortest path between two planets and calculate the total fuel cost"
      + "\n\tB. Print a list of all the planets"
      + "\n\tC. Print a list of all edges connecting planets"
      + "\n\tD. Check if two planets are connected" + "\n\tE. Add an edge between planets"
      + "\n\tF. Remove an edge between planets" + "\n\tG. Add a new planet"
      + "\n\tH. Remove an existing planet"
      + "\nEnter 'r' to go back to the file screen and 'x' to quit.\n";

  String promptChar = "Please enter a letter: ";

  String ty = "☾✯☾✯☾✯Thank you for using the Planet Navigator!★☽★☽★☽";

  @Test
  /**
   * Tests whether findShortestPath() method is working as expected.
   */
  public void testFindMST() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      String input = "f" + System.lineSeparator() + "solarsystem.csv" + System.lineSeparator() + "a"
          + System.lineSeparator() + "Earth" + System.lineSeparator() + "Sun"
          + System.lineSeparator() + "n" + System.lineSeparator() + "x";
      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());

      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      // set the output to the stream captor to read the output of the front end
      System.setOut(new PrintStream(outputStreamCaptor));
      Frontend.run(new BackEnd(new FileReader("solarsystem.csv")));
      // set the output back to standard out for running the test
      System.setOut(standardOut);
      // same for standard in
      System.setIn(standardIn);

      String appOutput = new String(outputStreamCaptor.toByteArray());
      // System.out.println(appOutput);

      String expected = greeting + "\n" + enterFile + "\n" + optionF + "\n" + modeOptions + "\n"
          + promptChar + "\n" + "What is the name of the planet you'd like to travel from?\n"
          + "What is the name of the planet you'd like to travel to?\n"
          + "It takes 1000 fuel cost to travel from Earth to Sun.\n"
          + "Would you like to search fuel costs to travel between other planets?\n"
          + "Enter 'y' for yes, 'n' for no.\n" + modeOptions + "\n" + promptChar + "\n" + ty + "\n";

      assertEquals(expected, appOutput);

    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
      // test failed
      fail("testFindMST() failed");
    }

  }

  @Test
  /**
   * Tests whether addPlanet() is working as expected.
   */
  public void testCreateNewPlanet() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      String input = "f" + System.lineSeparator() + "solarsystem.csv" + System.lineSeparator() + "g"
          + System.lineSeparator() + "potatopotato" + System.lineSeparator() + "n"
          + System.lineSeparator() + "x";

      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());

      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));
      Frontend.run(new BackEnd(new FileReader("solarsystem.csv")));
      System.setOut(standardOut);
      System.setIn(standardIn);
      String appOutput = new String(outputStreamCaptor.toByteArray());

      String expected = greeting + "\n" + enterFile + "\n" + optionF + "\n" + modeOptions + "\n"
          + promptChar + "\n"
          + "What is the name of the planet you would like to add to this dataset?: " + "\n"
          + "A new planet is successfully added to the dataset!" + "\n"
          + "\nWould you like to add another planet to the dataset? (enter 'y' for yes and 'n' for no):\t"
          + "\n" + modeOptions + "\n" + promptChar + "\n" + ty + "\n";

      assertEquals(expected, appOutput);

    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
      // test failed
      fail("testCreateNewPlanet() failed");
    }
  }

  @Test
  /**
   * Tests whether addEdge() is working as expected
   */
  public void testCreateNewPath() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      String input = "f" + System.lineSeparator() + "solarsystem.csv" + System.lineSeparator() + "e"
          + System.lineSeparator() + "Sun" + System.lineSeparator() + "8239"
          + System.lineSeparator() + "Uranus" + System.lineSeparator() + "n"
          + System.lineSeparator() + "x";

      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));
      Frontend.run(new BackEnd(new FileReader("solarsystem.csv")));
      System.setOut(standardOut);
      System.setIn(standardIn);
      String appOutput = new String(outputStreamCaptor.toByteArray());

      String expected = greeting + "\n" + enterFile + "\n" + optionF + "\n" + modeOptions + "\n"
          + promptChar + "\n"
          + "What is the name of the planet you would like to add the starting edge from?: " + "\n"
          + "What is the path cost you would like to add to the corresponding planet?: " + "\n"
          + "What is the name of the planet you would like to add the ending edge to?: " + "\n"
          + "A new planet is successfully added to the dataset!" + "\n"
          + "Would you like to add another path to the dataset?\nEnter 'y' for yes and 'n' for no"
          + "\n" + modeOptions + "\n" + promptChar + "\n" + ty + "\n";

      assertEquals(expected, appOutput);

    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
      // test failed
      fail("testCreateNewPlanet() failed");
    }
  }

  @Test
  /**
   * Tests whether printAllEdges() is working as expected
   */
  public void testDisplayEdges() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      String input = "f" + System.lineSeparator() + "solarsystem.csv" + System.lineSeparator() + "c"
          + System.lineSeparator() + "Mercury" + System.lineSeparator() + "y"
          + System.lineSeparator() + "Sun" + System.lineSeparator() + "n" + System.lineSeparator()
          + "x";

      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));
      Frontend.run(new BackEnd(new FileReader("solarsystem.csv")));
      System.setOut(standardOut);
      System.setIn(standardIn);
      String appOutput = new String(outputStreamCaptor.toByteArray());

      String expected = greeting + "\n" + enterFile + "\n" + optionF + "\n" + modeOptions + "\n"
          + promptChar + "\n" + "Which planet would you like to see the list of paths for? : "
          + "\n" + "Search results: " + "\n" + "[Mercury takes 400 to go to Sun]" + "\n"
          + "Would you like to search for other planet paths?" + "\n"
          + "Enter 'y' for yes, 'n' for no." + "\n"
          + "Which planet would you like to see the list of paths for? : " + "\n"
          + "Search results: " + "\n"
          + "[Sun takes 400 to go to Mercury, Sun takes 700 to go to Venus, "
          + "Sun takes 1000 to go to Earth, Sun takes 1500 to go to Mars, Sun takes 1500 to go to"
          + " Mars, Sun takes 30100 to go to Neptune]" + "\n"
          + "Would you like to search for other planet paths?" + "\n"
          + "Enter 'y' for yes, 'n' for no." + "\n" + modeOptions + "\n" + promptChar + "\n" + ty
          + "\n";
      assertEquals(expected, appOutput);

    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
      // test failed
      fail("testDisplayEdges() failed");
    }
  }

  @Test
  /**
   * Tests whether isConnected() is working as expected
   */
  public void testIsConnected() {
    PrintStream standardOut = System.out;
    InputStream standardIn = System.in;
    try {
      String input = "f" + System.lineSeparator() + "solarsystem.csv" + System.lineSeparator() + "d"
          + System.lineSeparator() + "Earth" + System.lineSeparator() + "Sun"
          + System.lineSeparator() + "x";

      InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
      System.setIn(inputStreamSimulator);
      ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStreamCaptor));
      Frontend.run(new BackEnd(new FileReader("solarsystem.csv")));
      System.setOut(standardOut);
      System.setIn(standardIn);
      String appOutput = new String(outputStreamCaptor.toByteArray());

      String expected = greeting + "\n" + enterFile + "\n" + optionF + "\n" + modeOptions + "\n"
          + promptChar + "\n" + "What is the FIRST planet you would like to search for?" + "\n"
          + "What is the SECOND planet you would like to search for?" + "\n"
          + "Earth and Sun are connected!" + "\n" + modeOptions + "\n" + promptChar + "\n" + ty
          + "\n";
      assertEquals(expected, appOutput);

    } catch (Exception e) {
      // make sure stdin and stdout are set correctly after we get exception in test
      System.setOut(standardOut);
      System.setIn(standardIn);
      e.printStackTrace();
      // test failed
      fail("testIsConnected() failed");
    }
  }



}
