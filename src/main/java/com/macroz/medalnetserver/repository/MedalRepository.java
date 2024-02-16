package com.macroz.medalnetserver.repository;

import com.macroz.medalnetserver.model.Medal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedalRepository extends JpaRepository<Medal, Long> {

	Optional<Medal> findMedalById(Long id);
}
