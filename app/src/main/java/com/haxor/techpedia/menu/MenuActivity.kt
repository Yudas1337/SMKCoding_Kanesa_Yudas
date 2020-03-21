package com.haxor.techpedia.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.ButterKnife
import com.facebook.shimmer.ShimmerFrameLayout
import com.haxor.techpedia.R
import com.haxor.techpedia.URL
import com.haxor.techpedia.auth.model.Session
import com.haxor.techpedia.konfigurasi.*
import com.haxor.techpedia.konfigurasi.userexist.AdapterMenu2
import com.haxor.techpedia.konfigurasi.adaptermenu.AdapterMenu
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class MenuActivity : AppCompatActivity() {

    private var results: List<Result>? = ArrayList()
    private var viewAdapter: AdapterMenu? = null

    private var viewAdapter2: AdapterMenu2? = null

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

    internal lateinit var nama_kategori: String

    private var shimermenu: ShimmerFrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        ButterKnife.bind(this)

        shimermenu = findViewById(R.id.shimmer_menu)

        val user = Session.getUser(this, Session.USER_SESSION)
        val intent = intent

        if(user != null)
        {
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
        }

        nama_kategori = intent.getStringExtra("nama_kategori")


        val searchkategori = findViewById<View>(R.id.searchmenu) as SearchView
        searchkategori.isIconified = false
        searchkategori.queryHint = "Cari " + nama_kategori
        ic_backmenu.setOnClickListener{
            finish()
        }

        judul.setText("List " + nama_kategori)

        searchkategori.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if(user == null)
                {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(URL.functions)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val api = retrofit.create<Functions>(Functions::class.java)
                    val call = api.searchmenu(newText,nama_kategori)
                    call.enqueue(object : Callback<Value> {
                        override fun onResponse(call: Call<Value>, response: Response<Value>) {
                            val value = response.body()?.value
                            recyclermenu!!.visibility = View.GONE
                            if (value == "1") {

                                shimermenu?.stopShimmer()
                                shimermenu?.clearAnimation()
                                shimermenu?.visibility = View.GONE
                                recyclermenu?.visibility = View.VISIBLE

                                results = response.body()?.result!!
                                val mLayoutManager = LinearLayoutManager(applicationContext)
                                recyclermenu!!.layoutManager = mLayoutManager
                                recyclermenu!!.itemAnimator = DefaultItemAnimator()
                                viewAdapter = results?.let { AdapterMenu(this@MenuActivity, it) }
                                recyclermenu!!.adapter = viewAdapter
                            }
                        }

                        override fun onFailure(call: Call<Value>, t: Throwable) {
                            Toast.makeText(this@MenuActivity, "Loading Data...", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

                else
                {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(URL.functions)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val api = retrofit.create<Functions>(Functions::class.java)
                    val call = api.searchmenu(newText,nama_kategori)
                    call.enqueue(object : Callback<Value> {
                        override fun onResponse(call: Call<Value>, response: Response<Value>) {
                            val value = response.body()?.value
                            recyclermenu!!.visibility = View.GONE
                            if (value == "1") {

                                shimermenu?.stopShimmer()
                                shimermenu?.clearAnimation()
                                shimermenu?.visibility = View.GONE
                                recyclermenu?.visibility = View.VISIBLE

                                results = response.body()?.result!!
                                val mLayoutManager = LinearLayoutManager(applicationContext)
                                recyclermenu!!.layoutManager = mLayoutManager
                                recyclermenu!!.itemAnimator = DefaultItemAnimator()
                                viewAdapter2 = results?.let {
                                    this@MenuActivity.let { it1 ->
                                        id_user.let  { it2 ->
                                            nama.let { it3 ->
                                                telepon.let { it4 ->
                                                    email.let { it5 ->
                                                        foto_profil.let { it6 ->
                                                            is_active.let { it7 ->
                                                                is_premium.let { it8 ->
                                                                    created_at.let { it9 ->
                                                                        position.let { it10 ->
                                                                            about.let { it11 ->
                                                            AdapterMenu2(
                                                                it1,
                                                                it2,
                                                                it3,
                                                                it4,
                                                                it5,
                                                                it6,
                                                                it7,
                                                                it8,
                                                                it9,
                                                                it10,
                                                                it11,
                                                                it
                                                            )
                                                        } } } } } } } } } } }
                                }
                                recyclermenu!!.adapter = viewAdapter2
                            }
                        }

                        override fun onFailure(call: Call<Value>, t: Throwable) {
                            Toast.makeText(this@MenuActivity, "Loading Data...", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

                return true
            }
        })


        val pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.refreshmenu)
        pullToRefresh.setOnRefreshListener {
            loadDataApps()
            pullToRefresh.isRefreshing = false
        }


        loadDataApps()

        shimermenu?.startShimmer()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        loadDataApps()
    }

    private fun loadDataApps() {
        val user = Session.getUser(this, Session.USER_SESSION)
        if(user == null)
        {
            val retrofit = Retrofit.Builder()
                .baseUrl(URL.functions)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create<Functions>(Functions::class.java)
            val call = api.listmenu(nama_kategori)
            call.enqueue(object : Callback<Value> {
                override fun onResponse(call: Call<Value>, response: Response<Value>) {
                    val value = response.body()?.value
                    recyclermenu?.visibility = View.GONE
                    if (value == "1") {

                        shimermenu?.stopShimmer()
                        shimermenu?.clearAnimation()
                        shimermenu?.visibility = View.GONE
                        recyclermenu?.visibility = View.VISIBLE

                        results = response.body()?.result
                        viewAdapter = results?.let { AdapterMenu(this@MenuActivity, it) }
                        val mLayoutManager = LinearLayoutManager(applicationContext)
                        recyclermenu!!.layoutManager = mLayoutManager
                        recyclermenu!!.itemAnimator = DefaultItemAnimator()
                        recyclermenu!!.adapter = viewAdapter



                    }
                }

                override fun onFailure(call: Call<Value>, t: Throwable) {
                    Toast.makeText(this@MenuActivity, "Loading Data...", Toast.LENGTH_SHORT).show()
                }
            })
        }

        else
        {
            val retrofit = Retrofit.Builder()
                .baseUrl(URL.functions)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create<Functions>(Functions::class.java)
            val call = api.listmenu(nama_kategori)
            call.enqueue(object : Callback<Value> {
                override fun onResponse(call: Call<Value>, response: Response<Value>) {
                    val value = response.body()?.value
                    recyclermenu!!.visibility = View.GONE
                    if (value == "1") {

                        shimermenu?.stopShimmer()
                        shimermenu?.clearAnimation()
                        shimermenu?.visibility = View.GONE
                        recyclermenu?.visibility = View.VISIBLE

                        results = response.body()?.result!!
                        val mLayoutManager = LinearLayoutManager(applicationContext)
                        recyclermenu!!.layoutManager = mLayoutManager
                        recyclermenu!!.itemAnimator = DefaultItemAnimator()
                        viewAdapter2 = results?.let {
                            this@MenuActivity.let { it1 ->
                                id_user.let  { it2 ->
                                    nama.let { it3 ->
                                        telepon.let { it4 ->
                                            email.let { it5 ->
                                                foto_profil.let { it6 ->
                                                    is_active.let { it7 ->
                                                        is_premium.let { it8 ->
                                                            created_at.let { it9 ->
                                                                position.let { it10 ->
                                                                    about.let { it11 ->
                                                                        AdapterMenu2(
                                                                            it1,
                                                                            it2,
                                                                            it3,
                                                                            it4,
                                                                            it5,
                                                                            it6,
                                                                            it7,
                                                                            it8,
                                                                            it9,
                                                                            it10,
                                                                            it11,
                                                        it
                                                    )
                                                } } } } } } } } } } } }
                        recyclermenu!!.adapter = viewAdapter2
                    }
                }

                override fun onFailure(call: Call<Value>, t: Throwable) {
                    Toast.makeText(this@MenuActivity, "Loading Data...", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        return true
    }
}
