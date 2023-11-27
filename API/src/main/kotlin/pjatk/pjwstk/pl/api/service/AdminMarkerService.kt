package pjatk.pjwstk.pl.api.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource
import pjatk.pjwstk.pl.api.model.Admin
import pjatk.pjwstk.pl.api.model.Marker

@Service
class AdminMarkerService(@Qualifier("mongodb") private val dataSource: MarkerDataSource) {
    fun addMarker(marker: Marker): Marker = dataSource.createMarker(marker)
    fun updateMarker(marker: Marker): Marker = dataSource.updateMarker(marker)
    fun deleteMarker(markerId: String): Unit = dataSource.deleteMarker(markerId)
    fun getAdmins(): Collection<Admin> = dataSource.retrieveAdmins()
    fun getAdminById(adminId: String): Admin = dataSource.retrieveAdminById(adminId)
    fun addAdmin(admin: Admin): Admin = dataSource.createAdmin(admin)
    fun deleteAdmin(adminId: String): Unit = dataSource.deleteAdmin(adminId)
}