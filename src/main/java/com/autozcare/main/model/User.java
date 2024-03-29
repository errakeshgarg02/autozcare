package com.autozcare.main.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "email"
        }),
        @UniqueConstraint(columnNames = {
                "mobileNumber"
            })
})
public class User extends Auditable {

	private static final long serialVersionUID = 8894651381735709494L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;
    
    private String mobileNumber;
    
    private String alternateMobileNumber;
    
    private LocalDate dob;
    
    private String otp;
    
    private boolean active;
    
    private boolean kycActive;
    
    private boolean validOtp;
    
    private boolean accountNonExpired;
    
    private boolean accountNonLocked;
    
    private boolean credentialsNonExpired;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "user_address", 
      joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
      inverseJoinColumns = { @JoinColumn(name = "address_id", referencedColumnName = "id") })
	private Address address;
}
