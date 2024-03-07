package com.example.collegeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.collegeapp.admin.screens.*
import com.example.collegeapp.screens.*
import com.example.collegeapp.utils.Constants.isAdmin

@Composable
fun NavGraph(navController:NavHostController) {


    NavHost(
        navController = navController,
        startDestination = if (isAdmin) Routes.AdminDashboard.route else Routes.BottomNav.route
    ){

        composable(Routes.BottomNav.route){
            BottomNav(navController)
        }


        composable(Routes.Home.route){
            Home()
        }


        composable(Routes.AboutUs.route){
            AboutUs()
        }


        composable(Routes.Faculty.route){
            Faculty()
        }


        composable(Routes.Gallery.route){
            Gallery()
        }

        /**----ADMIN------**/

        composable(Routes.AdminDashboard.route){
            AdminDashboard(navController)
        }

        composable(Routes.ManageCollegeInfo.route){
            ManageCollegeInfo()
        }

        composable(Routes.ManageBanner.route){
            ManageBanner(navController)
        }

        composable(Routes.ManageFaculty.route){
            ManageFaculty()
        }

        composable(Routes.ManageGallery.route){
            ManageGallery()
        }


    }

}