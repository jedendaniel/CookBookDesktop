/**
 * CookBook
 *
 * @author Daniel Koźlik
 * @version 1.0
 */
package pl.polsl.java.lab.Daniel.Kozlik;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import javax.swing.table.DefaultTableModel;

/**
 * Class CookBookView ensure read and write data in application.
 *
 * @author Daniel Koźlik
 * @version 1.0
 */
public class CookBookView extends JFrame implements ActionListener{

    /**
     * Internal frame used to display exact menu
     */
    private JInternalFrame internalFrame;
    /**
     * Listener created and handled in controller.
     */
    private ActionListener customListener;
    
    /**
     * New recipe menu components which events are handled in controller.
     */
    private JButton newRecipeSaveButton;
    private JTextField newRecipeNameTextField;
    private JTextField newIngredientTextField;
    private JList newRecipeList;
    private DefaultListModel newRecipelistModel;
    private JButton searchByNameRemoveButton;
    
    /**
     * Search by name menu components which events are handled in 
     * controller.
     */
    private JButton searchByNameButton;
    private JTextField searchByNameTextField;
    private DefaultListModel searchByNameListModel;
    
    /**
     * Search by ingredient menu components which events are handled in 
     * controller.
     */
    private JButton searchByIngredientAddButton;
    private JTextField searchByIngredientTextField;
    private JTable searchByIngredientTable;
    private List<String> enteredIngredientsTab;
    private DefaultTableModel searchByIngredientModel;
   
    /**
     * CookBookView constructor. Create window with MenuBar, ToolBar and 
     * internal frame with new recipe menu.
     * @param listener created and handled in controller listener. 
     * .
     */
    public CookBookView(ActionListener listener) {
                
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("CookBook");
        
        internalFrame = new JInternalFrame();
        customListener = listener;
        
        createMenuBar();
        createToolBar();        
        createNewRecipeFrame();
        
        this.setResizable(false);
        this.setVisible(true);
    } // constructior end
    
    /**
     * Override ActionListener method handle events which are not necessary to
     * be handle in controller.
     * @param e handled event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "createNewRecipePane":
                try {
                    internalFrame.setClosed(true);
                } catch (PropertyVetoException ex) {
                    System.err.println("Closing Exception");
                } 
                createNewRecipeFrame();
                SwingUtilities.updateComponentTreeUI(this);
                break;
            case "createSearchByNamePane":
                try {
                    internalFrame.setClosed(true);
                } catch (PropertyVetoException ex) {
                    System.err.println("Closing Exception");
                } 
                createSearchByNameFrame();
                break;
            case "createSearchByIngredientPane":
                try {
                    internalFrame.setClosed(true);
                } catch (PropertyVetoException ex) {
                    System.err.println("Closing Exception");
                }   
                createSearchByIngredientFrame();
                break;
            case "clearNewRecipePane":
                newRecipeNameTextField.setText(null);
                newIngredientTextField.setText(null);
                newRecipelistModel.removeAllElements();
                break;
            case "newRecipeAdd":
                String removed = newIngredientTextField.getText().trim();
                if(!removed.isEmpty()){
                newRecipelistModel.addElement(newIngredientTextField.getText());
                newIngredientTextField.setText(null);
                }
                break;
            case "createNewRecipePane1":
                this.setVisible(false);
                break;
            case "exit":
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                break;
            case "newRecipeRemove":
                if(newRecipelistModel.size() > 0)
                    newRecipelistModel.remove(newRecipeList.getSelectedIndex());
                break;
            case "about":
                String text = new String("CookBook version 1.0\n"
                        + "Author: Daniel Koźlik\n"
                        + "\nCookBook application is a simple data base.        \n"
                        + "You can add recipes and thier ingredients and        \n"
                        + "if you need, find them searching by recipe       \n"
                        + "name or ingredients. \n\n");
                this.showMessage("Info", text);
                break;
        }      

    } // actionPreformed end
    
    /**
     * Custom listener setter.
     * @param listener set listener
     */
    public void addCustomListener(ActionListener listener){
        customListener = listener;
    }
       
    /**
     * Creating MenuBar.
     */
    private void createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
        
        JMenuItem newRecipeMenuItem = new JMenuItem("New recipe");
        newRecipeMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        
        fileMenu.add(newRecipeMenuItem);
        newRecipeMenuItem.addActionListener(this);
        newRecipeMenuItem.setActionCommand("createNewRecipePane");
        
