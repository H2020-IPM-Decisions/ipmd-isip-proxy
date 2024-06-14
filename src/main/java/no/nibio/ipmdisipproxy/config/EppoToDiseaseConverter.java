package no.nibio.ipmdisipproxy.config;

import no.nibio.ipmdisipproxy.model.Disease;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @since 1.0.0
 */
@Component
public class EppoToDiseaseConverter implements Converter<String, Disease> {

    @Override
    public Disease convert(String source) {
        return Disease.fromIsipName(source);
    }
}