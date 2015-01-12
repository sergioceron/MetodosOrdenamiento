import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.*;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: sergio
 * Date: 10/04/12
 * Time: 08:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Meter implements ActionListener{
    private JPanel panel    = new JPanel();
    private JPanel controls = new JPanel();
    private JPanel contentPanel = new JPanel();
    private JTextField elements = new JTextField("1000", 12);
    private JButton exec = new JButton("Ejecutar");
    private JFrame frame = new JFrame("Meter");

    public Meter() {
    }

    public void show() {
        contentPanel.setLayout(new BorderLayout());
        controls.setLayout(new FlowLayout());
        frame.setContentPane(contentPanel);
        contentPanel.add(controls, BorderLayout.NORTH);
        contentPanel.add(panel, BorderLayout.CENTER);
        controls.add(new JLabel("Elementos"));
        controls.add(elements);
        controls.add(exec);
        exec.addActionListener(this);
        panel.setLayout(new GridLayout(2, 3, 5, 5));
        frame.setPreferredSize(new Dimension(950, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.pack();
        frame.setVisible(true);
    }

    private void addDialPlot(JPanel container, String title, double value) {

        DefaultValueDataset data = new DefaultValueDataset(value);
        DialPlot plot = new DialPlot();
        plot.setView(0.0, 0.0, 1.0, 1.0);
        plot.setDataset(0, data);

        StandardDialFrame dialFrame = new StandardDialFrame();
        dialFrame.setForegroundPaint(Color.darkGray);
        dialFrame.setStroke(new BasicStroke(3.0f));
        plot.setDialFrame(dialFrame);

        GradientPaint gp = new GradientPaint(new Point(),
                new Color(255, 255, 255), new Point(),
                new Color(170, 170, 220));
        DialBackground db = new DialBackground(gp);
        db.setGradientPaintTransformer(new StandardGradientPaintTransformer(
                GradientPaintTransformType.VERTICAL));
        plot.setBackground(db);

        DialTextAnnotation annotation1 = new DialTextAnnotation("Milisegundos");
        annotation1.setFont(new Font("Dialog", Font.BOLD, 14));
        annotation1.setRadius(0.7);
        plot.addLayer(annotation1);

        DialValueIndicator dvi = new DialValueIndicator(0);
        dvi.setFont(new Font("Dialog", Font.PLAIN, 14));
        dvi.setOutlinePaint(Color.darkGray);
        dvi.setRadius(0.60);
        dvi.setAngle(-90.0);
        plot.addLayer(dvi);

        StandardDialScale scale = new StandardDialScale(0, 100, -120, -300, 5, 10);
        scale.setTickRadius(0.88);
        scale.setTickLabelOffset(0.15);
        scale.setMajorTickPaint(Color.red);
        scale.setTickLabelFont(new Font("Dialog", Font.PLAIN, 10));
        plot.addScale(0, scale);

        DialPointer needle = new DialPointer.Pointer(0);
        plot.addLayer(needle);

        DialCap cap = new DialCap();
        cap.setRadius(0.1);
        plot.setCap(cap);

        JFreeChart chart = new JFreeChart(title,
                JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        plot.setNoDataMessage("No data available");
        ChartPanel chartPanel = new ChartPanel(chart);

        container.add(chartPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.removeAll();
        
        Integer[] elementos = getRandomArray(1000);
        BBubbleSort<Integer> sortMethod1   = new BBubbleSort<Integer>(elementos);
        QuickSort<Integer> sortMethod2     = new QuickSort<Integer>(elementos);
        ShellSort<Integer> sortMethod3     = new ShellSort<Integer>(elementos);
        MergeSort<Integer> sortMethod4     = new MergeSort<Integer>(elementos);
        InsertionSort<Integer> sortMethod5 = new InsertionSort<Integer>(elementos);
        HeapSort<Integer> sortMethod6      = new HeapSort<Integer>(elementos);

        Map<String, Long> methods = executionMap(sortMethod1, sortMethod2, sortMethod3, sortMethod4, sortMethod5, sortMethod6);
        
        for( String title : methods.keySet() ){
            Long time = methods.get(title);
            addDialPlot(panel, title, time/1e6);
        }
        frame.doLayout();
        frame.pack();
    }
    
    private Integer[] getRandomArray(int elementos) {
        List<Integer> array = new ArrayList<Integer>();
        Random rnd = new Random();
        for (int i = 0; i < elementos; i++) {
            array.add(rnd.nextInt(elementos));
        }
        return array.toArray(new Integer[]{});
    }
    
    private Map executionMap(ISort... sorts){
        Map<String, Long> map = new HashMap<String, Long>();
        for( ISort sort : sorts ){
            long a = System.nanoTime();
            sort.sort();
            long b = System.nanoTime();
            map.put(sort.getTitle(), b-a);
        }
        return map;
    }
}
