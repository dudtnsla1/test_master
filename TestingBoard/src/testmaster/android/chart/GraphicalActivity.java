package testmaster.android.chart;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.support.v7.app.ActionBarActivity;

public class GraphicalActivity extends ActionBarActivity{ 

	private XYMultipleSeriesDataset dataset = null;
	private GraphicalView graphicalView = null;

	private List<double[]> values = new ArrayList<double[]>();
	private List<double[]> x = new ArrayList<double[]>();
	private List<Double> datasetBuffer = new ArrayList<Double>();
	private final int CHART_COLOR = Color.argb(0xff, 0x0, 0xAA, 0xAA);
			
	static final String[] titles = new String[] { "State1", "State2" , "State3", "State4"};
	private String chartTitle = "";
	private String xLableText = "";
	private String yLableText = "";
	int[] colors = new int[] { Color.rgb(0xff, 0xcc, 0xf1), Color.rgb(0xa8, 0xff, 0xd7), Color.rgb(0xff, 0x7d, 0x88), Color.rgb(0xff, 0xd4, 0xd5)};
	PointStyle[] styles = new PointStyle[] { PointStyle.SQUARE, PointStyle.SQUARE, PointStyle.SQUARE, PointStyle.SQUARE};
	
	XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
	
	public void updateChart(double xMin, double xMax, int yMin, int yMax) {
		
		if (graphicalView != null) {
			renderer.setXAxisMin(xMin);
			renderer.setXAxisMax(xMax);
			renderer.setYAxisMin(yMin);
			renderer.setYAxisMax(yMax);
			graphicalView.repaint();
		}
	}
	
	public void updateChart(double data) {
		
		if (dataset != null) {
			XYSeries series = dataset.getSeriesAt(0);
			
			for (int i = 0; i < datasetBuffer.size(); i++) {
				series.add(series.getMaxX() + 1, datasetBuffer.get(i));
			}
			datasetBuffer.clear();
			series.add(series.getMaxX() + 1, data);
			
		} else {
			datasetBuffer.add(data);
		}
		/*
		if (dataset != null) {
			XYSeries series = dataset.getSeriesAt(0);
			series.add(series.getMaxX() + 1, data);
		}
		*/
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
		setCommonChartGraphicalView(xMin, xMax, yMin, yMax, 10, 4);
		setBarChartGraphicalView();
		graphicalView = ChartFactory.getBarChartView(getApplicationContext(), dataset, renderer, BarChart.Type.STACKED);
		graphicalView.setBackgroundColor(Color.argb(0x00,0x55,0x00,0x22));
		return graphicalView;
	}

	public GraphicalView getLineChartGraphicalView(int xMin, int xMax, int yMin, int yMax, int xLabelDevide, int yLabelDevide) {
		setCommonChartGraphicalView(xMin, xMax, yMin, yMax, xLabelDevide, yLabelDevide);
		setLineChartGraphicalView();
		graphicalView = ChartFactory.getLineChartView(getApplicationContext(), dataset, renderer);
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

		for (int i = 0; i < titles.length; i++) {
			x.add(getXSeries(values.get(i).length, 0));
		}
		
		dataset = buildDataset(titles, x, values);
	}
	
	private void setBarChartGraphicalView() {
		x.clear();
		for (int i = 0; i < titles.length; i++) {
			x.add(getXSeries(values.get(i).length, 0.5));
		}		
		
		dataset = buildDataset(titles, x, values);
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
//		renderer.setXTitle(xTitle);
		renderer.setLegendHeight(60);
//		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
		
	}
}
