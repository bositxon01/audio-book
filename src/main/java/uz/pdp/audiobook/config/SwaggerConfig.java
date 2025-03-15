package uz.pdp.audiobook.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "AudioBook API", version = "1.0", description = "OAuth2 Authentication"),
        security = {@SecurityRequirement(name = "BearerAuth")}
)
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("OAuth2",
                                new SecurityScheme()
                                        .name("OAuth2")
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .flows(new OAuthFlows()
                                                .authorizationCode(new OAuthFlow()
                                                        .authorizationUrl("/oauth2/authorization/google") // Change based on your provider
                                                        .tokenUrl("/login/oauth2/code/google") // Change this as per your OAuth2 provider
                                                        .scopes(new Scopes())
                                                )
                                        )
                        )
                )
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("OAuth2"));
    }
}

