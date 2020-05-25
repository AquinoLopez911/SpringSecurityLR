package losPisones.authenticator.repositoty;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import losPisones.authenticator.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	User saveAndFlush(User user);
	
	User findByuserId(Long userId);
	
	Optional<User> findByUsername(String userName);
		
}

