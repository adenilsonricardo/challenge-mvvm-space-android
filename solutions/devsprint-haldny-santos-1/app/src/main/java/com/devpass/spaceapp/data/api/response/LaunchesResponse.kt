package com.devpass.spaceapp.data.api.response

import com.google.gson.annotations.SerializedName

data class LaunchesResponse(
    @SerializedName("links")
    val links: Links,
    @SerializedName("name")
    val nameRocket: String,
    @SerializedName("date_utc")
    val date: String,
    @SerializedName("success")
    val status: Boolean,
    @SerializedName("flight_number")
    val flightNumber: Int,
)

data class Links(
    @SerializedName("patch")
    val patch: Patch,
)

data class Patch(
    @SerializedName("small")
    val small: List<String>,
)
