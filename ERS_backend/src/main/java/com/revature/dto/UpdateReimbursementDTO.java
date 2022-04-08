package com.revature.dto;

import io.javalin.http.UploadedFile;

import java.util.Objects;

public class UpdateReimbursementDTO {
    private double amount;
    private String desc;
    private UploadedFile receipt;
    private int typeId;
    private String type;

    public UpdateReimbursementDTO(){}

    public UpdateReimbursementDTO(double amount, String desc, UploadedFile receipt, int typeId, String type) {
        this.amount = amount;
        this.desc = desc;
        this.receipt = receipt;
        this.typeId = typeId;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public UploadedFile getReceipt() {
        return receipt;
    }

    public void setReceipt(UploadedFile receipt) {
        this.receipt = receipt;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateReimbursementDTO that = (UpdateReimbursementDTO) o;
        return Double.compare(that.amount, amount) == 0 && typeId == that.typeId && Objects.equals(desc, that.desc) && Objects.equals(receipt, that.receipt) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, desc, receipt, typeId, type);
    }

    @Override
    public String toString() {
        return "UpdateReimbursementDTO{" +
                "amount=" + amount +
                ", desc='" + desc + '\'' +
                ", receipt=" + receipt +
                ", typeId=" + typeId +
                ", type='" + type + '\'' +
                '}';
    }
}
