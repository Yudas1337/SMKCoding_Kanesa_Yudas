package com.haxor.techpedia.konfigurasi.adaptermodulterbaru

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.haxor.techpedia.konfigurasi.Result
import com.haxor.techpedia.R
import com.haxor.techpedia.URL.fotodetail
import com.haxor.techpedia.auth.LoginActivity
import com.haxor.techpedia.detailmodules.DetailModulesActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_modulterbaru.view.*

class AdapterModulTerbaru(private val context: Context, private val results: List<Result>) : RecyclerView.Adapter<AdapterModulTerbaru.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_modulterbaru, parent, false)

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
            itemView.title!!.text = result.getjudul()
            itemView.tanggal!!.text = result.getTanggal()
            itemView.kategori!!.text = result.getKategori()
            itemView.bahasa!!.text = result.getbahasaweb()
            Picasso.with(context)
                .load(fotodetail + result.getfoto_modul()!!)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(itemView.image)

            Picasso.with(context).isLoggingEnabled = true

            itemView.favorite.setOnClickListener{
                AlertDialog.Builder(context)
                    .setTitle("Peringatan")
                    .setMessage("Login untuk mengakses fitur favorite")
                    .setPositiveButton("Iya") { dialog, which ->
                        val i = Intent(context, LoginActivity::class.java)
                        context.startActivity(i)
                    }.setNegativeButton("Batal") { dialog, which ->
                        // do nothing
                    }.setIcon(R.drawable.ic_info)
                    .show()
            }

            itemView.pembungkus.setOnClickListener {


                val judul_uri = result.getjudulUri()
                val id = result.getId()
                val judul     = itemView.title!!.text.toString()
                val foto_modul = result.getfoto_modul()
                val tanggal = itemView.tanggal!!.text.toString()
                val kategori = itemView.kategori!!.text.toString()
                val bahasa = itemView.bahasa!!.text.toString()

                val i = Intent(context, DetailModulesActivity::class.java)
                i.putExtra("judul_uri",judul_uri)
                i.putExtra("judul",judul)
                i.putExtra("foto_modul",foto_modul)
                i.putExtra("id",id)
                i.putExtra("tanggal",tanggal)
                i.putExtra("kategori",kategori)
                i.putExtra("bahasa",bahasa)

                context.startActivity(i)
            }
        }


        override fun onClick(view: View) {


        }
    }
}
