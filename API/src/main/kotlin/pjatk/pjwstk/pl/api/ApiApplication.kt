package pjatk.pjwstk.pl.api

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import pjatk.pjwstk.pl.api.model.LatLng
import pjatk.pjwstk.pl.api.model.MapMarker
import pjatk.pjwstk.pl.api.model.Marker
import pjatk.pjwstk.pl.api.model.User
import pjatk.pjwstk.pl.api.model.enums.CrayfishType
import pjatk.pjwstk.pl.api.model.enums.Role
import pjatk.pjwstk.pl.api.service.AdminMarkerService
import pjatk.pjwstk.pl.api.service.UserService
import java.time.LocalDate

@SpringBootApplication
class ApiApplication
{
    @Bean   //REMOVE
    fun init(userService: UserService, markerService: AdminMarkerService): ApplicationRunner {
        return ApplicationRunner { args: ApplicationArguments ->
            println("Initialized with test data injection")

            userService.addUser(User("user@gmail.com", "user", Role.USER))
            userService.addUser(User("admin@gmail.com", "admin", Role.ADMIN))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.799346510440245, 20.422659671349358), "Title", "Desc"), "email@gmail.com", CrayfishType.NOBLE, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79880155843887, 20.419151342157353), "Title", "Desc"), "email@gmail.com", CrayfishType.NOBLE, LocalDate.now(), false))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79870017123868, 20.417552745675373), "Title", "Desc"), "email@gmail.com", CrayfishType.GALICIAN, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79681812683004, 20.425352609047195), "Title", "Desc"), "email@gmail.com", CrayfishType.GALICIAN, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.797942274901445, 20.41983246525674), "Title", "Desc"), "email@gmail.com", CrayfishType.AMERICAN, LocalDate.now(), false))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79499049140765, 20.42944062142749), "Title", "Desc"), "email@gmail.com", CrayfishType.AMERICAN, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79514258556878, 20.410278921314582), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79690430273986, 20.410407667339978), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.now(), false))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79424268709319, 20.413883810025634), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79035135454182, 20.41452754015261), "Title", "Desc"), "email@gmail.com", CrayfishType.GALICIAN, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79046543783123, 20.413669233316643), "Title", "Desc"), "email@gmail.com", CrayfishType.GALICIAN, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79002177885189, 20.42321789686675), "Title", "Desc"), "email@gmail.com", CrayfishType.AMERICAN, LocalDate.now(), false))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.78998375072102, 20.424011830690013), "Title", "Desc"), "email@gmail.com", CrayfishType.AMERICAN, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.7905921966778, 20.426264886134422), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79108655251948, 20.427423600362975), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.now(), false))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79288646559166, 20.429032925680406), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79281041448938, 20.42896855266771), "Title", "Desc"), "email@gmail.com", CrayfishType.GALICIAN, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79349486944583, 20.430298928263458), "Title", "Desc"), "email@gmail.com", CrayfishType.GALICIAN, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.794141288871515, 20.431436184821106), "Title", "Desc"), "email@gmail.com", CrayfishType.AMERICAN, LocalDate.now(), false))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79466095216646, 20.431007031403126), "Title", "Desc"), "email@gmail.com", CrayfishType.AMERICAN, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79492711867775, 20.43059933565604), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79492711867775, 20.429977063199967), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.now(), false))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.795472121025774, 20.42650092051431), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.now(), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79714561163842, 20.42410150829712), "Title", "Desc"), "email@gmail.com", CrayfishType.GALICIAN, LocalDate.parse("2023-11-09"), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79622906310947, 20.425581089937296), "Title", "Desc"), "email@gmail.com", CrayfishType.GALICIAN, LocalDate.parse("2023-11-01"), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79004716621976, 20.412012243676152), "Title", "Desc"), "email@gmail.com", CrayfishType.AMERICAN, LocalDate.parse("2023-02-01"), false))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.78994057380079, 20.41100179767798), "Title", "Desc"), "email@gmail.com", CrayfishType.AMERICAN, LocalDate.parse("2023-02-14"), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79087857779218, 20.405011296403114), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.parse("2022-12-14"), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79288242514236, 20.40883655625333), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.parse("2023-11-16"), false))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.790558806060574, 20.404397811332796), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.parse("2023-11-30"), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79379904680703, 20.41013570110812), "Title", "Desc"), "email@gmail.com", CrayfishType.GALICIAN, LocalDate.parse("2023-11-20"), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79727350053839, 20.425617177294374), "Title", "Desc"), "email@gmail.com", CrayfishType.GALICIAN, LocalDate.parse("2023-09-20"), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79878682290915, 20.422369315157397), "Title", "Desc"), "email@gmail.com", CrayfishType.AMERICAN, LocalDate.parse("2023-09-24"), false))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79859499633524, 20.41901319094919), "Title", "Desc"), "email@gmail.com", CrayfishType.AMERICAN, LocalDate.parse("2023-09-14"), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.7982752834288, 20.424318032439583), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.parse("2023-09-04"), true))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.798808136918765, 20.41930188980581), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.parse("2023-09-03"), false))
            markerService.addMarker(Marker(null, MapMarker(LatLng(53.79744401846501, 20.420528859946444), "Title", "Desc"), "email@gmail.com", CrayfishType.SIGNAL, LocalDate.parse("2023-09-09"), true))

        }
    }
}

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}