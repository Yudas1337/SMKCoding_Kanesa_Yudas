package com.haxor.techpedia.konfigurasi

class Result {

    // ini viewpager di fragment home
    internal var foto_sliders: String? = null

    // ini menu kategori di fragment home
    internal var id_kategori: String? = null
    internal var nama_kategori: String? = null

    internal var icon_kategori: String? = null

    internal var bahasa: String? = null
    internal var icon: String? = null

    // ini untuk bab modules
    internal var nama_bab: String? = null
    internal var kategori_bab: String? = null
    internal var id_bab: String? = null
    internal var rarity: String? = null
    internal var foto: String? = null
    internal var total: String? = null

    //ini untuk modules
    internal var judul: String? = null
    internal var foto_modul: String? = null
    internal var tanggal: String? = null
    internal var konten: String? = null
    internal var id_apps: String? = null

    internal var apps_detail: String? = null

    // apps favorite
    internal var kategori: String? = null
    internal var judul_uri: String? = null
    internal var id: String? = null
    internal var komentar: String? = null

    internal var foto_babmodules: String? = null

    fun getfoto_babmodules(): String?
    {
        return foto_babmodules
    }

    fun getApps_Detail(): String?
    {
        return apps_detail
    }



    fun getKomentar(): String?
    {
        return komentar
    }

    fun getKategori(): String?
    {
        return kategori
    }

    fun getId(): String?
    {
        return id
    }

    fun getidApps(): String?
    {
        return id_apps
    }

    fun getKonten(): String?
    {
        return konten
    }
    fun getjudulUri(): String?
    {
        return judul_uri
    }

    fun getTanggal(): String?
    {
        return tanggal
    }


    fun getfoto_modul(): String?
    {
        return foto_modul
    }

    fun getjudul(): String?
    {
        return judul
    }

    fun getTotal(): String?
    {
        return total
    }

    fun getFoto(): String?
    {
        return foto
    }


    fun getnama_bab(): String?
    {
        return nama_bab
    }

    fun getkategori_Bab(): String?
    {
        return kategori_bab
    }

    fun getid_bab(): String?
    {
        return id_bab
    }

    fun getrarity(): String?
    {
        return rarity
    }

    fun getbahasaweb(): String?
    {
        return bahasa
    }

    fun geticon(): String?
    {
        return icon
    }


    fun getnama_kategori(): String?
    {
        return nama_kategori
    }

    fun geticon_kategori(): String?
    {
        return icon_kategori
    }





}
