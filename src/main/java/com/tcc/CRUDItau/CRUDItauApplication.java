package com.tcc.CadeMeuBichinho;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.tcc.CadeMeuBichinho.config.AuthorizationServerConfiguration;
import com.tcc.CadeMeuBichinho.model.User;
import com.tcc.CadeMeuBichinho.repository.UserRepository;

@SpringBootApplication
public class CadeMeuBichinhoApplication {
	public static void main(String[] args) {	
		ApplicationContext applicationContext =SpringApplication.run(CadeMeuBichinhoApplication.class, args);
		ServiceAdmin service = applicationContext.getBean(ServiceAdmin.class);
		service.createUserAdmin();
	}
}

@Service
class ServiceAdmin {
	@Autowired
	AuthorizationServerConfiguration authorizationServerConfiguration;
	
	@Autowired
	private UserRepository userRepository;
	
	public void createUserAdmin() {
		try {
			User user = userRepository.findByEmail("cademeubichinho02@outlook.com");
			
			if (user == null) {
				User admin = new User();
				admin.setName("Admin geral");
				admin.setPhone(new Long(51992582113L));
				admin.setPhoneWithWhats(true);
				admin.setEmail("cademeubichinho02@outlook.com");
				admin.setActive(true);
				String pass = authorizationServerConfiguration.passwordEncoder.encode("admin123456");
				admin.setPassword(pass); 
				userRepository.save(admin);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
