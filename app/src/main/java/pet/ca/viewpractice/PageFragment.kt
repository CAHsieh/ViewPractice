package pet.ca.viewpractice

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_view.view.*
import kotlinx.android.synthetic.main.view_traditional_clock.*
import pet.ca.viewpractice.customView.TraditionalClock

class PageFragment : Fragment() {

    companion object {

        private const val ARG_RES = "ARG_RES"

        fun newInstance(@LayoutRes resId: Int): PageFragment {
            val fragment = PageFragment()
            val args = Bundle()
            args.putInt(ARG_RES, resId)
            fragment.arguments = args
            return fragment
        }
    }

    @LayoutRes
    private var layoutRes: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        layoutRes = args!!.getInt(ARG_RES)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view, container, false)

        view.viewStub.layoutResource = layoutRes
        view.viewStub.inflate()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (layoutRes) {
            R.layout.view_traditional_clock -> {
                traditionalClock.startClock()
                val timerListener = object : TraditionalClock.TimerListener {
                    @SuppressLint("SetTextI18n")
                    override fun onTimeUpdate(h: Int, m: Int, s: Int) {
                        time.text = h.toString() + ":" + m.toString() + ":" + s.toString()
                    }
                }
                traditionalClock.timerListener = timerListener
            }

            R.layout.fragment_dashboard -> {

                dashboard.setCurrent(50)
                seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        if (fromUser) {
                            dashboard.setCurrent(progress)
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }

                })

                limitSetter.setOnClickListener {
                    val max: Int = upperLimit.text.toString().toInt()
                    seek.max = max
                    dashboard.smoothSetMax(max)
                }

                valueSetter.setOnClickListener {
                    val v: Int = value.text.toString().toInt()
                    val result = dashboard.smoothToCurrent(v)
                    if (result != v) {
                        value.setText(result.toString())
                    }
                    seek.progress = result
                }
            }

        }
    }

    override fun onStop() {
        super.onStop()
        when (layoutRes) {
            R.layout.view_traditional_clock -> {
                traditionalClock.stopClock()
            }

        }
    }
}