package algonquin.cst2335.finalproject.flight;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The DTO class for flight.
 */
@Entity
public class FlightInfo {
    /**
     * The id of this FlightInfo object.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    /**
     * The destination airport of this flight
     */
    @ColumnInfo(name = "destination")
    private String destination;

    /**
     * The terminal of departure airport
     */
    @ColumnInfo(name = "terminal")
    private String terminal;

    /**
     * The gate of departure airport
     */
    @ColumnInfo(name = "gate")
    private String gate;

    /**
     * The delay minutes of this flight
     */
    @ColumnInfo(name = "delay")
    private String delay;

    /**
     * No argument constructor.
     */
    public FlightInfo() {
    }

    /**
     * Parameterized constructor.
     * @param destination the destination airport
     * @param terminal the terminal of departure airport
     * @param gate the gate of departure airport
     * @param delay the delay minutes of this flight
     */
    public FlightInfo(String destination, String terminal, String gate, String delay) {
        this.destination = destination;
        this.terminal = terminal;
        this.gate = gate;
        this.delay = delay;
    }

    /**
     * Returns the id of this FlightInfo object.
     * @return the id of this FlightInfo object.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of this FlightInfo object.
     * @param id the id of this FlightInfo object.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the destination airport of this flight
     * @return the destination airport of this flight
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the destination airport of this flight
     * @param destination the destination airport of this flight
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Returns the terminal of departure airport.
     * @return the terminal of departure airport
     */
    public String getTerminal() {
        return terminal;
    }

    /**
     * Sets the terminal of departure airport.
     * @param terminal the terminal of departure airport
     */
    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    /**
     * Returns the gate of departure airport
     * @return the gate of departure airport
     */
    public String getGate() {
        return gate;
    }

    /**
     * Sets the gate of departure airport
     * @param gate the gate of departure airport
     */
    public void setGate(String gate) {
        this.gate = gate;
    }

    /**
     * Returns the delay minutes of this flight
     * @return the delay minutes of this flight
     */
    public String getDelay() {
        return delay;
    }

    /**
     * Sets the delay minutes of this flight
     * @param delay the delay minutes of this flight
     */
    public void setDelay(String delay) {
        this.delay = delay;
    }
}
