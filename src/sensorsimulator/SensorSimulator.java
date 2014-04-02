/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsimulator;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;  
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author vgswetha92
 */
public class SensorSimulator extends JFrame implements ActionListener{
    
    JButton startSeminar, startLecture , stopSeminar, stopLecture;
    JLabel answer ;
    JTextArea Sem, Lec;
    //JLabel Sem, Lec;
    JPanel Readings, buttonPanel;
    
    static String file = "./Readings.txt";
    boolean SeminarRunning, LectureRunning;
    
    Timer timer1 = new Timer();
    Timer timer2 = new Timer();
    
    int timer1Count=  2*1000; 
    int timer2Count = 2*1000;
    
     String semSensors[]= {"2778510","2778511","2778512","2778513","2778514", "2778515"};
     int semValues[]= new int[6];
     String lecSensors[]= {"2345610","2345611","2345612","2345613","2345614", "2345615","2345616" };
     int lecValues[]=new int[7];
//     
//     String a[] ={"2778510","2778511","2778512","2778513","2778514", "2778515"};
//     int b[]={0,1,2,3,4,5};
//     
   static DatabaseHandler dbObj;
   static BufferedWriter bw;
    static BufferedReader br;
   protected static void createAndShowGUI() {
       
         
        SensorSimulator s = new SensorSimulator();
        JFrame frame = new JFrame("Sensor Simulator");

        frame.setContentPane(s.createContentPane());

        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                  String line;
                    try {
                        br =new BufferedReader (new FileReader (file));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SensorSimulator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        while((line = br.readLine()) != null)
                                {    StringTokenizer st = new StringTokenizer(line);
                                       String s = st.nextToken();
                                       Timestamp t = Timestamp.valueOf(st.nextToken()+" "+st.nextToken());
                                       int value = Integer.parseInt(st.nextToken());
                                       System.out.println(s+t+value);
                                        dbObj.insertIntoReadings(s, t , value);

                                }
                    } catch (IOException ex) {
                        Logger.getLogger(SensorSimulator.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(SensorSimulator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        bw = new BufferedWriter (new FileWriter(file));
                        System.out.println("Clearing");
                    } catch (IOException ex) {
                        Logger.getLogger(SensorSimulator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        bw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(SensorSimulator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    System.exit(0);
                }
                
               });
        frame.pack();
        frame.setMinimumSize(new Dimension(300, 400));
        frame.setVisible(true); // Otherwise invisible window
}

public JPanel createContentPane() {
    
    JPanel pane = new JPanel();
    pane.setLayout(null);
    
    // Creation of a Panel to contain the buttons 
      buttonPanel = new JPanel();
      buttonPanel.setLayout(null);
      buttonPanel.setLocation(10, 0);
      buttonPanel.setSize(250, 70);
      pane.add(buttonPanel);
      
    
    
    startSeminar = new JButton("Start  SR2");
    startSeminar.setLocation(0, 0);
    startSeminar.setSize(120, 30);
    startSeminar.setHorizontalAlignment(0);
    buttonPanel.add(startSeminar);
    
     startLecture  = new JButton("Start  LT16");
    startLecture.setLocation(130, 0);
    startLecture.setSize(120, 30);
    startLecture.setHorizontalAlignment(0);
    buttonPanel.add(startLecture);
    
    
    startSeminar.addActionListener(this);   // register button listener
    startLecture.addActionListener(this);

    stopSeminar = new JButton("Stop SR2");
    stopSeminar.setLocation(0, 40);
    stopSeminar.setSize(120, 30);
    stopSeminar.setHorizontalAlignment(0);
    buttonPanel.add(stopSeminar);
    
     stopLecture  = new JButton("Stop LT16");
    stopLecture.setLocation(130, 40);
    stopLecture.setSize(120, 30);
    stopLecture.setHorizontalAlignment(0);
    buttonPanel.add(stopLecture);
    
    stopSeminar.setVisible(false);
    stopLecture.setVisible(false);
    
    stopSeminar.addActionListener(this);   // register button listener
    stopLecture.addActionListener(this);
    
    
     Readings = new JPanel();
     Readings.setLayout(null);
     Readings.setLocation(10, 70);
     Readings.setSize(260, 120);
     pane.add(Readings);
     
     Sem = new JTextArea("Seminar");
    Sem.setLocation(0, 0);
    Sem.setSize(120, 120);
    Readings.add(Sem);

    Lec = new JTextArea("Lecture");
    Lec.setLocation(130, 0);
    Lec.setSize(120, 120);
    Readings.add(Lec);
     
    return pane;
}

public void connect() 
{
     
     dbObj = new DatabaseHandler("green_techniques");
        try {
            dbObj.connectToAndQueryDatabase();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SensorSimulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SensorSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public static void main(String[] args) throws SQLException {
        
    SensorSimulator s = new SensorSimulator();
    s.connect();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                createAndShowGUI();
            }
       });
    }

 public void actionPerformed(ActionEvent event) 
  {
    Object source = event.getSource();
    if (source == startSeminar)
    {
       startSeminar.setVisible(false);
       stopSeminar.setVisible(true);
       SeminarRunning = true;
       Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
                @Override public void run() {
                    if (SeminarRunning) {
                       Sem.setText(""+startSimulation(semSensors, semValues));;
                             try {
                            insertIntoFile(semSensors,semValues);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(SensorSimulator.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(SensorSimulator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
       }, 0,2*1000, TimeUnit.MILLISECONDS);
      
    }
    if(source == startLecture)
    {
        startLecture.setVisible(false);
       stopLecture.setVisible(true);
       LectureRunning = true;
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
                @Override public void run() {
                    if (LectureRunning) {
                        Lec.setText(""+startSimulation(lecSensors, lecValues));
                        try {
                            insertIntoFile(lecSensors, lecValues);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(SensorSimulator.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(SensorSimulator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
       },0,2*1000, TimeUnit.MILLISECONDS);
     //   answer.setText("Lecture simulation started");
      
       
    }
    if(source == stopSeminar)
    {
         
        startSeminar.setVisible(true);
       stopSeminar.setVisible(false);
       SeminarRunning = false;
       
       
    }
    if(source == stopLecture)
    {
        startLecture.setVisible(true);
       stopLecture.setVisible(false);
       LectureRunning = false;
    }
  }
 
 public  void writeToDB() throws FileNotFoundException, IOException, SQLException
 {   String line;
     br =new BufferedReader (new FileReader (file));
    
     while((line = br.readLine()) != null)
             {    StringTokenizer st = new StringTokenizer(line);
                    String s = st.nextToken();
                    Timestamp t = Timestamp.valueOf(st.nextToken()+" "+st.nextToken());
                    int value = Integer.parseInt(st.nextToken());
                    System.out.println(s+t+value);
                     dbObj.insertIntoReadings(s, t , value);
                    
             }
      bw = new BufferedWriter (new FileWriter(file));
     bw.close();
     
 }
 public void insertIntoFile(String[] sensors,int[] sensorValues) throws FileNotFoundException, IOException
 {
      bw = new BufferedWriter(new FileWriter(file, true));
     System.out.println("Writing to file");
     
     
     String text="";
     
     
    for(int i=0;i<sensors.length;i++)
     {  
        Date dt = new Date(Calendar.getInstance().getTimeInMillis()); // Your exising sql Date .
        Timestamp timestamp = new Timestamp(dt.getTime());
         text=sensors[i]+"\t"+timestamp+"\t"+sensorValues[i];
         bw.append(text);
         bw.newLine();
     }
    bw.close();
             
 }
public void insertIntoDB(String[] sensors,int[] sensorValues) throws SQLException
{
    
    System.out.println("Inserting");
     
         
      for(int i=0;i<sensorValues.length;i++)
    {    Date dt = new Date(Calendar.getInstance().getTimeInMillis()); // Your exising sql Date .
        Timestamp timestamp = new Timestamp(dt.getTime());
        System.out.println(i);
        dbObj.insertIntoReadings(sensors[i],timestamp, sensorValues[i]);
        
    }
      
}
  public String startSimulation(String[] sensors,int[] sensorValues)
    { 
               
                 String text="";
                int max = 999;
                int min =10;
                Random rand = new Random();
                for(int i=0;i<sensorValues.length;i++)
                {    
                    sensorValues[i]=  rand.nextInt((max - min) + 1) + min;
                    text+=sensors[i]+"\t"+sensorValues[i]+"\n";
                    
                }
                System.out.println(text);
                return text;
  }
   
  
}
