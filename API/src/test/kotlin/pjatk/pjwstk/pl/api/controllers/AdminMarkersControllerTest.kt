package pjatk.pjwstk.pl.api.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import pjatk.pjwstk.pl.api.enums.CrayfishType
import pjatk.pjwstk.pl.api.model.LatLng
import pjatk.pjwstk.pl.api.model.MapMarker
import pjatk.pjwstk.pl.api.model.Marker
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
internal class AdminMarkersControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    val baseUrl = "/api/marker"

    @Nested
    @DisplayName("POST /api/marker")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddMarker {

        @Test
        fun `should add new marker`() {
            // given
            val newMarker = Marker(
                999,
                MapMarker(LatLng(999.999, 999.999), "title 999", "description 999"),
                999,
                CrayfishType.SIGNAL,
                LocalDate.now(),
                false
            )

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newMarker)
            }

            // then
            performPost
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.id") { value(newMarker.id) }
                    jsonPath("$.mapMarker.position.lat") { value(newMarker.mapMarker.position.lat) }
                    jsonPath("$.mapMarker.position.lng") { value(newMarker.mapMarker.position.lng) }
                    jsonPath("$.mapMarker.title") { value(newMarker.mapMarker.title) }
                    jsonPath("$.mapMarker.description") { value(newMarker.mapMarker.description) }
                    jsonPath("$.userId") { value(newMarker.userId) }
                    jsonPath("$.crayfishType") { value(newMarker.crayfishType.toString()) }
                    jsonPath("$.date") { value(newMarker.date.toString()) }
                    jsonPath("$.verified") { value(newMarker.verified) }
                }
        }

        @Test
        fun `should return BAD REQUEST marker with given id already exist`(){
            // given
            val invalidMarker = Marker(
                1,
                MapMarker(LatLng(1.1, 1.1), "title 1", "description 1"),
                1,
                CrayfishType.SIGNAL,
                LocalDate.now(),
                false
            )

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidMarker)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }
}