package com.wolf_pack.wolf_management.Repository;

import com.wolf_pack.wolf_management.Entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//Pack repository
public interface PackRepository extends JpaRepository<Pack, Long> {

    Optional<Object> findById(String packId);
}