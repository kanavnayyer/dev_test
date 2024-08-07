package com.awesome.devtest.datamodels

data class Datam(
    val favourite_links: List<Any>? = null,
    val overall_url_chart: List<ChartDataPoint>? = null,
    val recent_links: List<RecentLink>? = null,
    val top_links: List<TopLink>? = null
)

data class ChartDataPoint(
    val x: Float, // X-axis value
    val y: Float  // Y-axis value
)