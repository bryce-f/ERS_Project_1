package com.revature.dto;

import java.util.Objects;

public class ResponseReimbursementDTO {
    private int reimbId;
    private double reimbAmount;
    private String reimbSubmitted;
    private String reimbResolved;
    private String reimbDesc;
    private int reimbAuthorId;
    private int reimbResolver;
    private String reimbStatus;
    private String reimbType;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String receiptUrl;
    private String resolverFirst;
    private String resolverLast;

    public ResponseReimbursementDTO() {
    }

    public ResponseReimbursementDTO(int reimbId, double reimbAmount, String reimbSubmitted, String reimbResolved, String reimbDesc, int reimbAuthorId, int reimbResolver, String reimbStatus, String reimbType, String firstName, String lastName, String userEmail, String receiptUrl, String resolverFirst, String resolverLast) {
        this.reimbId = reimbId;
        this.reimbAmount = reimbAmount;
        this.reimbSubmitted = reimbSubmitted;
        this.reimbResolved = reimbResolved;
        this.reimbDesc = reimbDesc;
        this.reimbAuthorId = reimbAuthorId;
        this.reimbResolver = reimbResolver;
        this.reimbStatus = reimbStatus;
        this.reimbType = reimbType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.receiptUrl = receiptUrl;
        this.resolverFirst = resolverFirst;
        this.resolverLast = resolverLast;
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

    public String getReimbDesc() {
        return reimbDesc;
    }

    public void setReimbDesc(String reimbDesc) {
        this.reimbDesc = reimbDesc;
    }

    public int getReimbAuthorId() {
        return reimbAuthorId;
    }

    public void setReimbAuthorId(int reimbAuthorId) {
        this.reimbAuthorId = reimbAuthorId;
    }

    public int getReimbResolver() {
        return reimbResolver;
    }

    public void setReimbResolver(int reimbResolver) {
        this.reimbResolver = reimbResolver;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public String getResolverFirst() {
        return resolverFirst;
    }

    public void setResolverFirst(String resolverFirst) {
        this.resolverFirst = resolverFirst;
    }

    public String getResolverLast() {
        return resolverLast;
    }

    public void setResolverLast(String resolverLast) {
        this.resolverLast = resolverLast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseReimbursementDTO that = (ResponseReimbursementDTO) o;
        return reimbId == that.reimbId && Double.compare(that.reimbAmount, reimbAmount) == 0 && reimbAuthorId == that.reimbAuthorId && reimbResolver == that.reimbResolver && Objects.equals(reimbSubmitted, that.reimbSubmitted) && Objects.equals(reimbResolved, that.reimbResolved) && Objects.equals(reimbDesc, that.reimbDesc) && Objects.equals(reimbStatus, that.reimbStatus) && Objects.equals(reimbType, that.reimbType) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(userEmail, that.userEmail) && Objects.equals(receiptUrl, that.receiptUrl) && Objects.equals(resolverFirst, that.resolverFirst) && Objects.equals(resolverLast, that.resolverLast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbId, reimbAmount, reimbSubmitted, reimbResolved, reimbDesc, reimbAuthorId, reimbResolver, reimbStatus, reimbType, firstName, lastName, userEmail, receiptUrl, resolverFirst, resolverLast);
    }

    @Override
    public String toString() {
        return "ResponseReimbursementDTO{" +
                "reimbId=" + reimbId +
                ", reimbAmount=" + reimbAmount +
                ", reimbSubmitted='" + reimbSubmitted + '\'' +
                ", reimbResolved='" + reimbResolved + '\'' +
                ", reimbDesc='" + reimbDesc + '\'' +
                ", reimbAuthorId=" + reimbAuthorId +
                ", reimbResolver=" + reimbResolver +
                ", reimbStatus='" + reimbStatus + '\'' +
                ", reimbType='" + reimbType + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", receiptUrl='" + receiptUrl + '\'' +
                ", resolverFirst='" + resolverFirst + '\'' +
                ", resolverLast='" + resolverLast + '\'' +
                '}';
    }
}


