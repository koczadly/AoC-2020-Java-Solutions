package uk.oczadly.karl.aoc20.input;

import java.io.IOException;
import java.net.URL;

/**
 * @author Karl Oczadly
 */
public class ResourceInputRetriever implements InputRetriever {
    
    private final String dirs;
    
    public ResourceInputRetriever(String parentDirs) {
        this.dirs = parentDirs;
    }
    
    
    @Override
    public PuzzleInput fetchInput(int day) {
        URL resource = PuzzleInput.class.getClassLoader().getResource(dirs + "/day" + day + ".txt");
        if (resource == null)
            throw new InputRetrievalException("Input resource not found.");
        try {
            return new PuzzleInput(resource.openStream());
        } catch (IOException e) {
            throw new InputRetrievalException(e);
        }
    }
    
}
