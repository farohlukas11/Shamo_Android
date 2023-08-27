package com.faroh.shamoandroid.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@kotlinx.parcelize.Parcelize
data class RegisterAndLoginResponse(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("meta")
    val meta: Meta? = null
) : Parcelable

@kotlinx.parcelize.Parcelize
data class User(

    @field:SerializedName("profile_photo_url")
    val profilePhotoUrl: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("roles")
    val roles: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("email_verified_at")
    val emailVerifiedAt: String? = null,

    @field:SerializedName("current_team_id")
    val currentTeamId: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("profile_photo_path")
    val profilePhotoPath: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Meta(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable

@kotlinx.parcelize.Parcelize
data class Data(

    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("token_type")
    val tokenType: String? = null,

    @field:SerializedName("user")
    val user: User? = null
) : Parcelable
