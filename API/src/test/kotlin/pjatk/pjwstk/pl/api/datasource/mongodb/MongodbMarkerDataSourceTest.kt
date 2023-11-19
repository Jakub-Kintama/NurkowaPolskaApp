package pjatk.pjwstk.pl.api.datasource.mongodb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pjatk.pjwstk.pl.api.model.Marker

class MongodbMarkerDataSourceTest {

    private val mongodbMarkerDataSource = MongodbMarkerDataSource()

    @Test
    fun `should provide a collection of markers`() {
        // when
        val markers = mongodbMarkerDataSource.retrieveMarkers()

        // then
        assertThat(markers).isNotEmpty
        assertThat(markers.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide data with acceptable values`() {
        // when
        val markers = mongodbMarkerDataSource.retrieveMarkers()

        // then
        assertThat(markers).allMatch { it is Marker }
        assertThat(markers).allMatch { it.id != null }
        assertThat(markers).allMatch { it.mapMarker.title.isNotBlank() }
        assertThat(markers).allMatch { it.mapMarker.description.isNotBlank() }
        assertThat(markers).allMatch { it.userEmail != "" }
    }
}