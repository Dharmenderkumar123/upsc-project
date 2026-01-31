package com.cmt.model

import kotlinx.serialization.Serializable

@Serializable
data class UserAnswer(
    val qid: Int,
    val answer: String?
)