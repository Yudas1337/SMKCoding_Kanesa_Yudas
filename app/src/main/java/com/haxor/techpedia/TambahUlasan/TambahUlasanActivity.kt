package com.haxor.techpedia.TambahUlasan

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.haxor.techpedia.R
import com.haxor.techpedia.detailmodules.DetailModulesActivity
import com.haxor.techpedia.konfigurasi.Value
import kotlinx.android.synthetic.main.activity_tambah_ulasan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahUlasanActivity : AppCompatActivity() {

    internal lateinit var id: String
    internal lateinit var judul: String
    internal lateinit var judul_uri: String
    internal lateinit var foto_modul: String
    internal lateinit var tanggal: String
    internal lateinit var kategori: String
    internal lateinit var bahasa: String

    internal lateinit var id_user: String
    internal lateinit var nama: String
    internal lateinit var telepon: String
    internal lateinit var email: String
    internal lateinit var foto_profil: String
    internal lateinit var is_active: String
    internal lateinit var is_premium: String
    internal lateinit var created_at: String
    internal lateinit var position: String
    internal lateinit var about: String

    private var ulasanService: UlasanService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_ulasan)


        ic_backlogin.setOnClickListener {
            finish()
        }

        ulasanButton.setOnClickListener {
            TambahUlasan()
        }

        val intent = intent

        id          = intent.getStringExtra("id")
        judul       = intent.getStringExtra("judul")
        judul_uri   = intent.getStringExtra("judul_uri")
        foto_modul  = intent.getStringExtra("foto_modul")
        tanggal     = intent.getStringExtra("tanggal")
        kategori    = intent.getStringExtra("kategori")
        bahasa      = intent.getStringExtra("bahasa")

        id_user     = intent.getStringExtra("id_user")
        nama        = intent.getStringExtra("nama")
        telepon     = intent.getStringExtra("telepon")
        email       = intent.getStringExtra("email")
        foto_profil = intent.getStringExtra("foto_profil")
        is_active   = intent.getStringExtra("is_active")
        is_premium  = intent.getStringExtra("is_premium")
        created_at  = intent.getStringExtra("created_at")
        position    = intent.getStringExtra("position")
        about       = intent.getStringExtra("about")

        println("ID USER " + id_user )
        println("ID MODULES " + id)

    }

    private fun TambahUlasan()
    {
        val ulasan     = inputulasan.text.toString()

        when{
            ulasan.isEmpty() -> inputulasan!!.error = "Ulasan tidak boleh kosong"
            else -> {
                val dialog = ProgressDialog(this)
                dialog.setMessage("Loading ..")
                dialog.show()
                ulasanService = UlasanService(this)
                ulasanService!!.doUlasan(ulasan,id,id_user, object : Callback<Value> {

                    override fun onResponse(call: Call<Value>, response: Response<Value>) {
                        val value = response.body() as Value

                        if (value != null) {
                            if (!value.isError) {
                                val i = Intent(this@TambahUlasanActivity, DetailModulesActivity::class.java)
                                i.putExtra("id",id)
                                i.putExtra("judul",judul)
                                i.putExtra("judul_uri",judul_uri)
                                i.putExtra("foto_modul",foto_modul)
                                i.putExtra("tanggal",tanggal)
                                i.putExtra("kategori",kategori)
                                i.putExtra("bahasa",bahasa)


                                i.putExtra("id_user",id_user)
                                i.putExtra("nama",nama)
                                i.putExtra("telepon",telepon)
                                i.putExtra("email",email)
                                i.putExtra("foto_profil",foto_profil)
                                i.putExtra("is_active",is_active)
                                i.putExtra("is_premium",is_premium)
                                i.putExtra("created_at",created_at)
                                i.putExtra("position",position)
                                i.putExtra("about",about)
                                startActivity(i)
                                this@TambahUlasanActivity.finish()
                            }
                            dialog.dismiss()
                            Toast.makeText(this@TambahUlasanActivity, value.message, Toast.LENGTH_SHORT).show()

                        }

                    }

                    override fun onFailure(call: Call<Value>, t: Throwable) {
                        dialog.dismiss()
                        Toast.makeText(this@TambahUlasanActivity, "terjadi kesalahan , coba lagi !", Toast.LENGTH_SHORT).show()
                    }
                })

            }
        }
    }
}
