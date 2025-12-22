package com.example.rabia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RabiaTheme {
                val sepetViewModel = remember { SepetViewModel() }
                UygulamaGezinme(sepetViewModel = sepetViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UygulamaGezinme(sepetViewModel: SepetViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "katalog_ekrani",
        modifier = Modifier.fillMaxSize()
    )
    {

        composable("katalog_ekrani") { KatalogEkran(navController, sepetViewModel =sepetViewModel) }
        composable("sepet_ekrani")
        { SepetEkran(navController = navController, sepetViewModel = sepetViewModel) }

        composable(
            route = "detay_ekrani/{urunKodu}",
            arguments = listOf(navArgument("urunKodu") { type = NavType.StringType })
        ) { backStackEntry ->
            val urunKodu = backStackEntry.arguments?.getString("urunKodu")
            UrunDetayEkran(navController, urunKodu)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KatalogEkran(navController: NavController,sepetViewModel: SepetViewModel) {
    val sepetSayisi = sepetViewModel.sepetListesi.size
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("☕ Kahve Kataloğu") },
                actions = {
                    Text(
                        text = "Sepetim ($sepetSayisi)",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    IconButton(onClick = {navController.navigate("sepet_ekrani")}) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Sepet"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Bir kahve seçin:",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(kahveListesi) { urun ->

                    Card(
                        onClick = { navController.navigate("detay_ekrani/${urun.kod}") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = urun.resim),
                                contentDescription = urun.ad,
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(end = 16.dp)
                            )
                            Column {
                                Text(urun.ad, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(urun.aciklama, style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Fiyat: ${urun.fiyat} TL")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f)) // --> Butonu sağa yaslamak için araya boşluk atar

                    Button(
                        onClick = {
                            // Tıklandığında SepetViewModel'i kullanarak ürünü ekle
                            sepetViewModel.urunEkle(urun)
                        },
                        // Butonu Card'ın sağ kenarından biraz uzaklaştırır
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text("Sepete Ekle")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UrunDetayEkran(navController: NavController, urunKodu: String?) {

    val urun = kahveListesi.find { it.kod == urunKodu }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("☕ Kahve Detayı") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.tertiary,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { padding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (urun == null) {
                Text("Ürün bulunamadı.", color = MaterialTheme.colorScheme.error)
                return@Column
            }

            Image(
                painter = painterResource(id = urun.resim),
                contentDescription = urun.ad,
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = urun.ad,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = urun.aciklama,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Kataloğa Geri Dön")
            }
        }
    }
}