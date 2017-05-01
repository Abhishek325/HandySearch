 
import java.awt.Color;
import java.sql.*;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abhishek Kumar Singh
 */
public class NewJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
    }
    int j=4,learnFlag=0,db,space=0; 
    int[] spc=new int[100];
    void clear()
    {
        r1.setText("");
        r2.setText("");
        r3.setText("");
        r4.setText("");
        jTextField1.setText("");
        j=4; 
        jRadioButton1.setSelected(true);
        r1.setVisible(false);
        r2.setVisible(false);
        r3.setVisible(false);
        r4.setVisible(false);
        s1.setSelected(true);
        jButton8.setVisible(false);
    }
    com.mysql.jdbc.Connection con;
    com.mysql.jdbc.Statement stmt;
    ResultSet rs;
    public void connect()
    {
       try 
            {
                Class.forName("java.sql.DriverManager");
                con = (com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "");
                stmt = (com.mysql.jdbc.Statement) con.createStatement(); 
                txt1.append("Connected to the database\n");
                db=1;
                jLabel7.setVisible(false);
                jButton5.setVisible(false);
             }
         catch (Exception e)
            {  
                javax.swing.JOptionPane.showMessageDialog(rootPane,"Unable to connect to the database\n");  
                db=0;
            }
    }
    void  search(String a)
    {
        for(int i=1;i<=1;i++)
            try 
            {  
                if(a.contains(" "))
                {
                    String nxtString = "";
                    char[] words=a.toCharArray();
                   // char[] temp = new char[1000];
                    int j,l;
                    for(j=0;j<a.length();j++)
                    {
                        if(words[j]==' ')
                        {
                          // System.out.print("Space at:"+(j+1)+"\n");
                          /* while(words[j]==32)//String after spaces
                           {
                               j=j+1;
                              // System.out.println("j="+j);
                               break;
                           }*/
                            while(words[j]==32)
                                j++;
                            break;
                        }
                    }
                    l=j;
                    //System.out.println("l="+l);
                    for(j=l;j<a.length();j++)
                    { 
                         nxtString=nxtString+words[j];   
                    }
                     System.out.println("String:"+nxtString);
                     search(nxtString);
                    }//if   
                String query1 = "Select value from words where value like '"+a+"%' order by value ";  
                rs = stmt.executeQuery(query1);     
            if (rs.next())
            { 
               String wd = rs.getString("value");               
                setsugg(wd);
               break;
            }
            String query2 = "SELECT name FROM `web` WHERE `name` LIKE '"+a+"%'  order by name ";  
                rs = stmt.executeQuery(query2);
            if (rs.next())
            { 
               String wd = rs.getString("name");               
                setsugg(wd);
                jTextField1.setText(wd);
                 jButton8.setVisible(true);
               break;
            }    
           }
         catch (Exception e)
            {  
                //clear();
            }
    }
    int jflag=0;
 void setsugg(String x)
{
    String a=r1.getText();
    String b=r2.getText();
    String c=r3.getText();
    String d=r4.getText();
        switch(j)
        {
            case 4:if(disp(x)==1)
            {
                 r4.setText(x);
                 if(x.length()>0)
                   r4.setVisible(true);
                 if(j==1)
                     j++;
                 else
                    j--;
            }
                   break;
            case 3:if(disp(x)==1)
            {
                 r3.setText(x);
                 if(x.length()>0)
                   r3.setVisible(true); 
                 if(j==1)
                     j++;
                 else
                    j--;               
            }      
                    break; 
            case 2:if(disp(x)==1)
            {
                 r2.setText(x);  
                 if(x.length()>0)
                   r2.setVisible(true); 
                 if(j==1)
                     j++;
                 else 
                    j--;      
            }              
                    break;   
            case 1:if(disp(x)==1)
            {
                 r1.setText(x);
                 if(x.length()>0)
                   r1.setVisible(true); 
                 j=1;
                 jflag=1;
                 if(jflag==1)
                     j++;
            }
                    break; 
            default:;
        }
          
    }

