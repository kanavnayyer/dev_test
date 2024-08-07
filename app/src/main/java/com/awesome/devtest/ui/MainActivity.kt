package com.awesome.devtest.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awesome.devtest.R
import com.awesome.devtest.adapters.DashboardAdapter
import com.awesome.devtest.adapters.RecentLinksAdapter
import com.awesome.devtest.datamodelApi.DashboardViewModel
import com.awesome.devtest.datamodels.ChartDataPoint
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MainActivity : AppCompatActivity() {

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var recentLinksAdapter: RecentLinksAdapter
    private lateinit var topLinksAdapter: DashboardAdapter
    private lateinit var chart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        val myButton = findViewById<Button>(R.id.btnViewAnalytics)

        // Create the drawable programmatically
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(Color.WHITE) // Background color
            setStroke(1, Color.parseColor("#D8D8D8")) // Border width and color
            cornerRadius = 4f // Optional: radius for rounded corners
        }

        // Apply the drawable to the button
        myButton.background = drawable
        // Initialize RecyclerViews
        val recyclerViewRecentLinks = findViewById<RecyclerView>(R.id.recyclerViewamazon)
        recyclerViewRecentLinks.layoutManager = LinearLayoutManager(this)

        val recyclerViewTopLinks = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerViewTopLinks.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Initialize Adapters with empty lists
        recentLinksAdapter = RecentLinksAdapter(emptyList())
        topLinksAdapter = DashboardAdapter(emptyList())

        recyclerViewRecentLinks.adapter = recentLinksAdapter
        recyclerViewTopLinks.adapter = topLinksAdapter

        // Initialize LineChart
        chart = findViewById(R.id.chart1)

        // Observe data from ViewModel
        viewModel.data.observe(this) { datamodel ->
            datamodel?.let {
                // Update the adapters with data from datamodel
                recentLinksAdapter.updateData(it.data.recent_links ?: emptyList())
                topLinksAdapter.updateTopLinks(it.data.top_links ?: emptyList())
                updateChart(it.data.overall_url_chart)
                Log.d("MainActivity", "Data received: $it")
            }
        }

        // Fetch data from the API
        viewModel.fetchData()
    }

    private fun transformToChartDataPoints(data: Any?): List<ChartDataPoint>? {
        return (data as? List<Map<String, Any>>)?.mapNotNull {
            val x = (it["x"] as? Number)?.toFloat() ?: return@mapNotNull null
            val y = (it["y"] as? Number)?.toFloat() ?: return@mapNotNull null
            ChartDataPoint(x, y)
        }
    }

    private fun updateChart(chartData: Any?) {
        // Transform chart data into List<ChartDataPoint>
        val chartDataPoints = transformToChartDataPoints(chartData)

        if (chartDataPoints == null) {
            Log.e("MainActivity", "Error: Chart data is null or not in the expected format")
            return
        }

        // Check if chartDataPoints is not empty
        if (chartDataPoints.isEmpty()) {
            Log.e("MainActivity", "Error: Chart data is empty")
            return
        }

        // Convert List<ChartDataPoint> to Entry list
        val entries = chartDataPoints.mapIndexed { index, point ->
            Entry(index.toFloat(), point.y)
        }

        // Create a LineDataSet
        val dataSet = LineDataSet(entries, "Chart Label").apply {
            color = resources.getColor(R.color.black, null)
            valueTextColor = resources.getColor(R.color.blue, null)
            valueTextSize = 12f
            setDrawFilled(true)
            fillColor = resources.getColor(R.color.blue, null)
            fillAlpha = 50
        }

        // Create LineData
        val lineData = LineData(dataSet)

        // Set data to chart
        chart.data = lineData

        // Refresh chart
        chart.apply {
            invalidate() // Refresh chart
            notifyDataSetChanged() // Notify that data has changed
        }

        Log.d("MainActivity", "Chart updated with data: $entries")
    }


    private fun setupChart() {
        // Set some basic chart configurations
        chart.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            setDrawBorders(false)
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            setBackgroundColor(resources.getColor(R.color.white, null))

            // Configure X-axis
            xAxis.apply {
                isEnabled = true
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                setDrawAxisLine(true)
            }

            // Configure Y-axis
            axisLeft.apply {
                setDrawGridLines(true)
                setDrawAxisLine(true)
            }

            axisRight.isEnabled = false

            // Legend configuration
            legend.isEnabled = true
        }
    }

}