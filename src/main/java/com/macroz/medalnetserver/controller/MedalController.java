package com.macroz.medalnetserver.controller;

import com.macroz.medalnetserver.auth.JwtUtil;
import com.macroz.medalnetserver.exception.MedalNotFoundException;
import com.macroz.medalnetserver.model.Medal;
import com.macroz.medalnetserver.model.User;
import com.macroz.medalnetserver.service.MedalService;
import com.macroz.medalnetserver.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medal")
public class MedalController {
    private final MedalService medalService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public MedalController(MedalService medalService, JwtUtil jwtUtil, UserService userService) {
        this.medalService = medalService;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}

    @PostMapping("/add")
    public ResponseEntity<Medal> addMedal(@RequestBody Medal medal, @RequestHeader HttpHeaders headers) {
        String accessToken = jwtUtil.resolveToken(headers);
        String username = jwtUtil.getUsernameFromToken(accessToken);
        Optional<User> userOptional = userService.getByUsername(username);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        medal.setUserId(userOptional.get().getId());
        Medal newMedal = medalService.addMedal(medal);
        return new ResponseEntity<>(newMedal, HttpStatus.CREATED);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Medal>> getMyMedals(@RequestHeader HttpHeaders headers) {
        String accessToken = jwtUtil.resolveToken(headers);
        String username = jwtUtil.getUsernameFromToken(accessToken);
        Optional<User> userOptional = userService.getByUsername(username);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Medal> medals = medalService.findMedalsByUserId(userOptional.get().getId());
        return new ResponseEntity<>(medals, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Medal>> getAllMedals() {
        List<Medal> medals = medalService.findAllMedals();
        return  new ResponseEntity<>(medals, HttpStatus.OK);
    }

    @GetMapping("/search-by-name/{query}")
    public ResponseEntity<List<Medal>> searchMedalsByName(@PathVariable("query") String query) {
        List<Medal> medals = medalService.searchMedalsByName(query);
        return new ResponseEntity<>(medals, HttpStatus.OK);
    }

    @GetMapping("/search-by-number/{query}")
    public ResponseEntity<List<Medal>> searchMedalsByNumber(@PathVariable("query") String query) {
        List<Medal> medals = medalService.searchMedalsByNumber(query);
        return new ResponseEntity<>(medals, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Medal> getMedalById(@PathVariable("id") Long id) {
        Medal medal = medalService.findMedalById(id);
        return  new ResponseEntity<>(medal, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Medal> updateMedal(@RequestBody Medal medal, @RequestHeader HttpHeaders headers) {
        String accessToken = jwtUtil.resolveToken(headers);
        String username = jwtUtil.getUsernameFromToken(accessToken);
        Optional<User> userOptional = userService.getByUsername(username);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        medal.setUserId(userOptional.get().getId());
        Medal updateMedal = medalService.updateMedal(medal);
        return new ResponseEntity<>(updateMedal, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMedal(@PathVariable("id") Long id) {
        medalService.deleteMedal(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(MedalNotFoundException.class)
    public ResponseEntity<?> handleMedalNotFoundException(MedalNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
