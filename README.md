# Compose Graph

The purpose of this repository is to explore creating re-usable graph components with compose.
Some of the graph components that will be explored include: line chart, pie chart, bar chart, etc.


## LINE CHART
Function Definition:
```kotlin
fun LineChart(
     chartData: List<Int>,
     elevation: Dp = 5.dp,
     shape: Shape = RoundedCornerShape(5.dp),
     strokeWidth: Float = 8f,
     strokeColor: Color = Color(0xFFBB86FC),
     canvasHeight: Dp = 270.dp,
     modifier: Modifier = Modifier.fillMaxWidth().height(360.dp).padding(20.dp)
)
```
Calling the line chart composable with just the data points will cause the LineChart function to use
default values for the remaining parameters.

Example:
```kotlin
LineChart(listOf(10, 40, 20, 30, 80, 60))
```

If you want to customize the line chart, you can modify the default values for the remaining
parameters in the function.

##PIE CHART
Function Definition:
```kotlin
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
)
```

Default parameters can be overridden to customize the PieChart.
Make sure you set height and width for your modifiers. Tap gestures are not working on the canvas if they are not set.
