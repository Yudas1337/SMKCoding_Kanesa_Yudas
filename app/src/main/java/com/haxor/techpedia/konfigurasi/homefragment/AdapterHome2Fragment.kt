package com.haxor.techpedia.konfigurasi.homefragment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haxor.techpedia.menu.MenuActivity
import com.haxor.techpedia.konfigurasi.Result
import com.haxor.techpedia.R
import com.squareup.picasso.Picasso
import com.haxor.techpedia.URL.iconkategori
import kotlinx.android.synthetic.main.adapter_home.view.*

class AdapterHome2Fragment(private val context: Context, private val results: List<Result>) : RecyclerView.Adapter<AdapterHome2Fragment.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_home, parent, false)

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

                println(nama_kategori)

                val i = Intent(context, MenuActivity::class.java)
                i.putExtra("nama_kategori",nama_kategori)

                context.startActivity(i)
            }
        }


        override fun onClick(view: View) {


        }
    }
}
