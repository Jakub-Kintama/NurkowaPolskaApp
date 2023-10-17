package pjatk.pjwstk.pl.api.controllers

import com.fasterxml.jackson.databind.ObjectMapper
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
import org.springframework.test.web.servlet.patch
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
    @TestInstance(Lifecycle.PER_CLASS)
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
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newMarker))
                    }
                }

            mockMvc.get("/api/markers/${newMarker.id}")
                .andExpect { content { json(objectMapper.writeValueAsString(newMarker)) } }
        }

        @Test
        fun `should return BAD REQUEST if marker with given id already exist`() {
            // given
            val invalidMarker = Marker(
                1,
                MapMarker(LatLng(1.1, 1.1), "title 1", "description 1"),
                1,
                CrayfishType.SIGNAL,
                LocalDate.now(),
                true
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

    @Nested
    @DisplayName("PATCH /api/marker")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PatchExistingMarker {
        @Test
        fun `should update existing marker`() {
            // given
            val updatedMarker = Marker(
                2,
                MapMarker(LatLng(2.2, 2.2), "title 2", "description 2"),
                2,
                CrayfishType.AMERICAN,
                LocalDate.parse("2023-10-13"),
                true
            )

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedMarker)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedMarker))
                    }
                }

            mockMvc.get("/api/markers/${updatedMarker.id}")
                .andExpect { content { json(objectMapper.writeValueAsString(updatedMarker)) } }
        }

        @Test
        fun `should return BAD REQUEST if marker with given id does not exist`() {
            // given
            val invalidMarker = Marker(
                0,
                MapMarker(LatLng(2.2, 2.2), "title 2", "description 2"),
                2,
                CrayfishType.AMERICAN,
                LocalDate.parse("2023-10-13"),
                true
            )

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidMarker)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}