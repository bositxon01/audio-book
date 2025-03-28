package uz.pdp.audiobook.enums;

import lombok.Getter;

import java.util.EnumSet;
import java.util.Set;

import static uz.pdp.audiobook.enums.Permission.*;

@Getter
public enum Role {
    USER(EnumSet.of(
            LISTEN_AUDIOBOOK,
            READ_AUDIOBOOK,
            WRITE_REVIEW,
            RATE_AUDIOBOOK)
    ),

    ADMIN(EnumSet.of(
            LISTEN_AUDIOBOOK,
            READ_AUDIOBOOK,
            WRITE_REVIEW,
            RATE_AUDIOBOOK,
            MANAGE_AUDIOBOOKS,
            MANAGE_CATEGORIES,
            MANAGE_REVIEWS)
    ),

    SUPER_ADMIN(EnumSet.allOf(Permission.class));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
