package comvacio;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import static java.awt.image.ImageObserver.ERROR;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

public class SerialConnection {

    private OutputStream Output = null;
    private InputStream Input = null;
    SerialPort serialPort;
    //private final String PORT_NAME = "COM6";
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;

    private final int[] bloqueDatos = new int[2];
    private final int[] buffer = new int[29];
    private int contador = 0;
    private int control = 0;

    public void Connection(String PORT_NAME) {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements()) {

            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

            if (PORT_NAME.equals(currPortId.getName())) {
                portId = currPortId;
                break;
            }
        }

        if (portId == null) {

            System.exit(ERROR);
            return;
        }

        try {

            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            Output = serialPort.getOutputStream(); //Se prepara a Output //para enviar datos
            Input = serialPort.getInputStream(); //Se prepara input para //recibir datos
            // serialPort.addEventListener(this.evento); //Se agrega un Event //Listener
            //serialPort.notifyOnDataAvailable(true); //Se indica que se //notifique al usuario cuando sea que halla datos disponibles en //el puerto serie
            //this.wait(25);
            System.out.println("puerto " + PORT_NAME + " abierto");

        } catch (PortInUseException | UnsupportedCommOperationException | IOException e) {

            System.exit(ERROR);
        }

    }

    public void EnviarDatos(String data) {

        try {
            Output.write(data.getBytes());

        } catch (IOException e) {
            System.exit(ERROR);
        }
    }

    public int RecibirDatos() throws IOException {
        int output;
        output = Input.read();
        return output;
    }

    public int[] getBloqueDatos() {
        return bloqueDatos;
    }

    public void setBloqueDatos(int bloqueDatos, boolean bandera, int canal) {//llenar bloque con los datos sin ninguna modificacion, canal debe ser 0 o 1
        if (bandera) { //bandera para separacion de datos 
            this.bloqueDatos[canal] = bloqueDatos;
        }
    }

    public void setBloqueDatos(int Datos) { //llenar bloque con modificacion para interpretar los datos que llegan
        String dat1 = "0";
        String dat2 = "0";
        int ex1 = 0;
        int ex2 = 0;
        if (control > 0) { //bandera para separacion de datos, quita el dato bandera

            if (contador < 29) {
                buffer[contador] = Datos;
                contador++;
            }

            if (Datos == 13) { //bandera para separacion de datos, quita el dato bandera
                control = 0;
                //canal 1
                dat1 = "" + (char) buffer[3];
                ex1 = Integer.parseInt("" + (char) buffer[11] + (char) buffer[12]);
                for (int i = 5; i < 9; i++) {
                    dat1 = dat1 + (char) buffer[i];
                }
                //canal 2
                dat2 = "" + (char) buffer[17];
                ex2 = Integer.parseInt("" + (char) buffer[25] + (char) buffer[26]);
                for (int i = 19; i < 23; i++) {
                    dat2 = dat2 + (char) buffer[i];
                }
                this.limpiarBloqueDatos();
            }
        } else {
            contador = 0;
        }
        if (Datos == 10) {
            control++;
        }
        this.bloqueDatos[0] = (int) (Integer.parseInt(dat1) * Math.pow(10, ex1 - 4));
        this.bloqueDatos[1] = (int) (Integer.parseInt(dat2) * Math.pow(10, ex2 - 4));

    }

    public void desconnection() {
        serialPort.close();
        System.out.println("puerto cerrado");

    }

    public void limpiarBloqueDatos() {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = 0;
        }
        for (int i = 0; i < bloqueDatos.length; i++) {
            bloqueDatos[i] = 0;

        }
        contador = 0;
    }

}
