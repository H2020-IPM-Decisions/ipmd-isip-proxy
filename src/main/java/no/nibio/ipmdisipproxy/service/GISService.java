package no.nibio.ipmdisipproxy.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.locationtech.jts.geom.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.wololo.jts2geojson.GeoJSONReader;

/**
 * GISService contains logic for determining whether a given point is within Germany
 *
 * @since 0.0.1
 */
@Service
public class GISService {

  private static final String GERMANY_GEOJSON = "countries_DEU_as_feature.geojson";

  private final GeometryFactory geometryFactory;
  private Geometry germanyBoundaries;

  public GISService() throws IOException {
    this.geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    loadGeoJson();
  }

  private void loadGeoJson() throws IOException {
    ClassPathResource resource = new ClassPathResource(GERMANY_GEOJSON);
    BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
    String geoJsonStr = reader.lines().collect(Collectors.joining());
    GeoJSONReader geoJSONReader = new GeoJSONReader();
    this.germanyBoundaries = geoJSONReader.read(geoJsonStr);
  }

  public boolean isLocationInsideGermany(double latitude, double longitude) {
    Coordinate coordinate = new Coordinate(longitude, latitude);
    Point point = geometryFactory.createPoint(coordinate);
    return germanyBoundaries.contains(point);
  }

  public Geometry getGermanyBoundaries() {
    return germanyBoundaries;
  }
}
