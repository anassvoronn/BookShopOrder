package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class AddToOrderDTO {
    Integer bookId;

    public AddToOrderDTO(@JsonProperty("bookId") Integer bookId) {
        this.bookId = bookId;
    }
}
