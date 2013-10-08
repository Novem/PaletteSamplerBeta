/*
 * ColorInfo.java
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

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorInfo extends JFrame {
	
	private int ix = 0;
	private PaletteSampler_UI ps = null;


	/**
	 * 
	 */
	private static final long serialVersionUID = 2389839170132295856L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtRed;
	private JTextField txtGreen;
	private JTextField txtBlue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

			}
		});
	}


	/**
	 * Create the frame.
	 */
	public ColorInfo(PaletteSampler_UI parentFrame, int index) {
		this.ps = parentFrame;
		this.ix = index;
		
		setTitle("Color Info.");
		setResizable(false);
		setAlwaysOnTop(true);
		//setUndecorated(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new java.awt.Dimension(700, 400));
		setBounds(100, 100, 500, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		ColorPanel panel = new ColorPanel();
		panel.setBackground(ps.getPalette().get(index));
		
		JPanel panel_1 = new JPanel();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(107)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 468, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(121, Short.MAX_VALUE))
		);
		
		JPanel panel_2 = new JPanel();
		
		JPanel panel_3 = new JPanel();
		
		JPanel panel_4 = new JPanel();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))
					.addContainerGap())
				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addGap(60)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		SpringLayout sl_panel_4 = new SpringLayout();
		panel_4.setLayout(sl_panel_4);
		
		JLabel lblColorName = new JLabel("Color Name:");
		sl_panel_4.putConstraint(SpringLayout.NORTH, lblColorName, 7, SpringLayout.NORTH, panel_4);
		sl_panel_4.putConstraint(SpringLayout.WEST, lblColorName, 10, SpringLayout.WEST, panel_4);
		panel_4.add(lblColorName);
		
		txtName = new JTextField();
		sl_panel_4.putConstraint(SpringLayout.NORTH, txtName, 3, SpringLayout.NORTH, panel_4);
		sl_panel_4.putConstraint(SpringLayout.WEST, txtName, 6, SpringLayout.EAST, lblColorName);
		sl_panel_4.putConstraint(SpringLayout.EAST, txtName, -10, SpringLayout.EAST, panel_4);
		txtName.setText(ps.getPalette().get(index).getName());
		panel_4.add(txtName);
		txtName.setColumns(10);
		SpringLayout sl_panel_3 = new SpringLayout();
		panel_3.setLayout(sl_panel_3);
		
		JLabel lblRed = new JLabel("Red:");
		sl_panel_3.putConstraint(SpringLayout.NORTH, lblRed, 7, SpringLayout.NORTH, panel_3);
		sl_panel_3.putConstraint(SpringLayout.WEST, lblRed, 10, SpringLayout.WEST, panel_3);
		panel_3.add(lblRed);
		
		JLabel lblGreen = new JLabel("Green:");
		sl_panel_3.putConstraint(SpringLayout.NORTH, lblGreen, 0, SpringLayout.NORTH, lblRed);
		sl_panel_3.putConstraint(SpringLayout.EAST, lblGreen, -245, SpringLayout.EAST, panel_3);
		panel_3.add(lblGreen);
		
		JLabel lblBlue = new JLabel("Blue:");
		sl_panel_3.putConstraint(SpringLayout.EAST, lblBlue, -75, SpringLayout.EAST, panel_3);
		sl_panel_3.putConstraint(SpringLayout.NORTH, lblBlue, 0, SpringLayout.NORTH, lblRed);
		panel_3.add(lblBlue);
		
		txtRed = new JTextField();
		sl_panel_3.putConstraint(SpringLayout.NORTH, txtRed, -2, SpringLayout.NORTH, lblRed);
		sl_panel_3.putConstraint(SpringLayout.WEST, txtRed, 6, SpringLayout.EAST, lblRed);
		sl_panel_3.putConstraint(SpringLayout.EAST, txtRed, 67, SpringLayout.EAST, lblRed);
		txtRed.setText(String.valueOf(ps.getPalette().get(ix).getRed()));
		panel_3.add(txtRed);
		txtRed.setColumns(10);
		
		txtGreen = new JTextField();
		sl_panel_3.putConstraint(SpringLayout.NORTH, txtGreen, -2, SpringLayout.NORTH, lblRed);
		sl_panel_3.putConstraint(SpringLayout.WEST, txtGreen, 6, SpringLayout.EAST, lblGreen);
		sl_panel_3.putConstraint(SpringLayout.EAST, txtGreen, 67, SpringLayout.EAST, lblGreen);
		txtGreen.setText(String.valueOf(ps.getPalette().get(ix).getGreen()));
		panel_3.add(txtGreen);
		txtGreen.setColumns(10);
		
		txtBlue = new JTextField();
		sl_panel_3.putConstraint(SpringLayout.NORTH, txtBlue, -2, SpringLayout.NORTH, lblRed);
		sl_panel_3.putConstraint(SpringLayout.WEST, txtBlue, 6, SpringLayout.EAST, lblBlue);
		sl_panel_3.putConstraint(SpringLayout.EAST, txtBlue, -6, SpringLayout.EAST, panel_3);
		panel_3.add(txtBlue);
		txtBlue.setText(String.valueOf(ps.getPalette().get(ix).getBlue()));
		txtBlue.setColumns(10);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ps.rmColor(getIndex());
				setVisible(false);
			}
		});
		
		JButton btnOk = new JButton("Ok");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ps.getPalette().get(ix).setName(txtName.getText());
				setVisible(false);
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap(229, Short.MAX_VALUE)
					.addComponent(btnRemove)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRemove)
						.addComponent(btnOk))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		panel_1.setLayout(gl_panel_1);
		contentPane.setLayout(gl_contentPane);
		
		
        setLocationRelativeTo(null);

	}


	public int getIndex() {
		return ix;
	}


	public void setIndex(int index) {
		this.ix = index;
	}
}
