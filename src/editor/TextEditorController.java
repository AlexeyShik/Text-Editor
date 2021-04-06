package editor;


import editor.search.EmptySearcher;
import editor.search.RegexSearcher;
import editor.search.Searchable;
import editor.search.SimpleSearcher;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Main logic class.
 * <p>
 * This class process all user clicks,
 * changes internal state of GUI components and delegates to {@link TextEditor} drawing GUI
 * </p>
 */
public class TextEditorController implements Controller {
    private View view;
    private JTextArea textArea;
    private JTextField searchField;
    private JCheckBox regexpCheckBox;
    private JFileChooser fileChooser;
    private Searchable searcher = new EmptySearcher();

    private View getView() {
        if (view == null) {
            throw new IllegalStateException("View is not initialized");
        }
        return view;
    }

    @Override
    public void setView(final View view) {
        this.view = view;
    }

    @Override
    public void setTextArea(final JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void setCheckbox(final JCheckBox regexpCheckBox) {
        this.regexpCheckBox = regexpCheckBox;
    }

    @Override
    public void setSearchField(final JTextField searchField) {
        this.searchField = searchField;
    }

    @Override
    public void setFileChooser(final JFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    /**
     * Saving process is background, because {@code TextArea} can contain large content
     * and GUI shouldn't freeze while it is writing to file
     */
    @Override
    public void onSave() {
        final SwingWorker<Void, Void> onSaveWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                fileChooser.setVisible(true);
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    final File file = fileChooser.getSelectedFile();
                    if (file != null) {
                        try {
                            if (!file.exists() && !file.createNewFile()) {
                                getView().showWarning("The given file: " + file.getPath() +
                                        " cannot be saved", "Save file");
                                return null;
                            }
                            Files.write(file.toPath(), textArea.getText().getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            fileChooser.setVisible(false);
                        }
                    }
                }
                return null;
            }
        };

        onSaveWorker.execute();
    }

    /**
     * Opening process is background, because file can be large
     * and GUI shouldn't freeze while it is reading
     */
    @Override
    public void onOpen() {
        final SwingWorker<Void, Void> onOpenWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                fileChooser.setVisible(true);
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    final File file = fileChooser.getSelectedFile();
                    if (file != null) {
                        final Path path = file.toPath();
                        if (Files.isRegularFile(path)) {
                            try {
                                textArea.setText(Files.exists(path) ? Files.readString(path) : "");
                            } catch (IOException e) {
                                e.printStackTrace();
                                textArea.setText("");
                            } finally {
                                fileChooser.setVisible(false);
                            }
                        } else {
                            getView().showWarning("The given file: " + path +
                                            " is not a regular file. Please, choose the regular one",
                                    "Open file");
                        }
                    }
                }
                return null;
            }
        };

        onOpenWorker.execute();
    }

    @Override
    public void onCheckBoxClick() {
        regexpCheckBox.setSelected(!regexpCheckBox.isSelected());
    }

    @Override
    public void onExit() {
        getView().dispose();
    }

    @Override
    public void onSearch() {
        searcher = regexpCheckBox.isSelected()
                ? new RegexSearcher(textArea.getText(), searchField.getText(), this)
                : new SimpleSearcher(textArea.getText(), searchField.getText(), this);
    }

    /**
     * Invokes {@link TextEditor#highlightText(JTextArea, int, int)}
     *
     * @param index begin index of current occurrence
     * @param length length of current occurrence
     */
    public void highlightText(final int index, final int length) {
        view.highlightText(textArea, index, length);
    }

    @Override
    public void onPrevMatch() {
        searcher.findPrev();
    }

    @Override
    public void onNextMatch() {
        searcher.findNext();
    }
}
