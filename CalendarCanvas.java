package CalendarApp;

import java.awt.*;
import java.util.*;

public class CalendarCanvas extends Canvas{
    
    Calendar cal = Calendar.getInstance();
    
    public void month(int mon,int year){
        if(mon>=0 || mon<=11){
            cal.set(year,mon,1);
        }
        repaint();
    }
    
    @Override
    public void paint(Graphics g){
        setBackground(Color.getHSBColor(23, 12, 14));
        String wkday[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        //calender skeleton.
        g.setColor(Color.lightGray);
        Dimension d = getSize();
        g.drawRect(0,0,d.width-1,d.height-1);
        int tmp=1;
        
        for(int i=1; i<7; i++){
            g.drawLine(i*d.width/7, 0, i*d.width/7, d.height);
        }
        for(int i=1; i<7; i++){
            g.drawLine(0, i*d.height/7, d.width, i*d.height/7);
        }
        
        g.setColor(Color.blue);
        Font F = new Font("Calibri", Font.PLAIN, 20);
        g.setFont(F);
        for(int i=0; i<7; i++){
            g.drawString(wkday[i],i*d.width/7+20, 30);
        }
        
        F = new Font("Comic Sans MS", Font.PLAIN, 16);
        g.setFont(F);
        g.setColor(Color.black);
        
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
        int wkd=cal.get(Calendar.DAY_OF_WEEK)-1;
        Calendar verify = Calendar.getInstance();
        
        //Calender content
        while(true){
            int mon=cal.get(Calendar.MONTH);
            if(cal.get(Calendar.DATE)==verify.get(Calendar.DATE) && cal.get(Calendar.MONTH)==verify.get(Calendar.MONTH) && cal.get(Calendar.YEAR)==verify.get(Calendar.YEAR)){
                g.setColor(Color.red);
                g.drawString(""+cal.get(Calendar.DATE), (wkd%7)*d.width/7+30, tmp*d.height/7+30);
                g.setColor(Color.black);
            }
            else
                g.drawString(""+cal.get(Calendar.DATE), (wkd%7)*d.width/7+30, tmp*d.height/7+30);
            wkd++;
            if(wkd%7==0) tmp++;
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)+1);
            if(cal.get(Calendar.MONTH)!=mon){
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)-1, cal.get(Calendar.DATE));
                break;
            }
        }
    }
}