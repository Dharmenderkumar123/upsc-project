package com.cmt.services.apiInterface

import com.cmt.services.helper.IServiceConstants
import com.cmt.services.model.APIResponse
import com.cmt.services.model.MyMaterialModel
import com.cmt.services.model.SubjectsModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyMaterialAPI {
    @FormUrlEncoded
    @POST(IServiceConstants.API.my_materials)
    fun myMaterials(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<MutableList<MyMaterialModel>>>

    @FormUrlEncoded
    @POST(IServiceConstants.API.Subject_materials)
    fun purchasedMaterials(@FieldMap userParams: HashMap<String, String>): Call<APIResponse<MutableList<MyMaterialModel>>>

}