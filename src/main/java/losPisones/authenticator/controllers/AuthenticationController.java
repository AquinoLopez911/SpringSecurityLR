package losPisones.authenticator.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.annotation.JsonCreator;

import losPisones.authenticator.models.AuthenticationRequest;
import losPisones.authenticator.models.AuthenticationResponse;
import losPisones.authenticator.models.User;
import losPisones.authenticator.service.MyUserDetailService;
import losPisones.authenticator.utils.JwtUtil;

@Controller
public class AuthenticationController {
	
	
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailService userDetailsService;
	
	
	/**
	 * 
	 * @param model:
	 * model to be filled with user details from form
	 * @return {templateName}
	 */
	@GetMapping( "/" )
	public String registrationPage(Model model) {
		User  user = new User();
		model.addAttribute("user", user);
		return "registration";
	}
	
	
	/**
	 * 
	 * @return {templateName}
	 * returns a thymeleaf HTML template 
	 */
	@GetMapping( "/welcome" )
	public String success() {
		return "success";
	}
	
	
	/**
	 * 
	 * @param user:
	 * the User from data 
	 * 
	 * @param result:
	 * ???
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path="/register")
	public String registerUser1(@Valid @ModelAttribute("user") User user, 
		      BindingResult result, Model model) throws Exception {		
		
		if (result.hasErrors()) {
			System.out.println("ERRORS ******************************************************************************************************************");
			System.out.println(result.getErrorCount());
			System.out.println(result.toString());
            return "registration";
        }
		else {
			model.addAttribute("user", user);
			System.out.println("no ERRORS ******************************************************************************************************************");
			return "redirect:/welcome";
		}
		
        
	}
	

	/**
	 * 
	 * @param authenticationRequest:
	 * username and password sent by the user 
	 * 
	 * @return ResponseEntity
	 * returns the AuthenticationRepsonse 
	 * 
	 * @throws BadCredentialsException:
	 * thrown when user enters invalid credentials (username or password)
	 * 
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
//			create a new UsernamePasswordAuthenticationToken with the username and password provided by the user
			UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
			
//			authenticate the user credentials (makes sure there is a username and password)
			authenticationManager.authenticate(userToken);
		}
		catch (BadCredentialsException e) {
			
			throw new Exception("Incorrect username or password", e);
		}

//		load a user by a username 
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
//		creates the jwt for the given user
		final String jwt = jwtTokenUtil.generateToken(userDetails);

//		returns the generated jwt for the user
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	

}
//end AuthenticationController class