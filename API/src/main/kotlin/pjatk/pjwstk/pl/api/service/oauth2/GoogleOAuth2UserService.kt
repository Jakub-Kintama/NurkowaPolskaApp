package pjatk.pjwstk.pl.api.service.oauth2

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.stereotype.Service
import pjatk.pjwstk.pl.api.datasource.UserDataSource
import pjatk.pjwstk.pl.api.model.User
import pjatk.pjwstk.pl.api.model.enums.Role

@Service
class GoogleOAuth2UserService(
    @Qualifier("mongodbUser") private val dataSource: UserDataSource,
    ) : DefaultOAuth2UserService() {
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): GoogleOAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val userEmail = oAuth2User.attributes["email"].toString()
        val user = if(dataSource.existsUser(userEmail)) dataSource.retrieveUserByEmail(userEmail)
        else dataSource.createUser(User(userEmail, null, Role.USER))

        return GoogleOAuth2User(oAuth2User, user)
    }
}