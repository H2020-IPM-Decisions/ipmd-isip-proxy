package no.nibio.ipmdisipproxy.config;

import no.nibio.ipmdisipproxy.model.Crop;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @since 1.0.0
 */
@Component
public class EppoToCropConverter implements Converter<String, Crop> {

    @Override
    public Crop convert(String eppoCode) {
        return Crop.fromEppoCode(eppoCode);
    }
}