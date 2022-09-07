/**
 * This is my java mini project
 * You can start game from this
 * It's easy to quit, but don't. Don't quit. Don't stop.
 *
 * @description Run the class
 * @author Yifei Ni
 * BUPT number 2020213414
 * QMUL number 200981084
 * @version 1.0
 * @ClassName Start
 * @date 2022-6-5 15:59
 */

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This is the Start class
 * You can start game from this
 * Start the game
 * @version 1.0
 */
public class Start {
    //store all the words in word.txt
    private static ArrayList<String> WordList = new ArrayList<String>();

    //frame
    JFrame frame;

    //the table used to display
    JTable wordTable;

    //the value of the keyboard
    static int[] keyBoardList = {81, 87, 69, 82, 84, 89, 85, 73, 79, 80, 8, 65, 83, 68, 70, 71, 72, 74, 75, 76, 10, 90, 88, 67, 86, 66, 78, 77};

    //the input from the keyboard
    int input;

    //store the answer in String and Array
    String answer;
    String[] answerArray = new String[5];

    //the current input line
    String inputStr;
    //the value of the table
    String[][] tableValue = new String[6][5];
    //the head name of the table
    String[] tableHeadName = new String[]{"1", "2", "3", "4", "5"};

    //row 0-5  column 0-4
    int crow = 0, column = 0;

