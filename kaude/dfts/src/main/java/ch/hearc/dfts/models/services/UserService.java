package ch.hearc.dfts.models.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.hearc.dfts.dto.UserDto;
import ch.hearc.dfts.models.Role;
import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.VerificationToken;
import ch.hearc.dfts.models.repositories.RoleRepository;
import ch.hearc.dfts.models.repositories.UserRepository;
import ch.hearc.dfts.models.repositories.VerificationTokenRepository;

@Service
public class UserService implements IUserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role roleUser = roleRepository.findByName("ROLE_ADMIN");
		Set<Role> rolesUser = new HashSet<>();
		rolesUser.add(roleUser);
		user.setRoles(rolesUser);
		userRepository.save(user);
	}

	@Override
	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User registerNewUserAccount(User accountDto) throws Exception {
		if (emailExist(accountDto.getEmail())) {
			throw new Exception("There is an account with that email adress: " + accountDto.getEmail());
		}

		User user = new User();
		user.setName(accountDto.getName());
		user.setPassword(accountDto.getPassword());
		user.setEmail(accountDto.getEmail());

		Role roleUser = roleRepo.findByName("ROLE_ADMIN");

		Set<Role> rolesUser = new HashSet<>();
		rolesUser.add(roleUser);
		user.setRoles(rolesUser);

		return userRepository.save(user);
	}

	private boolean emailExist(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public User getUser(String verificationToken) {
		User user = tokenRepository.findByToken(verificationToken).getUser();
		return user;
	}

	@Override
	public void saveRegisteredUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void createVerificationToken(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}

	@Override
	public VerificationToken getVerificationToken(String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}

}