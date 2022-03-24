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

# Roadmap
The folliwings are the list of graph and chart type that this library could consider to support.

1. Multiple Line Chart (with curve and legend)

![Screen Shot 2565-03-24 at 11 14 05](https://user-images.githubusercontent.com/19642082/159846672-0b66d302-2d99-43a1-861c-2895eb30fad1.png)

2. Filled Line Chart

![Screen Shot 2565-03-24 at 11 16 22](https://user-images.githubusercontent.com/19642082/159846817-f69b5b08-7c30-488c-81d9-64c9e81a826c.png)

3. Bar Chart (with legend)

![Screen Shot 2565-03-24 at 11 16 55](https://user-images.githubusercontent.com/19642082/159847008-7809319c-1c94-4d7a-8ed2-55c434b382ff.png)
![Screen Shot 2565-03-24 at 11 17 09](https://user-images.githubusercontent.com/19642082/159847014-4fb3cba8-c15c-41c5-81e6-03baabee84a3.png)

4. Horizontal Bar Chart

![Screen Shot 2565-03-24 at 11 17 09](https://user-images.githubusercontent.com/19642082/159847014-4fb3cba8-c15c-41c5-81e6-03baabee84a3.png)

5. Pie Chart

![Screen Shot 2565-03-24 at 11 17 43](https://user-images.githubusercontent.com/19642082/159847404-94f5e9b9-9bd5-40d0-92bb-eebb83d99d68.png)

6. Redar Chart

![Screen Shot 2565-03-24 at 11 20 42](https://user-images.githubusercontent.com/19642082/159847409-4709ae96-7865-44ad-a55e-22ee10b9c5c9.png)
