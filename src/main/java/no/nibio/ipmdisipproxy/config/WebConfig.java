package no.nibio.ipmdisipproxy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final EppoToCropConverter eppoToCropConverter;
    private final EppoToDiseaseConverter eppoToDiseaseConverter;

    public WebConfig(EppoToCropConverter eppoToCropConverter, EppoToDiseaseConverter eppoToDiseaseConverter) {
        this.eppoToCropConverter = eppoToCropConverter;
        this.eppoToDiseaseConverter = eppoToDiseaseConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(eppoToCropConverter);
        registry.addConverter(eppoToDiseaseConverter);
    }
}