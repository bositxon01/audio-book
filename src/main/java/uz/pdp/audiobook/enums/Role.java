package uz.pdp.audiobook.enums;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Set;

@Getter
public enum Role {
    USER(EnumSet.of(
            Permission.LISTEN_AUDIOBOOK,
            Permission.READ_AUDIOBOOK,
            Permission.WRITE_REVIEW,
            Permission.RATE_AUDIOBOOK)
    ),

    ADMIN(EnumSet.allOf(Permission.class)),

    SUPER_ADMIN(EnumSet.allOf(Permission.class));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
