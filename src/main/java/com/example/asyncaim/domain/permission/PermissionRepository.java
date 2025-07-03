package com.example.asyncaim.domain.permission;

import com.example.asyncaim.domain.common.PagedContent;
import com.example.asyncaim.domain.common.Pagination;
import java.util.Optional;

public interface PermissionRepository {

  PagedContent<Permission> listPermissions(Pagination pagination);

  Optional<Permission> findPermission(String id);

  Permission save(Permission permission);

  void delete(String id);
}
