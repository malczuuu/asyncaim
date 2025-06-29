package com.example.asyncaim.infrastructure.configuration;

import com.example.asyncaim.domain.user.UserRepository;
import com.example.asyncaim.infrastructure.data.user.UserJpaRepository;
import com.example.asyncaim.infrastructure.data.user.UserRepositoryJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

  @Bean
  public UserRepository userRepository(UserJpaRepository userJpaRepository) {
    return new UserRepositoryJpaAdapter(userJpaRepository);
  }
}
