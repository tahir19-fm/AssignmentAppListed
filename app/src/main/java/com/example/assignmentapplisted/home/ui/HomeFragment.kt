package com.example.assignmentapplisted.home.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentapplisted.R
import com.example.assignmentapplisted.databinding.FragmentHomeBinding
import com.example.assignmentapplisted.home.data.OpenInDAO
import com.example.assignmentapplisted.home.data.RvModalHorizontal
import com.example.assignmentapplisted.home.util.HomeScreenAdapter
import com.example.assignmentapplisted.home.util.HomeScreenHorizontalRvAdapter
import com.example.assignmentapplisted.home.util.HomeViewModel
import com.example.assignmentapplisted.network.ApiResult
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var chart: LineChart

    companion object {
        const val RECENT_LINKS = 1
        const val TOP_LINKS = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setUpObserver()
    }


    @SuppressLint("SuspiciousIndentation")
    private fun setupViews() {
        viewModel.get()
        binding.rvLinksData.layoutManager = LinearLayoutManager(requireContext())
        binding.apply {
            tvGreeting.text = greeting()
            bad.setBackgroundResource(R.drawable.card_border)
            tvRecentLinks.setOnClickListener {
                viewModel.setState(RECENT_LINKS)
            }
            tvTopLinks.setOnClickListener {
                viewModel.setState(TOP_LINKS)
            }
        }
        setupGraph()
    }


    private fun setUpObserver() {
        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                ApiResult.Loading -> {
                    //show loading state
                }
                is ApiResult.Success -> {
                    val data = it.data as OpenInDAO
                    viewModel.setListDataTop()
                    setupHorizontalRv(data)

                }
                is ApiResult.Error -> {
                    Toast.makeText(requireContext(), "Api error : ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        viewModel.listData.observe(viewLifecycleOwner) {
            binding.rvLinksData.adapter = HomeScreenAdapter(it)
        }
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                RECENT_LINKS -> {
                    setRecentState()
                }
                TOP_LINKS -> {
                    setTopState()
                }
            }
        }
    }

    private fun setupHorizontalRv(data: OpenInDAO) {
        val list = listOf(
            RvModalHorizontal(
                R.drawable.pointor_backgroudn_icon,
                "Total Clicks",
                data.totalClicks.toString()
            ),
            RvModalHorizontal(
                R.drawable.location_background_icon,
                "Location",
                data.topLocation.toString()
            ),
            RvModalHorizontal(
                R.drawable.globe_background_icon,
                "Top Source",
                data.topSource.toString()
            ),
            RvModalHorizontal(R.drawable.clock_icon, "Best Time", data.startTime.toString())
        )
        binding.rvData.layoutManager=LinearLayoutManager(requireContext()).apply {
            orientation=LinearLayoutManager.HORIZONTAL
        }
        binding.rvData.adapter = HomeScreenHorizontalRvAdapter(list)
    }

    @SuppressLint("ResourceAsColor")
    private fun setRecentState() {
        binding.apply {
            tvRecentLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tvRecentLinks.setBackgroundResource(R.drawable.link_btn_res)
            tvTopLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvTopLinks.setBackgroundResource(R.color.transparent_color)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setTopState() {
        binding.apply {
            tvTopLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tvTopLinks.setBackgroundResource(R.drawable.link_btn_res)
            tvRecentLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvRecentLinks.setBackgroundResource(R.color.transparent_color)
        }
    }

    private fun greeting(): String {
        val currentTime = Date()
        val timeFormat = SimpleDateFormat("HH", Locale.getDefault())
        return when (timeFormat.format(currentTime).toInt()) {
            in 0..11 -> "Good Morning"
            in 12..16 -> "Good AfterNoon"
            in 17..20 -> "Good Evening"
            else -> "Good Night"
        }
    }

    private fun setupGraph() {
        chart = binding.chart
        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false
        chart.setTouchEnabled(false)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)
        val xAxis: XAxis = chart.xAxis
        val yAxis: YAxis = chart.axisLeft
        chart.axisRight.isEnabled = false
        yAxis.enableGridDashedLine(10f, 10f, 0f)
        yAxis.axisMaximum = 200f
        yAxis.axisMinimum = -10f
        val months = arrayOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
        )
        xAxis.valueFormatter = IndexAxisValueFormatter(months)
        xAxis.labelCount = months.size // Set the label count to the number of months
        xAxis.setAvoidFirstLastClipping(true) // Avoid clipping of the first and last labels
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.setExtraOffsets(10f, 0f, 10f, 10f) // Add extra offset to accommodate the labels
        chart.xAxis.textSize = 10f
        xAxis.setCenterAxisLabels(true) // Center the labels between the grid lines
        xAxis.granularity = 1f // Display one label per interval
        setData()
        chart.animateX(1500)
        val legend = chart.legend
        legend.form = Legend.LegendForm.LINE
        chart.invalidate()

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setData() {
        val dataPoints = listOf(50f, 80f, 120f, 90f, 110f, 70f, 100f, 130f)
        val values = ArrayList<Entry>()
        for (i in dataPoints.indices) {
            val value = dataPoints[i]
            val entry = Entry(i.toFloat(), value, resources.getDrawable(R.drawable.fade_red))
            values.add(entry)
        }
        val set1: LineDataSet
        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(values, "")
            set1.setDrawIcons(false)
            set1.enableDashedLine(10f, 5f, 0f)
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)
            set1.lineWidth = 1f
            set1.circleRadius = 3f
            set1.setDrawCircleHole(false)
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f
            set1.valueTextSize = 9f
            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)
            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }
            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_red)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets
            // create a data object with the data sets
            val data = LineData(dataSets)
            // set data
            chart.data = data
        }
    }


}