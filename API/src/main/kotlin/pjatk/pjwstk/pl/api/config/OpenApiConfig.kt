package pjatk.pjwstk.pl.api.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.*
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(info = Info(title = "Raki w Polsce", description = "Dokumentacja API projektu Raki w Polsce"))
@SecuritySchemes(
    SecurityScheme(
        name = "jwtAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
    ),
    SecurityScheme(
        name = "oauth2",
        type = SecuritySchemeType.OAUTH2,
        flows = OAuthFlows(
            authorizationCode = OAuthFlow(
                authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth",
                tokenUrl = "https://oauth2.googleapis.com/token",
                scopes = [
                    OAuthScope(
                        name = "email"
                    ),
                    OAuthScope(
                        name = "profile"
                    )
                ]
            )
        )
    )
)
class OpenApiConfig