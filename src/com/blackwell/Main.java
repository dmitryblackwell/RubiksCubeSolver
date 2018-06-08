package com.blackwell;

import com.blackwell.utils.Tools;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//A simple GUI example to demonstrate how to use the package org.kociemba.twophase

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation, company or business for any purpose whatever) then
 * you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of
 * Jigloo implies acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO
 * JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class Main extends JFrame {

	// +++++++++++++These variables used only in the GUI-interface+++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private JButton[][] facelet = new JButton[6][9];
	private final JButton[] colorSel = new JButton[6];

	private final int FSIZE = 45; // size of buttons
	private final int[] XOFF = { 3, 6, 3, 3, 0, 9 }; // start position of each button group
	private final int[] YOFF = { 0, 3, 3, 6, 3, 3 };

	private final Color[] COLORS = { Color.white, Color.red, Color.green, Color.yellow, Color.orange, Color.blue };
	private JButton buttonRandom; // scramble button
	private JButton solve;
	private Color curCol = COLORS[0];

	private static final int WIDTH = 556;
	private static final int HEIGHT = 491;

	private JButton nextBtn;
	private JButton privBtn;
	private JLabel resultText;
	private String[] rotations;
	private int index;

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static void main(String[] args) {
	    // running swing
		SwingUtilities.invokeLater(() -> {
            Main inst = new Main();
            inst.setLocationRelativeTo(null);
            inst.setVisible(true);
        });
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public Main() { initGUI(); }

	private static void setStyle(JButton btn, int fontSize){
        btn.setBackground(new Color(59, 89, 182));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Tahoma", Font.BOLD, fontSize));
    }
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private void initGUI() {
		ImageIcon icon = new ImageIcon("res/rubik.png");
		setIconImage(icon.getImage());
		getContentPane().setLayout(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(Color.decode("#F0F8FF"));
		this.setTitle("Rubik's Cube Solver");

		// ++++++++++++++++++++++++++++++++++ Set up Solve Cube Button ++++++++++++++++++++++++++++++++++++++++++++++++++++
		solve = new JButton("Solve");
		getContentPane().add(solve);
		solve.setBounds(422, 64, 114, 48);
		setStyle(solve, 20);
		solve.addActionListener(evt -> solveCube());

		// ++++++++++++++++++++++++++++++++++ Set up Scramble Button ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		{
			buttonRandom = new JButton("Scramble");
			getContentPane().add(buttonRandom);
			buttonRandom.setBounds(422, 17, 114, 22);
			setStyle(buttonRandom, 15);
			buttonRandom.addActionListener(evt -> {
                String r = Tools.randomCube();
                setFacelets(r);
            });
		}

		// next and priv buttons
        nextBtn = new JButton(">");
		nextBtn.setBounds(WIDTH/2+200,HEIGHT-80,40,40);
		setStyle(nextBtn, 7);
		nextBtn.addActionListener(e -> {
			FaceCube cube = new FaceCube();// get cube from facelet buttons
			CubieCube cubie = cube.toCubieCube();

			setFacelets(cubie.toFaceCube().to_String());

        });
        getContentPane().add(nextBtn);

        resultText = new JLabel("Enter data");
        resultText.setBounds(WIDTH/2 - 200,HEIGHT-80,400,40);
        resultText.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(resultText);

		privBtn = new JButton("<");
		privBtn.setBounds(WIDTH/2-50-200,HEIGHT-80,40,40);
		setStyle(privBtn,7);
		getContentPane().add(privBtn);


		setUpButtons();
		pack();
		this.setSize(WIDTH, HEIGHT);

	}


	private void setFacelets(String r){
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++) {
				switch (r.charAt(9 * i + j)) {
					case 'U':
						facelet[i][j].setBackground(COLORS[0]);
						break;
					case 'R':
						facelet[i][j].setBackground(COLORS[1]);
						break;
					case 'F':
						facelet[i][j].setBackground(COLORS[2]);
						break;
					case 'D':
						facelet[i][j].setBackground(COLORS[3]);
						break;
					case 'L':
						facelet[i][j].setBackground(COLORS[4]);
						break;
					case 'B':
						facelet[i][j].setBackground(COLORS[5]);
						break;
				}
			}
	}


    private void setUpButtons(){
        // ++++++++++++++++++++++++++++++++++ Set up editable facelets ++++++++++++++++++++++++++++++++++++++++++++++++++++
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 9; j++) {
                facelet[i][j] = new JButton();
                getContentPane().add(facelet[i][j]);
                //facelet[i][j].setBackground(Color.decode("#F0F8FF"));
                facelet[i][j].setBackground(new Color(0,0,0,0));
                facelet[i][j].setRolloverEnabled(false);
                facelet[i][j].setOpaque(true);
                facelet[i][j].setBounds(FSIZE * XOFF[i] + FSIZE * (j % 3), FSIZE * YOFF[i] + FSIZE * (j / 3), FSIZE, FSIZE);
                facelet[i][j].addActionListener(evt -> ((JButton) evt.getSource()).setBackground(curCol));
            }

        // set up symbol on the main sticker
        String[] txt = { "U", "R", "F", "D", "L", "B" };
        for (int i = 0; i < 6; i++)
            facelet[i][4].setText(txt[i]);

        // setUp colorSet buttons for controlling colors
        for (int i = 0; i < 6; i++) {
            colorSel[i] = new JButton();
            getContentPane().add(colorSel[i]);
            colorSel[i].setBackground(COLORS[i]);
            colorSel[i].setOpaque(true);
            colorSel[i].setBounds(FSIZE * (XOFF[1] + 1) + FSIZE / 4 * 3 * i, FSIZE * (YOFF[3] + 1), FSIZE / 4 * 3,
                    FSIZE / 4 * 3);
            colorSel[i].setName("" + i);
            colorSel[i].addActionListener(evt -> curCol = COLORS[Integer.parseInt(((JButton) evt.getSource()).getName())]);

        }
    }

    private String getCubeInput(){
		StringBuilder sb = new StringBuilder(54);

		for (int i = 0; i < 54; i++)
			sb.insert(i, 'B');// default initialization

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 9; j++) {
				if (facelet[i][j].getBackground() == facelet[0][4].getBackground())
					sb.setCharAt(9 * i + j, 'U');
				if (facelet[i][j].getBackground() == facelet[1][4].getBackground())
					sb.setCharAt(9 * i + j, 'R');
				if (facelet[i][j].getBackground() == facelet[2][4].getBackground())
					sb.setCharAt(9 * i + j, 'F');
				if (facelet[i][j].getBackground() == facelet[3][4].getBackground())
					sb.setCharAt(9 * i + j, 'D');
				if (facelet[i][j].getBackground() == facelet[4][4].getBackground())
					sb.setCharAt(9 * i + j, 'L');
				if (facelet[i][j].getBackground() == facelet[5][4].getBackground())
					sb.setCharAt(9 * i + j, 'B');
			}

		return sb.toString();

	}
	// ++++++++++++++++++++++++++++++++++++ End initGUI +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// +++++++++++++++++++++++++++++++ Generate cube from GUI-Input and solve it ++++++++++++++++++++++++++++++++++++++++
	private void solveCube() {
		resultText.setText("Loading...");
		String cubeString = getCubeInput();
		System.out.println(cubeString);

		// ++++++++++++++++++++++++ Call Search.solution method from package org.kociemba.twophase ++++++++++++++++++++++++
		String result = Search.solution(cubeString);
        resultText.setText(result);

        rotations = result.split(" ");
		System.out.println(Arrays.toString(rotations));
		// +++++++++++++++++++ Replace the error messages with more meaningful ones in your language ++++++++++++++++++++++

		//JOptionPane.showMessageDialog(null, result);
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	}
}


// What comes to life - falling for a kiss
