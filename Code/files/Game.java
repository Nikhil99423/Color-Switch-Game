package g;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static javafx.scene.paint.Color.*;

public class Game extends Application implements Serializable {
    ArrayList<Group> obs;
    ArrayList<Boolean> stars_travelled;
    Circle ball=new Circle();
    ArrayList<String> code;
    ArrayList<Circle> starsgroup;
    ArrayList<Circle> makecolourchangeballgroup;

    int count=0,stars_count=0;
    public static void main(String args[])
    {
        launch(args);
    }
    @Override
    public void start(Stage stage){
        stage.setTitle("COLOUR-SWITCH");
        stage.getIcons().add(new Image("1.png"));
        Button start_new_game=new Button("Start New Game");
        Button load_save_game=new Button("Load Save Game");
        Button exit_game=new Button("Exit Game");
        StackPane main_screen_spane=new StackPane();
        Pane main_screen_pane=new Pane();
        start_new_game.setBackground(new Background(new BackgroundFill(RED, CornerRadii.EMPTY, Insets.EMPTY)));
        load_save_game.setBackground(new Background(new BackgroundFill(RED, CornerRadii.EMPTY, Insets.EMPTY)));
        exit_game.setBackground(new Background(new BackgroundFill(RED, CornerRadii.EMPTY, Insets.EMPTY)));

        //adding coordinates of buttons to main screen of game
        start_new_game.setLayoutX(200);
        start_new_game.setLayoutY(150);
        load_save_game.setLayoutX(200);
        load_save_game.setLayoutY(200);
        exit_game.setLayoutX(200);
        exit_game.setLayoutY(250);

        start_new_game.setStyle("-fx-border-color: #00FFFF; -fx-border-width: 1.5px;");
        load_save_game.setStyle("-fx-border-color: #00FFFF; -fx-border-width: 1.5px;");
        exit_game.setStyle("-fx-border-color: #00FFFF; -fx-border-width: 1.5px;");

        //adding buttons to the main screen pane
        main_screen_pane.getChildren().add(start_new_game);
        main_screen_pane.getChildren().add(load_save_game);
        main_screen_pane.getChildren().add(exit_game);

        Group c1=getCircle(90.0f,480.0f,470.0f,6.0f);
        Group c2=getCircle(72.0f,480.0f,470.0f,6.0f);
        Group c3=getCircle(55.0f,480.0f,470.0f,6.0f);
        Group c5=getCircle(75.0f,110.0f,110.0f,6.0f);
        Group c6=getCircle(55.0f,110.0f,110.0f,6.0f);

        Group triangle1=getTraingle(80,425,8,52,RED,GREEN,YELLOW);
        Group triangle2=getTraingle(450,110,8,52,RED,BLUE,YELLOW);

        main_screen_pane.getChildren().addAll(c1,c2,c3,c5,c6);

        main_screen_pane.getChildren().add(triangle1);
        main_screen_pane.getChildren().add(triangle2);

        rotateMainScreenCircles(c1,1000,-1080,1080,20000);
        rotateMainScreenCircles(c5,1000,-1080,1080,20000);
        rotateMainScreenCircles(c6,1000,-950,950,20000);
        rotateMainScreenCircles(c2,1000,-720,720,20000);
        rotateMainScreenCircles(c3,1000,-900,900,20000);//ms
//        rotateMainScreenCircles(triangle1,1000,-1080,1080,20000);
//        rotateMainScreenCircles(triangle2,1000,1080,-1080,20000);

        main_screen_spane.getChildren().add(main_screen_pane);
        Scene main_screen_scene=new Scene(main_screen_spane,600,800);

        main_screen_pane.setBackground(new Background(new BackgroundFill(BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        start_new_game.setOnAction(e ->{
            code=new ArrayList<>();
            obs=new ArrayList<>();
            stars_travelled=new ArrayList<>();
            starsgroup=new ArrayList<>();
            makecolourchangeballgroup=new ArrayList<>();
            ArrayList<Boolean> travelled=new ArrayList<>();

            count=0;
            Pane start_new_game_pane=new Pane();
            Pane background_pane =new Pane();
            Pane main_pane=new Pane();
            Color ball_colour;
            while (true)
            {
                ball_colour=changeBallColour();
                if(ball_colour.equals(RED) || ball_colour.equals(YELLOW) || ball_colour.equals(GREEN))
                    break;
            }
            ball = new Circle(15, ball_colour);
            start_new_game_pane.getChildren().add(ball);
            //ball.relocate(250,425);
            ball.setCenterX(250);
            ball.setCenterY(700);

            Button pause_button=new Button("Pause");
            Button back_button=new Button("Back");
            Label score_label=new Label("SCORE : ");
            Label score_number=new Label("0");
            Label stars_label=new Label("STARS :");
            Label stars_collected=new Label("0");
            // stars_label.setLayoutX();
            score_label.setLayoutX(40);
            score_label.setLayoutY(15);
            stars_label.setLayoutX(40);
            stars_label.setLayoutY(50);
            score_number.setLayoutX(130);
            score_number.setLayoutY(15);
            stars_collected.setLayoutX(130);
            stars_collected.setLayoutY(50);
            score_label.setScaleX(2);
            score_label.setScaleY(2);
            stars_label.setScaleX(2);
            stars_label.setScaleY(2);
            stars_collected.setScaleX(2);
            stars_collected.setScaleY(2);
            score_number.setScaleX(2);
            score_number.setScaleY(2);
            stars_collected.setTextFill(Color.web("#FFD700"));
            stars_label.setTextFill(Color.web("#008000"));
            score_label.setTextFill(Color.web("#008000"));
            score_number.setTextFill(Color.web("#FFD700"));
            pause_button.setBackground(new Background(new BackgroundFill(DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            back_button.setBackground(new Background(new BackgroundFill(DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

            pause_button.setStyle("-fx-border-color: #EAA221; -fx-border-width: 1.5px;");
            back_button.setStyle("-fx-border-color: #EAA221; -fx-border-width: 1.5px;");
            background_pane.getChildren().addAll(score_label,score_number,pause_button,back_button,stars_label,stars_collected);
//            Color cc1;
//            Color cc2;
//            Color cc3;
//            cc1 = ball_colour;
//            while (true){
//                cc2=changeBallColour();
//                if(!cc2.equals(cc1))
//                {
//                    cc3=changeBallColour();
//                    if(!cc3.equals(cc1) && !cc3.equals(cc2))
//                        break;
//                }
//            }
            code.add("Triangle");
            obs.add(getTraingle(ball.boundsInParentProperty().get().getMinX()-124,ball.boundsInParentProperty().get().getMinY()-225,8,240, RED,YELLOW,GREEN));
            travelled.add(false);
            start_new_game_pane.getChildren().add(obs.get(0));
            main_pane.getChildren().addAll(background_pane,start_new_game_pane);
            pause_button.setLayoutX(540);
            pause_button.setLayoutY(10);
            back_button.setLayoutX(540);
            back_button.setLayoutY(45);
            Scene start_new_game_scene=new Scene(main_pane,600,800);
            main_pane.setBackground(new Background(new BackgroundFill(BLACK,CornerRadii.EMPTY, Insets.EMPTY)));
            VBox resurrection_pane=new VBox();
            resurrection_pane.setSpacing(30);
            resurrection_pane.setAlignment(Pos.CENTER);
            Label stars_left_label=new Label("You have "+Integer.toString(stars_count)+" stars left!");
            Label resurrection_label=new Label("Do you want to use your stars?");
            stars_left_label.setScaleX(2);
            stars_left_label.setScaleY(2);
            resurrection_label.setScaleX(2);
            resurrection_label.setScaleY(2);
            resurrection_label.setTextFill(Color.web("#008000"));
            stars_left_label.setTextFill(Color.web("#008000"));
            Button yes_button=new Button("YES");
            Button no_button=new Button("NO");
            resurrection_pane.getChildren().addAll(stars_left_label,resurrection_label,yes_button,no_button);
            stage.setScene(start_new_game_scene);
            int dy=25;
            ArrayList<Integer> scroll=new ArrayList<>();
            scroll.add(270);
            AnimationTimer timer=new AnimationTimer() {
                int i = 0,r=4,k=0;
                @Override
                public void handle(long l) {
                    start_new_game_scene.setOnKeyPressed(press -> {
                        TranslateTransition translate = new TranslateTransition();
                        if (press.getCode().toString().equalsIgnoreCase("W")) {
                            ball.setLayoutY(ball.getLayoutY() - dy);
                        }
                        translate.setByY(1000);
                        translate.setDuration(Duration.millis(4000));
                        translate.setNode(ball);
                        translate.play();
                        if (i < obs.size() && !start_new_game_pane.getChildren().get(start_new_game_pane.getChildren().size() - 1).equals(obs.get(i)))
                        {
                            start_new_game_pane.getChildren().add(obs.get(i));
                        }
                        if(checkGameOver(ball,code.get(i),obs.get(i)) || (i-1>=0 && checkGameOver(ball,code.get(i-1),obs.get(i-1))) || (i-2>=0 && checkGameOver(ball,code.get(i-2),obs.get(i-2)))){
                            if(stars_count==0)
                                gameOver(stage,main_screen_scene);
                            else{
                                Scene resurrection_scene=new Scene(resurrection_pane,600,800);
                                stage.setScene(resurrection_scene);
                                resurrection_pane.setBackground(new Background(new BackgroundFill(BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                                yes_button.setOnAction(exx->{
                                    stars_count-=1;
                                    stage.setScene(start_new_game_scene);
                                });
                                no_button.setOnAction(exx1->{
                                    stage.setScene(main_screen_scene);
                                });
                            }
                        }
                        if(i-1>=0 && !travelled.get(i-1) && code.get(i-1).equals("Line") && obs.get(i-1).boundsInParentProperty().get().getMinY()-ball.boundsInParentProperty().get().getMinY()>=0 && obs.get(i-1).boundsInParentProperty().get().getMinY()-ball.boundsInParentProperty().get().getMinY()<=30)
                        {
                            travelled.set(i-1,true);
                            checkScore(ball,code.get(i-1),obs.get(i-1),score_number);
                        }
                        if(starsgroup.size()>=1) {
                            stars_travelled.set(starsgroup.size() - 1,true);
                            smallobjectcollision_stars(ball, starsgroup.get(starsgroup.size() - 1), start_new_game_pane, stars_collected);
                        }
                        if(makecolourchangeballgroup.size()>=1){
                            if(code.get(i-1).equals("Line"))
                                smallobjectcollision_change(ball,makecolourchangeballgroup.get(makecolourchangeballgroup.size()-2),start_new_game_pane,obs.get(i-1),code.get(i-1));
                            else
                                smallobjectcollision_change(ball,makecolourchangeballgroup.get(makecolourchangeballgroup.size()-1),start_new_game_pane,obs.get(i),code.get(i));
                        }

                        if (i < obs.size() && ball.boundsInParentProperty().get().getMinY()-150< obs.get(i).boundsInParentProperty().get().getMinY()) {
                            if(!code.get(i).equals("Line"))
                            {
                                travelled.set(i,true);
                                checkScore(ball,code.get(i),obs.get(i),score_number);
                            }
                            TranslateTransition transition_pane=new TranslateTransition();
                            transition_pane.setByY(scroll.get(i));
                            transition_pane.setDuration(Duration.millis(1000));
                            transition_pane.setNode(start_new_game_pane);
                            transition_pane.play();
                            Random rand = new Random();
                            r = rand.nextInt(3);//0 to 3
                            if (r == 0) {
                                code.add("Square");
                                scroll.add(320);
                                travelled.add(false);
                                obs.add(getSquare(ball.boundsInParentProperty().get().getMinX()-70,ball.boundsInParentProperty().get().getMinY()-280,1000,900,-900,35000,130));
                            }
                            else if(r==1){
                                code.add("Line");
                                scroll.add(190);
                                travelled.add(false);
                                obs.add(getLineObstacle(ball.boundsInParentProperty().get().getMinY()-300, 8));//baad me anna at 1 pm???
                            }
                            else if(r==2){
//                                Color c11,c22,c33;
//                                c11 = ball_colour;
//                                while (true){
//                                    c22=changeBallColour();
//                                    if(!c22.equals(c11))
//                                    {
//                                        c33=changeBallColour();
//                                        if(!c33.equals(c11) && !c33.equals(c22))
//                                            break;
//                                    }
//                                }
                                scroll.add(290);
                                code.add("Triangle");
                                travelled.add(false);
                                obs.add(getTraingle(ball.boundsInParentProperty().get().getMinX()-124,ball.boundsInParentProperty().get().getMinY()-280,8,240,RED,YELLOW,GREEN));
//                                code.add("NormalCircle");
//                                obs.add(getCircle(58,ball.boundsInParentProperty().get().getMinX()+10,ball.boundsInParentProperty().get().getMinY()-240,8));
                            }
                            makecolourchangeballgroup.add(makecolourchangeball(obs.get(i).boundsInParentProperty().get().getMinY()-60));
                            start_new_game_pane.getChildren().add(makecolourchangeballgroup.get(makecolourchangeballgroup.size()-1));
                            Random randomnumber= new Random();
                            int checkrandom=randomnumber.nextInt(3);
                            if(checkrandom==1){
                                stars_travelled.add(false);
                                starsgroup.add(makestars(obs.get(i).boundsInParentProperty().get().getMinY()));
                                start_new_game_pane.getChildren().add(starsgroup.get(starsgroup.size()-1));
                            }
                            i++;
                        }
                    });
                }
            };
            timer.start();
            pause_button.setOnAction(e1 -> {
                pauseGame(stage,start_new_game_scene);
            });
            back_button.setOnAction((e2 -> {
                stage.setScene(main_screen_scene);
            }));
        });
        load_save_game.setOnAction(e -> {
            Label label1=new Label("Choose the Filename from the list of Game files");
            VBox load_game_pane=new VBox();
            TextField filename_input=new TextField();
            filename_input.setMaxSize(200,50);
            label1.setScaleX(2);
            label1.setScaleY(2);
            label1.setTextFill(Color.web("#87ceeb"));
            load_game_pane.getChildren().add(label1);
            load_game_pane.setAlignment(Pos.CENTER);
            load_game_pane.setSpacing(50);
            File file=new File("C:\\\\Users\\\\NIKHIL PRASAD\\\\IdeaProjects\\\\Game Colour Switch");
            if(file.isDirectory()){
                File[] files=file.listFiles();
                for(File i:files){
                    if(i.getName().length()>=4 && i.getName().substring(i.getName().length()-4).equals(".txt"))
                    {
                        Label filename=new Label();
                        filename.setText(i.getName().substring(0,i.getName().length()-4));
                        filename.setScaleX(2);
                        filename.setScaleY(2);
                        filename.setTextFill(Color.web("#008000"));
                        load_game_pane.getChildren().add(filename);
                    }
                }
            }
            Button load_button=new Button("LOAD GAME");
            Label show=new Label("Enter the FileName to load Game");
            load_game_pane.getChildren().addAll(show,filename_input,load_button);
            show.setScaleX(2);
            show.setScaleY(2);
            show.setTextFill(Color.web("#FF8C00"));
            load_button.setOnAction(ex ->{
                Label error=new Label("Error! Enter Filename");
                Label nofile=new Label("It is not a Game File");
                if(filename_input.getText().length()==0)
                {
                    load_game_pane.getChildren().add(error);
                    error.setScaleX(2);
                    error.setScaleY(2);
                    error.setTextFill(Color.web("#FF0000"));
                }
                else {
                    if(load_game_pane.getChildren().get(load_game_pane.getChildren().size()-1).equals(error)) {
                        System.out.println("yess");
                        load_game_pane.getChildren().remove(error);
                    }
//                    else{
//                        try {
//                            deserialising deserialising=new deserialising(filename_input.getText());
//                        } catch (IOException ioException) {
//                            load_game_pane.getChildren().add(nofile);
//                            nofile.setScaleX(2);
//                            nofile.setScaleY(2);
//                            nofile.setTextFill(Color.web("#FF0000"));
//                        }
//                    }
                }
            });
            load_game_pane.setBackground(new Background(new BackgroundFill(BLACK,CornerRadii.EMPTY, Insets.EMPTY)));
            Scene save_game_scene=new Scene(load_game_pane,600,800);
            stage.setScene(save_game_scene);

        });
        exit_game.setOnAction(e -> {
            System.exit(0);
        });
        stage.setScene(main_screen_scene);
        stage.show();
    }
    public boolean checkForSquare(Circle ball, Group sq){
        Line l1=(Line) sq.getChildren().get(0);
        Line l2=(Line) sq.getChildren().get(1);
        Line l3=(Line) sq.getChildren().get(2);
        Line l4=(Line) sq.getChildren().get(3);
        Shape in1=Shape.intersect(ball,l1);
        Shape in2=Shape.intersect(ball,l2);
        Shape in3=Shape.intersect(ball,l3);
        Shape in4=Shape.intersect(ball,l4);
        if((in1.getBoundsInParent().getWidth()!=-1 && !((l1).getStroke().equals(ball.getFill()))) || (in2.getBoundsInParent().getWidth()!=-1 && !((l2).getStroke().equals(ball.getFill()))) || (in3.getBoundsInParent().getWidth()!=-1 && !((l3).getStroke().equals(ball.getFill()))) || (in4.getBoundsInParent().getWidth()!=-1 && !((l4).getStroke().equals(ball.getFill()))))
            return true;
        return false;
    }
    //    public boolean checkFor2ConcentricCircles(Circle ball,Group twocircs){
////        Group gp1=(Group) twocircs.getChildren().get(0);
////        Group gp2=(Group) twocircs.getChildren().get(1);
//        Arc arc1=(Arc)((Group)twocircs.getChildren().get(0)).getChildren().get(0);
//        Arc arc2=(Arc)((Group)twocircs.getChildren().get(0)).getChildren().get(1);
//        Arc arc3=(Arc) ((Group)twocircs.getChildren().get(0)).getChildren().get(2);
//        Arc arc4=(Arc)((Group)twocircs.getChildren().get(0)).getChildren().get(3);
//        Arc arc5=(Arc)((Group)twocircs.getChildren().get(1)).getChildren().get(0);
//        Arc arc6=(Arc)((Group)twocircs.getChildren().get(1)).getChildren().get(1);
//        Arc arc7=(Arc)((Group)twocircs.getChildren().get(1)).getChildren().get(2);
//        Arc arc8=(Arc)((Group)twocircs.getChildren().get(1)).getChildren().get(3);
//        Shape in1=Shape.intersect(ball,arc1);
//        Shape in2=Shape.intersect(ball,arc2);
//        Shape in3=Shape.intersect(ball,arc3);
//        Shape in4=Shape.intersect(ball,arc4);
//        Shape in5=Shape.intersect(ball,arc5);
//        Shape in6=Shape.intersect(ball,arc6);
//        Shape in7=Shape.intersect(ball,arc7);
//        Shape in8=Shape.intersect(ball,arc8);
//        if((in1.getBoundsInLocal().getWidth()!=-1 && !((arc1).getStroke().equals(ball.getFill()))) || ((in2.getBoundsInLocal().getWidth()!=-1 && !((arc2).getStroke().equals(ball.getFill())))) || (in3.getBoundsInLocal().getWidth()>=0 && !((arc3).getStroke().equals(ball.getFill()))) || (in4.getBoundsInLocal().getWidth()>=0 && !((arc4).getStroke().equals(ball.getFill()))) || (in5.getBoundsInLocal().getWidth()!=-1 && !((arc5).getStroke().equals(ball.getFill()))) || (in6.getBoundsInLocal().getWidth()!=-1 && !((arc6).getStroke().equals(ball.getFill()))) || (in7.getBoundsInLocal().getWidth()!=-1 && !((arc7).getStroke().equals(ball.getFill()))) || (in8.getBoundsInLocal().getWidth()!=-1 && !((arc8).getStroke().equals(ball.getFill()))))
//            return true;
//        return false;
//    }
    public boolean checkline(Circle ball, Group lines){
        Line l1=(Line) lines.getChildren().get(0);
        Line l2=(Line) lines.getChildren().get(1);
        Line l3=(Line) lines.getChildren().get(2);
        Line l4=(Line) lines.getChildren().get(3);
        Line l5=(Line) lines.getChildren().get(4);
        Line l6=(Line) lines.getChildren().get(5);
        Line l7=(Line) lines.getChildren().get(6);
        Line l8=(Line) lines.getChildren().get(7);
        Line l9=(Line) lines.getChildren().get(8);
        Line l10=(Line) lines.getChildren().get(9);
        Shape in1=Shape.intersect(ball,l1);
        Shape in2=Shape.intersect(ball,l2);
        Shape in3=Shape.intersect(ball,l3);
        Shape in4=Shape.intersect(ball,l4);
        Shape in5=Shape.intersect(ball,l5);
        Shape in6=Shape.intersect(ball,l6);
        Shape in7=Shape.intersect(ball,l7);
        Shape in8=Shape.intersect(ball,l8);
        Shape in9=Shape.intersect(ball,l9);
        Shape in10=Shape.intersect(ball,l10);
        if((in1.getBoundsInParent().getWidth()!=-1 && !(l1).getStroke().equals(ball.getFill())) || (in2.getBoundsInParent().getWidth()!=-1 && !(l2).getStroke().equals(ball.getFill())) || (in3.getBoundsInParent().getWidth()!=-1 && !(l3).getStroke().equals(ball.getFill()))|| (in4.getBoundsInParent().getWidth()!=-1 && !(l4).getStroke().equals(ball.getFill())) || (in5.getBoundsInParent().getWidth()!=-1 && !(l5).getStroke().equals(ball.getFill())) || (in6.getBoundsInParent().getWidth()!=-1 && !(l6).getStroke().equals(ball.getFill())) || (in7.getBoundsInParent().getWidth()!=-1 && !(l7).getStroke().equals(ball.getFill())) || (in8.getBoundsInParent().getWidth()!=-1 && !(l8).getStroke().equals(ball.getFill())) || (in9.getBoundsInParent().getWidth()!=-1 && !(l9).getStroke().equals(ball.getFill())) || (in10.getBoundsInParent().getWidth()!=-1 && !(l10).getStroke().equals(ball.getFill())))
            return true;
        return false;

    }
    public boolean checkGameOver(Circle ball,String r,Group obs){
        if(r.equals("Line"))
            return checkline(ball,obs);
        else if(r.equals("Square"))
            return checkForSquare(ball,obs);
        else if(r.equals("Triangle"))
            return checkTraingle(ball,obs);
        return false;
    }
    public void checkScore(Circle ball,String r,Group obs,Label score){
        if(r.equals("Line") && !checkline(ball,obs)){
            count+=10;
            score.setText(Integer.toString(count));
        }

        else if(r.equals("Square") && !checkForSquare(ball,obs)) {
            count+=10;
            score.setText(Integer.toString(count));
        }
        else if(r.equals("Triangle") && !checkTraingle(ball,obs)){
            count+=10;
            score.setText(Integer.toString(count));
        }
    }

    public boolean checkTraingle(Circle ball, Group obs) {
        Line l1= (Line) obs.getChildren().get(0);
        Line l2= (Line) obs.getChildren().get(1);
        Line l3= (Line) obs.getChildren().get(2);
        Shape in1=Shape.intersect(ball,l1);
        Shape in2=Shape.intersect(ball,l2);
        Shape in3=Shape.intersect(ball,l3);
        if(in1.getBoundsInLocal().getWidth()!=-1 && !l1.getStroke().equals(ball.getFill()) || in2.getBoundsInLocal().getWidth()!=-1 && !l2.getStroke().equals(ball.getFill()) || in3.getBoundsInLocal().getWidth()!=-1 && !l3.getStroke().equals(ball.getFill())){
            return true;
        }
        return false;

    }

    public Group getLineObstacle(double ycoordinate,double width){
        Line L13=new Line(-300,ycoordinate,-200,ycoordinate);
        Line L12=new Line(-200,ycoordinate,-100,ycoordinate);
        Line L11=new Line(-100,ycoordinate,0,ycoordinate);
        Line L1=new Line(0,ycoordinate,100,ycoordinate);
        Line L2=new Line(100,ycoordinate,200,ycoordinate);
        Line L3=new Line(200,ycoordinate,300,ycoordinate);
        Line L4=new Line(300,ycoordinate,400,ycoordinate);
        Line L5=new Line(400,ycoordinate,500,ycoordinate);
        Line L6=new Line(500,ycoordinate,600,ycoordinate);
        Line L61=new Line(600,ycoordinate,700,ycoordinate);
        Line L62=new Line(700,ycoordinate,800,ycoordinate);
        Line L63=new Line(800,ycoordinate,900,ycoordinate);
        L11.setStroke(BLUE);
        L12.setStroke(GREEN);
        L13.setStroke(RED);
        L1.setStroke(YELLOW);
        L2.setStroke(GREEN);
        L3.setStroke(RED);
        L4.setStroke(BLUE);
        L5.setStroke(GREEN);
        L6.setStroke(YELLOW);
        L61.setStroke(RED);
        L62.setStroke(BLUE);
        L63.setStroke(GREEN);
        L11.setStrokeWidth(width);
        L12.setStrokeWidth(width);
        L13.setStrokeWidth(width);
        L1.setStrokeWidth(width);
        L2.setStrokeWidth(width);
        L3.setStrokeWidth(width);
        L4.setStrokeWidth(width);
        L5.setStrokeWidth(width);
        L6.setStrokeWidth(width);
        L61.setStrokeWidth(width);
        L62.setStrokeWidth(width);
        L63.setStrokeWidth(width);
        Group lines=new Group();
        lines.getChildren().addAll(L1,L2,L3,L4,L5,L6,L11,L12,L13,L63,L61,L62);
        TranslateTransition t=new TranslateTransition();
        t.setByX(289);
        t.setNode(lines);
        t.setDuration(Duration.millis(5000));
        t.setCycleCount(500);
        t.setAutoReverse(true);
        t.play();
        return lines;
    }
    public Group getSquare(double x, double y, int count, int sa, int ea, int dur,int len){
        Line l1= new Line(x,y,x+len,y);
        Line l2= new Line(x,y,x,y-len);
        Line l3= new Line(x,y-len,x+len,y-len);
        Line l4= new Line(x+len,y-len,x+len,y);
        l1.setStroke(RED);
        l2.setStroke(YELLOW);
        l3.setStroke(BLUE);
        l4.setStroke(GREEN);
        l1.setStrokeWidth(8);
        l2.setStrokeWidth(8);
        l3.setStrokeWidth(8);
        l4.setStrokeWidth(8);
        Group sq=new Group();
        sq.getChildren().addAll(l1,l2,l3,l4);
        RotateTransition r=new RotateTransition();
        rotateMainScreenCircles(sq,count,sa,ea,dur);
        return sq;
    }

    public void rotateMainScreenCircles(Group group,double count,double start_angle,double end_angle,double duration)
    {
        RotateTransition rotate=new RotateTransition();
        rotate.setFromAngle(start_angle);
        rotate.setToAngle(end_angle);
        rotate.setCycleCount((int)count);
        rotate.setDuration(Duration.millis(duration));
        rotate.setAutoReverse(true);
        rotate.setNode(group);
        rotate.play();
    }
    public Color changeBallColour()
    {
        Color arr[]={RED, YELLOW, BLUE, GREEN};
        return arr[ThreadLocalRandom.current().nextInt(0, 3 + 1)];
    }
    public void pauseGame(Stage stage,Scene mainscreen){
        Pane menu=new Pane();
        Button endgame=new Button("Exit");
        Button savegame=new Button("Save Game");
        Button resumegame=new Button("Resume Game");
        Image image=new Image(Game.class.getResourceAsStream("2.png"));
        ImageView imageView=new ImageView(image);
        imageView.setX(25);
        imageView.setY(25);
        imageView.setFitHeight(550);
        imageView.setFitWidth(550);

        endgame.setBackground(new Background(new BackgroundFill(GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
        savegame.setBackground(new Background(new BackgroundFill(GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
        resumegame.setBackground(new Background(new BackgroundFill(GOLD, CornerRadii.EMPTY, Insets.EMPTY)));

        endgame.setStyle("-fx-border-color: #ff0000; -fx-border-width: 1.5px;");
        savegame.setStyle("-fx-border-color: #FF0000; -fx-border-width: 1.5px;");
        resumegame.setStyle("-fx-border-color: #FF0000; -fx-border-width: 1.5px;");
        resumegame.setLayoutX(200);
        resumegame.setLayoutY(200);
        savegame.setLayoutX(200);
        savegame.setLayoutY(250);
        endgame.setLayoutX(200);
        endgame.setLayoutY(300);
        Text text=new Text("Press w to move the BALL");
        menu.getChildren().add(text);
        text.setLayoutX(200);
        text.setLayoutY(350);
        savegame.setOnAction(e1 ->{
            VBox save_game_pane=new VBox();
            Button done=new Button("Done");
            save_game_pane.setAlignment(Pos.CENTER);
            save_game_pane.setSpacing(100);
            Label type=new Label("Enter the File Name to be saved : ");
            Label show=new Label("Error! Enter the filename");
            Label done_saving=new Label("Game Saved Successfully");
            TextField filename=new TextField();
            type.setLayoutX(250);
            type.setLayoutY(380);
            type.setScaleX(2);
            type.setScaleY(2);
            type.setTextFill(Color.web("#008000"));
            filename.setAlignment(Pos.CENTER);
            filename.setMaxSize(200,15);
            save_game_pane.getChildren().addAll(type,filename,done);
            Scene save_game_scene=new Scene(save_game_pane,600,800);
            save_game_pane.setBackground(new Background(new BackgroundFill(BLACK,CornerRadii.EMPTY, Insets.EMPTY)));
            stage.setScene(save_game_scene);
            done.setOnAction(e4->{

                if(filename.getText().length()==0)
                {
                    save_game_pane.getChildren().add(show);
                    show.setScaleX(2);
                    show.setScaleY(2);
                    show.setTextFill(Color.web("#FF0000"));
                }
                else {
                    if(save_game_pane.getChildren().get(save_game_pane.getChildren().size()-1).equals(show))
                        save_game_pane.getChildren().remove(show);
                    done_saving.setScaleX(2);
                    done_saving.setScaleY(2);
                    done_saving.setTextFill(Color.web("#008000"));
                    save_game_pane.getChildren().add(done_saving);
//                    try {
//                        forserialising A=new forserialising(filename.getText(),ball,obs);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            });
        });
        resumegame.setOnAction(e -> {
            stage.setScene(mainscreen);
        });
        endgame.setOnAction(e-> {
            System.exit(0);
        });
        menu.getChildren().add(imageView);
        menu.getChildren().add(resumegame);
        menu.getChildren().add(savegame);
        menu.getChildren().add(endgame);

        stage.setScene(new Scene(menu,600,800));
    }

    public Group getTraingle(double sx,double sy,double width,double len,Color c1,Color c2,Color c3)
    {
        Line l1=getLine(sx,sy,sx+len,sy,width,c1);
        Line l2=getLine(sx+len,sy,sx+len/2,sy-Math.sqrt(Math.pow(len,2)-Math.pow((len/2),2)),width,c2);
        Line l3=getLine(sx+len/2,sy-Math.sqrt(Math.pow(len,2)-Math.pow((len/2),2)),sx,sy,width,c3);
        Group group=new Group();
        group.getChildren().addAll(l1,l2,l3);
        rotateMainScreenCircles(group,500,900,-900,35000);
        return group;
    }
    public Line getLine(double startX,double startY,double endX,double endY,double width,Color color){
        Line line=new Line();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStrokeWidth(width);
        line.setStroke(color);
        return line;
    }
    public Arc getArc(double radius, double cx, double cy, double width, Color color, double angle){
        Arc arc=new Arc();

        arc.setCenterX((float)cx);
        arc.setCenterY((float)cy);
        arc.setRadiusX((float)radius);
        arc.setRadiusY((float)radius);
        arc.setStartAngle((float)angle);
        arc.setLength(((float)((2*Math.PI*(float)radius))/4.0f));
        arc.setType(ArcType.OPEN);
        arc.setStroke(color);
        arc.setStrokeType(StrokeType.CENTERED);
        arc.setStrokeWidth((float)width);
        arc.setFill(null);
        return arc;
    }
    public Circle makestars(double y){
        Circle star=new Circle();
        star.setRadius(5);
        star.setFill(WHITE);
        star.setCenterY(y);
        star.setCenterX(250);
        return star;

    }
    public Circle makecolourchangeball(double y){
        Circle star=new Circle();
        star.setRadius(5);
        star.setFill(BLUE);
        star.setCenterY(y);
        star.setCenterX(250);
        return star;

    }

    public Group getCircle(double r,double x,double y,double w){
        Arc red=getArc(r,x,y,w,RED,0.0f);
        Arc yellow=getArc(r,x,y,w,YELLOW,90.0f);
        Arc blue=getArc(r,x,y,w,BLUE,180.0f);
        Arc green=getArc(r,x,y,w,GREEN,270.0f);

        Group group=new Group();
        group.getChildren().addAll(red,yellow,blue,green);
        return group;
    }
    public void smallobjectcollision_stars(Circle ball,Circle smallball,Pane gp,Label stars_collected){
        Shape inter=Shape.intersect(ball,smallball);
        if(inter.getBoundsInLocal().getWidth()!=-1){
            stars_count++;
            stars_collected.setText(Integer.toString(stars_count));
            if(gp.getChildren().contains(smallball)){
                gp.getChildren().remove(smallball);
            }
        }
    }
    public void smallobjectcollision_change(Circle ball,Circle smallball,Pane gp,Group o1,String c1){
        Shape inter=Shape.intersect(ball,smallball);
        if(inter.getBoundsInLocal().getWidth()!=-1){
            Color c;
            while (true){
                c=changeBallColour();
                System.out.println("outing");
                //o1:i-1   o2:i

                    if(c1.equals("Triangle")){
                        System.out.println("in c1");
                        if(!c.equals(ball.getFill()) && (c.equals(RED) || c.equals(YELLOW) || c.equals(GREEN)))
                            break;
                    }
                    else {
                        if(!c.equals(ball.getFill())){
                            break;
                        }
                    }

            }
            ball.setFill(c);
            if(gp.getChildren().contains(smallball))
                gp.getChildren().remove(smallball);
        }
    }
    public void gameOver(Stage stage,Scene scene){
        Label over=new Label("GAME OVER");
        Button restart_game=new Button("RESTART GAME");
        Button exit=new Button("Exit Game");
        Label score=new Label("Score");
        Pane game_over_pane=new Pane();
        over.setLayoutX(190);
        over.setLayoutY(160);
        exit.setLayoutX(190);
        exit.setLayoutY(340);
        score.setLayoutX(190);
        score.setLayoutY(260);
        restart_game.setLayoutX(190);
        restart_game.setLayoutY(380);
        over.setScaleX(5);
        over.setScaleY(7);
        score.setScaleX(5);
        score.setScaleY(7);
        over.setTextFill(Color.web("#ff0000"));
        score.setTextFill(Color.web("#008000"));
        game_over_pane.getChildren().addAll(over,exit,score,restart_game);
        restart_game.setBackground(new Background(new BackgroundFill(RED, CornerRadii.EMPTY, Insets.EMPTY)));
        exit.setBackground(new Background(new BackgroundFill(RED, CornerRadii.EMPTY, Insets.EMPTY)));
        exit.setStyle("-fx-border-color: #00FFFF; -fx-border-width: 1.5px;");
        restart_game.setStyle("-fx-border-color: #00FFFF; -fx-border-width: 1.5px;");
        restart_game.setOnAction(e1 -> {
            stage.setScene(scene);
        });
        exit.setOnAction(e2 ->{
            System.exit(0);
        });
        game_over_pane.setBackground(new Background(new BackgroundFill(BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene game_over_scene=new Scene(game_over_pane,600,800);
        stage.setScene(game_over_scene);
    }
}
class savemygame implements Serializable{
    //    double ballgetx;
//    double ballgety;
//    double getobx;
//    double getoby;
//    int score;
    String balldetail;
    String obsdet;
    savemygame(String balldetail,String obsdet){
        this.balldetail=balldetail;
        this.obsdet=obsdet;
//        this.ballgetx=ballgetx;
//        this.ballgety=ballgety;
//        this.getobx=getobx;
//        this.getoby=getoby;

    }
}