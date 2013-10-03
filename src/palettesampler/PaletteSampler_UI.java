/*
 * PaletteSampler_UI.java
 * 
 * Copyright 2013 PaletteSampler <Kevin Wolf>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
package palettesampler;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.imgscalr.Scalr;
import static org.imgscalr.Scalr.*;

public class PaletteSampler_UI extends javax.swing.JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> listModel = new DefaultListModel<>();
    private BufferedImage image;
    private BufferedImage imageResized;
    private int color;
    private int imgScale = 500;
    private String dirName;
    private String fon;
    private boolean hasPresets = false;
    private ArrayList<ColorPanel> panelArray = new ArrayList<ColorPanel>();
    private ArrayList<PaletteColor> palette = new ArrayList<PaletteColor>();
    private int targetI = 0;
    

    /**
     * Creates new form palettesampler_UI
     */
    public PaletteSampler_UI() {
        this("./SampleImages/", "Untitled");
    }
    
    public PaletteSampler_UI(String dir, String fout) {
        dirName = dir;
        
        fon = fout;
        fon = fon + ".gpl";
        initComponents();
        //bannerPanel1.setVisible(false);
        
        palette = new ArrayList();
        panelArray.add(null);
        panelArray.add(targetColorPanel);
        panelArray.add(colorPanel1);
        panelArray.add(colorPanel2);
        panelArray.add(colorPanel3);
        panelArray.add(colorPanel4);
        panelArray.add(colorPanel5);
        panelArray.add(colorPanel6);
        panelArray.add(colorPanel7);
        panelArray.add(colorPanel8);
        panelArray.add(colorPanel9);
        panelArray.add(colorPanel10);
        panelArray.add(colorPanel11);
        panelArray.add(colorPanel12);
        panelArray.add(colorPanel13);
        panelArray.add(colorPanel14);
        panelArray.add(colorPanel15);
        panelArray.add(colorPanel16);
        panelArray.add(colorPanel17);
        panelArray.add(colorPanel18);
        panelArray.add(colorPanel19);
        panelArray.add(colorPanel20);
        panelArray.add(colorPanel21);
        panelArray.add(colorPanel22);
        panelArray.add(colorPanel23);
        panelArray.add(colorPanel24);
        panelArray.add(colorPanel25);
        panelArray.add(colorPanel26);
        panelArray.add(colorPanel27);
        panelArray.add(colorPanel28);
        panelArray.add(colorPanel29);
        panelArray.add(colorPanel30);
        panelArray.add(colorPanel31);
        panelArray.add(colorPanel32);
        
        loadImageFilenames();

        try {
            image = ImageIO.read(new File(dirName + (String) sampleImagesList.getSelectedValue()));
        } catch (IOException ex) {
            //Logger.getLogger(PaletteSampler_UI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Can't open files in: " + dirName);
        }
        imageResized = Scalr.resize(image, Method.AUTOMATIC, imgScale);
        updateImage();
        
        sampleImagesList.addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            try {
                image = ImageIO.read(new File(dirName + (String) sampleImagesList.getSelectedValue()));
            } catch (IOException ex) {
                Logger.getLogger(PaletteSampler_UI.class.getName()).log(Level.SEVERE, null, ex);
            }
            imageResized = Scalr.resize(image, Method.AUTOMATIC, imgScale);
            updateImage();
        }}
        );
        updateImage();
    }
    
       private void loadImageFilenames() {
           
        String fileEndings[] = {"JPEG","JPG","jpeg","jpg",/*"png","PNG"*/};
         
        File images = new File(dirName);
        String imgArray[] = images.list();
        int imgArraySize = 0;
        boolean end = false;
        try {
            while(end == false) {
//                File name = new File(dirName + (String) imgArray[imgArraySize]);
//                for (int i = 0; i < 6; i++) {
//                   if(name.getName().endsWith(fileEndings[i])) {
//                      System.out.println(name.getName());
                      listModel.addElement(imgArray[imgArraySize]);
//                   } 
//                }
                //System.out.println(imgArray[imgArraySize]);
                imgArraySize++;
            }
        }
        catch (ArrayIndexOutOfBoundsException ex0) {
            end = true;
        }
       
        sampleImagesList.setModel(listModel);
        sampleImagesList.setSelectedIndex(0);   
    }
    
    private void updateImage() {
        imagePanel.setSize(imageResized.getWidth(), imageResized.getHeight());
        imagePanel.setLocation((this.getWidth() - imageResized.getWidth())/2 ,
                                (this.getHeight() - imageResized.getHeight()-12)/2);
        
        imagePanel.setImage(imageResized);
        imagePanel.repaint();
    }
    
    private void updateColor(java.awt.event.MouseEvent e) {
        color = imageResized.getRGB(e.getX(), e.getY());
        colorPanel.setBackground(new Color(color));
    }
    
    private void addColor() {
        if(palette.size() < 32) {
            PaletteColor newColor = new PaletteColor(color);
            //System.out.println(newColor.getRed()+ " " + newColor.getGreen() + " " + newColor.getBlue());
            palette.add(newColor);
        
            for (int i = 1; i <= 33; i++) {
                if(palette.size() >= i) {
                    panelArray.get(i).setBackground(palette.get(palette.size()-i));
                }
            }
 
           if(targetI >= palette.size()) {
//               if(palette.size() > 33) {
//                   tar
//               }
                    targetI = palette.size();
            } 
            else {
               targetI++; 
            }
        }
        //System.out.println(targetI);
    }
    
    private void rmColor() {
        if (targetI <= 1) {
            targetI = 1;
        } else {
            targetI--;
        }
        palette.remove(targetI);
        for (int i = 1; i < 33; i++) {
            panelArray.get(i).setBackground(panelArray.get(i+1).getBackground());
        }
        
        
    }
    
    private void targetUp() {
        
        for (int i = 1; i < 33; i++) {
            panelArray.get(i).setBackground(panelArray.get(i+1).getBackground());
        }
        

        if (targetI <= 1) {
            targetI = 1;
        } else {
            targetI--;
        }

        //System.out.println(targetI);
    }
    
    private void targetDown() {
        if(targetI >= palette.size()) {
            targetI = palette.size();
        } 
        else {
           targetI++; 
        };
        //System.out.println(targetI);
        
        for (int i = 1; i <= targetI; i++) {
            if(palette.size() >= i) {
                panelArray.get(i).setBackground(palette.get(targetI-i));
            }
        }
    }  
    
    private void saveFile() {
//        System.out.println("GIMP Palette");
//        System.out.println("Name: TestName");
//        System.out.println("Columns: 4");
//        System.out.println("#");
//        for (int i = 0; i < palette.size(); i++) {
//            System.out.println(palette.get(i).getRed()+ " " + palette.get(i).getGreen() + " " + palette.get(i).getBlue() + " " + "Untitled");
//        }
//        System.out.println("");
        
        File fo = new File(fon);
        BufferedWriter writer = null;
        try {
            fo.createNewFile();
            fo.canWrite();
            writer = new BufferedWriter(new FileWriter(fo));
            writer.write("GIMP Palette\n");
            writer.write("Name: " + fon.split("\\.")[0] + "\n");
            writer.write("Columns: 4\n");
            writer.write("#\n");
            for (int i = 0; i < palette.size(); i++) {
                writer.write(
                             palette.get(i).getRed() + 
                             " " + 
                             palette.get(i).getGreen() + 
                             " " + 
                             palette.get(i).getBlue() + 
                             " " + 
                             "Untitled" +
                             "\n"
                             );
            }
            writer.newLine();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(PaletteSampler_UI.class.getName()).log(Level.SEVERE, null, ex);
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

        imagePanel = new palettesampler.ImagePanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sampleImagesList = new javax.swing.JList();
        colorPanel = new palettesampler.ColorPanel();
        jPanel2 = new javax.swing.JPanel();
        colorPanel1 = new palettesampler.ColorPanel();
        colorPanel2 = new palettesampler.ColorPanel();
        colorPanel3 = new palettesampler.ColorPanel();
        colorPanel4 = new palettesampler.ColorPanel();
        colorPanel5 = new palettesampler.ColorPanel();
        colorPanel6 = new palettesampler.ColorPanel();
        colorPanel7 = new palettesampler.ColorPanel();
        colorPanel8 = new palettesampler.ColorPanel();
        colorPanel9 = new palettesampler.ColorPanel();
        colorPanel10 = new palettesampler.ColorPanel();
        colorPanel11 = new palettesampler.ColorPanel();
        colorPanel12 = new palettesampler.ColorPanel();
        colorPanel13 = new palettesampler.ColorPanel();
        colorPanel14 = new palettesampler.ColorPanel();
        colorPanel15 = new palettesampler.ColorPanel();
        colorPanel16 = new palettesampler.ColorPanel();
        colorPanel17 = new palettesampler.ColorPanel();
        colorPanel18 = new palettesampler.ColorPanel();
        colorPanel19 = new palettesampler.ColorPanel();
        colorPanel20 = new palettesampler.ColorPanel();
        colorPanel21 = new palettesampler.ColorPanel();
        colorPanel22 = new palettesampler.ColorPanel();
        colorPanel23 = new palettesampler.ColorPanel();
        colorPanel24 = new palettesampler.ColorPanel();
        colorPanel25 = new palettesampler.ColorPanel();
        colorPanel26 = new palettesampler.ColorPanel();
        colorPanel27 = new palettesampler.ColorPanel();
        colorPanel28 = new palettesampler.ColorPanel();
        colorPanel29 = new palettesampler.ColorPanel();
        colorPanel30 = new palettesampler.ColorPanel();
        colorPanel31 = new palettesampler.ColorPanel();
        colorPanel32 = new palettesampler.ColorPanel();
        targetColorPanel = new palettesampler.ColorPanel();
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        rmButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        bannerPanel1 = new palettesampler.BannerPanel();
        jLabel1 = new javax.swing.JLabel();
        bannerPanel2 = new palettesampler.BannerPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setMinimumSize(new java.awt.Dimension(950, 600));
        setResizable(false);

        imagePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imagePanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imagePanelMouseEntered(evt);
            }
        });
        imagePanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                imagePanelMouseDragged(evt);
            }
        });

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 529, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 489, Short.MAX_VALUE)
        );

        sampleImagesList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        sampleImagesList.setName(""); // NOI18N
        jScrollPane1.setViewportView(sampleImagesList);

        colorPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        colorPanel.setPreferredSize(new java.awt.Dimension(122, 122));

        javax.swing.GroupLayout colorPanelLayout = new javax.swing.GroupLayout(colorPanel);
        colorPanel.setLayout(colorPanelLayout);
        colorPanelLayout.setHorizontalGroup(
            colorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 121, Short.MAX_VALUE)
        );
        colorPanelLayout.setVerticalGroup(
            colorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 118, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout colorPanel1Layout = new javax.swing.GroupLayout(colorPanel1);
        colorPanel1.setLayout(colorPanel1Layout);
        colorPanel1Layout.setHorizontalGroup(
            colorPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel1Layout.setVerticalGroup(
            colorPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel2.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel2Layout = new javax.swing.GroupLayout(colorPanel2);
        colorPanel2.setLayout(colorPanel2Layout);
        colorPanel2Layout.setHorizontalGroup(
            colorPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel2Layout.setVerticalGroup(
            colorPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel3.setPreferredSize(new java.awt.Dimension(0, 20));

        javax.swing.GroupLayout colorPanel3Layout = new javax.swing.GroupLayout(colorPanel3);
        colorPanel3.setLayout(colorPanel3Layout);
        colorPanel3Layout.setHorizontalGroup(
            colorPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel3Layout.setVerticalGroup(
            colorPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel4.setPreferredSize(new java.awt.Dimension(0, 20));

        javax.swing.GroupLayout colorPanel4Layout = new javax.swing.GroupLayout(colorPanel4);
        colorPanel4.setLayout(colorPanel4Layout);
        colorPanel4Layout.setHorizontalGroup(
            colorPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel4Layout.setVerticalGroup(
            colorPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel5.setPreferredSize(new java.awt.Dimension(0, 20));

        javax.swing.GroupLayout colorPanel5Layout = new javax.swing.GroupLayout(colorPanel5);
        colorPanel5.setLayout(colorPanel5Layout);
        colorPanel5Layout.setHorizontalGroup(
            colorPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel5Layout.setVerticalGroup(
            colorPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel6.setPreferredSize(new java.awt.Dimension(0, 20));

        javax.swing.GroupLayout colorPanel6Layout = new javax.swing.GroupLayout(colorPanel6);
        colorPanel6.setLayout(colorPanel6Layout);
        colorPanel6Layout.setHorizontalGroup(
            colorPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel6Layout.setVerticalGroup(
            colorPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel7.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel7Layout = new javax.swing.GroupLayout(colorPanel7);
        colorPanel7.setLayout(colorPanel7Layout);
        colorPanel7Layout.setHorizontalGroup(
            colorPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel7Layout.setVerticalGroup(
            colorPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel8.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel8Layout = new javax.swing.GroupLayout(colorPanel8);
        colorPanel8.setLayout(colorPanel8Layout);
        colorPanel8Layout.setHorizontalGroup(
            colorPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel8Layout.setVerticalGroup(
            colorPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel9.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel9Layout = new javax.swing.GroupLayout(colorPanel9);
        colorPanel9.setLayout(colorPanel9Layout);
        colorPanel9Layout.setHorizontalGroup(
            colorPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel9Layout.setVerticalGroup(
            colorPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel10.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel10Layout = new javax.swing.GroupLayout(colorPanel10);
        colorPanel10.setLayout(colorPanel10Layout);
        colorPanel10Layout.setHorizontalGroup(
            colorPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel10Layout.setVerticalGroup(
            colorPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel11.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel11Layout = new javax.swing.GroupLayout(colorPanel11);
        colorPanel11.setLayout(colorPanel11Layout);
        colorPanel11Layout.setHorizontalGroup(
            colorPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel11Layout.setVerticalGroup(
            colorPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel12.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel12Layout = new javax.swing.GroupLayout(colorPanel12);
        colorPanel12.setLayout(colorPanel12Layout);
        colorPanel12Layout.setHorizontalGroup(
            colorPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel12Layout.setVerticalGroup(
            colorPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel13.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel13Layout = new javax.swing.GroupLayout(colorPanel13);
        colorPanel13.setLayout(colorPanel13Layout);
        colorPanel13Layout.setHorizontalGroup(
            colorPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel13Layout.setVerticalGroup(
            colorPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel14.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel14Layout = new javax.swing.GroupLayout(colorPanel14);
        colorPanel14.setLayout(colorPanel14Layout);
        colorPanel14Layout.setHorizontalGroup(
            colorPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel14Layout.setVerticalGroup(
            colorPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel15.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel15Layout = new javax.swing.GroupLayout(colorPanel15);
        colorPanel15.setLayout(colorPanel15Layout);
        colorPanel15Layout.setHorizontalGroup(
            colorPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel15Layout.setVerticalGroup(
            colorPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout colorPanel16Layout = new javax.swing.GroupLayout(colorPanel16);
        colorPanel16.setLayout(colorPanel16Layout);
        colorPanel16Layout.setHorizontalGroup(
            colorPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel16Layout.setVerticalGroup(
            colorPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel17.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel17Layout = new javax.swing.GroupLayout(colorPanel17);
        colorPanel17.setLayout(colorPanel17Layout);
        colorPanel17Layout.setHorizontalGroup(
            colorPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel17Layout.setVerticalGroup(
            colorPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel18.setPreferredSize(new java.awt.Dimension(0, 20));

        javax.swing.GroupLayout colorPanel18Layout = new javax.swing.GroupLayout(colorPanel18);
        colorPanel18.setLayout(colorPanel18Layout);
        colorPanel18Layout.setHorizontalGroup(
            colorPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel18Layout.setVerticalGroup(
            colorPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel19.setPreferredSize(new java.awt.Dimension(0, 20));

        javax.swing.GroupLayout colorPanel19Layout = new javax.swing.GroupLayout(colorPanel19);
        colorPanel19.setLayout(colorPanel19Layout);
        colorPanel19Layout.setHorizontalGroup(
            colorPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel19Layout.setVerticalGroup(
            colorPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel20.setPreferredSize(new java.awt.Dimension(0, 20));

        javax.swing.GroupLayout colorPanel20Layout = new javax.swing.GroupLayout(colorPanel20);
        colorPanel20.setLayout(colorPanel20Layout);
        colorPanel20Layout.setHorizontalGroup(
            colorPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel20Layout.setVerticalGroup(
            colorPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel21.setPreferredSize(new java.awt.Dimension(0, 20));

        javax.swing.GroupLayout colorPanel21Layout = new javax.swing.GroupLayout(colorPanel21);
        colorPanel21.setLayout(colorPanel21Layout);
        colorPanel21Layout.setHorizontalGroup(
            colorPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel21Layout.setVerticalGroup(
            colorPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel22.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel22Layout = new javax.swing.GroupLayout(colorPanel22);
        colorPanel22.setLayout(colorPanel22Layout);
        colorPanel22Layout.setHorizontalGroup(
            colorPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel22Layout.setVerticalGroup(
            colorPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel23.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel23Layout = new javax.swing.GroupLayout(colorPanel23);
        colorPanel23.setLayout(colorPanel23Layout);
        colorPanel23Layout.setHorizontalGroup(
            colorPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel23Layout.setVerticalGroup(
            colorPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel24.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel24Layout = new javax.swing.GroupLayout(colorPanel24);
        colorPanel24.setLayout(colorPanel24Layout);
        colorPanel24Layout.setHorizontalGroup(
            colorPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel24Layout.setVerticalGroup(
            colorPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel25.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel25Layout = new javax.swing.GroupLayout(colorPanel25);
        colorPanel25.setLayout(colorPanel25Layout);
        colorPanel25Layout.setHorizontalGroup(
            colorPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel25Layout.setVerticalGroup(
            colorPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel26.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel26Layout = new javax.swing.GroupLayout(colorPanel26);
        colorPanel26.setLayout(colorPanel26Layout);
        colorPanel26Layout.setHorizontalGroup(
            colorPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel26Layout.setVerticalGroup(
            colorPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel27.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel27Layout = new javax.swing.GroupLayout(colorPanel27);
        colorPanel27.setLayout(colorPanel27Layout);
        colorPanel27Layout.setHorizontalGroup(
            colorPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel27Layout.setVerticalGroup(
            colorPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel28.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel28Layout = new javax.swing.GroupLayout(colorPanel28);
        colorPanel28.setLayout(colorPanel28Layout);
        colorPanel28Layout.setHorizontalGroup(
            colorPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel28Layout.setVerticalGroup(
            colorPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel29.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel29Layout = new javax.swing.GroupLayout(colorPanel29);
        colorPanel29.setLayout(colorPanel29Layout);
        colorPanel29Layout.setHorizontalGroup(
            colorPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel29Layout.setVerticalGroup(
            colorPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel30.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel30Layout = new javax.swing.GroupLayout(colorPanel30);
        colorPanel30.setLayout(colorPanel30Layout);
        colorPanel30Layout.setHorizontalGroup(
            colorPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel30Layout.setVerticalGroup(
            colorPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel31.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel31Layout = new javax.swing.GroupLayout(colorPanel31);
        colorPanel31.setLayout(colorPanel31Layout);
        colorPanel31Layout.setHorizontalGroup(
            colorPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel31Layout.setVerticalGroup(
            colorPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        colorPanel32.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout colorPanel32Layout = new javax.swing.GroupLayout(colorPanel32);
        colorPanel32.setLayout(colorPanel32Layout);
        colorPanel32Layout.setHorizontalGroup(
            colorPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorPanel32Layout.setVerticalGroup(
            colorPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout targetColorPanelLayout = new javax.swing.GroupLayout(targetColorPanel);
        targetColorPanel.setLayout(targetColorPanelLayout);
        targetColorPanelLayout.setHorizontalGroup(
            targetColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        targetColorPanelLayout.setVerticalGroup(
            targetColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        upButton.setText("Up");
        upButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                upButtonMouseClicked(evt);
            }
        });

        downButton.setText("Down");
        downButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                downButtonMouseClicked(evt);
            }
        });

        addButton.setText("Add");
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });
        addButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                addButtonKeyTyped(evt);
            }
        });

        rmButton.setText("Remove");
        rmButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rmButtonMouseClicked(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(targetColorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(colorPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(colorPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(colorPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(colorPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(colorPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(colorPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(colorPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(colorPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(colorPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(colorPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(colorPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(colorPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(colorPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(colorPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colorPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(upButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rmButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(downButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(targetColorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rmButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(upButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(downButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        bannerPanel1.setMinimumSize(new java.awt.Dimension(1000, 500));
        bannerPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bannerPanel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout bannerPanel1Layout = new javax.swing.GroupLayout(bannerPanel1);
        bannerPanel1.setLayout(bannerPanel1Layout);
        bannerPanel1Layout.setHorizontalGroup(
            bannerPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bannerPanel1Layout.setVerticalGroup(
            bannerPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setText("Sample Files");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bannerPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(bannerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 872, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(colorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78)
                .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bannerPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bannerPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(colorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void imagePanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagePanelMouseDragged
        updateColor(evt);
    }//GEN-LAST:event_imagePanelMouseDragged

    private void imagePanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagePanelMouseEntered
        Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        imagePanel.setCursor(cursor);
    }//GEN-LAST:event_imagePanelMouseEntered

    private void imagePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagePanelMouseClicked
        //System.out.println(evt.getButton());
        if(evt.getButton() == 3) {
            addColor(); 
        }
        else {
            updateColor(evt);
        }
    }//GEN-LAST:event_imagePanelMouseClicked

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        addColor();
    }//GEN-LAST:event_addButtonMouseClicked

    private void addButtonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addButtonKeyTyped
        addColor();
    }//GEN-LAST:event_addButtonKeyTyped

    private void bannerPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bannerPanel1MouseClicked
        bannerPanel1.setVisible(false);
    }//GEN-LAST:event_bannerPanel1MouseClicked

    private void upButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_upButtonMouseClicked
        targetUp();
    }//GEN-LAST:event_upButtonMouseClicked

    private void downButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downButtonMouseClicked
        targetDown();
    }//GEN-LAST:event_downButtonMouseClicked

    private void rmButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rmButtonMouseClicked
        rmColor();
    }//GEN-LAST:event_rmButtonMouseClicked

    private void saveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveButtonMouseClicked
        saveFile();
    }//GEN-LAST:event_saveButtonMouseClicked

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PaletteSampler_UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaletteSampler_UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaletteSampler_UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaletteSampler_UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                //new palettesampler_UI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private palettesampler.BannerPanel bannerPanel1;
    private palettesampler.BannerPanel bannerPanel2;
    private palettesampler.ColorPanel colorPanel;
    private palettesampler.ColorPanel colorPanel1;
    private palettesampler.ColorPanel colorPanel10;
    private palettesampler.ColorPanel colorPanel11;
    private palettesampler.ColorPanel colorPanel12;
    private palettesampler.ColorPanel colorPanel13;
    private palettesampler.ColorPanel colorPanel14;
    private palettesampler.ColorPanel colorPanel15;
    private palettesampler.ColorPanel colorPanel16;
    private palettesampler.ColorPanel colorPanel17;
    private palettesampler.ColorPanel colorPanel18;
    private palettesampler.ColorPanel colorPanel19;
    private palettesampler.ColorPanel colorPanel2;
    private palettesampler.ColorPanel colorPanel20;
    private palettesampler.ColorPanel colorPanel21;
    private palettesampler.ColorPanel colorPanel22;
    private palettesampler.ColorPanel colorPanel23;
    private palettesampler.ColorPanel colorPanel24;
    private palettesampler.ColorPanel colorPanel25;
    private palettesampler.ColorPanel colorPanel26;
    private palettesampler.ColorPanel colorPanel27;
    private palettesampler.ColorPanel colorPanel28;
    private palettesampler.ColorPanel colorPanel29;
    private palettesampler.ColorPanel colorPanel3;
    private palettesampler.ColorPanel colorPanel30;
    private palettesampler.ColorPanel colorPanel31;
    private palettesampler.ColorPanel colorPanel32;
    private palettesampler.ColorPanel colorPanel4;
    private palettesampler.ColorPanel colorPanel5;
    private palettesampler.ColorPanel colorPanel6;
    private palettesampler.ColorPanel colorPanel7;
    private palettesampler.ColorPanel colorPanel8;
    private palettesampler.ColorPanel colorPanel9;
    private javax.swing.JButton downButton;
    private palettesampler.ImagePanel imagePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton rmButton;
    private javax.swing.JList sampleImagesList;
    private javax.swing.JButton saveButton;
    private palettesampler.ColorPanel targetColorPanel;
    private javax.swing.JButton upButton;
    // End of variables declaration//GEN-END:variables
}
