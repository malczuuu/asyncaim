package com.example.asyncaim.application.permission;

import com.example.asyncaim.domain.common.PagedContent;
import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.permission.Permission;

public interface ListPermissionsUseCase {

  PagedContent<Permission> execute(Pagination pagination);
}
