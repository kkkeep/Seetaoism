package com.seetaoism.widgets


/*
 * created by Cherry on 2019-10-09
**/
import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest
class RoundCorner constructor(context: Context,leftTop: Float = 0f,
                              rightTop: Float = 0f,
                              rightBottom: Float = 0f,
                              leftBottom: Float = 0f) : BitmapTransformation() {


    constructor(context: Context, corner: Float) : this(context,corner,corner,corner,corner) {
    }

    private val ID: String = "com.seetaoism.widgets$leftTop$rightTop$leftBottom$rightBottom"

    private val leftTop: Float = dip2px(context,leftTop).toFloat()
    private val rightTop =dip2px(context,rightTop).toFloat()
    private val leftBottom =  dip2px(context,leftBottom).toFloat()
    private val rightBottom =  dip2px(context,rightBottom).toFloat()

    private val ID_BYTES: ByteArray


    //dp转px
    fun dip2px(var0: Context, var1: Float): Int {
        val var2 = var0.resources.displayMetrics.density
        return (var1 * var2 + 0.5f).toInt()
    }

    init {
        ID_BYTES = ID.toByteArray(Key.CHARSET)
    }

    override fun transform(
            pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val width = toTransform.width
        val height = toTransform.height
        val bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setHasAlpha(true)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val radii = floatArrayOf(leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom)
        val path = Path()
        path.addRoundRect(rect, radii, Path.Direction.CW)
        canvas.drawPath(path, paint)
        return bitmap
    }

    override fun equals(other: Any?): Boolean {
        if (other is RoundCorner) {
            return leftTop == other.leftTop && rightTop == other.rightTop && leftBottom == other.leftBottom && rightBottom == other.rightBottom
        }
        return false
    }

    override fun hashCode(): Int {
        return ID.hashCode() + leftTop.hashCode() + rightTop.hashCode() + leftBottom.hashCode() + rightBottom.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }


}

