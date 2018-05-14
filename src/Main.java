import com.blackwell.Search;
import com.blackwell.utils.Tools;

import javax.swing.*;
import java.awt.*;

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
	private JButton Solve;
	private Color curCol = COLORS[0];

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

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private void initGUI() {

		getContentPane().setLayout(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Rubik's Cube Solver");

		// ++++++++++++++++++++++++++++++++++ Set up Solve Cube Button ++++++++++++++++++++++++++++++++++++++++++++++++++++
		Solve = new JButton("Solve Cube");
		getContentPane().add(Solve);
		Solve.setBounds(422, 64, 114, 48);
		Solve.addActionListener(evt -> solveCube());

		// ++++++++++++++++++++++++++++++++++ Set up Scramble Button ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		{
			buttonRandom = new JButton("Scramble");
			getContentPane().add(buttonRandom);
			buttonRandom.setBounds(422, 17, 114, 22);
			buttonRandom.addActionListener(evt -> {
                String r = Tools.randomCube();
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
            });
		}

		setUpButtons();
		pack();
		this.setSize(556, 441);

	}
    private void setUpButtons(){
        // ++++++++++++++++++++++++++++++++++ Set up editable facelets ++++++++++++++++++++++++++++++++++++++++++++++++++++
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 9; j++) {
                facelet[i][j] = new JButton();
                getContentPane().add(facelet[i][j]);
                facelet[i][j].setBackground(Color.gray);
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
	// ++++++++++++++++++++++++++++++++++++ End initGUI +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// +++++++++++++++++++++++++++++++ Generate cube from GUI-Input and solve it ++++++++++++++++++++++++++++++++++++++++
	private void solveCube() {
		StringBuilder sb = new StringBuilder(54);

		for (int i = 0; i < 54; i++)
			sb.insert(i, 'B');// default initialization

		for (int i = 0; i < 6; i++)
			// read the 54 facelets
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

		String cubeString = sb.toString();
		System.out.println(cubeString);

		// ++++++++++++++++++++++++ Call Search.solution method from package org.kociemba.twophase ++++++++++++++++++++++++
		String result = Search.solution(cubeString);

		// +++++++++++++++++++ Replace the error messages with more meaningful ones in your language ++++++++++++++++++++++

		JOptionPane.showMessageDialog(null, result);
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	}
}


// What comes to life - falling for a kiss
