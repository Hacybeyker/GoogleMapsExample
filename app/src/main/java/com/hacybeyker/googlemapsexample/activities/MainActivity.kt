package com.hacybeyker.googlemapsexample.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hacybeyker.googlemapsexample.R
import com.hacybeyker.googlemapsexample.fragment.MapFragment
import com.hacybeyker.googlemapsexample.fragment.WelcomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "Here - llamado", Toast.LENGTH_SHORT).show()
        if (savedInstanceState==null){
            currentFragment = WelcomeFragment()
            changeFragment(currentFragment)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuWelcome -> {
                currentFragment = WelcomeFragment()
            }
            R.id.menuMap -> {
                currentFragment = MapFragment()
            }
        }
        changeFragment(currentFragment)
        return super.onOptionsItemSelected(item)
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}