package com.webbdealer.detailing.vehicle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class VehicleServiceUnitTests {

    @Autowired
    private VehicleLookupService vehicleLookupService;

    @Test
    public void commaSeparatedIdList_Test() {

        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        idList.add(4L);
        idList.add(6L);
        idList.add(8L);
        idList.add(3L);
        String commaSeparatedList = vehicleLookupService.commaSeparatedIdList(idList);

        assertEquals("1,4,6,8,3", commaSeparatedList);
    }
}
