package ch.hearc.dfts;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ch.hearc.dfts.models.Role;
import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.repositories.RoleRepository;
import ch.hearc.dfts.models.repositories.UserRepository;

@SpringBootApplication
public class DftsApplication {

	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(DftsApplication.class, args);
	}
	
	@PostConstruct
	public void init() {
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		roleRepo.save(role);
		
		User user = new User();
		user.setName("admin");
		user.setPassword(bCryptPasswordEncoder.encode("1234"));
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		userRepo.save(user);
	}

}
