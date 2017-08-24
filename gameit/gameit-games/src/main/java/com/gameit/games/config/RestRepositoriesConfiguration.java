package com.gameit.games.config;

import com.gameit.games.model.AbstractBaseEntity;
import com.gameit.games.model.Game;
import com.gameit.games.model.User;
import com.gameit.games.model.UserGameOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.http.MediaType;

@Configuration
public class RestRepositoriesConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.useHalAsDefaultJsonMediaType(false);
        config.exposeIdsFor(UserGameOrder.class, User.class, Game.class, AbstractBaseEntity.class);

        // Specify JSON instead of default HAL+JSON
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);
        super.configureRepositoryRestConfiguration(config);
    }
}