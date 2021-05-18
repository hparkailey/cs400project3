// --== CS400 File Header Information ==--
// Name: Gabriela Setyawan
// Email: gsetyawan@wisc.edu
// Team: Blue
// Role: Data Wrangler
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: N/A
/**
 * Planets represent a data in the vertices in the Solar System Graph that contains the name of the planet, diameter and meanTemperature
 * @author Gabriela Setyawan 
 *
 */
public class Planets {
private String name;

    /**
     * Constructor for Planets object
     * @param name String that represents name of the planet
     */
    public Planets(String name) {
        this.name = name;
        
    }
    
    /**
     * Getter method for the name field of the planet
     * @return String that represents the name of the planet
     */
    public String getName() {
        return this.name;
    }
    

    /**
     * Get the details of the planet as a string object
     * @return String that contains the information about a certain planet
     */
    public String toString() {
        return "This planet is called " + this.name;
    }
    
}