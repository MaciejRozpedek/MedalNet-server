package com.macroz.medalnetserver.repository;

import com.macroz.medalnetserver.model.Medal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MedalRepository extends JpaRepository<Medal, Long> {

	Optional<Medal> findMedalById(Long id);

	List<Medal> findMedalsByNameContainingOrSurnameContaining(String name, String surname);

	@Query("SELECT m FROM Medal m " +
			"WHERE m.number LIKE :query% OR m.number LIKE %:query% " +
			"ORDER BY CASE WHEN m.number LIKE :query% THEN 1 ELSE 2 END")
	List<Medal> findMedalsByNumber(String query);
}
