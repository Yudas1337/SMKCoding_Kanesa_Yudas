package com.haxor.techpedia.konfigurasi

/**
 * Created by Yudas Malabi on 10/08/2019
 */

open class Value {

    var value: String? = null
        internal set
    var message: String? = null
        internal set
    var result: List<Result>? = null
        internal set
    var isError: Boolean = false
}
