package com.example.bankoui20

import android.annotation.SuppressLint
import android.icu.text.RelativeDateTimeFormatter.Style
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bankoui20.ui.theme.BankoUi20Theme
import com.example.bankoui20.ui.theme.Blackbackground

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankoUi20Theme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = R.drawable.home
                                ),
                                BottomNavItem(
                                    name = "Wallet",
                                    route = "wallet",
                                    icon = R.drawable.wallet
                                ),
                                BottomNavItem(
                                    name = "Activity",
                                    route = "activity",
                                    icon = R.drawable.activity
                                ),
                                BottomNavItem(
                                    name = "Profile",
                                    route = "profile",
                                    icon = R.drawable.person
                                )
                            ),

                            navController = navController, onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(navController)
        }
        composable("home") {
            HomeScreen()
        }
        composable("wallet") {
            WalletScreen()
        }
        composable("activity") {
            ActivityScreen()
        }
        composable("profile") {
            ProfileScreen()
        }

    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = Modifier,
        backgroundColor = Color.White,
        elevation = 6.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(selected = selected, onClick = { onItemClick(item) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = {
                                Text(text = item.badgeCount.toString())
                            }
                            )
                            {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = ""
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = ""
                            )
                        }
                        if (selected) {
                            Text(text = item.name, textAlign = TextAlign.Center, fontSize = 10.sp)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(5.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderProfileComponent()
        Spacer(modifier = Modifier.padding(10.dp))
        VisaCard()
        Spacer(modifier = Modifier.padding(10.dp))
        FirstColumnInfo()
        Spacer(modifier = Modifier.padding(10.dp))
        SecondItemHeading()
        SendColumnInfo()
        ThirdItemHeading()
        ///////////////////////////////
        ThirdItemColumn()
        ThirdItemColumn()
        ThirdItemColumn()
        ThirdItemColumn()

    }
}

@Composable
fun WalletScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "WalletScreen")
    }
}

@Composable
fun ActivityScreen() {
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(5.dp)
        .verticalScroll(rememberScrollState())) {

        ActivityHeader(name = "Your Balance")
        Spacer(modifier = Modifier.padding(10.dp))
        IncomeAndExpensesButton()
        BalanceHeadline()
        ActivityThirdItemHeading()
        ImplementationOfQuadLineChart()
        MonthHeadingRow()
        PaymentHistoryText()
        LazyRowForTransactionHistory()
    }
}

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "ProfileScreen")
    }
}

/**
 * Header Profile*/
@Composable
fun HeaderProfileComponent() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
    ) {
        // Profile Image
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    text = "Hello Robert",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Welcome Back",
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Start
                )
            }
        }

        // Badge
        BadgedBox(badge = { Badge(backgroundColor = Color.Green) }) {
            Icon(Icons.Default.Notifications, contentDescription = "")
        }
    }
}

/**Visa Card*/
@Composable
fun VisaCard() {
    LazyRow(
        Modifier.height(160.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CardUIItems(
                imagePainter = painterResource(id = R.drawable.asst),
                title = "Visa",
                subtitle = "Balance",
                header = "$444444",
                footer = "Ex 05/20",
                backgroundColor = Blackbackground
            )
        }
        item {
            CardUIItems(
                imagePainter = painterResource(id = R.drawable.asst),
                title = "Visa",
                subtitle = "Balance",
                header = "$444444",
                footer = "Ex 05/20",
                backgroundColor = Blackbackground
            )
        }
    }
}

@Composable
fun CardUIItems(
    title: String = "",
    subtitle: String = "",
    header: String = "",
    footer: String = "",
    backgroundColor: Color = Color.Transparent,
    imagePainter: Painter
) {
    Card(
        Modifier.width(300.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = backgroundColor,
        elevation = 0.dp
    ) {
        Row {
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = title, fontSize = 24.sp, color = Color.White)
                Text(
                    text = subtitle,
                    fontSize = 10.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(text = header, fontSize = 28.sp, color = Color.White)

                Column(Modifier.padding(top = 19.dp)) {
                    Text(text = footer, fontSize = 11.sp, color = Color.White)
                }
            }
            Image(
                painter = imagePainter, contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                alignment = Alignment.CenterEnd,
                contentScale = ContentScale.Fit
            )
        }
    }


}

/**First Item Column*/
@Composable
fun FirstColumnInfo() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        FirstColumn(R.drawable.send, text = "Send")
        FirstColumn(R.drawable.download, text = "Request")
        FirstColumn(R.drawable.wallet, text = "E-wallet")
        FirstColumn(R.drawable.dotsthree, text = "More")
    }
}

@Composable
fun FirstColumn(@DrawableRes iconDrawable: Int, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconDrawable),
            contentDescription = "",
            modifier = Modifier
                .height(54.dp)
                .width(54.dp)
                .clip(CircleShape)
                .background(Color.Yellow)
                .padding(10.dp)
        )
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

/*Second Item Heading and ActionButton*/
@Composable
fun SecondItemHeading() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Text(text = "Send To", fontWeight = FontWeight.Bold, fontSize = 26.sp)
        Text(text = "View All", fontSize = 16.sp)
    }
}

@Composable
fun ButtonSection(
    modifier: Modifier
) {
    val miniWidth = 45.dp
    val miniHeight = 40.dp
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier) {
        ActionButton(
            text = "Today",
            icon = Icons.Default.ArrowDropDown,
            modifier = Modifier
                .defaultMinSize(miniWidth)
                .height(height = miniHeight)
        )
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: ImageVector? = null
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(5.dp)
    ) {
        if (text != null){
            Text(text = text, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
    if (icon != null){
        Icon(imageVector = icon, contentDescription ="")
    }
}

/**Lazy Row*/
@Composable
fun SendColumnInfo() {
    LazyRow(modifier = Modifier.padding(16.dp)) {
        // Add a single item

        item {
            FirstColumn(R.drawable.add, text = "Add")
            Spacer(modifier = Modifier.padding(20.dp))

            FirstColumn(R.drawable.person, text = "Person1")
            Spacer(modifier = Modifier.padding(20.dp))

            FirstColumn(R.drawable.person, text = "Person2")
            Spacer(modifier = Modifier.padding(20.dp))

            FirstColumn(R.drawable.person, text = "Person3")
            Spacer(modifier = Modifier.padding(20.dp))

            FirstColumn(R.drawable.person, text = "Person4")
            Spacer(modifier = Modifier.padding(20.dp))

            FirstColumn(R.drawable.person, text = "Person5")
            Spacer(modifier = Modifier.padding(20.dp))
        }
    }
}

/**ThirdItemHeading*/
@Composable
fun ThirdItemHeading() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
//            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Text(text = "Transactions", fontWeight = FontWeight.Bold, fontSize = 29.sp)
        ButtonSection(modifier = Modifier.height(30.dp))
    }
}

/**ThirdItemsColumn*/

@Composable
fun ThirdItemColumn() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
    ) {
        // Profile Image
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    text = "Amazon Pay",
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Payment",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Start
                )
            }
        }

        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                text = "-$200",
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "10:32am",
                fontSize = 15.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Preview
@Composable
fun ActivityScreenPreview() {
    ActivityScreen()
}


