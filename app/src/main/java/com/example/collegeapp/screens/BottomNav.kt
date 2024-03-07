package com.example.collegeapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.collegeapp.R
import com.example.collegeapp.models.NavItems
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.collegeapp.models.BottomNavItems
import com.example.collegeapp.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(navController: NavController) {

    val context = LocalContext.current
    val navController1 = rememberNavController()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    val list = listOf(

        NavItems(
            "Website",
            R.drawable.globe
        ),

        NavItems(
            "Notice",
            R.drawable.about_us
        ),

        NavItems(
            "Notes",
            R.drawable.notes
        ),

        NavItems(
            "Contact Us",
            R.drawable.contact_us
        ),

    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            
            ModalDrawerSheet {
                
                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = null,
                    modifier = Modifier
                        .height(220.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                
                Divider()
                
                Text(text = "")
                
                list.forEachIndexed { index, items -> 
                    
                    NavigationDrawerItem(
                        label = {  Text(text = items.title)  },
                        selected = index == selectedItemIndex,
                        onClick = {

                            Toast.makeText(context, items.title, Toast.LENGTH_SHORT).show()

                            scope.launch {
                                drawerState.close()

                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = items.icon),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
                
            }
        },
        content = {
                  Scaffold(
                      topBar = {
                          TopAppBar(
                              title = { Text(text = "College App")},
                              navigationIcon = {
                                  IconButton(onClick = { scope.launch { drawerState.open() }}) {
                                      Icon(
                                          imageVector = Icons.Rounded.Menu,
                                          contentDescription = null,
                                          tint = Color.Black
                                      )
                                  }
                              },
                              colors = TopAppBarColors(containerColor = Color.LightGray, scrolledContainerColor = Color.LightGray, navigationIconContentColor = Color.Black, titleContentColor = Color.Black, actionIconContentColor = Color.Black)
                          )
                      },
                      
                      bottomBar = {
                          MyBottomNav(navController = navController1)
                      }
                  
                  ) {padding ->
                      
                      NavHost(
                          navController = navController1,
                          startDestination = Routes.Home.route,
                          modifier = Modifier.padding(padding)
                      ){

                          composable(route = Routes.Home.route){
                              Home()
                          }
                          composable(Routes.AboutUs.route){
                              com.example.collegeapp.screens.AboutUs()
                          }
                          composable(Routes.Faculty.route){
                              Faculty()
                          }
                          composable(Routes.Gallery.route){
                              Gallery()
                          }

                      }
                      
                  }
        },
        gesturesEnabled = false
    )

}

@Composable
fun MyBottomNav(navController: NavController){

    val backStachEntry = navController.currentBackStackEntryAsState()
    val list = listOf(

        BottomNavItems(
            "Home",
            R.drawable.home,
            Routes.Home.route
        ),
        BottomNavItems(
            "Faculty",
            R.drawable.faculty,
            Routes.Faculty.route
        ),
        BottomNavItems(
            "Gallery",
            R.drawable.gallery,
            Routes.Gallery.route
        ),
        BottomNavItems(
            "About Us",
            R.drawable.about_us,
            Routes.AboutUs.route
        ),
        
    )
    
    BottomAppBar() {
        list.forEach{
            val currRoute = it.routes
            val otherRoute = try {
                backStachEntry.value!!.destination.route
            }catch (e: Exception){
                Routes.Home.route
            }
            val selected = currRoute == otherRoute

            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(it.routes) {
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                } },
                icon = {
                    Icon(
                        painterResource(id = it.icon) ,
                        contentDescription = it.title,
                        modifier = Modifier.size(26.dp)
                    )
                }
            )


        }
    }
}