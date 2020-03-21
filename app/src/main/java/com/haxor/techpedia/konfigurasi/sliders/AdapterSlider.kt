package com.haxor.techpedia.konfigurasi.sliders

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter

import com.haxor.techpedia.R
import com.haxor.techpedia.Slider.SliderActivity
import com.haxor.techpedia.URL.Sliders
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_fotomodul.view.*


/**
 * Created by YUDAS MALABI
 */

class AdapterSlider(internal var context: Context,
                    private val images: List<ResultSliders>) : PagerAdapter() {

    internal var layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = layoutInflater.inflate(R.layout.adapter_fotomodul, container, false)
        val imageViews = itemView.findViewById<View>(R.id.foto_detail) as ImageView

        val resultnya = images[position]

        itemView.deskripsi!!.text = resultnya.get_deskripsi()

        val result = images[position]


        Picasso.with(context)
                .load(Sliders + result.getsliders()!!)
                .fit()
                .into(imageViews)
        Picasso.with(context).isLoggingEnabled = true

        container.addView(itemView)

        itemView.cardSliders.setOnClickListener {

            val id_sliders = result.getId_Sliders()
            val getJudul = result.getJudul()
            val foto_sliders = result.getsliders()
            val i  = Intent(context,SliderActivity::class.java)
            i.putExtra("foto_sliders",foto_sliders)
            i.putExtra("id_sliders",id_sliders)
            i.putExtra("judul",getJudul)
            context.startActivity(i)
        }


        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

}
