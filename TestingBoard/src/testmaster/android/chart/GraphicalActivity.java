package testmaster.android.chart;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class GraphicalActivity extends ActionBarActivity{ 

	private XYMultipleSeriesDataset dataset = null;
	private GraphicalView graphicalView = null;

	private List<double[]> values = new ArrayList<double[]>();
	private List<double[]> x = new ArrayList<double[]>();
	private List<Double> datasetBuffer = new ArrayList<Double>();
	private final int CHART_COLOR = Color.argb(0xff, 0x0, 0xAA, 0xAA);

	private static final String[] titles = new String[] { "State1", "State2" , "State3", "State4"};
	private String chartTitle = "";
	private String xLableText = "";
	private String yLableText = "";
	private int[] colors = new int[] { Color.rgb(0xff, 0x00, 0x00), Color.rgb(0x00, 0x00, 0xff), Color.rgb(0x00, 0x00, 0x00), Color.rgb(0x00, 0x00, 0xff)};
	private PointStyle[] styles = new PointStyle[] { PointStyle.SQUARE, PointStyle.SQUARE, PointStyle.SQUARE, PointStyle.SQUARE};
	private int xScale = 0;

	private XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);

	public void setOrientationHorizontal() {
		renderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
		renderer.setShowLabels(false);
		renderer.setShowGrid(false);
		renderer.setShowGridY(false);
		renderer.setShowGridX(false);
		renderer.setPanEnabled(false);
	}

	public void setLables(double xMin, double xMax, int yMin, int yMax) {

		if (graphicalView != null) {
			renderer.setXAxisMin(xMin);
			renderer.setXAxisMax(xMax);
			renderer.setYAxisMin(yMin);
			renderer.setYAxisMax(yMax);
			xScale = (int) (xMax - xMin);
			graphicalView.repaint();
		}
	}
	
	public void setYAxisMax(float y) 
	{
		renderer.setYAxisMax(y);
	}

	protected void chartInit() {

		double [][] sample = new double[][] {
				{ 0, 10},
				{ 0, 10},
				{ 0, 10},
				{ 0, 10},
		};
		setChartData(sample, "", "", "");  
	}

	private boolean seriesLockFlag = false;

	private synchronized void seriesLock(int x) {
//		Log.d("TestingBoard GraphicalActivity", "lock" + x);
		while (seriesLockFlag);
		seriesLockFlag = true;
	}

	private void seriesRelease(int x) {
		seriesLockFlag = false;
//		Log.d("TestingBoard GraphicalActivity", "release" + x);
	}

	public void resetLineChart(int index) {
		Log.d("TestingBoard GraphicalActivity", "reset chart");
		seriesLock(1);
		if (dataset != null) {
			XYSeries series = dataset.getSeriesAt(index);
			series.clear();
			series.add(0, 0);
			chartInit();
			setLineChartGraphicalView();
			datasetBuffer.clear();	
		}		
		seriesRelease(1);
	}

	public void updateChart(int chartNum, ChartUpdateAdeptor adapter, double []xList) {
		Log.d("TestingBoard GraphicalActivity", "update chart 2");
		double []data = adapter.getIndex();
		Log.d("TestingBoard GraphicalActivity", "lock debugging " + 2);
		seriesLock(2);
		if (dataset != null) {
			XYSeries series = dataset.getSeriesAt(chartNum);
			for (int i = 0; i < data.length; i++) {
				series.add(xList[i], data[i]);		
			}
		} 
		seriesRelease(2);
	}

	public void updateChart(int chartNum, ChartUpdateAdeptor adapter) {
		Log.d("TestingBoard GraphicalActivity", "update chart 1");
		double []data = adapter.getIndex();
		seriesLock(3);
		if (dataset != null) {
			XYSeries series = dataset.getSeriesAt(chartNum);
			for (int i = 0; i < data.length; i++) {
				series.add(series.getMaxX() + 1, data[i]);		
			}
			Log.d("TestingBoard GraphicalActivity", "lock debugging " + 3);
		} 
		seriesRelease(3);
	}

	public void updateCurrent(double data) {
		seriesLock(4);
		if (dataset != null) {

			XYSeries series = dataset.getSeriesAt(0);
			for (int i = 0; i < datasetBuffer.size(); i++) {
				series.add(series.getMaxX() + 1, datasetBuffer.get(i));
			}

			datasetBuffer.clear();
			series.add(series.getMaxX() + 1, data);

			int max = (int) (series.getMaxX() + xScale/2);

			renderer.setXAxisMin(max - xScale);
			renderer.setXAxisMax(max);
		} else {
			datasetBuffer.add(data);
		}

		seriesRelease(4);
	}

	public void setChartData(double [][]data, String chartTitle, String xLableText, String yLableText) {
		values.clear();

		for (int i = 0; i < data.length; i++) {
			values.add(data[i]);
		}

		this.chartTitle = chartTitle;
		this.xLableText = xLableText;
		this.yLableText = yLableText;
	}

	public GraphicalView getBarChartGraphicalView(int xMin, int xMax, int yMin, int yMax) {
		seriesLock(5);
		Log.d("TestingBoard GraphicalActivity", "lock debugging " + 5);
		setCommonChartGraphicalView(xMin, xMax, yMin, yMax, 10, 4);
		setBarChartGraphicalView();
		graphicalView = ChartFactory.getBarChartView(getApplicationContext(), dataset, renderer, BarChart.Type.STACKED);
		graphicalView.setBackgroundColor(Color.argb(0x00,0x55,0x00,0x22));

		seriesRelease(5);
		return graphicalView;
	}

	public GraphicalView getLineChartGraphicalView(int xMin, int xMax, int yMin, int yMax) {
		seriesLock(6);
		setCommonChartGraphicalView(xMin, xMax, yMin, yMax, 10, 4);
		setLineChartGraphicalView();
		graphicalView = ChartFactory.getLineChartView(getApplicationContext(), dataset, renderer);
		seriesRelease(6);
		return graphicalView;
	}

	private double[] getXSeries(int xSize, double start) {

		double temp[] = new double[xSize];

		for (int i = 0; i < xSize; i++) {
			temp[i] = start+i;
		}

		return temp;
	}

	private void setLineChartGraphicalView() {
		x.clear();
		for (int i = 0; i < titles.length; i++) {
			x.add(getXSeries(values.get(i).length, 0));
		}

		dataset = buildDataset(titles, x, values);
	}

	private void setBarChartGraphicalView() {
		seriesLock(8);
		x.clear();
		for (int i = 0; i < titles.length; i++) {
			x.add(getXSeries(values.get(i).length, 0.5));
		}		

		dataset = buildDataset(titles, x, values);
		seriesRelease(8);
	}

	protected void setCommonChartGraphicalView(int xMin, int xMax, int yMin, int yMax, int xLabel, int yLabel) {

		int length = renderer.getSeriesRendererCount();

		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
			.setFillPoints(true);
		}

		renderer.setOrientation(XYMultipleSeriesRenderer.Orientation.VERTICAL);
		renderer.setMarginsColor(Color.argb(0x00,0xff,0xff,0xff));
		//		renderer.setBackgroundColor(Color.argb(0x00, 0xaa, 0xaa, 0xaa));
		renderer.setGridColor(CHART_COLOR);
		renderer.setPanLimits(new double[] {-30000, 30000, 0, 0});
		renderer.setXLabelsColor(CHART_COLOR);
		renderer.setYLabelsColor(0, CHART_COLOR);

		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.LEFT);
		renderer.setYLabelsAlign(Align.LEFT);
		renderer.setZoomButtonsVisible(false);
		renderer.setXLabelsAngle(90);
		renderer.setYLabelsAngle(90);
		renderer.setPanEnabled(true);
		renderer.setZoomEnabled(false);
		renderer.setShowLegend(false);
		renderer.setZoomEnabled(false, false);
		renderer.setPointSize(3);

		setChartSettings(renderer, chartTitle, xLableText,
				yLableText, xMin, xMax, yMin, yMax,
				Color.argb(0xFF, 0xff, 0xff, 0xff),
				CHART_COLOR);

		renderer.setXLabels(xLabel);
		renderer.setYLabels(yLabel);
	}

	private XYMultipleSeriesDataset buildDataset(String[] titles,
			List<double[]> xValues, List<double[]> yValues) {

		if (dataset == null) {
			XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
			addXYSeries(dataset, titles, xValues, yValues, 0);
			return dataset;
		}
		return dataset;
	}

	private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,
			List<double[]> xValues, List<double[]> yValues, int scale) {

		int length = titles.length;

		for (int i = 0; i < length; i++) {
			XYSeries series = new XYSeries(titles[i], scale);

			double[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;

			for (int k = 0; k < seriesLength; k++) { 
				series.add(xV[k], yV[k]);
			}

			dataset.addSeries(series);
		}
	}

	private XYMultipleSeriesRenderer buildRenderer(int[] colors,
			PointStyle[] styles) {

		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);

		return renderer;
	}

	private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
			PointStyle[] styles) {

		renderer.setAxisTitleTextSize(25);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(20);
		renderer.setLegendTextSize(20);
		renderer.setLegendHeight(50);

		renderer.setMargins(new int[] { 10, 10, 10, 10 });

		int length = colors.length;

		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}

	private void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {

		renderer.setChartTitle(title);
		renderer.setLegendHeight(60);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		xScale = (int) (xMax - xMin);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);

	}
}
