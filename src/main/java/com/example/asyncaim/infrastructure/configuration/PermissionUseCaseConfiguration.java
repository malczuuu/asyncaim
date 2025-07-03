package com.example.asyncaim.infrastructure.configuration;

import com.example.asyncaim.application.permission.CreatePermissionUseCase;
import com.example.asyncaim.application.permission.DeletePermissionUseCase;
import com.example.asyncaim.application.permission.FindPermissionUseCase;
import com.example.asyncaim.application.permission.ListPermissionsUseCase;
import com.example.asyncaim.application.permission.PatchPermissionUseCase;
import com.example.asyncaim.application.permission.UpdatePermissionUseCase;
import com.example.asyncaim.application.permission.impl.CreatePermissionUseCaseService;
import com.example.asyncaim.application.permission.impl.DeletePermissionUseCaseService;
import com.example.asyncaim.application.permission.impl.FindPermissionUseCaseService;
import com.example.asyncaim.application.permission.impl.ListPermissionsUseCaseService;
import com.example.asyncaim.application.permission.impl.PatchPermissionUseCaseService;
import com.example.asyncaim.application.permission.impl.UpdatePermissionUseCaseService;
import com.example.asyncaim.domain.identifier.IdentifierService;
import com.example.asyncaim.domain.permission.PermissionRepository;
import com.example.asyncaim.infrastructure.decorator.permission.TransactionAwarePatchPermissionUseCase;
import com.example.asyncaim.infrastructure.decorator.permission.TransactionAwareUpdatePermissionUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionUseCaseConfiguration {

  private final PermissionRepository permissionRepository;
  private final IdentifierService identifierService;

  public PermissionUseCaseConfiguration(
      PermissionRepository permissionRepository, IdentifierService identifierService) {
    this.permissionRepository = permissionRepository;
    this.identifierService = identifierService;
  }

  @Bean
  public ListPermissionsUseCase listPermissionsUseCase() {
    return new ListPermissionsUseCaseService(permissionRepository);
  }

  @Bean
  public FindPermissionUseCase findPermissionUseCase() {
    return new FindPermissionUseCaseService(permissionRepository);
  }

  @Bean
  public CreatePermissionUseCase createPermissionUseCase() {
    return new CreatePermissionUseCaseService(permissionRepository, identifierService);
  }

  @Bean
  public UpdatePermissionUseCase updatePermissionUseCase() {
    return new TransactionAwareUpdatePermissionUseCase(
        new UpdatePermissionUseCaseService(permissionRepository));
  }

  @Bean
  public PatchPermissionUseCase patchPermissionUseCase() {
    return new TransactionAwarePatchPermissionUseCase(
        new PatchPermissionUseCaseService(permissionRepository));
  }

  @Bean
  public DeletePermissionUseCase deletePermissionUseCase() {
    return new DeletePermissionUseCaseService(permissionRepository);
  }
}
