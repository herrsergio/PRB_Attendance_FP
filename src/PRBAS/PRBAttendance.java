package PRBAS;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class PRBAttendance extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	private JTextField timeField;
	private JTextField dateField;
	private DefaultComboBoxModel EmployeeBoxComboModel;
	private JComboBox EmployeeBox;
	private JComboBox InOutBox;
	private JComboBox FingerBox;
	private JLabel EmployeeLabel;
	private JLabel InOutLabel;
	private JLabel FingerLabel;
	private JButton OkButton;
	private JButton CancelButton;
	private static JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuHelp;
	private JMenuItem menuItem;
	
	private native int verify(int finger, String path, String user);
	
	static {
		System.loadLibrary("verify_java");
	}
	
	public PRBAttendance() {
		
		timeField = new JTextField(7);
		timeField.setEditable(false);
		timeField.setFont(new Font("arial", Font.BOLD, 48));
		
		dateField = new JTextField(6);
		dateField.setEditable(false);
		dateField.setFont(new Font("arial", Font.BOLD, 48));
		
		final JPanel DateHourPanel = new JPanel();
		DateHourPanel.setLayout(new GridLayout(5, 2));
		
		String[] InOutStrings = { "Entrada", "Salida"};
		
		/*
		String[] FingerStrings = { "Indice derecho", "Indice izquierdo", "Pulgar derecho", "Pulgar izquierdo",
				                   "Medio derecho", "Medio izquierdo", "Anular derecho", "Anular izquierdo",
				                   "Meñique derecho", "Meñique izquierdo"};
		*/
		
		String[] FingerStrings = { "Indice derecho", "Indice izquierdo"};
		
		EmployeeBoxComboModel  = new DefaultComboBoxModel();
		EmployeeBoxComboModel  = getFMSEmployees(EmployeeBoxComboModel);
		EmployeeBox = new JComboBox(EmployeeBoxComboModel);
		EmployeeBox.setModel(EmployeeBoxComboModel);
		
		
		InOutBox    = new JComboBox(InOutStrings);
		FingerBox   = new JComboBox(FingerStrings);
		
		EmployeeLabel = new JLabel("Asociado: ");
		InOutLabel    = new JLabel("Entrada/Salida: ");
		FingerLabel   = new JLabel("Dedo a ingresar: ");
		
		OkButton     = new JButton("Ok");
		CancelButton = new JButton("Cancelar");
		
		menuBar     = new JMenuBar();
		menuFile    = new JMenu("Archivo");
		menuHelp    = new JMenu("Ayuda");
		
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		
		menuItem = new JMenuItem("Salir");
		menuItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		menuFile.add(menuItem);
		
		menuItem = new JMenuItem("Acerca de");
		menuItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(DateHourPanel,
					    "PRB Enroll Fingerprint System\nSergio Cuellar Valdes\n2011",
					    "Acerca de",
					    JOptionPane.PLAIN_MESSAGE);
			}
			
		});
		menuHelp.add(menuItem);
		
		
		DateHourPanel.setBorder(BorderFactory.createTitledBorder("Entrada/Salida"));
		
		DateHourPanel.add(timeField, 0);
		DateHourPanel.add(dateField, 1);
				
		DateHourPanel.add(EmployeeLabel, 2);
		DateHourPanel.add(EmployeeBox, 3);
		
		DateHourPanel.add(InOutLabel, 4);
		DateHourPanel.add(InOutBox, 5);
		
		DateHourPanel.add(FingerLabel, 6);
		DateHourPanel.add(FingerBox, 7);
		
		DateHourPanel.add(OkButton, 8);
		DateHourPanel.add(CancelButton, 9);
		
		OkButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				Employee emp = (Employee) EmployeeBox.getSelectedItem();
				String    id = emp.getID();
				String name  = emp.getName();
				String instructions;
				
				int f = 0;
				int result = 0;
				
				String fingerName = (String)FingerBox.getSelectedItem();
				String EntradaSalida = (String)InOutBox.getSelectedItem();
				
				/*
				if(fingerName.equals("Pulgar izquierdo")) f = 0;
		        else if (fingerName.equals("Indice izquierdo")) f = 1;
		        else if (fingerName.equals("Medio izquierdo")) f = 2;
		        else if (fingerName.equals("Anular izquierdo")) f = 3;
		        else if (fingerName.equals("Meñique izquierdo")) f = 4;
		        else if (fingerName.equals("Pulgar derecho")) f = 5;
		        else if (fingerName.equals("Indice derecho")) f = 6;
		        else if (fingerName.equals("Medio derecho")) f = 7;
		        else if (fingerName.equals("Anular derecho")) f = 8;
		        else if (fingerName.equals("Meñique derecho")) f = 9;
		        */
				
				if (fingerName.equals("Indice izquierdo")) f = 1;
				else if (fingerName.equals("Indice derecho")) f = 6;
				
				System.out.println("id= "+id+", name= "+name+", fingerName= "+fingerName+", Entrada/salida= "+EntradaSalida);
				
				if(name.equals("Seleccione")) {
		        	JOptionPane.showMessageDialog(DateHourPanel, "Favor de seleccionar un asociado.", 
		        			"Seleccionar Asociado", JOptionPane.ERROR_MESSAGE);
		        } else {	        
		        	instructions = "A continuación debe colocar su dedo "+fingerName+" en el lector. Presione Aceptar para continuar";
		        
		        	JOptionPane.showMessageDialog(DateHourPanel, instructions);
		        
		        	result = verify(f, "/tmp/Employees", id);
		        	System.out.println("1 verify f= "+f+", id= "+id+" ,result= "+result);
		        	/*
		        	while(result != 1) {
		        		JOptionPane.showMessageDialog(DateHourPanel,
		        			    "La huella no corresponde. Favor de intentarlo de nuevo",
		        			    "Atención",
		        			    JOptionPane.WARNING_MESSAGE);
		        		try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		result = verify(f, "/tmp/Employees", id);
		        		System.out.println("N verify f= "+f+", id= "+id+" ,result= "+result);
		        	}
		        	*/
		        	if(result != 1) {
		        		JOptionPane.showMessageDialog(DateHourPanel,
		        				"La huella no corresponde. Favor de intentarlo de nuevo",
		        				"Atención",
		        				JOptionPane.WARNING_MESSAGE);
		        	} else {
		        
		        		String msg;
		        	
		        		if(EntradaSalida.equals("Entrada")) {
		        			msg = "Bienvenido(a) "+ name;
		        			JOptionPane.showMessageDialog(DateHourPanel, msg);
		        			System.exit(0);
		        			
		        		} else {
		        			msg = "Hasta luego "+name;
		        			JOptionPane.showMessageDialog(DateHourPanel, msg);
		        		}
		        	}
     	
		        }
			}
			
		});
		
		
		this.add(DateHourPanel);
		
		this.setTitle("PRB Attendance System");
		
		this.setSize(650, 310);
		
		javax.swing.Timer t = new javax.swing.Timer(1000, new ClockListener());
		
		t.start();
	}
	
	public  DefaultComboBoxModel getFMSEmployees(DefaultComboBoxModel EmployeeBoxComboModel) {
		
		String[] cmd = { "/bin/sh", "-c", "/usr/bin/ph/emplalt/bin/emplshowdata.s /usr/fms/data/hrcempl.dat" };
		String s;
		String [] data;
		
		/*
		 * Formato de salida del comando: /usr/bin/ph/emplalt/bin/emplshowdata.s /usr/fms/data/hrcempl.dat
		 * CESAR GARAVITO|105502|GAMC870107000|92|87
		 * NOMBRE|No. Empl|RFC|Depto.|Puesto
		 * 
		 * donde Depto puede ser:
		 *    92 Tripulación
		 *    90 Gerente
		 *    91 Asistente Sal
		 *    93 Asistente Hr
		 *    94 Shift Manager
		 *    
		 * Puesto puede ser:
		 *    20 1_Geren_Directas 00 90 0 0 U
		 *    66 1_Subger_Directas 00 94 0 0 H
         *    80 2_Serv_Anf/Coc/Mtro 00 92 0 0 S
         *    81 2_Prod_Anf/Coc/Mtro 00 92 0 0 P
		 *	  82 3_Serv_Empacad/Ay_Coc 00 92 0 0 S
		 *	  83 3_Prod_Empacad/Ay_Coc 00 92 0 0 P
		 *	  85 4_Coordinador_HD 00 92 0 0 S
		 *    86 5_SubCoordinador_HD 00 92 0 0 S
		 *    57 6_Repartidor 00 92 0 0 D
		 *	  87 7_Atye_General 00 92 0 0 S
		 *	  88 8_Lider_Tripul 00 92 0 0 S
		 *	  35 9_Serv_Tripul 00 92 0 0 S
		 *	  36 9_Prod_Tripul 00 92 0 0 P
		 *	  21 Gerente_Indirectas 00 90 0 2 U
		 *	  23 Gerente_Entrenam 00 90 0 1 U
		 *	  37 Trip_Indirectas 00 92 0 2 M
		 *	  38 Trip_Entrenam 00 92 0 1 M
		 *	  67 Shift_Ger_Indirect 00 94 0 2 H
		 *	  68 Shift_Ger_Entrenam 00 94 0 1 H 
         *
		 *    
		 */
		
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			
			BufferedReader stdInput = new BufferedReader(new
	                 InputStreamReader(p.getInputStream()));
			
			EmployeeBoxComboModel.addElement(new Employee("Seleccione", ""));
			
			while ((s = stdInput.readLine()) != null) {
				data = s.split("\\|");
				EmployeeBoxComboModel.addElement(new Employee(data[0], data[1]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return EmployeeBoxComboModel;
	}
	
	public class ClockListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Calendar now = Calendar.getInstance();
			
			Format formatter;
			
			Date date = new Date();
  
			formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			String Sdate = formatter.format(date);
			
			int h     = now.get(Calendar.HOUR_OF_DAY);
            int m     = now.get(Calendar.MINUTE);
            int s     = now.get(Calendar.SECOND);
            int am_pm = now.get(Calendar.AM_PM);
            
            String Sam_pm;
            
            if (am_pm == 0) {
            	Sam_pm = "am";
            } else {
            	Sam_pm = "pm";
            }
            
            if (m < 10 && s < 10) {
            	timeField.setText("" + h + ":0" + m + ":0" + s + " " +Sam_pm);
            } else if (m < 10) {
            	timeField.setText("" + h + ":0" + m + ":" + s + " " +Sam_pm);
            } else if (s < 10) {
            	timeField.setText("" + h + ":" + m + ":0" + s + " " +Sam_pm);
            } else {
            	timeField.setText("" + h + ":" + m + ":" + s + " " +Sam_pm);
            }
            
            dateField.setText(Sdate);
            
		}
	}
	
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame MainFrame = new PRBAttendance();
		MainFrame.setJMenuBar(menuBar);
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainFrame.setVisible(true);
		MainFrame.setLocationRelativeTo(null);
	}
	
	
}