package com.cmt.services.model

import com.google.gson.annotations.SerializedName

class ProgressiveItem {
    @SerializedName("profile")
    private var profile = 0

    @SerializedName("mime")
    private var mime: String? = null

    @SerializedName("origin")
    private var origin: String? = null

    @SerializedName("width")
    private var width = 0

    @SerializedName("fps")
    private var fps = 0

    @SerializedName("id")
    private var id: String? = null

    @SerializedName("cdn")
    private var cdn: String? = null

    @SerializedName("url")
    private var url: String? = null

    @SerializedName("quality")
    private var quality: String? = null

    @SerializedName("height")
    private var height = 0

    fun setProfile(profile: Int) {
        this.profile = profile
    }

    fun getProfile(): Int {
        return profile
    }

    fun setMime(mime: String?) {
        this.mime = mime
    }

    fun getMime(): String? {
        return mime
    }

    fun setOrigin(origin: String?) {
        this.origin = origin
    }

    fun getOrigin(): String? {
        return origin
    }

    fun setWidth(width: Int) {
        this.width = width
    }

    fun getWidth(): Int {
        return width
    }

    fun setFps(fps: Int) {
        this.fps = fps
    }

    fun getFps(): Int {
        return fps
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getId(): String? {
        return id
    }

    fun setCdn(cdn: String?) {
        this.cdn = cdn
    }

    fun getCdn(): String? {
        return cdn
    }

    fun setUrl(url: String?) {
        this.url = url
    }

    fun getUrl(): String? {
        return url
    }

    fun setQuality(quality: String?) {
        this.quality = quality
    }

    fun getQuality(): String? {
        return quality
    }

    fun setHeight(height: Int) {
        this.height = height
    }

    fun getHeight(): Int {
        return height
    }
}