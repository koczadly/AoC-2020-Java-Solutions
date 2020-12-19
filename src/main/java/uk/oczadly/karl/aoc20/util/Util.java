package uk.oczadly.karl.aoc20.util;

import uk.oczadly.karl.aoc20.input.IllegalInputException;

import java.util.Collection;

/**
 * @author Karl Oczadly
 */
public class Util {
    
    public static <T> T getOnlyElement(Collection<T> collection) {
        if (collection.isEmpty()) throw new IllegalInputException("Collection was empty.");
        if (collection.size() > 1) throw new IllegalInputException("Collection has more than 1 item.");
        return collection.iterator().next();
    }
    
}
