package pet.ca.viewpractice.CustomView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import pet.ca.viewpractice.Utils

class TraditionalClock : View {

    private val paint: Paint = Paint()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var measuredWidth = Utils.dpToPixels(100f).toInt()
        var measuredHeight = Utils.dpToPixels(100f).toInt()

        measuredWidth = resolveSize(measuredWidth, widthMeasureSpec)
        measuredHeight = resolveSize(measuredHeight, widthMeasureSpec)


        val measuredSize: Int
        measuredSize =
                if (measuredWidth > measuredHeight) {
                    measuredHeight
                } else {
                    measuredWidth
                }

        setMeasuredDimension(measuredSize, measuredSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //draw body
        canvas!!.save()
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dpToPixels(3f)
        canvas.drawCircle(measuredWidth / 2f, measuredWidth / 2f, measuredWidth / 3f, paint)
        paint.reset()
        canvas.restore()

        //draw hour line
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dpToPixels(3f)
        for (i in 0..11) {
            canvas.save()
            canvas.translate((measuredWidth / 2).toFloat(), (measuredWidth / 2).toFloat())
            canvas.rotate((30 * i).toFloat())
            canvas.drawLine(0f, measuredWidth / 3f, 0f, measuredWidth / 3.5f, paint)
            canvas.restore()
        }

        //draw minute line
        for (i in 0..60) {
            val degree = (i * 6)
            if (degree % 30 == 0) {
                continue
            }
            canvas.save()
            canvas.translate((measuredWidth / 2).toFloat(), (measuredWidth / 2).toFloat())
            canvas.rotate(degree.toFloat())
            canvas.drawLine(0f, measuredWidth / 3f, 0f, measuredWidth / 3.2f, paint)
            canvas.restore()
        }
        paint.reset()

    }
}