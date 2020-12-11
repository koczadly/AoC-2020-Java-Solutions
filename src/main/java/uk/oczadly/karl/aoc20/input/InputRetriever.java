package uk.oczadly.karl.aoc20.input;

/**
 * @author Karl Oczadly
 */
public interface InputRetriever {

    InputData fetchInput(int day) throws InputRetrievalException;

}
