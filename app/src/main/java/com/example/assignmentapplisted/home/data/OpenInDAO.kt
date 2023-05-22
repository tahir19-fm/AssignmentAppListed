package com.example.assignmentapplisted.home.data

import com.google.gson.annotations.SerializedName

data class OpenInDAO (

    @SerializedName("status"                  ) var status                : Boolean? = null,
    @SerializedName("statusCode"              ) var statusCode            : Int?     = null,
    @SerializedName("message"                 ) var message               : String?  = null,
    @SerializedName("support_whatsapp_number" ) var supportWhatsappNumber : String?  = null,
    @SerializedName("extra_income"            ) var extraIncome           : Double?  = null,
    @SerializedName("total_links"             ) var totalLinks            : Int?     = null,
    @SerializedName("total_clicks"            ) var totalClicks           : Int?     = null,
    @SerializedName("today_clicks"            ) var todayClicks           : Int?     = null,
    @SerializedName("top_source"              ) var topSource             : String?  = null,
    @SerializedName("top_location"            ) var topLocation           : String?  = null,
    @SerializedName("startTime"               ) var startTime             : String?  = null,
    @SerializedName("links_created_today"     ) var linksCreatedToday     : Int?     = null,
    @SerializedName("applied_campaign"        ) var appliedCampaign       : Int?     = null,
    @SerializedName("data"                    ) var data                  : Data?    = Data()

)

data class RecentLinks (

    @SerializedName("url_id"         ) var urlId         : Int?    = null,
    @SerializedName("web_link"       ) var webLink       : String? = null,
    @SerializedName("smart_link"     ) var smartLink     : String? = null,
    @SerializedName("title"          ) var title         : String? = null,
    @SerializedName("total_clicks"   ) var totalClicks   : Int?    = null,
    @SerializedName("original_image" ) var originalImage : String? = null,
    @SerializedName("thumbnail"      ) var thumbnail     : String? = null,
    @SerializedName("times_ago"      ) var timesAgo      : String? = null,
    @SerializedName("created_at"     ) var createdAt     : String? = null,
    @SerializedName("domain_id"      ) var domainId      : String? = null,
    @SerializedName("url_prefix"     ) var urlPrefix     : String? = null,
    @SerializedName("url_suffix"     ) var urlSuffix     : String? = null,
    @SerializedName("app"            ) var app           : String? = null

)
data class TopLinks (

    @SerializedName("url_id"         ) var urlId         : Int?    = null,
    @SerializedName("web_link"       ) var webLink       : String? = null,
    @SerializedName("smart_link"     ) var smartLink     : String? = null,
    @SerializedName("title"          ) var title         : String? = null,
    @SerializedName("total_clicks"   ) var totalClicks   : Int?    = null,
    @SerializedName("original_image" ) var originalImage : String? = null,
    @SerializedName("thumbnail"      ) var thumbnail     : String? = null,
    @SerializedName("times_ago"      ) var timesAgo      : String? = null,
    @SerializedName("created_at"     ) var createdAt     : String? = null,
    @SerializedName("domain_id"      ) var domainId      : String? = null,
    @SerializedName("url_prefix"     ) var urlPrefix     : String? = null,
    @SerializedName("url_suffix"     ) var urlSuffix     : String? = null,
    @SerializedName("app"            ) var app           : String? = null

)
data class OverallUrlChart (

    @SerializedName("2023-04-22" ) var one : Int? = null,
@SerializedName("2023-04-23" ) var two: Int? = null,
@SerializedName("2023-04-24" ) var three : Int? = null,
@SerializedName("2023-04-25" ) var four : Int? = null,
@SerializedName("2023-04-26" ) var five : Int? = null,
@SerializedName("2023-04-27" ) var six : Int? = null,
@SerializedName("2023-04-28" ) var seven: Int? = null,
@SerializedName("2023-04-29" ) var eight : Int? = null,
@SerializedName("2023-04-30" ) var nine : Int? = null,
@SerializedName("2023-05-01" ) var ten: Int? = null,
@SerializedName("2023-05-02" ) var eleven: Int? = null,
@SerializedName("2023-05-03" ) var twelve : Int? = null,
@SerializedName("2023-05-04" ) var thirteen : Int? = null,
@SerializedName("2023-05-05" ) var fourteen : Int? = null,
@SerializedName("2023-05-06" ) var fifteen : Int? = null,
@SerializedName("2023-05-07" ) var sixteen: Int? = null,
@SerializedName("2023-05-08" ) var seventeen : Int? = null,
@SerializedName("2023-05-09" ) var eighteen : Int? = null,
@SerializedName("2023-05-10" ) var nineteen : Int? = null,
@SerializedName("2023-05-11" ) var twenty : Int? = null,
@SerializedName("2023-05-12" ) var twentyone : Int? = null,
@SerializedName("2023-05-13" ) var twentytwo : Int? = null,
@SerializedName("2023-05-14" ) var twentythree: Int? = null,
@SerializedName("2023-05-15" ) var twentyfour : Int? = null,
@SerializedName("2023-05-16" ) var twentyFive : Int? = null,
@SerializedName("2023-05-17" ) var twentySix : Int? = null,
@SerializedName("2023-05-18" ) var twentySeven : Int? = null,
@SerializedName("2023-05-19" ) var twentyEight : Int? = null,
@SerializedName("2023-05-20" ) var twentyNine : Int? = null,
@SerializedName("2023-05-21" ) var thirty : Int? = null,
@SerializedName("2023-05-22" ) var thirtyOne : Int? = null

)
data class Data (

    @SerializedName("recent_links"      ) var recentLinks     : ArrayList<RecentLinks> = arrayListOf(),
    @SerializedName("top_links"         ) var topLinks        : ArrayList<TopLinks>    = arrayListOf(),
    @SerializedName("overall_url_chart" ) var overallUrlChart : OverallUrlChart?       = OverallUrlChart()

)