package com.example.bankoui20


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankoui20.ui.theme.Blackbackground
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import com.example.bankoui20.ui.theme.GreenBackground
import kotlin.math.roundToInt
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import kotlin.math.round


@Composable
fun ActivityHeader(
    name: String,
    modifier: Modifier = Modifier
) {

    Row(modifier = Modifier.padding(10.dp)) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }

    }
}

@Composable
fun IncomeAndExpensesButton() {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Blackbackground)
        ) {
            Text(
                text = "Income", color = Color.White,
                modifier = Modifier.padding(5.dp)
            )
        }
        Spacer(modifier = Modifier.padding(40.dp))
        OutlinedButton(
            onClick = { /*TODO*/ },
        ) {
            Text(
                text = "Expenses", color = Color.Black,
                modifier = Modifier.padding(5.dp)
            )
        }


    }
}

@Composable
fun BalanceHeadline() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(text = "Balance", fontSize = 20.sp, color = Color.Black)
    }
}

@Composable
fun ActivityButtonSection(
    modifier: Modifier
) {
    val miniWidth = 45.dp
    val miniHeight = 40.dp
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier) {
        ActionButton(
            text = " oct - feb ",
            icon = Icons.Default.ArrowDropDown,
            modifier = Modifier
                .defaultMinSize(miniWidth)
                .height(height = miniHeight)
        )
    }
}

@Composable
fun ActivityThirdItemHeading() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
//            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Text(text = "$ 15,560.00", fontWeight = FontWeight.Bold, fontSize = 29.sp)
        ActivityButtonSection(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun QuadLineChart(
    data: List<Pair<Int, Double>> = emptyList(),
    modifier: Modifier = Modifier
) {
    val spacing = 100f
    val graphColor = GreenBackground
    val transparentGraphColor = remember { graphColor.copy(alpha = 0.5f) }
    val upperValue = remember { (data.maxOfOrNull { it.second }?.plus(1))?.roundToInt() ?: 0 }
    val lowerValue = remember { (data.minOfOrNull { it.second }?.toInt() ?: 0) }
    val density = LocalDensity.current

    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / data.size
        (data.indices step 2).forEach { i ->
            val hour = data[i].first
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    spacing + i * spacePerHour,
                    size.height,
                    textPaint
                )
            }
        }

        val priceStep = (upperValue - lowerValue) / 5f
        (0..4).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(lowerValue + priceStep * i).toString(),
                    30f,
                    size.height - spacing - i * size.height / 5f,
                    textPaint
                )
            }
        }

        var medX: Float
        var medY: Float
        val strokePath = Path().apply {
            val height = size.height
            data.indices.forEach { i ->
                val nextInfo = data.getOrNull(i + 1) ?: data.last()
                val firstRatio = (data[i].second - lowerValue) / (upperValue - lowerValue)
                val secondRatio = (nextInfo.second - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (firstRatio * height).toFloat()
                val x2 = spacing + (i + 1) * spacePerHour
                val y2 = height - spacing - (secondRatio * height).toFloat()
                if (i == 0) {
                    moveTo(x1, y1)
                } else {
                    medX = (x1 + x2) / 2f
                    medY = (y1 + y2) / 2f
                    quadraticBezierTo(x1 = x1, y1 = y1, x2 = medX, y2 = medY)
                }
            }
        }

        drawPath(
            path = strokePath,
            color = GreenBackground,
            style = Stroke(
                width = 1.5.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
            lineTo(size.width - spacePerHour, size.height - spacing)
            lineTo(spacing, size.height - spacing)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )

    }
}

@Composable
fun ImplementationOfQuadLineChart() {
    val data = listOf(
        Pair(6, 111.45),
        Pair(7, 111.0),
        Pair(8, 113.45),
        Pair(9, 112.25),
        Pair(10, 116.45),
        Pair(11, 113.35),
        Pair(12, 118.65),
        Pair(13, 110.15),
        Pair(14, 113.05),
        Pair(15, 114.25),
        Pair(16, 116.35),
        Pair(17, 117.45),
        Pair(18, 112.65),
        Pair(19, 115.45),
        Pair(20, 111.85)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Center
    ) {
        QuadLineChart(
            data,
            Modifier
                .fillMaxWidth()
                .height(180.dp)
                .align(CenterHorizontally)
        )
    }
}

@Composable
fun MonthHeadingRow() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, bottom = 6.dp)
    ) {
        Text(text = "Oct", fontSize = 16.sp, fontWeight = FontWeight.Normal)
        Text(text = "Nov", fontSize = 16.sp, fontWeight = FontWeight.Normal)
        Text(text = "Dec", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(text = "Jan", fontSize = 16.sp, fontWeight = FontWeight.Normal)
        Text(text = "Feb", fontSize = 16.sp, fontWeight = FontWeight.Normal)
    }
}

@Composable
fun PaymentHistoryText() {
    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        Arrangement.Start
    ) {
        Text(text = "Payment History", fontSize = 26.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun FirstCardView() {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .background(Color.White),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .width(210.dp)
                .height(200.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color(0XFF6AE9B6)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Image(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "",
                    Modifier
                        .size(40.dp)
                        .background(Color.White)
                        .padding(2.dp)
                )
                Text(
                    text = "Shoping",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = "Jul 12,2022",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
                
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "+$132.7",
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Image(painter = painterResource(id = R.drawable.upright), contentDescription ="",Modifier.size(50.dp))
                }

            }
        }
    }
}

@Composable
fun SecondCardView() {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .background(Color.White),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .width(210.dp)
                .height(200.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color(0XFFE79DFE)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Image(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "",
                    Modifier
                        .size(40.dp)
                        .background(Color.White)
                        .padding(2.dp)
                )
                Text(
                    text = "Paypal",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = "Jul 09,2022",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "+$3502",
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Image(painter = painterResource(id = R.drawable.upright), contentDescription ="",Modifier.size(50.dp))
                }

            }
        }
    }
}

@Composable
fun ThirdCardView() {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .background(Color.White),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .width(210.dp)
                .height(200.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color(0XFFE6BD6E)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Image(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "",
                    Modifier
                        .size(40.dp)
                        .background(Color.White)
                        .padding(2.dp)
                )
                Text(
                    text = "Paypal",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = "Jul 09,2022",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "+$3502",
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Image(painter = painterResource(id = R.drawable.upright), contentDescription ="",Modifier.size(50.dp))
                }

            }
        }
    }
}

@Composable
fun LazyRowForTransactionHistory() {
    LazyRow(modifier = Modifier.padding(6.dp)){
        item {
            FirstCardView()
            Spacer(modifier = Modifier.padding(1.dp))
            SecondCardView()
            Spacer(modifier = Modifier.padding(1.dp))
            ThirdCardView()
        }
    }
}

@Preview
@Composable
fun PreviewActivityItems() {
    LazyRowForTransactionHistory()
}