package com.autozcare.main.security;
import com.autozcare.main.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1457844782917600111L;

	private Long id;

    private String name;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;
    
    private String mobileNumber;
    
    private LocalDate dob;

    private Collection<? extends GrantedAuthority> authorities;

	/*
	 * public UserPrincipal(Long id, String name, String username, String email,
	 * String password, Collection<? extends GrantedAuthority> authorities) {
	 * this.id = id; this.name = name; this.username = username; this.email = email;
	 * this.password = password; this.authorities = authorities; }
	 */

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return UserPrincipal.builder().id(user.getId())
        .name(user.getName()).username(user.getUsername())
        .email(user.getEmail()).password(user.getPassword())
        .mobileNumber(user.getMobileNumber())
        .dob(user.getDob())
        .authorities(authorities).build();
        
		/*
		 * return new UserPrincipal( user.getId(), user.getName(), user.getUsername(),
		 * user.getEmail(), user.getPassword(), authorities );
		 */
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
