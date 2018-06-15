package com.blackwell;

import com.blackwell.utils.Cube;
import com.blackwell.utils.Tools;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


public class Main extends JFrame {

	// These variables used only in the GUI-interface
	private JButton[][] facelet = new JButton[6][9];
	private final JButton[] colorSel = new JButton[6];

    private final int[] XOFF = { 3, 6, 3, 3, 0, 9 }; // start position of each button group
	private final int[] YOFF = { 0, 3, 3, 6, 3, 3 };

	private final Color[] COLORS = { Color.white, Color.red, Color.green, Color.yellow, Color.orange, Color.blue };
    private Color curCol = COLORS[0];

	private static final int WIDTH = 556;
	private static final int HEIGHT = 491;

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
	private Main() {
		initGUI();
		new Thread(() -> Search.solution(Tools.randomCube())).start();
	}

	private static void setStyle(JButton btn, int fontSize){
        btn.setBackground(new Color(59, 89, 182));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Tahoma", Font.BOLD, fontSize));
    }



	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private void initGUI() {

		ImageIcon icon = new ImageIcon(getClass().getResource("/res/rubik.png"));
		setIconImage(icon.getImage());
		getContentPane().setLayout(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.decode("#F0F8FF"));
		this.setTitle("Rubik's Cube Solver");

		// Set up Solve Cube Button

        JButton solve = new JButton("Solve");
        getContentPane().add(solve);
        solve.setBounds(422, 70, 114, 35);
        setStyle(solve, 20);
        solve.addActionListener(evt -> solveCube());

		// Button to enter moves

        JButton enterBtn = new JButton("Enter");
        getContentPane().add(enterBtn);
        enterBtn.setBounds(422, 40, 114, 22);
        setStyle(enterBtn, 15);
        enterBtn.addActionListener(evt -> {
            String input = JOptionPane.showInputDialog(null, "Enter rotations");
            if ("".equals(input)) {
                FaceCube fc = new FaceCube();
                setFacelets(fc.to_String());
                return;
            }
            String[] rot = input.split(" ");
            Cube c = new Cube(getCubeInput());
            for (String r : rot)
                c.rotate(r);
            setFacelets(c.toString());

        });

		// Set up Scramble Button

        JButton buttonRandom = new JButton("Scramble");
        getContentPane().add(buttonRandom);
        buttonRandom.setBounds(422, 17, 114, 22);
        setStyle(buttonRandom, 15);
        buttonRandom.addActionListener(evt -> {
            String r = Tools.randomCube();
            setFacelets(r);
        });


		// next move button
		JButton nextBtn = new JButton(">");
		nextBtn.setBounds(WIDTH/2+200,HEIGHT-80,40,40);
		setStyle(nextBtn, 7);
		nextBtn.addActionListener(e -> {
		    Cube c = new Cube(getCubeInput());
            if (rotations != null && index < rotations.length)
                c.rotate(rotations[index++]);
            //String rot = JOptionPane.showInputDialog(null, "Enter rotation: ");
		    //c.rotate(rot);
			setFacelets(c.toString());
			updateRotations();
        });
        getContentPane().add(nextBtn);

        // Label with solution
        resultText = new JLabel("Enter data");
        resultText.setBounds(WIDTH/2 - 200,HEIGHT-80,400,40);
        resultText.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(resultText);

        // priv move button
		JButton privBtn = new JButton("<");
		privBtn.setBounds(WIDTH/2-50-200,HEIGHT-80,40,40);
        setStyle(privBtn,7);
        getContentPane().add(privBtn);
        privBtn.addActionListener(e -> {
            Cube c = new Cube(getCubeInput());
            if (rotations != null && index > 0)
                c.alternative(rotations[--index]);
            setFacelets(c.toString());
            updateRotations();
        });

		setUpButtons();
		pack();
		this.setSize(WIDTH, HEIGHT);
	}

	private void updateRotations(){
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		for(int i=0; i<rotations.length; ++i){
			if(i == index)
				sb.append("<font color=\'red\'>").append(rotations[i]).append("</font>");
			else
				sb.append(rotations[i]);
			sb.append(" ");
		}
		sb.append("</html>");
		resultText.setText(sb.toString());
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

    // Set up editable facelets
    private void setUpButtons(){
        // size of buttons
        final int FSIZE = 45;
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

	private void solveCube() {

		resultText.setText("Loading...");
		String cubeString = getCubeInput();
		System.out.println(cubeString);

		String result = Search.solution(cubeString);
        resultText.setText(result);

        rotations = result.split(" ");
        index = 0;
		System.out.println(Arrays.toString(rotations));

	}
}


// What comes to life - falling for a kiss
