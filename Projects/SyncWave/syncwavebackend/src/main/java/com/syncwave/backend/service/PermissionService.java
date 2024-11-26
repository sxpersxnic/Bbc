package com.syncwave.backend.service;

import com.syncwave.backend.model.Permission;
import com.syncwave.backend.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<Permission> permissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return Collections.singletonList(permissions.remove(1));
    }
}
