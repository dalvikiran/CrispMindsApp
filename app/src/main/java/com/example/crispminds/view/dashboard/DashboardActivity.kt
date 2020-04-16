package com.example.crispminds.view.dashboard

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.crispminds.R
import com.example.crispminds.databinding.ActivityDashboardBinding
import com.example.crispminds.models.UserDTO
import com.example.crispminds.retrofit_services.network_utils.SERVER_URL
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.Utils
import com.example.crispminds.view.userlogin.LoginActivity
import com.example.crispminds.view.mysession.MySessionActivity
import com.example.crispminds.view.dashboard.viewmodel.DashboardViewModel
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import kotlinx.android.synthetic.main.drawer_menu_layout.view.*

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var userDto: UserDTO? = null
    private var userName: String? = null
    private var userProfileUrl: String? = null
    private var userProfileBitmap: Bitmap? = null
    lateinit var navUsrProfileImg: CircleImageView
    private lateinit var navController: NavController

    private lateinit var viewModel: DashboardViewModel
    private lateinit var binding: ActivityDashboardBinding
    var isBackPressed = false
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.bottom_navigation_fragment)
        bottom_navigation_view.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
        NavigationUI.setupWithNavController(drawer_navigation_view, navController)

        viewModel.populateData()

        initNavigationDrawer()

//        onDataObserver()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawer_layout)
    }


    // Assigning the viewmodel and databinding
    private fun initBinding() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        viewModel = Utils.obtainBaseObservable(
            this,
            DashboardViewModel::class.java,
            this,
            binding.root
        )!!
        binding.dashboardViewModel = viewModel

        sharedPreferences = Constants.getSharedPreferences(this)
        userDto = Constants.getUserDTO(
            Constants.decrypt(
                sharedPreferences.getString(
                    Constants.USER_DTO,
                    null
                )
            )
        )

        userName = userDto?.userFirstName + " " + userDto?.userLastName


    }

    /*fun onDataObserver(){
        viewModel.subCategoriesList.observe(this, Observer {
            viewModel.ins
        })
    }*/


    private fun initNavigationDrawer() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
/*
        val toggle =
            ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )

        drawer.addDrawerListener(toggle)
        toggle.syncState()
*/


        val navigationView = findViewById<NavigationView>(R.id.drawer_navigation_view)
        navigationView.setNavigationItemSelectedListener(this)
        val hView = navigationView.getHeaderView(0)
        val mySessionLayout = navigationView.my_session_layout
        val logoutLayout = navigationView.logout_layout

        // Initialise
        val navProfileContentLayout =
            hView.findViewById<LinearLayout>(R.id.nav_profile_content_layout)
        navUsrProfileImg =
            hView.findViewById(R.id.nav_usr_profile_img)
        val navUserName =
            hView.findViewById<TextView>(R.id.nav_user_name)

        hView.setOnClickListener { drawer.closeDrawer(GravityCompat.START) }

/*

        ProfileActivity.bitmapMutableLiveData.observeForever(Observer<Any?> { profileDataDTO ->
            if (profileDataDTO != null) {
                userProfileBitmap = profileDataDTO.getBitmap()
                if (userProfileBitmap != null) {
                    navUsrProfileImg.setImageBitmap(profileDataDTO.getBitmap())
                }
                navUserName.setText(StringEscapeUtils.unescapeJava(profileDataDTO.getName()))
            }
        })
*/

        // Setting data
        if (userProfileBitmap != null) {
            navUsrProfileImg.setImageBitmap(userProfileBitmap)
        } else if (userProfileUrl != null) {

            Glide.with(this).load("$SERVER_URL/$userProfileUrl")
                .placeholder(R.drawable.ic_profile).error(R.drawable.ic_profile)
                .into(navUsrProfileImg)

        }

        if (userName != null) {
            navUserName.text = userName
        }


        // Navigation Item Click Listener
        navProfileContentLayout.setOnClickListener {
            drawer.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.profileFragment)
        }

        mySessionLayout.setOnClickListener {
            drawer.closeDrawer(GravityCompat.START)
            Constants.changeActivity<MySessionActivity>(this)
        }

        logoutLayout.setOnClickListener {
            drawer.closeDrawer(GravityCompat.START)
            Constants.changeActivity<LoginActivity>(this)
            finish()
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // Handle navigation view item clicks here.
        val id: Int = item.itemId
        return true

    }


    override fun onBackPressed() {

        if (navController.currentDestination?.id != R.id.homeFragment) {
            navController.navigateUp()
        } else {

            Handler().postDelayed({
                isBackPressed = false
            }, 2000)

            if (isBackPressed) {
                super.onBackPressed()
            } else {
                isBackPressed = true
                Toast.makeText(this, "Press back again to Exit", Toast.LENGTH_SHORT).show()
            }

        }

    }
}
