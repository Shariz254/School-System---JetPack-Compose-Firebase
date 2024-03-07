package com.example.collegeapp.navigation

sealed class Routes(val route: String) {

    object Home : Routes("home")

    object Faculty : Routes("faculty")

    object Gallery : Routes("gallery")

    object AboutUs : Routes("about_us")

    object BottomNav : Routes("bottom_nav")

    object AdminDashboard : Routes("admin_dashboard")

    object ManageBanner : Routes("manage_banner")

    object ManageCollegeInfo : Routes("manage_college_info")

    object ManageFaculty : Routes("manage_faculty")

    object ManageGallery : Routes("manage_gallery")

}