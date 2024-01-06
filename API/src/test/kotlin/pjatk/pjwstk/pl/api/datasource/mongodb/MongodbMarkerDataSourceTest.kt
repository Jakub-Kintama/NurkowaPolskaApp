package pjatk.pjwstk.pl.api.datasource.mongodb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.data.mongodb.core.MongoTemplate
import pjatk.pjwstk.pl.api.model.LatLng
import pjatk.pjwstk.pl.api.model.MapMarker
import pjatk.pjwstk.pl.api.model.Marker
import pjatk.pjwstk.pl.api.model.enums.MarkerType
import java.time.LocalDate

@Disabled
class MongodbMarkerDataSourceTest {
    private val mongoTemplate = Mockito.mock(MongoTemplate::class.java)
    private val mongodbMarkerDataSource = MongodbMarkerDataSource(mongoTemplate)

    private val expectedMarkers = listOf(
        Marker("1", MapMarker(LatLng(1.1, 1.1), "title 1", "description 1"), "user1", MarkerType.NOBLE, LocalDate.of(2023, 10, 10), true),
        Marker("2", MapMarker(LatLng(2.2, 2.2), "title 2", "description 2"), "user2", MarkerType.AMERICAN, LocalDate.of(2023, 10, 13), false),
        Marker("3", MapMarker(LatLng(3.3, 3.3), "title 3", "description 3"), "user3", MarkerType.SIGNAL, LocalDate.of(2023, 10, 16), true)
    )

    @BeforeEach
    fun setUp() {
        Mockito.`when`(mongoTemplate.findAll(Marker::class.java)).thenReturn(expectedMarkers)
    }

    @Test
    fun `should provide a collection of markers`() {
        // when
        val markers = mongodbMarkerDataSource.retrieveMarkers()

        // then
        assertThat(markers).isNotEmpty
        assertThat(markers.count()).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide data with acceptable values`() {
        // when
        val markers = mongodbMarkerDataSource.retrieveMarkers()

        // then
        assertThat(markers).allSatisfy {
            assertThat(it.id).isNotBlank
            assertThat(it.mapMarker.title).isNotBlank
            assertThat(it.mapMarker.description).isNotBlank
            assertThat(it.userEmail).isNotBlank
            assertThat(it.date).isNotNull()
        }
    }
}