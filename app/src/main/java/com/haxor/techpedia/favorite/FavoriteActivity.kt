package com.haxor.techpedia.favorite

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.haxor.techpedia.MainActivity
import com.haxor.techpedia.R
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    internal lateinit var id_user: String
    internal lateinit var nama: String
    internal lateinit var telepon: String
    internal lateinit var email: String
    internal lateinit var foto_profil: String
    internal lateinit var is_active: String
    internal lateinit var is_premium: String
    internal lateinit var created_at: String
    internal lateinit var position: String
    internal lateinit var about: String

    var viewAdapter: AdapterFavorite? = null
    var cursor: Cursor? = null
    internal lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        ic_back.setOnClickListener {
            val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
            startActivity(intent)
            this@FavoriteActivity.finish()
        }


        val intent = intent

        id_user     = intent.getStringExtra("id_user")
        nama        = intent.getStringExtra("nama")
        telepon     = intent.getStringExtra("telepon")
        email       = intent.getStringExtra("email")
        foto_profil = intent.getStringExtra("foto_profil")
        is_active   = intent.getStringExtra("is_active")
        is_premium  = intent.getStringExtra("is_premium")
        created_at  = intent.getStringExtra("created_at")
        position    = intent.getStringExtra("position")
        about       = intent.getStringExtra("about")

        dbManager = DBManager(this)
        dbManager.open()
        cursor = dbManager.fetch()


        if(cursor!!.count > 0 )
        {
            relativeempty.visibility = View.GONE
        }

        else
        {
            relativeempty.visibility = View.VISIBLE
        }

        val pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.refreshmodules)
        pullToRefresh.setOnRefreshListener {
            loadFav()

            if(cursor!!.count > 0 )
            {
                relativeempty.visibility = View.GONE
            }

            else
            {
                relativeempty.visibility = View.VISIBLE
            }

            pullToRefresh.isRefreshing = false
        }


        viewAdapter = cursor?.let { AdapterFavorite(this, it, id_user, nama, telepon, email, foto_profil,is_active,is_premium,created_at,position,about) }
        val mLayoutManager = LinearLayoutManager(this)

        recyclermodules!!.layoutManager = mLayoutManager
        recyclermodules!!.itemAnimator = DefaultItemAnimator()
        recyclermodules!!.adapter = viewAdapter

        loadFav()


    }

    private fun loadFav() {
        cursor = dbManager.fetch()
        viewAdapter = cursor?.let { AdapterFavorite(this, it, id_user, nama, telepon, email, foto_profil,is_active,is_premium,created_at,position,about) }
        recyclermodules!!.adapter = viewAdapter


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
        startActivity(intent)
        this@FavoriteActivity.finish()
    }
}
