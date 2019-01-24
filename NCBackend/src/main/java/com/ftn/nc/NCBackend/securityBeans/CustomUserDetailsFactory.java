package com.ftn.nc.NCBackend.securityBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ftn.nc.NCBackend.web.model.Korisnik;
import com.ftn.nc.NCBackend.web.model.TipKorisnika;

public class CustomUserDetailsFactory {

	private CustomUserDetailsFactory() {}
	
	public static CustomUserDetails createCustomUserDetails(Korisnik korisnik) {
		return new CustomUserDetails(
				korisnik.getEmail(),
				korisnik.getId(),
				mapToGrantedAuthorities(korisnik.getTip())
				);
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(Set<TipKorisnika> roles) {
        
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(TipKorisnika role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getNaziv().toString()));
			role.getPermisije().stream().map(p -> new SimpleGrantedAuthority(p.getNaziv().toString())).forEach(authorities::add);
		}
		
		return authorities;
    }
	
}
