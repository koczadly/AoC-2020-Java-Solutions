package uk.oczadly.karl.aoc20.input;

/**
 * @author Karl Oczadly
 */
public interface InputRetriever {

    InputData forDay(int day) throws InputRetrievalException;

}
