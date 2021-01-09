package comvacio;

import java.util.Calendar;
import java.util.Date;

public class Controlar {

    private final int[][] variacion = new int[2][5];//variable para almacenar el dato y el tiempo
    private final int[][] buckup = new int[2][5];// variable de desplazamiento 
    private int tiempoAnterior;  //variable que guarda el minuto anterior para comparar con el actual

    public boolean controlar(int datos) {
        int[] tiempo;
        int tiempoActual;
        int control;
        tiempo = this.actualizaHora();
        tiempoActual = tiempo[1];//muestreo cada minuto

        if (tiempoAnterior != tiempoActual) {
            if (datos < 100 && datos > 0) {//micras por debajo de 100

                //desplazar valor
                for (int i = 0; i < 5; i++) {
                    buckup[1][i] = variacion[1][i];
                    buckup[0][i] = variacion[0][i];
                }

                //llenar ultima posicion con valor actual
                variacion[0][0] = datos;//llena la ultima posicion del array con el dato actual
                variacion[1][0] = tiempo[1];//llena la ultima posicion del array con la hora actual

                //actualizar array
                for (int i = 1; i < 5; i++) {
                    variacion[1][i] = buckup[1][i - 1];
                    variacion[0][i] = buckup[0][i - 1];
                }

                //control
                control = variacion[0][4] - variacion[0][0];
                control = Math.abs(control);
                //-------condicion de activacion-----
                if (control <= 5) {
                    return true;
                }
                //visualizacion vector datos ultimos 5 mins
                for (int i = 0; i < 5; i++) {
                    System.out.print(variacion[1][i] + "-");
                    System.out.println(variacion[0][i]);
                }
                System.out.println("siguiente");
            }

        }
        tiempoAnterior = tiempoActual;
        return false;
    }

    public int[] actualizaHora() {

        Calendar calendario = Calendar.getInstance();
        int[] tiempo = new int[3];
        Date fechaHoraActual = new Date();
        calendario.setTime(fechaHoraActual);

        tiempo[0] = calendario.get(Calendar.HOUR_OF_DAY); //hora
        tiempo[1] = calendario.get(Calendar.MINUTE); //minutos
        tiempo[2] = calendario.get(Calendar.SECOND); //segundos

        /*----configuracion como String en formato 00:00:00---
    hora = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY)); 
    minutos = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE); 
    segundos = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND); 
    dia = calendario.get(Calendar.DATE) > 9 ? "" + calendario.get(Calendar.DATE) : "0" + calendario.get(Calendar.DATE); 
    mes = calendario.get(Calendar.MONTH) > 9 ? "" + calendario.get(Calendar.MONTH) : "0" + calendario.get(Calendar.MONTH); 
    año = calendario.get(Calendar.YEAR) > 9 ? "" + calendario.get(Calendar.YEAR) : "0" + calendario.get(Calendar.YEAR); 
    System.out.println(hora + ":" + minutos + ":" + segundos);*/
        // System.out.println(tiempo[0]+ ":" + tiempo[1] + ":" + tiempo[2]);
        return tiempo;
    }

    public void limpiarVariables() {
        for (int i = 0; i < 5; i++) {
            variacion[1][i] = 0;
            buckup[1][i] = 0;
            variacion[0][i] = 0;
            buckup[0][i] = 0;
        }

    }

}
