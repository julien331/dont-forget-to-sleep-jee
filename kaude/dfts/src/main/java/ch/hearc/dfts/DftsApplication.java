package ch.hearc.dfts;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import ch.hearc.dfts.models.Role;
import ch.hearc.dfts.models.Task;
import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.repositories.RoleRepository;
import ch.hearc.dfts.models.repositories.TaskRepository;
import ch.hearc.dfts.models.repositories.UserRepository;

@SpringBootApplication
public class DftsApplication {

	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	TaskRepository taskRepo;
	

	public static void main(String[] args) {
		SpringApplication.run(DftsApplication.class, args);
	}
	
	@PostConstruct
	public void init() {
		//Admin and his tasks
		Role roleAdmin = new Role();
		roleAdmin.setName("ROLE_ADMIN");
		
		User admin = new User();
		admin.setName("admin");
		admin.setEmail("asdasd@asd.net");
		admin.setEnabled(true);
		admin.setPassword(bCryptPasswordEncoder.encode("1234"));
		
		Set<Role> roles = new HashSet<>();
		roles.add(roleAdmin);
		admin.setRoles(roles);
		
		Task task1 = new Task();
		task1.setName("Don't forget to sleep");
		task1.setDescription("Dormir est très important pour la santé. Il est important de ne pas l'oublier, surtout avec la surcharge de travail imposée par la HE-Arc.");
		task1.setDone(false);
		Task task2 = new Task();
		task2.setName("Faire coucou");
		task2.setDescription("On leur fait coucou les p'tits gars! On leur fait coucou!");
		task2.setDone(false);
		
		Set<Task> tasks = new HashSet<>();
		tasks.add(task1);
		tasks.add(task2);
		
		admin.setTasks(tasks);
		userRepo.save(admin);
		
		//User and his tasks
		Role roleUser = new Role();
		roleUser.setName("ROLE_USER");
		
		User user = new User();
		user.setName("toto");
		user.setEmail("toto@asd.net");
		user.setEnabled(true);
		user.setPassword(bCryptPasswordEncoder.encode("1234"));
		
		Set<Role> rolesUser = new HashSet<>();
		rolesUser.add(roleUser);
		user.setRoles(rolesUser);
		
		Task task3 = new Task();
		task3.setName("Préparer une révolution communiste");
		task3.setDescription("Pour Lénine !");
		task3.setDone(false);
		
		Set<Task> tasksUser = new HashSet<>();
		tasksUser.add(task3);
		
		user.setTasks(tasksUser);
		userRepo.save(user);
	}

}
