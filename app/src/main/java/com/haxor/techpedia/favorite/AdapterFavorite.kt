package com.haxor.techpedia.favorite

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.haxor.techpedia.R
import com.haxor.techpedia.URL.fotodetail
import com.haxor.techpedia.detailmodules.DetailModulesActivity
import com.squareup.picasso.Picasso

import java.util.ArrayList


class AdapterFavorite(private val context: Context, private val cursor: Cursor, internal var id_user: String,internal var nama: String ,internal var telepon: String,
                      internal var email: String,internal var foto_profil: String,internal var is_active: String,internal var is_premium: String ,
                      internal var created_at: String , internal var posisi: String  ,internal var about: String ) : RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {
    private val ID = ArrayList<String>()
    private val JUDUL = ArrayList<String>()
    private val FOTO_MODUL = ArrayList<String>()
    private val TANGGAL = ArrayList<String>()
    private val KATEGORI = ArrayList<String>()
    private val BAHASA = ArrayList<String>()
    private val JUDUL_URI = ArrayList<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_favorite, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewJudul.text = JUDUL[position]
        holder.textViewTanggal.text = TANGGAL[position]
        holder.textViewKategori.text = KATEGORI[position]
        holder.textViewBahasa.text = BAHASA[position]


        Picasso.with(context)
            .load(fotodetail + FOTO_MODUL[position])
            .fit()
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(holder.fotomodul)

        Picasso.with(context).isLoggingEnabled = true
        

        holder.textViewId = ID[position]
        holder.textViewJudulUri = JUDUL_URI[position]
        holder.stringfotomodul = FOTO_MODUL[position]


        val cursor = holder.dbManager.fetchbyid(ID[position])!!
        if (cursor.count != 0) {
            holder.favoritenya.setImageResource(R.drawable.ic_favorite)
            holder.relativeRarity.setBackgroundResource(R.color.colorPrimary)
        } else {
            holder.favoritenya.setImageResource(R.drawable.ic_favorite_border)
            holder.relativeRarity.setBackgroundResource(R.color.standart)
        }

        holder.card1.setOnClickListener {
           val cursor = holder.dbManager.fetchbyid(ID[position])!!
            if (cursor.count != 0) {

                AlertDialog.Builder(context)
                    .setTitle("Peringatan")
                    .setMessage("Hapus dari favorit?")
                    .setPositiveButton("Iya") { dialog, which ->
                        holder.dbManager.deletebyid(ID[position])

                        holder.favoritenya.setImageResource(R.drawable.ic_favorite_border)
                        holder.relativeRarity.setBackgroundResource(R.color.standart)
                        Toast.makeText(context, "Berhasil Menghapus Favorite!", Toast.LENGTH_SHORT).show()
                    }.setNegativeButton("Batal") { dialog, which ->
                        // do nothing
                    }.setIcon(R.drawable.ic_info)
                    .show()
            }
            else {
                AlertDialog.Builder(context)
                    .setTitle("Peringatan")
                    .setMessage("Apa yakin akan menambahkan Modul ke Favorit?")
                    .setPositiveButton("Iya") { dialog, which ->
                        holder.dbManager.insert(ID[position],JUDUL[position],FOTO_MODUL[position],TANGGAL[position],KATEGORI[position],BAHASA[position],JUDUL_URI[position])
                        holder.favoritenya.setImageResource(R.drawable.ic_favorite)
                        holder.relativeRarity.setBackgroundResource(R.color.colorPrimary)
                        Toast.makeText(context, "Berhasil Menambahkan Modul ke Favorite!", Toast.LENGTH_SHORT).show()

                    }.setNegativeButton("Batal") { dialog, which ->

                    }.setIcon(R.drawable.ic_info)
                    .show()


            }

        }


    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var textViewJudul: TextView
        internal var textViewTanggal: TextView
        internal var textViewKategori: TextView
        internal var textViewBahasa: TextView

        internal var relativeRarity: RelativeLayout
        internal var card1: CardView

        internal var textViewJudulUri: String? = null
        internal var textViewId: String? = null

        internal var stringfotomodul: String? = null

        internal var fotomodul: ImageView
        internal var favoritenya: ImageView

        internal var dbManager: DBManager


        init {
            card1 = itemView.findViewById<View>(R.id.card1) as CardView
            relativeRarity = itemView.findViewById<View>(R.id.relativeRarity) as RelativeLayout

            fotomodul = itemView.findViewById<View>(R.id.image) as ImageView

            textViewJudul = itemView.findViewById<View>(R.id.title) as TextView
            textViewTanggal = itemView.findViewById<View>(R.id.tanggal) as TextView

            textViewKategori = itemView.findViewById<View>(R.id.kategori) as TextView
            textViewBahasa = itemView.findViewById<View>(R.id.bahasa) as TextView
            favoritenya = itemView.findViewById<View>(R.id.RarityImage) as ImageView

            dbManager = DBManager(context)
            dbManager.open()
            ID.clear()
            JUDUL.clear()


            if (cursor.moveToFirst()) {
                do {
                    val dataid = cursor.getString(cursor.getColumnIndex("id"))
                    val datajudul = cursor.getString(cursor.getColumnIndex("judul"))
                    val datafoto_modul = cursor.getString(cursor.getColumnIndex("foto_modul"))
                    val datatanggal = cursor.getString(cursor.getColumnIndex("tanggal"))
                    val datakategori = cursor.getString(cursor.getColumnIndex("kategori"))
                    val databahasa = cursor.getString(cursor.getColumnIndex("bahasa"))
                    val datajudul_uri = cursor.getString(cursor.getColumnIndex("judul_uri"))


                    ID.add(dataid)
                    JUDUL.add(datajudul)
                    FOTO_MODUL.add(datafoto_modul)
                    TANGGAL.add(datatanggal)
                    KATEGORI.add(datakategori)
                    BAHASA.add(databahasa)
                    JUDUL_URI.add(datajudul_uri)

                    // do what ever you want here
                } while (cursor.moveToNext())
            }


            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val judul = textViewJudul.text.toString()
            val tanggal = textViewTanggal.text.toString()
            val kategori = textViewKategori.text.toString()
            val bahasa = textViewBahasa.text.toString()

            val id = textViewId
            val judul_uri = textViewJudulUri
            val foto_modulnya = stringfotomodul

            val i = Intent(context, DetailModulesActivity::class.java)
            i.putExtra("id", id)
            i.putExtra("judul_uri", judul_uri)
            i.putExtra("judul", judul)
            i.putExtra("tanggal", tanggal)
            i.putExtra("kategori", kategori)
            i.putExtra("bahasa", bahasa)
            i.putExtra("foto_modul",foto_modulnya)

            i.putExtra("id_user", id_user)
            i.putExtra("nama", nama)
            i.putExtra("telepon", telepon)
            i.putExtra("email", email)
            i.putExtra("foto_profil", foto_profil)
            i.putExtra("is_active",is_active)
            i.putExtra("is_premium",is_premium)
            i.putExtra("created_at",created_at)
            i.putExtra("position",posisi)
            i.putExtra("about",about)

            context.startActivity(i)
        }
    }


}
