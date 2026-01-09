package com.cmt.view.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.helper.setSnackBar
import com.cmt.view.fragment.*
import com.cmt.viewModel.activity.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    var doubleBackToExitPressedOnce = false
    private val mainFragment = MainFragment()
    private val categoriesFragment = CategoriesFragment()

//    private val onlineTestFragment = OnlineTestFragment()

    private val eBookFragment = EBookFragment()
    private val currentAffairs = CurrentAffairsListFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            viewModel = ViewModelProvider(this@MainActivity).get(MainActivityViewModel::class.java)
            lifecycleOwner = this@MainActivity

        }
        setContentView(binding.root)

        binding.bottomNav.setOnNavigationItemSelectedListener { item: MenuItem ->
            onNavigationItemSelected(
                item
            )
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, MainFragment())
            .commit()

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment = supportFragmentManager.findFragmentById(binding.mainContainer.id)

        when (item.itemId) {
            R.id.home -> {
                binding.title.visibility = View.GONE
                if (fragment != mainFragment) {
                    supportFragmentManager.beginTransaction()
                        .remove(supportFragmentManager.findFragmentById(binding.mainContainer.id)!!)
                        .commit()

                    loadFragment(mainFragment)
                }
                return true
            }
            /*R.id.categories -> {
                binding.title.isVisible = true
                binding.title.text = getString(R.string.tit_categories)
                if (fragment != categoriesFragment) {
                    supportFragmentManager.beginTransaction()
                        .remove(supportFragmentManager.findFragmentById(binding.mainContainer.id)!!)
                        .commit()


                    loadFragment(categoriesFragment)
                }
                return true

            }*/
            R.id.online_tests -> {
                binding.title.isVisible = true
                binding.title.text = getString(R.string.cources)

                if (fragment != categoriesFragment) {
                    supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentById(binding.mainContainer.id)!!).commit()
                    loadFragment(categoriesFragment)
                }
                return true
            }
            R.id.current_affairs -> {
                binding.title.isVisible = true
                binding.title.text = getString(R.string.tit_current)

                if (fragment != currentAffairs) {
                    supportFragmentManager.beginTransaction()
                        .remove(supportFragmentManager.findFragmentById(binding.mainContainer.id)!!)
                        .commit()

                    loadFragment(currentAffairs)
                }
                return true
            }
            R.id.profile -> {
                binding.title.visibility = View.VISIBLE
                binding.title.text = getString(R.string.txt_profile)
                if (fragment != profileFragment) {
                    supportFragmentManager.beginTransaction()
                        .remove(supportFragmentManager.findFragmentById(binding.mainContainer.id)!!)
                        .commit()
                    loadFragment(profileFragment)
                }
                return true

            }

        }
        return false
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, fragment)
            .commit()
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(binding.mainContainer.id)
        if (currentFragment is MainFragment) {
            if (doubleBackToExitPressedOnce) {
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
                alertDialog.setMessage("Are You Really Want To Exit")
                alertDialog.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        finishAffinity()
                    }
                })
                alertDialog.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialogInterface: DialogInterface?, p1: Int) {

                        dialogInterface?.dismiss()
                    }
                })

                alertDialog.show().create()

                return
            }
            this.doubleBackToExitPressedOnce = true
            setSnackBar("Press again to exit")
            Handler(Looper.getMainLooper()).postDelayed(
                { doubleBackToExitPressedOnce = false },
                2000
            )
        } else if (currentFragment is CategoriesFragment) {
            if (currentFragment != mainFragment) {
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit)
                /*supportFragmentManager.beginTransaction()
                    .remove(supportFragmentManager.findFragmentById(binding.mainContainer.id)!!)
                    .commit()

                loadFragment(mainFragment)*/
            }
        } else if (currentFragment is OnlineTestFragment) {
            if (currentFragment != mainFragment) {
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit)
            }
        } else if (currentFragment is EBookFragment) {
            if (currentFragment != mainFragment) {
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit)
            }
        } else if (currentFragment is ProfileFragment) {
            if (currentFragment != mainFragment) {
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit)
            }
        }
    }

    fun activityLoader(isShow: Boolean) {
        if (isShow) {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            binding.mainContainer.animate()?.alpha(0.2f)
            binding.progressBar.visibility = View.VISIBLE
        } else {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding.mainContainer.animate()?.alpha(1f)
            binding.progressBar.visibility = View.GONE
        }
    }
}
