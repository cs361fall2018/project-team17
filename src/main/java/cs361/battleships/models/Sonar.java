package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Sonar {

    @JsonProperty private SonarStatus result;
    @JsonProperty private Square location;

    /*unused*/
    public Sonar() {
    }

    public Sonar(Square location) {
        result = SonarStatus.VISIBLE;
        this.location = location;
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
