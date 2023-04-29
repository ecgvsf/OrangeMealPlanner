package com.example.orangemealplanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.orangemealplanner.Activities.DietActivity
import com.example.orangemealplanner.Activities.FavoritesActivity
import com.example.orangemealplanner.Activities.LogInActivity
import com.example.orangemealplanner.Activities.SettingsActivity
import com.example.orangemealplanner.Fragments.HomeFragment
import com.example.orangemealplanner.Fragments.RecipesFragment
import com.example.orangemealplanner.Fragments.UserFragment
import com.example.orangemealplanner.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    //private lateinit var database: DatabaseReference
    //private lateinit var user : FirebaseUser

    companion object {
        lateinit var flag: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        flag = this

        /*
        database = Firebase.database.reference

        //user = intent.extras?.get("User") as FirebaseUser
        user = Firebase.auth.currentUser!!
        */

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            replaceFragment(HomeFragment(), "Orange Planner", R.id.home_page, R.id.nav_home, View.GONE)

        }



        binding.bottomNavigation.setOnItemSelectedListener {
            when (it) {
                R.id.home_page -> {
                    replaceFragment(HomeFragment(), "My Training Orange", R.id.home_page, R.id.nav_home, View.GONE)
                }
                R.id.recipes_page -> {
                    replaceFragment(RecipesFragment(), "Recipes", R.id.recipes_page, R.id.nav_recipes, View.VISIBLE)
                }
                R.id.user_page -> {
                    replaceFragment(UserFragment(), "Settings User", R.id.user_page, R.id.nav_user, View.GONE)
                }
            }
        }


        binding.orange.setOnClickListener {
            openMenu()
        }

        val settings: ImageView = binding.navView.getHeaderView(0).findViewById(R.id.settings)
        settings.setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment(), "My Training Orange", R.id.home_page, R.id.nav_home, View.GONE)
                }
                R.id.nav_recipes -> {
                    replaceFragment(RecipesFragment(), "Recipes", R.id.recipes_page, R.id.nav_recipes, View.VISIBLE)
                }
                R.id.nav_diet -> {
                    startActivity(Intent(this, DietActivity::class.java))
                }
                R.id.nav_user -> {
                    replaceFragment(UserFragment(), "Settings User", R.id.user_page, R.id.nav_user, View.GONE)
                }

                R.id.nav_pref -> {
                    startActivity(Intent(this, FavoritesActivity::class.java))
                }
                R.id.nav_share -> {
                    val intent= Intent()
                    intent.action=Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:")
                    intent.type="text/plain"
                    startActivity(Intent.createChooser(intent,"Share To:"))
                }
                R.id.nav_log_out -> {
                    //Firebase.auth.signOut()
                    startActivity(Intent(this, LogInActivity::class.java))
                    finish()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


    }



    fun openMenu(){
        binding.drawerLayout.open()
    }

    private fun replaceFragment(fragment: Fragment, name: String, idPage: Int, idNav: Int, visibility: Int) {
        //val bundle = Bundle()
        //bundle.putString("UserId", user.uid)
        //fragment.arguments = bundle
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

        binding.titleText.text = name
        binding.bottomNavigation.setItemSelected(idPage, true)
        binding.navView.setCheckedItem(idNav)
        binding.searchIcon.visibility = visibility
    }

}

