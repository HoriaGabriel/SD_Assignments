package View;

import controller.DestinationController;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DeleteDestinationView extends JFrame{

    private JTextField nameTextField;
    private JButton deleteButton;
    private JButton returnButton;

    private TravelAgencyView tView;
    private MainView mView;

    DestinationController dc = new DestinationController();

    public void initialize(MainView mView, TravelAgencyView tView) {

        this.setTitle("DeletePage");
        this.setSize(300, 300);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.tView=tView;
        this.mView=mView;

        JPanel panel = new JPanel();
        panel.setLayout(null);

        initializeForm(panel);
        initializeListeners();

        this.setContentPane(panel);
        this.setVisible(true);
    }

    private void initializeForm(JPanel panel) {

        JLabel introLabel = new JLabel("Delete Destination");
        introLabel.setBounds(100,10,500,30);

        JLabel nameLabel = new JLabel("Destination Name");
        nameLabel.setBounds(100,70,500,30);

        nameTextField = new JTextField();
        nameTextField.setBounds(100,100,100,30);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(70,160,170,30);

        returnButton = new JButton("Return");
        returnButton.setBounds(70,210,170,30);

        panel.add(introLabel);
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(deleteButton);
        panel.add(returnButton);
    }

    private void initializeListeners() {

        deleteButton.addActionListener(e -> {

            String s = nameTextField.getText();
            dc.deleteDestination(s);
        });

        returnButton.addActionListener(e -> {

            tView.initialize(mView,tView);
            this.setVisible(false);
        });
    }


}
