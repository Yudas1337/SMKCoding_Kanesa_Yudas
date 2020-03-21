package com.haxor.techpedia.detailmodules

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.haxor.techpedia.auth.LoginActivity
import com.haxor.techpedia.favorite.DBManager

import com.haxor.techpedia.R
import com.haxor.techpedia.URL.fotodetail
import com.haxor.techpedia.URL.modul
import com.haxor.techpedia.auth.model.Session
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_modules.*
import java.util.ArrayList
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.haxor.techpedia.TambahUlasan.TambahUlasanActivity


class DetailModulesActivity : AppCompatActivity() {

    internal lateinit var id: String
    internal lateinit var judul: String
    internal lateinit var tanggal: String
    internal lateinit var kategori: String
    internal lateinit var bahasa: String
    internal lateinit var foto_modul: String
    internal lateinit var judul_uri : String

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

    internal lateinit var dbManager: DBManager

    private var detailwebresult: List<com.haxor.techpedia.konfigurasi.Result>? = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_modules)



        val user = Session.getUser(this, Session.USER_SESSION)

        val intent = intent
        id          = intent.getStringExtra("id")
        judul       = intent.getStringExtra("judul")
        judul_uri   = intent.getStringExtra("judul_uri")
        foto_modul  = intent.getStringExtra("foto_modul")
        tanggal     = intent.getStringExtra("tanggal")
        kategori    = intent.getStringExtra("kategori")
        bahasa      = intent.getStringExtra("bahasa")

        settings()
        loadwebview(judul_uri)
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

        Picasso.with(this)
            .load(fotodetail + foto_modul)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(ImageHeader)

        Picasso.with(this).isLoggingEnabled = true


        toolbarjudul.title = "" + judul
        setSupportActionBar(toolbarjudul)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        sharebutton.setOnClickListener{
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.putExtra(Intent.EXTRA_SUBJECT, "TechPedia")
            val shareBody = "Baca Informasi Mengenai Modul Terbaru dan Terupdate tentang $bahasa $kategori $judul Sekarang hanya di Techpedia . selengkapnya: https://techpedia.pecelsec.com/courses/modul/$judul_uri "
            share.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(share, "Bagikan Melalui.."))

        }



        dbManager = DBManager(this)
        dbManager.open()

        val cursor = dbManager.fetchbyid(id)
        if (cursor!!.count != 0) {
            ic_favoritedetail.setImageResource(R.drawable.ic_favorite)
            dbManager.update(id,judul,foto_modul,tanggal,kategori,bahasa,judul_uri)
        } else {
            ic_favoritedetail.setImageResource(R.drawable.ic_favorite_border)
        }

        icon_detail2.setOnClickListener{
            if(user == null)
            {
                AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Login untuk mengakses fitur Ulasan")
                    .setPositiveButton("Iya") { dialog, which ->
                        val i = Intent(this@DetailModulesActivity, LoginActivity::class.java)
                        startActivity(i)
                    }.setNegativeButton("Batal") { dialog, which ->

                    }.setIcon(R.drawable.ic_info)
                    .show()
            }

            else
            {
                val i = Intent(this@DetailModulesActivity, TambahUlasanActivity::class.java)
                i.putExtra("id",id)
                i.putExtra("judul",judul)
                i.putExtra("judul_uri",judul_uri)
                i.putExtra("foto_modul",foto_modul)
                i.putExtra("tanggal",tanggal)
                i.putExtra("kategori",kategori)
                i.putExtra("bahasa",bahasa)


                i.putExtra("id_user",id_user)
                i.putExtra("nama",nama)
                i.putExtra("telepon",telepon)
                i.putExtra("email",email)
                i.putExtra("foto_profil",foto_profil)
                i.putExtra("is_active",is_active)
                i.putExtra("is_premium",is_premium)
                i.putExtra("created_at",created_at)
                i.putExtra("position",position)
                i.putExtra("about",about)
                startActivity(i)
            }
        }

        ic_favoritedetail.setOnClickListener{

            if(user == null)
            {
                AlertDialog.Builder(this)
                    .setTitle("Peringatan")
                    .setMessage("Login untuk mengakses fitur favorite")
                    .setPositiveButton("Iya") { dialog, which ->
                        val i = Intent(this@DetailModulesActivity, LoginActivity::class.java)
                        startActivity(i)
                    }.setNegativeButton("Batal") { dialog, which ->

                    }.setIcon(R.drawable.ic_info)
                    .show()
            }

            else
            {
                val cursor = dbManager.fetchbyid(id)
                if (cursor!!.count != 0) {
                    //


                    AlertDialog.Builder(this)
                        .setTitle("Peringatan")
                        .setMessage("Apa yakin akan menghapus Modul dari Favorit?")
                        .setPositiveButton("Iya") { dialog, which ->
                            dbManager.deletebyid(id)
                            ic_favoritedetail.setImageResource(R.drawable.ic_favorite_border)
                            Toast.makeText(this, "Berhasil Menghapus Favorite!", Toast.LENGTH_SHORT).show()
                        }.setNegativeButton("Batal") { dialog, which ->

                        }.setIcon(R.drawable.ic_info)
                        .show()
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("Peringatan")
                        .setMessage("Apa yakin akan menambahkan Modul ke Favorit?")
                        .setPositiveButton("Iya") { dialog, which ->
                            dbManager.insert(id,judul,foto_modul,tanggal,kategori,bahasa,judul_uri)
                            ic_favoritedetail.setImageResource(R.drawable.ic_favorite)
                            Toast.makeText(this, "Berhasil Menambahkan Favorite!", Toast.LENGTH_SHORT).show()

                        }.setNegativeButton("Batal") { dialog, which ->

                        }.setIcon(R.drawable.ic_info)
                        .show()


                }

            }
        }




    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            else ->


                return super.onOptionsItemSelected(item)
        }

    }

    private fun settings()
    {
        val myWeb_settings = websiteku.settings
        myWeb_settings.domStorageEnabled = true
        myWeb_settings.javaScriptEnabled = true
        myWeb_settings.allowContentAccess = true
        myWeb_settings.useWideViewPort = true
        myWeb_settings.loadsImagesAutomatically = true
        myWeb_settings.cacheMode = WebSettings.LOAD_NO_CACHE

    }

    fun loadwebview(judul_uri: String)
    {
        if(Build.VERSION.SDK_INT >= 19){
            websiteku.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }else{
            websiteku.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        websiteku.webChromeClient = object: WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                loading.visibility = View.VISIBLE
                loading.progress = newProgress
                if(newProgress == 100){
                    loading.visibility = View.GONE
                }
                super.onProgressChanged(view, newProgress)
            }
        }
        websiteku.webViewClient = object: WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, URL: String?): Boolean {
                view?.loadUrl(URL)
                loading.visibility = View.VISIBLE
                return true
            }
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(request?.url.toString())
                }
                loading.visibility = View.VISIBLE
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loading.visibility = View.GONE
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                //Your code to do
                Toast.makeText(
                    this@DetailModulesActivity,
                    "Loading Data..",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        websiteku.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        websiteku.loadUrl(modul + judul_uri)
    }


}