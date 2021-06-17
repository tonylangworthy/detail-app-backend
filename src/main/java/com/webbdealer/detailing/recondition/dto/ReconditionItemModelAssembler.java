package com.webbdealer.detailing.recondition.dto;

import com.webbdealer.detailing.recondition.ReconditionController;
import com.webbdealer.detailing.recondition.dao.Recondition;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ReconditionItemModelAssembler extends RepresentationModelAssemblerSupport<Recondition, ReconditionItemModel> {


    public ReconditionItemModelAssembler() {
        super(ReconditionController.class, ReconditionItemModel.class);
    }

    @Override
    public ReconditionItemModel toModel(Recondition recondition) {
        ReconditionItemModel reconditionItem = new ReconditionItemModel();
        reconditionItem.setId(recondition.getId());
        reconditionItem.setName(recondition.getName());
        reconditionItem.setPrice(recondition.getPrice().toString());
        return reconditionItem;
    }
}
