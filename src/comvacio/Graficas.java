package comvacio;

import java.awt.Paint;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graficas {

    JFreeChart grafica;
    XYSeriesCollection datos = new XYSeriesCollection();
    String titulo;
    String tx;
    String ty;
    private int tiempo;
    private final ArrayList<Integer> x1 = new ArrayList<>();
    private final ArrayList<Integer> y1 = new ArrayList<>();
    final static int LINEAL = 1;
    final static int POLAR = 2;
    final static int DISPERSION = 3;
    final static int AREA = 4;
    final static int LOGARITMICA = 5;
    final static int SERIETIEMPO = 6;
    final static int PASO = 7;
    final static int PASOAREA = 8;

    public Graficas(int tipo, String titulo, String tx, String ty) {
        this.titulo = titulo;
        this.tx = tx;
        this.ty = ty;
        tipoGrafica(tipo);
    }

    public void tipoGrafica(int tipo) {
        switch (tipo) {
            case LINEAL:
                grafica = ChartFactory.createXYLineChart(titulo, tx, ty, datos, PlotOrientation.VERTICAL, true, true, true);
                break;
            case POLAR:
                grafica = ChartFactory.createPolarChart(titulo, datos, true, true, true);
                break;
            case DISPERSION:
                grafica = ChartFactory.createScatterPlot(titulo, tx, ty, datos, PlotOrientation.VERTICAL, true, true, true);
                break;
            case AREA:
                grafica = ChartFactory.createXYAreaChart(titulo, tx, ty, datos, PlotOrientation.VERTICAL, true, true, true);
                break;
            case LOGARITMICA:
                grafica = ChartFactory.createXYLineChart(titulo, tx, ty, datos, PlotOrientation.VERTICAL, true, true, true);
                //CAMBIAR RANGO DEL EJE Y A LOGARITMICO
                XYPlot ejes = grafica.getXYPlot();
                NumberAxis rango = new LogarithmicAxis(ty);
                ejes.setRangeAxis(rango);
                break;
            case SERIETIEMPO:
                grafica = ChartFactory.createTimeSeriesChart(titulo, tx, ty, datos, true, true, true);
                break;
            case PASO:
                grafica = ChartFactory.createXYStepChart(titulo, tx, ty, datos, PlotOrientation.VERTICAL, true, true, true);
                break;
            case PASOAREA:
                grafica = ChartFactory.createXYStepAreaChart(titulo, tx, ty, datos, PlotOrientation.VERTICAL, true, true, true);
                break;
            default:
                throw new AssertionError();
        }
    }

    public void agregarSerie(String id, ArrayList x, ArrayList y, Paint paint) {
        datos.removeAllSeries();
        XYSeries s = new XYSeries(id);
        // Agregar Color
        XYPlot plott = grafica.getXYPlot();
        plott.getRenderer().setSeriesPaint(0, paint);
        for (int i = 0; i < x.size(); i++) {
            s.add((Number) x.get(i), (Number) y.get(i));

        }
        datos.addSeries(s);
    }

    public JPanel obtienepanel() {
        return new ChartPanel(grafica);

    }

    public ArrayList<Integer> getX1() {
        return x1;
    }

    public ArrayList<Integer> getY1() {
        return y1;
    }

    public void setX1(int x1) {
        this.x1.add(x1);
    }

    public void setY1(int y1) {
        this.y1.add(y1);
    }
    
    public void actualizar(int x, int y, String titulo,Paint paint){
       this.setX1(x);
       this.setY1(y);
       this.agregarSerie(titulo, this.getX1(), this.getY1(), paint);
    }
    
    public void limpiarDatos(){
        this.x1.removeAll(x1);
        this.y1.removeAll(y1);
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(boolean reset) {
        if(!reset){
            this.tiempo++;
        }else{
            this.tiempo=0;
        }
    }
}
