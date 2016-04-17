package CalendarApp;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CalendarApp extends Applet implements ActionListener, FocusListener{
    Button prev = new Button("<"), next = new Button(">");
    TextField mon = new TextField(1), yr = new TextField(3);
    CalendarCanvas cal = new CalendarCanvas();
    int month=cal.cal.get(Calendar.MONTH), year=cal.cal.get(Calendar.YEAR);
    
    @Override
    public void init(){
        setBackground(Color.getHSBColor(100,9000,100));
        this.setSize(500, 330);
        cal.setSize(450,270);
        resetDate();
        
        add(prev); add(mon);
        add(yr); add(next); add(cal);
        prev.addActionListener(this);
        next.addActionListener(this);
        mon.addFocusListener(this);
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
    
    public void resetDate(){
        mon.setText(cal.cal.get(Calendar.MONTH)+1+"");
        yr.setText(cal.cal.get(Calendar.YEAR)+"");
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        switch (ae.getActionCommand()) {
            case "<":
                if(month == 0){
                    month = 11;
                    year--;
                }
                else month--;
                cal.month(month,year);
                resetDate();
                break;
            case ">":
                if(month == 11){
                    month = 0;
                    year++;
                }
                else month++;
                cal.month(month,year);
                resetDate();
                break;
            default:
                JOptionPane.showMessageDialog(null, "App Failure! Press \"OK\" to exit.");
                System.exit(0);
        }
        repaint();
    }
    
    @Override
    public void focusGained(FocusEvent fe){
        
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
        showStatus("This month is:  "+cal.cal.getDisplayName(Calendar.MONTH, 2, Locale.ENGLISH)+" of "+year);
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
                cal.month(tmp-1, year);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Please Enter a valid date!");
            mon.setText("");
        }
        repaint();
    }
    
    public void setYear(){
        try{
            Integer tmp = new Integer(yr.getText());
            year = tmp;
            cal.month(month, tmp);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Please Enter a valid date!");
            yr.setText("");
        }
        repaint();
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