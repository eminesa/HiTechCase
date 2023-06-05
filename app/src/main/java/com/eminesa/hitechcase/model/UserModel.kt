package com.eminesa.hitechcase.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val avatar: Uri?,
    val firstName: String?,
    val mail: String?,
    val password: String?,
    val webSite: String?
) : Parcelable
