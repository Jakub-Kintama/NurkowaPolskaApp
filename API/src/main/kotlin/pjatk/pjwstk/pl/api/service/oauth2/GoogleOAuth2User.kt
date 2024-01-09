package pjatk.pjwstk.pl.api.service.oauth2

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import pjatk.pjwstk.pl.api.model.User

class GoogleOAuth2User(
    private var oAuth2User: OAuth2User,
    private var user: User
) : OAuth2User {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities = ArrayList(oAuth2User.authorities)
        authorities.add(SimpleGrantedAuthority("ROLE_${user.role}"))
        return authorities
    }
    override fun getAttributes(): Map<String, Any> = oAuth2User.attributes
    override fun getName(): String = oAuth2User.name
    fun getUser(): User = this.user
}