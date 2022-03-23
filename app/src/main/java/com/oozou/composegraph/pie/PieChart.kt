package com.oozou.composegraph.pie

import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp


@Composable
fun PieChart(
    pieDataList: List<PieData>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(500.dp)
        .padding(15.dp),
    radius: Float = 500f,
    isAnimate: Boolean = true,
    onClick: (index: Int) -> Unit,
) {
    //Topmost part of a circle = 270 deg, rightmost = 0 or 360 deg,
    // bottommost = 90 and leftmost = 180
    //The pie chart will be drawn in a clockwise direction. It will start at 0 which is the
    //rightmost part of the circle and then gradually draw arcs round the entire circle.
    var startAngle = 0f
    val totalSumOfPieDataValues = pieDataList.sumOf { it.value.toInt() }

    //Rect represents a rectangle around which our pie will be drawn.
    //The x-axis is set to -radius to radius while y-axis is also set to -radius  to radius
    //Why you might ask? Why not set both x and y axis from 0 to radius
    //Remember the top left corner of the canvas represents 0, 0. So based on this, if we have an
    //x-axis path from 0 to any +ve integer and y-axis path from 0 to any +ve integer, we will have
    //an inverted right angle triangle which is a single quadrant
    //Keep in mind that a radius is the distance from the centre of a circle to any point on the circumference
    //In order to form a well rounded circle, we will expand the range of our canvas on both the x
    //and y axis. X-axis will range from -radius to 0 to radius (leftmost to center to rightmost)
    //and Y-axis will range from -radius to 0 to radius (topmost to center to bottommost)
    //Therefore (0,0) will then be the center of our newly formed rectangle
    //This will allow us to have four quadrants that we can draw a well rounded and centered circle on
    val rect = Rect(Offset(-radius, -radius), Offset(radius, radius))

    //A list of angles that will be calculated from pieDataList
    val pieAngles = mutableListOf<Float>()

    //This state is responsible for triggering the tween animation to run on the canvas
    var isStartDrawingPie by remember { mutableStateOf(false) }

    //This animation state will be triggered to run for a duration of 1 second
    // when isStartDrawingPie is set to true
    val animationStateValue by animateFloatAsState (
        targetValue = if (isStartDrawingPie) 1f else 0f,
        animationSpec = FloatTweenSpec(duration = 1000)
    )

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures (
                    onTap = { offset ->
                        //Offset here represents the coordinates of the touch on the screen
                        //Why do we need to subtract radius from offset.x and offset.y?
                        //Suppose we have a radius of 200. That means we have an x coordinate
                        //ranging from -200 to 0 to 200 (leftmost to center to rightmost)
                        // and a y coordinate ranging from -200 to 0 to 200 (topmost to center to bottommost)
                        //When a sector is clicked in the top-left region say at touchY = -170 and touchX = -150
                        //offset.x will actually return 30 and offset.y will return 50
                        //The offset value for gestures is like this because it is treating the top left corner as (0,0)
                        //Bear in mind that (0,0) is no longer the top left of our canvas, it is the center.
                        //So in order to get the actual touch points that matches our canvas coordinates
                        //We will do touchX = 30 - 200 = -170 and touchY = 50 - 200 = -150
                        val touchX = offset.x - radius
                        val touchY = offset.y - radius

                        //Calculate the angle between two points touchX and touchY
                        var touchAngle = Math.toDegrees(Math.atan2(touchY.toDouble(), touchX.toDouble()))

                        //If the condition below is true, then that means touchAngle is -ve
                        //Since we do not have any negative angle in our pie, we need to make it +ve
                        //by adding 360 to it
                        if (touchX < 0 && touchY < 0 || touchX > 0 && touchY < 0) {
                            touchAngle += 360
                        }

                        val index = getPieDataIndexFromTouchAngle(pieAngles, touchAngle)
                        onClick(index)
                    }
                )
            }
    ) {
        //translate ensures the canvas center (0, 0) is not drawn at the topLeft corner of the screen
        translate(radius, radius) {
            isStartDrawingPie = true //triggers animation to run
            pieDataList.forEach { pieData ->
                //There are 360 degrees in a circle. In order to find the angle of a pieData, we
                //need to divide the pieData value by the total sum of pieData values and then multiply
                //it by 360. This way we get an angle that is within the range of  0 - 360 degrees.
                val pieDataAngle = (pieData.value / totalSumOfPieDataValues) * 360f

                val path = Path()

                //Set the beginning of the next contour to the point of origin of the canvas
                //The origin in this case is the center of the circle. (Reference comment on 'rect' above)
                //This ensures that the arc sector is drawn from the origin to the angles on the circumference
                path.moveTo(0f, 0f)

                //This represents the angle that should be measured from a given starting point
                val sweepAngle = if (isAnimate) pieDataAngle * animationStateValue else pieDataAngle

                //An arc is the length between two points around the circumference of a circle
                //This sets the start and end angles for the arc on the circumference of the circle
                //Suppose startAngle = 50 and sweepAngle = 30, how will the arc points be marked?
                //The first point of the arc will be at angle 50 deg while the second point will be
                //at angle 50 + 30 = 80 deg
                path.arcTo(rect = rect, startAngle, sweepAngle, false)

                //TODO(Figure out how to write text within the sector bounded by an arc)

                //A sector (an area of a circle bounded by two radii) is drawn from the center of
                // the circle to the start and end angles using path.arcTo above. This sector is also
                // filled with the pieData color
                drawPath(path = path, color = pieData.color)

                pieAngles.add(pieDataAngle)
                startAngle += pieDataAngle
            }
        }
    }

}

private fun getPieDataIndexFromTouchAngle(
    pieAngles: List<Float>,
    touchAngle: Double
): Int {
    var totalAngle = 0f
    for ((index, angle) in pieAngles.withIndex()) {
        totalAngle += angle
        if (touchAngle <= totalAngle) return index
    }

    return Int.MIN_VALUE
}