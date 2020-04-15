package ch.hearc.dfts.models.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.hearc.dfts.models.Privilege;
import ch.hearc.dfts.models.Role;
import ch.hearc.dfts.models.repositories.*;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository utilisateurRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ch.hearc.dfts.models.User husser = utilisateurRepository.findByName(username);

		if (husser == null || !husser.isEnabled())
			throw new UsernameNotFoundException(username);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : husser.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return new User(husser.getName(), husser.getPassword(), grantedAuthorities);
	}
}