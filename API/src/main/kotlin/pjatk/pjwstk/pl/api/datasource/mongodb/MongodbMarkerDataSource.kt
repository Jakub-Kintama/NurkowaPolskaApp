package pjatk.pjwstk.pl.api.datasource.mongodb

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.FindAndReplaceOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource
import pjatk.pjwstk.pl.api.model.Marker
import java.io.IOException
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Repository("mongodbMarker")
class MongodbMarkerDataSource(
    @Autowired private val mongoTemplate: MongoTemplate
) : MarkerDataSource {
    override fun retrieveMarkers(): Collection<Marker> {
        val markers = mongoTemplate.findAll(Marker::class.java)
        return markers.ifEmpty {
            throw IOException("Could not fetch markers from the database")
        }
    }

    override fun retrieveMarkerById(markerId: String): Marker {
        return mongoTemplate.findById(markerId, Marker::class.java)
            ?: throw NoSuchElementException("Could not find a marker with id $markerId.")
    }

    override fun retrieveMarkersByUserEmail(userEmail: String): Collection<Marker> {
        val query = Query.query(
            Criteria.where("userEmail").`is`(userEmail)
        )

        return mongoTemplate.find(query, Marker::class.java)
    }

    override fun retrieveMarkersSinceDate(since: LocalDate): Collection<Marker> {
        val query = Query.query(
            Criteria.where("date").gte(since)
        )

        return mongoTemplate.find(query, Marker::class.java)
    }

    override fun retrieveMarkersSinceDateToDate(since: LocalDate, to: LocalDate): Collection<Marker> {
        val query = Query.query(
            Criteria.where("date").gte(since).lte(to)
        )

        return mongoTemplate.find(query, Marker::class.java)
    }

    override fun retrieveMarkersByYear(year: Int): Collection<Marker> {
        val startDate = LocalDate.of(year, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()
        val endDate = LocalDate.of(year + 1, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()

        val query = Query.query(
            Criteria.where("date").gte(Date.from(startDate)).lt(Date.from(endDate))
        )

        return mongoTemplate.find(query, Marker::class.java)
    }

    override fun createMarker(marker: Marker): Marker {
        val markerId = marker.id
        if (mongoTemplate.exists(Query.query(Criteria.where("_id").`is`(markerId)), Marker::class.java)) {
            throw IllegalArgumentException("Marker with id $markerId already exists.")
        }
        return mongoTemplate.save(marker)
    }

    override fun updateMarker(marker: Marker): Marker {
        val markerId = marker.id
        val query = Query.query(Criteria.where("_id").`is`(markerId))
        mongoTemplate.findOne(query, Marker::class.java)
            ?: throw NoSuchElementException("Could not find a marker with id $markerId.")

        mongoTemplate.findAndReplace(
            query,
            marker,
            FindAndReplaceOptions.options().upsert()
        )

        return marker
    }

    override fun deleteMarker(markerId: String) {
        val query = Query.query(Criteria.where("_id").`is`(markerId))
        val existingMarker = mongoTemplate.findOne(query, Marker::class.java)
            ?: throw NoSuchElementException("Could not find a marker with id $markerId.")

        mongoTemplate.remove(existingMarker)
    }
}