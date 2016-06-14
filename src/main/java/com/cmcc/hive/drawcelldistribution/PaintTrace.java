package com.cmcc.hive.drawcelldistribution;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.*;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import com.cmcc.hive.drawcelldistribution.baseGralPaint;
import de.erichseifert.gral.graphics.Label;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.axes.LogarithmicRenderer2D;
import de.erichseifert.gral.plots.lines.DiscreteLineRenderer2D;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.plots.points.SizeablePointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.Orientation;

import com.cmcc.hive.drawcelldistribution.CustomerTrace;


public class PaintTrace extends baseGralPaint {
	/** Version id for serialization. */
	private static final long serialVersionUID = -5263057758564264676L;
	private static List<DataTable> dataList = new ArrayList<DataTable>();

	public PaintTrace() {
		// Create data series
		this.setDataTable();
		DataSeries cus1 = new DataSeries(dataList.get(9), 0, 1);
		DataSeries cus2 = new DataSeries(dataList.get(18), 0, 1);

		// Create new xy-plot
		XYPlot plot = new XYPlot(cus1, cus2);

		// Format plot
		plot.setInsets(new Insets2D.Double(60.0, 60.0, 60.0, 60.0));
		plot.setBackground(Color.WHITE);
		plot.getTitle().setText(getDescription());

		// Format plot area
		plot.getPlotArea().setBackground(new RadialGradientPaint(
			new Point2D.Double(0.5, 0.5),
			0.75f,
			new float[] { 0.6f, 0.8f, 1.0f },
			new Color[] { new Color(0, 0, 0, 0), new Color(0, 0, 0, 32), new Color(0, 0, 0, 128) }
		));
		plot.getPlotArea().setBorderStroke(null);

		// Format axes
		AxisRenderer axisRendererX = plot.getAxisRenderer(XYPlot.AXIS_X);
		AxisRenderer axisRendererY = plot.getAxisRenderer(XYPlot.AXIS_Y);
		axisRendererX.setLabel(new Label("Plain axis"));
		axisRendererY.setLabel(new Label("Plain axis"));
		plot.setAxisRenderer(XYPlot.AXIS_X, axisRendererX);
		plot.setAxisRenderer(XYPlot.AXIS_Y, axisRendererY);
		// Custom tick labels
		Map<Double, String> labels = new HashMap<Double, String>();
		labels.put(2.0, "Two");
		labels.put(1.5, "OnePointFive");
		axisRendererX.setCustomTicks(labels);
		// Custom stroke for the x-axis
		BasicStroke stroke = new BasicStroke(2f);
		axisRendererX.setShapeStroke(stroke);
		//Custom Y linear label
		Label linearAxisLabelY = new Label("Latitude");
		linearAxisLabelY.setRotation(90);
		axisRendererY.setLabel(linearAxisLabelY);
		//Custom X linear label
		Label linearAxisLabelX = new Label("Longitude");
		linearAxisLabelX.setRotation(0);
		axisRendererX.setLabel(linearAxisLabelX);
		// Change intersection point of X AND Y axis
		axisRendererY.setIntersection(1000);
		axisRendererY.setTickSpacing(1000);
		axisRendererX.setIntersection(20000);
		axisRendererX.setTickSpacing(20000);

		// Format rendering of data points
		PointRenderer sizeablePointRenderer = new SizeablePointRenderer();
		sizeablePointRenderer.setColor(GraphicsUtils.deriveDarker(COLOR1));
		plot.setPointRenderers(cus1, sizeablePointRenderer);
		plot.setPointRenderers(cus2, sizeablePointRenderer);

		// Format data lines
		DefaultLineRenderer2D defaultLineRenderer = new DefaultLineRenderer2D();
		plot.setLineRenderers(cus1, defaultLineRenderer);
		plot.setLineRenderers(cus2, defaultLineRenderer);
		/*		DiscreteLineRenderer2D discreteRenderer = new DiscreteLineRenderer2D();discreteRenderer.setColor(COLOR1);
		discreteRenderer.setStroke(new BasicStroke(
				3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
				10.0f, new float[] {3f, 6f}, 0.0f));
		plot.setLineRenderers(cus1, discreteRenderer);
		plot.setLineRenderers(cus2, discreteRenderer);
		// Custom gaps for points
		discreteRenderer.setGap(2.0);
		discreteRenderer.setGapRounded(true);
		// Custom ascending
		discreteRenderer.setAscentDirection(Orientation.);
		discreteRenderer.setAscendingPoint(0.5);*/

		// Add plot to Swing component
		add(new InteractivePanel(plot), BorderLayout.CENTER);
	}

	@Override
	public String getTitle() {
		return "Shenzhen CMCC customer Trace";
	}

	@Override
	public String getDescription() {
		return "Shenzhen CMCC customer Trace Demo";
	}
	
	public void setDataTable(){
		 CustomerTrace tmpCT = null;
		 DataTable tmpDt = null;
		 HashMap<String,Location> tmpHm = null;
		 int cusNums = 20;
		 int i = 0;
		 for(Map.Entry<String, CustomerTrace> tmpEn : Trace.getCustomerTraceMap().entrySet()){
			 if(i < cusNums){
				 i++;
				 tmpCT = tmpEn.getValue();
				 tmpDt = new DataTable(2,Double.class);
				 tmpHm = tmpCT.getTimeTrace();
				 for(Map.Entry<String, Location> timeLoc : tmpHm.entrySet()){
					 tmpDt.add(new Double(timeLoc.getValue().getLongitude().doubleValue()*111000),new Double(timeLoc.getValue().getLatitude().doubleValue()*111000));
				 }
				 dataList.add(tmpDt);
			 }
			 else{
				 break;
			 }
		 }
	}
	
}