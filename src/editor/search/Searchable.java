package editor.search;

/**
 * Common contract for all search strategies
 */
public interface Searchable {
    /**
     * Finds next occurrence of pattern in text
     */
    void findNext();

    /**
     * Finds previous occurrence of pattern in text
     */
    void findPrev();

    /**
     * Gets index in text of current occurrence
     * @return index in text of current occurrence of pattern in text
     */
    int getPos();

    /**
     * Gets length of current occurrence
     * @return length of current occurrence of pattern in text
     */
    int getPatternLength();
}
