package uz.pdp.audiobook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.audiobook.entity.template.Person;
import uz.pdp.audiobook.enums.Role;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "password") // Parolni chiqarib yuborish
@Entity(name = "users")

@SQLRestriction(value = "deleted = false")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
public class User extends Person implements UserDetails {

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @NotBlank
    @Column(nullable = false)
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@#$%^&+=!)"
    )
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList();
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
