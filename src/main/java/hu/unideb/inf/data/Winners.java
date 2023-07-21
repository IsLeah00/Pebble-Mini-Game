package hu.unideb.inf.data;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * The Winners class represents a winner in a game.
 * It is used for XML serialization and deserialization of winner data.
 */
@XmlRootElement(name = "Winners")
public class Winners {
    private String name;

    /**
     * Default constructor for the Winners class.
     * Creates an empty Winners object.
     */
    public Winners() {
    }

    /**
     * Constructor for the Winners class.
     * Creates a Winners object with the specified name.
     * @param name the name of the winner
     */
    public Winners(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the winner.
     * @return the name of the winner
     */
    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the winner.
     * @param name the name of the winner
     */
    public void setName(String name) {
        this.name = name;
    }
}
