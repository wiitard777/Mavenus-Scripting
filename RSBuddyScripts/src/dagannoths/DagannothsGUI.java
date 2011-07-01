package dagannoths;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import com.rsbuddy.script.methods.Environment;

import strategyLoop.*;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * newJFrame.java
 *
 * Created on May 10, 2011, 5:22:14 PM
 */

/**
 *
 * @author lisonbee
 */
public class DagannothsGUI extends javax.swing.JFrame {

	
	public ArrayList<Actions> actions = new ArrayList<Actions>();
	public Properties save = new Properties();
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates new form newJFrame */
    public DagannothsGUI() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
                          
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jCheckBox13 = new javax.swing.JCheckBox();
        jCheckBox14 = new javax.swing.JCheckBox();
        jCheckBox15 = new javax.swing.JCheckBox();
        jCheckBox16 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jCheckBox17 = new javax.swing.JCheckBox();
        jCheckBox18 = new javax.swing.JCheckBox();
        jCheckBox19 = new javax.swing.JCheckBox();
        jCheckBox20 = new javax.swing.JCheckBox();
        jCheckBox21 = new javax.swing.JCheckBox();
        jCheckBox22 = new javax.swing.JCheckBox();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jCheckBox23 = new javax.swing.JCheckBox();
        jCheckBox24 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        
        
        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setForeground(java.awt.Color.black);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Guthans Mode");
        if (save.getProperty("guthan") == "true"){
        	jRadioButton1.setSelected(true);
        }

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Bones to Peaches Mode");
        if (save.getProperty("peaches") == "true"){
        	jRadioButton2.setSelected(true);
        }

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Unnoting Mode");
        if (save.getProperty("unnote")=="true"){
        	jRadioButton3.setSelected(true);
        }

        jCheckBox1.setText("B2P for backup healing?");
        jCheckBox1.setEnabled(false);

        jCheckBox2.setText("Unnote Attack/Strength potions when out?");
        jCheckBox2.setEnabled(false);

        jCheckBox3.setText("Don't attack Dagannoths that are in combat");
        if (save.getProperty("selective")=="true"){
        	jCheckBox3.setSelected(true);
        }

        jCheckBox4.setText("Unnote Bunyip pouches / Summoning pots");
        jCheckBox4.setEnabled(false);

