package com.cmt.services.model

import com.google.gson.annotations.SerializedName

class PackagesModel {
    @SerializedName("package")
    var packageDes: String=""
    var description: String=""
    var price: String=""
    var days: String=""
    var isClicked=false
}