package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class SessionDTO {
    int id;
    String sessionId;
    int userId;

    @JsonCreator
    public SessionDTO(@JsonProperty("id") int id,
                      @JsonProperty("sessionId") String sessionId,
                      @JsonProperty("userId") int userId) {
        this.id = id;
        this.sessionId = sessionId;
        this.userId = userId;
    }
}
