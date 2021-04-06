package editor.search;

import editor.TextEditorController;

import javax.swing.*;

/**
 * Represents search strategy without regular expressions.
 * <p>
 * This strategy uses O({@code 1}) additional memory, but O({@code |text| + |pattern|})
 * time complexity for finding next/previous occurrence.
 * All searches happens in separate thread.
 * </p>
 */
public class SimpleSearcher extends AbstractSearcher {
    private final String text;
    private final String pattern;
    private int pos;

    private class SearchAfterPos extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() {
            setNextOccurrence(text.indexOf(pattern, pos + 1), text.indexOf(pattern));
            return null;
        }

        @Override
        protected void done() {
            highlightTextArea();
        }
    }

    private class SearchBeforePos extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() {
            setNextOccurrence(text.lastIndexOf(pattern, pos - 1), text.lastIndexOf(pattern));
            return null;
        }

        @Override
        protected void done() {
            highlightTextArea();
        }
    }

    public SimpleSearcher(final String text, final String pattern, final TextEditorController controller) {
        super(controller);
        this.text = text;
        this.pattern = pattern;
        findNext();
    }

    private void setNextOccurrence(final int mainValue, final int defaultValue) {
        pos = mainValue == -1 ? defaultValue : mainValue;
    }

    @Override
    public void findNext() {
        executor.execute(new SearchAfterPos());
    }

    @Override
    public void findPrev() {
        executor.execute(new SearchBeforePos());
    }

    @Override
    public int getPos() {
        return pos;
    }

    @Override
    public int getPatternLength() {
        return pos == -1 ? 0 : pattern.length();
    }
}