package pet.ca.viewpractice.customView

/*
  儀表板View

  1.有一個弧形的線條 done.
  2.弧型線條顏色控制(attr)
  3.弧形內顯示數值or百分比 (attr控制顯示哪個)
  4.設定max值 done.
  5.設定Current值 done.
  6.setCurrent及smoothToCurrent done.
  7.smooth要有加速及反彈效果 done.

 */

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import pet.ca.viewpractice.Utils


class DashboardView : View {


    private val rect: RectF = RectF()
    private val paint: Paint = Paint()

    private var rate: Float = 0f
    private var upperLimit: Int = 100
    private var current: Int = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    public fun setMax(upperLimit: Int) {
        this.upperLimit = upperLimit
        if (current > upperLimit) {
            current = upperLimit
        }

        rate = (current.toFloat() / upperLimit)
        invalidate()
    }

    public fun setUpperLimit(upperLimit: Int) {
        setMax(upperLimit)
    }

    public fun smoothSetMax(upperLimit: Int) {
        val animator: ObjectAnimator = ObjectAnimator.ofInt(this, "upperLimit", this.upperLimit, upperLimit)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 500
        animator.start()
    }

    public fun setCurrent(current: Int): Int {
        this.current = current
        if (current > upperLimit) {
            this.current = upperLimit
        }

        rate = (current.toFloat() / upperLimit)
        invalidate()
        return this.current
    }

    public fun smoothToCurrent(current: Int): Int {
        val to: Int = if (current > upperLimit) upperLimit else current
        val relay = if (current >= this.current) current + 20 else current - 20

        val keyframe1 = Keyframe.ofInt(0f, this.current)
        val keyframe2 = Keyframe.ofInt(0.5f, relay)
        val keyframe3 = Keyframe.ofInt(1f, to)
        val holder = PropertyValuesHolder.ofKeyframe("current", keyframe1, keyframe2, keyframe3)
        val animator = ObjectAnimator.ofPropertyValuesHolder(this, holder)

//        val animator: ObjectAnimator = ObjectAnimator.ofInt(this, "current", this.current, to)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 1000
        animator.start()

        return to
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var baseWidth = Utils.dpToPixels(200f).toInt()
        var baseHeight = Utils.dpToPixels(200f).toInt()

        baseWidth = resolveSize(baseWidth, widthMeasureSpec)
        baseHeight = resolveSize(baseHeight, heightMeasureSpec)

        setMeasuredDimension(baseWidth, baseHeight)
        baseWidth = measuredWidth
        baseHeight = measuredHeight


        val offset: Float = Math.abs((baseHeight - baseWidth) / 2f)

        when {
            baseHeight > baseWidth -> {
                //比較長， rect top,bottom要調
                rect.set(5f, offset, baseWidth.toFloat() - 5, offset + baseWidth)
            }
            baseHeight < baseWidth -> {
                //比較寬，rect left,right要調
                rect.set(offset, 5f, offset + baseHeight, baseHeight.toFloat() - 5)
            }
            else -> //正方形，rect長寬不用調
                rect.set(5f, 5f, baseWidth.toFloat() - 5, baseHeight.toFloat() - 5)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //先畫dashboard基準線
        canvas?.save()
        paint.color = Color.BLACK
        paint.strokeWidth = Utils.dpToPixels(1f)
        paint.style = Paint.Style.STROKE
        canvas?.drawArc(rect, 30f, -240f, false, paint)
        canvas?.restore()

        //根據目前的rate畫線條

        canvas?.save()
        paint.color = Color.RED
        paint.strokeWidth = Utils.dpToPixels(5f)
        paint.style = Paint.Style.STROKE
        val sweep: Float = -240f * rate
        val start: Float = 30f - 240f * (1 - rate)
        canvas?.drawArc(rect, start, sweep, false, paint)
        canvas?.restore()
    }
}