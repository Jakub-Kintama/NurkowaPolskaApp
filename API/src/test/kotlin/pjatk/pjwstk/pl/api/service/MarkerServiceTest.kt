package pjatk.pjwstk.pl.api.service

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource

internal class MarkerServiceTest {

    private val dataSource: MarkerDataSource = mockk(relaxed = true)
    private val markerService = MarkerService(dataSource)

    @Test
    fun `should call its data source to retrieve banks`() {
        // when
        markerService.getMarkers()

        // then
        verify(exactly = 1) { dataSource.retrieveMarkers() }

    }
}