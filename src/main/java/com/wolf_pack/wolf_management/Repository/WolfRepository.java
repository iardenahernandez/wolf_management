package com.wolf_pack.wolf_management.Repository;

import com.wolf_pack.wolf_management.Entity.Wolf;
import org.springframework.data.jpa.repository.JpaRepository;

//Wolf repository
public interface WolfRepository extends JpaRepository<Wolf, Long> {

}