    KeyAdapter ka = new KeyAdapter() {
        //Overrode the keyPressed() method in this adapter
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
//            System.out.println("keyCode = " + keyCode);
            if (isInKeyBoardList(keyCode)) {
                input = keyCode;
                //Operation with input
//                System.out.println("beforeOperation");
                operation();

            }
        }
    };

    /**
     *  The main method is used to invoke the run () method.
     *  The specific functionality is done in run ().
     *  @param  args The program does not use this parameter.
     */

    public static void main(String[] args) {
        //to run the program
        Start s = new Start();
        s.run();
    }

    /**
     * This method is used to create a frame,
     * a JPanel as a background to contain all the components,
     * a JLabel to display the title,
     * a JTable to display the entered letters,
     * a JButton to complete the function of restart,
     * call getAWord() method to select a word randomly,
     */
    public void run() {

        frame = new JFrame("WORDLE");

        //Setting window size
        frame.setSize(600, 650);
        //Set window color
        frame.setBackground(new Color(255, 255, 255));
        //Set size fixed
        frame.setResizable(false);
        //Setting close window
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Setting window position
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setLocation((screenWidth - 200 - 400) / 2, (screenHeight - 200 - 400) / 2);
        //Set the layout
        frame.setLayout(null);

        //Build the JPanel background container
        JPanel panelBackground = new JPanel();
        panelBackground.setBackground(Color.WHITE);
        panelBackground.setBounds(0, 0, 600, 600);
        //Set the layout
        panelBackground.setLayout(new BorderLayout());
        frame.getContentPane().add(panelBackground);

        //Create a JLabel title
        JLabel wordLabel = new JLabel("Wordle", SwingConstants.CENTER);
//        wordLabel.setHorizontalAlignment();
        wordLabel.setFont(new Font("华文隶书", Font.BOLD, 60));
        wordLabel.setBounds(0, 0, 600, 200);
        panelBackground.add(wordLabel, BorderLayout.NORTH);

        //Create a JTable table
        wordTable = new JTable();
        wordTable.setModel(new DefaultTableModel(tableValue, tableHeadName));
        wordTable.setFont(new Font("黑体", Font.PLAIN, 40));
        wordTable.setBackground(Color.WHITE);
        wordTable.setBorder(new LineBorder(new Color(0, 0, 0)));
        wordTable.setBounds(200, 20, 400, 480);
        wordTable.setEnabled(false);
        wordTable.setRowHeight(80);
        //The table text is centered
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        wordTable.setDefaultRenderer(Object.class, renderer);

        panelBackground.add(wordTable, BorderLayout.CENTER);

        //Get the answer words and put them in an array
        try {
            answer = getAWord();
            char[] letter = answer.toCharArray();
            for (int i = 0; i < 5; i++) {
                answerArray[i] = String.valueOf(letter[i]);
            }

//            System.out.println("answer " + answer);
        } catch (IOException e) {
            System.out.println("Invalid");
        }

        frame.setFocusable(true);
        frame.addKeyListener(ka);

        //Build the reopen button
        JButton btnNewButtonReset = new JButton("Reset");
        btnNewButtonReset.setFont(new Font("华文仿宋", Font.PLAIN, 16));
        btnNewButtonReset.setBackground(Color.LIGHT_GRAY);
        btnNewButtonReset.setBounds(650, 15, 70, 40);
        panelBackground.add(btnNewButtonReset, BorderLayout.SOUTH);

        //Click the restart button event
        btnNewButtonReset.addActionListener(new ActionListener() {
            /**
             * This method is used to define the reset event.
             * It will call the method reset()
             * @param: e the ActionEvent
             */
            public void actionPerformed(ActionEvent e) {
                //reset the game
//                System.out.println("reset");
                reset();
            }
        });

        //***Setting visibility
        frame.setVisible(true);
    }

    /**
     * This method is used to reset the game
     * You'll get the correct answer for the last game at the same time.
     */
    public void reset() {

//        System.out.println(answer);
        JOptionPane.showMessageDialog(null, "The answer is " + answer);

        //reset tableValue
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                tableValue[i][j] = null;
            }
        }
        wordTable.setModel(new DefaultTableModel(tableValue, tableHeadName));
        wordTable.repaint();

        //reset the row and the column
        crow = 0;
        column = 0;

        //Get the answer words and put them in an array
        try {
            answer = getAWord();
            char[] letter = answer.toCharArray();
            for (int i = 0; i < 5; i++) {
                answerArray[i] = String.valueOf(letter[i]);
            }

//            System.out.println("answer " + answer);
        } catch (IOException ie) {
            System.out.println("Invalid");
        }

        frame.requestFocusInWindow();
    }

    /**
     * This method will call different functions based on the values you enter on the keyboard.
     * For example, input == 8 means you just hit the backspace key,
     * input == 10 means the enter key.
     * Other cases mean that one of the twenty-six English letters is typed.
     */
    public void operation() {
 /*       System.out.println("operation" + input);
        System.out.println("row " + crow + " col" + column);*/

        if (input == 8) {
            if (column != 0) {
                //column == 0 is empty, backspace
                backspace();
            }
        } else if (input == 10) {
            if (column != 5) {
                JOptionPane.showMessageDialog(null, "No enough letters");
            } else
                //is full,enter
                enter();
        } else {
            //column = 5 is not full
            if (column < 5) {
                //The input data
//                System.out.println("Successful insertion, row =" + crow + " col = " + column);
                tableValue[crow][column] = String.valueOf((char) input);

                wordTable.setValueAt(tableValue[crow][column], crow, column);

                //Render a single word
                DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
                    @Override
                    /**
                     * This method is used to configure the renderer appropriately before drawing.
                     * The typed word appears black.
                     * @param: table - the JTable that is asking the renderer to draw; can be null.
                     * @param: value - the value of the cell to be rendered. It is up to the specific renderer to interpret and draw the value. For example, if value is the string "true", it could be rendered as a string or it could be rendered as a check box that is checked. null is a valid value
                     * @param: isSelected - true if the cell is to be rendered with the selection highlighted; otherwise false.
                     * @param: hasFocus - if true, render cell appropriately. For example, put a special border on the cell, if the cell can be edited, render in the color used to indicate editing
                     * @param: row - the row index of the cell being drawn. When drawing the header, the value of row is -1
                     * @param: column - the column index of the cell being drawn.
                     * @return Returns the component used for drawing the cell.
                     */
                    public Component getTableCellRendererComponent(JTable table, Object value,
                                                                   boolean isSelected, boolean hasFocus, int row, int column) {
                        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        cell.setForeground(Color.BLACK);
                        return cell;
                    }
                };
                tcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

                wordTable.getColumn(tableHeadName[column]).setCellRenderer(tcr);

                column++;
            }
        }
    }

    /**
     * This method is used to complete the function of the backspace key.
     * input == 8 means you just hit the backspace key.
     */
    public void backspace() {

        //backspace
        tableValue[crow][--column] = null;
        wordTable.setValueAt(null, crow, column);

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
            @Override
            /**
             * This method is used to configure the renderer appropriately before drawing.
             * 1. Green means the letter is contained in the word and is in that position.
             * 2. Yellow means the letter is contained in the word but not in that position.
             * 3. Grey means the letter is not contained in the word.
             * @param: table - the JTable that is asking the renderer to draw; can be null.
             * @param: value - the value of the cell to be rendered. It is up to the specific renderer to interpret and draw the value. For example, if value is the string "true", it could be rendered as a string or it could be rendered as a check box that is checked. null is a valid value
             * @param: isSelected - true if the cell is to be rendered with the selection highlighted; otherwise false.
             * @param: hasFocus - if true, render cell appropriately. For example, put a special border on the cell, if the cell can be edited, render in the color used to indicate editing
             * @param: row - the row index of the cell being drawn. When drawing the header, the value of row is -1
             * @param: column - the column index of the cell being drawn.
             * @return Returns the component used for drawing the cell.
             */
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (table.getValueAt(row, column) == null || crow <= row) {
                    cell.setBackground(Color.WHITE);
                    return cell;
                }

                if (table.getValueAt(row, column).equals(answerArray[column]))
                    cell.setBackground(Color.GREEN);
                else if (table.getValueAt(row, column).equals(answerArray[0]) || table.getValueAt(row, column).equals(answerArray[1]) || table.getValueAt(row, column).equals(answerArray[2]) || table.getValueAt(row, column).equals(answerArray[3]) || table.getValueAt(row, column).equals(answerArray[4]))
                    cell.setBackground(Color.YELLOW);
                else
                    cell.setBackground(Color.GRAY);
                return cell;
            }
        };
        tcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        for (int i = 0; i < 5; i++) {
            wordTable.getColumn(tableHeadName[i]).setCellRenderer(tcr);
        }

        wordTable.repaint();

