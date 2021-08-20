package com.tcc.CRUDItau.controller;

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tcc.CRUDItau.model.Performance;
import com.tcc.CRUDItau.model.Performance.Type;
import com.tcc.CRUDItau.model.User;
import com.tcc.CRUDItau.repository.PerformanceRepository;
import com.tcc.CRUDItau.repository.UserRepository;

@RestController
@RequestMapping("performance")
public class PerformanceController {
	@Autowired
	PerformanceRepository performanceRepository;
	@Autowired
	UserRepository userRepository; 
	
	
	@PostMapping("/add")
	public ResponseEntity<?> addPerformance(@RequestBody Map<String, String> performanceMap) {
		try {
			
			if(performanceMap.get("userId") == null) {
				return new ResponseEntity<String>("Preencha o id do usuário", HttpStatus.BAD_REQUEST); 
			}
			if(performanceMap.get("type") == null) {
				return new ResponseEntity<String>("Preencha o tipo de desempenho", HttpStatus.BAD_REQUEST); 
			}
			if(performanceMap.get("time") == null) {
				return new ResponseEntity<String>("Preencha tempo de desempenho", HttpStatus.BAD_REQUEST); 
			}

			Optional<User> optionalUser = userRepository.findById(Long.parseLong(performanceMap.get("userId")));
			if (!optionalUser.isPresent()) {
				return new ResponseEntity<String>("User não existe", HttpStatus.BAD_REQUEST);
			}
			User user = optionalUser.get();
			
			Performance performance = new Performance();
			performance.setUser(user);
			Integer index = Integer.parseInt(performanceMap.get("type"));
			Type type = Type.values()[index]; 
			performance.setType(type); 
			performance.setTime(Double.parseDouble(performanceMap.get("time")));
			
			performanceRepository.save(performance);
			return new ResponseEntity<Performance>(performance, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
	}
	
}
