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
     strokeColor: Color = colorResource(id = R.color.purple_200),
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
