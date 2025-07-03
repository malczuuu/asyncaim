package com.example.asyncaim.infrastructure.configuration;

import com.example.asyncaim.application.user.CreateUserUseCase;
import com.example.asyncaim.application.user.DeleteUserUseCase;
import com.example.asyncaim.application.user.FindUserUseCase;
import com.example.asyncaim.application.user.InitUsersUseCase;
import com.example.asyncaim.application.user.ListUsersUseCase;
import com.example.asyncaim.application.user.PatchUserUseCase;
import com.example.asyncaim.application.user.UpdateUserUseCase;
import com.example.asyncaim.application.user.impl.CreateUserUseCaseService;
import com.example.asyncaim.application.user.impl.DeleteUserUseCaseService;
import com.example.asyncaim.application.user.impl.FindUserUseCaseService;
import com.example.asyncaim.application.user.impl.InitUsersUseCaseService;
import com.example.asyncaim.application.user.impl.ListUsersUseCaseService;
import com.example.asyncaim.application.user.impl.PatchUserUseCaseService;
import com.example.asyncaim.application.user.impl.UpdateUserUseCaseService;
import com.example.asyncaim.domain.identifier.IdentifierService;
import com.example.asyncaim.domain.user.UserRepository;
import com.example.asyncaim.infrastructure.decorator.TransactionAwareInitUsersUseCase;
import com.example.asyncaim.infrastructure.decorator.TransactionAwarePatchUserUseCase;
import com.example.asyncaim.infrastructure.decorator.TransactionAwareUpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfiguration {

  private final UserRepository userRepository;
  private final IdentifierService identifierService;

  public UserUseCaseConfiguration(
      UserRepository userRepository, IdentifierService identifierService) {
    this.userRepository = userRepository;
    this.identifierService = identifierService;
  }

  @Bean
  public ListUsersUseCase listUsersUseCase() {
    return new ListUsersUseCaseService(userRepository);
  }

  @Bean
  public FindUserUseCase findUserUseCase() {
    return new FindUserUseCaseService(userRepository);
  }

  @Bean
  public CreateUserUseCase createUserUseCase() {
    return new CreateUserUseCaseService(userRepository, identifierService);
  }

  @Bean
  public UpdateUserUseCase updateUserUseCase() {
    return new TransactionAwareUpdateUserUseCase(new UpdateUserUseCaseService(userRepository));
  }

  @Bean
  public PatchUserUseCase patchUserUseCase() {
    return new TransactionAwarePatchUserUseCase(new PatchUserUseCaseService(userRepository));
  }

  @Bean
  public DeleteUserUseCase deleteUserUseCase() {
    return new DeleteUserUseCaseService(userRepository);
  }

  @Bean
  public InitUsersUseCase initUsersUseCase() {
    return new TransactionAwareInitUsersUseCase(new InitUsersUseCaseService(userRepository));
  }
}
