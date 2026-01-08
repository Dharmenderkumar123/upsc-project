package com.cmt.services.model

import java.io.Serializable

class OptionsModel : Serializable {
    val question_id: String? = null
    val question: String? = null
    val option1: String? = null
    val option2: String? = null
    val option3: String? = null
    val option4: String? = null
    val answer: String? = null
    val marks: String? = null
    var isSelected: Boolean = false
    var isSelectedQues: Boolean = false
    var isMarkReview: Boolean = false
    var selectedOption: String = ""
}