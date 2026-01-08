package com.cmt.services.model

import java.io.Serializable

class PackageDetailsModel : Serializable {

    var mock_tests: MutableList<MockTestModel>? = null
    var videos: MutableList<SampleVideosModel>? = null
}