package com.oozou.composegraph.util

import androidx.compose.ui.graphics.Color
import com.oozou.composegraph.pie.PieData

object DataFactory {
    fun lineData() = listOf(
        40, 70, 80, 60, 90, 20, 10, 50, 30, 100
    )

    fun pieData() = listOf(
        PieData(20f, Color.Magenta),
        PieData(30f, Color.DarkGray),
        PieData(72f, Color.Black),
        PieData(87f, Color.Cyan),
        PieData(98f, Color.Yellow),
    )
}