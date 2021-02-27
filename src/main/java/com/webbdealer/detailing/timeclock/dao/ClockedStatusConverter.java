package com.webbdealer.detailing.timeclock.dao;

import javax.persistence.AttributeConverter;

public class ClockedStatusConverter implements AttributeConverter<ClockedStatus, String> {
    @Override
    public String convertToDatabaseColumn(ClockedStatus clockedStatus) {
        return null;
    }

    @Override
    public ClockedStatus convertToEntityAttribute(String dbData) {
        return null;
    }
}
