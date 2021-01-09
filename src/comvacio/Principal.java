package comvacio;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author angello morales
 */
public class Principal extends javax.swing.JFrame {

    SerialConnection comDisplay1 = new SerialConnection(); //objeto comunicacion serial
    ExportData exportarDatos = new ExportData(); //objeto para exportar datos a archivo TXT
    Controlar controlarCH1 = new Controlar();//objeto para hacer control de baliza por ch1
    Controlar controlarCH2 = new Controlar();//objeto para hacer control de baliza por ch2
    boolean conectado = false;  //variable que verifica si esta conectado al display
    int canalAlterno = 0;//variable que verifica si los 2 canales estan activos al mismo tiempo
    boolean canal1 = false; // habilitar canal 1
    boolean canal2 = false; // habilitar canal 2

    Graficas graficas1 = new Graficas(Graficas.LINEAL, "Vacio", "Minutos", "Micras");//objeto para manejar la grafica
    Graficas graficas2 = new Graficas(Graficas.LINEAL, "Vacio", "Minutos", "Micras");//objeto para manejar la grafica


    SerialPortEventListener evento = new SerialPortEventListener() {//listener del puerto serie
        @Override
        public synchronized void serialEvent(SerialPortEvent spe) {
            if (spe.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                try {
                    Integer data;
                    boolean controlCH1, controlCH2;

                    data = comDisplay1.RecibirDatos();//Se invoca la función RecibirDatos()
                    comDisplay1.setBloqueDatos(data);//llena arreglo de datos
                    
                    //<editor-fold defaultstate="collapsed" desc="controlar, graficar y generar archivo cuando finaliza el control">
                    if (comDisplay1.getBloqueDatos()[0] > 0 || comDisplay1.getBloqueDatos()[1] > 0) {//muestra cuando llega un valor diferente de 0
                        controlCH1 = controlarCH1.controlar(comDisplay1.getBloqueDatos()[0]);
                        controlCH2 = controlarCH2.controlar(comDisplay1.getBloqueDatos()[1]);

                        // mostrar todo lo recibido en consola
                        if (canal1) {
                            consolaCH1.append(controlarCH1.actualizaHora()[0] + ":" + controlarCH1.actualizaHora()[1] + ":" + controlarCH1.actualizaHora()[2] + "-" + comDisplay1.getBloqueDatos()[0] + "\n");// mostrar todo lo recibido en consola modificado
                            ScrollConsolaCH1.getVerticalScrollBar().setValue(ScrollConsolaCH1.getVerticalScrollBar().getMaximum());
                        }
                        if (canal2) {
                            consolaCH2.append(controlarCH1.actualizaHora()[0] + ":" + controlarCH1.actualizaHora()[1] + ":" + controlarCH1.actualizaHora()[2] + "-" + comDisplay1.getBloqueDatos()[1] + "\n");
                            ScrollConsolaCH2.getVerticalScrollBar().setValue(ScrollConsolaCH2.getVerticalScrollBar().getMaximum());
                        }

                        //realizar control
                        if (controlCH1) {
                            indicadorFinCH1.setVisible(true);
                            exportarDatos.escribir(consolaCH1.getText(), numeroOTCH1.getText());
                            finalizarComunicacion();
                            //activar alarma
                            if (hablilitarAlarma.getSelectedObjects() == null) {
                                activarAlarma();// activar alarma en arduino
                            }
                        }

                        if (controlCH2) {
                            indicadorFinCH2.setVisible(true);
                            exportarDatos.escribir(consolaCH2.getText(), numeroOTCH2.getText());
                            finalizarComunicacion();
                            //activar alarma
                            if (hablilitarAlarma.getSelectedObjects() == null) {
                                activarAlarma();// activar alarma en arduino
                            }
                        }

                        //Graficar lo recibido
                        actualizarGrafica(comDisplay1.getBloqueDatos()[0]);
                        graficas1.setTiempo(false);
                    }//</editor-fold>

                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }
        }
    };

    public Principal() throws TooManyListenersException {
        initComponents();
        indicadorFinCH1.setVisible(false);
        indicadorFinCH1.setIcon(new javax.swing.ImageIcon("Images/alarma.gif"));
        indicadorFinCH2.setVisible(false);
        indicadorFinCH2.setIcon(new javax.swing.ImageIcon("Images/alarma.gif"));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ScrollConsolaCH1 = new javax.swing.JScrollPane();
        consolaCH1 = new javax.swing.JTextArea();
        IniciarCH1 = new javax.swing.JButton();
        finalizarCH1 = new javax.swing.JButton();
        indicadorFinCH1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        numeroOTCH1 = new javax.swing.JTextField();
        graficaVacioCH1 = new javax.swing.JPanel();
        puertoBaliza = new javax.swing.JComboBox<>();
        puertoDisplay = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        hablilitarAlarma = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        graficaVacioCH2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        numeroOTCH2 = new javax.swing.JTextField();
        IniciarCH2 = new javax.swing.JButton();
        finalizarCH2 = new javax.swing.JButton();
        indicadorFinCH2 = new javax.swing.JLabel();
        ScrollConsolaCH2 = new javax.swing.JScrollPane();
        consolaCH2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        consolaCH1.setColumns(20);
        consolaCH1.setRows(5);
        ScrollConsolaCH1.setViewportView(consolaCH1);

        IniciarCH1.setText("Iniciar");
        IniciarCH1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IniciarCH1ActionPerformed(evt);
            }
        });

        finalizarCH1.setText("Finalizar");
        finalizarCH1.setEnabled(false);
        finalizarCH1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalizarCH1ActionPerformed(evt);
            }
        });

        indicadorFinCH1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        indicadorFinCH1.setForeground(new java.awt.Color(255, 0, 0));
        indicadorFinCH1.setText(" ");
        indicadorFinCH1.setMaximumSize(null);
        indicadorFinCH1.setMinimumSize(null);
        indicadorFinCH1.setPreferredSize(null);

        jLabel2.setText("Serial CH1:");

        graficaVacioCH1.setBackground(new java.awt.Color(153, 153, 153));
        graficaVacioCH1.setLayout(new java.awt.BorderLayout());

        puertoBaliza.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "COM10" }));

        puertoDisplay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "COM10" }));
        puertoDisplay.setSelectedIndex(5);

        jLabel1.setText("COM Alarma:");

        jLabel3.setText("COM display:");

        hablilitarAlarma.setSelected(true);
        hablilitarAlarma.setText("Deshabilitar Alarma");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("BOMBA 5");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("BOMBA 6");

        graficaVacioCH2.setBackground(new java.awt.Color(153, 153, 153));
        graficaVacioCH2.setLayout(new java.awt.BorderLayout());

        jLabel6.setText("Serial CH2:");

        IniciarCH2.setText("Iniciar");
        IniciarCH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IniciarCH2ActionPerformed(evt);
            }
        });

        finalizarCH2.setText("Finalizar");
        finalizarCH2.setEnabled(false);
        finalizarCH2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalizarCH2ActionPerformed(evt);
            }
        });

        indicadorFinCH2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        indicadorFinCH2.setForeground(new java.awt.Color(255, 0, 0));
        indicadorFinCH2.setText(" ");
        indicadorFinCH2.setMaximumSize(null);
        indicadorFinCH2.setMinimumSize(null);
        indicadorFinCH2.setPreferredSize(null);

        consolaCH2.setColumns(20);
        consolaCH2.setRows(5);
        ScrollConsolaCH2.setViewportView(consolaCH2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(numeroOTCH1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(IniciarCH1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(finalizarCH1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(puertoDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 77, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(puertoBaliza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(hablilitarAlarma)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(indicadorFinCH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(graficaVacioCH1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ScrollConsolaCH1))))
                        .addGap(47, 47, 47))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(indicadorFinCH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(graficaVacioCH2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ScrollConsolaCH2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numeroOTCH2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(IniciarCH2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(finalizarCH2)))
                        .addGap(0, 130, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(puertoBaliza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hablilitarAlarma)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(indicadorFinCH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(indicadorFinCH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(puertoDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(graficaVacioCH1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(graficaVacioCH2, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                .addGap(18, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ScrollConsolaCH1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(ScrollConsolaCH2, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IniciarCH1)
                    .addComponent(finalizarCH1)
                    .addComponent(numeroOTCH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(numeroOTCH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IniciarCH2)
                    .addComponent(finalizarCH2))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc="botones">
    private void IniciarCH1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IniciarCH1ActionPerformed
        consolaCH1.append("HORA" + "-" + "BOMBA 5" + "\n");// TITULO DE LAS COLUMNAS
        ScrollConsolaCH1.getVerticalScrollBar().setValue(ScrollConsolaCH1.getVerticalScrollBar().getMaximum());
        canal1 = true;
        if (this.numeroOTCH1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por Favor digite el numero de la OT en el campo y vuelva a iniciar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            controlarCH1.limpiarVariables();
            //limpiar grafica
            graficas1.limpiarDatos();
            canalAlterno++;
            if (!conectado) {
                comDisplay1.limpiarBloqueDatos();
                graficas1.setTiempo(true);//limpiar eje x grafica
                iniciarComunicacion();
            }
            this.IniciarCH1.setEnabled(false);
            this.finalizarCH1.setEnabled(true);
        }
    }//GEN-LAST:event_IniciarCH1ActionPerformed

    private void finalizarCH1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalizarCH1ActionPerformed
        finalizarComunicacion();
        canal1 = false;
        exportarDatos.escribir(consolaCH1.getText(), numeroOTCH1.getText());
        this.IniciarCH1.setEnabled(true);
        this.finalizarCH1.setEnabled(false);

    }//GEN-LAST:event_finalizarCH1ActionPerformed

    private void IniciarCH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IniciarCH2ActionPerformed
        consolaCH2.append("HORA" + "-" + "BOMBA 6" + "\n");// TITULO DE LAS COLUMNAS
        ScrollConsolaCH2.getVerticalScrollBar().setValue(ScrollConsolaCH2.getVerticalScrollBar().getMaximum());
        canal2 = true;
        if (this.numeroOTCH2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por Favor digite el numero de la OT en el campo y vuelva a iniciar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            controlarCH2.limpiarVariables();
            //limpiar grafica
            graficas2.limpiarDatos();
            canalAlterno++;
            if (!conectado) {
                comDisplay1.limpiarBloqueDatos();
                graficas2.setTiempo(true);//limpiar eje x grafica
                iniciarComunicacion();
            }
            this.IniciarCH2.setEnabled(false);
            this.finalizarCH2.setEnabled(true);
        }
    }//GEN-LAST:event_IniciarCH2ActionPerformed

    private void finalizarCH2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalizarCH2ActionPerformed
        finalizarComunicacion();
        canal2 = false;
        exportarDatos.escribir(consolaCH2.getText(), numeroOTCH2.getText());
        this.IniciarCH2.setEnabled(true);
        this.finalizarCH2.setEnabled(false);
    }//GEN-LAST:event_finalizarCH2ActionPerformed
//</editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Principal().setVisible(true);

                } catch (TooManyListenersException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton IniciarCH1;
    private javax.swing.JButton IniciarCH2;
    private javax.swing.JScrollPane ScrollConsolaCH1;
    private javax.swing.JScrollPane ScrollConsolaCH2;
    private javax.swing.JTextArea consolaCH1;
    private javax.swing.JTextArea consolaCH2;
    private javax.swing.JButton finalizarCH1;
    private javax.swing.JButton finalizarCH2;
    private javax.swing.JPanel graficaVacioCH1;
    private javax.swing.JPanel graficaVacioCH2;
    private javax.swing.JCheckBox hablilitarAlarma;
    private javax.swing.JLabel indicadorFinCH1;
    private javax.swing.JLabel indicadorFinCH2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField numeroOTCH1;
    private javax.swing.JTextField numeroOTCH2;
    private javax.swing.JComboBox<String> puertoBaliza;
    private javax.swing.JComboBox<String> puertoDisplay;
    // End of variables declaration//GEN-END:variables

//<editor-fold defaultstate="collapsed" desc="funciones propias">
    public void activarAlarma() {
        comDisplay1.Connection((String) this.puertoBaliza.getSelectedItem());
        try {
            Thread.sleep(2000);
            System.out.println("enviado");
            comDisplay1.EnviarDatos("5\n");
        } catch (InterruptedException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        comDisplay1.desconnection();
    }

    public void actualizarGrafica(int datos) {

        graficas1.actualizar(graficas1.getTiempo(), datos, "Bomba 5", Color.RED);
        graficaVacioCH1.removeAll();
        graficaVacioCH1.add(graficas1.obtienepanel(), BorderLayout.CENTER);
        graficaVacioCH1.validate();
    }

    public void iniciarComunicacion() { //iniciar comunicacion serial con display
        comDisplay1.Connection((String) this.puertoDisplay.getSelectedItem());
        try {
            comDisplay1.serialPort.addEventListener(this.evento);//Se agrega un Event //Listener
        } catch (TooManyListenersException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        comDisplay1.serialPort.notifyOnDataAvailable(true);//Se indica que se //notifique al usuario cuando sea que halla datos disponibles en //el puerto serie       
        conectado = true;
    }

    public void finalizarComunicacion() {
        //verificar si existe otro canal tomando datos antes de cerrar el puerto
        if (canalAlterno <= 1) {
            comDisplay1.desconnection();
            conectado = false;
            canalAlterno--;
        } else {
            canalAlterno--;
        }
    }

//</editor-fold>
}
