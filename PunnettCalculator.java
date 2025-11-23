import java.util.*;

public class PunnettCalculator {
    
    public static Map<String, Integer> getOffspringCounts(String parent1, String parent2) {
        int geneCount = parent1.length() / 2;
        List<String[]> geneAllelesList = new ArrayList<>();
        
        for (int i = 0; i < geneCount; i++) {
            char p1a1 = parent1.charAt(2 * i);
            char p1a2 = parent1.charAt(2 * i + 1);
            char p2a1 = parent2.charAt(2 * i);
            char p2a2 = parent2.charAt(2 * i + 1);

            String[] combos = new String[]{
                "" + p1a1 + p2a1,
                "" + p1a1 + p2a2,
                "" + p1a2 + p2a1,
                "" + p1a2 + p2a2
            };

            for (int j = 0; j < combos.length; j++) {
                char[] chars = combos[j].toCharArray();
                Arrays.sort(chars);
                combos[j] = new String(chars);
            }
            geneAllelesList.add(combos);
        }
        
        // Cartesian product
        Map<String, Integer> freqMap = new HashMap<>();
        for (String genotype : geneAllelesList.get(0)) {
            freqMap.put(genotype, freqMap.getOrDefault(genotype, 0) + 1);
        }
        
        for (int i = 1; i < geneAllelesList.size(); i++) {
            String[] geneCombos = geneAllelesList.get(i);
            Map<String, Integer> tempMap = new HashMap<>();

            for (String existing : freqMap.keySet()) {
                int existingCount = freqMap.get(existing);
                for (String combo : geneCombos) {
                    String combined = existing + combo;
                    tempMap.put(combined, tempMap.getOrDefault(combined, 0) + existingCount);
                }
            }
            freqMap = tempMap;
        }
        return freqMap;
    }
    
    public static String formatResults(Map<String, Integer> freqMap, int geneCount) {
        int total = (int) Math.pow(4, geneCount);
        StringBuilder result = new StringBuilder();
        result.append("Possible offspring genotypes and frequencies:\n");
        
        freqMap.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue() - e1.getValue())
            .forEach(e -> {
                double percent = (double) e.getValue() / total * 100;
                result.append(String.format("%s : %d (%.2f%%)\n", e.getKey(), e.getValue(), percent));
            });
        return result.toString();
    }
}
