// Tyler Nguyen and Andy Lai, 2024/06/15
// Class: Popup
// Description: Class which creates the popup window for the user to input their username

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Popup extends JFrame implements ActionListener{
    // Instance variables
    JTextField filenameArea;
    JButton submitFilename;

    // Description: constructor for poppup class
	// Parameters: takes in nothing
	// Return: nothing, but creates a popup window
    public Popup() {
        // Setup popup layout and window 
        this.setPreferredSize(new Dimension(400, 200));
        this.getContentPane().setBackground(new Color(8, 5, 7));
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(layout);
		this.setFocusable(true);
        this.setName("Enter file name");
        this.setVisible(false);
        this.validate();
        this.revalidate();

        // Add filename input textfield to popup panel with grid bag layout properties
        filenameArea = new JTextField("", 2);
        filenameArea.setFont(GamePanel.customFont50);
        filenameArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        filenameArea.setForeground(new Color(0, 0, 0));

        // Add submit button to popup panel with grid bag layout properties
        submitFilename = new JButton("Submit");
        submitFilename.setFocusPainted(false);
        submitFilename.setFont(GamePanel.customFont50);
        submitFilename.setForeground(new Color(255, 255, 255));
        submitFilename.setBackground(new Color(129, 58, 138));
        submitFilename.setActionCommand("submit");
        submitFilename.addActionListener(this);

        // Add  filename input textfield to popup panel with grid bag layout properties
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 0.5;
        this.add(filenameArea, gbc);

        // Add submit button to popup panel with grid bag layout properties
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(submitFilename, gbc);

        // Pack window
        this.pack();
    }

    // Description: actionListener method to make sure the button and textfield work
	// Parameters: takes in ActionEvent e
	// Return: nothing, but ensures the UI is fully functional
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        // If user clicks submit
        if(event.equals("submit")) {
            // Play audio
            Audio.playAudio("click");
            // Get username
            if(filenameArea.getText().replaceAll(" ", "").length() > 0) {
                GamePanel.username = filenameArea.getText().replaceAll(" ", "");
                GamePanel.nameAdded = true;
                // Close popup and change gamestate
                this.setVisible(false);
                GamePanel.previousGameState = GamePanel.gameState;
                GamePanel.gameState = 2;
            }      
        }
    }

}
