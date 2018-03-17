import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {
    public Gui(String title) {
        super(title);

        Container con = getContentPane();
        setLayout(new BasicSplitPaneUI.CardLayout(con, BoxLayout.X_AXIS));
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setTitle("Cards Generator");
//        frame.setSize(300,300);
    }


//    @Override
//    public void actionPerformed(ActionEvent e) {


    equalsButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent event)
        {

        }

}
