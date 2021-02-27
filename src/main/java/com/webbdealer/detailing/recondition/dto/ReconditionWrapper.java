package com.webbdealer.detailing.recondition.dto;

import java.util.ArrayList;
import java.util.List;

public class ReconditionWrapper {

    private List<ReconditionCreateForm> services = new ArrayList<>();

    public List<ReconditionCreateForm> getServices() {
        return services;
    }

    public void setServices(List<ReconditionCreateForm> services) {
        this.services = services;
    }
}
