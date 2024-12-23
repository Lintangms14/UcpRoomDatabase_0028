package com.example.ucp2.ui.view.dokter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigation
import com.example.ucp2.viewmodel.DokterEvent
import com.example.ucp2.viewmodel.DokterUiState
import com.example.ucp2.viewmodel.DokterViewModel
import com.example.ucp2.viewmodel.FormErrorStateDokter
import com.example.ucp2.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FormDokter(
    doketerEvent: DokterEvent = DokterEvent(),
    onValueChange: (DokterEvent) -> Unit = {},
    errorState: FormErrorStateDokter = FormErrorStateDokter(),
    modifier: Modifier = Modifier
){
    val listSpesialis = listOf("Dokter Spesialis Bedah", "Dokter Spesialis Syaraf", "Dokter Spesialis Penyakit Dalam", "Dokter Spesialis Jantung")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = doketerEvent.id,
            onValueChange = {
                onValueChange(doketerEvent.copy(id = it))
            },
            label = { Text(text = "ID Dokter") },
            isError = errorState.id != null
        )
        Text(
            text = errorState.id ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = doketerEvent.nama,
            onValueChange = {
                onValueChange(doketerEvent.copy(nama = it))
            },
            label = { Text(text = "Nama") },
            isError = errorState.nama != null
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = doketerEvent.spesialis,
                onValueChange = {},
                readOnly = true,
                label = { Text("Spesialis") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                isError = errorState.spesialis != null
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listSpesialis.forEach { spesialis ->
                    DropdownMenuItem(
                        text = { Text(text = spesialis) },
                        onClick = {
                            onValueChange(doketerEvent.copy(spesialis = spesialis))
                            expanded = false
                        }
                    )
                }
            }
        }
        Text(
            text = errorState.spesialis ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = doketerEvent.klinik,
            onValueChange = {
                onValueChange(doketerEvent.copy(klinik = it))
            },
            label = { Text(text = "Klinik") },
            isError = errorState.klinik != null
        )
        Text(
            text = errorState.klinik ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = doketerEvent.NoTelepon,
            onValueChange = {
                onValueChange(doketerEvent.copy(NoTelepon = it))
            },
            label = { Text(text = "No Telepon") },
            isError = errorState.NoTelpon != null
        )
        Text(
            text = errorState.NoTelpon ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = doketerEvent.jamKerja,
            onValueChange = {
                onValueChange(doketerEvent.copy(jamKerja = it))
            },
            label = { Text(text = "Jam Kerja") },
            isError = errorState.jamKerja != null
        )
        Text(
            text = errorState.jamKerja ?: "",
            color = Color.Red
        )
    }
}

@Composable
fun InsertBodyDokter(
    modifier: Modifier = Modifier,
    onValueChange: (DokterEvent) -> Unit,
    uiState: DokterUiState,
    onClick: () -> Unit
){
    Column  (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormDokter(
            doketerEvent = uiState.dokterEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = modifier.fillMaxWidth()
        ){
            Text(text = "Simpan")
        }
    }
}

object DestinasiInsertDokter : AlamatNavigation {
    override val route = "Insert_Dokter"
}

