package losPisones.authenticator.models;
/**
 * This class models a User 
 * 
 * @author Anthony Aquino-Lopez
 */

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity	
@Table(name = "user")
public class User {
	
	
	private Long userId;
	
	private String email;
	
	private String username;
	
	private String password;
	
	private boolean enabled;
	
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	

	public User() {}

	public User(String email, String username, String password, boolean enabled) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User(String email, String username, String password,
		boolean enabled, Set<UserRole> userRoles) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRoles = userRoles;
	}
	
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name = "userId", unique = true, nullable = false)
	public Long getUserId() {
		return this.userId;
	}
	
	

	@Column(name = "userName", unique = true,
			nullable = false, length = 45)
	@Size(min=3, max=15, message = "username must be atleadt 3 characters")
	public String getUsername() {
		return this.username;
	}
	
	
	
	@Column(name = "password",
			nullable = false, length = 60)
	@Size(min=8, message = "Password must be atleast 8 characters")
	public String getPassword() {
		return this.password;
	}
	
	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}
	
	@Email
	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}
	
	
	
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}//end User class