//        System.out.println("backspace");
    }

    /**
     * This method is used to complete the function of the enter key.
     * input == 10 means you just hit the enter key.
     * Inside the method the getTableCellRendererComponent method is overridden
     */
    public void enter() {
//        System.out.println("enter");

        //Gets and determines whether it is a word
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            //position = 8   [1,2]
            sb.append(tableValue[crow][i]);
        }
        //Gets the currently typed word
        inputStr = sb.toString();
//        System.out.println("inputStr" + inputStr);

        if (isAWord(inputStr, WordList)) {
            if (inputStr.equals(answer)) {
                //End game Win popover

                DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
                    @Override
                    /**
                     * This method is used to configure the renderer appropriately before drawing.
                     * 1. Green means the letter is contained in the word and is in that position.
                     * 2. Yellow means the letter is contained in the word but not in that position.
                     * 3. Grey means the letter is not contained in the word.
                     * @param: table - the JTable that is asking the renderer to draw; can be null.
                     * @param: value - the value of the cell to be rendered. It is up to the specific renderer to interpret and draw the value. For example, if value is the string "true", it could be rendered as a string or it could be rendered as a check box that is checked. null is a valid value
                     * @param: isSelected - true if the cell is to be rendered with the selection highlighted; otherwise false.
                     * @param: hasFocus - if true, render cell appropriately. For example, put a special border on the cell, if the cell can be edited, render in the color used to indicate editing
                     * @param: row - the row index of the cell being drawn. When drawing the header, the value of row is -1
                     * @param: column - the column index of the cell being drawn.
                     * @return Returns the component used for drawing the cell.
                     */
                    public Component getTableCellRendererComponent(JTable table, Object value,
                                                                   boolean isSelected, boolean hasFocus, int row, int column) {
                        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                        if (table.getValueAt(row, column) == null) {
                            cell.setBackground(Color.WHITE);
                            return cell;
                        }

                        if (table.getValueAt(row, column).equals(answerArray[column]))
                            cell.setBackground(Color.GREEN);
                        else if (table.getValueAt(row, column).equals(answerArray[0]) || table.getValueAt(row, column).equals(answerArray[1]) || table.getValueAt(row, column).equals(answerArray[2]) || table.getValueAt(row, column).equals(answerArray[3]) || table.getValueAt(row, column).equals(answerArray[4]))
                            cell.setBackground(Color.YELLOW);
                        else
                            cell.setBackground(Color.GRAY);
                        return cell;
                    }
                };
                tcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                for (int i = 0; i < 5; i++) {
                    wordTable.getColumn(tableHeadName[i]).setCellRenderer(tcr);
                }

                wordTable.repaint();

                JOptionPane.showMessageDialog(null, "You win!");
                System.out.println("win");
            } else {
                //Input error
                JOptionPane.showMessageDialog(null, "Input error");

/*                //Render cell
                System.out.println("--------------------------");
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                        System.out.println(tableValue[i][j]);
                    }
                }
                System.out.println("--------------------------");*/

                DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
                    @Override
                    /**
                     * This method is used to configure the renderer appropriately before drawing.
                     * 1. Green means the letter is contained in the word and is in that position.
                     * 2. Yellow means the letter is contained in the word but not in that position.
                     * 3. Grey means the letter is not contained in the word.
                     * @param: table - the JTable that is asking the renderer to draw; can be null.
                     * @param: value - the value of the cell to be rendered. It is up to the specific renderer to interpret and draw the value. For example, if value is the string "true", it could be rendered as a string or it could be rendered as a check box that is checked. null is a valid value
                     * @param: isSelected - true if the cell is to be rendered with the selection highlighted; otherwise false.
                     * @param: hasFocus - if true, render cell appropriately. For example, put a special border on the cell, if the cell can be edited, render in the color used to indicate editing
                     * @param: row - the row index of the cell being drawn. When drawing the header, the value of row is -1
                     * @param: column - the column index of the cell being drawn.
                     * @return Returns the component used for drawing the cell.
                     */
                    public Component getTableCellRendererComponent(JTable table, Object value,
                                                                   boolean isSelected, boolean hasFocus, int row, int column) {
                        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                        if (table.getValueAt(row, column) == null) {
                            cell.setBackground(Color.WHITE);
                            return cell;
                        }

                        if (table.getValueAt(row, column).equals(answerArray[column]))
                            cell.setBackground(Color.GREEN);
                        else if (table.getValueAt(row, column).equals(answerArray[0]) || table.getValueAt(row, column).equals(answerArray[1]) || table.getValueAt(row, column).equals(answerArray[2]) || table.getValueAt(row, column).equals(answerArray[3]) || table.getValueAt(row, column).equals(answerArray[4]))
                            cell.setBackground(Color.YELLOW);
                        else
                            cell.setBackground(Color.GRAY);
                        return cell;
                    }
                };
                tcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                for (int i = 0; i < 5; i++) {
                    wordTable.getColumn(tableHeadName[i]).setCellRenderer(tcr);
                }

                wordTable.repaint();

                //A newline
                crow++;
                column = 0;

