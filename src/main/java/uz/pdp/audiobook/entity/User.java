package uz.pdp.audiobook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.audiobook.entity.template.Person;
import uz.pdp.audiobook.enums.Role;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "password")
@Entity(name = "users")
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_active_user_username",
                columnNames = {"username", "deleted"}
        )
)

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
public class User extends Person implements UserDetails {

    @NotBlank
    @Email
    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    // New flags for personalization:
    @Column(nullable = false)
    private Boolean preferencesConfigured = false;

    @Column(nullable = false)
    private Boolean preferencesSkipped = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> permissionAuthorities = role.getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList();

        List<SimpleGrantedAuthority> roleAuthorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + role.name())
        );

        return Stream.concat(permissionAuthorities.stream(), roleAuthorities.stream())
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Hisob muddati tugaganligini tekshirish
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Hisob bloklanganligini tekshirish
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Parol eskirganligini tekshirish
    }

    @Override
    public boolean isEnabled() {
        return !isDeleted(); // Agar foydalanuvchi o'chirilmagan bo'lsa, true qaytaradi
    }
}
