package com.haxor.techpedia.modules

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
import com.haxor.techpedia.konfigurasi.adaptermodules.AdapterModules
import com.haxor.techpedia.konfigurasi.userexist.AdapterModules2
import kotlinx.android.synthetic.main.activity_modules.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class ModulesActivity : AppCompatActivity() {

    private var results: List<Result>? = ArrayList()
    private var viewAdapter: AdapterModules? = null

    private var viewAdapter2: AdapterModules2? = null


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

    internal lateinit var id_bab: String
    internal lateinit var nama_bab: String
    internal lateinit var id_apps: String
    internal lateinit var tanggal: String

    private var shimmer_modules: ShimmerFrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modules)
        ButterKnife.bind(this)

        shimmer_modules = findViewById(R.id.shimmer_modules)

        id_bab = intent.getStringExtra("id_bab")
        nama_bab = intent.getStringExtra("nama_bab")
        id_apps = intent.getStringExtra("id_apps")


        judulmodules.setText("Modul " + nama_bab)

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


        ic_back.setOnClickListener {

            finish()
        }

        val searchmodules = findViewById<View>(R.id.searchmodules) as SearchView
        searchmodules.isIconified = false
        searchmodules.queryHint = "Cari Modul "

        searchmodules.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String ): Boolean {

                if(user == null)
                {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(URL.functions)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val api = retrofit.create<Functions>(Functions::class.java)
                    val call = api.searchmodules(newText,id_bab,id_apps)
                    call.enqueue(object : Callback<Value> {
                        override fun onResponse(call: Call<Value>, response: Response<Value>) {
                            val value = response.body()?.value
                            recyclermodules!!.visibility = View.GONE
                            if (value == "1") {

                                shimmer_modules?.stopShimmer()
                                shimmer_modules?.clearAnimation()
                                shimmer_modules?.visibility = View.GONE
                                recyclermodules?.visibility = View.VISIBLE

                                results = response.body()?.result!!
                                val mLayoutManager = LinearLayoutManager(applicationContext)
                                recyclermodules!!.layoutManager = mLayoutManager
                                recyclermodules!!.itemAnimator = DefaultItemAnimator()
                                viewAdapter = results?.let { AdapterModules(this@ModulesActivity, it) }
                                recyclermodules!!.adapter = viewAdapter
                            }
                        }

                        override fun onFailure(call: Call<Value>, t: Throwable) {
                            Toast.makeText(this@ModulesActivity, "Loading Data...", Toast.LENGTH_SHORT).show()
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
                    val call = api.searchmodules(newText,id_bab,id_apps)
                    call.enqueue(object : Callback<Value> {
                        override fun onResponse(call: Call<Value>, response: Response<Value>) {
                            val value = response.body()?.value
                            recyclermodules!!.visibility = View.GONE
                            if (value == "1") {

                                shimmer_modules?.stopShimmer()
                                shimmer_modules?.clearAnimation()
                                shimmer_modules?.visibility = View.GONE
                                recyclermodules?.visibility = View.VISIBLE

                                results = response.body()?.result!!
                                val mLayoutManager = LinearLayoutManager(applicationContext)
                                recyclermodules!!.layoutManager = mLayoutManager
                                recyclermodules!!.itemAnimator = DefaultItemAnimator()
                                viewAdapter2 = results?.let {
                                    this@ModulesActivity.let { it1 ->
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
                                                            AdapterModules2(
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
                                recyclermodules!!.adapter = viewAdapter2
                            }
                        }

                        override fun onFailure(call: Call<Value>, t: Throwable) {
                            Toast.makeText(this@ModulesActivity, "Loading Data...", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

                return true
            }
        })



        val pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.refreshmodules)
        pullToRefresh.setOnRefreshListener {
            loadDataApps()
            pullToRefresh.isRefreshing = false
        }


        loadDataApps()
        shimmer_modules?.startShimmer()
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
            val call = api.modules(id_bab,id_apps)
            call.enqueue(object : Callback<Value> {
                override fun onResponse(call: Call<Value>, response: Response<Value>) {
                    val value = response.body()?.value

                    recyclermodules?.visibility = View.GONE
                    if (value == "1") {

                        shimmer_modules?.stopShimmer()
                        shimmer_modules?.clearAnimation()
                        shimmer_modules?.visibility = View.GONE
                        recyclermodules?.visibility = View.VISIBLE

                        results = response.body()?.result
                        val mLayoutManager = LinearLayoutManager(applicationContext)
                        recyclermodules!!.layoutManager = mLayoutManager
                        recyclermodules!!.itemAnimator = DefaultItemAnimator()
                        viewAdapter = results?.let { AdapterModules(this@ModulesActivity, it) }

                        recyclermodules!!.adapter = viewAdapter



                    }
                }

                override fun onFailure(call: Call<Value>, t: Throwable) {
                    Toast.makeText(this@ModulesActivity, "Loading Data...", Toast.LENGTH_SHORT).show()
                }
            })
        }

        else
        {
            recyclermodules!!.visibility = View.GONE
            val retrofit = Retrofit.Builder()
                .baseUrl(URL.functions)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create<Functions>(Functions::class.java)
            val call = api.modules(id_bab,id_apps)
            call.enqueue(object : Callback<Value> {
                override fun onResponse(call: Call<Value>, response: Response<Value>) {
                    val value = response.body()?.value
                    recyclermodules!!.visibility = View.GONE
                    if (value == "1") {

                        shimmer_modules?.stopShimmer()
                        shimmer_modules?.clearAnimation()
                        shimmer_modules?.visibility = View.GONE
                        recyclermodules?.visibility = View.VISIBLE

                        results = response.body()?.result!!
                        val mLayoutManager = LinearLayoutManager(applicationContext)
                        recyclermodules!!.layoutManager = mLayoutManager
                        recyclermodules!!.itemAnimator = DefaultItemAnimator()
                        viewAdapter2 = results?.let {
                            this@ModulesActivity.let { it1 ->
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
                                                    AdapterModules2(
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
                        recyclermodules!!.adapter = viewAdapter2
                    }
                }

                override fun onFailure(call: Call<Value>, t: Throwable) {
                    Toast.makeText(this@ModulesActivity, "Loading Data...", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        return true
    }
}
