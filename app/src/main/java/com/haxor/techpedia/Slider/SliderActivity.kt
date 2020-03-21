package com.haxor.techpedia.Slider
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.haxor.techpedia.R
import com.haxor.techpedia.auth.model.Session
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.haxor.techpedia.URL.Sliders
import com.haxor.techpedia.URL.isiSliders
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_slider.*


class SliderActivity : AppCompatActivity() {

    internal lateinit var id_sliders: String
    internal lateinit var judul: String
    internal lateinit var foto_sliders: String

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)



        val user = Session.getUser(this, Session.USER_SESSION)

        val intent = intent
        id_sliders          = intent.getStringExtra("id_sliders")
        judul               = intent.getStringExtra("judul")
        foto_sliders        = intent.getStringExtra("foto_sliders")

        settings()
        loadwebview(id_sliders)
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

        settings()
        loadwebview(id_sliders)

        Picasso.with(this)
            .load(Sliders + foto_sliders)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(ImageHeader)

        Picasso.with(this).isLoggingEnabled = true


        toolbarjudul.title = "" + judul
        setSupportActionBar(toolbarjudul)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


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
                    this@SliderActivity,
                    "Loading Data..",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        websiteku.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        websiteku.loadUrl(isiSliders + id_sliders)
    }


}