package losPisones.authenticator.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import losPisones.authenticator.models.UserRole;

public interface RoleRepository extends JpaRepository<UserRole, String> {

		
		UserRole findByUserRoleId(Long i);
		

		
}
