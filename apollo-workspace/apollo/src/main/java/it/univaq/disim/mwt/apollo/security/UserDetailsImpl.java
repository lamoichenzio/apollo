package it.univaq.disim.mwt.apollo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.univaq.disim.mwt.apollo.domain.Role;
import it.univaq.disim.mwt.apollo.domain.StandardUser;
import it.univaq.disim.mwt.apollo.domain.User;

public class UserDetailsImpl implements UserDetails{
	
	private static final String ROLE_PREFIX = "ROLE_";
	private static final SimpleGrantedAuthority ROLE_STANDARD = new SimpleGrantedAuthority(ROLE_PREFIX + "standard");
	
	private User user;
	
	public UserDetailsImpl(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
		

		result.add(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole()));
		
		if (user instanceof StandardUser) {
			result.add(ROLE_STANDARD);
		}
		return result;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
