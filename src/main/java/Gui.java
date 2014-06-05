import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lukasz on 5/31/14.
 */
public class Gui extends JFrame{

    private static final String title = "Podstawy Sztucznej Inteligencji";

    private JButton nextEpoch;
    private JProgressBar progress;
    private JPanel root;
    private JTextField startingSelectionPression;
    private JTextField endingSelectionPression;
    private JTextField epochCount;
    private JCheckBox eliteStrategy;
    private JButton run;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JCheckBox similarMatch;
    private JTextField textField1;
    private JTable epochResults;

    public Gui() {
        super(title);
        setContentPane(root);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
