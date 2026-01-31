package com.cmt.services.model

class ExamCompleteModel (
    val exam_id: String,
    val total_marks: Int,
    val score: Int,
    val correct_answers: Int,
    val Wrong_answers: Int,
    val not_answered: Int,
    val correct_answers_percent: Double,
    val Wrong_answers_percent: Double,
    val not_answered_percent: Double
)