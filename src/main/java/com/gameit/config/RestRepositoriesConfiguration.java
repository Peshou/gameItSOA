package com.gameit.config;

import com.gameit.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.http.MediaType;

/**
 * Created by Stefan on 06.4.2017.
 */
@Configuration
public class RestRepositoriesConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(User.class);

        // Specify JSON instead of default HAL+JSON
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);
    }
}