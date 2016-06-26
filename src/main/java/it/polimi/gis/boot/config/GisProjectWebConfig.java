
package it.polimi.gis.boot.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import it.polimi.gis.util.HibernateAwareObjectMapper;

@Configuration
public class GisProjectWebConfig
    extends WebMvcConfigurerAdapter
{


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);

        registry.addMapping("/**")

        .allowedOrigins("http://localhost:3000");

    }

    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        HibernateAwareObjectMapper hibernateAwareObjectMapper = new HibernateAwareObjectMapper();
        jsonConverter.setObjectMapper(hibernateAwareObjectMapper);
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
         converters.add(customJackson2HttpMessageConverter());
    }

}
