package com.unibuc.newsapp.service;

import com.unibuc.newsapp.entity.Role;
import com.unibuc.newsapp.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addRoleTest() {
        Role role = new Role("ADMIN");
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        Role result = roleService.addRole(role);

        assertNotNull(result);
        assertEquals("ADMIN", result.getName());
    }

    @Test
    public void updateRoleTest() {
        Role existingRole = new Role("USER");
        Role updatedRole = new Role("ADMIN");
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(existingRole));
        when(roleRepository.save(any(Role.class))).thenReturn(updatedRole);

        Role result = roleService.updateRole(1L, updatedRole);

        assertNotNull(result);
        assertEquals("ADMIN", result.getName());
    }

    @Test
    public void deleteRoleTest() {
        doNothing().when(roleRepository).deleteById(anyLong());

        roleService.deleteRole(1L);

        verify(roleRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getAllRolesTest() {
        Role role1 = new Role("ADMIN");
        Role role2 = new Role("USER");
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        List<Role> result = roleService.getAllRoles();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getName().equals("ADMIN")));
        assertTrue(result.stream().anyMatch(r -> r.getName().equals("USER")));
    }

    @Test
    public void getRoleByIdTest() {
        Role role = new Role("ADMIN");
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));

        Optional<Role> result = roleService.getRoleById(1L);

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getName());
    }
}
