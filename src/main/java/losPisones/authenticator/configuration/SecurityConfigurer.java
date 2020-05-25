package losPisones.authenticator.configuration;
import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import losPisones.authenticator.filters.JwtFilter;
import losPisones.authenticator.service.MyUserDetailService;

//@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private MyUserDetailService myUserDetailService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception{
		
		authBuilder.userDetailsService(myUserDetailService);
		
		
		authBuilder.parentAuthenticationManager(authenticationManagerBean()).userDetailsService(myUserDetailService);
		
	
//		
		authBuilder.jdbcAuthentication().dataSource(dataSource);
		
	}
	
	
//	ignores security to access static files
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	            .antMatchers("/resources/**", "/static/**", "/css/**", "/images/**");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
//		disable security for /authenticate and / paths 
		http.csrf().disable()
			.authorizeRequests().antMatchers("/authenticate", "/", "/register", "/welcome").permitAll()
			.anyRequest().authenticated()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //tell java not to make a session
		
//		add jwt filter
//		calling the jwt filter before the default filter is called 
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	//spring 2 needs to explicitly create a authenticationManager bean 
	@Bean(name="AuthenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
	}
	
	
	@Bean
	public BCryptPasswordEncoder BCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	

	
}//
