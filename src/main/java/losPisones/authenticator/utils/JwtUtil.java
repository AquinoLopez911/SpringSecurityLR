package losPisones.authenticator.utils;

/**
* The class helps deal with jwt logic
* @version 1
* @author Anthony Aquino-Lopez
*/


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String SECRET_KEY = "91145LosP30396";

    /**
    * this method is used to extract the username from the jwt token
    *
    * @author  Anthony Aquino-Lopez
    * @version 1.0
    * @since   2020-05-20 
    */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * this method is used to extract the expiration time from the jwt 
     *
     * @author  Anthony Aquino-Lopez
     * @version 1.0
     * @since   2020-05-20 
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * this method is used to extract the claim from the jwt 
     * claim (username, expiration..)
     *
     * @author  Anthony Aquino-Lopez
     * @version 1.0
     * @since   2020-05-20 
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * this method is used to extract all claims from the jwt 
     *????
     *
     * @author  Anthony Aquino-Lopez
     * @version 1.0
     * @since   2020-05-20 
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * this method returns weather a token is expired or not
     *
     * @author  Anthony Aquino-Lopez
     * @version 1.0
     * @since   2020-05-20 
     */    
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * this method is used to generate a jwt for the given user
     *
     * @author  Anthony Aquino-Lopez
     * @version 1.0
     * @since   2020-05-20 
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * this method is used to create a jwt
     * return the created jwt
     *
     * @author  Anthony Aquino-Lopez
     * @version 1.0
     * @since   2020-05-20 
     */
    private String createToken(Map<String, Object> claims, String subject) {

//    	takes the claims and the username, creates an expiration time, and sings the jwt
//    	return the jwt
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * this method return weather a jwt is valid or not
     *
     * @author  Anthony Aquino-Lopez
     * @version 1.0
     * @since   2020-05-20 
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
