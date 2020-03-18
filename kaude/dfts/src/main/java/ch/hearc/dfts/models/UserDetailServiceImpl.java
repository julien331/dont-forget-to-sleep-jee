package ch.hearc.dfts.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.hearc.dfts.models.repositories.*;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

	  @Autowired
	  private UserRepository utilisateurRepository;
	  
	  @Override
	  @Transactional(readOnly = true)
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    ch.hearc.dfts.models.User hüsser = utilisateurRepository.findByName(username);
	    if (hüsser == null) throw new UsernameNotFoundException(username);
			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			for (Role role : hüsser.getRoles()){
			    grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			}
	    return new User(hüsser.getName(), hüsser.getPassword(), grantedAuthorities);
	  }
}