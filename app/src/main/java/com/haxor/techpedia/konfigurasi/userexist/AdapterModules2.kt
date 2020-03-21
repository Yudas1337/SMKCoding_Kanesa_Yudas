package com.haxor.techpedia.konfigurasi.userexist

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.haxor.techpedia.R
import com.haxor.techpedia.URL.fotodetail
import com.haxor.techpedia.auth.LoginActivity
import com.haxor.techpedia.detailmodules.DetailModulesActivity
import com.haxor.techpedia.favorite.DBManager
import com.squareup.picasso.Picasso
import com.haxor.techpedia.konfigurasi.Result
import kotlinx.android.synthetic.main.adapter_modules.view.*

class AdapterModules2(
    private val context: Context, internal var id_user: String ,
    internal var nama: String , internal var email: String , internal var telepon: String ,
    internal var foto_profil: String ,internal var is_active: String,internal var is_premium: String ,
    internal var created_at: String , internal var posisi: String , internal var about: String , private val results: List<Result>) : RecyclerView.Adapter<AdapterModules2.ViewHolder>() {

    internal lateinit var dbManager: DBManager

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



            val judul_uri = result.getjudulUri()
            val judul = itemView.judul!!.text.toString()
            val tanggal = itemView.tanggal!!.text.toString()
            val konten = result.getKonten()
            val id = result.getId()
            val bahasa = result.getbahasaweb()
            val foto_modul = result.getfoto_modul()
            val kategori = result.getKategori()

            dbManager = DBManager(context)
            dbManager.open()

            val cursor = dbManager.fetchbyid(id!!)

            if (cursor!!.count != 0) {
                itemView.RarityImage.setImageResource(R.drawable.ic_favorite)
                itemView.relativeRarity.setBackgroundResource(R.color.colorPrimary)
                dbManager.update(id,judul,foto_modul!!,tanggal,kategori!!,bahasa!!,judul_uri!!)
            } else {
                itemView.RarityImage.setImageResource(R.drawable.ic_favorite_border)
                itemView.relativeRarity.setBackgroundResource(R.color.standart)
            }



            itemView.card1.setOnClickListener{
                val cursor = dbManager.fetchbyid(id)
                if (cursor!!.count != 0) {

                    AlertDialog.Builder(context)
                        .setTitle("Peringatan")
                        .setMessage("Apa yakin akan menghapus Modul dari Favorit?")
                        .setPositiveButton("Iya") { dialog, which ->
                            dbManager.deletebyid(id)
                            itemView.RarityImage.setImageResource(R.drawable.ic_favorite_border)
                            itemView.relativeRarity.setBackgroundResource(R.color.standart)
                            Toast.makeText(context, "Berhasil Menghapus Favorite!", Toast.LENGTH_SHORT).show()
                        }.setNegativeButton("Batal") { dialog, which ->

                        }.setIcon(R.drawable.ic_info)
                        .show()
                } else {
                    AlertDialog.Builder(context)
                        .setTitle("Peringatan")
                        .setMessage("Apa yakin akan menambahkan Modul ke Favorit?")
                        .setPositiveButton("Iya") { dialog, which ->
                            dbManager.insert(id,judul,foto_modul!!,tanggal,kategori!!,bahasa!!,judul_uri!!)
                            itemView.RarityImage.setImageResource(R.drawable.ic_favorite)
                            itemView.relativeRarity.setBackgroundResource(R.color.colorPrimary)
                            Toast.makeText(context, "Berhasil Menambahkan Modul ke Favorite!", Toast.LENGTH_SHORT).show()

                        }.setNegativeButton("Batal") { dialog, which ->

                        }.setIcon(R.drawable.ic_info)
                        .show()


                }
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


            itemView.relativebungkus.setOnClickListener {

                val i = Intent(context, DetailModulesActivity::class.java)
                i.putExtra("id",id)
                i.putExtra("judul",judul)
                i.putExtra("konten",konten)
                i.putExtra("tanggal",tanggal)
                i.putExtra("kategori",kategori)
                i.putExtra("bahasa",bahasa)
                i.putExtra("foto_modul",foto_modul)
                i.putExtra("judul_uri",judul_uri)
                i.putExtra("tanggal",tanggal)


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
