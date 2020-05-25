package losPisones.authenticator.models;
/**
 * This class models the Authentication Request to get a jwt
 * 
 * @author Anthony Aquino-Lopez
 */

public class AuthenticationRequest {

	private String username;
	private String password;
	
	
	public AuthenticationRequest() {
		
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
