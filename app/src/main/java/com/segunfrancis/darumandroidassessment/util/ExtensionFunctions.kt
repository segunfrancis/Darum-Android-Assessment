package com.segunfrancis.darumandroidassessment.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException
import timber.log.Timber

fun ImageView.loadImage(
    imageUrl: String,
    loading: () -> Unit,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    loading.invoke()
    Glide.with(this)
        .load(imageUrl)
        .listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                onFailure.invoke()
                Timber.e(e)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onSuccess.invoke()
                return false
            }
        })
        .into(this)
}

fun Throwable.handleThrowable(): String {
    Timber.e(this)
    return when {
        this is UnknownHostException -> "We've detected a network problem. Please check your internet connection and try again"
        this is HttpException && this.code() in 401..403 -> "Not authorized. Please make sure your API key is valid"
        this is HttpException && this.code() in 500..599 -> "Sorry, we are currently unable to complete your request. Please try again later"
        this is HttpException -> "Sorry, we are currently unable to complete your request. Please try again later"
        this is SocketTimeoutException -> "Please check your connectivity and try again"
        else -> this.message
            ?: "Sorry, we are currently unable to complete your request. Please try again later"
    }
}
