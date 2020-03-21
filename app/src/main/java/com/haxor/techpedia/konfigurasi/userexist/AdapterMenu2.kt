package com.haxor.techpedia.konfigurasi.userexist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.haxor.techpedia.R
import com.squareup.picasso.Picasso

import com.haxor.techpedia.URL.icon
import com.haxor.techpedia.kategori.KategoriBabActivity
import com.haxor.techpedia.konfigurasi.Result
import kotlinx.android.synthetic.main.adapter_menu.view.*

class AdapterMenu2(
    private val context: Context, internal var id_user: String ,
    internal var nama: String , internal var email: String , internal var telepon: String ,
    internal var foto_profil: String , internal var is_active: String,internal var is_premium: String ,
    internal var created_at: String , internal var posisi: String , internal var about: String , private val results: List<Result>) : RecyclerView.Adapter<AdapterMenu2.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_menu, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.setData(result)

    }

    override fun getItemCount(): Int {
        return results.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        public fun setData(result: Result)
        {

            itemView.bahasa!!.text = result.getbahasaweb()
            itemView.detail!!.text = result.getApps_Detail()
            Picasso.with(context)
                .load(icon + result.geticon()!!)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(itemView.icon)

            Picasso.with(context).isLoggingEnabled = true

            val bahasa = itemView.bahasa.text.toString()

            itemView.linear1.setOnClickListener{

                val i = Intent(context, KategoriBabActivity::class.java)
                i.putExtra("bahasa", bahasa)

                i.putExtra("id_user",id_user)
                i.putExtra("nama",nama)
                i.putExtra("telepon",telepon)
                i.putExtra("email",email)
                i.putExtra("foto_profil",foto_profil)
                i.putExtra("is_active",is_active)
                i.putExtra("is_premium",is_premium)
                i.putExtra("created_at",created_at)
                i.putExtra("position",posisi)
                i.putExtra("about",about)

                context.startActivity(i)
            }

        }


        override fun onClick(view: View) {

        }
    }
}
