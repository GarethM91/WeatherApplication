package com.gareth.weatherapplication.ui.main

import android.content.res.Configuration
import android.graphics.drawable.shapes.Shape
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.gareth.weatherapplication.R
import com.gareth.weatherapplication.domain.models.WeatherProperty
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun WeatherScreen(
    weatherForecast: List<WeatherProperty>,
    errorStatus: Boolean,
    isRefreshing: Boolean,
    isLoading: Boolean,
    location: String,
    weatherUpdate: () -> Unit) {

    Scaffold(
       topBar = {
           TopAppBar(
               backgroundColor = MaterialTheme.colors.primary,
               elevation = AppBarDefaults.TopAppBarElevation,
               contentPadding = AppBarDefaults.ContentPadding) {
               Text(
                   text = stringResource(R.string.appBar_title),
                   color = Color.White,
                   fontStyle = FontStyle.Normal,
                   fontSize = 23.sp,
                   modifier = Modifier.padding(8.dp, 0.dp)
               )
           }
       }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = weatherUpdate,
            indicator = {state, refreshTrigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTrigger,
                    scale = true,
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    shape = MaterialTheme.shapes.small) }
        ) {
            InitialCircularProgressIndicator(isLoading)
            if(weatherForecast.isEmpty())
                ErrorScreen(errorStatus)

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(weatherForecast){ dayWeatherReport ->
                    if (dayWeatherReport == weatherForecast[0]) {
                        when (LocalConfiguration.current.orientation) {
                            Configuration.ORIENTATION_LANDSCAPE -> {
                                TodayWeatherRowLandscape(
                                    location = location,
                                    dayWeatherReport = dayWeatherReport,
                                    Modifier.fillParentMaxWidth()
                                )
                            }
                            else -> {
                                TodayWeatherRowPortrait(
                                    location = location,
                                    dayWeatherReport = dayWeatherReport,
                                    Modifier.fillParentMaxWidth()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    } else {
                        WeatherRow(
                            dayWeatherReport,
                            Modifier.fillParentMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherRow(dayWeatherReport: WeatherProperty, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 1.dp, vertical = 5.dp)
            .shadow(1.dp, RoundedCornerShape(1.dp), true)
            .height(dimensionResource(R.dimen.forecastRow_height)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            dayWeatherReport.day,
            fontSize = dimensionResource(R.dimen.forecastWeatherRow_fontSize).value.sp,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .padding(20.dp, 0.dp, 0.dp, 0.dp)
        )
        Spacer(modifier = Modifier.width(1.dp))

        Text(
            "${dayWeatherReport.temperature}°",
            fontSize = dimensionResource(R.dimen.forecastWeatherRow_fontSize).value.sp,
            style = MaterialTheme.typography.subtitle2,
        )
        Spacer(modifier = Modifier.width(1.dp))
        WeatherImageLoader(
            imageUrl = dayWeatherReport.imageUrl,
            height = dimensionResource(R.dimen.forecastWeatherImage_Size),
            width = dimensionResource(R.dimen.forecastWeatherImage_Size)
        )

        Text(
            dayWeatherReport.weatherState,
            fontSize = dimensionResource(R.dimen.forecastWeatherRow_fontSize).value.sp,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .padding(0.dp,0.dp,25.dp,0.dp)
        )
    }
}

@Composable
fun TodayWeatherRowPortrait(location: String, dayWeatherReport: WeatherProperty, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 15.dp, vertical = 2.dp)
            .height(dimensionResource(R.dimen.todayRow_height)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier
            .padding(0.dp, 4.dp, 0.dp, 0.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = location,
                fontSize = dimensionResource(R.dimen.location_fontSize).value.sp,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = dayWeatherReport.day,
                fontSize = dimensionResource(R.dimen.currentDay_fontSize).value.sp,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(4.dp,0.dp,0.dp,0.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = dayWeatherReport.dateMonth,
                fontSize = dimensionResource(R.dimen.currentDate_fontSize).value.sp,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(4.dp,0.dp,0.dp,0.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                "${dayWeatherReport.temperature}°",
                fontSize = dimensionResource(R.dimen.currentTemperature_fontSize).value.sp,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(4.dp,0.dp,0.dp,0.dp)
            )
        }

        Column(
            modifier = Modifier.padding(0.dp, 5.dp, 20.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            WeatherImageLoader(
            imageUrl = dayWeatherReport.imageUrl,
            height = dimensionResource(R.dimen.currentWeatherImage_Size),
            width = dimensionResource(R.dimen.currentWeatherImage_Size)
            )
            Spacer(modifier = Modifier.height(7.dp))

            Text(
                dayWeatherReport.weatherState,
                fontSize = dimensionResource(R.dimen.weatherState_fontSize).value.sp,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@Composable
fun TodayWeatherRowLandscape(location: String, dayWeatherReport: WeatherProperty, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 15.dp, vertical = 2.dp)
            .height(dimensionResource(R.dimen.todayRow_height)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier
            .padding(0.dp, 4.dp, 0.dp, 0.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = location,
                fontSize = dimensionResource(R.dimen.location_fontSize).value.sp,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = dayWeatherReport.day,
                fontSize = dimensionResource(R.dimen.currentDay_fontSize).value.sp,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(4.dp,0.dp,0.dp,0.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = dayWeatherReport.dateMonth,
                fontSize = dimensionResource(R.dimen.currentDate_fontSize).value.sp,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(4.dp,0.dp,0.dp,0.dp)
            )
        }

        Text(
            "${dayWeatherReport.temperature}°",
            fontSize = dimensionResource(R.dimen.currentTemperature_fontSize).value.sp,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(0.dp, 50.dp)
        )

        Column(
            modifier = Modifier.padding(0.dp, 5.dp, 20.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            WeatherImageLoader(
                imageUrl = dayWeatherReport.imageUrl,
                height = dimensionResource(R.dimen.currentWeatherImage_Size),
                width = dimensionResource(R.dimen.currentWeatherImage_Size)
            )
            Spacer(modifier = Modifier.height(7.dp))

            Text(
                dayWeatherReport.weatherState,
                fontSize = dimensionResource(R.dimen.weatherState_fontSize).value.sp,
                style = MaterialTheme.typography.subtitle2,
            )
        }
    }
}

@Composable
fun WeatherImageLoader(imageUrl: String, height: Dp, width: Dp) {
    val imagePainter = rememberImagePainter(
        data =  imageUrl,
        builder = {
            size(OriginalSize)
            crossfade(true)
            crossfade(10)
        })
    Image(
        painter = imagePainter,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .height(height)
            .width(width)
    )
}

@Composable
fun ErrorScreen(errorStatus: Boolean) {
    Box(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        if (errorStatus) {
            Image(
                painter = painterResource(R.drawable.ic_broken_image),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )

            Toast.makeText(
                LocalContext.current,
                stringResource(R.string.weather_error),
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}

@Composable
fun InitialCircularProgressIndicator(initialLoad: Boolean) {
    if(initialLoad) {
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = MaterialTheme.colors.primaryVariant,
                strokeWidth = 5.dp)
        }
    }
}

@Preview
@Composable
fun PreviewWeatherScreen() {
    val items = listOf(
        WeatherProperty(1,"2021-10-08", "Clear","c", 16),
        WeatherProperty(1,"2021-10-09", "Showers","s", 16),
        WeatherProperty(1,"2021-10-10", "Light Rain","lr", 16),
        WeatherProperty(1,"2021-10-11", "Heavy Cloud","hc", 16),
        WeatherProperty(1,"2021-10-12", "Heavy Cloud","hc", 16),
        WeatherProperty(1,"2021-10-13", "Light Cloud","lc", 16)
    )

    WeatherScreen(
        weatherForecast = items,
        errorStatus = false,
        isRefreshing = false,
        isLoading = false,
        location = "Belfast",
        weatherUpdate = {}
    )
}

@Preview
@Composable
fun PreviewTodayWeatherRow() {
    val weatherProperty = WeatherProperty(1,"2021-10-08", "Clear","c", 16)

    TodayWeatherRowPortrait(
        location = "Belfast",
        dayWeatherReport = weatherProperty,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun PreviewTodayWeatherLandscape() {
    val weatherProperty = WeatherProperty(1,"2021-10-08", "Clear","c", 16)

    TodayWeatherRowLandscape(
        location = "Belfast",
        dayWeatherReport = weatherProperty,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun PreviewWeatherRow() {
    val weatherProperty = WeatherProperty(1,"2021-10-08", "Clear","c", 16)

    WeatherRow(
        dayWeatherReport = weatherProperty,
        modifier = Modifier.fillMaxWidth()
    )
}