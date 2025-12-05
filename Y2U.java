package Y2U;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Y2U.Transformer.Translator;


public class Y2U {

	private JFrame frmY2U;
	private JTextField textfieldYPath;
	private JTextField textfieldUPath;
	private JFileChooser YFileChooser;
	private JFileChooser UFileChooser;
	private JTextArea textareaResult;
	
	
	private String YFilePath;
	private String UFilePath;
	
 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Y2U window = new Y2U();
					window.frmY2U.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Y2U() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmY2U = new JFrame();
		frmY2U.setTitle("Y2U");
		frmY2U.setBounds(100, 100, 667, 370);
		frmY2U.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmY2U.getContentPane().setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("UPPAAL File:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 109, 82, 20);
		frmY2U.getContentPane().add(lblNewLabel);
		
		JLabel lblYakinduFile = new JLabel("Yakindu File:");
		lblYakinduFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblYakinduFile.setBounds(10, 79, 82, 20);
		frmY2U.getContentPane().add(lblYakinduFile);		
		
		textfieldYPath = new JTextField();
		textfieldYPath.setBounds(102, 79, 539, 20);
		frmY2U.getContentPane().add(textfieldYPath);
		textfieldYPath.setColumns(10);
		
		textfieldUPath = new JTextField();
		textfieldUPath.setColumns(10);
		textfieldUPath.setBounds(102, 109, 539, 20);
		frmY2U.getContentPane().add(textfieldUPath);
		
		JLabel lblTransformationResults = new JLabel("Transformation Results:");
		lblTransformationResults.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransformationResults.setBounds(10, 139, 149, 20);
		frmY2U.getContentPane().add(lblTransformationResults);
		
		textareaResult = new JTextArea();
		textareaResult.setEditable(false);
		textareaResult.setLineWrap(true);
		textareaResult.setBounds(10, 159, 631, 162);
		frmY2U.getContentPane().add(textareaResult);

		
		//Create Yakind file chooser
		YFileChooser = new JFileChooser();
		FileFilter Yfilter = new FileNameExtensionFilter("Yakindu Model (*.sct)", new String[] {"sct"});
		YFileChooser.setFileFilter(Yfilter);
		//YFileChooser.addChoosableFileFilter(Yfilter);
		
		//Create UPPAAL file chooser
		UFileChooser = new JFileChooser();
		FileFilter Ufilter = new FileNameExtensionFilter("UPPAAL Model (*.xml)", new String[] {"xml"});
		UFileChooser.setFileFilter(Ufilter);
		//UFileChooser.addChoosableFileFilter(Ufilter);
		
		
		JButton btnSaveFile = new JButton("Choose UPPAAL File");
		btnSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actSaveButton();
			}
		});
		btnSaveFile.setBounds(193, 45, 149, 23);
		frmY2U.getContentPane().add(btnSaveFile);
		
		JButton btnOpenFile = new JButton("Choose Yakindu File");
		btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actOpenButton();
			}
		});
		btnOpenFile.setBounds(10, 45, 149, 23);
		frmY2U.getContentPane().add(btnOpenFile);
		
		JButton btnTransform = new JButton("Transform");
		btnTransform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actTransformButton();
			}
		});
		btnTransform.setBounds(376, 45, 116, 23);
		frmY2U.getContentPane().add(btnTransform);
		
		JButton btnRunY = new JButton("Run Yakindu");
		btnRunY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actRunYakinduButton();
			}
		});
		btnRunY.setBounds(10, 11, 149, 23);
		frmY2U.getContentPane().add(btnRunY);
		
		
		
		JButton btnRunU = new JButton("Run UPPAAL");
		btnRunU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actRunUppaalButton();
			}
		});
		btnRunU.setBounds(193, 11, 149, 23);
		frmY2U.getContentPane().add(btnRunU);
	}
	
	
	private void actOpenButton() {
		
		int returnVal = YFileChooser.showOpenDialog(frmY2U);		

        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	        	
        	File file = YFileChooser.getSelectedFile();
        	
        	YFilePath =  file.getPath();
        	
        	textfieldYPath.setText(YFilePath);        	
        } 
	}
	
	private void actSaveButton() {
		int returnVal = UFileChooser.showSaveDialog(frmY2U);		

        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	        	
        	File file = UFileChooser.getSelectedFile();
        	
        	UFilePath = file.getPath();
        	
        	textfieldUPath.setText(UFilePath);
        } 
	}
	
	private void actTransformButton() {
		
		Translator translator = new Translator(YFilePath, UFilePath);
		
		translator.translate();
		
		textareaResult.setText("Transform Successfully!!!");
	}
	
	private void actRunYakinduButton() {
		
		String Ypath = "";
		try {
            FileReader reader = new FileReader("config.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);  
            
            Ypath = bufferedReader.readLine();            		
            
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		try {
			// Run a java app in a separate system process
			Process proc = Runtime.getRuntime().exec(Ypath);
			
			// Then retreive the process output
			InputStream in = proc.getInputStream();
			InputStream err = proc.getErrorStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void actRunUppaalButton() {				
		
		String Upath = "";
		try {
            FileReader reader = new FileReader("config.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);  
            
            bufferedReader.readLine();
            Upath = bufferedReader.readLine();
            
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		try {
			// Run a java app in a separate system process
			Process proc = Runtime.getRuntime().exec("java -jar " + Upath);
			
			// Then retreive the process output
			InputStream in = proc.getInputStream();
			InputStream err = proc.getErrorStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
