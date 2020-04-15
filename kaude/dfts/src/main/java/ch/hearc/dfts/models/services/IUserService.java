package ch.hearc.dfts.models.services;

import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.VerificationToken;

public interface IUserService {

	User findByName(String name);
	User findByEmail(String email);
	User getUser(String verificationToken);
	VerificationToken getVerificationToken(String VerificationToken);
	
	void save(User user);
	void saveRegisteredUser(User user);
	void createVerificationToken(User user, String token);
}