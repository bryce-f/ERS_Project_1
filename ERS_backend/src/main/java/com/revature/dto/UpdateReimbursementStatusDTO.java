package com.revature.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class UpdateReimbursementStatusDTO {

    private String resolver;
    private int resolverId;
    private int statusId;
    private Timestamp resolveDate;

    public UpdateReimbursementStatusDTO() {
    }

    public UpdateReimbursementStatusDTO(String resolver, int resolverId, int statusId, Timestamp resolveDate) {
        this.resolver = resolver;
        this.resolverId = resolverId;
        this.statusId = statusId;
        this.resolveDate = resolveDate;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public int getResolverId() {
        return resolverId;
    }

    public void setResolverId(int resolverId) {
        this.resolverId = resolverId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Timestamp getResolveDate() {
        return resolveDate;
    }

    public void setResolveDate(Timestamp resolveDate) {
        this.resolveDate = resolveDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateReimbursementStatusDTO that = (UpdateReimbursementStatusDTO) o;
        return resolverId == that.resolverId && statusId == that.statusId && Objects.equals(resolver, that.resolver) && Objects.equals(resolveDate, that.resolveDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resolver, resolverId, statusId, resolveDate);
    }

    @Override
    public String toString() {
        return "UpdateReimbursementStatusDTO{" +
                "resolver='" + resolver + '\'' +
                ", resolverId=" + resolverId +
                ", statusId=" + statusId +
                ", resolveDate=" + resolveDate +
                '}';
    }
}