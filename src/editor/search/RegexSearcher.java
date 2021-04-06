package editor.search;

import editor.TextEditorController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents search strategy with regular expressions.
 * <p>
 * This strategy uses O({@code |occurrences|}) additional memory, but O({@code 1})
 * time complexity for finding next/previous occurrence and O({@code |text| + |pattern|}) preprocessing.
 * All searches happens in separate thread.
 * </p>
 */
public class RegexSearcher extends AbstractSearcher {
    private List<Integer> indexes;
    private List<Integer> lengths;
    private int indexInList;

    public RegexSearcher(final String text, final String pattern, final TextEditorController controller) {
        super(controller);

        final SwingWorker<Void, Void> searchWorker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() {
                final Pattern regex = Pattern.compile(pattern);
                final Matcher matcher = regex.matcher(text);
                indexes = new ArrayList<>();
                lengths = new ArrayList<>();
                while (matcher.find()) {
                    indexes.add(matcher.start());
                    lengths.add(matcher.group().length());
                }
                return null;
            }

            @Override
            protected void done() {
                highlightTextArea();
            }
        };

        searchWorker.execute();
    }

    private void findByNewIndex(int newIndex) {
        indexInList = newIndex % indexes.size();
        highlightTextArea();
    }

    @Override
    public void findNext() {
        executor.execute(() -> findByNewIndex(indexInList + 1));
    }

    @Override
    public void findPrev() {
        executor.execute(() -> findByNewIndex(indexInList - 1 + indexes.size()));
    }

    private boolean inBounds() {
        return 0 <= indexInList && indexInList < indexes.size();
    }

    private int getFromOrDefault(final List<Integer> list) {
        return inBounds() ? list.get(indexInList) : 0;
    }

    @Override
    public int getPos() {
        return getFromOrDefault(indexes);
    }

    @Override
    public int getPatternLength() {
        return getFromOrDefault(lengths);
    }
}
