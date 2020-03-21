package com.haxor.techpedia.bottomfragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.haxor.techpedia.URL.functions
import com.haxor.techpedia.konfigurasi.Functions
import com.haxor.techpedia.konfigurasi.Result
import com.haxor.techpedia.konfigurasi.Value
import com.haxor.techpedia.konfigurasi.adaptermodulterbaru.AdapterModulTerbaru
import com.haxor.techpedia.konfigurasi.homefragment.AdapterHome2Fragment
import com.haxor.techpedia.konfigurasi.sliders.AdapterSlider
import com.haxor.techpedia.konfigurasi.sliders.ResultSliders
import com.haxor.techpedia.konfigurasi.sliders.ValueSliders
import com.haxor.techpedia.konfigurasi.userexist.AdapterHomeFragment
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.haxor.techpedia.R
import com.haxor.techpedia.konfigurasi.adaptermodulpopuler.AdapterModulPopuler
import com.facebook.shimmer.ShimmerFrameLayout
import com.haxor.techpedia.auth.LoginActivity
import com.haxor.techpedia.favorite.FavoriteActivity
import com.haxor.techpedia.konfigurasi.sliders.AdapterSlider2
import com.haxor.techpedia.konfigurasi.userexist.AdapterModulPopuler2
import com.haxor.techpedia.konfigurasi.userexist.AdapterModulTerbaru2


