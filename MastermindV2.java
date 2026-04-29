// Plus X Puzzle
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Project extends JApplet
{
    private int MAXNUM, size;
    private JButton button[], start, giveUp, helpBt;
    private JPanel buttonPanel, startPanel;
    private JLabel scoreLabel, probLabel;
    private JTextField score, probNum;
    private JTextArea helpArea;
    private String sizeInput, btName, buttonLabel, help;
    private int number[], findNum[], problem, sum,
                total=0, count=0, getScore=0, bonus2=1, bonus3=1;
    private Title titlePanel;
    private Score scorePanel;
    private Font f1, f2, f3;
    public void init()
    {
        //
get the size of puzzle
        do {
            sizeInput = JOptionPane.showInputDialog("Please enter the size (2-10)");
            size = Integer.parseInt(sizeInput);
            MAXNUM = size*size;
        }
while (size>10 || size<2);
        //
the panel that contains puzzle title
        titlePanel = new Title();
        titlePanel.setBackground(Color.pink);
        titlePanel.setSize(getPreferredSize());
        //
the panel that contains number buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(size, size));
        buttonPanel.setBackground(Color.darkGray);
        //
the score panel that contains problem and score
        scorePanel = new Score();
        scorePanel.setSize(getPreferredSize());
        //
the panel that contains a start, giveUp and help button
        startPanel = new JPanel();
        //
number buttons
        findNum = new int[20];
        number = new int[MAXNUM];
        ButtonHandler handler = new ButtonHandler();
        button = new JButton[MAXNUM];
        //
start button
        start = new JButton("Start");
        start.addActionListener(handler);
        //
giveUp button
        giveUp = new JButton("Give Up");
        giveUp.addActionListener(handler);
        giveUp.setEnabled(false);
        //
text area that contains a help message
        helpArea = new JTextArea(10, 10);
        helpBt = new JButton("Help");
        helpBt.addActionListener(handler);
        help = "See the problem number\n   showed on the right upper corner,\n" +
        "   and press the number buttons \n   to make the problem number.\n" +
        "If the 'x2' or 'x3' button is used, \n" +
        "   the total number will be doubled or tripled.\n" +
        "When it is impossible to make \n   the showed number, the game is over.";
        helpArea.setText(help);
        f3 = new Font("Aria", Font.BOLD, 15);
        helpArea.setFont(f3);
        //
score label and text field
        f1 = new Font("Arial", Font.BOLD, 22);
        scoreLabel = new JLabel("Score");
        scoreLabel.setFont(f1);
        score = new JTextField(4);
        score.setEditable(false);
        score.setFont(f1);
        //
problem label and text field
        f2 = new Font("Arial", Font.BOLD, 50);
        probLabel = new JLabel("Problem");
        probLabel.setFont(f1);
        probNum = new JTextField(2);
        probNum.setEditable(false);
        probNum.setFont(f2);
        //
assign the button lables
        for (int i=0; i<MAXNUM; i++) {
            number[i] = 1 + (int)(Math.random()*10);
            if (number[i] == 10)
                btName = "x2";
            else if (number[i] == 9)
                btName = "x3";
            else
                btName = Integer.toString(number[i]);
            button[i] = new JButton(btName);
            setButtonColor(i);
            button[i].setVisible(false);
            buttonPanel.add(button[i]);
            button[i].addActionListener(handler);
        }
        //
add objects to panels
        scorePanel.add(probLabel);
        scorePanel.add(probNum);
        scorePanel.add(scoreLabel);
        scorePanel.add(score);
        startPanel.add(start);
        startPanel.add(giveUp);
        startPanel.add(helpBt);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        //
set panels
        c.add(titlePanel, BorderLayout.NORTH);
        c.add(buttonPanel, BorderLayout.CENTER);
        c.add(scorePanel, BorderLayout.EAST);
        c.add(startPanel, BorderLayout.SOUTH);
    }
    public class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            buttonLabel = e.getActionCommand();
            //
when a start button is pressed
            if (buttonLabel == "Start") {
                for (int i=0; i<MAXNUM; i++)
                    button[i].setVisible(true);
                problem = getNumber();
                probNum.setText(Integer.toString(problem));
                start.setEnabled(false);
                giveUp.setEnabled(true);
            }
            //
when a giveUp button is pressed
            else if (buttonLabel == "Give Up") {
                for (int i=0; i<MAXNUM; i++) {
                    if (button[i].isVisible() == true){
                        button[i].setEnabled(false);
                        button[i].setBackground(Color.gray);
                    }
                }
                total=0; count=0;
                probNum.setText("");
                score.setText("");
                giveUp.setEnabled(false);
                JOptionPane.showMessageDialog
                    (null, "Your score is " + Integer.toString(getScore));
            }
            //
