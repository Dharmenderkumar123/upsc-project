package com.cmt.helper

interface IConstants {

    object IntentStrings {
        const val type = "type"
        const val dialog = "dialog"
        const val payload = "payload"
        const val title = "title"
        const val userId = "userId"
        const val courseType = "courseType"
        const val course = "course"
        const val ebook = "ebook"
        const val image = "image"
        const val description = "description"
        const val model = "model"
        const val youtubeId = "youtubeId"
        const val language = "language"
        const val testId = "testId"
        const val subjectId = "subjectId"
        const val duration = "duration"
        const val examId = "examId"
        const val result = "result"
        const val catType = "catType"
    }

    object ValidateStrings{
        const val expired = "Expired"
    }

    object FragmentType {
        const val Login = "Login"
        const val Register = "Register"
        const val Otp = "Otp"

        const val OnlineTest = "OnlineTest"
        const val ForgotPassword = "ForgotPassword"
        const val AppGuidFragment = "AppGuidFragment"
        const val SubCourse = "SubCourse"
        const val ReviewAnswer = "ReviewAnswer"
        const val AgricetCategory = "AgricetCategory"
        const val CourseDescription = "CourseDescription"
        const val Subjects = "Subjects"
        const val Categories = "Categories"
        const val SubjectsDetails = "SubjectsDetails"
        const val OlineTestCostDetails = "OlineTestCostDetails"
        const val EbookSubjects = "EbookSubjects"
        const val EbookSubjectsType = "EbookSubjectsType"
        const val Search = "Search"
        const val Notifications = "Notifications"
        const val Ebook = "Ebook"
        const val EditProfile = "EditProfile"
        const val TestScreen = "TestScreen"
        const val PurchasedTestScreen = "PurchasedTestScreen"
        const val PurchasedMaterials = "PurchasedMaterials"
        const val ScoreBoard = "ScoreBoard"
        const val ScoreBoardResult = "ScoreBoardResult"
        const val CurrentAffairsView = "CurrentAffairsView"

    }

    object ValueHolder {
        var courseName: String? = null
    }

    object ServerValidate {
        const val valid: String = "valid"
    }

    object Params {
        const val version = "version_code"
        const val device_type = "device_id"
        const val access_token = "access_token"
        const val push_notification_key = "push_notification_key"
        const val name = "name"
        const val dob = "dob"
        const val password = "password"
        const val email = "email"
        const val phone_number = "mobile"
        const val otp = "otp"
        const val user_id = "user_id"
        const val mobile = "mobile"
        const val device_id = "device_id"
        const val old_password = "old_password"
        const val new_password = "password"
        const val category_id = "category_id"
        const val page_no = "page_no"
        const val sub_category_id = "sub_category_id"
        const val search_key = "search_key"
        const val subject_id = "subject_id"
        const val exam_id = "exam_id"
        const val answers = "answers"
        const val message = "message"
        const val grand_total = "grand_total"
        const val txn_id = "txn_id"
        const val test_id = "test_id"
        const val language = "language"
        const val current_version = "current_version"
        const val tracking_id = "tracking_id"
        const val package_id = "package_id"
        const val amount = "amount"
        const val order_id = "order_id"
        const val status = "status"
        const val course_id = "course_id"

    }

    object Defaults {
        const val request_from = "android"
        const val forgot_password = "forgot_password"
        const val register = "register"
        const val login = "login"
        const val logout = "logout"
        const val test_submit_confirmation = "test_submit_confirmation"


    }

    object Pref {
        const val shared_preference = "shared_preference"
        const val access_token = Params.access_token
        const val push_notification_token = Params.push_notification_key
        const val user_id = "user_id"
    }

    object PermissionCode {
        const val app_permission = 1000
        const val storage_camera = 1001
        const val location = 1002
    }

    object ProfileType {
        const val My_Courses = "My_Courses"
        const val Results = "Results"
        const val My_Material = "My_Material"
        const val Current_Affairs = "Current_Affairs"
        const val My_Orders = "My_Orders"
        const val Settings = "Settings"
        const val Terms_conditions = "Terms_conditions"
        const val Help_Support = "Help_Support"
        const val Faq = "Faq"
        const val About_us = "About_us"
        const val Logout = "Logout"

    }

    object Response {
        const val valid = "valid"
        const val invalid = "invalid"
    }
}