package no.nibio.ipmdisipproxy.service;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GISServiceTest {

  @Autowired private GISService gisService;

  @Test
  public void testLoadGeoJson() {
    Geometry boundaries = gisService.getGermanyBoundaries();
    assertThat(boundaries, notNullValue());
    assertThat(boundaries.getArea(), closeTo(45.94649702569486, 0.001));
  }

  @Test
  public void testBerlinIsInsideGermany() {
    assertThat(gisService.isLocationInsideGermany(52.5200, 13.4050), is(true));
  }

  @Test
  public void testOsloIsNotInsideGermany() {
    assertThat(gisService.isLocationInsideGermany(59.9139, 10.7522), is(false));
  }
}