        JMenuItem searchByNameMenuItem = new JMenuItem("Search by name");
        searchByNameMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileMenu.add(searchByNameMenuItem);
        searchByNameMenuItem.addActionListener(this);
        searchByNameMenuItem.setActionCommand("createSearchByNamePane");
        
        JMenuItem searchByIngredientMenuItem = new JMenuItem("Search by ingredient");
        searchByIngredientMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                            KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        fileMenu.add(searchByIngredientMenuItem);
        searchByIngredientMenuItem.addActionListener(this);
        searchByIngredientMenuItem.setActionCommand("createSearchByIngredientPane");
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(this);
        exitMenuItem.setActionCommand("exit");                
        
        JMenu helpMenu = new JMenu("Help");
        fileMenu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(helpMenu);
        
        JMenuItem aboutMenuItem = new JMenuItem("About");
        helpMenu.add(aboutMenuItem);
        aboutMenuItem.addActionListener(this);
        aboutMenuItem.setActionCommand("about");
        
        this.setJMenuBar(menuBar);
    }
    
    /**
     * Creating ToolBar
     */
    private void createToolBar(){
        JToolBar toolBar = new JToolBar();
        toolBar.setRollover(true);
        JButton Button1 = new JButton("button 1");
        Button1.addActionListener(this);
        Button1.setActionCommand("createNewRecipePane");
        toolBar.add(Button1);
        toolBar.addSeparator();
        JButton Button2 = new JButton("button 2");
        Button2.addActionListener(this);
        Button2.setActionCommand("createSearchByNamePane");
        toolBar.add(Button2);
        toolBar.addSeparator();
        JButton Button3 = new JButton("button 3");
        Button3.addActionListener(this);
        Button3.setActionCommand("createSearchByIngredientPane");
        toolBar.add(Button3);
        Container contentPane = this.getContentPane();
        contentPane.add(toolBar, BorderLayout.NORTH);
    }
    
    /**
     * Creating new recipe frame.
     */
    private void createNewRecipeFrame(){
        internalFrame = new JInternalFrame();
        internalFrame.setVisible(true);
        internalFrame.setSize(600, 400);
        internalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(internalFrame);
        //Create panel with components to enter data
        JPanel dataPanel = new JPanel();
        JLabel nameLabel = new JLabel("New recipe name:");
        newRecipeNameTextField = new JTextField(15);
        JLabel recipeLabel = new JLabel("Add ingredient:");
        newIngredientTextField = new JTextField(15);
        JButton addButton = new JButton("add");
        addButton.addActionListener(this);
        addButton.setActionCommand("newRecipeAdd");
        
        Box box1 = Box.createVerticalBox();
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridBagLayout());
        addComp(p1, nameLabel, 0,0,1,1, GridBagConstraints.FIRST_LINE_START, 
                GridBagConstraints.NONE, new Insets(5,5,5,5));
        addComp(p1, newRecipeNameTextField, 0,1,1,1, GridBagConstraints.FIRST_LINE_START, 
                GridBagConstraints.NONE, new Insets(5,5,5,5));
        addComp(p1, recipeLabel, 0,2,1,1, GridBagConstraints.FIRST_LINE_START, 
                GridBagConstraints.NONE, new Insets(5,5,5,5));
        addComp(p1, newIngredientTextField, 0,3,1,1, GridBagConstraints.FIRST_LINE_START, 
                GridBagConstraints.NONE, new Insets(5,5,5,5));
        addComp(p1, addButton, 1,3,1,1, GridBagConstraints.FIRST_LINE_START, 
                GridBagConstraints.NONE, new Insets(5,5,5,5));
        
        Box horizontalBox1 = Box.createHorizontalBox();
        JButton clearButton = new JButton("clear");
        clearButton.addActionListener(this);
        clearButton.setActionCommand("clearNewRecipePane");
        newRecipeSaveButton = new JButton("save");
        newRecipeSaveButton.addActionListener(customListener);
        newRecipeSaveButton.setActionCommand("newRecipeSave");
        horizontalBox1.add(clearButton);
        horizontalBox1.add(Box.createHorizontalStrut(10));
        horizontalBox1.add(newRecipeSaveButton);
        addComp(p1, horizontalBox1, 0,4,1,1, GridBagConstraints.FIRST_LINE_START, 
                GridBagConstraints.NONE, new Insets(50,5,5,5));
        
        box1.add(Box.createVerticalStrut(10));
        box1.add(p1);
        box1.add(Box.createVerticalStrut(100));
        dataPanel.add(box1);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridBagLayout());
        
        newRecipelistModel = new DefaultListModel();

        newRecipeList = new JList(newRecipelistModel);
        newRecipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        newRecipeList.setSelectedIndex(0);
        newRecipeList.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(newRecipeList);
        JLabel listLabel = new JLabel("ingredients:");
        searchByNameRemoveButton = new JButton("remove ingredient");
        searchByNameRemoveButton.addActionListener(this);
        searchByNameRemoveButton.setActionCommand("newRecipeRemove");
        Box box2 = Box.createVerticalBox();
        box2.add(listLabel);
        box2.add(listScrollPane);
        box2.add(Box.createVerticalStrut(30));
        box2.add(searchByNameRemoveButton);
        addComp(listPanel, box2, 0,1,1,1, GridBagConstraints.CENTER, 
                GridBagConstraints.BOTH, new Insets(15,15,30,15));
        

        //Create a split pane with the two scroll panes in it.
        JSplitPane splitPane;
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   dataPanel, listPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(300);

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(260, 50);
        dataPanel.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 200));
        internalFrame.add(splitPane);
    } // createNewRecipePane end
      
    /**
     * Creating search by name frame.
     */
    private void createSearchByNameFrame(){
        internalFrame = new JInternalFrame();
        internalFrame.setVisible(true);
        internalFrame.setSize(600, 400);
        internalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(internalFrame);
        //Create panel to enter data
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridBagLayout());
        JLabel nameLabel = new JLabel("Recipe name:");
        searchByNameTextField = new JTextField(15);
        searchByNameButton = new JButton("search");
        searchByNameButton.addActionListener(customListener);
        searchByNameButton.setActionCommand("searchByName");
        Box box1 = Box.createVerticalBox();
        box1.add(Box.createVerticalStrut(10));
        box1.add(nameLabel);
        box1.add(Box.createVerticalStrut(10));
        box1.add(searchByNameTextField);
        box1.add(Box.createVerticalStrut(20));
        box1.add(searchByNameButton);      
        addComp(dataPanel, box1, 0,0,1,1, GridBagConstraints.FIRST_LINE_START, 
                GridBagConstraints.NONE, new Insets(10,30,5,5));
        // Create panel to list ingredients
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridBagLayout());
        JList list;
        searchByNameListModel = new DefaultListModel();

        list = new JList(searchByNameListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        
        JLabel listLabel = new JLabel("ingredients:");
        JButton deleteButton = new JButton("delete recipe"); 
        deleteButton.addActionListener(customListener);
        deleteButton.setActionCommand("deleteRecipe");
        Box box2 = Box.createVerticalBox();
        box2.add(listLabel);
        box2.add(listScrollPane);
        box2.add(Box.createVerticalStrut(30));
        box2.add(deleteButton);
        addComp(listPanel, box2, 0,1,1,1, GridBagConstraints.CENTER, 
                GridBagConstraints.BOTH, new Insets(15,15,30,15));
        
        //Create a split pane with the two scroll panes in it.
        JSplitPane splitPane;
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   dataPanel, listPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(300);

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(220, 50);
        dataPanel.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 200));
        internalFrame.add(splitPane);
    } // createSearchByNamePane end
    
    /**
     * Creating search by ingredient frame.
     */
    private void createSearchByIngredientFrame(){
        enteredIngredientsTab = new ArrayList<>();
        internalFrame = new JInternalFrame();
        internalFrame.setVisible(true);
        internalFrame.setSize(600, 400);
        internalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(internalFrame);
        //Create panel with components to enter data
        JPanel dataPanel = new JPanel();
        JLabel recipeLabel = new JLabel("Ingredient name:");
        searchByIngredientTextField = new JTextField(15);
        searchByIngredientAddButton = new JButton("add");
        searchByIngredientAddButton.addActionListener(this);
        searchByIngredientAddButton.addActionListener(customListener);
        searchByIngredientAddButton.setActionCommand("searchByIngredient");
        
        Box box1 = Box.createVerticalBox();
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridBagLayout());
        addComp(p1, recipeLabel, 0,1,1,1, GridBagConstraints.FIRST_LINE_START, 
                GridBagConstraints.NONE, new Insets(5,5,5,5));
        addComp(p1, searchByIngredientTextField, 0,2,1,1, GridBagConstraints.FIRST_LINE_START, 
                GridBagConstraints.NONE, new Insets(5,5,5,5));
        addComp(p1, searchByIngredientAddButton, 1,2,1,1, GridBagConstraints.FIRST_LINE_START, 
                GridBagConstraints.NONE, new Insets(5,5,5,5));
              
        box1.add(Box.createVerticalStrut(10));
        box1.add(p1);
        box1.add(Box.createVerticalStrut(50));
        dataPanel.add(box1);

        // tableScrollPane
        searchByIngredientModel = new DefaultTableModel();
        searchByIngredientModel.addColumn("name");
        searchByIngredientModel.addColumn("matches");
        searchByIngredientTable = new JTable(searchByIngredientModel);
        searchByIngredientTable.setFillsViewportHeight(true);
        searchByIngredientTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        JScrollPane tableScrollPane = new JScrollPane(searchByIngredientTable);
        tableScrollPane.setBorder( BorderFactory.createTitledBorder(
                                    BorderFactory.createEmptyBorder(20, 15, 15, 15), 
                                    "Recipes") );
        
        //Create a split pane with the two scroll panes in it.
        JSplitPane splitPane;
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   dataPanel, tableScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(300);

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(260, 50);
        dataPanel.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 200));
        internalFrame.add(splitPane);
    } // createSearchByIngredientPane end
    
    /**
     * Add java swing component to panel with JGridBagLayout.
     * @param thePanel panel to which the component is added.
     * @param comp added component.
     * @param xPos position x in layout.
     * @param yPos position y in layout.
     * @param compWidth component width in layout.
     * @param compHeight component height in layout.
     * @param anchor component anchor.
     * @param stretch component stretch.
     * @param insets insets between components.
     */
    private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos,
            int compWidth, int compHeight, int anchor, int stretch, Insets insets){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = xPos;
        c.gridy = yPos;
        c.gridwidth = compWidth;
        c.gridheight = compHeight;
        c.weightx = 100;
        c.weighty = 100;
        c.insets = insets;
        c.anchor = anchor;
        c.fill = stretch;
        thePanel.add(comp, c);
    }
    
    /**
     * newRecipeNameTextField text getter.
     * @return newRecipeNameTextField.getText().
     */
    public String getNewRecipeName(){
        return newRecipeNameTextField.getText();
    }
    /**
     * newRecipeList elements getter.
     * @return Tab of newRecipeList elements converted to String.
     */
    public String[] getNewRecipeIngredients(){
        int size = newRecipeList.getModel().getSize();
        String[] list = new String[size];
        for (int i = 0; i < size; i++){
            list[i] = newRecipeList.getModel().getElementAt(i).toString();
        }
        return list;
    }
    
    /**
     * searchByNameTextField text getter.
     * Using this method clear searchByNameListModel.
     * @return searchByNameTextField.getText().
     */
    public String getSearchRecipeName(){
        searchByNameListModel.setSize(0);
        return searchByNameTextField.getText();
    }
    
    /**
     * Add given as parameters ingredients to searchByNameListModel.
     * @param ingredients String tab to display.
     */
    public void showIngredients(String[] ingredients){
        for(String s : ingredients){
            searchByNameListModel.addElement(s);
        }
        SwingUtilities.updateComponentTreeUI(this);
    }
    
    /**
     * clear searchByIngredientModel.
     */
    public void clearTable(){
        searchByIngredientModel.setRowCount(0);
    }
    
    /**
     * Add new ingredients to list, which is used to search by Ingredient.
     * @return ingredients list.
     */
    public List<String> getSearchIngredients(){
        boolean repeat = false;
        for(String s : enteredIngredientsTab){
            if (searchByIngredientTextField.getText().equals(s))
                repeat = true;
        }
        if (!repeat){
            enteredIngredientsTab.add(searchByIngredientTextField.getText());
        }
        return enteredIngredientsTab;
    }
    
    /**
     * Add found recipe name and its matches to searchByIngredientModel.
     * @param name recipe name
     * @param matches recipe matches
     */
    public void showRecipe(String name, int matches){
        searchByIngredientModel.addRow(new Object[]{name,Integer.toString(matches)});
    }
    
    /**
     * Display message about error or different information.
     * @param tittle message tittle
     * @param msg message text
     */
    public void showMessage(String tittle, String msg){
            JOptionPane.showMessageDialog(this, msg, tittle, JOptionPane.INFORMATION_MESSAGE);
    }  
    
} // CookBookView class end
