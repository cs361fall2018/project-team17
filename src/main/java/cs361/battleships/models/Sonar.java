package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sonar {

    @JsonProperty private SonarStatus result;
    @JsonProperty private Square location;
    @JsonProperty private boolean center;

    /*unused*/
    public Sonar() {}

    public Sonar(Square location, boolean c) {
        result = SonarStatus.VISIBLE;
        this.location = location;
        center = c;
    }

    public SonarStatus getResult() {
        return result;
    }

    public void setResult(SonarStatus result) {
        this.result = result;
    }

    public Square getLocation() {
        return location;
    }
}
