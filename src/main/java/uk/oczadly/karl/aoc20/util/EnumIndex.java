package uk.oczadly.karl.aoc20.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Indexes enum values by a specific property.
 * Allows for quick and dirty valueOf implementations.
 */
public class EnumIndex<E extends Enum<E>, T> {
    
    private final Map<T, E> map;
    
    public EnumIndex(Class<E> clazz, Function<E, T> mapper) {
        E[] enums = clazz.getEnumConstants();
        this.map = new HashMap<>(enums.length);
        for (E e : enums) {
            T val = mapper.apply(e);
            if (val != null && map.put(val, e) != null) {
                throw new IllegalArgumentException("Value \"" + val + "\" is applicable to more than one enum.");
            }
        }
    }
    
    
    public E valueOf(T identifier) {
        if (identifier == null) throw new NullPointerException("Identifier argument cannot be null.");
        E enumVal = map.get(identifier);
        if (enumVal == null) throw new IllegalArgumentException("No enum corresponds to the given identifier \""
                + identifier.toString() + "\".");
        return enumVal;
    }
    
    public boolean exists(T identifier) {
        if (identifier == null) throw new NullPointerException("Identifier argument cannot be null.");
        return map.containsKey(identifier);
    }
    
}
