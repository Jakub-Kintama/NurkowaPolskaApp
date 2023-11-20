package pjatk.pjwstk.pl.api.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pjatk.pjwstk.pl.api.model.Marker

internal class MockMarkerDataSourceTest {

    private val mockMarkerDataSource = MockMarkerDataSource()

    @Test
    fun `should provide a collection of markers`() {
        // when
        val markers = mockMarkerDataSource.retrieveMarkers()

        // then
        assertThat(markers).isNotEmpty
        assertThat(markers.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`() {
        // when
        val markers = mockMarkerDataSource.retrieveMarkers()

        // then
        assertThat(markers).allMatch { it is Marker }
        assertThat(markers).allMatch { it.id != null }
        assertThat(markers).allMatch { it.mapMarker.title.isNotBlank() }
        assertThat(markers).allMatch { it.mapMarker.description.isNotBlank() }
        assertThat(markers).allMatch { it.userEmail != "" }
    }
}