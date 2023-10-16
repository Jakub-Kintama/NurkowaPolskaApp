package pjatk.pjwstk.pl.api.controllers

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
internal class MarkersControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    val baseUrl = "/api/markers"

    @Nested
    @DisplayName("getMarkers()")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return all markers`() {
            // when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].mapMarker.position.lat") { value(1.1) }
                }
        }

    }

    @Nested
    @DisplayName("getMarkerById()")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetMarkerById {

        @Test
        fun `should return marker by id`() {
            // given
            val markerId = 3

            // when/then
            mockMvc.get("$baseUrl/$markerId")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.id") { value(markerId) }
                    jsonPath("$.mapMarker.position.lat") { value(3.3) }
                }
        }

        @Test
        fun `should return NOT FOUND if marker does not exist`() {
            // given
            val markerId = 99999

            // when/then
            mockMvc.get("$baseUrl/$markerId")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}