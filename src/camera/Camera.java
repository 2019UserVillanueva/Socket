
package camera;

//imports
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;


public class Camera extends javax.swing.JFrame {

    // definitions
 private DaemonThread myThread = null;
    int count = 0;
    VideoCapture webSource = null;

    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();
    
    
    class DaemonThread implements Runnable
    {
    protected volatile boolean runnable = false;

    @Override
    public  void run()
    {
        synchronized(this)
        {
            while(runnable)
            {
                if(webSource.grab())
                {
		    	try
                        {
                            webSource.retrieve(frame);
			    Imgcodecs.imencode(".bmp", frame, mem);
			    Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

			    BufferedImage buff = (BufferedImage) im;
			    Graphics g=jPanel1.getGraphics();

			    if (g.drawImage(buff, 0, 0, getWidth(), getHeight() -50 , 0, 0, buff.getWidth(), buff.getHeight(), null))
			    
			    if(runnable == false)
                            {
			    	System.out.println("Pausa");
			    	this.wait();
			    }
			 }
			 catch(Exception ex)
                         {
			    System.out.println("Error");
                         }
                }
            }
        }
     }
   }
    public Camera() {
        initComponents();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(720, 480));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButton1.setLabel("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setLabel("Pause");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(244, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        /// start button 
            webSource =new VideoCapture(0);//crear videocaptura
            myThread = new DaemonThread();//crear hilo
            Thread t = new Thread(myThread);
            t.setDaemon(true);
            myThread.runnable = true;
            t.start();// start thread
            jButton1.setEnabled(false);  // desactivar botón
            jButton2.setEnabled(true);  // activar botón stop
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        /// stop button 
            myThread.runnable = false; //detenet hilo
            jButton2.setEnabled(false);   //activar botón start 
            jButton1.setEnabled(true); //desactivar botón stop 
            
            webSource.release();//detener ejecución de captura	
    }//GEN-LAST:event_jButton2ActionPerformed

   
    public static void main(String args[]) {
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // cargar librería nativa  opencv
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Camera().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
