package losPisones.authenticator.models;
/**
 * This class models a User Role
 * eg. USER, ADMIN ect.  
 * 
 * @author Anthony Aquino-Lopez
 */

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "user_role",
	uniqueConstraints = @UniqueConstraint(
		columnNames = { "role", "userId" }))
public class UserRole{

	private Long userRoleId;
	
	private User user;
	
	private String role;

	public UserRole() {}
	

	public UserRole(User user, String role) {
		this.user = user;
		this.role = role;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_role_id",
	unique = true, nullable = false)
	public Long getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "role", nullable = false, length = 45)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
