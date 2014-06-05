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
    private JTable epochResults;

    public Gui() {
        super(title);
        setContentPane(root);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUIComponents();
        setVisible(true);
    }

    private void createUIComponents() {
        Object[][] data = {{1,1,1},{2,2,2},{3,3,3},{4,4,4}};
        String[] columnNames = {"Column 1","Column 2","Column 3"};
        epochResults.setModel(new DefaultTableModel(data, columnNames));
        nextEpoch.addActionListener(new OnClickAction());
    }

   private class OnClickAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            progress.setValue((progress.getValue()) + 1);
            progress.repaint();
        }
    }
}
