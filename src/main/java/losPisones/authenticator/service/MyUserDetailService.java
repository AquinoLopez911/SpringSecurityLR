package losPisones.authenticator.service;

/**
 * This class is used by spring controller to authenticate and authorize user
 * only users in the db with be able to authenticate
 * 
 * @author Anthony Aquino-Lopez
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import losPisones.authenticator.models.MyUserDetails;
import losPisones.authenticator.models.User;
import losPisones.authenticator.models.UserRole;
import losPisones.authenticator.repositoty.RoleRepository;
import losPisones.authenticator.repositoty.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	/**
	 * returns a MyUserDetails object mapped from a User Object
	 * 
	 * @author Anthony Lopez-Aquino
	 */
	@Override
	public MyUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByUsername(userName);
		
		user.orElseThrow( ( ) -> new UsernameNotFoundException("username notfound"));
		
		
		return user.map(MyUserDetails::new).get();
		
	}//end loadUserByUsername
	
	/**
	 * returns all users
	 * 
	 * @author Anthony Lopez-Aquino
	 */
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	/**
	 * saves a user to the database if the user has a role
	 * 
	 * @author Anthony Lopez-Aquino
	 */
	public User saveUser(User user) throws Exception {
		Long roleId = 1L;
		UserRole userRole = roleRepository.findByUserRoleId(roleId);
		
		if (userRole != null) {
			Set<UserRole> roles = new HashSet<UserRole>();
			roles.add(userRole);
			user.setEmail(user.getEmail());
	        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
	        user.setUserRoles(roles);
	        return userRepository.saveAndFlush(user);
		}
		else {
			throw new Exception("user role not found");
		}
		
		
	}
	
	
	
	
	
}//end UserDetailServiceImpl class 