@SuppressLint("ValidFragment")
class HomeFragment(nama1: String?, email1: String?, telepon1: String?,
                   idUser1: String?, fotoProfil1: String?,
                   is_active1: String? , is_premium1: String? , created_at1: String?,
                   position1: String? , about1: String?)
    : Fragment(){

    internal var ambilid: String? = idUser1
    internal var ambilnama: String? = nama1
    internal var ambiltelepon: String? = telepon1
    internal var ambilemail: String? = email1
    internal var ambilfoto_profil: String? = fotoProfil1
    internal var ambilis_active: String? = is_active1
    internal var ambilis_premium: String? = is_premium1
    internal var ambilcreated_at: String?  = created_at1


    internal var ambilposition: String?  = position1
    internal var ambilabout: String?  = about1

    private var adapterhome2fragment: AdapterHome2Fragment? = null
    private var adapterhomefragment: AdapterHomeFragment? = null

    private var homefragmentresults: List<Result>? = ArrayList()

    private var sliderresults: List<ResultSliders>? = ArrayList()
    private var adapterslider: AdapterSlider? = null
    private var adapterslider2: AdapterSlider2? = null

    private var dots: Array<TextView?>? = null
    private var dotsLayout: LinearLayout? = null

    private var slidernya: ViewPager? = null

    private var adaptermodulterbaru: AdapterModulTerbaru? = null
    private var adaptermodulpopuler: AdapterModulPopuler? = null

    private var adaptermodulterbaru2: AdapterModulTerbaru2? = null
    private var adaptermodulpopuler2: AdapterModulPopuler2? = null

    private var shimerutama: ShimmerFrameLayout? = null
    private var shimerpopuler: ShimmerFrameLayout? = null
    private var shimerterbaru: ShimmerFrameLayout? = null

    private var favorite: ImageView? = null

    private fun loadmodulpopuler(): Boolean{

        if(ambilid == null)
        {
            val retrofit = Retrofit.Builder()
                .baseUrl(functions)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create<Functions>(Functions::class.java!!)
            val call = api.modulpopuler()
            call.enqueue(object : Callback<Value> {
                override fun onResponse(call: Call<Value>, response: Response<Value>) {
                    val value = response.body()?.value
                    recycler2?.visibility = View.VISIBLE
                    if (value == "1") {

                        shimerpopuler?.stopShimmer()
                        shimerpopuler?.clearAnimation()
                        shimerpopuler?.visibility = View.GONE
                        recycler2?.visibility = View.VISIBLE

                        homefragmentresults = response.body()?.result
                        val horizontalLayoutManagaer = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                        recycler2?.layoutManager = horizontalLayoutManagaer
                        adaptermodulpopuler = homefragmentresults?.let { activity?.let { it1 -> AdapterModulPopuler(it1, it) } }
                        recycler2?.adapter = adaptermodulpopuler
                    }
                }

                override fun onFailure(call: Call<Value>, t: Throwable) {
                    Toast.makeText(activity, "Loading Data..", Toast.LENGTH_SHORT).show()
                }
            })

        }


        else
        {
            val retrofit = Retrofit.Builder()
                .baseUrl(functions)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create<Functions>(Functions::class.java)
            val call = api.modulpopuler()
            call.enqueue(object : Callback<Value> {
                override fun onResponse(call: Call<Value>, response: Response<Value>) {
                    val value = response.body()?.value
                    recycler2?.visibility = View.GONE
                    if (value == "1") {

                        shimerpopuler?.stopShimmer()
                        shimerpopuler?.clearAnimation()
                        shimerpopuler?.visibility = View.GONE
                        recycler2?.visibility = View.VISIBLE

                        homefragmentresults = response.body()?.result
                        val horizontalLayoutManagaer = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                        recycler2?.layoutManager = horizontalLayoutManagaer
                        adaptermodulpopuler2 = homefragmentresults?.let {
                            activity?.let { it1 ->
                                ambilid?.let  { it2 ->
                                    ambilnama?.let { it3 ->
                                        ambiltelepon?.let { it4 ->
                                            ambilemail?.let { it5 ->
                                                ambilfoto_profil?.let { it6 ->
                                                    ambilis_active?.let { it7 ->
                                                        ambilis_premium?.let { it8 ->
                                                            ambilcreated_at?.let { it9 ->
                                                                ambilposition?.let { it10 ->
                                                                    ambilabout?.let { it11 ->

                                                                        AdapterModulPopuler2(it1, it2, it3, it4, it5, it6, it7,it8,it9,it10,it11, it)
                                                                    } } } } } } } } } } } }
                        recycler2?.adapter = adaptermodulpopuler2
                    }
                }

                override fun onFailure(call: Call<Value>, t: Throwable) {
                    Toast.makeText(activity, "Loading Data...", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return true
    }

    private fun loadmodulterbaru(): Boolean{

        if(ambilid == null)
        {
            val retrofit = Retrofit.Builder()
                .baseUrl(functions)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create<Functions>(Functions::class.java!!)
            val call = api.modulterbaru()
            call.enqueue(object : Callback<Value> {
                override fun onResponse(call: Call<Value>, response: Response<Value>) {
                    val value = response.body()?.value
                    recycler3?.visibility = View.GONE
                    if (value == "1") {

                        shimerterbaru?.stopShimmer()
                        shimerterbaru?.clearAnimation()
                        shimerterbaru?.visibility = View.GONE
                        recycler3?.visibility = View.VISIBLE

                        homefragmentresults = response.body()?.result
                        val horizontalLayoutManagaer = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                        recycler3?.layoutManager = horizontalLayoutManagaer
                        adaptermodulterbaru = homefragmentresults?.let { activity?.let { it1 -> AdapterModulTerbaru(it1, it) } }
                        recycler3?.adapter = adaptermodulterbaru
                    }
                }

                override fun onFailure(call: Call<Value>, t: Throwable) {
                    Toast.makeText(activity, "Loading Data..", Toast.LENGTH_SHORT).show()
                }
            })

        }


        else
        {
            val retrofit = Retrofit.Builder()
                .baseUrl(functions)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create<Functions>(Functions::class.java)
            val call = api.modulterbaru()
            call.enqueue(object : Callback<Value> {
                override fun onResponse(call: Call<Value>, response: Response<Value>) {
                    val value = response.body()?.value
                    recycler3?.visibility = View.GONE
                    if (value == "1") {

                        shimerterbaru?.stopShimmer()
                        shimerterbaru?.clearAnimation()
                        shimerterbaru?.visibility = View.GONE
                        recycler3?.visibility = View.VISIBLE

                        homefragmentresults = response.body()?.result
                        val horizontalLayoutManagaer = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                        recycler3?.layoutManager = horizontalLayoutManagaer
                        adaptermodulterbaru2 = homefragmentresults?.let {
                            activity?.let { it1 ->
                                ambilid?.let  { it2 ->
                                    ambilnama?.let { it3 ->
                                        ambiltelepon?.let { it4 ->
                                            ambilemail?.let { it5 ->
                                                ambilfoto_profil?.let { it6 ->
                                                    ambilis_active?.let { it7 ->
                                                        ambilis_premium?.let { it8 ->
                                                            ambilcreated_at?.let { it9 ->
                                                                ambilposition?.let { it10 ->
                                                                    ambilabout?.let { it11 ->

                                                                        AdapterModulTerbaru2(it1, it2, it3, it4, it5, it6, it7,it8,it9,it10,it11, it)
                                                                    } } } } } } } } } } } }
                        recycler3?.adapter = adaptermodulterbaru2
                    }
                }

                override fun onFailure(call: Call<Value>, t: Throwable) {
                    Toast.makeText(activity, "Loading Data...", Toast.LENGTH_SHORT).show()
                }
            })
        }


        return true

    }


    private fun ambilslider(): Boolean {
        if(ambilid == null)
        {
            val retrofit = Retrofit.Builder()
                .baseUrl(functions)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create<Functions>(Functions::class.java)
            val call = api.ambilslider()
            call.enqueue(object : Callback<ValueSliders> {
                override fun onResponse(call: Call<ValueSliders>, response: Response<ValueSliders>) {
                    val value = response.body()?.valueSliders
                    if (value == "1") {

                        sliderresults = response.body()?.resultSliders
                        adapterslider = sliderresults?.let { context?.let { it1 ->
                            AdapterSlider(
                                it1,
                                it
                            )
                        } }
                        slidernya?.adapter = adapterslider
                    }

                }

                override fun onFailure(call: Call<ValueSliders>, t: Throwable) {
                    Toast.makeText(activity,"Loading Data..",Toast.LENGTH_SHORT).show()
                }
            })
        }

        else
        {
            val retrofit = Retrofit.Builder()
                .baseUrl(functions)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create<Functions>(Functions::class.java)
            val call = api.ambilslider()
            call.enqueue(object : Callback<ValueSliders> {
                override fun onResponse(call: Call<ValueSliders>, response: Response<ValueSliders>) {
                    val value = response.body()?.valueSliders
                    if (value == "1") {

                        sliderresults = response.body()?.resultSliders
                        adapterslider2 = sliderresults?.let {
                            context?.let { it1 ->
                                ambilid?.let  { it2 ->
                                    ambilnama?.let { it3 ->
                                        ambiltelepon?.let { it4 ->
                                            ambilemail?.let { it5 ->
                                                ambilfoto_profil?.let { it6 ->
                                                    ambilis_active?.let { it7 ->
                                                        ambilis_premium?.let { it8 ->
                                                            ambilcreated_at?.let { it9 ->
                                                                ambilposition?.let { it10 ->
                                                                    ambilabout?.let { it11 ->
                            AdapterSlider2(
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
                        slidernya?.adapter = adapterslider2
                    }

                }

                override fun onFailure(call: Call<ValueSliders>, t: Throwable) {
                    Toast.makeText(activity,"Loading Data..",Toast.LENGTH_SHORT).show()
                }
            })
        }

        adapterhomefragment = homefragmentresults?.let {
            activity?.let { it1 ->
                ambilid?.let  { it2 ->
                    ambilnama?.let { it3 ->
                        ambiltelepon?.let { it4 ->
                            ambilemail?.let { it5 ->
                                ambilfoto_profil?.let { it6 ->
                                    ambilis_active?.let { it7 ->
                                        ambilis_premium?.let { it8 ->
                                            ambilcreated_at?.let { it9 ->
                                                ambilposition?.let { it10 ->
                                                    ambilabout?.let { it11 ->

                                                        AdapterHomeFragment(it1, it2, it3, it4, it5, it6, it7,it8,it9,it10,it11, it)
                                                    } } } } } } } } } } } }

        return true
    }

    private fun loadmenu(): Boolean {
        if(ambilid == null)
        {
            val retrofit = Retrofit.Builder()
                .baseUrl(functions)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create<Functions>(Functions::class.java!!)
            val call = api.tampilmenu()
            call.enqueue(object : Callback<Value> {
                override fun onResponse(call: Call<Value>, response: Response<Value>) {
                    val value = response.body()?.value
                    recyclerhome?.visibility = View.GONE
                    if (value == "1") {

                        shimerutama?.stopShimmer()
                        shimerutama?.clearAnimation()
                        shimerutama?.visibility = View.GONE
                        recyclerhome?.visibility = View.VISIBLE

                        homefragmentresults = response.body()?.result
                        val horizontalLayoutManagaer = GridLayoutManager(activity, 4)
                        recyclerhome?.layoutManager = horizontalLayoutManagaer
                        adapterhome2fragment = homefragmentresults?.let { activity?.let { it1 -> AdapterHome2Fragment(it1, it) } }
                        recyclerhome?.adapter = adapterhome2fragment
                    }
                }

                override fun onFailure(call: Call<Value>, t: Throwable) {
                    Toast.makeText(activity, "Loading Data..", Toast.LENGTH_SHORT).show()
                }
            })

        }

        else
        {
            val retrofit = Retrofit.Builder()
                .baseUrl(functions)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create<Functions>(Functions::class.java)
            val call = api.tampilmenu()
            call.enqueue(object : Callback<Value> {
                override fun onResponse(call: Call<Value>, response: Response<Value>) {
                    val value = response.body()?.value
                    recyclerhome?.visibility = View.GONE
                    if (value == "1") {

                        shimerutama?.stopShimmer()
                        shimerutama?.clearAnimation()
                        shimerutama?.visibility = View.GONE
                        recyclerhome?.visibility = View.VISIBLE

                        homefragmentresults = response.body()?.result
                        val horizontalLayoutManagaer = GridLayoutManager(activity, 4)
                        recyclerhome?.layoutManager = horizontalLayoutManagaer
                        adapterhomefragment = homefragmentresults?.let {
                            activity?.let { it1 ->
                                ambilid?.let  { it2 ->
                                    ambilnama?.let { it3 ->
                                        ambiltelepon?.let { it4 ->
                                            ambilemail?.let { it5 ->
                                                ambilfoto_profil?.let { it6 ->
                                                    ambilis_active?.let { it7 ->
                                                        ambilis_premium?.let { it8 ->
                                                            ambilcreated_at?.let { it9 ->
                                                                ambilposition?.let { it10 ->
                                                                    ambilabout?.let { it11 ->

                                                    AdapterHomeFragment(it1, it2, it3, it4, it5, it6, it7,it8,it9,it10,it11, it)
                                                } } } } } } } } } } } }
                        recyclerhome?.adapter = adapterhomefragment
                    }
                }

                override fun onFailure(call: Call<Value>, t: Throwable) {
                    Toast.makeText(activity, "Loading Data...", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return true
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        slidernya = view.findViewById<View>(R.id.slider) as ViewPager
        dotsLayout = view.findViewById<View>(R.id.layoutDots) as LinearLayout

        shimerutama = view.findViewById<View>(R.id.shimmer_utama)  as ShimmerFrameLayout
        shimerpopuler = view.findViewById<View>(R.id.shimmer_populer) as ShimmerFrameLayout
        shimerterbaru = view.findViewById<View>(R.id.shimmer_terbaru) as ShimmerFrameLayout

        favorite = view.findViewById<View>(R.id.favorite) as ImageView



        favorite!!.setOnClickListener {

            if(ambilid == null)
            {
                context?.let { it1 ->
                    AlertDialog.Builder(it1)
                        .setTitle("Peringatan")
                        .setMessage("Login untuk mengakses Favorite")
                        .setPositiveButton("Iya") { dialog, which ->
                            val i = Intent(context, LoginActivity::class.java)
                            context!!.startActivity(i)
                        }.setNegativeButton("Batal") { dialog, which ->
                            // do nothing
                        }.setIcon(R.drawable.ic_info)
                        .show()
                }
            }

            else
            {

                val i = Intent(context, FavoriteActivity::class.java)

                i.putExtra("id_user",ambilid)
                i.putExtra("nama",ambilnama)
                i.putExtra("telepon",ambiltelepon)
                i.putExtra("email",ambilemail)
                i.putExtra("foto_profil",ambilfoto_profil)
                i.putExtra("is_active",ambilis_active)
                i.putExtra("is_premium",ambilis_premium)
                i.putExtra("created_at",ambilcreated_at)
                i.putExtra("position",ambilposition)
                i.putExtra("about",ambilabout)
                startActivity(i)
            }
        }


        slidernya!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                addBottomDots(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }


        })

        addBottomDots(0)

        val pullToRefresh = view.findViewById<SwipeRefreshLayout>(R.id.refreshHome)
        pullToRefresh.setOnRefreshListener {
            loadmenu()
            ambilslider()
            loadmodulterbaru()
            loadmodulpopuler()
            addBottomDots(0)
            pullToRefresh.isRefreshing = false
            Toast.makeText(activity,"Refreshing Data..",Toast.LENGTH_LONG).show()
        }


        loadmenu()

        ambilslider()

        loadmodulterbaru()

        loadmodulpopuler()

        shimerutama?.startShimmer()
        shimerpopuler?.startShimmer()
        shimerterbaru?.startShimmer()

        return view

    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(sliderresults!!.size)

        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)

        dotsLayout!!.removeAllViews()
        for (i in dots!!.indices) {
            dots!![i] = TextView(context)
            dots!![i]?.text = Html.fromHtml("&#8226;")
            dots!![i]?.textSize  = 35f
            dots!![i]?.setTextColor(colorsInactive[currentPage])
            dotsLayout!!.addView(dots!![i])
        }

        if (dots!!.size > 0)
            dots!![currentPage]?.setTextColor(colorsActive[currentPage])
    }




}