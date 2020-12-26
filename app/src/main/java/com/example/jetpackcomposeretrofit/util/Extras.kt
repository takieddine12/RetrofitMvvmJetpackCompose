package com.example.jetpackcomposeretrofit.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Ambient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AmbientContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object Extras {

    @Composable
    fun loadPicture(imageUrl : String ,
                    @DrawableRes defaultImg : Int) : MutableState<Bitmap?> {

        val bitmapState : MutableState<Bitmap?> = mutableStateOf(null)

        Glide.with(AmbientContext.current)
                .asBitmap()
                .load(imageUrl)
                .placeholder(defaultImg)
                .error(defaultImg)
                .into(object  : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        bitmapState.value = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })
        return bitmapState
    }
}