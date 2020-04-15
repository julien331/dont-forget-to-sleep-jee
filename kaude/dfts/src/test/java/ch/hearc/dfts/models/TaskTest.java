package ch.hearc.dfts.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class TaskTest {

	@Test
	void givenATask_whenMarkingAsDone_thenReturnsUser() {

		User user = new User();

		user.setName("admin");
		user.setEmail("asdasd@asd.net");
		user.setEnabled(true);
		user.setPassword("1234");
		
		Role role = new Role();
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		
		System.out.println(user);
		
		Set<User> users = new HashSet<User>();
		System.out.println(users);
		users.add(user);
		role.setUsers(users);
		
		Set<User> usersRes = role.getUsers();
		
		assertNotNull(usersRes);
		assertEquals(usersRes.size(), 1);
	}

}
