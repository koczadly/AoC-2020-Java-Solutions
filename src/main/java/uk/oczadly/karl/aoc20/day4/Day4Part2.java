package uk.oczadly.karl.aoc20.day4;

import uk.oczadly.karl.aoc20.Helper;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karl Oczadly
 */
public class Day4Part2 {
    
    public static void main(String[] args) throws Exception {
        // Each map represents a single ID, with a value for each field
        List<String> data = Helper.loadInput("4");
        data.add(""); // Add empty line to process final group
        List<Map<FieldType, String>> ids = loadIds(data);

        int valid = 0;
        for (Map<FieldType, String> id : ids) {
            if (isValid(id)) valid++;
        }

        System.out.printf("Valid passports: %d%n", valid);
    }
    
    public static boolean isValid(Map<FieldType, String> id) {
        for (FieldType field : FieldType.values()) {
            if (field.validator != null) {
                String val = id.get(field);
                if (val == null || !field.validator.test(val))
                    return false;
            }
        }
        return true;
    }
    
    public static List<Map<FieldType, String>> loadIds(List<String> input) {
        List<Map<FieldType, String>> loaded = new ArrayList<>();
        
        Map<FieldType, String> currentId = null;
        for (String ln : input) {
            if (ln.isEmpty()) { // Empty line
                if (currentId != null)
                    loaded.add(currentId);
                currentId = null;
            } else {
                if (currentId == null)
                    currentId = new HashMap<>();
                
                for (String s : ln.split(" ")) {
                    String[] fieldData = s.split(":");
                    FieldType fieldType = FieldType.ofCodename(fieldData[0]);
                    currentId.put(fieldType, fieldData[1]);
                }
            }
        }
        return loaded;
    }
    
    static final Pattern HEIGHT_PATTERN = Pattern.compile("^(\\d+)(cm|in)$"); // Re-use the compiled pattern
    
    enum FieldType {
        BIRTH_YEAR      ("byr", inRange(1920, 2002)),
        ISSUE_YEAR      ("iyr", inRange(2010, 2020)),
        EXPIRATION_YEAR ("eyr", inRange(2020, 2030)),
        HEIGHT          ("hgt", val -> {
            Matcher m = HEIGHT_PATTERN.matcher(val);
            if (!m.matches()) return false;
            return m.group(2).equals("cm") ? inRange(m.group(1), 150, 193) : inRange(m.group(1), 59, 76);
        }),
        HAIR_COLOUR     ("hcl", Pattern.compile("^#[0-9a-f]{6}$").asPredicate()),
        EYE_COLOUR      ("ecl", Pattern.compile("^(amb|blu|brn|gry|grn|hzl|oth)$").asPredicate()),
        PASSPORT_ID     ("pid", Pattern.compile("^\\d{9}$").asPredicate()),
        COUNTRY_ID      ("cid", null);
        
        final String codename;
        final Predicate<String> validator;
        FieldType(String codename, Predicate<String> validator) {
            this.codename = codename;
            this.validator = validator;
        }
        
        public static FieldType ofCodename(String codename) {
            for (FieldType p : values()) {
                if (p.codename.equalsIgnoreCase(codename))
                    return p;
            }
            throw new IllegalArgumentException("Unknown property codename.");
        }
    
        private static Predicate<String> inRange(int min, int max) {
            return str -> inRange(str, min, max);
        }
    
        private static boolean inRange(String strVal, int min, int max) {
            int val = Integer.parseInt(strVal);
            return val >= min && val <= max;
        }
    }
    
}
