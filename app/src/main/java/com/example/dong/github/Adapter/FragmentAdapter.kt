package com.example.dong.github.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by 振兴 on 2018.3.12.
 */

class FragmentAdapter(fm: FragmentManager,private val mFragmentList:ArrayList<Fragment> ,private val mFragmentTitle:ArrayList<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitle[position]
    }
}
