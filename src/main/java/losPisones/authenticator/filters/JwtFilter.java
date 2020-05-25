package losPisones.authenticator.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import losPisones.authenticator.service.MyUserDetailService;
import losPisones.authenticator.utils.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@Autowired
	private MyUserDetailService userDetailsService; 
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//		grab the key pair value Authorization which holds the jwt value
//		header value format: "token {jwt}"
		final String authorizationHeader = request.getHeader("Authorization"); 
		
		String username = null;
		String jwt = null;
		
//		checks if the value of Authorization statrs with token
		if(authorizationHeader != null && authorizationHeader.startsWith("token ")) {
//			extracting the jwt from the Authorization value
			jwt = authorizationHeader.substring(7);
//			extracting the username from the jwt			
			username = jwtUtil.extractUsername(jwt);
		}
		
//		if there is a user name check if there is already an existing user in the security context
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwt, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);		
			}
		}
//		continue the filter chain 
		filterChain.doFilter(request, response);
	}

}
