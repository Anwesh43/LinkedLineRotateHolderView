package com.example.linerotateholderview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.app.Activity
import android.content.Context

val colors : Array<Int> = arrayOf(
    "#f44336",
    "#311B92",
    "#BF360C",
    "#00C853",
    "#FFC107"
).map  {
    Color.parseColor(it)
}.toTypedArray()
val lines : Int = 3
val parts : Int = lines + 2
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val sizeFactor : Float = 6.9f
val boxSizeFactor : Float = 4.2f
val delay : Long = 20
val backColor : Int = Color.parseColor("#BDBDBD")
