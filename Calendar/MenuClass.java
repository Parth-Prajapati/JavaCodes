package CalendarApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuClass extends JFrame implements ActionListener{
    MenuBar m = new MenuBar();
    Menu app = new  Menu("Apps");
    MenuItem mItem1 = new MenuItem("Calc");
    MenuItem mItem2 = new MenuItem("Calendar");
    MenuItem mItem3 = new MenuItem("Notepad");
    
    public MenuClass(){
        setMenuBar(m);
        m.add(app);
        app.add(mItem1);
        app.add(mItem2);
        app.add(mItem3);
        mItem1.addActionListener(this);
        mItem2.addActionListener(this);
        mItem3.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==mItem3){
            Runtime r = Runtime.getRuntime(); 
            Process p = null;
            try { p = r.exec("C:\\Program Files\\Windows NT\\Accessories\\wordpad D:\\abc.txt"); } catch (Exception e) { System.out.println("Error executing notepad."); }
        }
        else if(ae.getSource()==mItem1){
            Runtime r = Runtime.getRuntime(); 
            Process p = null;
            try { p = r.exec("Calc"); } catch (Exception e) { System.out.println("Error executing notepad."); }
        }
        else if(ae.getSource()==mItem2){
        CalendarApp2 cal2 = new CalendarApp2();
        cal2.setSize(500, 330);
        cal2.setVisible(true);
        cal2.setResizable(false);
        cal2.setTitle("Calendar");
        cal2.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        cal2.setLocation((d.width-500)/2,(d.height-330)/2);
        cal2.setAlwaysOnTop(true);
        }
    }
    
    public static void main(String[] args){
        MenuClass menu = new MenuClass();
        menu.setVisible(true);
        menu.setSize(300,100);
        menu.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
