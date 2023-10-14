package pjatk.pjwstk.pl.api.controllers.guest

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import pjatk.pjwstk.pl.api.model.Marker

@SpringBootTest
@AutoConfigureMockMvc
internal class GuestMarkersControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should return all markers`() {
        // when/then
        mockMvc.get("/api/markers")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].mapMarker.position.lat"){ value(1.1) }
            }
    }

    @Test
    fun getMarkerById() {
    }

    @Test
    fun getMarkersByUserId() {
    }

    @Test
    fun getMarkersSinceDate() {
    }

    @Test
    fun getMarkersSinceDateToDate() {
    }

    @Test
    fun getMarkersFromYear() {
    }
}