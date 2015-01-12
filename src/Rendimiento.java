import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sergio
 * Date: 25/05/12
 * Time: 07:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Rendimiento extends JFrame implements ActionListener{
    private JPanel controls;
    private JTextField elements, increments;
    private JButton run;

    public Rendimiento(){
        controls = new JPanel();
        elements = new JTextField("20");
        increments = new JTextField("50");
        run = new JButton("Ejecutar");
        run.addActionListener(this);
        
        controls.setLayout(new GridLayout(1, 5));
        controls.add(new JLabel("Iteraciones: "));
        controls.add(elements);
        controls.add(new JLabel("Incrementos: "));
        controls.add(increments);
        controls.add(run);

        this.setLayout(new BorderLayout());
        this.add(controls, BorderLayout.SOUTH);

        this.setTitle("Metodos de Busqueda");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int iteraciones = Integer.parseInt(elements.getText());
        Map<String, XYSeries> xys = new HashMap<String, XYSeries>();
        XYSeriesCollection dataset = new XYSeriesCollection();
        int inc = Integer.parseInt(increments.getText());

        for( int i = 0; i < iteraciones; i++ ){
            Integer[] elementos = getRandomArray(inc + inc * i);
            BBubbleSort<Integer> sortMethod1   = new BBubbleSort<Integer>(elementos);
            QuickSort<Integer> sortMethod2     = new QuickSort<Integer>(elementos);
            ShellSort<Integer> sortMethod3     = new ShellSort<Integer>(elementos);
            MergeSort<Integer> sortMethod4     = new MergeSort<Integer>(elementos);
            InsertionSort<Integer> sortMethod5 = new InsertionSort<Integer>(elementos);
            HeapSort<Integer> sortMethod6      = new HeapSort<Integer>(elementos);
    
            Map<String, Long> execution = executionMap(sortMethod1, sortMethod2, sortMethod3, sortMethod4, sortMethod5, sortMethod6);
    
            XYSeries xy1 = xys.get(sortMethod1.getTitle()) != null ? xys.get(sortMethod1.getTitle()) : new XYSeries(sortMethod1.getTitle());
            XYSeries xy2 = xys.get(sortMethod2.getTitle()) != null ? xys.get(sortMethod2.getTitle()) : new XYSeries(sortMethod2.getTitle());
            XYSeries xy3 = xys.get(sortMethod3.getTitle()) != null ? xys.get(sortMethod3.getTitle()) : new XYSeries(sortMethod3.getTitle());
            XYSeries xy4 = xys.get(sortMethod4.getTitle()) != null ? xys.get(sortMethod4.getTitle()) : new XYSeries(sortMethod4.getTitle());
            XYSeries xy5 = xys.get(sortMethod5.getTitle()) != null ? xys.get(sortMethod5.getTitle()) : new XYSeries(sortMethod5.getTitle());
            XYSeries xy6 = xys.get(sortMethod6.getTitle()) != null ? xys.get(sortMethod6.getTitle()) : new XYSeries(sortMethod6.getTitle());

            xy1.add(inc + inc * i, execution.get(sortMethod1.getTitle()));
            xy2.add(inc + inc * i, execution.get(sortMethod2.getTitle()));
            xy3.add(inc + inc * i, execution.get(sortMethod3.getTitle()));
            xy4.add(inc + inc * i, execution.get(sortMethod4.getTitle()));
            xy5.add(inc + inc * i, execution.get(sortMethod5.getTitle()));
            xy6.add(inc + inc * i, execution.get(sortMethod6.getTitle()));

            xys.put(sortMethod1.getTitle(), xy1);
            xys.put(sortMethod2.getTitle(), xy2);
            xys.put(sortMethod3.getTitle(), xy3);
            xys.put(sortMethod4.getTitle(), xy4);
            xys.put(sortMethod5.getTitle(), xy5);
            xys.put(sortMethod6.getTitle(), xy6);
        }

        for( String k : xys.keySet() ){
            dataset.addSeries(xys.get(k));
        }

        NumberAxis numberaxis = new NumberAxis("Iteraciones");
        numberaxis.setAutoRangeIncludesZero(false);
        NumberAxis numberaxis1 = new NumberAxis("Tiempo(nS)");
        numberaxis1.setAutoRangeIncludesZero(false);
        XYSplineRenderer xysplinerenderer = new XYSplineRenderer();
        XYPlot xyplot = new XYPlot(dataset, numberaxis, numberaxis1, xysplinerenderer);
        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        //xyplot.setAxisOffset(new RectangleInsets(4D, 4D, 4D, 4D));
        JFreeChart jfreechart = new JFreeChart("Metodos de ordenamiento", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
        jfreechart.setBackgroundPaint(Color.white);
        ChartPanel chartpanel = new ChartPanel(jfreechart, false);

        this.add(chartpanel, BorderLayout.CENTER);
        this.doLayout();
        this.pack();
    }

    private Integer[] getRandomArray(int elementos) {
        java.util.List<Integer> array = new ArrayList<Integer>();
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