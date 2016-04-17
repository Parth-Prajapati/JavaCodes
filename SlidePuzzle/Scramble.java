package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Scramble extends JFrame implements ActionListener{
    Button but[][] = new Button[4][3];
    MenuBar menu = new MenuBar();
    int moves=0;
    Menu action = new Menu("Action"), color = new Menu("Color"), window = new Menu("Window");
    MenuItem colors[] = new MenuItem[5], help = new MenuItem("Help!");
    MenuItem newgame = new MenuItem("New Game"), about = new MenuItem("About Scramble");
    MenuItem exit = new MenuItem("Exit");
    
    public Scramble(){
        setMenuBar(menu);
        menu.add(action); menu.add(color); menu.add(window);
        action.add(newgame); action.add(exit);
        window.add(help); window.add(about);
        colors[0] = new MenuItem("Gray"); colors[1] = new MenuItem("Red"); colors[2] = new MenuItem("Blue");
        colors[3] = new MenuItem("Magenta"); colors[4] = new MenuItem("Black");
        for(int i=0; i<5; i++){
            color.add(colors[i]);
            colors[i].addActionListener(this);
        }
        
        setLayout(new GridLayout(4,3));
        int tmp=1;
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                but[i][j] = new Button(""+(tmp++));
                add(but[i][j]);
                but[i][j].setEnabled(false);
                but[i][j].setBackground(Color.darkGray);
                but[i][j].setForeground(Color.white);
                but[i][j].addActionListener(this);
            }
        }
        but[3][2].setVisible(false);
        
        newgame.addActionListener(this);
        exit.addActionListener(this);
        help.addActionListener(this);
        about.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==newgame){
            for(int i=0; i<4; i++)
                for(int j=0; j<3; j++)
                    but[i][j].setEnabled(true);
            randomize();
            moves=0;
        }
        else if(ae.getSource()==exit)   exit();
        else if(ae.getSource()==help)   help();
        else if(ae.getSource()==about)  about();
        else{
            for(int i=0; i<5; i++){
                if(ae.getSource()==colors[i]){
                    Color c1 = new Color(0);
                    if(i==0) c1 = Color.darkGray;
                    else if(i==1) c1 = Color.red;
                    else if(i==2) c1 = Color.blue;
                    else if(i==3) c1 = Color.magenta;
                    else if(i==4) c1 = Color.black;
                    setColor(c1);
                    return;
                }
            }
            Button tmp1 = (Button)ae.getSource();
            Button tmp2 = checkMoves(tmp1);
        
            if(tmp2!=null){
                moves++;
                tmp2.setLabel(tmp1.getLabel());
                tmp2.setVisible(true);
                tmp1.setVisible(false);
                tmp1.setLabel("12");
                tmp2.requestFocusInWindow();
                boolean win = WinStat();
                if(win == true){
                    for(int i=0; i<4; i++)
                        for(int j=0; j<3; j++)
                            but[i][j].setEnabled(false);
                    JOptionPane.showMessageDialog(this,"   You win!\n\nTotal moves: "+moves);
                }
            }
        }
    }
    
    public Button checkMoves(Button obj){
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                if(obj == but[i][j]){
                    if(i-1>=0 && but[i-1][j].isVisible()==false) return but[i-1][j];
                    else if(j-1>=0 && but[i][j-1].isVisible()==false) return but[i][j-1];
                    else if(i+1<4 && but[i+1][j].isVisible()==false) return but[i+1][j];
                    else if(j+1<3 && but[i][j+1].isVisible()==false) return but[i][j+1];
                    else return null;
                }
            }
        }
        return null;
    }
    
    public boolean WinStat(){
        int temp=1;
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                try{
                    Integer tmp = new Integer(but[i][j].getLabel());
                    if((temp++)!=tmp) return false;
                }catch(NumberFormatException nfe){
                    System.out.println(nfe);
                    return false;
                }
            }
        }
        return true;
    }
    
    public void randomize(){
        int tmp[] = {0,0,0,0,0,0,0,0,0,0,0,0};
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                but[i][j].setVisible(true);
                Double d = Math.random()*12;
                int k = d.intValue()+1;
                if(k==12 && tmp[k-1]==0){
                    but[i][j].setVisible(false);
                    tmp[k-1]=1;
                    continue;
                }
                if(tmp[k-1]==0){
                    but[i][j].setLabel(""+k);
                    tmp[k-1]=1;
                }
                else{
                    while(tmp[k-1]!=0){
                        d = Math.random()*12;
                        k = d.intValue()+1;
                    }
                    if(k==12 && tmp[11]==0){
                        but[i][j].setVisible(false);
                        tmp[11]=1;
                        continue;
                    }
                    tmp[k-1]=1;
                    but[i][j].setLabel(""+k);
                }
            }
        }
    }
    
    public void setColor(Color c){
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                but[i][j].setBackground(c);
                but[i][j].setForeground(Color.WHITE);
            }
        }
    }
    
    public void help(){
        JOptionPane.showMessageDialog(this, "Scramble help:\n"
                + " 1. Go to Action -> New Game for starting new game.\n"
                + " 2. Go to Color and pick a color for changing color.\n"
                + " 3. Go to Window -> Help! for help window.\n"
                + " 4. Go to Window -> About for credits of game.\n"
                + " 5. Go to Action -> Exit to stop playing the game.\n\n"
                + "Aim of Game:\n"
                + " -> Arrange all the pieces in order.\n"
                + " -> Total moves will be shown at end of game.\n"
                + " -> Try to complete with minimum moves!");
    }
    
    public void about(){
        JOptionPane.showMessageDialog(this, "Scramble: \n->Created in Java by Parth Prajapati."
                + "\n->University: Dhirubhai Ambani Institute of Information and Communication Technology.\n"
                + "->Info: 201501211, First year, B.Tech. ");
    }
    
    public void exit(){
        int e = JOptionPane.showConfirmDialog(this, "Are you sure want to stop playing?","Exit",JOptionPane.YES_NO_OPTION);
        if(e==0)    System.exit(0);
    }
    
    public static void main(String[] args){
        Scramble sc = new Scramble();
        sc.setVisible(true);
        sc.setSize(300,300);
        sc.setDefaultCloseOperation(EXIT_ON_CLOSE);
        sc.setResizable(false);
        sc.setLocation(500,200);
        sc.setTitle("Scramble");
    }
}
