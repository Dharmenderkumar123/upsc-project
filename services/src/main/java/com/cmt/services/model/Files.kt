package com.cmt.services.model

import com.google.gson.annotations.SerializedName

class Files {
    @SerializedName("progressive")
    private var progressive: List<ProgressiveItem>? = null

    fun setProgressive(progressive: List<ProgressiveItem?>?) {
        this.progressive = progressive as List<ProgressiveItem>?
    }

    fun getProgressive(): List<ProgressiveItem?>? {
        return progressive
    }
}