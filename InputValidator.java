package com.yourname.validation;

/**
 * Class to validate genotype input strings.
 */
public class InputValidator {

    /**
     * Validates that the genotype string:
     * - Is not null or empty
     * - Has even length (pairs of alleles)
     * - Contains only alphabetic characters A-Z or a-z
     *
     * @param genotype Input genotype string (e.g., "AaBb")
     * @return true if valid, false otherwise
     */
    public static boolean isValidGenotype(String genotype) {
        if (genotype == null || genotype.isEmpty()) return false;
        if (genotype.length() % 2 != 0) return false;

        for (int i = 0; i < genotype.length(); i++) {
            char c = genotype.charAt(i);
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

}

