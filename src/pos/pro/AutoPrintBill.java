/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.pro;

/**
 *
 * @author Liamani
 */


import java.awt.print.PrinterJob;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class AutoPrintBill {
     private String billContent;

    // Constructor for the AutoPrintBill class
    public AutoPrintBill() {
        this.billContent = ""; // Initialize bill content as an empty string
    }

    // Method to set the bill content
    public void setBillContent(String billContent) {
        this.billContent = billContent;
    }
    public static void main(String[] args) {
        printBill();
    }

    public static void printBill() {
        // Create a Printable object
        Printable billPrintable = new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                // Create the content to be printed in this method
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.drawString("Your bill content goes here", 100, 100); // Customize the content

                return Printable.PAGE_EXISTS;
            }
        };

        // Get the default printer job
        PrinterJob printerJob = PrinterJob.getPrinterJob();

        // Set the printable object for the print job
        printerJob.setPrintable(billPrintable);

        try {
            // Print the bill
            printerJob.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
}
