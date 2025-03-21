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

    public Integer getAmountToAdd() {
        return amountToAdd;
    }

    public void setAmountToAdd(Integer amountToAdd) {
        this.amountToAdd = amountToAdd;
    }
}
