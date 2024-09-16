package com.macroz.medalnetserver.service;

import com.macroz.medalnetserver.exception.MedalNotFoundException;
import com.macroz.medalnetserver.model.Medal;
import com.macroz.medalnetserver.repository.MedalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedalService {
	private final MedalRepository medalRepository;

	@Autowired
	public MedalService(MedalRepository medalRepository) {
		this.medalRepository = medalRepository;
	}

	public Medal addMedal(Medal medal) {
		return medalRepository.save(medal);
	}

	public List<Medal> findAllMedals() {
		return medalRepository.findAll();
	}

	public Medal findMedalById(Long id) {
		return medalRepository.findMedalById(id)
				.orElseThrow(() -> new MedalNotFoundException("Medal by id " + id + " was not found"));
	}

	public Medal updateMedal(Medal medal) {
		return medalRepository.save(medal);
	}

	public void deleteMedal(Long id) {
		medalRepository.deleteById(id);
	}

	public List<Medal> searchMedalsByName(String query) {
		return medalRepository.findMedalsByNameContainingOrSurnameContaining(query, query);
	}

	public List<Medal> searchMedalsByNumber(String query) {
		return medalRepository.findMedalsByNumber(query);
	}

	public List<Medal> findMedalsByUserId(Long id) {
		return medalRepository.findMedalsByUserId(id);
	}
}
