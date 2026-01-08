package com.cmt.services.model

import java.io.Serializable

class SubjectsListModel : Serializable {
    var package_id: String? = null
    var actual_price: String? = null
    var price: String? = null
    var days: String? = null
    var description: String? = null
    var image: String? = null
    var subjects: MutableList<AgricatCategoryModel>? = null
    var paid_status: String? = null
}