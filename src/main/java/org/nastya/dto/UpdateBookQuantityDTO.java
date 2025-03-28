package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UpdateBookQuantityDTO {
    Integer bookId;
    Integer amountToAdd;

    public UpdateBookQuantityDTO(@JsonProperty("bookId") Integer bookId,
                                 @JsonProperty("amountToAdd") Integer amountToAdd) {
        this.bookId = bookId;
        this.amountToAdd = amountToAdd;
    }
}
