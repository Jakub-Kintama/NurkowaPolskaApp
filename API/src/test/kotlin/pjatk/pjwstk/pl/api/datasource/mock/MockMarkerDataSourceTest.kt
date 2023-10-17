package pjatk.pjwstk.pl.api.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pjatk.pjwstk.pl.api.enums.CrayfishType

internal class MockMarkerDataSourceTest {

    private val mockDataSource = MockMarkerDataSource()

    @Test
    fun `should provide a collection of markers`() {
        // when
        val markers = mockDataSource.retrieveMarkers()

        // then
        assertThat(markers).isNotEmpty
        assertThat(markers.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`() {
        // when
        val markers = mockDataSource.retrieveMarkers()

        // then
        assertThat(markers).allMatch { it.id != 0 }
        assertThat(markers).allMatch { it.mapMarker.title.isNotBlank() }
        assertThat(markers).allMatch { it.mapMarker.description.isNotBlank() }
        assertThat(markers).allMatch { it.userId != 0 }
        assertThat(markers).allMatch { it.crayfishType != CrayfishType.UNVERIFIED }
    }
}