package pjatk.pjwstk.pl.api.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.types.ObjectId
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import pjatk.pjwstk.pl.api.model.LatLng
import pjatk.pjwstk.pl.api.model.MapMarker
import pjatk.pjwstk.pl.api.model.Marker
import pjatk.pjwstk.pl.api.model.User
import pjatk.pjwstk.pl.api.model.enums.CrayfishType
import java.time.LocalDate

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
internal class UserMarkersControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    val baseUrl = "/api/marker"

    @Suppress("UNUSED_EXPRESSION")
    @Nested
    @DisplayName("POST /api/marker")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class AddMarker {

        @Test
        fun `should add new marker`() {
            // given
            val newMarker = Marker(
                "000000000000000000000999",
                MapMarker(LatLng(999.999, 999.999), "title 999", "description 999"),
                "999",
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
                .andExpect { content { newMarker } }
        }

        @Test
        fun `should return BAD REQUEST if marker with given id already exist`() {
            // given
            val invalidMarker = Marker(
                "000000000000000000000001",
                MapMarker(LatLng(1.1, 1.1), "title 1", "description 1"),
                "1",
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
                "000000000000000000000002",
                MapMarker(LatLng(2.2, 2.2), "title 2", "description 2"),
                "2",
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
                "000000000000000000000000",
                MapMarker(LatLng(2.2, 2.2), "title 2", "description 2"),
                "2",
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

    @Nested
    @DisplayName("DELETE /api/marker/{markerId}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class DeleteExistingMarker {
        @Test
        fun `should delete marker with given id`() {
            // given
            val markerId = ObjectId("000000000000000000000002")

            // when/then
            mockMvc.delete("$baseUrl/$markerId")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }

            mockMvc.get("/api/markers/${markerId}")
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NOT FOUND if marker with given id does not exist`() {
            // given
            val invalidMarkerId = ObjectId("000000000000000000000000")

            // when/then
            mockMvc.delete("$baseUrl/$invalidMarkerId")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/admin")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetAdmins {
        @Test
        fun `should return all admins`() {
            // when/then
            mockMvc.get("/api/admins")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].id") { value("lol111@gmail.com") }
                    jsonPath("$[1].id") { value("xd222@wp.pl") }
                }
        }
    }

    @Suppress("UNUSED_EXPRESSION")
    @Nested
    @DisplayName("POST /api/admin")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class AddUser {
        @Test
        fun `should add new admin`() {
            // given
            val newUser = User("damn123@gmail.com")

            // when
            val performPost = mockMvc.post("/api/admin") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUser)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newUser))
                    }
                }

            mockMvc.get("/api/admin/${newUser.email}")
                .andExpect { content { newUser } }
        }

        @Test
        fun `should return BAD REQUEST if admin with given id already exist`() {
            // given
            val invalidUser = User("xd222@wp.pl")

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidUser)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }

    @Nested
    @DisplayName("DELETE /api/admin/{adminId}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class DeleteExistingUser {
        @Test
        fun `should delete admin with given id`() {
            // given
            val adminId = "wow333@pjwstk.edu.pl"

            // when/then
            mockMvc.delete("/api/admin/$adminId")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }

            mockMvc.get("/api/admin/${adminId}")
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NOT FOUND if admin with given id does not exist`() {
            // given
            val invalidAdminId = "000@00.00"

            // when/then
            mockMvc.delete("$baseUrl/$invalidAdminId")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    @Test
    fun `should return NOT FOUND if admin with given id does not exist`() {
        // given
        val invalidAdminId = "404@xd"

        // when/then
        mockMvc.delete("$baseUrl/$invalidAdminId")
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
            }
    }
}