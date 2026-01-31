package com.cmt.services.model

class APIResponse<T> {
    var error_code: String? = null
    var message: String? = null
    var data: T? = null
    var is_paid: Int=0
    var exam_id: Int=0
}

class PageNation<T> {
    var total_results_found: Int? = null
    var total_pages: Int? = null
    var results: T? = null
}