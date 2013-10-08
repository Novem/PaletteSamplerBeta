/*
 * BannerPanel.java
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
import java.awt.Graphics;
import java.util.Random;


public class BannerPanel extends javax.swing.JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6735176382887566619L;
	Random rand = new Random();
    private Color colorArray[] = {
                                  Color.BLACK, Color.BLUE, Color.CYAN,
                                  Color.GREEN, Color.MAGENTA, Color.PINK,
                                  Color.RED, Color.WHITE, Color.YELLOW,
                                  Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY,
                                 };

    /**
     * Creates new form BannerPanel
     */
    public BannerPanel() {
        initComponents();
        rand.setSeed(System.nanoTime());
    }
    
    private Color rColor() {
        return colorArray[Math.abs(rand.nextInt()%12)];
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        
        for (int i = 0; i < 10; i++) {
            g.setColor(rColor());
            g.fillRect(i*10, 0, 10, 10); 
            g.setColor(rColor());
            g.fillRect(i*10, 10, 10, 10);
            g.setColor(rColor());
            g.fillRect(i*10, 20, 10, 10);
            
        }
        g.setColor(Color.WHITE);
        g.fillRect(70, 0, 120, 30);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf("PaletteSampler"), 80, 20);
        
        for (int i = 0; i < this.getWidth(); i++) {
            g.setColor(rColor());
            g.fillRect(i*10+190, 0, 10, 10); 
            g.setColor(rColor());
            g.fillRect(i*10+190, 10, 10, 10);
            g.setColor(rColor());
            g.fillRect(i*10+190, 20, 10, 10);
            
        }
    }
    
    


    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );
    }

}
