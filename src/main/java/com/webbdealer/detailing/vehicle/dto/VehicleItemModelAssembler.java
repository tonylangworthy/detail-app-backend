package com.webbdealer.detailing.vehicle.dto;

import com.webbdealer.detailing.vehicle.VehicleController;
import com.webbdealer.detailing.vehicle.dao.Vehicle;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class VehicleItemModelAssembler extends RepresentationModelAssemblerSupport<VehicleResponse, VehicleItemModel> {


    public VehicleItemModelAssembler() {
        super(VehicleController.class, VehicleItemModel.class);
    }

    @Override
    public VehicleItemModel toModel(VehicleResponse vehicle) {
        VehicleItemModel vehicleItem = new VehicleItemModel();
        vehicleItem.setId(vehicle.getId());
        vehicleItem.setCatalogId(vehicle.getCatalogId());
        vehicleItem.setVin(vehicle.getVin());
        vehicleItem.setYear(vehicle.getYear());
        vehicleItem.setMake(vehicle.getMake());
        vehicleItem.setModel(vehicle.getModel());
        vehicleItem.setTrim(vehicle.getTrim());
        vehicleItem.setColor(vehicle.getColor());
        vehicleItem.setArrivedAt(vehicle.getArrivedAt());
        return vehicleItem;
    }


}
