package com.example.assignmentapplisted.home.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentapplisted.R
import com.example.assignmentapplisted.databinding.FragmentHomeBinding
import com.example.assignmentapplisted.home.data.OpenInDAO
import com.example.assignmentapplisted.home.util.HomeScreenAdapter
import com.example.assignmentapplisted.home.util.HomeViewModel
import com.example.assignmentapplisted.home.util.MyMarkerView
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

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val viewModel : HomeViewModel by activityViewModels()
    private lateinit var chart: LineChart
    companion object{
        const val RECENT_LINKS=1
        const val TOP_LINKS=2
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setUpObserver()
    }


    @SuppressLint("SuspiciousIndentation")
    private fun setupViews() {
    binding.rvLinksData.layoutManager=LinearLayoutManager(requireContext())
        binding.apply {
            bad.setBackgroundResource(R.drawable.card_border)
            tvRecentLinks.setOnClickListener{
                viewModel.setState(RECENT_LINKS)
            }
            tvTopLinks.setOnClickListener{
                viewModel.setState(TOP_LINKS)
            }
        }
        setupGraph()
    }


    private fun setUpObserver() {
    viewModel.data.observe(viewLifecycleOwner){
        when(it){
            ApiResult.Loading->{

            }
            is ApiResult.Success->{
               val data=it.data as OpenInDAO
                viewModel.setListDataTop()
            }
            is ApiResult.Error->{

                }
        }
    }
        viewModel.listData.observe(viewLifecycleOwner){
            binding.rvLinksData.adapter=HomeScreenAdapter(it)
        }
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                RECENT_LINKS->{
                    setRecentState()
                }
                TOP_LINKS->{
                    setTopState()
                }
            }
        }
    }
    @SuppressLint("ResourceAsColor")
    private fun setRecentState(){
        binding.apply {
            tvRecentLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tvRecentLinks.setBackgroundResource(R.drawable.link_btn_res)
            tvTopLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvTopLinks.setBackgroundResource(R.color.transparent_color)
        }
    }
    @SuppressLint("ResourceAsColor")
    private fun setTopState(){
        binding.apply {
            tvTopLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            tvTopLinks.setBackgroundResource(R.drawable.link_btn_res)
            tvRecentLinks.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            tvRecentLinks.setBackgroundResource(R.color.transparent_color)
        }
    }


    private fun setupGraph() {
        chart=binding.chart
        run {
            // background color
            chart.setBackgroundColor(Color.WHITE)

            // disable description text
            chart.description.isEnabled = false

            // enable touch gestures
            chart.setTouchEnabled(false)

            // set listeners
            val mv = MyMarkerView(requireContext(), R.layout.custom_marker_view)
            mv.chartView = chart
            chart.marker = mv

            // enable scaling and dragging
            chart.isDragEnabled = true
            chart.setScaleEnabled(true)
            chart.setPinchZoom(true)
        }

        val xAxis: XAxis = chart.xAxis

        var yAxis: YAxis
        run {   // // Y-Axis Style // //
            yAxis = chart.axisLeft

            // disable dual axis (only use LEFT axis)
            chart.axisRight.isEnabled = false

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f)

            // axis range
            yAxis.axisMaximum = 200f
            yAxis.axisMinimum = -10f
        }
        val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        xAxis.valueFormatter = IndexAxisValueFormatter(months)
        xAxis.labelCount = months.size // Set the label count to the number of months
        xAxis.setAvoidFirstLastClipping(true) // Avoid clipping of the first and last labels
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        chart.setExtraOffsets(10f, 0f, 10f, 10f) // Add extra offset to accommodate the labels
        chart.xAxis.textSize = 10f

        xAxis.setCenterAxisLabels(true) // Center the labels between the grid lines
        xAxis.granularity = 1f // Display one label per interval
       // xAxis.labelRotationAngle = -45f // Rotate the labels by -45 degrees for better visibility

        setData(8,9f)
        chart.animateX(1500)

        val legend = chart.legend
        legend.form = Legend.LegendForm.LINE

        chart.invalidate()

    }
    @SuppressLint("UseCompatLoadingForDrawables")
    fun setData(count: Int, range: Float) {
        val dataPoints = listOf(50f, 80f, 120f, 90f, 110f, 70f, 100f, 130f)
        val values = ArrayList<Entry>()
        for (i in dataPoints.indices) {
            val value = dataPoints[i]
            val entry = Entry(i.toFloat(), value,resources.getDrawable(R.drawable.fade_red))
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
            // create a dataset and give it a type
            set1 = LineDataSet(values, "")
            set1.setDrawIcons(false)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
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