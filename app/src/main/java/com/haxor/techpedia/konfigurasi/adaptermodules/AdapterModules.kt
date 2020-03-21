package com.haxor.techpedia.konfigurasi.adaptermodules

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
import kotlinx.android.synthetic.main.adapter_modules.view.*

class AdapterModules(private val context: Context, private val results: List<Result>) : RecyclerView.Adapter<AdapterModules.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_modules, parent, false)

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
            itemView.tanggal!!.text = result.getTanggal()
            itemView.judul!!.text = result.getjudul()
            Picasso.with(context)
                .load(fotodetail + result.getfoto_modul()!!)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(itemView.foto)

            Picasso.with(context).isLoggingEnabled = true

            itemView.card1.setOnClickListener{
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

            itemView.komentar!!.text = result.getKomentar()
            val komentarnya = itemView.komentar!!.text.toString()
            if(komentarnya > "0")
            {
                itemView.komentar!!.setText(komentarnya + " Comments")
                itemView.relativeTotal.setBackgroundResource(R.color.success)
            }

            else if (komentarnya == "0")
            {
                itemView.komentar!!.setText("No Comments")
                itemView.relativeTotal.setBackgroundResource(R.color.standart)
            }

            itemView.relativeRarity.setBackgroundResource(R.color.standart)

            itemView.relativebungkus.setOnClickListener {

                val judul_uri = result.getjudulUri()
                val judul = itemView.judul!!.text.toString()
                val tanggal = itemView.tanggal!!.text.toString()
                val id = result.getId()
                val bahasa = result.getbahasaweb()
                val foto_modul = result.getfoto_modul()
                val kategori = result.getKategori()

                val i = Intent(context, DetailModulesActivity::class.java)
                i.putExtra("id",id)
                i.putExtra("judul",judul)
                i.putExtra("tanggal",tanggal)
                i.putExtra("kategori",kategori)
                i.putExtra("bahasa",bahasa)
                i.putExtra("foto_modul",foto_modul)
                i.putExtra("judul_uri",judul_uri)
                i.putExtra("tanggal",tanggal)



                context.startActivity(i)
            }
        }


        override fun onClick(view: View) {


        }
    }
}
