package com.example.collegeapp.admin.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.collegeapp.models.DashboardItemModel
import com.example.collegeapp.navigation.Routes
import com.example.collegeapp.ui.theme.Purple200
import com.example.collegeapp.ui.theme.Purple500
import com.example.collegeapp.ui.theme.Purple80
import com.example.collegeapp.ui.theme.TITLE_SIZE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(navController: NavController) {

    val list = listOf (

        DashboardItemModel(
            "Manage Banner",
            Routes.ManageBanner.route
        ),
        DashboardItemModel(
            "Manage Gallery",
            Routes.ManageGallery.route
        ),
        DashboardItemModel(
            "Manage Faculty",
            Routes.ManageFaculty.route
        ),
        DashboardItemModel(
            "Manage College Info",
            Routes.ManageCollegeInfo.route
        ),

    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Admin Dashboard")
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Purple80
                )
            )
        },
        content = {padding ->

            LazyColumn(
                modifier = Modifier.padding(padding)
            ) {

                items(
                    items = list,
                    itemContent = {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate(it.route)
                                }
                        ) {
                            Text(
                                text = it.title,
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = TITLE_SIZE
                            )
                        }
                    }
                )
            }

        }
    )

}

@Preview(showSystemUi = true)
@Composable
fun AdminDashboardPreview(){
    AdminDashboard(navController = rememberNavController())
}