int disp(String x)
{
    String a=r1.getText();
    String b=r2.getText();
    String c=r3.getText();
    String d=r4.getText();    
    if(a.equals(x) ||b.equals(x) ||c.equals(x) ||d.equals(x))
    return 0;
    //Else case:
    return 1;
}
char retLastIndex(String a)
{
    int index;
    char []arr=a.toCharArray();
    return arr[a.length()-1];
}
int retSpaceIndex(String a)
{
    int index;
    int sp=0;
    char []arr=a.toCharArray();
    for(index=0;index<a.length();index++)
    {
        if(arr[index]==' ')
          sp=index;  
    }
    return (sp+1);
}
int isAlphaNum(char c)
{
    if(c>=48&&c<=57)
      return 1;
    if(c>=65&&c<=90)
      return 1;
    if(c>=97&&c<=122)
      return 1;
    return 0;
}
void LearnFromChunks(String w)
{
    w=w+" ";
    char[] words=w.toCharArray();
    String wd="";
    for(int i=space;i<w.length();i++)
    {
       if(words[i]!=' ')
       {
         wd=wd+words[i];
       }
       else
       {
        System.out.println("chunk:"+wd);
        CheckAndaddTodb(wd);
        wd="";
        space=i+1;
         //System.out.println("space"+space);
       }
      
    }
    
}
void CheckAndaddTodb(String w)
{
         String checkQ="Select value from words where value like '"+w+"';";
         try {
          rs=stmt.executeQuery(checkQ);
         if(!rs.next())//word doesnt exist
         {
             String query="INSERT INTO `userdb`.`words` (`value`) VALUES ('"+w+"');";   
             stmt.executeUpdate(query);
             //System.out.print("**"+w+" added\n");
             txt1.append("**"+w+" added\n");
         }
         else
         {
            //System.out.print(w+" exists..\n");
            txt1.append(w+" exists..\n");
         }
         } catch (SQLException ex) {
           ex.printStackTrace();
         }
}
/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        f1 = new javax.swing.JFrame();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        f2 = new javax.swing.JFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt1 = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        s1 = new javax.swing.JRadioButton();
        s2 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        r1 = new javax.swing.JRadioButton();
        r2 = new javax.swing.JRadioButton();
        r3 = new javax.swing.JRadioButton();
        r4 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        cm1 = new javax.swing.JCheckBoxMenuItem();
        cm2 = new javax.swing.JCheckBoxMenuItem();

        jLabel8.setText("Website name:");

        jLabel9.setText("Address:");

        jButton6.setText("Save and Exit");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Clear");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        txt.setColumns(20);
        txt.setRows(5);
        jScrollPane1.setViewportView(txt);

        jLabel10.setText("Description:");

        javax.swing.GroupLayout f1Layout = new javax.swing.GroupLayout(f1.getContentPane());
        f1.getContentPane().setLayout(f1Layout);
        f1Layout.setHorizontalGroup(
            f1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(f1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(f1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(f1Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        f1Layout.setVerticalGroup(
            f1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(f1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(f1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(f1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(26, 26, 26)
                .addGroup(f1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        txt1.setEditable(false);
        txt1.setColumns(20);
        txt1.setRows(5);
        jScrollPane2.setViewportView(txt1);

        jLabel11.setText("Activity Log :");

        jButton9.setText("Exit");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout f2Layout = new javax.swing.GroupLayout(f2.getContentPane());
        f2.getContentPane().setLayout(f2Layout);
        f2Layout.setHorizontalGroup(
            f2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2Layout.createSequentialGroup()
                .addGroup(f2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(f2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11))
                    .addGroup(f2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(f2Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        f2Layout.setVerticalGroup(
            f2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Optimal query engine");
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setText("Enter text:");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel2.setText("Seacrh in");

        buttonGroup1.add(s1);
        s1.setText("Google");
        s1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(s2);
        s2.setText("Yahoo");

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Exit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        buttonGroup2.add(r1);
        r1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                r1MouseMoved(evt);
            }
        });
        r1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r1ActionPerformed(evt);
            }
        });

        buttonGroup2.add(r2);
        r2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r2ActionPerformed(evt);
            }
        });

        buttonGroup2.add(r3);
        r3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r3ActionPerformed(evt);
            }
        });

        buttonGroup2.add(r4);
        r4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(r1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(r1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(r2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(r3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(r4)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        buttonGroup2.add(jRadioButton1);

        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("Database not connected");

        jButton5.setText("Connect");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setText("Open");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jMenu1.setText("Tools");

        jMenuItem1.setText("Webpage custom");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Logs");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        cm1.setSelected(true);
        cm1.setText("Learn form chunks");
        cm1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cm1ActionPerformed(evt);
            }
        });
        jMenu1.add(cm1);

        cm2.setSelected(true);
        cm2.setText("Learn sentences");
        jMenu1.add(cm2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(s1)
                    .addComponent(jLabel2)
                    .addComponent(s2)
                    .addComponent(jButton1)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(s1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(s2)
                                .addGap(11, 11, 11)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 4, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        clear();
        space=0;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try{
          con.close();
        }
        catch(Exception e)
        {}
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void s1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_s1ActionPerformed

    private void r1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r1ActionPerformed
        // TODO add your handling code here:
        String a=jTextField1.getText();
        String b=r1.getText();
        if(!a.contains(" "))
           jTextField1.setText(b+" ");
        else
        { 
            if(a.length()>b.length())
            {
             int index=retSpaceIndex(a); 
             char c=retLastIndex(a); 
             jTextField1.setText(a.substring(0, index)+b+" ");
            }
            else
               jTextField1.setText(b);
        }
        jTextField1.requestFocus();
        r1.setText("");
        r2.setText("");
        r3.setText("");
        r4.setText("");
        j=4;
        jRadioButton1.setSelected(true);
        r1.setVisible(false);
        r2.setVisible(false);
        r3.setVisible(false);
        r4.setVisible(false);
    }//GEN-LAST:event_r1ActionPerformed

    private void r2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r2ActionPerformed
        // TODO add your handling code here:
        String a=jTextField1.getText();
        String b=r2.getText();
        if(!a.contains(" "))
           jTextField1.setText(b+" ");
        else
        { 
           if(a.length()>b.length())
            {
             int index=retSpaceIndex(a); 
             char c=retLastIndex(a); 
             jTextField1.setText(a.substring(0, index)+b+" ");
            }
            else
               jTextField1.setText(b);
        }
         jTextField1.requestFocus();
         r1.setText("");
        r2.setText("");
        r3.setText("");
        r4.setText("");
        j=4;
        jRadioButton1.setSelected(true);
        r1.setVisible(false);
        r2.setVisible(false);
        r3.setVisible(false);
        r4.setVisible(false);
    }//GEN-LAST:event_r2ActionPerformed

    private void r3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r3ActionPerformed
        // TODO add your handling code here:
        String a=jTextField1.getText();
        String b=r3.getText();
        if(!a.contains(" "))
           jTextField1.setText(b+" ");
        else
        { 
          if(a.length()>b.length())
            {
             int index=retSpaceIndex(a); 
             char c=retLastIndex(a); 
             jTextField1.setText(a.substring(0, index)+b+" ");
            }
            else
               jTextField1.setText(b);
        }
         jTextField1.requestFocus();
         r1.setText("");
        r2.setText("");
        r3.setText("");
        r4.setText("");
        j=4;
        jRadioButton1.setSelected(true);
        r1.setVisible(false);
        r2.setVisible(false);
        r3.setVisible(false);
        r4.setVisible(false);
    }//GEN-LAST:event_r3ActionPerformed

    private void r4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r4ActionPerformed
        // TODO add your handling code here:
        String a=jTextField1.getText();
        String b=r4.getText();
        if(!a.contains(" "))
           jTextField1.setText(b+" ");
        else
        { 
            if(a.length()>b.length())
            {
             int index=retSpaceIndex(a); 
             char c=retLastIndex(a); 
             jTextField1.setText(a.substring(0, index)+b+" ");
            }
            else
               jTextField1.setText(b);
        }
         jTextField1.requestFocus();
         r1.setText("");
        r2.setText("");
        r3.setText("");
        r4.setText("");
        j=4;
        jRadioButton1.setSelected(true);
        r1.setVisible(false);
        r2.setVisible(false);
        r3.setVisible(false);
        r4.setVisible(false);
    }//GEN-LAST:event_r4ActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        connect();
        s1.setSelected(true);
        jRadioButton1.setVisible(false);
        r1.setVisible(false);
        r2.setVisible(false);
        r3.setVisible(false);
        r4.setVisible(false);
        jButton8.setVisible(false);
        if(db==0)
        {
            jLabel7.setVisible(true);
            jButton5.setVisible(true);
        }
        else if(db==1)
        {
            jLabel7.setVisible(false);
            jButton5.setVisible(false);
        }
    }//GEN-LAST:event_formComponentShown

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String w=jTextField1.getText();  
        if(cm1.isSelected())
         LearnFromChunks(w);
        if(cm2.isSelected())
         CheckAndaddTodb(w);
        if(s1.isSelected()&&w.equals(""))
        {
            String url="www.google.com/?q="+w  ;
            try{
             //java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
             txt1.append("Opening "+url);
            }
            catch(Exception e)
            {javax.swing.JOptionPane.showMessageDialog(rootPane, "Unable to start browser!!!");}
        } 
        else if(s2.isSelected()&&w.equals(""))
        {
            String url="https://in.search.yahoo.com/search?p="+w  ;
            try{
             //java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
             txt1.append("Opening "+url+"\n");
            }
            catch(Exception e)
            {javax.swing.JOptionPane.showMessageDialog(rootPane, "Unable to start browser!!!");}
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        connect();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jTextField2.setText("");
        jTextField3.setText("");
        txt.setText("");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        String w=jTextField2.getText().toLowerCase();
        String addr=jTextField3.getText().toLowerCase();
        String desc=txt.getText();
        if(db==0)
          connect();
        if(w.equals("")||addr.equals("")||desc.equals(""))
        {
            javax.swing.JOptionPane.showMessageDialog(rootPane,"Null value termination !!!");
        }
        else
        {
        String checkQ="Select name from web where name like '"+w+"';";
         try {
          rs=stmt.executeQuery(checkQ);
         if(!rs.next())//word doesnt exist
         {
             String query="INSERT INTO `userdb`.`web` (`name`, `addr`, `desc`) VALUES ('"+w+"', '"+addr+"', '"+desc+"');";   
             stmt.executeUpdate(query);
             txt1.append(w+ " added to database"+"\n");
             f1.dispose();
         }
         else
         {
             javax.swing.JOptionPane.showMessageDialog(rootPane,w+" already exists in the database");
         }
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        f1.setVisible(true);
        f1.setBounds(407, 307,407, 307);
    }//GEN-LAST:event_jMenuItem1ActionPerformed
private static String VOICENAME="kevin16";
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here: 
        if(db==0)
           connect();
        String url = "";
        String q="SELECT addr FROM `web` WHERE `name` LIKE '"+jTextField1.getText()+"'; ";
        try {
        rs=stmt.executeQuery(q);
        if(rs.next())
        {
            url=rs.getString("addr");
        }
        }
        catch(Exception e)
                {
                 e.printStackTrace();
                }
        try
        {
        java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
             txt1.append("Opening "+url+"\n");
         }
            catch(Exception e)
            {e.printStackTrace();}
        jButton8.setVisible(false);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        f2.dispose();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        f2.setVisible(true);
        f2.setBounds(400, 316,400, 316);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        String a=jTextField1.getText();
        //System.out.print(a+" is entered");
        search(a);
        jPanel2.setVisible(true);
        if(jTextField1.getText().equals(""))
        clear();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void r1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_r1MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_r1MouseMoved

    private void cm1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cm1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cm1ActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        jTextField1.requestFocus();
    }//GEN-LAST:event_formWindowActivated

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBoxMenuItem cm1;
    private javax.swing.JCheckBoxMenuItem cm2;
    private javax.swing.JFrame f1;
    private javax.swing.JFrame f2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JRadioButton r1;
    private javax.swing.JRadioButton r2;
    private javax.swing.JRadioButton r3;
    private javax.swing.JRadioButton r4;
    private javax.swing.JRadioButton s1;
    private javax.swing.JRadioButton s2;
    private javax.swing.JTextArea txt;
    private javax.swing.JTextArea txt1;
    // End of variables declaration//GEN-END:variables
}
