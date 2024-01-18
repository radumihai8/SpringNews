package com.unibuc.newsapp.controller;

import com.unibuc.newsapp.entity.Role;
import com.unibuc.newsapp.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRole() {
        Role role = new Role("ADMIN");
        when(roleService.addRole(any(Role.class))).thenReturn(role);

        ResponseEntity<Role> response = roleController.addRole(role);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("ADMIN", response.getBody().getName());
    }

    @Test
    void testUpdateRole() {
        Role updatedRole = new Role("UPDATED_ROLE");
        when(roleService.updateRole(anyLong(), any(Role.class))).thenReturn(updatedRole);

        ResponseEntity<Role> response = roleController.updateRole(1L, updatedRole);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("UPDATED_ROLE", response.getBody().getName());
    }

    @Test
    void testDeleteRole() {
        doNothing().when(roleService).deleteRole(anyLong());

        ResponseEntity<Void> response = roleController.deleteRole(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllRoles() {
        Role role = new Role("ADMIN");
        when(roleService.getAllRoles()).thenReturn(Collections.singletonList(role));

        ResponseEntity<List<Role>> response = roleController.getAllRoles();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals("ADMIN", response.getBody().get(0).getName());
    }

    @Test
    void testGetRoleById() {
        Role role = new Role("ADMIN");
        when(roleService.getRoleById(anyLong())).thenReturn(Optional.of(role));

        ResponseEntity<Role> response = roleController.getRoleById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ADMIN", response.getBody().getName());
    }
}
