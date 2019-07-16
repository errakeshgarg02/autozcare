package com.autozcare.main.security;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.autozcare.main.model.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1457844782917600111L;

    private User user;
    
    private Collection<? extends GrantedAuthority> authorities;

    
	public UserPrincipal(User user) {
		this.user = user;
		this.authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
	}
    
    @Override
    public String getUsername() {
        return user.getMobileNumber();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(this.user.getId(), that.getUser().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.user.getId());
    }
}
