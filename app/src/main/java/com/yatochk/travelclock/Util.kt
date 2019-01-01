package com.yatochk.travelclock

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas

fun getBitmap(drawableRes: Int, activity: Context): Bitmap {
    val drawable = activity.resources.getDrawable(drawableRes, activity.theme)
    val canvas = Canvas()
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    canvas.setBitmap(bitmap)
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    drawable.draw(canvas)

    return bitmap
}