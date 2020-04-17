package com.nickharder88.parkabl;

import com.nickharder88.parkabl.data.dto.VehicleDTO;
import com.nickharder88.parkabl.data.model.Vehicle;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void create_vehicle() {
        String id = "01";
        VehicleDTO data = new VehicleDTO();
        data.make = "Toyota";
        data.model = "Corolla";
        data.licensePlateNum = "1234";
        data.state = "OH";
        Vehicle v = new Vehicle(id, data);
        assertEquals("01", v.id);
        assertEquals("Toyota", v.data.make);
        assertEquals("Corolla", v.data.model);
        assertEquals("1234", v.data.licensePlateNum);
        assertEquals("OH", v.data.state);
    }
}