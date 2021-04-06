package editor.search;

/**
 * This class is like neutral element for searching operation - does nothing.
 * <p>
 * It is used to avoid {@code NPE} if user clicks "{@code NextMatchButton}"
 * before "{@code StartSearchButton}"
 *</p>
 */
public class EmptySearcher implements Searchable {
    @Override
    public void findNext() {
    }

    @Override
    public void findPrev() {
    }

    @Override
    public int getPos() {
        return 0;
    }

    @Override
    public int getPatternLength() {
        return 0;
    }
}
