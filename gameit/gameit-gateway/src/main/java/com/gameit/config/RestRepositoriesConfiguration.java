package com.gameit.config;

import com.gameit.model.AbstractBaseEntity;
import com.gameit.model.Game;
import com.gameit.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestRepositoriesConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(Game.class);
        config.exposeIdsFor(AbstractBaseEntity.class);
        config.useHalAsDefaultJsonMediaType(false);

        // Specify JSON instead of default HAL+JSON
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
