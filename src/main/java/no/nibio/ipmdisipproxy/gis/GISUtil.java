/*
 * Copyright (c) 2024 NIBIO <http://www.nibio.no/>. 
 * 
 * This file is part of IPM Decisions DSS Service.
 * IPM Decisions DSS Service is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * IPM Decisions DSS Service is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with IPM Decisions DSS Service.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package no.nibio.ipmdisipproxy.gis;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.wololo.jts2geojson.GeoJSONReader;



/**
 * Util methods for handling GIS operations
 * 
 * @author Tor-Einar Skog <tor-einar.skog@nibio.no>
 */
public class GISUtil {

    private Geometry germanyBoundaries = null;

    /**
     * @return Multipolygon of Germany
     * @throws IOException
     */
    public Geometry getGermanyBoundaries() throws IOException{
        if (this.germanyBoundaries == null) {
            BufferedReader fileReader = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(this.getClass().getResourceAsStream("/countries_DEU_as_feature.geojson")),
                            StandardCharsets.UTF_8));
            String fileStr = fileReader.lines().collect(Collectors.joining("\n"));
            fileReader.close();
            GeoJSONReader geoJSONReader = new GeoJSONReader();
        
            this.germanyBoundaries = geoJSONReader.read(fileStr);
        }
        return this.germanyBoundaries;
    }


    /**
     * Checks against a Multipolygon for Germany if the requested lon-lat location is inside Germany
     * The implication is that if we are, weather data shall not be provided in the request to 
     * ISIP
     * @param longitude Decimal degrees longitude
     * @param latitude Decimal degrees latitude
     * @return This should be obvious
     */
    public Boolean isLocationInsideGermany(Double longitude, Double latitude) throws IOException
    {
        double[] coordinate = new double[2];
        coordinate[0] = longitude;
        coordinate[1] = latitude;
        Coordinate c = new Coordinate(longitude, latitude);
        GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = gf.createPoint(c);
        
        return this.getGermanyBoundaries().contains(point);
    }
}
