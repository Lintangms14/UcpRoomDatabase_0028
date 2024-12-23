package com.example.ucp2.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.dokter.DestinasiInsertDokter
import com.example.ucp2.ui.view.dokter.HomeDokterView
import com.example.ucp2.ui.view.dokter.InsertDokterView
import com.example.ucp2.ui.view.jadwal.DestinasiInsertJadwal
import com.example.ucp2.ui.view.jadwal.DetailJdwlView
import com.example.ucp2.ui.view.jadwal.HomeJdwlView
import com.example.ucp2.ui.view.jadwal.InsertJdwlView
import com.example.ucp2.ui.view.jadwal.UpdateJdwlView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = AlamatNavigation.DestinasiHomeDokter.route
    ) {
        composable(
            route = AlamatNavigation.DestinasiHomeDokter.route
        ) {
            HomeDokterView(
                onAddDktr = {
                    navController.navigate(DestinasiInsertDokter.route)
                },
                onAddJdwl = {
                    navController.navigate(AlamatNavigation.DestinasiHomeJadwal.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertDokter.route
        ) {
            InsertDokterView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = AlamatNavigation.DestinasiHomeJadwal.route
        ) {
            HomeJdwlView(
                onBack = {
                    navController.popBackStack()
                },
                onDetailClick = { id ->
                    navController.navigate("${AlamatNavigation.DestinasiDetail.route}/$id")
                    println(
                        "PengelolaHalaman: id = $id"
                    )
                },
                onAddjadwal = {
                    navController.navigate(DestinasiInsertJadwal.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertJadwal.route
        ) {
            InsertJdwlView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            AlamatNavigation.DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(AlamatNavigation.DestinasiDetail.id) {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString(AlamatNavigation.DestinasiDetail.id)
            id?.let { id ->
                DetailJdwlView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${AlamatNavigation.DestinasiEdit.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            AlamatNavigation.DestinasiEdit.routeWithArgs,
            arguments = listOf(
                navArgument(AlamatNavigation.DestinasiEdit.id) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateJdwlView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
    }
}