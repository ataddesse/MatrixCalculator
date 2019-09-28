package Lab1;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

// By Amanuel Taddesse


public class Control extends View
{
    //
    Model m = new Model(this);

    public Control()
    {
   

    }
    // Create the basic components of the GUI

    public Component createComponents()
    {

        /*== MATRICES ==*/
        taA = new JTextArea();
        taB = new JTextArea();
        taC = new JTextArea();


        JPanel paneMs = new JPanel();
        paneMs.setLayout(new BoxLayout(paneMs, BoxLayout.X_AXIS));
        paneMs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        paneMs.add(MatrixPane("Matrix A", taA));
        paneMs.add(Box.createRigidArea(new Dimension(10, 0)));
        paneMs.add(MatrixPane("Matrix B", taB));
        paneMs.add(Box.createRigidArea(new Dimension(10, 0)));
        paneMs.add(MatrixPane("Matrix C", taC));


        /*== OPERATION BUTTONS ==*/
        JPanel paneBtn = new JPanel();
        paneBtn.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        paneBtn.setLayout(new GridLayout(3,3));

        JButton btnApB = new JButton("A + B = C");
        JButton btnAcB = new JButton("A - B = C");
        JButton btnAmB = new JButton("A * B = C");
        JButton btnBmA = new JButton("B * A = C");

        JButton btnTrnsA = new JButton("transpose(A) = C");

        paneBtn.add(btnApB);
        paneBtn.add(btnAcB);
        paneBtn.add(btnAmB);
        paneBtn.add(btnBmA);

        paneBtn.add(btnTrnsA);


        /*== ADD BUTTON m ==*/
        btnApB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt)
            {
                try { DisplayMatrix(AddMatrix(ReadInMatrix(taA), ReadInMatrix(taB)), taC); }
                catch(Exception e) { System.err.println("Error: " + e); }
            }
        });


        btnAcB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt)
            {
                try { DisplayMatrix(MinusMatrix(ReadInMatrix(taA), ReadInMatrix(taB)), taC); }
                catch(Exception e) { System.err.println("Error 4: " + e); }
            }
        });

        btnAmB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt)
            {
                try { DisplayMatrix(MultiplyMatrix(ReadInMatrix(taA), ReadInMatrix(taB)), taC); }
                catch(Exception e) { System.err.println("Error 3: " + e); }
            }
        });

        btnBmA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt)
            {
                try { DisplayMatrix(MultiplyMatrix(ReadInMatrix(taB), ReadInMatrix(taA)), taC); }
                catch(Exception e) { System.err.println("Error 2: " + e); }
            }
        });






        btnTrnsA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt)
            {
                try { DisplayMatrix(Transpose(ReadInMatrix(taA)), taC); }
                catch(Exception e) { System.err.println("Error 1: " + e); }
            }
        });






        /*== MAIN PANEL ==*/
        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(5,5, 5, 5));
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(paneMs);
        pane.add(paneBtn);

        JPanel fpane = new JPanel();
        fpane.setLayout(new BorderLayout());
        fpane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        fpane.add("Center", pane);
        statusBar = new JLabel("Ready");
        fpane.add("South", statusBar);

        return fpane;
    }

    /*== Setup Invidual Matrix Panes ==*/
    private JPanel MatrixPane(String str, JTextArea ta)
    {
        JScrollPane scrollPane= new JScrollPane(ta);
        int size = 200;

        scrollPane.setPreferredSize(new Dimension(size, size));
        JLabel label = new JLabel(str);
        label.setLabelFor(scrollPane);

        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(5,5, 5, 5));
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(label);
        pane.add(scrollPane);

        return pane;
    }

    public static void main(String[] args)
    {
     
    	new Control();

    }


// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------

    /*== NO MORE GUI CRAP -- LET'S DO SOME REAL WORK ==*/

// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------


    public double[][] ReadInMatrix(JTextArea ta) throws Exception
    {
        if (DEBUG) { System.out.println("Reading In Matrix"); }

        /*== Parse Text Area ==*/
        String rawtext = ta.getText();
        String val = "";
        int i=0; int j=0;
        int[] rsize = new int[max];

        /*== Determine Matrix Size/Valid ==*/
        StringTokenizer ts = new StringTokenizer(rawtext, "\n");
        while (ts.hasMoreTokens())
        {
            StringTokenizer ts2 = new StringTokenizer(ts.nextToken());
            while (ts2.hasMoreTokens()) { ts2.nextToken(); j++; }
           
            System.out.println("The value of i is: " + i);
            System.out.println("The value of j is: " + j);
            
            rsize[i] = j; i++; j=0;
            
            
        }
        
        statusBar.setText("Matrix Size: " + i);
        if ((DEBUG) || (INFO)) { System.out.println("Matrix Size: " + i); }

        for (int c=0; c < i; c++)
        {
            if (DEBUG) { System.out.println("i="+i+ "  j="+rsize[c] + "   Column: "+c); }

            if (rsize[c] != i) {
                statusBar.setText("Invalid Matrix Entered. Size Mismatch.");
                throw new Exception("Invalid Matrix Entered. Size Mismatch.");
            }
        }
        /*== set matrix size ==*/
        n = i;

        double matrix[][] = new double[n][n];
        i = j = 0; val="";

        /*== Do the actual parsing of the text now ==*/
        StringTokenizer st = new StringTokenizer(rawtext, "\n");
        while (st.hasMoreTokens())
        {
            StringTokenizer st2 = new StringTokenizer(st.nextToken());
            while (st2.hasMoreTokens())
            {
                val = st2.nextToken();
                try { matrix[i][j] = Double.valueOf(val).floatValue(); }
                catch (Exception exception) { statusBar.setText("Invalid Number Format"); }
                j++;
            }
            i++; j=0;
        }

        if (DEBUG)
        {
            System.out.println("Matrix Read::");
            for (i=0; i<n; i++)
            {
                for (j=0; j<n; j++)
                { System.out.print("m["+i+"]["+j+"] = " + matrix[i][j] + "   "); }
                System.out.println();
            }
        }

        return matrix;
    }

    //--------------------------------------------------------------
// Display Matrix in TextArea
//--------------------------------------------------------------
    public void DisplayMatrix(double[][] matrix, JTextArea ta)
    {

        /*== TODO: Better Formatting of Resultant Matrix ==*/

     //   if (DEBUG) { System.out.println("Displaying Matrix"); }
        int tms = matrix.length;
        System.out.println(tms + "This is a test"); 

        String rstr = ""; String dv = "";

        for (int i=0; i < tms; i++)
        {
            for (int j=0; j < tms; j++)
            {
                dv = nf.format(matrix[i][j]);
                rstr = rstr.concat(dv + "  ");
            }

            rstr = rstr.concat("\n");
        }

        ta.setText(rstr);
    }



    public double[][] AddMatrix(double[][] a, double[][] b) throws Exception
    {
        int tms = a.length; int tmsB = b.length;
        if (tms != tmsB) { statusBar.setText("Matrix Size Mismatch"); }

        double matrix[][] = new double[tms][tms];

        for (int i=0; i < tms; i++)
            for (int j=0; j < tms; j++)
            {
                matrix[i][j] = a[i][j] + b[i][j];
            }

        return matrix;
    }
    public double[][] MinusMatrix(double[][] a, double[][] b) throws Exception
    {
        int tms = a.length; int tmsB = b.length;
        if (tms != tmsB) { statusBar.setText("Matrix Size Mismatch"); }

        double matrix[][] = new double[tms][tms];

        for (int i=0; i < tms; i++)
            for (int j=0; j < tms; j++)
            {
                matrix[i][j] = a[i][j] - b[i][j];
            }

        return matrix;
    }

    //--------------------------------------------------------------
    public double[][] MultiplyMatrix(double[][] a, double[][] b) throws Exception
    {

        int tms = a.length; int tmsB = b.length;
        if (tms != tmsB) { statusBar.setText("Matrix Size Mismatch"); }

        double matrix[][] = new double[tms][tms];

        for (int i=0; i < tms; i++)
            for (int j=0; j < tms; j++) matrix[i][j]=0;

        for (int i=0; i < tms; i++)
            for (int j=0; j < tms; j++)
            {
                for (int p=0; p < tms; p++)
                    matrix[i][j] += a[i][p]*b[p][j];
            }

        return matrix;
    }
//--------------------------------------------------------------

    public double[][] Transpose(double[][] a)
    {
        if (INFO) { System.out.println("Performing Transpose..."); }
        int tms = a.length;

        double m[][] = new double[tms][tms];

        for (int i=0; i<tms; i++)
            for (int j=0; j<tms; j++)
            {
                m[i][j] = a[j][i];
            }

        return m;
    }

//--------------------------------------------------------------


//--------------------------------------------------------------



//--------------------------------------------------------------



//--------------------------------------------------------------




}
