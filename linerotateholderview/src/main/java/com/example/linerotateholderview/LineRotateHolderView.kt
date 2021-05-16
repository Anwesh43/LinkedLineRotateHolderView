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

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawLineRotateHolder(scale : Float, w : Float, h : Float, paint : Paint) {
    val size : Float = Math.min(w, h) / sizeFactor
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(lines, parts)
    val sf2 : Float = sf.divideScale(lines + 1, parts)
    val sqSize : Float = Math.min(w, h) / boxSizeFactor
    save()
    for (j in 0..(lines - 1)) {
        save()
        translate(0f, h / 2)
        rotate(180f * sf.divideScale(j, parts))
        save()
        translate(-size * j, 0f)
        drawLine(0f, 0f, -size, 0f, paint)
        restore()
        restore()
    }
    save()
    translate((size * lines) / 2, (h / 2) * sf2)
    drawRect(RectF(-sqSize / 2, 0f, sf1 * sqSize * 0.5f, sqSize), paint)
    restore()
    restore()
}

fun Canvas.drawLRHNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawLineRotateHolder(scale, w, h, paint)
}

class LineRotateHolderView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }
}