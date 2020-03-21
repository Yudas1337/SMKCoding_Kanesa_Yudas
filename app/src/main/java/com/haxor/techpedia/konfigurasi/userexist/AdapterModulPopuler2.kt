package com.haxor.techpedia.konfigurasi.userexist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.haxor.techpedia.konfigurasi.Result
import com.haxor.techpedia.R
import com.haxor.techpedia.URL
import com.squareup.picasso.Picasso
import com.haxor.techpedia.detailmodules.DetailModulesActivity
import com.haxor.techpedia.favorite.DBManager
import kotlinx.android.synthetic.main.adapter_modulpopuler.view.*

class AdapterModulPopuler2
    (private val context: Context,internal var id_user: String,internal var nama: String ,internal var telepon: String,
     internal var email: String,internal var foto_profil: String,internal var is_active: String,internal var is_premium: String ,
     internal var created_at: String , internal var position: String , internal var about: String ,private val results: List<Result>) : RecyclerView.Adapter<AdapterModulPopuler2.ViewHolder>() {

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
    internal lateinit var dbManager: DBManager


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_modulpopuler, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdapterModulPopuler2.ViewHolder, position: Int) {
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
                .load(URL.fotodetail + result.getfoto_modul()!!)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(itemView.image)

            Picasso.with(context).isLoggingEnabled = true

            val judul_uri = result.getjudulUri()
            val id = result.getId()
            val judul     = itemView.title!!.text.toString()
            val foto_modul = result.getfoto_modul()
            val tanggal = itemView.tanggal!!.text.toString()
            val kategori = itemView.kategori!!.text.toString()
            val bahasa = itemView.bahasa!!.text.toString()

            dbManager = DBManager(context)
            dbManager.open()

            val cursor = dbManager.fetchbyid(id!!)
            if (cursor!!.count != 0) {
                itemView.favorite.setImageResource(R.drawable.ic_favorite)
                dbManager.update(id,judul,foto_modul!!,tanggal,kategori,bahasa,judul_uri!!)
            } else {
                itemView.favorite.setImageResource(R.drawable.ic_favorite_border)
            }



            itemView.favorite.setOnClickListener{

                val cursor = dbManager.fetchbyid(id)
                if (cursor!!.count != 0) {

                    AlertDialog.Builder(context)
                        .setTitle("Peringatan")
                        .setMessage("Apa yakin akan menghapus Modul dari Favorit?")
                        .setPositiveButton("Iya") { dialog, which ->
                            dbManager.deletebyid(id)
                            itemView.favorite.setImageResource(R.drawable.ic_favorite_border)
                            Toast.makeText(context, "Berhasil Menghapus Favorite!", Toast.LENGTH_SHORT).show()
                        }.setNegativeButton("Batal") { dialog, which ->

                        }.setIcon(R.drawable.ic_info)
                        .show()
                } else {
                    AlertDialog.Builder(context)
                        .setTitle("Peringatan")
                        .setMessage("Apa yakin akan menambahkan Modul ke Favorit?")
                        .setPositiveButton("Iya") { dialog, which ->
                            dbManager.insert(id,judul,foto_modul!!,tanggal,kategori,bahasa,judul_uri!!)
                            itemView.favorite.setImageResource(R.drawable.ic_favorite)
                            Toast.makeText(context, "Berhasil Menambahkan Modul ke Favorite!", Toast.LENGTH_SHORT).show()

                        }.setNegativeButton("Batal") { dialog, which ->

                        }.setIcon(R.drawable.ic_info)
                        .show()


                }

            }

            itemView.pembungkus.setOnClickListener {




                val i = Intent(context, DetailModulesActivity::class.java)
                i.putExtra("judul_uri",judul_uri)
                i.putExtra("judul",judul)
                i.putExtra("foto_modul",foto_modul)
                i.putExtra("id",id)
                i.putExtra("tanggal",tanggal)
                i.putExtra("kategori",kategori)
                i.putExtra("bahasa",bahasa)


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
