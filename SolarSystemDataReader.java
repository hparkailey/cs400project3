
// --== CS400 File Header Information ==--
// Name: Gabriela Setyawan
// Email: gsetyawan@wisc.edu
// Team: Blue
// Role: Data Wrangler
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: N/A
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * SolarSystemDataReader reads a csv file containing all the planets and paths
 *
 *
 * @author Gabriela Setyawan
 */
public class SolarSystemDataReader {
	/**
	 * Reads the inputed Reader and converts the csv file entries into a list of
	 * Planet Objects
	 * 
	 * @param inputFileReader Reader of csv file containing strings of planets
	 *                        (vertex) and paths (edges)
	 * @return List<Planets> which is the list of planetNames contained in csv file
	 * @throws FileNotFoundException if the csv file is not found
	 * @throws IOException           if there an error when reading the file/sending
	 *                               the output to the file
	 */
	public List<Planets> readPlanetNames(Reader inputFileReader) throws FileNotFoundException, IOException {

		try {
			List<Planets> planetNames = new ArrayList<Planets>(); // list of Planet Objects to be returned
			BufferedReader reader = new BufferedReader(inputFileReader);
			String row = reader.readLine(); // reads every line of the csv file after the header
//	      int planetRadius;
//	      int planetMeanTemp;
			while (row != null) {
				String[] planetEntry = row.split(","); // planetEntry[] = {Planet, <PlanetName>}
				if (planetEntry[0].equals("Planet")) { // checks if the first column of the data entry equals to Planet,
														// if so add it to planetNames list
//	    	   planetRadius = Integer.parseInt(planetEntry[2]);
//	    	   planetMeanTemp = Integer.parseInt(planetEntry[3]);
					planetNames.add(new Planets(planetEntry[1]));
				}
				row = reader.readLine();
			}
			reader.close();
			return planetNames;
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (IOException ioe) {
			throw ioe;
		}
	}

	/**
	 * Reads the inputed Reader and converts the csv file entries into a list of
	 * Paths objects
	 * 
	 * @param inputFileReader Reader of csv file containing strings of planets
	 *                        (vertex) and paths (edges)
	 * @return List<Paths> which is the list of edges contained in csv file
	 * @throws FileNotFoundException if the csv file is not found
	 * @throws IOException           if there an error when reading the file/sending
	 *                               the output to the file
	 */
	public List<Paths> readPaths(Reader inputFileReader) throws FileNotFoundException, IOException {

		try {
			List<Paths> pathToPlanets = new ArrayList<Paths>(); // list of paths objects to be returned
			BufferedReader reader = new BufferedReader(inputFileReader);
			String row = reader.readLine(); // reads every line of the csv file after the header
			Planets planetStart;
			Planets targetPlanet;
			int fuel;
			while (row != null) {
				String[] pathEntry = row.split(","); // pathEntry = {Edge, planetStart, targetPlanet, fuel}
				if (pathEntry[0].equals("Edge")) { // checks if the first column of the data entry equals to Edge, if
													// so add it to pathToPlanets list
					planetStart = new Planets(pathEntry[1]); 
					targetPlanet = new Planets(pathEntry[2]);
					fuel = Integer.parseInt(pathEntry[3]);
					pathToPlanets.add(new Paths(planetStart, targetPlanet, fuel)); // add a Paths object to the list of pathToPlanets
				}
				
				row = reader.readLine();
			}
			reader.close();
			return pathToPlanets;
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (IOException ioe) {
			throw ioe;
		}
	}
	
	public List<String> read(Reader inputFileReader) throws FileNotFoundException, IOException {
		try {
			List<String> path = new ArrayList<String>(); // list of paths objects to be returned
			BufferedReader reader = new BufferedReader(inputFileReader);
			String row = reader.readLine(); // reads every line of the csv file after the header
			String toAdd = "";
			
			while (row != null) {
				String[] pathEntry = row.split(","); // pathEntry = {Edge, planetStart, targetPlanet, fuel}
				if (pathEntry[0].equals("Edge")) { // checks if the first column of the data entry equals to Edge, if
													// so add it to pathToPlanets list
					toAdd = pathEntry[0]+ ", " +  pathEntry[1] + ", " + pathEntry[2] + ", " + pathEntry[3];
				}
				
				if (pathEntry[0].equals("Planet")) {
					toAdd = pathEntry[0] + ", " + pathEntry[1]; 
				}
				path.add(toAdd);
				
				row = reader.readLine();
			}
			reader.close();
			return path;
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (IOException ioe) {
			throw ioe;
		}
	}

}