package Utils;

import static Utils.Config.conf;
import javax.swing.JFileChooser;

/**
 *
 * @author Даниил
 */
public class uiSimulator extends javax.swing.JFrame {

    /**
     * Creates new form uiSimulator
     */
    public uiSimulator() {
        initComponents();
        this.setTitle("Energy simulator");
        setParamsToGui();
        Topology.setEnabled(false);
    }

    private void setParamsToGui() {
        amperage.setValue(conf.getAverageAmperageConsumer());
        voltage.setValue(conf.getAverageVoltageOrigin());
        resistance.setValue(conf.getAverageResistanceOnWire());
        nbOfConsumer.setValue(conf.getNbOfAgentsConsumer());
        nbOfMiddle.setValue(conf.getNbOfAgentsMiddle());
        amperagePersent.setValue(conf.getDeviationAmperagePersent());
        voltagePersent.setValue(conf.getDeviationVoltagePersent());
        resistancePersent.setValue(conf.getDeviationResistanceOnWirePersent());
        startDelay.setValue(conf.getStartDelay() / 1000);
        workDelay.setValue(conf.getWorkDelay() / 1000);
        port.setValue(conf.getCoapPort());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startBtn = new javax.swing.JButton();
        loadBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        resistance = new javax.swing.JSpinner();
        voltage = new javax.swing.JSpinner();
        amperage = new javax.swing.JSpinner();
        amperagePersent = new javax.swing.JSpinner();
        voltagePersent = new javax.swing.JSpinner();
        resistancePersent = new javax.swing.JSpinner();
        nbOfMiddle = new javax.swing.JSpinner();
        nbOfConsumer = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        startDelay = new javax.swing.JSpinner();
        workDelay = new javax.swing.JSpinner();
        Topology = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        port = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        startBtn.setText("Запустить");
        startBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startBtnActionPerformed(evt);
            }
        });

        loadBtn.setText("Загрузить");
        loadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBtnActionPerformed(evt);
            }
        });

        saveBtn.setText("Сохранить");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Количество подстанций");

        jLabel2.setText("Количество конечных потребителей");

        jLabel3.setText("Генерируемое потребляемое значение, А");

        jLabel4.setText("Погрешность, %");

        jLabel5.setText("Генерируемое напряжение на входе, В");

        jLabel6.setText("Погрешность, %");

        jLabel7.setText("Генерируемое сопротивление на проводах, мОм");

        jLabel8.setText("Погрешность, %");

        resistance.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(7.5d), null, null, Double.valueOf(0.1d)));

        voltage.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(220.0d), null, null, Double.valueOf(0.1d)));

        amperage.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(6.0d), null, null, Double.valueOf(0.01d)));

        amperagePersent.setModel(new javax.swing.SpinnerNumberModel(60, 0, 100, 1));

        voltagePersent.setModel(new javax.swing.SpinnerNumberModel(5, 0, 100, 1));

        resistancePersent.setModel(new javax.swing.SpinnerNumberModel(33, 0, 100, 1));

        nbOfMiddle.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(5), null, null, Integer.valueOf(1)));

        nbOfConsumer.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(15), null, null, Integer.valueOf(1)));

        jLabel9.setText("Начальная задержка");

        jLabel10.setText("Задержка новых данных, сек");

        startDelay.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        workDelay.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(2), Integer.valueOf(1), null, Integer.valueOf(1)));

        Topology.setText("Топология");
        Topology.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TopologyActionPerformed(evt);
            }
        });

        jLabel11.setText("Порт");

        port.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(7000), null, null, Integer.valueOf(1)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nbOfConsumer, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                                    .addComponent(nbOfMiddle))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(port, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(resistance, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(amperage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(voltage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(voltagePersent, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(amperagePersent, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(resistancePersent, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(startDelay, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(workDelay, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(Topology)
                        .addGap(18, 18, 18)
                        .addComponent(startBtn)
                        .addGap(18, 18, 18)
                        .addComponent(saveBtn)
                        .addGap(18, 18, 18)
                        .addComponent(loadBtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nbOfMiddle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nbOfConsumer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(amperage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amperagePersent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(voltage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(voltagePersent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(resistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resistancePersent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(startDelay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(workDelay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startBtn)
                    .addComponent(loadBtn)
                    .addComponent(saveBtn)
                    .addComponent(Topology))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startBtnActionPerformed
        startBtn.setVisible(false);
        loadBtn.setVisible(false);
        amperage.setEnabled(false);
        amperagePersent.setEnabled(false);
        voltage.setEnabled(false);
        voltagePersent.setEnabled(false);
        resistance.setEnabled(false);
        resistancePersent.setEnabled(false);
        nbOfConsumer.setEnabled(false);
        nbOfMiddle.setEnabled(false);
        startDelay.setEnabled(false);
        workDelay.setEnabled(false);
        port.setEnabled(false);
        Topology.setEnabled(true);
        
        double amper, volt, resist;
        int amperP, voltP, resistP, nbOfMid, nbOfCons, workDelay, startDelay;
        amper = (double) amperage.getValue();
        volt = (double) voltage.getValue();
        resist = (double) resistance.getValue();
        amperP = (int) amperagePersent.getValue();
        voltP = (int) voltagePersent.getValue();
        resistP = (int) resistancePersent.getValue();
        nbOfMid = (int) nbOfMiddle.getValue();
        nbOfCons = (int) nbOfConsumer.getValue();
        startDelay = ((int) this.startDelay.getValue()) * 1000;
        workDelay = ((int) this.workDelay.getValue()) * 1000;
        int port = (int) this.port.getValue();
        conf.setConfig(nbOfMid, nbOfCons, startDelay, workDelay, amper, amperP, volt, voltP, resist, resistP, 900001, port);
        Utils.LauncherSimulation.startSimulator(true);
    }//GEN-LAST:event_startBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        double amper, volt, resist;
        int amperP, voltP, resistP, nbOfMid, nbOfCons;
        amper = (double) amperage.getValue();
        volt = (double) voltage.getValue();
        resist = (double) resistance.getValue();
        amperP = (int) amperagePersent.getValue();
        voltP = (int) voltagePersent.getValue();
        resistP = (int) resistancePersent.getValue();
        nbOfMid = (int) nbOfMiddle.getValue();
        nbOfCons = (int) nbOfConsumer.getValue();
        int port = (int) this.port.getValue();
        int startDelay = ((int) this.startDelay.getValue()) * 1000;
        int workDelay = ((int) this.workDelay.getValue()) * 1000;
        conf.setConfig(nbOfMid, nbOfCons, startDelay, workDelay, amper, amperP, volt, voltP, resist, resistP, 900001, port);
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Save file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            conf.writeConfigToFile(fileopen.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void loadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBtnActionPerformed
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Open file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            conf.setConfigFromFile(fileopen.getSelectedFile().getAbsolutePath());
        }
        setParamsToGui();
    }//GEN-LAST:event_loadBtnActionPerformed

    private void TopologyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TopologyActionPerformed
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Save file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            LauncherSimulation.getTopology(fileopen.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_TopologyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        if (args.length > 1 && args[0].equals("-nogui")) {
            if (!args[1].isEmpty()) {
                conf.setConfigFromFile(args[1]);
                Utils.LauncherSimulation.startSimulator(false);
                if(args.length>2 && !args[2].isEmpty()){
                    LauncherSimulation.getTopology(args[2]);
                }
            } else {
                Utils.LauncherSimulation.startSimulator(false);
            }

        } else {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(uiSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(uiSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(uiSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(uiSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new uiSimulator().setVisible(true);
                }
            });
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Topology;
    private javax.swing.JSpinner amperage;
    private javax.swing.JSpinner amperagePersent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton loadBtn;
    private javax.swing.JSpinner nbOfConsumer;
    private javax.swing.JSpinner nbOfMiddle;
    private javax.swing.JSpinner port;
    private javax.swing.JSpinner resistance;
    private javax.swing.JSpinner resistancePersent;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton startBtn;
    private javax.swing.JSpinner startDelay;
    private javax.swing.JSpinner voltage;
    private javax.swing.JSpinner voltagePersent;
    private javax.swing.JSpinner workDelay;
    // End of variables declaration//GEN-END:variables
}
