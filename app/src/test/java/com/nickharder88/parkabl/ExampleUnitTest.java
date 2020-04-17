package com.nickharder88.parkabl;

import com.nickharder88.parkabl.data.dto.AddressDTO;
import com.nickharder88.parkabl.data.dto.LandlordDTO;
import com.nickharder88.parkabl.data.dto.PropertyDTO;
import com.nickharder88.parkabl.data.dto.TenantDTO;
import com.nickharder88.parkabl.data.model.Address;
import com.nickharder88.parkabl.data.model.Landlord;
import com.nickharder88.parkabl.data.model.Property;
import com.nickharder88.parkabl.data.model.Tenant;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void address_and_addressDTO_tied_together() {
        AddressDTO address_info = new AddressDTO();
        address_info.apartmentNum = "325";
        address_info.city = "Columbus";
        address_info.country = "US";
        address_info.postal = "43212";
        address_info.state = "OH";
        address_info.street = "West 3rd Ave";

        Address addy = new Address("addy1", address_info);

        assertEquals("OH", addy.data.state);
    }

    @Test
    public void landlord_and_landlordDTO_tied_together() {
        LandlordDTO landlord_info = new LandlordDTO();
        landlord_info.address = "123 Street St, Columbus, OH, 43212";
        landlord_info.name = "Jeff";

        Landlord scum = new Landlord("land1", landlord_info);

        assertEquals("Jeff", scum.data.name);
    }

    @Test
    public void property_and_propertyDTO_tied_together() {
        PropertyDTO property_info = new PropertyDTO();
        property_info.address = "1212 Buckeye Drive, Columbus, OH, 43201";
        property_info.landlord = "Jeff";

        Property prop = new Property("prop1", property_info);

        assertEquals("Jeff", prop.data.landlord);
    }

    @Test
    public void tenant_and_tenantDTO_tied_together() {
        TenantDTO tenant_info = new TenantDTO();
        tenant_info.email = "email@gmail.com";
        tenant_info.name = "Geoff Jefferson";
        tenant_info.phone = "614-678-5423";

        Tenant tenant = new Tenant("tent1", tenant_info);

        assertEquals("email@gmail.com", tenant.data.email);
    }
}