package org.nastya.dto;

public class UpdateBookQuantityDTO {
    private Integer bookId;
    private Integer amountToAdd;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return amountToAdd;
    }

    public void setQuantity(Integer quantity) {
        this.amountToAdd = quantity;
    }
}
