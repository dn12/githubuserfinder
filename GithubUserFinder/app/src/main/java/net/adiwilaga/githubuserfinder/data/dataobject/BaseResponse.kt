package net.adiwilaga.githubuserfinder.data.dataobject


import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<T>,
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("message")
    val message: String
)