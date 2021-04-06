package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.Map;

/**
 * Main class for drawing GUI.
 * <p>
 * This class sets the appearance of application.
 * It has no information about internal state of Text Editor.
 * </p>
 */
public class TextEditor extends JFrame implements View {
    private static final Dimension BUTTON_DIMENSION = new Dimension(26, 26);
    private static final Dimension FIELD_DIMENSION = new Dimension(27, 27);
    private static final Path IMAGE_ROOT = Path.of("/Users/alexeyshik/IdeaProjects/Text Editor/Text Editor/task/src/editor/images");

    private static String resolveImage(final String imageName) {
        return IMAGE_ROOT.resolve(imageName).toString();
    }

    private static final Map<String, String> NAME_TO_PATH = Map.of(
            "OpenButton", resolveImage("open-button.png"),
            "SaveButton", resolveImage("save-button.png"),
            "StartSearchButton", resolveImage("search-button.png"),
            "PreviousMatchButton", resolveImage("left-arrow-button.png"),
            "NextMatchButton", resolveImage("right-arrow-button.png")
    );

    private static final Map<String, String> NAME_TO_TEXT = Map.of(
            "MenuFile", "File",
            "MenuSearch", "Search",
            "MenuOpen", "Open",
            "MenuSave", "Save",
            "MenuExit", "Exit",
            "MenuStartSearch", "Start search",
            "MenuPreviousMatch", "Previous match",
            "MenuNextMatch", "Next match",
            "MenuUseRegExp", "Use regular expressions"
    );

    private final ActionListener actionSaver = event -> getController().onSave();
    private final ActionListener actionOpener = event -> getController().onOpen();
    private final ActionListener actionSearcher = event -> getController().onSearch();
    private final ActionListener actionPrevMatch = event -> getController().onPrevMatch();
    private final ActionListener actionNextMatch = event -> getController().onNextMatch();
    private final ActionListener actionExit = event -> getController().onExit();
    private final ActionListener actionCheckBoxClick = event -> getController().onCheckBoxClick();

    private final Controller controller;

    private Controller getController() {
        if (controller == null) {
            throw new IllegalStateException("Controller is not initialized");
        }
        return controller;
    }

    private static <T extends AbstractButton> T initAbstractButton(final T button, final String name,
                                                                   final ActionListener actionListener) {
        button.setName(name);
        button.addActionListener(actionListener);
        return button;
    }

    private static JButton initButton(final String name, final ActionListener actionListener) {
        final JButton button = new JButton(new ImageIcon(NAME_TO_PATH.get(name)));
        button.setPreferredSize(BUTTON_DIMENSION);
        return initAbstractButton(button, name, actionListener);
    }

    private static void addMenuItem(final JMenu menu, final String name, final ActionListener actionListener) {
        final JMenuItem item = new JMenuItem();
        item.setText(NAME_TO_TEXT.get(name));
        menu.add(initAbstractButton(item, name, actionListener));
    }

    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initFileChooser() {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");
        fileChooser.setVisible(false);
        add(fileChooser, BorderLayout.PAGE_END);
        getController().setFileChooser(fileChooser);
    }

    private void initTextArea() {
        final JPanel textPanel = new JPanel();

        final JTextArea textArea = new JTextArea(11, 35);
        textArea.setName("TextArea");

        final JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        textPanel.add(scrollPane);

        add(textPanel, BorderLayout.CENTER);
        getController().setTextArea(textArea);
    }

    private JTextField initSearchField() {
        final JTextField searchField = new JTextField(15);
        searchField.setPreferredSize(FIELD_DIMENSION);
        searchField.setName("SearchField");
        getController().setSearchField(searchField);
        return searchField;
    }

    private JCheckBox initCheckBox() {
        final JCheckBox regexCheckBox = new JCheckBox("Use regex");
        regexCheckBox.setName("UseRegExCheckbox");
        getController().setCheckbox(regexCheckBox);
        return regexCheckBox;
    }

    private static JMenu initMenu(final String name) {
        final JMenu menu = new JMenu(NAME_TO_TEXT.get(name));
        menu.setName(name);
        return menu;
    }

    private void initPanel(final JTextField searchField, final JCheckBox regexCheckBox) {
        final JPanel controllerPanel = new JPanel(new FlowLayout());
        controllerPanel.add(initButton("SaveButton", actionSaver));
        controllerPanel.add(initButton("OpenButton", actionOpener));
        controllerPanel.add(searchField);
        controllerPanel.add(initButton("StartSearchButton", actionSearcher));
        controllerPanel.add(initButton("PreviousMatchButton", actionPrevMatch));
        controllerPanel.add(initButton("NextMatchButton", actionNextMatch));
        controllerPanel.add(regexCheckBox);
        add(controllerPanel, BorderLayout.NORTH);
    }

    private JMenu initMenuFile() {
        final JMenu menuFile = initMenu("MenuFile");
        addMenuItem(menuFile, "MenuOpen", actionOpener);
        addMenuItem(menuFile, "MenuSave", actionSaver);
        addMenuItem(menuFile, "MenuExit", actionExit);
        return menuFile;
    }

    private JMenu initMenuSearch() {
        final JMenu menuSearch = initMenu("MenuSearch");
        addMenuItem(menuSearch, "MenuStartSearch", actionSearcher);
        addMenuItem(menuSearch, "MenuPreviousMatch", actionPrevMatch);
        addMenuItem(menuSearch, "MenuNextMatch", actionNextMatch);
        addMenuItem(menuSearch, "MenuUseRegExp", actionCheckBoxClick);
        return menuSearch;
    }

    private void initMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(initMenuFile());
        menuBar.add(initMenuSearch());
        setJMenuBar(menuBar);
    }

    /**
     * Constructs {@code TextEditor} and paired {@link TextEditorController},
     * initialized all GUI and sends main components to {@code Controller}
     */
    public TextEditor() {
        super("Text editor");
        this.controller = new TextEditorController();
        getController().setView(this);
        initFrame();
        initFileChooser();
        initTextArea();
        initSearchField();
        initPanel(initSearchField(), initCheckBox());
        initMenuBar();
        pack();
        setVisible(true);
    }

    @Override
    public void showWarning(final String message, final String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void highlightText(final JTextArea textArea, final int index, final int length) {
        if (index >= 0) {
            textArea.setCaretPosition(index + length);
            textArea.select(index, index + length);
            textArea.grabFocus();
        }
    }
}
