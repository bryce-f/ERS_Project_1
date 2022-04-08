package com.revature.model;

import java.io.InputStream;
import java.util.Objects;

public class Reimbursement {

    private int reimbId;
    private double reimbAmount;
    private String reimbStatus;
    private String reimbType;
    private String reimbDesc;
    private String reimbSubmitted;
    private String reimbResolved;
    private int reimbResolver;
    private int reimbAuthor;
    private String firstName;
    private String lastName;
    private String email;
    private String reimbReceipt;

    public Reimbursement(int reimbId, double reimbAmount, String reimbStatus, String reimbType, String reimbDesc, String reimbSubmitted,
                         String reimbResolved, int reimbResolver, int reimbAuthor, String firstName, String lastName,
                         String email, String reimbReceipt) {
        this.reimbId = reimbId;
        this.reimbAmount = reimbAmount;
        this.reimbStatus = reimbStatus;
        this.reimbType = reimbType;
        this.reimbDesc = reimbDesc;
        this.reimbSubmitted = reimbSubmitted;
        this.reimbResolved = reimbResolved;
        this.reimbResolver = reimbResolver;
        this.reimbAuthor = reimbAuthor;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.reimbReceipt = reimbReceipt;
    }

    public int getReimbId() {
        return reimbId;
    }

    public void setReimbId(int reimbId) {
        this.reimbId = reimbId;
    }

    public double getReimbAmount() {
        return reimbAmount;
    }

    public void setReimbAmount(double reimbAmount) {
        this.reimbAmount = reimbAmount;
    }

    public String getReimbStatus() {
        return reimbStatus;
    }

    public void setReimbStatus(String reimbStatus) {
        this.reimbStatus = reimbStatus;
    }

    public String getReimbType() {
        return reimbType;
    }

    public void setReimbType(String reimbType) {
        this.reimbType = reimbType;
    }

    public String getReimbDesc() {
        return reimbDesc;
    }

    public void setReimbDesc(String reimbDesc) {
        this.reimbDesc = reimbDesc;
    }

    public String getReimbSubmitted() {
        return reimbSubmitted;
    }

    public void setReimbSubmitted(String reimbSubmitted) {
        this.reimbSubmitted = reimbSubmitted;
    }

    public String getReimbResolved() {
        return reimbResolved;
    }

    public void setReimbResolved(String reimbResolved) {
        this.reimbResolved = reimbResolved;
    }

    public int getReimbResolver() {
        return reimbResolver;
    }

    public void setReimbResolver(int reimbResolver) {
        this.reimbResolver = reimbResolver;
    }

    public int getReimbAuthor() {
        return reimbAuthor;
    }

    public void setReimbAuthor(int reimbAuthor) {
        this.reimbAuthor = reimbAuthor;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReimbReceipt() {
        return reimbReceipt;
    }

    public void setReimbReceipt(String reimbReceipt) {
        this.reimbReceipt = reimbReceipt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement that = (Reimbursement) o;
        return reimbId == that.reimbId && reimbAuthor == that.reimbAuthor && Objects.equals(reimbType, that.reimbType) && Objects.equals(reimbSubmitted, that.reimbSubmitted) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbId, reimbType, reimbSubmitted, reimbAuthor, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbId=" + reimbId +
                ", reimbAmount=" + reimbAmount +
                ", reimbStatus='" + reimbStatus + '\'' +
                ", reimbType='" + reimbType + '\'' +
                ", reimbDesc='" + reimbDesc + '\'' +
                ", reimbSubmitted='" + reimbSubmitted + '\'' +
                ", reimbResolved='" + reimbResolved + '\'' +
                ", reimbResolver=" + reimbResolver +
                ", reimbAuthor=" + reimbAuthor +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", reimbReceipt=" + reimbReceipt +
                '}';
    }
}
