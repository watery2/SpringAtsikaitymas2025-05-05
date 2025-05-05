package com.kitm.atsiskaitymas_2025_05_05.dao;

import com.kitm.atsiskaitymas_2025_05_05.entity.Role;
import com.kitm.atsiskaitymas_2025_05_05.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles name);
}
