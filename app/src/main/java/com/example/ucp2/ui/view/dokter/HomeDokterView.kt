package com.example.ucp2.ui.view.dokter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.data.entity.Dokter
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.R
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.viewmodel.HomeDokterUiState
import com.example.ucp2.viewmodel.HomeDokterViewModel
import com.example.ucp2.viewmodel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDokter(
    Dokter: Dokter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    val spesialisColor = when (Dokter.spesialis) {
        "Dokter Spesialis Bedah" -> Color.Red
        "Dokter Spesialis Syaraf" -> Color.Blue
        "Dokter Spesialis Penyakit Dalam" -> Color.Green
        "Dokter Spesialis Jantung" -> Color.Magenta
        else -> Color.Gray
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = Dokter.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = Dokter.id,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = Dokter.spesialis,
                    fontWeight = FontWeight.Bold,
                    color = spesialisColor
                )
            }
        }
    }
}

@Composable
fun ListDokter(
    listDktr: List<Dokter>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listDktr,
            itemContent = { Dokter ->
                CardDokter(
                    Dokter = Dokter,
                    onClick = { onClick(Dokter.id) },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        )
    }
}

@Composable
fun BodyHomeDokterView(
    homeDokterUiState: HomeDokterUiState,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    when {
        homeDokterUiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        homeDokterUiState.isError -> {
            LaunchedEffect(homeDokterUiState.errorMessages) {
                homeDokterUiState.errorMessages?.let { message ->
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(message)
                    }
                }
            }
        }

        homeDokterUiState.listDokter.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak Ada Data Dokter",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            ListDokter(
                listDktr = homeDokterUiState.listDokter,
                modifier = modifier
            )
        }
    }
}

@Composable
fun HomeDokterView(
    viewModel: HomeDokterViewModel = viewModel(factory = PenyediaViewModel.factory),
    onAddDktr: () -> Unit = { },
    onAddJdwl: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF6A1B9A),
                                Color(0xFF8E24AA),
                                Color(0xFFBA68C8)
                            )
                        )
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 36.dp, horizontal = 24.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.kesehatan),
                            contentDescription = "Header Image",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .border(5.dp, Color.White, CircleShape)
                                .shadow(10.dp, CircleShape)
                        )

                        Spacer(modifier = Modifier.width(24.dp))

                        Column {
                            Text(
                                text = "Sehat Pedia",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        blurRadius = 10f
                                    )
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Kesehatan Anda, Prioritas Kami",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White.copy(alpha = 0.85f),
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = Color.Black.copy(alpha = 0.3f),
                                        blurRadius = 6f
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        val homeDokterUiState by viewModel.HomeDokterUiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ButtonCard(
                    text = "Tambah Dokter",
                    backgroundColor = Color(0xFF4CAF50),
                    icon = Icons.Default.Person,
                    textColor = Color.White,
                    onClick = onAddDktr
                )

                ButtonCard(
                    text = "Jadwal Pasien",
                    backgroundColor = Color(0xFF2196F3),
                    icon = Icons.Default.List,
                    textColor = Color.White,
                    onClick = onAddJdwl
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Daftar Dokter",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6A1B9A)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BodyHomeDokterView(
                        homeDokterUiState = homeDokterUiState,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}




@Composable
fun ButtonCard(
    text: String,
    backgroundColor: Color,
    icon: ImageVector,
    textColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .clickable(onClick = onClick)
            .shadow(8.dp, shape = RoundedCornerShape(12.dp))
            .background(backgroundColor, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = textColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = textColor
            )
        }
    }
}
