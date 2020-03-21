package com.haxor.techpedia.konfigurasi.userexist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haxor.techpedia.konfigurasi.Result
import com.haxor.techpedia.R
import com.squareup.picasso.Picasso
import com.haxor.techpedia.URL.iconkategori
import com.haxor.techpedia.menu.MenuActivity
import kotlinx.android.synthetic.main.adapter_home.view.*

class AdapterHomeFragment
    (private val context: Context,internal var id_user: String,internal var nama: String ,internal var telepon: String,
     internal var email: String,internal var foto_profil: String,internal var is_active: String,internal var is_premium: String ,
     internal var created_at: String , internal var position: String , internal var about: String ,private val results: List<Result>) : RecyclerView.Adapter<AdapterHomeFragment.ViewHolder>() {

    internal var ambilid: String? = id_user
    internal var ambilnama: String? = nama
    internal var ambiltelepon: String? = telepon
    internal var ambilemail: String? = email
    internal var ambilfoto_profil: String? = foto_profil
    internal var ambilis_active: String? = is_active
    internal var ambilis_premium: String? = is_premium
    internal var ambilcreated_at: String? = created_at
    internal var ambilposition: String? = position
    internal var ambilabout: String? = about


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_home, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdapterHomeFragment.ViewHolder, position: Int) {
        val result = results[position]
        holder.setData(result)

    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun setData(result: Result)
        {
            itemView.nama_kategori!!.text = result.getnama_kategori()
            Picasso.with(context)
                .load(iconkategori + result.geticon_kategori()!!)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(itemView.icon_kategori)

            Picasso.with(context).isLoggingEnabled = true

            itemView.icon_kategori.setOnClickListener {
                val nama_kategori = itemView.nama_kategori!!.text.toString()

                val i = Intent(context, MenuActivity::class.java)
                i.putExtra("nama_kategori", nama_kategori)

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

                context.startActivity(i)
            }
        }


        override fun onClick(view: View) {


        }
    }
}
