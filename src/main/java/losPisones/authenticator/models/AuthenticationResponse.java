package losPisones.authenticator.models;
/**
 * This class models the Authentication Response to a valid Authentication Request
 * 
 * @author Anthony Aquino-Lopez
 */

public class AuthenticationResponse {
	
	private String jwt;
	
	public AuthenticationResponse() {
		
	}

	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
	
	
	
	
	
	

}
