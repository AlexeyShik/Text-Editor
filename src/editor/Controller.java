package editor;

import javax.swing.*;

/**
 * Logic of Text Editor
 * <p>
 * When happens some action from GUI,
 * {@link View} should use this methods to change internal state of Text Editor
 * and process corresponding actions
 * </p>
 */
public interface Controller {

    /**
     * Set {@link View} instance to {@code this} {@link Controller}
     *
     * @param view instance of {@code View}
     */
    void setView(final View view);

    /**
     * Action when user clicks "{@code SaveButton}" or "{@code MenuOpen}" item
     */
    void onSave();

    /**
     * Action when user clicks "{@code OpenButton}" or "{@code MenuOpen}" item
     */
    void onOpen();

    /**
     * Action when user clicks "{@code UseRegExCheckbox}" button or "{@code MenuUseRegExp}" item
     */
    void onCheckBoxClick();

    /**
     * Action when user clicks "{@code MenuExit}" item
     */
    void onExit();

    /**
     * Action when user clicks "{@code StartSearchButton}" or "{@code MenuStartSearch}" item
     */
    void onSearch();

    /**
     * Action when user clicks "{@code PreviousMatchButton}" or "{@code MenuPreviousMatch}" item
     */
    void onPrevMatch();

    /**
     * Action when user clicks "{@code MenuNextMatch}" or "{@code NextMatchButton}" item
     */
    void onNextMatch();

    /**
     * Sets {@link JFileChooser} to {@link Controller} instance
     *
     * @param fileChooser instance of {@code JFileChooser}
     */
    void setFileChooser(final JFileChooser fileChooser);

    /**
     * Sets {@link JTextArea} to {@link Controller} instance
     *
     * @param textArea instance of {@code JTextArea}
     */
    void setTextArea(final JTextArea textArea);

    /**
     * Sets {@link JCheckBox} to {@link Controller} instance
     *
     * @param regexpCheckBox instance of {@code JCheckBox}
     */
    void setCheckbox(final JCheckBox regexpCheckBox);

    /**
     * Sets {@link JTextField} to {@link Controller} instance
     *
     * @param searchField instance of {@code JTextField}
     */
    void setSearchField(final JTextField searchField);
}
