package com.cmcc.hive.drawcelldistribution;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import com.cmcc.hive.drawcelldistribution.baseGralPaint;
import de.erichseifert.gral.graphics.Label;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.axes.LogarithmicRenderer2D;
import de.erichseifert.gral.plots.lines.DiscreteLineRenderer2D;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.plots.points.SizeablePointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.Orientation;


public class gralPaintPosition extends baseGralPaint {
	/** Version id for serialization. */
	private static final long serialVersionUID = -5263057758564264676L;

	/** Instance to generate random data values. */
	private static final Random random = new Random();

	@SuppressWarnings("unchecked")
	
	public gralPaintPosition(){
		
	}
	
	public gralPaintPosition(DataTable dt) {
		// Bind data
		DataTable data = dt; 

		// Create data series
		DataSeries seriesLin = new DataSeries(data, 0,1);

		// Create new xy-plot
		XYPlot plot = new XYPlot(seriesLin);

		// Format plot
		plot.setInsets(new Insets2D.Double(60.0,60.0,60.0,60.0));
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
		// Custom tick labels
		Map<Double, String> labels = new HashMap<Double, String>();
		labels.put(2.0, "Two");
		labels.put(1.5, "OnePointFive");
		axisRendererX.setCustomTicks(labels);
		// Custom stroke for the x-axis
		BasicStroke stroke = new BasicStroke(2f);
		axisRendererX.setShapeStroke(stroke);
		Label linearAxisLabel = new Label("Linear axis");
		linearAxisLabel.setRotation(90);
		axisRendererY.setLabel(linearAxisLabel);
		// Change intersection point of Y axis
		axisRendererY.setIntersection(0.1);
		axisRendererY.setTickSpacing(0.1);
		// Change tick spacing
		axisRendererX.setIntersection(1.0);
		axisRendererX.setTickSpacing(1.0);

		// Format rendering of data points
		PointRenderer sizeablePointRenderer = new SizeablePointRenderer();
		sizeablePointRenderer.setColor(GraphicsUtils.deriveDarker(COLOR1));
		plot.setPointRenderers(seriesLin, sizeablePointRenderer);

		// Add plot to Swing component
		add(new InteractivePanel(plot), BorderLayout.CENTER);
	}

	@Override
	public String getTitle() {
		return "x-y plot";
	}

	@Override
	public String getDescription() {
		return "Shenzhen CMCC Customer Position Plot";
	}
}
