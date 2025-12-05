/*
 * AddItemDialog.java
 * purpose: dialog window for adding new items to the collection
 *          uses the factory pattern to create appropriate item types
 * author: phin
 */

package gui;

import factory.ItemFactory;
import model.CollectionItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

// dialog for adding new items - demonstrates factory pattern usage
public class AddItemDialog extends JDialog {

    // the item that was created (null if cancelled)
    private CollectionItem createdItem;

    // common fields
    private JTextField titleField;
    private JTextField artistField;
    private JSpinner yearSpinner;
    private JComboBox<String> conditionCombo;
    private JComboBox<String> mediaTypeCombo;

    // panels for format-specific options
    private JPanel specificOptionsPanel;
    private CardLayout cardLayout;

    // vinyl record specific fields
    private JComboBox<String> recordSizeCombo;
    private JComboBox<String> recordSpeedCombo;

    // cd specific fields
    private JSpinner trackCountSpinner;
    private JCheckBox hasBookletCheck;

    // cassette specific fields
    private JComboBox<String> tapeTypeCombo;
    private JSpinner tapeLengthSpinner;

    // constructor sets up the dialog
    public AddItemDialog(JFrame parent) {
        super(parent, "Add New Item", true); // modal dialog
        setSize(450, 450);
        setLocationRelativeTo(parent);
        setResizable(false);

        initializeComponents();
    }

    // builds all the ui components
    private void initializeComponents() {
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // main form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // media type selection - this drives which factory method we use
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Format:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        mediaTypeCombo = new JComboBox<>(new String[]{"Vinyl Record", "CD", "Cassette"});
        mediaTypeCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // switch to the appropriate options panel
                cardLayout.show(specificOptionsPanel, (String) e.getItem());
            }
        });
        formPanel.add(mediaTypeCombo, gbc);
        gbc.weightx = 0;
        row++;

        // artist field (first, to match table column order)
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Artist:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        artistField = new JTextField(20);
        formPanel.add(artistField, gbc);
        gbc.weightx = 0;
        row++;

        // title field
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        titleField = new JTextField(20);
        formPanel.add(titleField, gbc);
        gbc.weightx = 0;
        row++;

        // year spinner
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        SpinnerNumberModel yearModel = new SpinnerNumberModel(2025, 1900, 2030, 1);
        yearSpinner = new JSpinner(yearModel);
        JSpinner.NumberEditor yearEditor = new JSpinner.NumberEditor(yearSpinner, "#");
        yearSpinner.setEditor(yearEditor);
        formPanel.add(yearSpinner, gbc);
        gbc.weightx = 0;
        row++;

        // condition dropdown
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Condition:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        // goldmine grading scale: M (mint), NM (near mint), EX (excellent), VG+ (very good plus),
        // VG (very good), G+ (good plus), G (good), F (fair), P (poor)
        conditionCombo = new JComboBox<>(new String[]{"M", "NM", "EX", "VG+", "VG", "G+", "G", "F", "P"});
        formPanel.add(conditionCombo, gbc);
        gbc.weightx = 0;
        row++;

        // separator before format-specific options
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        formPanel.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;
        row++;

        // format-specific options label
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        JLabel specificLabel = new JLabel("Format-Specific Options:");
        specificLabel.setFont(specificLabel.getFont().deriveFont(Font.BOLD));
        formPanel.add(specificLabel, gbc);
        gbc.gridwidth = 1;
        row++;

        // card layout panel for format-specific options
        cardLayout = new CardLayout();
        specificOptionsPanel = new JPanel(cardLayout);

        // add panels for each format type
        specificOptionsPanel.add(createRecordOptionsPanel(), "Vinyl Record");
        specificOptionsPanel.add(createCDOptionsPanel(), "CD");
        specificOptionsPanel.add(createCassetteOptionsPanel(), "Cassette");

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        formPanel.add(specificOptionsPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // button panel at bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        JButton addButton = new JButton("Add Item");
        addButton.setBackground(new Color(0, 184, 148));
        addButton.addActionListener(e -> createItem());
        buttonPanel.add(addButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // creates the options panel for vinyl records
    private JPanel createRecordOptionsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // record size
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Size:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        recordSizeCombo = new JComboBox<>(new String[]{"12\"", "10\"", "7\""});
        panel.add(recordSizeCombo, gbc);

        // record speed
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panel.add(new JLabel("Speed:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        recordSpeedCombo = new JComboBox<>(new String[]{"33", "45", "78"});
        panel.add(recordSpeedCombo, gbc);

        return panel;
    }

    // creates the options panel for cds
    private JPanel createCDOptionsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // track count
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Track Count:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        SpinnerNumberModel trackModel = new SpinnerNumberModel(10, 1, 50, 1);
        trackCountSpinner = new JSpinner(trackModel);
        panel.add(trackCountSpinner, gbc);

        // has booklet checkbox
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panel.add(new JLabel("Has Booklet:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        hasBookletCheck = new JCheckBox();
        hasBookletCheck.setSelected(true);
        panel.add(hasBookletCheck, gbc);

        return panel;
    }

    // creates the options panel for cassettes
    private JPanel createCassetteOptionsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // tape type
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Tape Type:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tapeTypeCombo = new JComboBox<>(new String[]{"Normal", "Chrome", "Metal"});
        panel.add(tapeTypeCombo, gbc);

        // tape length
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panel.add(new JLabel("Length (min):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        SpinnerNumberModel lengthModel = new SpinnerNumberModel(60, 30, 120, 10);
        tapeLengthSpinner = new JSpinner(lengthModel);
        panel.add(tapeLengthSpinner, gbc);

        return panel;
    }

    // validates input and creates the item using the factory
    private void createItem() {
        // validate required fields
        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();

        if (title.isEmpty() || artist.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in both title and artist fields.",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // get common field values
        int year = (Integer) yearSpinner.getValue();
        String condition = (String) conditionCombo.getSelectedItem();
        String mediaType = (String) mediaTypeCombo.getSelectedItem();

        // use the factory to create the appropriate item type
        // this is where the factory pattern really shines!
        switch (mediaType) {
            case "Vinyl Record":
                String size = (String) recordSizeCombo.getSelectedItem();
                String speed = (String) recordSpeedCombo.getSelectedItem();
                createdItem = ItemFactory.createRecord(title, artist, year, condition, size, speed);
                break;

            case "CD":
                int trackCount = (Integer) trackCountSpinner.getValue();
                boolean hasBooklet = hasBookletCheck.isSelected();
                createdItem = ItemFactory.createCD(title, artist, year, condition, trackCount, hasBooklet);
                break;

            case "Cassette":
                String tapeType = (String) tapeTypeCombo.getSelectedItem();
                int length = (Integer) tapeLengthSpinner.getValue();
                createdItem = ItemFactory.createCassette(title, artist, year, condition, tapeType, length);
                break;
        }

        dispose();
    }

    // returns the item that was created (null if dialog was cancelled)
    public CollectionItem getCreatedItem() {
        return createdItem;
    }
}
