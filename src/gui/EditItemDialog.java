/*
 * EditItemDialog.java
 * purpose: dialog window for editing existing collection items
 *          allows users to modify item properties
 * author: phin
 */

package gui;

import model.CollectionItem;
import model.Record;
import model.CD;
import model.Cassette;

import javax.swing.*;
import java.awt.*;

// dialog for editing existing items
public class EditItemDialog extends JDialog {

    // the item being edited
    private CollectionItem item;

    // tracks if changes were made
    private boolean updated = false;

    // common fields
    private JTextField titleField;
    private JTextField artistField;
    private JSpinner yearSpinner;
    private JComboBox<String> conditionCombo;

    // format-specific fields panel
    private JPanel specificFieldsPanel;

    // vinyl record specific fields
    private JComboBox<String> recordSizeCombo;
    private JComboBox<String> recordSpeedCombo;

    // cd specific fields
    private JSpinner trackCountSpinner;
    private JCheckBox hasBookletCheck;

    // cassette specific fields
    private JComboBox<String> tapeTypeCombo;
    private JSpinner tapeLengthSpinner;

    // constructor takes the item to edit
    public EditItemDialog(JFrame parent, CollectionItem item) {
        super(parent, "Edit Item", true);
        this.item = item;

        setSize(400, 400);
        setLocationRelativeTo(parent);
        setResizable(false);

        initializeComponents();
        populateFields();
    }

    // builds the ui components
    private void initializeComponents() {
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // format label (not editable - can't change type)
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Format:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        JLabel formatLabel = new JLabel(item.getMediaType());
        formatLabel.setFont(formatLabel.getFont().deriveFont(Font.BOLD));
        formPanel.add(formatLabel, gbc);
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
        // goldmine grading scale
        conditionCombo = new JComboBox<>(new String[]{"M", "NM", "EX", "VG+", "VG", "G+", "G", "F", "P"});
        formPanel.add(conditionCombo, gbc);
        gbc.weightx = 0;
        row++;

        // separator
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        formPanel.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;
        row++;

        // format-specific fields
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        specificFieldsPanel = createSpecificFieldsPanel();
        formPanel.add(specificFieldsPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(new Color(0, 184, 148));
        saveButton.addActionListener(e -> saveChanges());
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // creates the appropriate panel based on item type
    private JPanel createSpecificFieldsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // check what type of item we're editing and show appropriate fields
        if (item instanceof Record) {
            // record fields
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Size:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            recordSizeCombo = new JComboBox<>(new String[]{"12\"", "10\"", "7\""});
            panel.add(recordSizeCombo, gbc);

            gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
            panel.add(new JLabel("Speed:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            recordSpeedCombo = new JComboBox<>(new String[]{"33", "45", "78"});
            panel.add(recordSpeedCombo, gbc);

        } else if (item instanceof CD) {
            // cd fields
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Track Count:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            SpinnerNumberModel trackModel = new SpinnerNumberModel(10, 1, 50, 1);
            trackCountSpinner = new JSpinner(trackModel);
            panel.add(trackCountSpinner, gbc);

            gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
            panel.add(new JLabel("Has Booklet:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            hasBookletCheck = new JCheckBox();
            panel.add(hasBookletCheck, gbc);

        } else if (item instanceof Cassette) {
            // cassette fields
            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Tape Type:"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            tapeTypeCombo = new JComboBox<>(new String[]{"Normal", "Chrome", "Metal"});
            panel.add(tapeTypeCombo, gbc);

            gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
            panel.add(new JLabel("Length (min):"), gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            SpinnerNumberModel lengthModel = new SpinnerNumberModel(60, 30, 120, 10);
            tapeLengthSpinner = new JSpinner(lengthModel);
            panel.add(tapeLengthSpinner, gbc);
        }

        return panel;
    }

    // fills in the current values
    private void populateFields() {
        titleField.setText(item.getTitle());
        artistField.setText(item.getArtist());
        yearSpinner.setValue(item.getYear());
        conditionCombo.setSelectedItem(item.getCondition());

        // populate format-specific fields
        if (item instanceof Record) {
            Record record = (Record) item;
            recordSizeCombo.setSelectedItem(record.getSize());
            recordSpeedCombo.setSelectedItem(record.getSpeed());

        } else if (item instanceof CD) {
            CD cd = (CD) item;
            trackCountSpinner.setValue(cd.getTrackCount());
            hasBookletCheck.setSelected(cd.hasBooklet());

        } else if (item instanceof Cassette) {
            Cassette cassette = (Cassette) item;
            tapeTypeCombo.setSelectedItem(cassette.getTapeType());
            tapeLengthSpinner.setValue(cassette.getLength());
        }
    }

    // saves the changes to the item
    private void saveChanges() {
        // validate
        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();

        if (title.isEmpty() || artist.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in both title and artist fields.",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // update common fields
        item.setTitle(title);
        item.setArtist(artist);
        item.setYear((Integer) yearSpinner.getValue());
        item.setCondition((String) conditionCombo.getSelectedItem());

        // update format-specific fields
        if (item instanceof Record) {
            Record record = (Record) item;
            record.setSize((String) recordSizeCombo.getSelectedItem());
            record.setSpeed((String) recordSpeedCombo.getSelectedItem());

        } else if (item instanceof CD) {
            CD cd = (CD) item;
            cd.setTrackCount((Integer) trackCountSpinner.getValue());
            cd.setHasBooklet(hasBookletCheck.isSelected());

        } else if (item instanceof Cassette) {
            Cassette cassette = (Cassette) item;
            cassette.setTapeType((String) tapeTypeCombo.getSelectedItem());
            cassette.setLength((Integer) tapeLengthSpinner.getValue());
        }

        updated = true;
        dispose();
    }

    // returns whether changes were made
    public boolean wasUpdated() {
        return updated;
    }
}
