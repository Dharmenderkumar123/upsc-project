package com.cmt.services.model

data class FaqModel(val question: String, val answer: String, var isSelected: Boolean = false)