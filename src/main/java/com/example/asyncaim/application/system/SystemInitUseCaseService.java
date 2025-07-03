package com.example.asyncaim.application.system;

import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.permission.Permission;
import com.example.asyncaim.domain.permission.PermissionRepository;
import com.example.asyncaim.domain.user.User;
import com.example.asyncaim.domain.user.UserRepository;
import com.example.asyncaim.domain.user.UsersQuery;

public class SystemInitUseCaseService implements SystemInitUseCase {

  private final UserRepository userRepository;
  private final PermissionRepository permissionRepository;

  public SystemInitUseCaseService(
      UserRepository userRepository, PermissionRepository permissionRepository) {
    this.userRepository = userRepository;
    this.permissionRepository = permissionRepository;
  }

  @Override
  public void execute() {
    if (userRepository.listUsers(UsersQuery.empty(), Pagination.of(0, 1)).totalElements() == 0L) {

      User admin = new User("1", "admin", "admin@example.com");
      User user = new User("2", "user", "admin@example.com");

      userRepository.save(admin);
      userRepository.save(user);
    }
    if (permissionRepository.listPermissions(Pagination.of(0, 1)).totalElements() == 0L) {
      Permission usersRead = new Permission("1", "users_read", "");
      Permission usersWrite = new Permission("2", "users_write", "");
      Permission usersDelete = new Permission("3", "users_delete", "");
      Permission permissionsRead = new Permission("4", "permissions_read", "");
      Permission permissionsWrite = new Permission("5", "permissions_write", "");
      Permission permissionsDelete = new Permission("6", "permissions_delete", "");

      permissionRepository.save(usersRead);
      permissionRepository.save(usersWrite);
      permissionRepository.save(usersDelete);
      permissionRepository.save(permissionsRead);
      permissionRepository.save(permissionsWrite);
      permissionRepository.save(permissionsDelete);
    }
  }
}
