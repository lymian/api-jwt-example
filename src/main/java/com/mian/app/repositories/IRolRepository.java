package com.mian.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mian.app.entities.Rol;
import com.mian.app.entities.enums.RolEnum;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(RolEnum nombre);
}