package pjatk.pjwstk.pl.api.datasource.mongodb

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

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
}