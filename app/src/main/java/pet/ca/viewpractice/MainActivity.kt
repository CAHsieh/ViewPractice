package pet.ca.viewpractice

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager.adapter = PagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(pager)
    }


    class PagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        val titles: Array<String> = arrayOf("Clock","Dashboard")
        val resIds: Array<Int> = arrayOf(R.layout.view_traditional_clock, R.layout.fragment_dashboard)

        override fun getItem(position: Int): Fragment {
            return PageFragment.newInstance(resIds[position])
        }

        override fun getCount(): Int {
            return titles.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}
