package com.oozou.composegraph

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val stringResId: Int, @DrawableRes val drawableRes: Int ) {
    object LINE: Screen("line_screen", R.string.bottom_nav_item_line, 0)
    object PIE: Screen("pie_screen", R.string.bottom_nav_item_pie, 0)
    object BAR: Screen("bar_screen", R.string.bottom_nav_item_bar, 0)
}