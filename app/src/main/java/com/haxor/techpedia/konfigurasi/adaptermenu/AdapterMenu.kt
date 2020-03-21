package com.haxor.techpedia.konfigurasi.adaptermenu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haxor.techpedia.konfigurasi.Result
import com.haxor.techpedia.R
import com.haxor.techpedia.URL.icon
import com.haxor.techpedia.kategori.KategoriBabActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_menu.view.*

class AdapterMenu(private val context: Context, private val results: List<Result>) : RecyclerView.Adapter<AdapterMenu.ViewHolder>() {

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

        fun setData(result: Result)
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


            itemView.linear1.setOnClickListener {

                val bahasa = itemView.bahasa!!.text.toString()

                val i = Intent(context, KategoriBabActivity::class.java)
                i.putExtra("bahasa",bahasa)


                context.startActivity(i)
            }
        }


        override fun onClick(view: View) {


        }
    }
}
