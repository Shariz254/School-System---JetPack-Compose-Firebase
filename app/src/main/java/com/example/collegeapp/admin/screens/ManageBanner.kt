package com.example.collegeapp.admin.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.collegeapp.itemview.BannerItemView
import com.example.collegeapp.ui.theme.Purple80
import com.example.collegeapp.utils.Constants.BANNER
import com.example.collegeapp.viewmodels.BannerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageBanner(navController: NavController) {

    val context = LocalContext.current
    val bannerViewModel : BannerViewModel = viewModel()

    //observeAsState --> is a dependancy
    val isUploaded by bannerViewModel.isPosted.observeAsState(false)
    val isDeleted by bannerViewModel.isDeleted.observeAsState(false)
    val bannerList by bannerViewModel.bannerList.observeAsState(null)

    //load existing images
    bannerViewModel.getBanner()

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        imageUri = it
    }

    /** success uploading **/
    LaunchedEffect(isUploaded){
        if (isUploaded){
            Toast.makeText(context, "Image Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
        }
    }

    /** success deleting **/
    LaunchedEffect(isDeleted){
        if (isDeleted){
            Toast.makeText(context, "Image Deleted", Toast.LENGTH_SHORT).show()
            imageUri = null
        }
    }


    Scaffold(

        topBar = {
            TopAppBar(

                title = {
                    Text(text = "Manage Banner", color = Color.Black)
                },

                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple80),

                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },

            )
        },

        floatingActionButton = {
            
            FloatingActionButton(
                onClick = {

                    launcher.launch("image/*")

                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }

    ) {padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {

            if (imageUri != null)
            ElevatedCard(
                modifier = Modifier
                    .padding(8.dp)
            ) {

                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = BANNER,
                    modifier = Modifier
                        .height(220.dp)
                        .fillMaxWidth()
                        .padding(5.dp),
                    contentScale = ContentScale.Crop
                )

                Row {

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp),
                        onClick = { 
                            bannerViewModel.saveImage(imageUri!!)
                        }
                    ) {
                        Text(text = "Add Image")
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp),
                        onClick = { imageUri = null }
                    ) {
                        Text(text = "Cancel")
                    }

                }


            }

            LazyColumn {
                items(bannerList?: emptyList()){
                    BannerItemView(
                        bannerModel = it, 
                        delete = { docId ->
                            bannerViewModel.deleteBanner(docId)
                        }
                    )
                }
            }


        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun ManageBannerView(){
    ManageBanner(rememberNavController())
}