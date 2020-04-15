package ch.hearc.dfts.models;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
}
