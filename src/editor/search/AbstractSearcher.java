package editor.search;

import editor.TextEditorController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Abstract searching strategy.
 * <p>
 * This class delegates highlighting occurrence
 * to {@link TextEditorController} that will delegate it to {@link editor.TextEditor}.
 * </p>
 */
abstract class AbstractSearcher implements Searchable {
    private final TextEditorController controller;

    /**
     * {@link ExecutorService} is used to avoid problems like: User fast clicks two buttons
     * in order "{@code PreviousMatchButton}" then "{@code NextMatchButton}", but the
     * "{@code NextMatchButton}" ends earlier and user sees inverted order of operations.
     */
    protected final ExecutorService executor;

    AbstractSearcher(final TextEditorController controller) {
        this.controller = controller;
        this.executor = Executors.newSingleThreadExecutor();
    }

    protected synchronized void highlightTextArea() {
        controller.highlightText(getPos(), getPatternLength());
    }
}
