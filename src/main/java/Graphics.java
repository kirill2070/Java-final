import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.Color;
import java.util.Map;

public class Graphics extends JFrame {

    public Graphics(Map<String, Double> generosity) {
        initUI(generosity);
    }

    private void initUI(Map<String, Double> generosity) {

        CategoryDataset dataset = createDataset(generosity);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setVerticalAxisTrace(true);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Bar chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private CategoryDataset createDataset(Map<String, Double> generosity) {
        var dataset = new DefaultCategoryDataset();
        generosity.forEach((key, value) -> dataset.setValue(value, "HealthScore", key));
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        return ChartFactory.createBarChart(
                "Generosity score in countries",
                "Countries",
                "Generosity score",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
    }
}