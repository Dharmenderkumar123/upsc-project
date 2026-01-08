package com.cmt.model

data class ChangePasswordModel(
    var old_password: String?=null,
    var new_password : String ?= null,
    var confirm_password : String ?= null
)