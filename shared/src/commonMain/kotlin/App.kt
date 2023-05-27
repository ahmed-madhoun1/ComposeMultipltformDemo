import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(platform: String) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    MaterialTheme {
        ModalNavigationDrawer(
            drawerContent = {
                if (platform == "Desktop") {
                    AppDrawer(
                        route = "Home",
                        navigateToHome = { /*navigationActions.navigateToHome()*/ },
                        navigateToSettings = { /*navigationActions.navigateToSettings()*/ },
                        closeDrawer = { coroutineScope.launch { drawerState.close() } },
                        modifier = Modifier
                    )
                }
            }, drawerState = drawerState
        ) {
            Scaffold(
                modifier = Modifier,
                topBar = {
                    if (platform == "Desktop") {
                        TopAppBar(
                            title = {
                                TopAppBar(modifier = Modifier, title = { Text("Home") })
                            },
                            navigationIcon = {
                                if (platform == "Desktop") {
                                    IconButton(
                                        onClick = {
                                            if (drawerState.isOpen) {
                                                coroutineScope.launch { drawerState.close() }
                                            } else {
                                                coroutineScope.launch { drawerState.open() }
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Menu,
                                            contentDescription = "Drawer Icon"
                                        )
                                    }
                                }
                            },
                        )
                    }
                },
                bottomBar = {
                    if (platform == "Mobile") {
                        BottomNavigationComponent()
                    }
                },
            ) {
                Column(
                    modifier = Modifier
                ) {
                    if (platform == "Desktop") {
                        AllCityList(imageHeight = 350, paddingBottom = 0, paddingTop = 60)
                    } else {
                        AllCityList()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AllCityList(
    imageHeight: Int = 140,
    paddingBottom: Int = 80,
    paddingTop: Int = 0,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(bottom = paddingBottom.dp, top = paddingTop.dp)
    ) {
        items(City.list.size) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Image(
                    painter = painterResource(City.list[it].image),
                    contentDescription = "City Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(imageHeight.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                )
                Text(
                    text = City.list[it].name,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun BottomNavigationComponent() {
    BottomAppBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = true,
            onClick = { /*TODO*/ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            selected = false,
            onClick = { /*TODO*/ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") },
            selected = false,
            onClick = { /*TODO*/ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = false,
            onClick = { /*TODO*/ }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    route: String,
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {},
    navigateToSettings: () -> Unit = {},
    closeDrawer: () -> Unit = {}
) {
    ModalDrawerSheet(modifier = Modifier) {
        DrawerHeader(modifier)
        Spacer(modifier = Modifier.padding(10.dp))
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            selected = route == "Home destenation",
            onClick = {
                navigateToHome()
                closeDrawer()
            },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
            shape = MaterialTheme.shapes.small
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Favorite",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            selected = route == "Home destenation",
            onClick = {
                navigateToHome()
                closeDrawer()
            },
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = null) },
            shape = MaterialTheme.shapes.small
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            selected = route == "Home destenation",
            onClick = {
                navigateToHome()
                closeDrawer()
            },
            icon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            shape = MaterialTheme.shapes.small
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            selected = route == "Home destenation",
            onClick = {
                navigateToHome()
                closeDrawer()
            },
            icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = null) },
            shape = MaterialTheme.shapes.small
        )


    }
}


@Composable
fun DrawerHeader(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(10.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = "Home",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

data class City(val name: String, val image: String) {
    companion object {
        val list = listOf(
            City(name = "London", image = "london.jpg"),
            City(name = "Belgium", image = "belgium.jpeg"),
            City(name = "Berlin", image = "berlin.jpeg"),
            City(name = "Dubai", image = "dubai.jpeg"),
            City(name = "French", image = "french.jpeg"),
            City(name = "Morroco", image = "moroco.jpeg"),
            City(name = "Tokyo", image = "tokyo.webp"),
            City(name = "London", image = "london.jpg"),
            City(name = "Belgium", image = "belgium.jpeg"),
            City(name = "Berlin", image = "berlin.jpeg"),
            City(name = "Dubai", image = "dubai.jpeg"),
            City(name = "French", image = "french.jpeg"),
            City(name = "Morroco", image = "moroco.jpeg"),
            City(name = "Tokyo", image = "tokyo.webp"),
        )
    }
}

expect fun getPlatformName(): String