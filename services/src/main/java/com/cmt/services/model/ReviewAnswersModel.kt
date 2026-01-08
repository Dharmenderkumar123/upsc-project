package com.cmt.services.model

import java.io.Serializable

class ReviewAnswersModel : Serializable {
    var question_id: String? = null
    var question: String? = null
    var option1: String? = null
    var option2: String? = null
    var option3: String? = null
    var option4: String? = null
    var answer: String? = null
    var marks: String? = null
    var solution: String? = null
    var answered: String? = null
    var answer_status: String? = null

}