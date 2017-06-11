import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import layout.TableLayout;

/**
 * Created by lakshika on 9/25/16.
 */
public class GUI extends javax.swing.JFrame {
	
	HashMap<String, String> bid_details = Server.hmap;
	
	private JFrame f;
	private JPanel panel;
	private JPanel panel2;
	private JPanel controlpanel;
	
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	
	private JLabel label1_price;
	private JLabel label2_price;
	private JLabel label3_price;
	private JLabel label4_price;
	private JLabel label5_price;
	private JLabel label6_price;
	private JLabel label7_price;
	private JLabel label8_price;
	
	private JLabel label;
	
	private JTable table;
	private JScrollPane s_pane;
	private JTextField Text;
	
	public GUI() {
		Display();
		update();
	}

    private void Display() {
		
		f = new JFrame("Auction Server");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		

		panel = new JPanel();
		panel.setBackground(new java.awt.Color(250, 200, 100));
		//panel.setSize(500,500);
		
		GridLayout layout = new GridLayout(0,2,10,10);
      
		label1 = new JLabel("FB");
		label1_price = new JLabel("");
		
		label2= new JLabel("VRTU");
		label2_price= new JLabel("");
		
		label3 = new JLabel("MSFT");
		label3_price = new JLabel("");
		
		label4 = new JLabel("GOOGL");
		label4_price = new JLabel("");
		
		label5 = new JLabel("YHOO");
		label5_price = new JLabel("");
		
		label6 = new JLabel("XLNX");
		label6_price = new JLabel("");
		
		label7= new JLabel("TSLA");
		label7_price= new JLabel("");
		
		label8 = new JLabel("TXN");
		label8_price = new JLabel("");

		label = new JLabel("Search details by Symbol");
		
		
		 table = new JTable();
		  table.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null} 
				},
                new String [] {
                        "Symbol", "Company Name", "Client Name", "Price", "Bid Time"
                }
        ));
		
		s_pane = new JScrollPane();
        s_pane.setViewportView(table);
		Text = new JTextField(5);
			
		 panel.setLayout(layout); 
		 panel.add(label1);
		 panel.add(label1_price);
		 panel.add(label2);
		 panel.add(label2_price);
		 panel.add(label3);
		 panel.add(label3_price);
		 panel.add(label4);
		 panel.add(label4_price);
		 panel.add(label5);
		 panel.add(label5_price);
		 panel.add(label6);
		 panel.add(label6_price);
		 panel.add(label7);
		 panel.add(label7_price);
		 panel.add(label8);
		 panel.add(label8_price);
		
		panel2 = new JPanel();
		panel2.add(label);
		panel2.add(Text);
		 
		 controlpanel = new JPanel();
		 controlpanel.add(panel);
		 controlpanel.add(panel2);
		 
		 controlpanel.add(s_pane);
		 f.add(controlpanel);
		 
		 f.setSize(800,800);
		 f.setVisible(true);
		 
	}
	
		public void Mytable() {
		
        DefaultTableModel default_table = (DefaultTableModel) table.getModel();
        default_table.setRowCount(0);
		
        for (Map.Entry < String   , String > entry : bid_details.entrySet()) {
			
            if (    entry.getKey().toLowerCase().startsWith( Text.getText().toLowerCase() )   ) {
                Vector v = new Vector();
                v.add(entry.getKey());
                v.add(entry.getValue().split("/")[0]);
                v.add(entry.getValue().split("/")[1]);
                v.add(entry.getValue().split("/")[2]);
                v.add(entry.getValue().split("/")[3]);
                default_table.addRow(v);
            }
        }
    }
	
	public void update() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    label1_price.setText(Server.getvalue("FB"));
                    label2_price.setText(Server.getvalue("VRTU"));
                    label3_price.setText(Server.getvalue("MSFT"));
                    label4_price.setText(Server.getvalue("GOOGL"));
                    label5_price.setText(Server.getvalue("YHOO"));
                    label6_price.setText(Server.getvalue("XLNX"));
                    label7_price.setText(Server.getvalue("TSLA"));
                    label8_price.setText(Server.getvalue("TXN"));
                    Mytable();
                   
				   try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
				
            }
        }).start();

    }
	
}

   