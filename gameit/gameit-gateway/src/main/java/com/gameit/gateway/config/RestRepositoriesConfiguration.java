package com.gameit.gateway.config;

import com.gameit.gateway.model.AbstractBaseEntity;
import com.gameit.gateway.model.Game;
import com.gameit.gateway.model.User;
import com.gameit.gateway.model.UserGameOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.http.MediaType;

@Configuration
public class RestRepositoriesConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        super.configureRepositoryRestConfiguration(config);
        config.useHalAsDefaultJsonMediaType(false);
        config.exposeIdsFor(UserGameOrder.class, User.class, Game.class, AbstractBaseEntity.class);

        // Specify JSON instead of default HAL+JSON
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);
    }
}