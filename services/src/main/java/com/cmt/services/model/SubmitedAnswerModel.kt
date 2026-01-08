package com.cmt.services.model

import java.io.Serializable

class SubmitedAnswerModel : Serializable {
    var exam_id: String? = null
    var total_marks: String? = null
    var score: String? = null
    var correct_answers: String? = null
    var Wrong_answers: String? = null
    var not_answered: String? = null
    var correct_answers_percent: String? = null
    var Wrong_answers_percent: String? = null
    var not_answered_percent: String? = null
}