package pet.ca.viewpractice

import android.content.res.Resources

class Utils {

    companion object {
        fun dpToPixels(dp: Float): Float {
            val metrics = Resources.getSystem().displayMetrics
            return dp * metrics.density
        }
    }

}

