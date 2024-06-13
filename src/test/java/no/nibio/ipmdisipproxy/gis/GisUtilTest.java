package no.nibio.ipmdisipproxy.gis;

import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;

import org.junit.jupiter.api.Test;


public class GisUtilTest {

    @Test
    public void testIsLocationInsideGermany()
    {
        System.out.println("isLocationInsideGermany");
        GISUtil instance = new GISUtil();
        
        try{
            // Location outside Germany
            Double longitude = 10.0;
            Double latitude = 10.0;
            assertFalse(instance.isLocationInsideGermany(longitude, latitude));
            //Location inside Germany
            longitude = 11.14;
            latitude = 50.71;
            assertTrue(instance.isLocationInsideGermany(longitude, latitude));
        }
        catch(IOException ex)
        {
            fail(ex.getMessage());
        }
    }
}
