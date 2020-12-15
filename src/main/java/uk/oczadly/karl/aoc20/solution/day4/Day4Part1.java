package uk.oczadly.karl.aoc20.solution.day4;

import uk.oczadly.karl.aoc20.input.PuzzleInput;
import uk.oczadly.karl.aoc20.PuzzleSolution;
import uk.oczadly.karl.aoc20.util.EnumIndex;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day4Part1 extends PuzzleSolution {
    
    static final Pattern FIELD_PATTERN = Pattern.compile("(\\w+):(\\S+)");
    
    public Day4Part1() { super(4, 1); } // Initializes the day and part number
    
    @Override
    public Object solve(PuzzleInput input) {
        int valid = 0;
        EnumSet<FieldType> presentFields = EnumSet.noneOf(FieldType.class);
        
        List<String> data = input.asList();
        data.add(""); // Add empty line to process final group
        for (String ln : data) {
            if (ln.isEmpty()) { // Empty line
                if (!presentFields.isEmpty() && isValid(presentFields))
                    valid++;
                presentFields.clear();
            } else {
                Matcher m = FIELD_PATTERN.matcher(ln);
                while (m.find())
                    presentFields.add(FieldType.INDEX_CODENAME.valueOf(m.group(1)));
            }
        }
        return valid;
    }
    
    /** Returns true if the set contains all the required fields. */
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
    
        
        public static final EnumIndex<FieldType, String> INDEX_CODENAME =
                new EnumIndex<>(FieldType.class, e -> e.codename);
        
        final String codename;
        final boolean required;
        FieldType(String codename, boolean required) {
            this.codename = codename;
            this.required = required;
        }
    }
    
}
