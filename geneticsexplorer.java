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
        private boolean isValidGenotype(String genotype) {
    if (genotype == null || genotype.length() == 0) return false;
    if (genotype.length() % 2 != 0) return false; // Must have pairs
    
    for (int i = 0; i < genotype.length(); i++) {
        char c = genotype.charAt(i);
        if (!Character.isLetter(c)) return false; // Only letters allowed
    }
    return true;
}

    }
    private String calculateOffspringGenotypes(String parent1, String parent2) {
    // Validate input lengths are equal and even sized (pairs of alleles)
    if (parent1.length() != parent2.length() || parent1.length() % 2 != 0) {
        return "Error: Genotypes must have equal even length (e.g. Aa, Bb).";
    }
    
    int geneCount = parent1.length() / 2;
    
    // For each gene pair, get all possible allele combinations (2x2 Punnett square)
    // Store results of all genes and later combine
    
    // List of partial genotype combinations from each gene
    java.util.List<String> geneCombinations = new java.util.ArrayList<>();
    
    for (int i = 0; i < geneCount; i++) {
        char p1a1 = parent1.charAt(2*i);
        char p1a2 = parent1.charAt(2*i + 1);
        char p2a1 = parent2.charAt(2*i);
        char p2a2 = parent2.charAt(2*i + 1);
        
        // Create all allele pairs for this gene (4 combinations)
        String[] combos = new String[] {
            "" + p1a1 + p2a1,
            "" + p1a1 + p2a2,
            "" + p1a2 + p2a1,
            "" + p1a2 + p2a2
        };
        
        // Sort alleles in each combo alphabetically (e.g. "aA" -> "Aa") for uniformity
        for (int j = 0; j < combos.length; j++) {
            char[] chars = combos[j].toCharArray();
            java.util.Arrays.sort(chars);
            combos[j] = new String(chars);
        }
        
        // Store combos for this gene (duplicated combos for frequency count)
        geneCombinations.add(String.join(",", combos));
    }
    
    // Now combine the gene combos to form full genotypes (Cartesian product)
    java.util.Map<String, Integer> freqMap = new java.util.HashMap<>();
    
    // Initialize freqMap with first gene combos
    String[] firstGeneCombos = geneCombinations.get(0).split(",");
    for (String fg : firstGeneCombos) {
        freqMap.put(fg, 1);
    }
    
    for (int i = 1; i < geneCombinations.size(); i++) {
        String[] geneCombos = geneCombinations.get(i).split(",");
        
        java.util.Map<String, Integer> tempMap = new java.util.HashMap<>();
        
        for (String existing : freqMap.keySet()) {
            int existingCount = freqMap.get(existing);
            for (String newGene : geneCombos) {
                String combined = existing + newGene;
                // Sort combined alleles gene-wise for clarity
                // We keep each gene's alleles grouped but not reorder genes overall here
                tempMap.put(combined, tempMap.getOrDefault(combined, 0) + existingCount);
            }
        }
        
        freqMap = tempMap;
    }
    
    // Total combinations = 4^geneCount
    int total = (int) Math.pow(4, geneCount);
    
    // Build result string sorted by descending frequency
    StringBuilder result = new StringBuilder();
    result.append("Possible offspring genotypes with probabilities:\n");
    freqMap.entrySet().stream()
        .sorted((e1, e2) -> e2.getValue() - e1.getValue())
        .forEach(e -> {
            double prob = (double) e.getValue() / total * 100;
            result.append(String.format("%s : %d times (%.2f%%)\n", e.getKey(), e.getValue(), prob));
        });
    
    return result.toString();
}

    @Override
    public void actionPerformed(ActionEvent e) {
    String p1 = parent1Input.getText().trim();
    String p2 = parent2Input.getText().trim();

    if (p1.isEmpty() || p2.isEmpty()) {
        resultArea.setText("Please enter both parent genotypes.");
        return;
    }

    if (!isValidGenotype(p1) || !isValidGenotype(p2)) {
        resultArea.setText("Invalid genotype format. Use pairs of letters only, e.g., AaBb.");
        return;
    }

    if (p1.length() != p2.length()) {
        resultArea.setText("Parent genotypes must be of equal length.");
        return;
    }

    String result = calculateOffspringGenotypes(p1, p2);
    resultArea.setText(result);
}

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GeneticsExplorer app = new GeneticsExplorer();
            app.setVisible(true);
        });
    }
}
