package com.example.asyncaim.infrastructure.configuration;

import com.example.asyncaim.application.system.SystemInitUseCase;
import com.example.asyncaim.application.system.SystemInitUseCaseService;
import com.example.asyncaim.domain.permission.PermissionRepository;
import com.example.asyncaim.domain.user.UserRepository;
import com.example.asyncaim.infrastructure.decorator.system.TransactionAwareSystemInitUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemUseCaseConfiguration {

  private final UserRepository userRepository;
  private final PermissionRepository permissionRepository;

  public SystemUseCaseConfiguration(
      UserRepository userRepository, PermissionRepository permissionRepository) {
    this.userRepository = userRepository;
    this.permissionRepository = permissionRepository;
  }

  @Bean
  public SystemInitUseCase initUsersUseCase() {
    return new TransactionAwareSystemInitUseCase(
        new SystemInitUseCaseService(userRepository, permissionRepository));
  }
}
