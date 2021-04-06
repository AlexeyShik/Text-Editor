package editor;

import javax.swing.*;

/**
 * GUI of Text Editor
 * <p>
 * When {@link Controller} wants to display something, it should use this methods
 * </p>
 */
public interface View {

    /**
     * Shows user-friendly warning if something goes wrong, instead of throwing exception
     *
     * @param message explanation of happened problem
     * @param title context name
     */
    void showWarning(final String message, final String title);

    /**
     * Highlights one of occurrences given pattern in textArea
     *
     * @param textArea text for highlighting occurrence
     * @param index index in text, when occurrence begins
     * @param length length of occurrence
     */
    void highlightText(final JTextArea textArea, final int index, final int length);

    /**
     * Safe close frame and remove all resources it is using
     *
     * @see JFrame#dispose()
     */
    void dispose();
}
