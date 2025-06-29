package com.example.asyncaim.infrastructure.data.init;

import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.common.Status;
import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UserRepository;
import com.example.asyncaim.domain.user.UsersQuery;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AdminUserInitializer implements CommandLineRunner {

  private final UserRepository userRepository;

  public AdminUserInitializer(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  @Override
  public void run(String... args) {
    if (userRepository.listUsers(UsersQuery.empty(), Pagination.of(0, 1)).totalElements() == 0L) {

      User admin = new User("1", "admin", "admin@example.com", Status.IDLE, null);
      User user = new User("2", "user", "admin@example.com", Status.IDLE, null);

      userRepository.save(admin);
      userRepository.save(user);
    }
  }
}
