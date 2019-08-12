package com.company;

import javax.swing.*;
import java.awt.*;
public class AlgoFrame extends JFrame {

    private int canvasHeight;
    private int canvasWidth;

    public AlgoFrame(String title,int canvasWidth,int canvasHeight){

        super(title);
        this.canvasWidth=canvasWidth;
        this.canvasHeight=canvasHeight;
        AlgoCanvas canvas=new AlgoCanvas();
        canvas.setPreferredSize(new Dimension(canvasWidth,canvasHeight));
        setContentPane(canvas);

        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public AlgoFrame(String title){
           this(title,1024,768);
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }
    public int getCanvasWidth() {
        return canvasWidth;
    }

    //设置自己的数据
    private Circle [] circles;
    public void render(Circle []circles){
        this.circles=circles;
        repaint();
    }


    private class AlgoCanvas extends JPanel{

        public AlgoCanvas(){
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d=(Graphics2D) g;
            //抗锯齿
            RenderingHints hints=new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.addRenderingHints(hints);

            //具体绘制
            AlgoVisHelper.setStrokeWidth(g2d,1);
            AlgoVisHelper.setColor(g2d);
            for(Circle circle:circles){
                if(!circle.isFilled)
                    AlgoVisHelper.strokeCircle(g2d,circle.x,circle.y,circle.getR());
                else
                    AlgoVisHelper.fillCircle(g2d,circle.x,circle.y,circle.getR());
            }
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth,canvasHeight);
        }


    }
}
