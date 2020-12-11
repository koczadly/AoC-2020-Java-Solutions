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
    public InputData forDay(int day) throws InputRetrievalException {
        URL resource = InputData.class.getClassLoader().getResource(dirs + "/day" + day + ".txt");
        if (resource == null)
            throw new InputRetrievalException("Input resource not found.");
        try {
            return new InputData(resource.openStream());
        } catch (IOException e) {
            throw new InputRetrievalException(e);
        }
    }
    
}
