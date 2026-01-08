package com.cmt.services.model

import java.io.Serializable

class PurchasedTestOptionsModel : Serializable {
    var exam_id: String? = null
    var questions: MutableList<OptionsModel>? = null

}