        jButton2.setText("Start");
        jButton2.addActionListener(new java.awt.event.ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				actions.add(new AutoRetaliate());
				actions.add(new DagJunk());
				actions.add(new BunyipSummon());
				if (jCheckBox15.isSelected()){
					actions.add(new LootEffigy());
				}
				if (jRadioButton1.isSelected()){
					
					actions.add(new GuthanHeal());
					actions.add(new GuthanOff());
					actions.add(new Normalize());
					actions.add(new DefaultBodyOn());
					actions.add(new DefaultLegsOn());
					actions.add(new DefaultHelmOn());
					actions.add(new DefaultWeapOn());
					actions.add(new DefaultShieldOn());
					actions.add(new GuthanBodyOn());
					actions.add(new GuthanSkirtOn());
					actions.add(new GuthanHelmOn());
					actions.add(new GuthanSpearOn());
					
				}else if (jRadioButton2.isSelected()){
					
					actions.add(new BonesToPeaches());
					actions.add(new EatFood());
					actions.add(new LootDags());
					actions.add(new LootBones());
					
				}else if (jRadioButton3.isSelected()){
					
					actions.add(new CloseStore());
					actions.add(new BuyItems());
					actions.add(new SellItems());
					actions.add(new WalkDags());
					actions.add(new WalkJossik());
					actions.add(new UnnoteFood());
					
					
				}
				if (jCheckBox3.isSelected()) {
					MavenDagannoths.selectiveAttack = true;
					
				} else {
					MavenDagannoths.selectiveAttack = false;
				}
				if (jCheckBox5.isSelected()){
					MavenDagannoths.WANTLOOT.add(5304);
				}else{
					MavenDagannoths.WANTJUNK.add(5304);
				}
				if (jCheckBox6.isSelected()){
					MavenDagannoths.WANTLOOT.add(5300);
				}else{
					MavenDagannoths.WANTJUNK.add(5300);
				}
				if (jCheckBox7.isSelected()){
					MavenDagannoths.WANTLOOT.add(5302);
				}else{
					MavenDagannoths.WANTJUNK.add(5302);
				}
				if (jCheckBox8.isSelected()){
					MavenDagannoths.WANTLOOT.add(5303);
				}else{
					MavenDagannoths.WANTJUNK.add(5303);
				}
				if (jCheckBox9.isSelected()){
					MavenDagannoths.WANTLOOT.add(5298);
				}else{
					MavenDagannoths.WANTJUNK.add(5298);
				}
				if (jCheckBox10.isSelected()){
					MavenDagannoths.WANTLOOT.add(5296);
				}else{
					MavenDagannoths.WANTJUNK.add(5296);
				}
				if (jCheckBox11.isSelected()){
					MavenDagannoths.WANTLOOT.add(12158);
				}else{
					MavenDagannoths.WANTJUNK.add(12158);
				}
				if (jCheckBox12.isSelected()){
					MavenDagannoths.WANTLOOT.add(12159);
				}else{
					MavenDagannoths.WANTJUNK.add(12159);
				}
				if (jCheckBox13.isSelected()){
					MavenDagannoths.WANTLOOT.add(12160);
				}else{
					MavenDagannoths.WANTJUNK.add(12160);
				}
				if (jCheckBox14.isSelected()){
					MavenDagannoths.WANTLOOT.add(12163);
				}else{
					MavenDagannoths.WANTJUNK.add(12163);
				}
				
				if (jCheckBox16.isSelected()){
					MavenDagannoths.WANTLOOT.add(402);
				}else{
					MavenDagannoths.WANTJUNK.add(402);
				}
				
				actions.add(new EatFood());
				actions.add(new LootDags());
				actions.add(new PotAttack());
				actions.add(new PotDefense());
				actions.add(new PotStrength());
				actions.add(new DagAttack());
				MavenDagannoths.setMouseSpeed = jSlider1.getValue();
				setVisible(false);
				MavenDagannoths.actions = actions;
				dispose();
			}
        		
        });

        jSlider1.setMaximum(10);
        jSlider1.setSnapToTicks(true);
        

        jLabel1.setText("Set Mouse Speed (lower = faster)");

        jButton4.setText("Save Settings");
        jButton4.addActionListener(new java.awt.event.ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				save.clear();
				if (jRadioButton1.isSelected())
					save.setProperty("guthans", "true");
				if (jRadioButton2.isSelected())
					save.setProperty("peaches", "true");
				if (jRadioButton3.isSelected())
					save.setProperty("unnote", "true");
				save.setProperty("speed",Integer.toString(jSlider1.getValue()));
				if (jCheckBox3.isSelected())
					save.setProperty("selective","true");
				if (jCheckBox5.isSelected())
					save.setProperty("torstol","true");
				if (jCheckBox6.isSelected())
					save.setProperty("snapdragon","true");
				if (jCheckBox7.isSelected())
					save.setProperty("lantadyme","true");
				if (jCheckBox8.isSelected())
					save.setProperty("dwarf weed","true");
				if (jCheckBox9.isSelected())
					save.setProperty("avantoe","true");
				if (jCheckBox10.isSelected())
					save.setProperty("toadflax","true");
				if (jCheckBox11.isSelected())
					save.setProperty("gold","true");
				if (jCheckBox12.isSelected())
					save.setProperty("green","true");
				if (jCheckBox13.isSelected())
					save.setProperty("crimson","true");
				if (jCheckBox14.isSelected())
					save.setProperty("blue","true");
				if (jCheckBox15.isSelected())
					save.setProperty("effigy","true");
				if (jCheckBox16.isSelected())
					save.setProperty("seaweed","true");
				
				File kill = new File("Dagannoth_save");
				if (kill.exists()){
					kill.delete();
				}
				FileOutputStream out;
				try {
					out = new FileOutputStream("Dagannoth_save");
					
					try {
						save.store(out, "dagannoth save");
						}catch (IOException e) {
							e.printStackTrace();
						}
				} catch (FileNotFoundException e1) {
					
					e1.printStackTrace();
				}
				
				
				
			}
        	
        });

        jButton5.setText("Load Settings");
        jButton5.addActionListener(new java.awt.event.ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					FileInputStream in = new FileInputStream("Dagannoth_save");
					try {
						save.load(in);
						
						if (save.getProperty("guthans") != null)
							jRadioButton1.setSelected(true);
						if (save.getProperty("peaches") != null)
							jRadioButton2.setSelected(true);
						if (save.getProperty("unnote") != null)
							jRadioButton3.setSelected(true);
						int temp = Integer.parseInt(save.get("speed").toString());
						
						jSlider1.setValue(temp);
						if (save.getProperty("selective")!=null)
							jCheckBox3.setSelected(true);
						if (save.getProperty("torstol")!=null)
							jCheckBox5.setSelected(true);
						if (save.getProperty("snapdragon")!=null)
							jCheckBox6.setSelected(true);
						if (save.getProperty("lantadyme")!=null)
							jCheckBox7.setSelected(true);
						if (save.getProperty("dwarf weed")!=null)
							jCheckBox8.setSelected(true);
						if (save.getProperty("avantoe")!=null)
							jCheckBox9.setSelected(true);
						if (save.getProperty("toadflax")!=null)
							jCheckBox10.setSelected(true);
						if (save.getProperty("gold")!=null)
							jCheckBox11.setSelected(true);
						if (save.getProperty("green")!=null)
							jCheckBox12.setSelected(true);
						if (save.getProperty("crimson")!=null)
							jCheckBox13.setSelected(true);
						if (save.getProperty("blue")!=null)
							jCheckBox14.setSelected(true);
						if (save.getProperty("effigy")!=null)
							jCheckBox15.setSelected(true);
						if (save.getProperty("seaweed")!=null)
							jCheckBox16.setSelected(true);
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
				} catch (FileNotFoundException e3){
					e3.printStackTrace();
				}
				
			}
        	
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jRadioButton1)
                .addGap(29, 29, 29)
                .addComponent(jRadioButton2)
                .addGap(22, 22, 22)
                .addComponent(jRadioButton3)
                .addContainerGap(101, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jCheckBox2)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jCheckBox4)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(181, 181, 181)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox3)))
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox4)
                        .addGap(12, 12, 12)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Main", jPanel1);

        jCheckBox5.setText("Torstol Seeds");
        

        jCheckBox6.setText("Snapdragon Seeds");
        

        jCheckBox7.setText("Lantadyme Seeds");
       

        jCheckBox8.setText("Dwarf Weed Seeds");
        

        jCheckBox9.setText("Avantoe Seeds");

        jCheckBox10.setText("Toadflax Seeds");

        jCheckBox11.setText("Gold Charms");
        

        jCheckBox12.setText("Green Charms");
        

        jCheckBox13.setText("Crimson Charms");
       

        jCheckBox14.setText("Blue Charms");
        

        jCheckBox15.setText("Effigies");
        

        jCheckBox16.setText("Noted Seaweed");

        jTextPane1.setText("Check which items you want to loot, all others will be put on the junk list.  Custom looting will come soon");
        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox9)
                    .addComponent(jCheckBox10)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jCheckBox5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCheckBox6)
                                            .addComponent(jCheckBox7))
                                        .addGap(38, 38, 38))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jCheckBox8)
                                        .addGap(41, 41, 41)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox14)
                                    .addComponent(jCheckBox13)
                                    .addComponent(jCheckBox11)
                                    .addComponent(jCheckBox12))
                                .addGap(6, 6, 6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox15)
                            .addComponent(jCheckBox16))))
                .addContainerGap(122, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox11)
                    .addComponent(jCheckBox15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox6)
                    .addComponent(jCheckBox16)
                    .addComponent(jCheckBox12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox7)
                    .addComponent(jCheckBox13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox8)
                    .addComponent(jCheckBox14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox10)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Loot/Junk", jPanel2);

        jRadioButton4.setText("Fight in Areas?");
        jRadioButton4.setEnabled(false);

        buttonGroup2.add(jRadioButton5);
        jRadioButton5.setText("Fight in area 1");
        jRadioButton5.setEnabled(false);

        buttonGroup2.add(jRadioButton6);
        jRadioButton6.setText("Fight in area 2");
        jRadioButton6.setEnabled(false);

        buttonGroup2.add(jRadioButton7);
        jRadioButton7.setText("Fight in area 3");
        jRadioButton7.setEnabled(false);

        buttonGroup2.add(jRadioButton8);
        jRadioButton8.setText("Fight in Custom Area");
        jRadioButton8.setEnabled(false);

        jLabel2.setText("Custom Area Setup:");

        jButton3.setText("Get Center Tile");
        jButton3.setEnabled(false);

        jLabel3.setText("Max distance to attack:");

        jTextField1.setText("10");
        jTextField1.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton4)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jRadioButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(jRadioButton8))
                .addContainerGap(175, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton4)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton5)
                    .addComponent(jRadioButton6)
                    .addComponent(jRadioButton7))
                .addGap(23, 23, 23)
                .addComponent(jRadioButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Areas", jPanel3);

        jLabel4.setText("Features to be developed and implemented later");

        jCheckBox17.setText("Cannon Use");
        jCheckBox17.setEnabled(false);

        jCheckBox18.setText("Cannon detection w/ world hopping");
        jCheckBox18.setEnabled(false);

        jCheckBox19.setText("Find Uncrowded world");
        jCheckBox19.setEnabled(false);

        jCheckBox20.setText("Stop when done with a slayer task");
        jCheckBox20.setEnabled(false);

        jCheckBox21.setText("Babysitting mode");
        jCheckBox21.setEnabled(false);

        jCheckBox22.setText("Disable Antiban");
        jCheckBox22.setEnabled(false);

        jTextField2.setText("10");
        jTextField2.setEnabled(false);

        jLabel6.setText("Logout after x hours:");
        jLabel6.setEnabled(false);
        

        jLabel7.setText("Antiban every x milliseconds");
        jLabel7.setEnabled(false);

        jTextField3.setText("18000,36000");
        jTextField3.setEnabled(false);

        jLabel8.setText("Format: floor,ceiling");
        jLabel8.setEnabled(false);

        jCheckBox23.setText("Reserved");
        jCheckBox23.setEnabled(false);

        jCheckBox24.setText("Reserved");
        jCheckBox24.setEnabled(false);

        jLabel9.setText("Custom Sleep");
        jLabel9.setEnabled(false);

        jTextField4.setText("0");
        jTextField4.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox17)
                            .addComponent(jLabel4)
                            .addComponent(jCheckBox18)
                            .addComponent(jCheckBox19)
                            .addComponent(jCheckBox20)
                            .addComponent(jCheckBox21)
                            .addComponent(jCheckBox22))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jCheckBox23)
                    .addComponent(jCheckBox24))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox19)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jCheckBox20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox22))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jCheckBox23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox24))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );

        jTabbedPane1.addTab("Misc", jPanel4);

        jLabel5.setText("Also to be finished later");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(400, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(228, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Beginner's Guide", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>                        

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DagannothsGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox16;
    private javax.swing.JCheckBox jCheckBox17;
    private javax.swing.JCheckBox jCheckBox18;
    private javax.swing.JCheckBox jCheckBox19;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox20;
    private javax.swing.JCheckBox jCheckBox21;
    private javax.swing.JCheckBox jCheckBox22;
    private javax.swing.JCheckBox jCheckBox23;
    private javax.swing.JCheckBox jCheckBox24;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration                   

}
