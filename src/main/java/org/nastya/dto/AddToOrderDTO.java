package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class AddToOrderDTO {
    Integer bookId;

    @JsonCreator
    public AddToOrderDTO(@JsonProperty("bookId") Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getBookId() {
        return bookId;
    }
}