when a help button is pressed
            else if (buttonLabel == "Help") {
                JOptionPane.showMessageDialog(null, helpArea, "Help",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            //
when a number button is pressed
            else {
                getSum();
    // get the sum
                //
when the sum is greater than the problem number
                if (sum > problem) {
                    play(getCodeBase(), "audio/exceed.au");
                    JOptionPane.showMessageDialog(null, "You exceeded the number!");
                    for (int i=0; i<count; i++) {
                        button[findNum[i]].setEnabled(true);
                        setButtonColor(findNum[i]);
                    }
                    count = 0;
                    total = 0;
                }
                //
{
when the sum is less than or equal to the problem number
                else
                for (int i=0; i<MAXNUM; i++)
                    if (e.getSource() == button[i])
                        findNum[count] = i;
                play(getCodeBase(), "audio/ding.au");
                button[findNum[count]].setEnabled(false);
= sum;
                button[findNum[count]].setBackground(Color.gray);
                count++;
                total
                   //
when it is solved
                if (sum == problem) {
                    play(getCodeBase(), "audio/return.au");
                    for (int i=0; i<count; i++)
                        button[findNum[i]].setVisible(false);
                       getScore += count * 30 * bonus2 * bonus3;
                    count=0; total = 0;
                    bonus2=1; bonus3=1;
                    if (isSolved() == true) {
                        getScore += 3000;
                        play(getCodeBase(), "audio/spacemusic.au");
                        JOptionPane.showMessageDialog(null,
                        "Congratulations!!!\nYour score is " + Integer.toString(getScore));
                    }
                    else
                        {
                        problem = getNumber();
                        probNum.setText(Integer.toString(problem));
                        score.setText(Integer.toString(getScore));
                        }
                    }
                }
            }
        }
    }
    // get the random number for a problem number
    public int getNumber()
    {
        return (1+(int)(Math.random()*2)) * (1+(int)(Math.random()*10));
    }
    // get the sum
    public void getSum()
    {
        if (buttonLabel == "x2") {
        sum = total*2;
        bonus2 = 2;
        }
        else if (buttonLabel == "x3") {
        sum = total*3;
        bonus3 = 3;
        }
        else
        sum = total + Integer.parseInt(buttonLabel);
    }
    // check if the problem is solved
    public boolean isSolved()
    {
        int check=1, i=0;
        while (i<MAXNUM) {
        if (button[i].isVisible()==true)
            check = 0;
        i++;
        }
        if (check == 0)
            return false;
        else
            return true;
    }
    // set the button color
    public void setButtonColor(int i)
    {
        switch(number[i]) {
            case 1: button[i].setBackground(Color.blue);
                break;
            case 2: button[i].setBackground(Color.red);
                break;
            case 3: button[i].setBackground(Color.green);
                break;
            case 4: button[i].setBackground(Color.pink);
                break;
            case 5: button[i].setBackground(Color.orange);
                break;
            case 6: button[i].setBackground(Color.cyan);
                break;
            case 7: button[i].setBackground(Color.lightGray);
                break;
            case 8: button[i].setBackground(Color.magenta);
                break;
            case 9: button[i].setBackground(Color.white);
                break;
            case 10: button[i].setBackground(Color.yellow);
                break;
            default: break;
        }
    }
}
// class Title
class Title extends JPanel
{
    private String title;
    private Font f;
    public Title()
    {
        f = new Font("Arial", Font.BOLD+Font.ITALIC, 35);
    }
    // draw the title string
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setFont(f);
        g.fillRect(130, 15, 350, 50);
        g.clearRect(135, 20, 350, 50);
        g.setColor(Color.blue);
        g.drawString("PLUS   X   PUZZLE", 150, 55);
    }
    // get Title panel size
    public Dimension getPreferredSize()
    {
        return new Dimension(600, 80);
    }
}
// class Score
class Score extends JPanel
{
    private Font f;
    public Score()
    {
        f = new Font("Century", Font.BOLD, 25);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setFont(f);
        g.setColor(Color.pink);
        g.fillRoundRect(10, 250, 110, 110, 20, 20);
        g.setColor(Color.gray);
        g.fillRoundRect(15, 255, 100, 100, 20, 20);
        g.setColor(Color.pink);
        g.drawString("Plus", 35, 280);
        g.drawString("X", 55, 310);
        g.drawString("Puzzle", 25, 340);
    }
    // get Score panel size
    public Dimension getPreferredSize()
    {
        return new Dimension(130, 300);
    }
}