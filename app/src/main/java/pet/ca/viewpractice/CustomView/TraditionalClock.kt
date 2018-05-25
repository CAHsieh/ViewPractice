package pet.ca.viewpractice.CustomView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import pet.ca.viewpractice.Utils
import java.util.*

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

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dpToPixels(3f)
        for (i in 0..60) {
            val degree = (i * 6)
            if (degree % 30 == 0) {
                //draw hour line
                canvas.save()
                canvas.translate((measuredWidth / 2).toFloat(), (measuredWidth / 2).toFloat())
                canvas.rotate(degree.toFloat())
                canvas.drawLine(0f, measuredWidth / 3f, 0f, measuredWidth / 3.5f, paint)
                canvas.restore()
                continue
            }

            //draw minute line
            canvas.save()
            canvas.translate((measuredWidth / 2).toFloat(), (measuredWidth / 2).toFloat())
            canvas.rotate(degree.toFloat())
            canvas.drawLine(0f, measuredWidth / 3f, 0f, measuredWidth / 3.2f, paint)
            canvas.restore()
        }
        paint.reset()


        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY) % 12
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        //draw hour hand
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dpToPixels(6f)
        val hourBaseDegree = 30 * hour
        val hourOffsetDegree = minute / 2f
        canvas.save()
        canvas.translate((measuredWidth / 2).toFloat(), (measuredWidth / 2).toFloat())
        canvas.rotate((360f - hourBaseDegree) + hourOffsetDegree)
        canvas.drawLine(0f, 0f, 0f, -measuredWidth / 4.5f, paint)
        canvas.restore()

        //draw minute hand
        paint.color = Color.DKGRAY
        paint.strokeWidth = Utils.dpToPixels(4f)
        val minBaseDegree = 6 * minute
        val minOffsetDegree = second / 10f
        canvas.save()
        canvas.translate((measuredWidth / 2).toFloat(), (measuredWidth / 2).toFloat())
        canvas.rotate((minBaseDegree + minOffsetDegree))
        canvas.drawLine(0f, 0f, 0f, -measuredWidth / 3.5f, paint)
        canvas.restore()

        //draw second hand
        paint.color = Color.BLACK
        paint.strokeWidth = Utils.dpToPixels(2f)
        val secDegree = 6 * second
        canvas.save()
        canvas.translate((measuredWidth / 2).toFloat(), (measuredWidth / 2).toFloat())
        canvas.rotate(secDegree.toFloat())
        canvas.drawLine(0f, 0f, 0f, -measuredWidth / 3.5f, paint)
        canvas.restore()
    }

    private var running: Boolean = false

    fun startClock() {
        running = true
        Thread(Runnable {
            while (running) {
                invalidate()
                Thread.sleep(1000)
            }
        }).start()
    }

    fun stopClock() {
        running = false
    }
}