package com.freshermanage.model.dto;

public class CenterFresherCountDTO {
    private String nameCenter;
    private long quantityFresher;

    public CenterFresherCountDTO(String nameCenter, long quantityFresher) {
        this.nameCenter = nameCenter;
        this.quantityFresher = quantityFresher;
    }

    public String getNameCenter() {
        return nameCenter;
    }

    public void setNameCenter(String nameCenter) {
        this.nameCenter = nameCenter;
    }

    public long getQuantityFresher() {
        return quantityFresher;
    }

    public void setQuantityFresher(long quantityFresher) {
        this.quantityFresher = quantityFresher;
    }
}
