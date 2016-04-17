package CalendarApp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CalendarApp2 extends JFrame implements ActionListener, FocusListener{
    Button prev = new Button("<"), next = new Button(">");
    TextField mon = new TextField(1), yr = new TextField(3);
    CalendarCanvas cal = new CalendarCanvas();
    MenuBar menu = new MenuBar();
    Menu action = new  Menu("Action");
    Menu setMon = new Menu("Set Month"), setYear = new Menu("Set Year");
    MenuItem exit = new MenuItem("Exit");
    Menu window = new Menu("Window");
    MenuItem mon1 = new MenuItem("In field"), mon2 = new MenuItem("In new Window");
    MenuItem yr1 = new MenuItem("In field"), yr2 = new MenuItem("In new Window");
    MenuItem help = new MenuItem("Help!");
    int month=cal.cal.get(Calendar.MONTH), year=cal.cal.get(Calendar.YEAR);
    Panel p1 = new Panel();
    Label stat = new Label("");
    
    public CalendarApp2(){
        setMenuBar(menu);
        menu.add(action); menu.add(window);
        action.add(setMon); action.add(setYear);
        setMon.add(mon1); setMon.add(mon2);
        setYear.add(yr1); setYear.add(yr2);
        window.add(help); window.add(exit);
        cal.setSize(450,270);
        resetDate();
        setLayout(new BorderLayout());
        p1.setLayout(new FlowLayout());
        p1.add(prev); p1.add(mon);
        p1.add(yr); p1.add(next);
        p1.setBackground(Color.getHSBColor(100,9000,100));
        stat.setBackground(Color.getHSBColor(2,90,8));
        add(stat,BorderLayout.SOUTH);
        add(p1,BorderLayout.NORTH); add(cal);
        prev.addActionListener(this);
        next.addActionListener(this);
        mon.addFocusListener(this);
        mon1.addActionListener(this); mon2.addActionListener(this);
        yr1.addActionListener(this); yr2.addActionListener(this);
        help.addActionListener(this); exit.addActionListener(this);
        yr.addFocusListener(this);
        mon.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke){
                switch(ke.getKeyCode()){
                    case KeyEvent.VK_ENTER:
                        setMon();
                        cal.requestFocusInWindow();
                        break;
                    case KeyEvent.VK_F1:
                        help();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        exit();
                }
            }
        });
        yr.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke){
                switch(ke.getKeyCode()){
                    case KeyEvent.VK_ENTER:
                        setYear();
                        cal.requestFocusInWindow();
                        break;
                    case KeyEvent.VK_F1:
                        help();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        exit();
                }
            }
        });
        prev.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke){
                shortcuts(ke);
            }
        });
        next.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke){
                shortcuts(ke);
            }
        });
        cal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke){
                shortcuts(ke);
            }
        });
    }
    
    private void resetDate(){
        mon.setText(cal.cal.get(Calendar.MONTH)+1+"");
        yr.setText(cal.cal.get(Calendar.YEAR)+"");
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getActionCommand().equals("<")){
            if(month == 0){
                month = 11;
                year--;
            }
            else month--;
            cal.month(month,year);
            resetDate();
        }
        else if(ae.getActionCommand().equals(">")){
            if(month == 11){
                month = 0;
                year++;
            }
            else month++;
            cal.month(month,year);
            resetDate();
        }
        else if(ae.getSource() == mon1){
            mon.requestFocusInWindow();
        }
        else if(ae.getSource() == yr1){
            yr.requestFocusInWindow();
        }
        else if(ae.getSource() == mon2){
            try{
                mon.setText("");
                Integer tmp = new Integer(JOptionPane.showInputDialog("Enter Month number: "));
                if(tmp>0 && tmp<13){
                    month = tmp-1;
                    cal.month(tmp-1, year);
                }
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(this, "Enter valid Month number!");
            }
            resetDate();
        }
        else if(ae.getSource() == yr2){
            try{
                yr.setText("");
                Integer tmp = new Integer(JOptionPane.showInputDialog("Enter Year: "));
                year = tmp;
                cal.month(month, year);
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(this, "Enter valid Year!");
            }
            resetDate();
        }
        else if(ae.getSource() == help){
            JOptionPane.showMessageDialog(this, help());
        }
        else if(ae.getSource() == exit){
            exit();
        }
        repaint();
    }
    
    @Override
    public void focusGained(FocusEvent fe){
        if(fe.getSource()==mon){
            mon.setText("");
        }
        else if(fe.getSource()==yr){
            yr.setText("");
        }
    }
    
    @Override
    public void focusLost(FocusEvent fe){
        if(fe.getSource()==mon){
            setMon();
        }
        else if(fe.getSource()==yr){
            setYear();
        }
        resetDate();
    }
    
    @Override
    public void paint(Graphics g){
        stat.setText("This month is:  "+cal.cal.getDisplayName(Calendar.MONTH, 2, Locale.ENGLISH)+" of "+year);
    }
    
    public void shortcuts(KeyEvent k){
        switch(k.getKeyCode()){
            case KeyEvent.VK_M:
                mon.requestFocusInWindow();
                break;
            case KeyEvent.VK_Y:
                yr.requestFocusInWindow();
                break;
            case KeyEvent.VK_F1:
                JOptionPane.showMessageDialog(this, help());
                break;
            case KeyEvent.VK_ESCAPE:
                exit();
                break;
            default:
                cal.requestFocusInWindow();
        }
    }
    
    public void setMon(){
        try{
            Integer tmp = new Integer(mon.getText());
            if(tmp>0 && tmp<13){
                month = tmp-1;
                cal.month(month, year);
            }
        }catch(Exception e){
            if(!mon.getText().isEmpty())
            JOptionPane.showMessageDialog(this, "Please Enter a valid month!");
            mon.setText("");
        }
        repaint();
    }
    
    public void setYear(){
        try{
            Integer tmp = new Integer(yr.getText());
            year = tmp;
            cal.month(month, year);
        }catch(Exception e){
            if(!yr.getText().isEmpty())
            JOptionPane.showMessageDialog(this, "Please Enter a valid year!");
            yr.setText("");
        }
        repaint();
    }
    
    public static void main(String[] args){
        CalendarApp2 cal2 = new CalendarApp2();
        cal2.setSize(500, 330);
        cal2.setVisible(true);
        cal2.setResizable(false);
        cal2.setTitle("Calendar");
        cal2.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        cal2.setLocation((d.width-500)/2,(d.height-330)/2);
    }
    
    public String help(){
        return "Calender Help:\n"
                + " 1. Press F1 for help.\n"
                + " 2. Press 'M' for accessing month field.\n"
                + " 3. Press 'Y' for accessing year field.\n"
                + " 4. Press 'Enter' after editing field to display month.\n"
                + " 5. Press '<' button for previous month.\n"
                + " 6. Press '>' button for next month.\n"
                + " 7. Current date is displayed in red.\n"
                + " 8. Current month is shown in status bar.\n"
                + " 9. Press 'Esc' to exit.";
    }
    
    public void exit(){
        int tmp = JOptionPane.showConfirmDialog(this, "Are you sure want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if(tmp==0){
            System.exit(0);
        }
        else{
            cal.requestFocusInWindow();
        }
    }
}