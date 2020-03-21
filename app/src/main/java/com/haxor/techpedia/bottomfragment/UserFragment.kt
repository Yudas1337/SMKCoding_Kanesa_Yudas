package com.haxor.techpedia.bottomfragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.haxor.techpedia.MainActivity
import com.haxor.techpedia.R
import com.haxor.techpedia.URL.profilurl
import com.haxor.techpedia.auth.model.Session
import com.haxor.techpedia.favorite.DBManager
import com.haxor.techpedia.favorite.FavoriteActivity
import com.squareup.picasso.Picasso
@SuppressLint("ValidFragment")
class UserFragment(nama1: String?, email1: String?, telepon1: String?, idUser1: String?, fotoProfil1: String?
    , is_active1: String? , is_premium1: String? , created_at1: String? , position1: String? , about1: String? ) : Fragment() {

    internal var ambilid: String? = idUser1
    internal var ambilnama: String? = nama1
    internal var ambiltelepon: String? = telepon1
    internal var ambilemail: String? = email1
    internal var ambilfoto_profil: String? = fotoProfil1
    internal var ambilis_active: String? = is_active1
    internal var ambilis_premium: String? = is_premium1
    internal var ambilcreated_at: String? = created_at1
    internal var ambilposition: String? = position1
    internal var ambilabout: String? = about1

    var cursor: Cursor? = null
    internal lateinit var dbManager: DBManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        val ic_premium = view.findViewById<View>(R.id.ic_premium) as ImageView

        val textdetail1 = view.findViewById<View>(R.id.textdetail1) as TextView
        val textdetail3 = view.findViewById<View>(R.id.textdetail3) as TextView

        val nama = view.findViewById<View>(R.id.namafragment) as TextView
        val fotonya = view.findViewById<View>(R.id.profil) as ImageView
        val ic_more = view.findViewById<View>(R.id.ic_more) as ImageView

        val position = view.findViewById<View>(R.id.positionfragment) as TextView

        val favorite = view.findViewById<View>(R.id.favorite) as ImageView

        favorite.setOnClickListener {

            val i = Intent(context,FavoriteActivity::class.java)
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
            context?.startActivity(i)
        }

        dbManager = context?.let { DBManager(it) }!!
        dbManager.open()
        cursor = dbManager.fetch()


        textdetail1.text = cursor!!.count.toString() + " Favorit"

        if(ambilis_premium == "1")
        {
            ic_premium.setImageResource(R.drawable.ic_premium_membership)
            textdetail3.text = "Premium Member"
        }

        else
        {
            ic_premium.setImageResource(R.drawable.ic_standart_member)
            textdetail3.text = "Standart Member"
        }


        nama.text = ambilnama
        position.text = ambilposition

        Picasso.with(context)
            .load(profilurl + ambilfoto_profil!!)
            .fit()
            .centerCrop()
            .into(fotonya)
        Picasso.with(context).isLoggingEnabled = true

        setHasOptionsMenu(true)

        ic_more.setOnClickListener{

            AlertDialog.Builder(context)
                .setTitle("Peringatan")
                .setMessage("Apakah anda yakin akan logout?")
                .setPositiveButton("Iya") { dialog, which ->
                    LogoutAct()
                    Toast.makeText(context, "Berhasil Logout!", Toast.LENGTH_SHORT).show()
                }.setNegativeButton("Batal") { dialog, which ->
                    // do nothing
                }.setIcon(R.drawable.ic_info)
                .show()

        }

        return view

    }


    fun LogoutAct()
    {
        activity?.let { Session.clear(it) }
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}