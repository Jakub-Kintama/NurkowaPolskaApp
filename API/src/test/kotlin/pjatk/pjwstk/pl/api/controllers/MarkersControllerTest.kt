package pjatk.pjwstk.pl.api.controllers

import org.hamcrest.Matchers
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
internal class MarkersControllerTest(
    @Autowired val mockMvc: MockMvc
) {

    val baseUrl = "/api/markers"

    @Nested
    @DisplayName("GET /api/markers")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetMarkers {

        @Test
        fun `should return all markers`() {
            // when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].id") { value(1) }
                    jsonPath("$[1].id") { value(2) }
                    jsonPath("$[2].id") { value(3) }
                    jsonPath("$[0].mapMarker.position.lat") { value(1.1) }
                }
        }

    }

    @Nested
    @DisplayName("GET /api/markers/{markerId}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetMarkerById {

        @Test
        fun `should return marker with given id`() {
            // given
            val markerId = "000000000000000000000003"

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
            val markerId = "000000000000000000000000"

            // when/then
            mockMvc.get("$baseUrl/$markerId")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("GET /api/markers/user/{userId}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetMarkerByUserId {
        @Test
        fun `should return markers with given user id`() {
            // given
            val userId = 3

            // when/then
            mockMvc.get("$baseUrl/user/$userId")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[*].userId") { value(Matchers.everyItem(Matchers.equalTo(userId))) }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/markers/since/{since}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetMarkersSinceDate {
        @Test
        fun `should return markers older than given date`() {
            // given
            val since = "2023-10-13"

            // when/then
            mockMvc.get("$baseUrl/since/$since")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[*].date") { value(Matchers.everyItem(Matchers.greaterThanOrEqualTo(since))) }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/markers/since/{since}/to/{to}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetMarkersSinceDateToDate {
        @Test
        fun `should return markers between given dates`() {
            // given
            val since = "2023-10-13"
            val to = "2023-10-16"

            // when/then
            mockMvc.get("$baseUrl/since/$since/to/$to")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[*].date") {
                        value(
                            Matchers.everyItem(
                                Matchers.allOf(
                                    Matchers.greaterThanOrEqualTo(since),
                                    Matchers.lessThanOrEqualTo(to)
                                )
                            )
                        )
                    }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/markers/year{year}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetMarkersByYear {
        @Test
        fun `should return markers By given year`() {
            // given
            val year = "2023"

            // when/then
            mockMvc.get("$baseUrl/year/$year")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[*].date.year") { value(Matchers.everyItem(Matchers.equalTo(year))) }
                }
        }
    }
}