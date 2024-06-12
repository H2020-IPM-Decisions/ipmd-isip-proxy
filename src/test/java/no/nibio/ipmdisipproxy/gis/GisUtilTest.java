package no.nibio.ipmdisipproxy.gis;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;


public class GisUtilTest {

    @Test
    public void testIsLocationInsideGermany()
    {
        System.out.println("isLocationInsideGermany");
        GISUtil instance = new GISUtil();
        Double longitude = 10.0;
        Double latitude = 10.0;
        try{
            Boolean result = instance.isLocationInsideGermany(longitude, latitude);
            //assertFalse(instance.isLocationInsideGermany(longitude, latitude));
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }
}
