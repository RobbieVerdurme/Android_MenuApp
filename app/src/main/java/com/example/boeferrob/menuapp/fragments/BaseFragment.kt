package com.example.boeferrob.menuapp.fragments

import android.support.v4.app.Fragment

/**
 * the basefragement class is to provide an sjabloon that the fragments can use
 * in this case its to fill up the tabs on the Mainactivity with fragments (decide or foodlist)
 */
open class BaseFragment: Fragment() {
    /**
     * here we find the tag var
     * this is to find the fragment
     */
    open var TAG : String = ""

    companion object {
        const val DECIDE = 0
        const val FOODLIST = 1
    }
}