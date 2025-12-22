package com.example.rabia

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SepetEkran(navController: androidx.navigation.NavController, sepetViewModel: SepetViewModel) {
    val sepetListesi = sepetViewModel.sepetListesi

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sepetim (${sepetListesi.size} Ürün)") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            items(sepetListesi) { kahve ->
                SepetUrunKarti(urun = kahve, sepetViewModel = sepetViewModel)
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun SepetUrunKarti(urun: Coffee, sepetViewModel: SepetViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = urun.ad, style = MaterialTheme.typography.titleMedium)
            Text(text = "Fiyat: ${urun.fiyat} TL", style = MaterialTheme.typography.bodyMedium)
        }

        Button(onClick = { sepetViewModel.urunCikar(urun) }) {
            Text(text = "Çıkar")
        }
    }
}