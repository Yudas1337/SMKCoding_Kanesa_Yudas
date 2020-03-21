package com.haxor.techpedia.konfigurasi.adapterkategoribab

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.haxor.techpedia.konfigurasi.Result
import com.haxor.techpedia.R
import com.haxor.techpedia.URL.babmodules
import com.squareup.picasso.Picasso
import com.haxor.techpedia.auth.LoginActivity
import com.haxor.techpedia.modules.ModulesActivity
import kotlinx.android.synthetic.main.adapter_kategoribab.view.*

class AdapterKategoriBab(private val context: Context, private val results: List<Result>) : RecyclerView.Adapter<AdapterKategoriBab.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_kategoribab, parent, false)

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
            itemView.nama_bab!!.text = result.getnama_bab()
            itemView.total!!.text = result.getTotal()
            Picasso.with(context)
                .load(babmodules + result.getfoto_babmodules()!!)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(itemView.foto)

            Picasso.with(context).isLoggingEnabled = true

            itemView.rarity!!.text = result.getrarity()

            val raritynya = itemView.rarity!!.text.toString()
            val totalnya = itemView.total!!.text.toString()

            if(totalnya == "0")
            {
                itemView.total.setText("Modul Kosong")
                itemView.relativeTotal.setBackgroundResource(R.color.standart)
            }

            else if (totalnya >= "0")
            {
                itemView.total.setText(result.getTotal() + " Modul")
                itemView.relativeTotal.setBackgroundResource(R.color.success)
            }


            if(raritynya == "1")
            {
                itemView.rarity.setText("Premium")
                itemView.relativeRarity.setBackgroundResource(R.color.colorPrimary)
                itemView.relativebungkus.setOnClickListener {

                    AlertDialog.Builder(context)
                        .setTitle("Peringatan")
                        .setMessage("Login untuk mengakses modul premium")
                        .setPositiveButton("Iya") { dialog, which ->
                            val i = Intent(context, LoginActivity::class.java)
                            context.startActivity(i)
                        }.setNegativeButton("Batal") { dialog, which ->
                            // do nothing
                        }.setIcon(R.drawable.ic_info)
                        .show()
                }
            }

            else if (raritynya == "0")
            {
                itemView.rarity.setText("Standart")
                itemView.relativeRarity.setBackgroundResource(R.color.standart)

                itemView.relativebungkus.setOnClickListener {

                    val id_apps = result.getidApps()
                    val nama_bab =  itemView.nama_bab.text.toString()
                    val id_bab = result.getid_bab()
                    val i = Intent(context, ModulesActivity::class.java)
                    i.putExtra("id_bab",id_bab)
                    i.putExtra("nama_bab",nama_bab)
                    i.putExtra("id_apps",id_apps)
                    context.startActivity(i)
                }
            }


        }


        override fun onClick(view: View) {


        }
    }
}
