package com.ftn.nc.NCBackend.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.repository.KorisnikRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils 
{

    private String secret = "NCproj2018SecretCode#Mjok";

    private Long expiration = new Long(2*60*60);

    @Autowired
    private KorisnikRepository korisnikRepository;


    public String getUsernameFromToken(String token) 
    {
        String username;
        try 
        {
            Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } 
        catch (Exception e) 
        {
            username = null;
        }
       
        return username;
    }

    
    private Claims getClaimsFromToken(String token) 
    {
        Claims claims;
        try 
        {
            claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        } 
        catch (Exception e) 
        {
            claims = null;
        }
        return claims;
    }

    
    public Date getExpirationDateFromToken(String token) 
    {
        Date expiration;
        try 
        {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } 
        catch (Exception e) 
        {
            expiration = null;
        }
        return expiration;
    }

    
    private boolean isTokenExpired(String token) 
    {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    
    public boolean validateToken(String token, UserDetails userDetails) 
    {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
        
        
    }

    @Transactional
    public String generateToken(UserDetails userDetails) 
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("kreirano", new Date(System.currentTimeMillis()));
        claims.put("uloga", userDetails.getAuthorities());
        
        Korisnik korisnik = this.korisnikRepository.findByEmail(userDetails.getUsername());
        if(korisnik==null) {
        	throw new UsernameNotFoundException("Nepostojeci korisnik");
        }
          
        try
        {
            claims.put("id", korisnik.getId());
        }
        catch(NullPointerException e){}

        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }



}

