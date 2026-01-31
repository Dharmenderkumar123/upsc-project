package com.cmt.services.model

data class UserRank(
    val user_id: String,
    val test_id: String,
    val marks_obtained: String,
    val name: String,
    val rank: Int
)