package uk.oczadly.karl.aoc20.day4;

import uk.oczadly.karl.aoc20.Helper;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author Karl Oczadly
 */
public class Day4Part1 {
    
    public static void main(String[] args) throws Exception {
        int valid = 0;
        boolean inProgress = false;
        EnumSet<FieldType> presentFields = EnumSet.noneOf(FieldType.class);
        
        for (String ln : Helper.loadInput("4")) {
            if (ln.equals("")) { // Empty line
                if (inProgress && isValid(presentFields)) valid++;
                presentFields.clear();
            } else {
                inProgress = true;
                for (String s : ln.split(" ")) {
                    presentFields.add(FieldType.ofCodename(s.split(":")[0]));
                }
            }
        }
        if (inProgress && isValid(presentFields))
            valid++; // Add any last unfinished value
    
        System.out.printf("Valid passports: %d%n", valid);
    }
    
    public static boolean isValid(Set<FieldType> fields) {
        for (FieldType p : FieldType.values()) {
            if (p.required && !fields.contains(p))
                return false;
        }
        return true;
    }
    
    enum FieldType {
        BIRTH_YEAR      ("byr", true),
        ISSUE_YEAR      ("iyr", true),
        EXPIRATION_YEAR ("eyr", true),
        HEIGHT          ("hgt", true),
        HAIR_COLOUR     ("hcl", true),
        EYE_COLOUR      ("ecl", true),
        PASSPORT_ID     ("pid", true),
        COUNTRY_ID      ("cid", false);
        
        final String codename;
        final boolean required;
        FieldType(String codename, boolean required) {
            this.codename = codename;
            this.required = required;
        }
        
        public static FieldType ofCodename(String codename) {
            for (FieldType p : values()) {
                if (p.codename.equalsIgnoreCase(codename))
                    return p;
            }
            throw new IllegalArgumentException("Unknown property codename.");
        }
    }
    
}