//                System.out.println("over");
                if (crow == 6) {
                    //End of the game lost popover
                    JOptionPane.showMessageDialog(null, "Over the number of times you have failed, the correct answer is:" + answer);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Not a word list");
        }
    }

    /**
     * This method is used to read all words from word.txt into the WordList,
     * and select a word from the WordList randomly.
     * @return The word chosen at random as a String
     * @throws IOException Cannot find the word.txt
     */
    public String getAWord() throws IOException {
        //The store path
        String path = "src\\word.txt";
        BufferedReader br = new BufferedReader(new FileReader(path));
        //Temporary storage words
        String str;
        while ((str = br.readLine()) != null) {
            WordList.add(str.toUpperCase());
        }

        //Temporary storage words
        int index = (int) (Math.random() * WordList.size());

        br.close();

        //Returns the selected word as the correct answer
        return WordList.get(index);
    }

    /**
     * This method is used to determine if the inputStr is in the WordList,
     * that is, if it is a word.
     * @param inputStr The five characters you just typed
     * @param WordList Store all words
     * @return true or false
     */
    public boolean isAWord(String inputStr, ArrayList<String> WordList) {
        for (int i = 0; i < WordList.size(); i++) {
            if (inputStr.equals(WordList.get(i)))
                return true;
        }
        return false;
    }

    /**
     * This method is used to check whether the entered key is a letter, backspace, or enter,
     * or some other key(we do not want).
     * @param charPress the ASCII characters corresponding to the current keyboard input
     * @return true or false
     */
    public boolean isInKeyBoardList(int charPress) {
        for (int i = 0; i < 28; i++) {
            if (charPress == keyBoardList[i]) {
                return true;
            }
        }
        return false;
    }
}