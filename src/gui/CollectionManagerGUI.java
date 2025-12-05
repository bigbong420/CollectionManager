/*
 * CollectionManagerGUI.java
 * purpose: main application window for the music collection manager
 *          provides ui for viewing, adding, and sorting collection items
 *          showcases factory and strategy patterns in action
 * author: phin
 */

package gui;

import model.CollectionItem;
import strategy.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// main window class - this is where users interact with their collection
public class CollectionManagerGUI extends JFrame {

    // our data store - holds all the collection items
    private List<CollectionItem> collection;

    // current sorting strategy - can be swapped at runtime (strategy pattern!)
    private SortStrategy currentStrategy;

    // all available sorting strategies
    private List<SortStrategy> sortStrategies;

    // ui components we need to access later
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> sortComboBox;
    private JLabel statusLabel;
    private JTextField searchField;

    // table column names
    private final String[] columnNames = {"Artist", "Title", "Year", "Condition", "Format", "Details"};

    // constructor sets up the whole ui
    public CollectionManagerGUI() {
        // initialize data structures
        collection = new ArrayList<>();
        initializeSortStrategies();

        // set up the main window
        setTitle("Music Collection Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null); // center on screen

        // build the ui
        initializeComponents();

        // make it visible
        setVisible(true);
    }

    // sets up all the available sort strategies
    private void initializeSortStrategies() {
        sortStrategies = new ArrayList<>();
        sortStrategies.add(new SortByArtist());
        sortStrategies.add(new SortByTitle());
        sortStrategies.add(new SortByYear());
        sortStrategies.add(new SortByCondition());
        sortStrategies.add(new SortByMediaType());

        // default to sort by artist
        currentStrategy = sortStrategies.get(0);
    }

    // builds all the ui components
    private void initializeComponents() {
        // use borderlayout for main structure
        setLayout(new BorderLayout(10, 10));

        // create the header panel with title and stats
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // create the main table area
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // create the control panel (buttons and sort options)
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);

        // add some padding around the edges
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // creates the top header with title and collection stats
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(45, 52, 54));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // main title
        JLabel titleLabel = new JLabel("Music Collection Manager");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.WEST);

        // search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setForeground(Color.WHITE);
        searchPanel.add(searchLabel);

        searchField = new JTextField(20);
        searchField.addActionListener(e -> filterTable());
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Filter");
        searchButton.addActionListener(e -> filterTable());
        searchPanel.add(searchButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            searchField.setText("");
            refreshTable();
        });
        searchPanel.add(clearButton);

        panel.add(searchPanel, BorderLayout.EAST);

        return panel;
    }

    // creates the main table for displaying items
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // create table model - not editable directly
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // no direct editing, use edit dialog
            }
        };

        // create the table
        itemTable = new JTable(tableModel);
        itemTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        itemTable.setRowHeight(25);
        itemTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // set column widths
        itemTable.getColumnModel().getColumn(0).setPreferredWidth(120); // artist
        itemTable.getColumnModel().getColumn(1).setPreferredWidth(150); // title
        itemTable.getColumnModel().getColumn(2).setPreferredWidth(60);  // year
        itemTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // condition
        itemTable.getColumnModel().getColumn(4).setPreferredWidth(100); // format
        itemTable.getColumnModel().getColumn(5).setPreferredWidth(150); // details

        // wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(itemTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // creates the bottom control panel
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // left side - add/edit/delete buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addButton = new JButton("Add Item");
        addButton.setBackground(new Color(0, 184, 148));
        addButton.addActionListener(e -> showAddItemDialog());
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit Item");
        editButton.addActionListener(e -> showEditItemDialog());
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Item");
        deleteButton.setBackground(new Color(255, 118, 117));
        deleteButton.addActionListener(e -> deleteSelectedItem());
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.WEST);

        // right side - sort options
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel sortLabel = new JLabel("Sort by: ");
        sortPanel.add(sortLabel);

        // populate combo box with strategy names
        String[] strategyNames = new String[sortStrategies.size()];
        for (int i = 0; i < sortStrategies.size(); i++) {
            strategyNames[i] = sortStrategies.get(i).getStrategyName();
        }
        sortComboBox = new JComboBox<>(strategyNames);
        sortComboBox.addActionListener(e -> {
            // change strategy based on selection - this is the strategy pattern in action!
            int selectedIndex = sortComboBox.getSelectedIndex();
            currentStrategy = sortStrategies.get(selectedIndex);
            sortCollection();
        });
        sortPanel.add(sortComboBox);

        panel.add(sortPanel, BorderLayout.EAST);

        // status bar at the bottom
        statusLabel = new JLabel("Ready - 0 items in collection");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        panel.add(statusLabel, BorderLayout.SOUTH);

        return panel;
    }

    // shows the dialog for adding a new item
    private void showAddItemDialog() {
        AddItemDialog dialog = new AddItemDialog(this);
        dialog.setVisible(true);

        // if user added an item, refresh the table
        if (dialog.getCreatedItem() != null) {
            collection.add(dialog.getCreatedItem());
            sortCollection();
            updateStatus("Added: " + dialog.getCreatedItem().getTitle());
        }
    }

    // shows dialog for editing selected item
    private void showEditItemDialog() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select an item to edit.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        CollectionItem item = collection.get(selectedRow);
        EditItemDialog dialog = new EditItemDialog(this, item);
        dialog.setVisible(true);

        if (dialog.wasUpdated()) {
            sortCollection();
            updateStatus("Updated: " + item.getTitle());
        }
    }

    // deletes the currently selected item
    private void deleteSelectedItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select an item to delete.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        CollectionItem item = collection.get(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete '" + item.getTitle() + "' by " + item.getArtist() + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            collection.remove(selectedRow);
            refreshTable();
            updateStatus("Deleted: " + item.getTitle());
        }
    }

    // applies the current sort strategy to the collection
    private void sortCollection() {
        if (!collection.isEmpty()) {
            // this is where strategy pattern shines - just call sort on whatever strategy
            currentStrategy.sort(collection);
        }
        refreshTable();
    }

    // filters table based on search text
    private void filterTable() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.isEmpty()) {
            refreshTable();
            return;
        }

        // clear and repopulate with matching items
        tableModel.setRowCount(0);
        int matchCount = 0;
        for (CollectionItem item : collection) {
            // search across all fields
            if (item.getTitle().toLowerCase().contains(searchText) ||
                item.getArtist().toLowerCase().contains(searchText) ||
                item.getMediaType().toLowerCase().contains(searchText) ||
                String.valueOf(item.getYear()).contains(searchText)) {

                addItemToTable(item);
                matchCount++;
            }
        }
        updateStatus("Found " + matchCount + " matching items");
    }

    // refreshes the table with current collection data
    private void refreshTable() {
        tableModel.setRowCount(0); // clear existing rows
        for (CollectionItem item : collection) {
            addItemToTable(item);
        }
        updateStatus("Showing " + collection.size() + " items");
    }

    // adds a single item to the table
    private void addItemToTable(CollectionItem item) {
        Object[] rowData = {
            item.getArtist(),
            item.getTitle(),
            item.getYear(),
            item.getCondition(),
            item.getMediaType(),
            item.getFormatDetails()
        };
        tableModel.addRow(rowData);
    }

    // updates the status bar
    private void updateStatus(String message) {
        statusLabel.setText(message + " - " + collection.size() + " items in collection");
    }

    // allows external classes to add items (useful for testing)
    public void addItem(CollectionItem item) {
        collection.add(item);
        sortCollection();
    }

    // get the collection (useful for testing)
    public List<CollectionItem> getCollection() {
        return collection;
    }

    // set the sort strategy programmatically (useful for testing)
    public void setSortStrategy(SortStrategy strategy) {
        this.currentStrategy = strategy;
        sortCollection();
    }
}
