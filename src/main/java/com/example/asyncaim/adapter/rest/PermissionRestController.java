package com.example.asyncaim.adapter.rest;

import com.example.asyncaim.application.permission.CreatePermissionUseCase;
import com.example.asyncaim.application.permission.DeletePermissionUseCase;
import com.example.asyncaim.application.permission.FindPermissionUseCase;
import com.example.asyncaim.application.permission.ListPermissionsUseCase;
import com.example.asyncaim.application.permission.PatchPermissionUseCase;
import com.example.asyncaim.application.permission.UpdatePermissionUseCase;
import com.example.asyncaim.application.permission.model.PermissionCreateModel;
import com.example.asyncaim.application.permission.model.PermissionMapper;
import com.example.asyncaim.application.permission.model.PermissionModel;
import com.example.asyncaim.application.permission.model.PermissionPatchModel;
import com.example.asyncaim.application.permission.model.PermissionUpdateModel;
import com.example.asyncaim.domain.common.PagedContent;
import com.example.asyncaim.domain.common.Pagination;
import com.example.asyncaim.domain.permission.Permission;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/permissions")
public class PermissionRestController {

  private final ListPermissionsUseCase listPermissionsUseCase;
  private final FindPermissionUseCase findPermissionUseCase;
  private final CreatePermissionUseCase createPermissionUseCase;
  private final UpdatePermissionUseCase updatePermissionUseCase;
  private final PatchPermissionUseCase patchPermissionUseCase;
  private final DeletePermissionUseCase deletePermissionUseCase;

  private final PermissionMapper permissionMapper = new PermissionMapper();

  public PermissionRestController(
      ListPermissionsUseCase listPermissionsUseCase,
      FindPermissionUseCase findPermissionUseCase,
      CreatePermissionUseCase createPermissionUseCase,
      UpdatePermissionUseCase updatePermissionUseCase,
      PatchPermissionUseCase patchPermissionUseCase,
      DeletePermissionUseCase deletePermissionUseCase) {
    this.listPermissionsUseCase = listPermissionsUseCase;
    this.findPermissionUseCase = findPermissionUseCase;
    this.createPermissionUseCase = createPermissionUseCase;
    this.updatePermissionUseCase = updatePermissionUseCase;
    this.patchPermissionUseCase = patchPermissionUseCase;
    this.deletePermissionUseCase = deletePermissionUseCase;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public PagedContent<PermissionModel> getPermissions(
      @RequestParam(name = "page", defaultValue = "0") String page,
      @RequestParam(name = "size", defaultValue = "20") String size) {
    PagedContent<Permission> users = listPermissionsUseCase.execute(Pagination.parse(page, size));
    return users.map(permissionMapper::toModel);
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public PermissionModel getPermission(@PathVariable("id") String id) {
    return permissionMapper.toModel(findPermissionUseCase.execute(id));
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PermissionModel createPermission(@RequestBody @Valid PermissionCreateModel requestBody) {
    return permissionMapper.toModel(createPermissionUseCase.execute(requestBody));
  }

  @PutMapping(
      path = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PermissionModel updatePermission(
      @PathVariable("id") String id, @RequestBody @Valid PermissionUpdateModel requestBody) {
    return permissionMapper.toModel(updatePermissionUseCase.execute(id, requestBody));
  }

  @PatchMapping(
      path = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public PermissionModel patchPermission(
      @PathVariable("id") String id, @RequestBody @Valid PermissionPatchModel requestBody) {
    return permissionMapper.toModel(patchPermissionUseCase.execute(id, requestBody));
  }

  @DeleteMapping(path = "/{id}")
  public void deletePermission(@PathVariable("id") String id) {
    deletePermissionUseCase.execute(id);
  }
}
