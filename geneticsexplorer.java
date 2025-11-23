import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneticsExplorer extends JFrame implements ActionListener {
    private JTextField parent1Input;
    private JTextField parent2Input;
    private JTextArea resultArea;
    private JButton calculateButton;
    
    public GeneticsExplorer() {
        setTitle("Genetics Inheritance Explorer");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        inputPanel.add(new JLabel("Parent 1 Genotype (e.g. Aa):"));
        parent1Input = new JTextField();
        inputPanel.add(parent1Input);
        
        inputPanel.add(new JLabel("Parent 2 Genotype (e.g. Aa):"));
        parent2Input = new JTextField();
        inputPanel.add(parent2Input);
        
        calculateButton = new JButton("Calculate Offspring Genotypes");
        calculateButton.addActionListener(this);
        inputPanel.add(calculateButton);
        
        add(inputPanel, BorderLayout.NORTH);
        
        // Result display
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // To be filled in next steps for calculation
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GeneticsExplorer app = new GeneticsExplorer();
            app.setVisible(true);
        });
    }
}
