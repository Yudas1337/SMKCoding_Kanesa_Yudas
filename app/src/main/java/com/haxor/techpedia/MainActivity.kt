package com.haxor.techpedia

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import com.haxor.techpedia.bottomfragment.HomeFragment
import com.haxor.techpedia.bottomfragment.UserFragment
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.haxor.techpedia.auth.LoginActivity
import com.haxor.techpedia.auth.model.Session


class MainActivity : AppCompatActivity() {

    private lateinit var frameLayout: FrameLayout
    private var fragment: Fragment? = null
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    var bottomNavigation: BottomNavigationView? = null

    private var id_user1: String? = null
    private var nama1: String? = null
    private var telepon1: String? = null
    private var email1: String? = null
    private var foto_profil1: String? = null
    private var is_active1: String? = null
    private var is_premium1: String? = null
    private var created_at1: String? = null


    private var position1: String? = null
    private var about1: String? = null


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.navigationView)
        bottomNavigation!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        frameLayout = findViewById<View>(R.id.frameLayoutMain) as FrameLayout

        val user = Session.getUser(this, Session.USER_SESSION)
        nama1 = user?.data?.nama
        email1 = user?.data?.email
        telepon1 = user?.data?.telepon
        id_user1 = user?.data?.id_user
        foto_profil1 = user?.data?.foto_profil
        is_active1 = user?.data?.is_active
        is_premium1 = user?.data?.is_premium
        created_at1 = user?.data?.created_at
        position1 = user?.data?.position
        about1 = user?.data?.about
        fragment = HomeFragment(nama1,email1,telepon1,id_user1,foto_profil1,is_active1,is_premium1,created_at1
        ,position1,about1)
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutMain, fragment!!)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()

    }


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutMain, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun onBackPressed()
    {

        val seletedItemId = bottomNavigation!!.selectedItemId
        if (R.id.ic_home != seletedItemId) {
            setHomeItem(this@MainActivity)
        } else {
            alert()
        }

    }

    private fun alert()
    {
        AlertDialog.Builder(this)
            .setTitle("Peringatan")
            .setMessage("Apa anda yakin ingin keluar aplikasi?")
            .setPositiveButton("Iya") { dialog, which ->
                // Do Nothing
                finish()
            }.setNegativeButton("Batal") { dialog, which ->

            }.setIcon(R.drawable.ic_info)
            .show()
    }

    fun setHomeItem(activity: Activity) {
        bottomNavigation!!.selectedItemId = R.id.ic_home
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val user = Session.getUser(this, Session.USER_SESSION)
        val selectedItemId = bottomNavigation!!.selectedItemId
        when (item.itemId) {
            R.id.ic_home -> {

                if(R.id.ic_home != selectedItemId)
                {

                    nama1 = user?.data?.nama
                    email1 = user?.data?.email
                    telepon1 = user?.data?.telepon
                    id_user1 = user?.data?.id_user
                    foto_profil1 = user?.data?.foto_profil
                    is_active1 = user?.data?.is_active
                    is_premium1 = user?.data?.is_premium
                    created_at1 = user?.data?.created_at
                    position1 = user?.data?.position
                    about1 = user?.data?.about

                    val homefragment = HomeFragment(nama1,email1,telepon1,id_user1,foto_profil1,is_active1,is_premium1,created_at1
                    ,position1,about1)
                    openFragment(homefragment)
                    return@OnNavigationItemSelectedListener true
                }

            }


            R.id.ic_saya -> {

                if(user != null)
                {
                    nama1 = user.data?.nama
                    email1 = user.data?.email
                    telepon1 = user.data?.telepon
                    id_user1 = user.data?.id_user
                    foto_profil1 = user.data?.foto_profil
                    is_active1 = user.data?.is_active
                    is_premium1 = user.data?.is_premium
                    created_at1 = user.data?.created_at
                    position1 = user.data?.position
                    about1 = user.data?.about
                    val userfragment = UserFragment(nama1,email1,telepon1,id_user1,foto_profil1,is_active1,is_premium1
                    ,created_at1,position1,about1)
                    openFragment(userfragment)
                    return@OnNavigationItemSelectedListener true
                }

                else
                {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                }

            }
        }

        false
    }




}
