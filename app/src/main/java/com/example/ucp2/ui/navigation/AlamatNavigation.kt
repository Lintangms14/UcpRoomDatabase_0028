package com.example.ucp2.ui.navigation

interface AlamatNavigation {
    val route: String

    object DestinasiHomeDokter : AlamatNavigation {
        override val route = "home"
    }

    object DestinasiHomeJadwal : AlamatNavigation {
        override val route = "Jadwal"
    }

    object DestinasiDetail : AlamatNavigation {
        override val route = "detail"
        const val id = "Id"
        val routeWithArgs = "$route/{$id}"
    }

    object DestinasiUpdate : AlamatNavigation {
        override val route = "update"
        const val id = "Id"
        val routeWithAgrs = "$route/{$id}"
    }

    object DestinasiEdit : AlamatNavigation {
        override val route = "edit"
        const val id = "Id"
        val routeWithArgs = "$route/{$id}"
    }
}