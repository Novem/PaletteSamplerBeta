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
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PaletteSampler_UI extends javax.swing.JFrame {
    

	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> listModel = new DefaultListModel<>();
    private BufferedImage image;
    private BufferedImage imageResized;
    private int color;
    private int imgScale = 500;
    private String sdir;
    private String fon;
    private ArrayList<ColorPanel> pArray = new ArrayList<ColorPanel>();
    private ArrayList<PaletteColor> palette = new ArrayList<PaletteColor>();
	private int ptop = 0; 
	private int first = 0; 
	//private int index = 0;
	private PaletteSampler_UI ps = null;

    /**
     * Creates new form palettesampler_UI
     */
    public PaletteSampler_UI() {
    	this("./SampleImages/", "Untitled");
    	setTitle("Palette Sampler"); 
    }
    
    public PaletteSampler_UI(String dir, String fout) {
    	ps = this;
    	
        sdir = dir;
        fon = fout;
        fon = fon + ".gpl";
        
        initComponents();
        
        ColorPanel panelArray[] = {
                null,
                colorPanel1, colorPanel2, colorPanel3, colorPanel4,
                colorPanel5, colorPanel6, colorPanel7, colorPanel8,
                colorPanel9, colorPanel10, colorPanel11, colorPanel12,
                colorPanel13, colorPanel14, colorPanel15, colorPanel16,
                colorPanel17, colorPanel18, colorPanel19, colorPanel20,
                colorPanel21, colorPanel22, colorPanel23, colorPanel24,
                colorPanel25, colorPanel26, colorPanel27, colorPanel28,
                colorPanel29, colorPanel30, colorPanel31, colorPanel32, 
               };
        
        for(ColorPanel c : panelArray) {
        	pArray.add(c);
        }
        
        for(int i = 0; i < 257; i++) {
            palette.add(new PaletteColor(255,255,255));
        }

        loadImageFilenames();
        loadImage();
        //updateImage();
    }
    
    private void loadImageFilenames() {
        
        String fileEndings[] = {"JPEG","JPG","jpeg","jpg","png","PNG"};
         
        File images = new File(sdir);
        ArrayList<String> imgArray = new ArrayList<String>();
        for(String name : images.list()) {
        	if(name.split("\\.").length > 1) {
        		for (int i = 0; i < fileEndings.length; i++) {
        	        if(name.split("\\.")[1].equals(fileEndings[i])) {
        		        imgArray.add(name);
        	        }
        		}
        	}
        }
        int imgArraySize = 0;
        boolean end = false;
        try {
            while(end == false) {
                listModel.addElement(imgArray.get(imgArraySize));
                imgArraySize++;
            }
        }
        catch (IndexOutOfBoundsException ex0) {
            end = true;
        }
       
        sampleImagesList.setModel(listModel);
        sampleImagesList.setSelectedIndex(0);   
    } 
    
    
    private void loadImage() {
    	   try {
               image = ImageIO.read(new File(sdir + (String) sampleImagesList.getSelectedValue()));
           } catch (IOException ex) {
               //Logger.getLogger(PaletteSampler_UI.class.getName()).log(Level.SEVERE, null, ex);
               JOptionPane.showMessageDialog(this, "Can't open files in: " + sdir);
           }
    		   imageResized = Scalr.resize(image, Method.AUTOMATIC, imgScale);
               updateImage();
           
           sampleImagesList.addListSelectionListener(new ListSelectionListener() {
           @Override
           public void valueChanged(ListSelectionEvent e) {
               try {
                   image = ImageIO.read(new File(sdir + (String) sampleImagesList.getSelectedValue()));
               } catch (IOException ex) {
                   Logger.getLogger(PaletteSampler_UI.class.getName()).log(Level.SEVERE, null, ex);
               }
               imageResized = Scalr.resize(image, Method.AUTOMATIC, imgScale);
               updateImage();
           }}
           );
           updateImage();
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
    
    private void up() {
    	if(first < 257) {
    	    first++;
    	}
        updatePArray();
    }
    
    private void down() {
    	if(first > 0) {
    	    first--;
    	}
        updatePArray();
    }
    
    public void updatePArray() {
    	for (int i = 1; i <= 32; i++) {
          	pArray.get(i).setBackground(palette.get(first+i));
          }
    }
    
    public void addColor() {
    	if(ptop < 257) {
            PaletteColor newColor = new PaletteColor(color);
            ptop++;
            palette.add(ptop, newColor);
        
            if(ptop > 32) {
        	    first++;
            }
            updatePArray();
    	}
    }
    
    public void popColor() {
    	if(ptop > 0) {
            palette.remove(ptop);
            ptop--;
            updatePArray();
    	}   
    }
    
    public void rmColor(int index) {
    	if(ptop > 0) {
            palette.remove(index);
            ptop--;
            updatePArray();
    	}   
    }
 	
    public ArrayList<PaletteColor> getPalette() {
		return palette;
	}

//	public void setPalette(ArrayList<PaletteColor> palette) {
//		this.palette = palette;
//	}
    
	public int getFirst() {
		return first;
	}

//	public void setFirst(int first) {
//		this.first = first;
//	}
    
    /**  */
    private void initComponents() {

        imagePanel = new palettesampler.ImagePanel();
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
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        rmButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        bannerPanel1 = new palettesampler.BannerPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        this.setPreferredSize(new java.awt.Dimension(1000, 675));
        //setMinimumSize(new java.awt.Dimension(950, 600));
        //setMaximumSize(new java.awt.Dimension(950, 600));
        

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
        
        loadButton.setText("Load");
        loadButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadButtonMouseClicked(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveButtonMouseClicked(evt);
            }
        });

        
        colorPanel1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, getFirst() + 1);
				frame.setVisible(true);
        	}
        });
        
        colorPanel2.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 2);
				frame.setVisible(true);
        	}
        });
        
        colorPanel3.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 3);
				frame.setVisible(true);
        	}
        });
        
        colorPanel4.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 4);
				frame.setVisible(true);
        	}
        });
        
        colorPanel5.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 5);
				frame.setVisible(true);
        	}
        });
        
        colorPanel6.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 6);
				frame.setVisible(true);
        	}
        });
        
        colorPanel7.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 7);
				frame.setVisible(true);
        	}
        });
        
        colorPanel8.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 8);
				frame.setVisible(true);
        	}
        });
        
        colorPanel9.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 9);
				frame.setVisible(true);
        	}
        });
        
        colorPanel10.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 10);
				frame.setVisible(true);
        	}
        });
        
        colorPanel11.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 11);
				frame.setVisible(true);
        	}
        });
        
        colorPanel12.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 12);
				frame.setVisible(true);
        	}
        });
        
        colorPanel13.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 13);
				frame.setVisible(true);
        	}
        });
        
        colorPanel14.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 14);
				frame.setVisible(true);
        	}
        });
        
        colorPanel15.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 15);
				frame.setVisible(true);
        	}
        });
        
        colorPanel16.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 16);
				frame.setVisible(true);
        	}
        });
        
        colorPanel17.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 17);
				frame.setVisible(true);
        	}
        });
        
        colorPanel18.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 18);
				frame.setVisible(true);
        	}
        });
        
        colorPanel19.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 19);
				frame.setVisible(true);
        	}
        });
        
        colorPanel20.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 20);
				frame.setVisible(true);
        	}
        });
        
        colorPanel21.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 21);
				frame.setVisible(true);
        	}
        });
        
        colorPanel22.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 22);
				frame.setVisible(true);
        	}
        });
        
        colorPanel23.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 23);
				frame.setVisible(true);
        	}
        });
        
        colorPanel24.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 24);
				frame.setVisible(true);
        	}
        });
        
        colorPanel25.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 25);
				frame.setVisible(true);
        	}
        });
        
        colorPanel26.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 26);
				frame.setVisible(true);
        	}
        });
        
        colorPanel27.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 27);
				frame.setVisible(true);
        	}
        });
        
        colorPanel28.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 28);
				frame.setVisible(true);
        	}
        });
        
        colorPanel29.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 29);
				frame.setVisible(true);
        	}
        });
        
        colorPanel30.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 30);
				frame.setVisible(true);
        	}
        });
        
        colorPanel31.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 31);
				frame.setVisible(true);
        	}
        });
        
        colorPanel32.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		ColorInfo frame = new ColorInfo(ps, ps.getFirst() + 32);
				frame.setVisible(true);
        	}
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2Layout.setHorizontalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(jPanel2Layout.createSequentialGroup()
        							.addComponent(colorPanel13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        						.addGroup(jPanel2Layout.createSequentialGroup()
        							.addComponent(colorPanel9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        						.addGroup(jPanel2Layout.createSequentialGroup()
        							.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(colorPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(colorPanel5, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        								.addGroup(jPanel2Layout.createSequentialGroup()
        									.addComponent(colorPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(colorPanel3, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(colorPanel4, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
        								.addGroup(jPanel2Layout.createSequentialGroup()
        									.addComponent(colorPanel6, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(colorPanel7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(colorPanel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        						.addGroup(jPanel2Layout.createSequentialGroup()
        							.addComponent(colorPanel17, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel18, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel19, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel20, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
        						.addGroup(jPanel2Layout.createSequentialGroup()
        							.addComponent(colorPanel21, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel22, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel23, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel24, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        						.addGroup(jPanel2Layout.createSequentialGroup()
        							.addComponent(colorPanel25, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel26, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel27, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel28, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        						.addGroup(jPanel2Layout.createSequentialGroup()
        							.addComponent(colorPanel29, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel30, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel31, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(colorPanel32, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        					.addContainerGap())
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addComponent(addButton, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        					.addGap(15))
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addComponent(rmButton, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        					.addGap(15))
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addComponent(upButton, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        					.addGap(15))
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addComponent(downButton, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        					.addGap(15))
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addComponent(loadButton, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        					.addGap(15))
        				.addGroup(jPanel2Layout.createSequentialGroup()
        					.addComponent(saveButton, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        					.addGap(15))))
        );
        jPanel2Layout.setVerticalGroup(
        	jPanel2Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel2Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(colorPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(colorPanel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(colorPanel9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(colorPanel13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(colorPanel17, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel18, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel19, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel20, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(colorPanel21, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel22, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel23, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel24, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(colorPanel25, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel26, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel27, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel28, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel2Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(colorPanel29, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel30, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel31, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(colorPanel32, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(addButton)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(rmButton)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(upButton)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(downButton)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(loadButton)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(saveButton)
        			.addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2.setLayout(jPanel2Layout);

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
        sampleImagesList = new javax.swing.JList<String>();
        
                sampleImagesList.setModel(new javax.swing.AbstractListModel<String>() {
                    /**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
                    public int getSize() { return strings.length; }
                    public String getElementAt(int i) { return strings[i]; }
                });
                sampleImagesList.setName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(bannerPanel1, GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(colorPanel, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
        					.addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, 564, GroupLayout.PREFERRED_SIZE)
        					.addGap(26)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel1)
        						.addComponent(sampleImagesList, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(bannerPanel1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(colorPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addGap(6)
        							.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 417, GroupLayout.PREFERRED_SIZE))
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(jLabel1)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(sampleImagesList, GroupLayout.PREFERRED_SIZE, 504, GroupLayout.PREFERRED_SIZE))))
        				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
        					.addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
        					.addGap(49))))
        );
        getContentPane().setLayout(layout);

        pack();
        setLocationRelativeTo(null);
    }
    
    /*---------------------------------- handle events----------------------------------------*/
    
    private void imagePanelMouseDragged(java.awt.event.MouseEvent evt) {
        updateColor(evt);
    }

    private void imagePanelMouseEntered(java.awt.event.MouseEvent evt) {
        Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        imagePanel.setCursor(cursor);
    }

    private void imagePanelMouseClicked(java.awt.event.MouseEvent evt) {
        //System.out.println(evt.getButton());
        if(evt.getButton() == 3) {
            addColor(); 
        }
        else {
            updateColor(evt);
        }
    }

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {
        addColor();
    }

    private void addButtonKeyTyped(java.awt.event.KeyEvent evt) {
        addColor();
    }

    private void bannerPanel1MouseClicked(java.awt.event.MouseEvent evt) {
        bannerPanel1.setVisible(false);
        updateImage();
    }

    private void upButtonMouseClicked(java.awt.event.MouseEvent evt) {
    	up();
    }

    private void downButtonMouseClicked(java.awt.event.MouseEvent evt) {
    	down();
    }

    private void rmButtonMouseClicked(java.awt.event.MouseEvent evt) {
    	popColor();
    }
    
    private void loadButtonMouseClicked(java.awt.event.MouseEvent evt) {
        //loadFile();
    }
    
    private void saveButtonMouseClicked(java.awt.event.MouseEvent evt) {
  
//      System.out.println("GIMP Palette");
//      System.out.println("Name: " + fon.split("\\.")[0]);
//      System.out.println("Columns: 4");
//      System.out.println("#");
//      for (int i = 1; i <= ptop; i++) {
//          System.out.println(palette.get(i).getRed()+ " " + palette.get(i).getGreen() + " " + palette.get(i).getBlue() + " " + palette.get(i).getName());
//      }
//      System.out.println("");
      
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
          for (int i = 1; i <= ptop; i++) {
              writer.write(
                           palette.get(i).getRed() + 
                           " " + 
                           palette.get(i).getGreen() + 
                           " " + 
                           palette.get(i).getBlue() + 
                           " " +
                           palette.get(i).getName() + 
                           "\n"
                           );
          }
          writer.newLine();
          writer.close();
      } catch (IOException ex) {
          Logger.getLogger(PaletteSampler_UI.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    /*-----------------------------------------------------------------------*/
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new PaletteSampler_UI().setVisible(true);
            }
        });
    }
    private javax.swing.JButton addButton;
    private palettesampler.BannerPanel bannerPanel1;
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
    private javax.swing.JButton rmButton;
    private javax.swing.JList<String> sampleImagesList;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton upButton;
}
