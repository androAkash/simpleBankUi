package com.example.bankoui20

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    
    LaunchedEffect(key1 = false){
        delay(1000L)
        navController.navigate("home")
    }
    
    Row(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxSize()
            .background(Color.White),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(),
            backgroundColor = Color.White
        ) {
            Image(
                painter = painterResource(id = R.drawable.splashtwo),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), contentScale = ContentScale.Fit
            )
        }
    }



}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}