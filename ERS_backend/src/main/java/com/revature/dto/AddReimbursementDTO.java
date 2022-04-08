package com.revature.dto;

import io.javalin.http.UploadedFile;

import java.io.InputStream;

import java.sql.Timestamp;

public class AddReimbursementDTO {
    private int reimbAuthor;
    private int reimbType;
    private double reimbAmount;
    private Timestamp reimbSubmitted;
    private String reimbDescription;
    private UploadedFile reimbReceipt;

    public AddReimbursementDTO() {

    }

    public AddReimbursementDTO(int reimbAuthor, int reimbType, double reimbAmount, Timestamp reimbSubmitted, String reimbDescription,
                               UploadedFile reimbReceipt) {
        this.reimbAuthor = reimbAuthor;
        this.reimbType = reimbType;
        this.reimbAmount = reimbAmount;
        this.reimbSubmitted = reimbSubmitted;
        this.reimbDescription = reimbDescription;
        this.reimbReceipt = reimbReceipt;
    }

    public int getReimbAuthor() {
        return reimbAuthor;
    }

    public void setReimbAuthor(int reimbAuthor) {
        this.reimbAuthor = reimbAuthor;
    }

    public int getReimbType() {
        return reimbType;
    }

    public void setReimbType(int reimbType) {
        this.reimbType = reimbType;
    }

    public double getReimbAmount() {
        return reimbAmount;
    }

    public void setReimbAmount(double reimbAmount) {
        this.reimbAmount = reimbAmount;
    }

    public Timestamp getReimbSubmitted() {
        return reimbSubmitted;
    }

    public void setReimbSubmitted(Timestamp reimbSubmitted) {
        this.reimbSubmitted = reimbSubmitted;
    }

    public String getReimbDescription() {
        return reimbDescription;
    }

    public void setReimbDescription(String reimbDescription) {
        this.reimbDescription = reimbDescription;
    }

    public UploadedFile getReimbReceipt() {
        return reimbReceipt;
    }

    public void setReimbReceipt(UploadedFile reimbReceipt) {
        this.reimbReceipt = reimbReceipt;
    }
}
