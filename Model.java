package Lab1;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

// By Amanuel Taddesse


public class Model 
{
    //

public Control c;
    public Model(Control fromC)
    {
    	
    c = fromC;

    }
    // Create the basic components of the GUI

   



// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------

    /*== NO MORE GUI CRAP -- LET'S DO SOME REAL WORK ==*/

// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------


    public double[][] ReadInMatrix(JTextArea ta) throws Exception
    {
        if (c.DEBUG) { System.out.println("Reading In Matrix"); }

        /*== Parse Text Area ==*/
        String rawtext = ta.getText();
        String val = "";
        int i=0; int j=0;
        int[] rsize = new int[c.max];

        /*== Determine Matrix Size/Valid ==*/
        StringTokenizer ts = new StringTokenizer(rawtext, "\n");
        while (ts.hasMoreTokens())
        {
            StringTokenizer ts2 = new StringTokenizer(ts.nextToken());
            while (ts2.hasMoreTokens()) { ts2.nextToken(); j++; }
            rsize[i] = j; i++; j=0;
        }
        c.statusBar.setText("Matrix Size: " + i);
        if ((c.DEBUG) || (c.INFO)) { System.out.println("Matrix Size: " + i); }

        for (int k=0; k < i; k++)
        {
            if (c.DEBUG) { System.out.println("i="+i+ "  j="+rsize[k] + "   Column: "+k); }

            if (rsize[k] != i) {
                c.statusBar.setText("Invalid Matrix Entered. Size Mismatch.");
                throw new Exception("Invalid Matrix Entered. Size Mismatch.");
            }
        }
        /*== set matrix size ==*/
        c.n = i;

        double matrix[][] = new double[c.n][c.n];
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
                catch (Exception exception) { c.statusBar.setText("Invalid Number Format"); }
                j++;
            }
            i++; j=0;
        }

        if (c.DEBUG)
        {
            System.out.println("Matrix Read::");
            for (i=0; i<c.n; i++)
            {
                for (j=0; j<c.n; j++)
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

        if (c.DEBUG) { System.out.println("Displaying Matrix"); }
        int tms = matrix.length;

        String rstr = ""; String dv = "";

        for (int i=0; i < tms; i++)
        {
            for (int j=0; j < tms; j++)
            {
                dv = c.nf.format(matrix[i][j]);
                rstr = rstr.concat(dv + "  ");
            }

            rstr = rstr.concat("\n");
        }

        ta.setText(rstr);
    }



    public double[][] AddMatrix(double[][] a, double[][] b) throws Exception
    {
        int tms = a.length; int tmsB = b.length;
        if (tms != tmsB) { c.statusBar.setText("Matrix Size Mismatch"); }

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
        if (tms != tmsB) { c.statusBar.setText("Matrix Size Mismatch"); }

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
        if (tms != tmsB) { c.statusBar.setText("Matrix Size Mismatch"); }

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
        if (c.INFO) { System.out.println("Performing Transpose..."); }
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
