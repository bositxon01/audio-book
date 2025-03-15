package uz.pdp.audiobook.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User, OidcUser {

    private final OAuth2User oAuth2User;
    private final String jwtToken;
    private OidcUser oidcUser;

    public CustomOAuth2User(OAuth2User oAuth2User, String jwtToken) {
        this.oAuth2User = oAuth2User;
        this.jwtToken = jwtToken;
        if (oAuth2User instanceof OidcUser) {
            this.oidcUser = (OidcUser) oAuth2User;
        }
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    public String getJwtToken() {
        return jwtToken;
    }

    @Override
    public OidcIdToken getIdToken() {
        return oidcUser != null ? oidcUser.getIdToken() : null;
    }

    @Override
    public Map<String, Object> getClaims() {
        return Map.of();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return oidcUser != null ? oidcUser.getUserInfo() : null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }
}
