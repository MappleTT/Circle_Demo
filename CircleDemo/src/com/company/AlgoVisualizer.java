package com.company;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    private Circle []circles;
    private AlgoFrame frame;
    private boolean isAnimated=true;


    public AlgoVisualizer(int sceneWidth,int sceneHeight,int N){
        circles =new Circle[N];
        int R=50;
        for(int i=0;i<N;i++){
            int x=(int)(Math.random()*(sceneWidth-2*R))+R;
            int y=(int)(Math.random()*(sceneHeight-2*R))+R;
            int vx=(int)(Math.random()*11)-7;
            int vy=(int)(Math.random()*11)-7;
            circles[i]=new Circle(x,y,R,vx,vy);
        }

        //事件分发线程,初始化视图
        EventQueue.invokeLater(()->{
            frame=new AlgoFrame("Welcome",sceneWidth,sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(()->{
                    run();
            }).start();

        });

    }

    //动画逻辑
    public void run(){
        while(true){
            //绘制数据
            frame.render(circles);
            AlgoVisHelper.pause(7);

            //更新数据
            if(isAnimated)
                for(Circle circle:circles)
                    circle.move(0,0,frame.getCanvasWidth(),frame.getCanvasHeight());
        }
    }

    public class AlgoKeyListener extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyChar()==' '){
                    isAnimated=!isAnimated;
            }
        }
    }

    private class AlgoMouseListener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            e.translatePoint(0,
                    -(frame.getBounds().height-frame.getCanvasHeight()));
            //System.out.println(e.getPoint());

            for(Circle circle:circles){
                if(circle.contain(e.getPoint())){
                        circle.isFilled=!circle.isFilled;
                }
            }
        }
    }
    public static void main(String[] args) {
        int sceneWidth=800;
        int sceneHeight=800;
        int N=15;
        AlgoVisualizer algoVisualizer=new AlgoVisualizer(sceneWidth,sceneHeight,N);
    }

}
