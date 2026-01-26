package com.cmt.services.model

import java.io.Serializable

class Courses :Serializable {
    var category_id: String? = null
    var category_name: String? = null
    var image: String? = null
    var is_purchased: Boolean = false
}