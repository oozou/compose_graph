package com.oozou.composegraph.line

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.oozou.composegraph.R
import com.oozou.composegraph.util.DataFactory

@Composable()
fun LineChart(
    chartData: List<Int>,
    elevation: Dp = 5.dp,
    shape: Shape = RoundedCornerShape(5.dp),
    strokeWidth: Float = 8f,
    strokeColor: Color = colorResource(id = R.color.purple_200),
    canvasHeight: Dp = 270.dp,
    modifier: Modifier = Modifier.fillMaxWidth().height(360.dp).padding(20.dp)
) {
    Card(
        modifier = modifier,
        elevation = elevation,
        shape = shape
    ) {
        Column(modifier = Modifier.wrapContentSize(align = Alignment.BottomStart)) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(canvasHeight)) {
                //IntervalBtwXCoordinates represents the equal interval between all x coordinates.
                //You may wonder, why divide by charData.size + 1 rather than just charData.size.
                //Let me illustrate with an example.
                //Suppose size.width = 100 and we have 10 data points then 100/10 = 10
                //The x coordinate will be positioned at point 10, 20, 30, 40...100
                //Remember the width of the canvas is a 100, if a line is drawn up to that point,
                //then there will be no space at the end of the canvas. Keep in mind that there was
                //a 10 unit space at the beginning of the canvas. So in order to solve this problem,
                //let's divide (size.width) / (data.size + 1) == 100 / 11 = 9.09
                //The x coordinates will then be positioned at 9.09, 18.18, 27.27...90.9
                //This way, at the beginning of the canvas, we have a space of 9.09 and at the end of
                //of the canvas after the last line has been drawn, we will still have a space of 9.1.
                //This ensures we have almost equal spaces at the beginning and end of the canvas.
                val intervalBtwXCoordinates = size.width / (chartData.size + 1)

                //Current horizontal position is set to zero which is the leftmost
                //region of the canvas. This value will be gradually moved to the rightmost region
                //as the canvas draws the chart.
                var currentXCoordinate = 0F

                //Represents maximum value in the chart data
                val maxValue = chartData.maxOrNull() ?: 0

                //Represents a list coordinates x and y axis for each chartData point
                val dataCoordinates = mutableListOf<PointF>()
                
                chartData.forEach { value ->
                    //Understand that the coordinate (0,0) represents top-left corner of the canvas
                    //So the bigger the value of coordinateY, the lower its position on the canvas
                    //   and vice versa.
                    //Keep in mind that the results of (size.height / maxValue) is going to be
                    //   constant for all coordinate points calculations.
                    //For example lets take two data points 20 and 80. Let's assume maxValue = 100
                    //Let's assume the result of our (size.height / maxValue) = 5
                    //The coordinateY for data (20) will be (100 - 20) * 5 = 400
                    //The coordinateY for data (80) will be (100 - 80) * 5 = 100
                    //Thus, data (80) will be positioned higher on the canvas
                    //   as it is closer to zero than data (20)
                    //Also, the calculations done for coordinateY ensures we get a value within the
                    //range of the height of the canvas.
                    val coordinateY = (maxValue - value) * (size.height / maxValue)
                    val coordinateX = currentXCoordinate + intervalBtwXCoordinates
                    dataCoordinates.add(PointF(coordinateX, coordinateY))
                    currentXCoordinate = coordinateX
                }

                for (i in 0 until dataCoordinates.size - 1) {
                    drawLine(
                        start = Offset(dataCoordinates[i].x, dataCoordinates[i].y),
                        end = Offset(dataCoordinates[i + 1].x, dataCoordinates[i + 1].y),
                        color = strokeColor,
                        strokeWidth = strokeWidth
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLineChartScreen(){
    LineChart(DataFactory.lineData())
}