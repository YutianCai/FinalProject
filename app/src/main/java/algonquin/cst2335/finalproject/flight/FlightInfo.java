package algonquin.cst2335.finalproject.flight;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FlightInfo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "destination")
    private String destination;

    @ColumnInfo(name = "terminal")
    private String terminal;

    @ColumnInfo(name = "gate")
    private String gate;

    @ColumnInfo(name = "delay")
    private String delay;

    public FlightInfo() {
    }

    public FlightInfo(String destination, String terminal, String gate, String delay) {
        this.destination = destination;
        this.terminal = terminal;
        this.gate = gate;
        this.delay = delay;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }
}
