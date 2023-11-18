package pjatk.pjwstk.pl.api.datasource.mongodb

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import pjatk.pjwstk.pl.api.model.Marker

class MongodbMarkerDataSourceTest {

    private val mongodbMarkerDataSource = MongodbMarkerDataSource()

    @Test
    fun `should provide a collection of markers`() {
        // when
        val markers = mongodbMarkerDataSource.retrieveMarkers()

        // then
        Assertions.assertThat(markers).isNotEmpty
        Assertions.assertThat(markers.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide data with acceptable values`() {
        // when
        val markers = mongodbMarkerDataSource.retrieveMarkers()

        // then
        Assertions.assertThat(markers).allMatch { it is Marker }
        Assertions.assertThat(markers).allMatch { it.id != null }
        Assertions.assertThat(markers).allMatch { it.mapMarker.title.isNotBlank() }
        Assertions.assertThat(markers).allMatch { it.mapMarker.description.isNotBlank() }
        Assertions.assertThat(markers).allMatch { it.userEmail != "" }
    }
}