package ch.hearc.dfts.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class UserTest {
	@Test
	void givenAUserInstanceWithNoParms_whenCallGetName_thenCallReturnTrue() {
		User user = new User();
		
		assertEquals(user.getName(),"Toto");
	}
	
	
	@Test
	void givenAUserInstanceWithName_whenCallGetName_thenCallReturnTrue() {
		User user = new User();
		
		user.setName("Tata");
		assertEquals(user.getName(),"Tata");
	}
	
	@Test
	void givenAUserInstanceWithAdminRole_whenCallIsAdmin_thenReturnTrue() {
		User user = new User();

		Role roleAdmin = new Role();
		roleAdmin.setName("ROLE_ADMIN");

		Set<Role> roles = new HashSet<>();
		roles.add(roleAdmin);
		user.setRoles(roles);
		
		assertTrue(user.isAdmin());
	}
	
	@Test
	void givenAUserInstanceWithoutAdminRole_whenCallIsAdmin_thenReturnFalse() {
		User user = new User();

		Role roleAdmin = new Role();
		roleAdmin.setName("ROLE_USER");

		Set<Role> roles = new HashSet<>();
		roles.add(roleAdmin);
		user.setRoles(roles);
		
		assertFalse(user.isAdmin());
	}
}
