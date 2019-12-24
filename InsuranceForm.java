/* Chris Bolton
 * CIS 452 Project
 * 12/13/2019
 * 
 * This program is designed to interact with an insurance database. It can add accidents and involvments as well as
 * display contents of the database through several search criteria.
 * 
 * The InsuranceForm class handles the GUI aspect of the program.
 * Credit for crashed car icon: https://icons8.com
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import com.toedter.calendar.JDateChooser;

public class InsuranceForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtCity;
	private JTextField txtState;
	private JTextField txtVin;
	private JTextField txtDriverSSN;
	private JTextField txtDamages;
	String[][] involvements = new String[3][100];
	int involvementsCount = 0;
	private JTextField txtAid;
	private JTextField txtMinAvgDamage;
	private JTextField txtMaxAvgDamage;
	private JTextField txtMinDamagesSum;
	private JTextField txtMaxDamagesSum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsuranceForm frame = new InsuranceForm();
					frame.setVisible(true);
					frame.setIconImage(ImageIO.read(getClass().getResourceAsStream("/icons8-crashed-car-50.png")));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InsuranceForm() {
		setTitle("Insurance Database Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 656);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JDateChooser datAccidentDate = new JDateChooser();
		datAccidentDate.setDateFormatString("yyyy-MM-dd");
		GridBagConstraints gbc_datAccidentDate = new GridBagConstraints();
		gbc_datAccidentDate.anchor = GridBagConstraints.NORTH;
		gbc_datAccidentDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_datAccidentDate.insets = new Insets(0, 0, 5, 5);
		gbc_datAccidentDate.gridx = 1;
		gbc_datAccidentDate.gridy = 4;
		contentPane.add(datAccidentDate, gbc_datAccidentDate);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 11;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		JTextArea rtbOutput = new JTextArea();
		scrollPane.setViewportView(rtbOutput);
		
		
		
		
		JButton btnAddReport = new JButton("Add Report");
		btnAddReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Make sure city and state have been entered
				String strState = txtState.getText();
				String strCity = txtCity.getText();

				if(strState.length() > 0 && strCity.length() > 0){
					//City and state have been entered
					if(involvementsCount > 0){
						//At least one  involvement has been entered
						if(datAccidentDate.getDate() != null){
							//Get ugly date
							String strAccidentDate = datAccidentDate.getDate().toString();
							//format to more usable date
							strAccidentDate = formatDate(strAccidentDate);
							Main.addAccident(strState, strCity, strAccidentDate, involvements, involvementsCount);
							involvementsCount = 0;
							rtbOutput.setText("Records Added.");
							clearAccidentEntryFields();
						}else{
							rtbOutput.setText("Please select a date.");
						}
					}else{
						rtbOutput.setText("Please enter at least one involvement.");
					}
				}else{
					rtbOutput.setText("Please enter both the city and state.");
				}
			}
		});
		GridBagConstraints gbc_btnAddReport = new GridBagConstraints();
		gbc_btnAddReport.anchor = GridBagConstraints.WEST;
		gbc_btnAddReport.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddReport.gridx = 1;
		gbc_btnAddReport.gridy = 10;
		contentPane.add(btnAddReport, gbc_btnAddReport);
		
		
		
		JLabel lblSumDamagesRange = new JLabel("Sum Damages Range:");
		GridBagConstraints gbc_lblSumDamagesRange = new GridBagConstraints();
		gbc_lblSumDamagesRange.anchor = GridBagConstraints.EAST;
		gbc_lblSumDamagesRange.insets = new Insets(0, 0, 5, 5);
		gbc_lblSumDamagesRange.gridx = 3;
		gbc_lblSumDamagesRange.gridy = 8;
		contentPane.add(lblSumDamagesRange, gbc_lblSumDamagesRange);
		
		txtMinDamagesSum = new JTextField();
		txtMinDamagesSum.setToolTipText("Min Damages");
		GridBagConstraints gbc_txtMinDamagesSum = new GridBagConstraints();
		gbc_txtMinDamagesSum.anchor = GridBagConstraints.NORTH;
		gbc_txtMinDamagesSum.insets = new Insets(0, 0, 5, 0);
		gbc_txtMinDamagesSum.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMinDamagesSum.gridx = 4;
		gbc_txtMinDamagesSum.gridy = 8;
		contentPane.add(txtMinDamagesSum, gbc_txtMinDamagesSum);
		txtMinDamagesSum.setColumns(10);
		
		txtMaxDamagesSum = new JTextField();
		txtMaxDamagesSum.setToolTipText("Max Damages");
		GridBagConstraints gbc_txtMaxDamagesSum = new GridBagConstraints();
		gbc_txtMaxDamagesSum.insets = new Insets(0, 0, 5, 0);
		gbc_txtMaxDamagesSum.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMaxDamagesSum.gridx = 4;
		gbc_txtMaxDamagesSum.gridy = 9;
		contentPane.add(txtMaxDamagesSum, gbc_txtMaxDamagesSum);
		txtMaxDamagesSum.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isNumeric(txtMinDamagesSum.getText()) && isNumeric(txtMaxDamagesSum.getText())){
					String result = "";
					result = Main.totalDamagesRangeSearch(Double.parseDouble(txtMinDamagesSum.getText()), Double.parseDouble(txtMaxDamagesSum.getText()));
					rtbOutput.setText(result);
				}else{
					rtbOutput.setText("Please enter valid average damages.");
				}
			}
		});
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearch.gridx = 4;
		gbc_btnSearch.gridy = 10;
		contentPane.add(btnSearch, gbc_btnSearch);
		
		
		
		JLabel lblAvgDamagesRange = new JLabel("Avg Damages Range:");
		GridBagConstraints gbc_lblAvgDamagesRange = new GridBagConstraints();
		gbc_lblAvgDamagesRange.anchor = GridBagConstraints.EAST;
		gbc_lblAvgDamagesRange.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvgDamagesRange.gridx = 3;
		gbc_lblAvgDamagesRange.gridy = 5;
		contentPane.add(lblAvgDamagesRange, gbc_lblAvgDamagesRange);
		
		txtMinAvgDamage = new JTextField();
		txtMinAvgDamage.setToolTipText("Min Avg Damage");
		GridBagConstraints gbc_txtMinAvgDamage = new GridBagConstraints();
		gbc_txtMinAvgDamage.insets = new Insets(0, 0, 5, 0);
		gbc_txtMinAvgDamage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMinAvgDamage.gridx = 4;
		gbc_txtMinAvgDamage.gridy = 5;
		contentPane.add(txtMinAvgDamage, gbc_txtMinAvgDamage);
		txtMinAvgDamage.setColumns(10);
		
		txtMaxAvgDamage = new JTextField();
		txtMaxAvgDamage.setToolTipText("Max Avg Damage");
		GridBagConstraints gbc_txtMaxAvgDamage = new GridBagConstraints();
		gbc_txtMaxAvgDamage.insets = new Insets(0, 0, 5, 0);
		gbc_txtMaxAvgDamage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMaxAvgDamage.gridx = 4;
		gbc_txtMaxAvgDamage.gridy = 6;
		contentPane.add(txtMaxAvgDamage, gbc_txtMaxAvgDamage);
		txtMaxAvgDamage.setColumns(10);
		
		JButton btnAvgDamageSearch = new JButton("Search");
		btnAvgDamageSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result = "";
				if((isNumeric(txtMaxAvgDamage.getText())) && (isNumeric(txtMinAvgDamage.getText()))){
					result = Main.averageDamagesRangeSearch(Double.parseDouble(txtMaxAvgDamage.getText()), Double.parseDouble(txtMinAvgDamage.getText()));
					rtbOutput.setText(result);
				}else{
					rtbOutput.setText("Please enter valid average damages.");
				}
			}
		});
		GridBagConstraints gbc_btnAvgDamageSearch = new GridBagConstraints();
		gbc_btnAvgDamageSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAvgDamageSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btnAvgDamageSearch.gridx = 4;
		gbc_btnAvgDamageSearch.gridy = 7;
		contentPane.add(btnAvgDamageSearch, gbc_btnAvgDamageSearch);
		
		
		
		JLabel lblSearch = new JLabel("Search");
		GridBagConstraints gbc_lblSearch = new GridBagConstraints();
		gbc_lblSearch.anchor = GridBagConstraints.WEST;
		gbc_lblSearch.insets = new Insets(0, 0, 5, 0);
		gbc_lblSearch.gridx = 4;
		gbc_lblSearch.gridy = 3;
		contentPane.add(lblSearch, gbc_lblSearch);
		
		JLabel lblDate = new JLabel("Date:");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 4;
		contentPane.add(lblDate, gbc_lblDate);
		
		
		
		JLabel lblDateRange = new JLabel("Date Range:");
		GridBagConstraints gbc_lblDateRange = new GridBagConstraints();
		gbc_lblDateRange.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblDateRange.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateRange.gridx = 3;
		gbc_lblDateRange.gridy = 4;
		contentPane.add(lblDateRange, gbc_lblDateRange);
		
		JDateChooser datStartDate = new JDateChooser();
		datStartDate.setDateFormatString("yyyy-MM-dd");
		GridBagConstraints gbc_datStartDate = new GridBagConstraints();
		gbc_datStartDate.anchor = GridBagConstraints.NORTH;
		gbc_datStartDate.insets = new Insets(0, 0, 5, 0);
		gbc_datStartDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_datStartDate.gridx = 4;
		gbc_datStartDate.gridy = 4;
		contentPane.add(datStartDate, gbc_datStartDate);
		
		JDateChooser datEndDate = new JDateChooser();
		datEndDate.setDateFormatString("yyyy-MM-dd");
		datStartDate.add(datEndDate, BorderLayout.SOUTH);
		
		JButton btnDatSearch = new JButton("Search");
		btnDatSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(datStartDate.getDate()!= null && datEndDate.getDate() != null){
					String datStart = "";
					String datEnd = "";
					String results = "";
					
					datStart = formatDate(datStartDate.getDate().toString());
					datEnd = formatDate(datEndDate.getDate().toString());
					
					results = Main.dateRangeSearch(datStart, datEnd);
					
					rtbOutput.setText(results);
				}else{
					rtbOutput.setText("Please select a start and end date for search.");
				}			
			}
		});
		datEndDate.add(btnDatSearch, BorderLayout.SOUTH);
		
		JLabel lblInvolvements = new JLabel("Involvements");
		GridBagConstraints gbc_lblInvolvements = new GridBagConstraints();
		gbc_lblInvolvements.anchor = GridBagConstraints.WEST;
		gbc_lblInvolvements.insets = new Insets(0, 0, 5, 5);
		gbc_lblInvolvements.gridx = 1;
		gbc_lblInvolvements.gridy = 5;
		contentPane.add(lblInvolvements, gbc_lblInvolvements);
		
		JLabel lblVin = new JLabel("VIN:");
		GridBagConstraints gbc_lblVin = new GridBagConstraints();
		gbc_lblVin.insets = new Insets(0, 0, 5, 5);
		gbc_lblVin.anchor = GridBagConstraints.WEST;
		gbc_lblVin.gridx = 0;
		gbc_lblVin.gridy = 6;
		contentPane.add(lblVin, gbc_lblVin);
		
		txtVin = new JTextField();
		GridBagConstraints gbc_txtVin = new GridBagConstraints();
		gbc_txtVin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVin.insets = new Insets(0, 0, 5, 5);
		gbc_txtVin.gridx = 1;
		gbc_txtVin.gridy = 6;
		contentPane.add(txtVin, gbc_txtVin);
		txtVin.setColumns(10);
		
		
		
		JLabel lblAccidentLocation = new JLabel("Accident Info");
		GridBagConstraints gbc_lblAccidentLocation = new GridBagConstraints();
		gbc_lblAccidentLocation.anchor = GridBagConstraints.WEST;
		gbc_lblAccidentLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblAccidentLocation.gridx = 1;
		gbc_lblAccidentLocation.gridy = 0;
		contentPane.add(lblAccidentLocation, gbc_lblAccidentLocation);
		
		JLabel lblAccidentLookup = new JLabel("Direct Lookup");
		GridBagConstraints gbc_lblAccidentLookup = new GridBagConstraints();
		gbc_lblAccidentLookup.anchor = GridBagConstraints.WEST;
		gbc_lblAccidentLookup.insets = new Insets(0, 0, 5, 0);
		gbc_lblAccidentLookup.gridx = 4;
		gbc_lblAccidentLookup.gridy = 0;
		contentPane.add(lblAccidentLookup, gbc_lblAccidentLookup);
		
		JLabel lblCity = new JLabel("City:");
		GridBagConstraints gbc_lblCity = new GridBagConstraints();
		gbc_lblCity.anchor = GridBagConstraints.WEST;
		gbc_lblCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblCity.gridx = 0;
		gbc_lblCity.gridy = 1;
		contentPane.add(lblCity, gbc_lblCity);
		
		txtCity = new JTextField();
		GridBagConstraints gbc_txtCity = new GridBagConstraints();
		gbc_txtCity.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCity.insets = new Insets(0, 0, 5, 5);
		gbc_txtCity.gridx = 1;
		gbc_txtCity.gridy = 1;
		contentPane.add(txtCity, gbc_txtCity);
		txtCity.setColumns(10);
		
		JLabel lblAccidentId = new JLabel("Accident ID:");
		GridBagConstraints gbc_lblAccidentId = new GridBagConstraints();
		gbc_lblAccidentId.insets = new Insets(0, 0, 5, 5);
		gbc_lblAccidentId.anchor = GridBagConstraints.EAST;
		gbc_lblAccidentId.gridx = 3;
		gbc_lblAccidentId.gridy = 1;
		contentPane.add(lblAccidentId, gbc_lblAccidentId);
		
		txtAid = new JTextField();
		GridBagConstraints gbc_txtAid = new GridBagConstraints();
		gbc_txtAid.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAid.insets = new Insets(0, 0, 5, 0);
		gbc_txtAid.gridx = 4;
		gbc_txtAid.gridy = 1;
		contentPane.add(txtAid, gbc_txtAid);
		txtAid.setColumns(10);
		
		JLabel lblState = new JLabel("State:");
		GridBagConstraints gbc_lblState = new GridBagConstraints();
		gbc_lblState.insets = new Insets(0, 0, 5, 5);
		gbc_lblState.anchor = GridBagConstraints.WEST;
		gbc_lblState.gridx = 0;
		gbc_lblState.gridy = 2;
		contentPane.add(lblState, gbc_lblState);
		
		txtState = new JTextField();
		GridBagConstraints gbc_txtState = new GridBagConstraints();
		gbc_txtState.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtState.insets = new Insets(0, 0, 5, 5);
		gbc_txtState.gridx = 1;
		gbc_txtState.gridy = 2;
		contentPane.add(txtState, gbc_txtState);
		txtState.setColumns(10);
		
		
		
		
		JButton btnShowInfo = new JButton("Show Info");
		btnShowInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String aid = txtAid.getText();
				if(isNumeric(aid)){
					rtbOutput.setText(Main.getDetails(Integer.parseInt(aid)));
				}else{
					rtbOutput.setText("Please enter an accident ID");
				}
			}
		});
		GridBagConstraints gbc_btnShowInfo = new GridBagConstraints();
		gbc_btnShowInfo.anchor = GridBagConstraints.WEST;
		gbc_btnShowInfo.insets = new Insets(0, 0, 5, 0);
		gbc_btnShowInfo.gridx = 4;
		gbc_btnShowInfo.gridy = 2;
		contentPane.add(btnShowInfo, gbc_btnShowInfo);
		
		JLabel lblDriverSsn = new JLabel("Driver SSN:");
		GridBagConstraints gbc_lblDriverSsn = new GridBagConstraints();
		gbc_lblDriverSsn.anchor = GridBagConstraints.EAST;
		gbc_lblDriverSsn.insets = new Insets(0, 0, 5, 5);
		gbc_lblDriverSsn.gridx = 0;
		gbc_lblDriverSsn.gridy = 7;
		contentPane.add(lblDriverSsn, gbc_lblDriverSsn);
		
		txtDriverSSN = new JTextField();
		GridBagConstraints gbc_txtDriverSSN = new GridBagConstraints();
		gbc_txtDriverSSN.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDriverSSN.insets = new Insets(0, 0, 5, 5);
		gbc_txtDriverSSN.gridx = 1;
		gbc_txtDriverSSN.gridy = 7;
		contentPane.add(txtDriverSSN, gbc_txtDriverSSN);
		txtDriverSSN.setColumns(10);
		
		JLabel lblDamages = new JLabel("Damages:");
		GridBagConstraints gbc_lblDamages = new GridBagConstraints();
		gbc_lblDamages.insets = new Insets(0, 0, 5, 5);
		gbc_lblDamages.anchor = GridBagConstraints.WEST;
		gbc_lblDamages.gridx = 0;
		gbc_lblDamages.gridy = 8;
		contentPane.add(lblDamages, gbc_lblDamages);
		
		txtDamages = new JTextField();
		GridBagConstraints gbc_txtDamages = new GridBagConstraints();
		gbc_txtDamages.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDamages.insets = new Insets(0, 0, 5, 5);
		gbc_txtDamages.gridx = 1;
		gbc_txtDamages.gridy = 8;
		contentPane.add(txtDamages, gbc_txtDamages);
		txtDamages.setColumns(10);
		
		
		
		
		
		
		
		JButton btnUpdateInvolvements = new JButton("Update Involvements");
		btnUpdateInvolvements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Update Involvements Clicked
				//Needs each involvement text field to be filled with the correct type of info
				
				//take input
				String strVin = txtVin.getText();
				String strDamages = txtDamages.getText();
				String strSSN = txtDriverSSN.getText();
				
				//Check input
				if(strVin.length() == 12){
					if(isNumeric(strSSN) && strSSN.length() == 9){	
						if(isNumeric(strDamages)){
							//all numbers are in the correct format
							//Save them as strings so that they can be in one array
							involvements[0][involvementsCount] = strVin;
							involvements[1][involvementsCount] = strDamages;
							involvements[2][involvementsCount] = strSSN;
							involvementsCount++;
							
							rtbOutput.setText("Involvement #" + involvementsCount + " added. \n");
							
						}else{
							rtbOutput.setText("Damages must be a number. \n");
						}
					}else{
						rtbOutput.setText("SSN must be a 9 digit number. \n");
					}
				}else{
					rtbOutput.setText("VIN must be a 12 character string. \n");
				}
				
				
			}
		});
		
		GridBagConstraints gbc_btnUpdateInvolvements = new GridBagConstraints();
		gbc_btnUpdateInvolvements.anchor = GridBagConstraints.WEST;
		gbc_btnUpdateInvolvements.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpdateInvolvements.gridx = 1;
		gbc_btnUpdateInvolvements.gridy = 9;
		contentPane.add(btnUpdateInvolvements, gbc_btnUpdateInvolvements);
		
		
		
		
		
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static String formatDate(String uglyDate){
		//Reference: ugly date looks like this: Sun Dec 01 19:35:47 EST 2019
		String formattedDate = "";
		String year = "";
		String month = "";
		String day = "";
		String[] splitDate = uglyDate.split(" ");
		
		//Isolate the information needed
		for(int i = 0; i<6; i++){
			switch(i){
			case 0:
				//day of the week = useless
				break;
			case 1:
				if(splitDate[i].equals("Jan")){
					month = "01";
				}else if(splitDate[i].equals("Feb")){
					month = "02";
				}else if(splitDate[i].equals("Mar")){
					month = "03";
				}else if(splitDate[i].equals("Apr")){
					month = "04";
				}else if(splitDate[i].equals("May")){
					month = "05";
				}else if(splitDate[i].equals("Jun")){
					month = "06";
				}else if(splitDate[i].equals("Jul")){
					month = "07";
				}else if(splitDate[i].equals("Aug")){
					month = "08";
				}else if(splitDate[i].equals("Sep")){
					month = "09";
				}else if(splitDate[i].equals("Oct")){
					month = "10";
				}else if(splitDate[i].equals("Nov")){
					month = "11";
				}else if(splitDate[i].equals("Dec")){
					month = "12";
				}else{
					//Hmmm why did this happen?
					System.out.println(splitDate[i]);
				}
				break;
			case 2:
				day = splitDate[i];
				break;
			case 3:
				//time is useless
				break;
			case 4:
				//timezone also useless
				break;
			case 5:
				year = splitDate[i];
				break;
			}
					
		}
		
		//Build the formatted date
		formattedDate = year + "-" + month + "-" + day;
		
		return formattedDate;
	}
	
	private void clearAccidentEntryFields(){
		txtCity.setText("");
		txtState.setText("");
		txtVin.setText("");
		txtDriverSSN.setText("");
		txtDamages.setText("");
	}

}
