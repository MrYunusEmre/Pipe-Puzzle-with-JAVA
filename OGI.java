package OGI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Muhammed Enes AKTÜRK - 150117036 , Yunus Emre ERTUNÇ - 150117064
 * This program creates a simple 2D game
 */
public class OGI extends Application {
    
    Stage window ;
    static ArrayList<CreateBoxes> stagesArray = new ArrayList<>();
    static ArrayList<CreateBoxes> stagePassArray = new ArrayList<>();
    static ArrayList<Boolean> hasPassed = new ArrayList<>();
    static ArrayList<Double> pathList = new ArrayList<>();
    static Pane gp , bp , pane;
    static CreateBoxes newClassTile ;
    static boolean hasFinished ,level1Passed , level2Passed , level3Passed ,
            level4Passed , level5Passed ,level6Passed;
    static double WIDTH = 1366 , HEIGHT = 768 , pointX , pointY;
    static Polygon menu , play , options , exit , music , voice , language , back ,time,move, yes , no , nextLevel , oldLevel;
    static Text playT,gameName,optionsT,exitT,languageT,backT,yesT,noT,nextLevelT,oldLevelT ;
    static String playStr , optionsStr , exitStr ,languageStr , backStr , yesStr , noStr,
            exit2Str , nextLevelStr , oldLevelStr , gameNameStr="O . G . I" , moveMessage ,moveStr, timeMessage;
    static int bölüm = 1 , hamleSayısı=0 , basılan , bırakılan , nerdeyim , nerdeydim , secondsPassedint=0;
    static Label moveLabel, secondsLabel=new Label(); //hamle sayısını içine alan label
    int secondsPassed =0;
    Timer timer = new Timer();
    ImageView siw , siw2 ;
    static CreateBoxes basıldı , bırakıldı ;
    Circle ball ;
    static Path path2 ;
    ArcTo arcTo ;   
    static PathTransition pt ;
    Media mediaMusic , mediaVoice ;
    MediaPlayer mpMusic , mpVoice ;
    Slider sliderMusic , sliderVoice ;
    File file = new File("C:\\Users\\enesa\\NetBeansProjects\\ogıdeneme2\\src\\OGI"); 
    
    @Override
    public void start(Stage primaryStage) {
    
        window = primaryStage ; 
        TrString();
        menuOluştur();
        window.setMinHeight(600);
        window.setMinWidth(1000);
        window.show();
        window.setTitle("O . G . I");
       
        mediaMusic = new Media(file.toURI().toString()+"music.mp3");
        mpMusic = new MediaPlayer(mediaMusic);
        mpMusic.play();
        
        sliderMusic = new Slider();
        sliderMusic.setMax(100);
        sliderMusic.setMin(0);
        sliderMusic.setShowTickLabels(true);
        sliderMusic.setScaleX(1.5);
        sliderMusic.setScaleY(1.5);
        sliderMusic.setValue(25);
        mpMusic.volumeProperty().bind(sliderMusic.valueProperty().divide(100));

        mediaVoice = new Media(file.toURI().toString()+"click.mp3");
        mpVoice = new MediaPlayer(mediaVoice);
        
        sliderVoice = new Slider();
        sliderVoice.setMax(100);
        sliderVoice.setMin(0);
        sliderVoice.setShowTickLabels(true);
        sliderVoice.setScaleX(1.5);
        sliderVoice.setScaleY(1.5);
        sliderVoice.setValue(25);
        mpVoice.volumeProperty().bind(sliderVoice.valueProperty().divide(100));
        
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
        window.setOnCloseRequest(e->{
        System.exit(1);
        });
 
    }
    
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Platform.runLater(() -> {
                secondsPassed++;
                secondsLabel.setText(timeMessage + secondsPassed);   
            });
        }

        @Override
        public boolean cancel() {
            return super.cancel(); //To change body of generated methods, choose Tools | Templates.
        }
        
        
    };
    
    /**
    *  In menuOluştur() method , polygons and shapes belonging this menu is created.
    *  These features are same for "Play" , "Options" and "Exit" 
    */
    void menuOluştur(){
        
        secondsPassedint=0;
        Pane pane = new Pane();         // Create pane 
        pane.setStyle("-fx-background-color:Gray");        
        
        double köşe = (WIDTH - HEIGHT)/12 ;
        double boy = (WIDTH-HEIGHT)/10 ;
        double kb = köşe/2 ;
        // Create menu polygon which is the biggest one 
        menu = new Polygon(WIDTH/9 , HEIGHT/8 , WIDTH/2-köşe , HEIGHT/8 , WIDTH/2 , HEIGHT/8+köşe,
                WIDTH/2 , 7*HEIGHT/8 , WIDTH/9+köşe , HEIGHT*7/8 , WIDTH/9 , 7*HEIGHT/8-köşe );
        menu.setFill(Color.DARKGRAY);
        
        // set gameName's coordinates depending on WIDTH and HEIGHT properties 
        gameName = new Text((WIDTH/9+(WIDTH/2 - WIDTH/9)/2.2) ,(HEIGHT/7+(2*kb)) , gameNameStr);
        gameName.setScaleX(3);
        gameName.setScaleY(3);
        
        // creates play Polygon and set coordinates depending on WIDTH and HEIGHT properties
        play = new Polygon((WIDTH/9+kb) , (HEIGHT/2-(2*boy)-kb) , (WIDTH/2-(2*kb)) , (HEIGHT/2-(2*boy)-kb) ,
                (WIDTH/2-kb) ,(HEIGHT/2-(2*boy)) , (WIDTH/2-kb) , (HEIGHT/2-(boy+kb)) , (WIDTH/9+(2*kb)) , 
                (HEIGHT/2-(boy+kb)) , (WIDTH/9+kb) , (HEIGHT/2-(boy+(2*kb))) ) ;
        play.setFill(Color.GRAY);
        
        // creates playText coordinates depending on WIDTH and HEIGHT properties and String 
        playT = new Text((WIDTH/9+(WIDTH/2 - WIDTH/9)/2.2) ,(HEIGHT/2-(boy+2*kb)) , playStr);
        playT.setScaleX(3);
        playT.setScaleY(3);
        
        /*
        *   if mouse entered , stroke of the polygon will be white and when exited back to the normal , if user 
        *   click play polygon or playT text , program will create play Pane using playOluştur() method
        */
        play.setOnMouseEntered(e -> {
            play.setStroke(Color.WHITE);
            mpVoice.play();
            mpVoice = new MediaPlayer(mediaVoice);
            mpVoice.volumeProperty().bind(sliderVoice.valueProperty().divide(100));
        });
        play.setOnMouseClicked(e -> {
            playOluştur();
        });
        play.setOnMouseExited(e -> {
            play.setStroke(Color.TRANSPARENT);
            mpVoice.pause();
        });
        playT.setOnMouseEntered(e -> {
            play.setStroke(Color.WHITE);
        });
        playT.setOnMouseClicked(e -> {
            playOluştur();
        });
        playT.setOnMouseExited(e -> {
            play.setStroke(Color.TRANSPARENT);
        });
        
        // creates options Polygon and set coordinates depending on WIDTH and HEIGHT properties
        options = new Polygon((WIDTH/9+kb) , (HEIGHT/2-boy) , (WIDTH/2-(2*kb)) , (HEIGHT/2-boy) ,
                (WIDTH/2-kb) ,(HEIGHT/2-boy+kb) , (WIDTH/2-kb) , (HEIGHT/2) , (WIDTH/9+(2*kb)) , 
                (HEIGHT/2) , (WIDTH/9+kb) , (HEIGHT/2-kb) ) ;
        options.setFill(Color.GRAY);
        
        // creates optionsText coordinates depending on WIDTH and HEIGHT properties and String
        optionsT = new Text((WIDTH/9+(WIDTH/2 - WIDTH/9)/2.2) ,(HEIGHT/2-kb) , optionsStr);
        optionsT.setScaleX(3);
        optionsT.setScaleY(3);
        
        /*
        *   if mouse entered , stroke of the polygon will be white and when exited back to the normal , if user 
        *   click options polygon or optionsT text , program will create options Pane using opitonsOluştur() method
        */
        options.setOnMouseEntered(e -> {
            options.setStroke(Color.WHITE);
            mpVoice.play();
            mpVoice = new MediaPlayer(mediaVoice);
            mpVoice.volumeProperty().bind(sliderVoice.valueProperty().divide(100));
        });
        options.setOnMouseClicked(e -> {
            optionsOluştur();
        });
        options.setOnMouseExited(e -> {
            options.setStroke(Color.TRANSPARENT);
            mpVoice.pause();
        });
        optionsT.setOnMouseEntered(e -> {
            options.setStroke(Color.WHITE);
        });
        optionsT.setOnMouseClicked(e -> {
            optionsOluştur();
        });
        optionsT.setOnMouseExited(e -> {
            options.setStroke(Color.TRANSPARENT);
        });
        
        // creates exit Polygon and set coordinates depending on WIDTH and HEIGHT properties
        exit = new Polygon((WIDTH/9+kb) , (HEIGHT/2+kb) , (WIDTH/2-(2*kb)) , (HEIGHT/2+kb) ,
                (WIDTH/2-kb) ,(HEIGHT/2+(2*kb)) , (WIDTH/2-kb) , (HEIGHT/2+(boy+kb)) , (WIDTH/9+(2*kb)) , 
                (HEIGHT/2+(boy+kb)) , (WIDTH/9+kb) , (HEIGHT/2+(boy)) ) ;
        exit.setFill(Color.GRAY);
        
        // creates exitText coordinates depending on WIDTH and HEIGHT properties and String
        exitT = new Text((WIDTH/9+(WIDTH/2 - WIDTH/9)/2.2) ,(HEIGHT/2+(boy)) , exitStr);
        exitT.setScaleX(3);
        exitT.setScaleY(3);
        
        /*
        *   if mouse entered , stroke of the polygon will be white and when exited back to the normal , if user 
        *   click exit polygon or exitT text , program will create exit Pane using exitOluştur() method
        */
        exit.setOnMouseEntered(e -> {
            exit.setStroke(Color.WHITE);
            mpVoice.play();
            mpVoice = new MediaPlayer(mediaVoice);
            mpVoice.volumeProperty().bind(sliderVoice.valueProperty().divide(100));
        });
        exit.setOnMouseClicked(e -> {
            exitOluştur();
        });
        exit.setOnMouseExited(e -> {
            exit.setStroke(Color.TRANSPARENT);
            mpVoice.pause();
        });
        exitT.setOnMouseEntered(e -> {
            exit.setStroke(Color.WHITE);
        });
        exitT.setOnMouseClicked(e -> {
            exitOluştur();
        });
        exitT.setOnMouseExited(e -> {
            exit.setStroke(Color.TRANSPARENT);
        });
        
        // adds nodes to the pane 
        pane.getChildren().addAll(menu , gameName , play , playT , options , optionsT , exit , exitT );
        
        Scene s = new Scene(pane, WIDTH, HEIGHT);
        window.setScene(s);
        
        // these 2 method is used for resize 
        pane.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                WIDTH = (double)newValue ;
                menuOluştur();
            }
        });
        pane.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HEIGHT = (double)newValue ;
                menuOluştur();
            }
        });
        
    }
    
    /** 
    *  In playOluştur() method , polygons and shapes belonging this menu is created.
    *  These features are same for "Play" , "Options" and "Exit" 
    */
    void playOluştur(){
        
        secondsPassedint=0;
        hasPassed.clear();
        hasPassed.add(level1Passed);
        hasPassed.add(level2Passed);
        hasPassed.add(level3Passed);
        hasPassed.add(level4Passed);
        hasPassed.add(level5Passed);
        hasPassed.add(level6Passed);
        
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color:Gray");        
        
        double köşe = (WIDTH - HEIGHT)/12 ;
        double boy = (WIDTH-HEIGHT)/10 ;
        double kb = köşe/2 ;
        
        menu = new Polygon(WIDTH/9 , HEIGHT/8 , WIDTH/2-köşe , HEIGHT/8 , WIDTH/2 , HEIGHT/8+köşe,
                WIDTH/2 , 7*HEIGHT/8 , WIDTH/9+köşe , HEIGHT*7/8 , WIDTH/9 , 7*HEIGHT/8-köşe );
        menu.setFill(Color.DARKGRAY);
        
        Text playT2 = new Text((WIDTH/9+(WIDTH/2 - WIDTH/9)/2.2) ,(HEIGHT/7+(2*kb)) , nextLevelStr+" "+bölüm);
        playT2.setScaleX(3);
        playT2.setScaleY(3);

        playT2.setOnMouseClicked(e->{
            switch(bölüm){
                case 1 : stagesArray.clear(); level1Scene(); break;
                case 2 : stagesArray.clear(); level2Scene(); break;
                case 3 : stagesArray.clear(); level3Scene(); break;
                case 4 : stagesArray.clear(); level4Scene(); break;
                case 5 : stagesArray.clear(); level5Scene(); break;
                case 6 : stagesArray.clear(); level6Scene(); break;
            }
        });
        
            nextLevel = new Polygon((WIDTH/9+(WIDTH/2 - WIDTH/9)*2/3) , (HEIGHT*7/8-(3.5*boy+kb)) , (WIDTH/2-(2*kb)),
                (HEIGHT*7/8-(3.5*boy+kb)) ,(WIDTH/2-kb) , (HEIGHT*7/8-(3.5*boy)) , (WIDTH/2-kb) , (HEIGHT*7/8-(2.5*boy+kb)), 
                (WIDTH/9+(WIDTH/2 - WIDTH/9)*2/3)+kb , (HEIGHT*7/8-(2.5*boy+kb)) , (WIDTH/9+(WIDTH/2 - WIDTH/9)*2/3) , 
                        (HEIGHT*7/8-(2.5*boy+2*kb)) ) ;
            nextLevel.setFill(Color.GRAY);

            nextLevelT = new Text((WIDTH/2)-(((WIDTH/2 - WIDTH/9)/4)) ,(HEIGHT*7/8-(2.5*boy+2*kb)), nextLevelStr+" "+String.valueOf(bölüm+1));
            nextLevelT.setScaleX(2);
            nextLevelT.setScaleY(2);
            if(hasPassed.get(bölüm-1)){
                nextLevel.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                    mpVoice.play();
                    mpVoice = new MediaPlayer(mediaVoice);
                    mpVoice.volumeProperty().bind(sliderVoice.valueProperty().divide(100));
                });
                nextLevel.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    playOluştur();
                });
                nextLevelT.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    playOluştur();
                });
                nextLevel.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                    mpVoice.pause();
                });
                nextLevelT.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevelT.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
            }
            else{
                nextLevel.setFill(Color.DARKRED);
            }

        oldLevel = new Polygon((WIDTH/9+kb) , (HEIGHT*7/8-(3.5*boy+kb)), (WIDTH/9+(WIDTH/2-WIDTH/9)/3-kb) 
                , (HEIGHT*7/8-(3.5*boy+kb)) ,(WIDTH/9+(WIDTH/2-WIDTH/9)/3),(HEIGHT*7/8-(3.5*boy)), 
                (WIDTH/9+(WIDTH/2-WIDTH/9)/3) 
                , (HEIGHT*7/8-(2.5*boy+kb)) , (WIDTH/9+(2*kb)) , 
                (HEIGHT*7/8-(2.5*boy+kb)) , (WIDTH/9+kb) , (HEIGHT*7/8-(2.5*boy+2*kb)) ) ;
        oldLevel.setFill(Color.GRAY);

        oldLevelT = new Text((WIDTH/9+3*kb)  ,(HEIGHT*7/8-(2.5*boy+2*kb))  , oldLevelStr+" "+String.valueOf(bölüm-1));
        oldLevelT.setScaleX(2);
        oldLevelT.setScaleY(2);

        oldLevel.setOnMouseEntered(e -> {
            oldLevel.setStroke(Color.WHITE);
            mpVoice.play();
            mpVoice = new MediaPlayer(mediaVoice);
            mpVoice.volumeProperty().bind(sliderVoice.valueProperty().divide(100));
        });
        oldLevel.setOnMouseClicked(e -> {
            bölüm--;
            stagesArray.clear();
            playOluştur();
        });
        oldLevel.setOnMouseExited(e -> {
            oldLevel.setStroke(Color.TRANSPARENT);
            mpVoice.pause();
        });
        oldLevelT.setOnMouseEntered(e -> {
            oldLevel.setStroke(Color.WHITE);
        });
        oldLevelT.setOnMouseClicked(e -> {
            bölüm--;
            stagesArray.clear();
            playOluştur();
        });
        oldLevelT.setOnMouseExited(e -> {
            oldLevel.setStroke(Color.TRANSPARENT);
        });
        
        back = new Polygon((WIDTH/9+kb) , (HEIGHT*7/8-(2*boy)) , (WIDTH/2-(2*kb)) , (HEIGHT*7/8-(2*boy)) ,
                (WIDTH/2-kb) , (HEIGHT*7/8-(2*boy)+kb) , (WIDTH/2-kb) , (HEIGHT*7/8-(1*boy)) , (WIDTH/9+(2*kb)) , 
                (HEIGHT*7/8-(1*boy)) , (WIDTH/9+kb) , (HEIGHT*7/8-(1*boy)-kb) ) ;
        back.setFill(Color.GRAY);
        
        backT = new Text((WIDTH/9+(WIDTH/2 - WIDTH/9)/2.2) ,(HEIGHT*7/8-(1*boy)-kb) , backStr);
        backT.setScaleX(3);
        backT.setScaleY(3);
        
        back.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
            mpVoice.play();
            mpVoice = new MediaPlayer(mediaVoice);
            mpVoice.volumeProperty().bind(sliderVoice.valueProperty().divide(100));
        });
        back.setOnMouseClicked(e -> {
            stagesArray.clear();
            menuOluştur();
        });
        back.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
            mpVoice.pause();
        });
        backT.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        backT.setOnMouseClicked(e -> {
            stagesArray.clear();
            menuOluştur();
        });
        backT.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });
        
        if(bölüm<hasPassed.size() && bölüm!=1){
            pane.getChildren().addAll(menu,playT2,back,backT,nextLevel,nextLevelT,oldLevel,oldLevelT);
        }
        else if(bölüm==1){
            pane.getChildren().addAll(menu,playT2,back,backT,nextLevel,nextLevelT);
        }
        else {
            pane.getChildren().addAll(menu,playT2,back,backT,oldLevel,oldLevelT);
        }
        
        try {
            Scanner level1txt = new Scanner(
                    new File("C:\\Users\\enesa\\NetBeansProjects\\ogıdeneme2\\src\\OGI\\level"+String.valueOf(bölüm)+".txt")
            );
            while (level1txt.hasNext()) {
            String next = level1txt.next();
            String[] intypo = next.split(",");
            CreateBoxes st1 = new CreateBoxes(Integer.parseInt(intypo[0]), intypo[1], intypo[2]);
            stagesArray.add(st1);
            }
        } catch (FileNotFoundException ex) {
            ex.getStackTrace();
        }
        int x , y ;
        for(int i=0; i<stagesArray.size() ; i++){
            x = i%4 ;
            y = i/4 ;
            stagesArray.get(i).getIw().setLayoutX(x*(WIDTH/18) + WIDTH/5);
            stagesArray.get(i).getIw().setLayoutY(y*(HEIGHT/16) + HEIGHT/4);
            stagesArray.get(i).getIw().setFitWidth(WIDTH/18);
            stagesArray.get(i).getIw().setFitHeight(HEIGHT/16);
            stagesArray.get(i).getIw().setOnMouseClicked(e->{
            switch(bölüm){
                case 1 :  stagesArray.clear(); level1Scene(); break;
                case 2 :  stagesArray.clear(); level2Scene(); break;
                case 3 :  stagesArray.clear(); level3Scene(); break;
                case 4 :  stagesArray.clear(); level4Scene(); break;
                case 5 :  stagesArray.clear(); level5Scene(); break;
                case 6 :  stagesArray.clear(); level6Scene(); break;
            }
        });
            pane.getChildren().addAll(stagesArray.get(i).getIw());
        }

        Scene s = new Scene(pane, WIDTH, HEIGHT);
        window.setScene(s);
        
        pane.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                WIDTH = (double)newValue ;
                stagesArray.clear();
                playOluştur();
            }
        });
        pane.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HEIGHT = (double)newValue ;
                stagesArray.clear();
                playOluştur();
            }
        });
        
    }
    
    /** 
    *  In optionsOluştur() method , polygons and shapes belonging this menu is created.
    *  These features are same for "Play" , "Options" and "Exit" 
    */
    void optionsOluştur(){
        
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color:Gray");        
        
        double köşe = (WIDTH - HEIGHT)/12 ;
        double boy = (WIDTH-HEIGHT)/10 ;
        double kb = köşe/2 ;
        
        menu = new Polygon(WIDTH/9 , HEIGHT/8 , WIDTH/2-köşe , HEIGHT/8 , WIDTH/2 , HEIGHT/8+köşe,
                WIDTH/2 , 7*HEIGHT/8 , WIDTH/9+köşe , HEIGHT*7/8 , WIDTH/9 , 7*HEIGHT/8-köşe );
        menu.setFill(Color.DARKGRAY);
        
        Text optionsT2 = new Text((WIDTH/9+(WIDTH/2 - WIDTH/9)/2.2) ,(HEIGHT/7+(2*kb)) , optionsStr);
        optionsT2.setScaleX(3);
        optionsT2.setScaleY(3);
          
        music = new Polygon((WIDTH/9+kb) , (HEIGHT/2-(2*boy)-kb) , (WIDTH/2-(2*kb)) , (HEIGHT/2-(2*boy)-kb) ,
                (WIDTH/2-kb) ,(HEIGHT/2-(2*boy)) , (WIDTH/2-kb) , (HEIGHT/2-(boy+kb)) , (WIDTH/9+(2*kb)) , 
                (HEIGHT/2-(boy+kb)) , (WIDTH/9+kb) , (HEIGHT/2-(boy+(2*kb))) ) ;
        music.setFill(Color.GRAY);

        Pane pMusic = new Pane();
        pMusic.setLayoutX((WIDTH/9+4.5*kb));
        pMusic.setLayoutY((HEIGHT/2-(2*boy)-0.5*kb));

        ImageView iw = new ImageView(file.toURI().toString()+"ses.png");
        iw.setFitHeight(40);
        iw.setFitWidth(40);
        pMusic.getChildren().add(iw);
        
        Pane pMusic2 = new Pane();
        pMusic2.setLayoutX((WIDTH/9+3.5*boy));
        pMusic2.setLayoutY((HEIGHT/2-(2*boy))-0.2*kb);
        
        pMusic2.getChildren().add(sliderMusic);

        Pane pVoice = new Pane();
        pVoice.setLayoutX((WIDTH/9+4.5*kb));
        pVoice.setLayoutY((HEIGHT/2-2*kb));
        ImageView iw2 = new ImageView(file.toURI().toString()+"ses.png");
        iw2.setFitHeight(40);
        iw2.setFitWidth(40);
        pVoice.getChildren().add(iw2);
        
        Pane pVoice2 = new Pane();
        pVoice2.setLayoutX((WIDTH/9+3.5*boy));
        pVoice2.setLayoutY((HEIGHT/2-1.5*kb));
        pVoice2.getChildren().add(sliderVoice);
 
        voice = new Polygon((WIDTH/9+kb) , (HEIGHT/2-boy) , (WIDTH/2-(2*kb)) , (HEIGHT/2-boy) ,
                (WIDTH/2-kb) ,(HEIGHT/2-boy+kb) , (WIDTH/2-kb) , (HEIGHT/2) , (WIDTH/9+(2*kb)) , 
                (HEIGHT/2) , (WIDTH/9+kb) , (HEIGHT/2-kb) ) ;
        voice.setFill(Color.GRAY);

        language = new Polygon((WIDTH/9+kb) , (HEIGHT/2+kb) , (WIDTH/2-(2*kb)) , (HEIGHT/2+kb) ,
                (WIDTH/2-kb) ,(HEIGHT/2+(2*kb)) , (WIDTH/2-kb) , (HEIGHT/2+(boy+kb)) , (WIDTH/9+(2*kb)) , 
                (HEIGHT/2+(boy+kb)) , (WIDTH/9+kb) , (HEIGHT/2+(boy)) ) ;
        language.setFill(Color.GRAY);
        
        languageT = new Text((WIDTH/9+(WIDTH/2 - WIDTH/9)/2.2) ,(HEIGHT/2+(boy)) , languageStr);
        languageT.setScaleX(3);
        languageT.setScaleY(3);
        
        language.setOnMouseEntered(e -> {
            language.setStroke(Color.WHITE);
            mpVoice.play();
            mpVoice = new MediaPlayer(mediaVoice);
            mpVoice.volumeProperty().bind(sliderVoice.valueProperty().divide(100));
        });
        language.setOnMouseClicked(e -> {
            dilDeğiş();
            optionsOluştur();
        });
        language.setOnMouseExited(e -> {
            language.setStroke(Color.TRANSPARENT);
            mpVoice.pause();
        });
        languageT.setOnMouseEntered(e -> {
            language.setStroke(Color.WHITE);
        });
        languageT.setOnMouseClicked(e -> {
            dilDeğiş();
            optionsOluştur();
        });
        languageT.setOnMouseExited(e -> {
            language.setStroke(Color.TRANSPARENT);
        });

        back = new Polygon((WIDTH/9+kb) , (HEIGHT*7/8-(2*boy)) , (WIDTH/2-(2*kb)) , (HEIGHT*7/8-(2*boy)) ,
                (WIDTH/2-kb) , (HEIGHT*7/8-(2*boy)+kb) , (WIDTH/2-kb) , (HEIGHT*7/8-(1*boy)) , (WIDTH/9+(2*kb)) , 
                (HEIGHT*7/8-(1*boy)) , (WIDTH/9+kb) , (HEIGHT*7/8-(1*boy)-kb) ) ;
        back.setFill(Color.GRAY);
        
        backT = new Text((WIDTH/9+(WIDTH/2 - WIDTH/9)/2.2) ,(HEIGHT*7/8-(1*boy)-kb) , backStr);
        backT.setScaleX(3);
        backT.setScaleY(3);
        
        back.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
            mpVoice.play();
            mpVoice = new MediaPlayer(mediaVoice);
            mpVoice.volumeProperty().bind(sliderVoice.valueProperty().divide(100));
        });
        back.setOnMouseClicked(e -> {
            menuOluştur();
        });
        back.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
            mpVoice.pause();
        });
        backT.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        backT.setOnMouseClicked(e -> {
            menuOluştur();
        });
        backT.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });
        
        pane.getChildren().addAll(menu,optionsT2,music,voice,language,languageT,back,backT,pVoice,pVoice2,pMusic,pMusic2);
        
        Scene s = new Scene(pane, WIDTH, HEIGHT);
        window.setScene(s);
        
        pane.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                WIDTH = (double)newValue ;
                optionsOluştur();
            }
        });
        pane.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HEIGHT = (double)newValue ;
                optionsOluştur();
            }
        });
        
    }
    
    /** 
    *  In exitOluştur() method , polygons and shapes belonging this menu is created.
    *  These features are same for "Play" , "Options" and "Exit" 
    */
    void exitOluştur(){
        
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color:Gray");        
        
        double köşe = (WIDTH - HEIGHT)/12 ;
        double boy = (WIDTH-HEIGHT)/10 ;
        double kb = köşe/2 ;
        
        menu = new Polygon(WIDTH/9 , HEIGHT/8 , WIDTH/2-köşe , HEIGHT/8 , WIDTH/2 , HEIGHT/8+köşe,
                WIDTH/2 , 7*HEIGHT/8 , WIDTH/9+köşe , HEIGHT*7/8 , WIDTH/9 , 7*HEIGHT/8-köşe );
        menu.setFill(Color.DARKGRAY);
        
        Text exitT2 = new Text((WIDTH/9+(WIDTH/2 - WIDTH/9)/2.2) ,(HEIGHT/7+(3*kb)) , exit2Str);
        exitT2.setScaleX(3);
        exitT2.setScaleY(3);
        
        yes = new Polygon((WIDTH/9+kb) , (HEIGHT/2-boy) , (WIDTH/2-(2*kb)) , (HEIGHT/2-boy) ,
                (WIDTH/2-kb) ,(HEIGHT/2-boy+kb) , (WIDTH/2-kb) , (HEIGHT/2) , (WIDTH/9+(2*kb)) , 
                (HEIGHT/2) , (WIDTH/9+kb) , (HEIGHT/2-kb) ) ;
        yes.setFill(Color.GRAY);
        
        yesT = new Text((WIDTH/9+(WIDTH/2 - WIDTH/9)/2.2) ,(HEIGHT/2-kb) , yesStr);
        yesT.setScaleX(3);
        yesT.setScaleY(3);
        
        yes.setOnMouseEntered(e -> {
            yes.setStroke(Color.WHITE);
            mpVoice.play();
            mpVoice = new MediaPlayer(mediaVoice);
            mpVoice.volumeProperty().bind(sliderVoice.valueProperty().divide(100));
        });
        yes.setOnMouseClicked(e -> {
            timerTask.cancel();
            window.close();
            System.exit(0);
        });
        yes.setOnMouseExited(e -> {
            yes.setStroke(Color.TRANSPARENT);
            mpVoice.pause();
        });
        yesT.setOnMouseEntered(e -> {
            yes.setStroke(Color.WHITE);
        });
        yesT.setOnMouseClicked(e -> {
            timerTask.cancel();
            window.close();
            System.exit(0); 
        });
        yesT.setOnMouseExited(e -> {
            yes.setStroke(Color.TRANSPARENT);
        });
        
        no = new Polygon((WIDTH/9+kb) , (HEIGHT/2+kb) , (WIDTH/2-(2*kb)) , (HEIGHT/2+kb) ,
                (WIDTH/2-kb) ,(HEIGHT/2+(2*kb)) , (WIDTH/2-kb) , (HEIGHT/2+(boy+kb)) , (WIDTH/9+(2*kb)) , 
                (HEIGHT/2+(boy+kb)) , (WIDTH/9+kb) , (HEIGHT/2+(boy)) ) ;
        no.setFill(Color.GRAY);
        
        noT = new Text((WIDTH/9+(WIDTH/2 - WIDTH/9)/2.2) ,(HEIGHT/2+(boy)) , noStr);
        noT.setScaleX(3);
        noT.setScaleY(3);
        
        no.setOnMouseEntered(e -> {
            no.setStroke(Color.WHITE);
            mpVoice.play();
            mpVoice = new MediaPlayer(mediaVoice);
            mpVoice.volumeProperty().bind(sliderVoice.valueProperty().divide(100));
        });
        no.setOnMouseClicked(e -> {
            menuOluştur();
        });
        no.setOnMouseExited(e -> {
            no.setStroke(Color.TRANSPARENT);
            mpVoice.pause();
        });
        noT.setOnMouseEntered(e -> {
            no.setStroke(Color.WHITE);
        });
        noT.setOnMouseClicked(e -> {
            menuOluştur();
        });
        noT.setOnMouseExited(e -> {
            no.setStroke(Color.TRANSPARENT);
        });
        
        pane.getChildren().addAll(menu,exitT2,yes,yesT,no,noT);
        
        Scene s = new Scene(pane, WIDTH, HEIGHT);
        window.setScene(s);
        
        pane.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                WIDTH = (double)newValue ;
                exitOluştur();
            }
        });
        pane.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HEIGHT = (double)newValue ;
                exitOluştur();
            }
        });
        
    }
    
    /**
    *   In level1Scene() and other methods for level creating , txt file for each level is read by scanner and 
    *   objects are created from CreateBoxes class by using txt file. This method call paneOluştur() method .
    */
    void level1Scene(){
        
        secondsPassed=secondsPassedint;
        try {
            Scanner level1txt = new Scanner(
                    new File("C:\\Users\\enesa\\NetBeansProjects\\ogıdeneme2\\src\\OGI\\level"+String.valueOf(bölüm)+".txt")
            );
            while (level1txt.hasNext()) {
            String next = level1txt.next();
            String[] intypo = next.split(",");
            CreateBoxes st1 = new CreateBoxes(Integer.parseInt(intypo[0]), intypo[1], intypo[2]);
            stagesArray.add(st1);
            }
        } catch (FileNotFoundException ex) {
           ex.getStackTrace();
        }
           
        
        gp = paneOluştur();
        bp = new Pane();
        bp.setStyle("-fx-background-color:Gray");

        double köşe = (WIDTH - HEIGHT)/12 ;
        double boy = (WIDTH-HEIGHT)/10 ;
        double kb = köşe/2 ; 
    
        back = new Polygon(3.75*WIDTH/10 ,7*HEIGHT/8 , 5.5*WIDTH/10 ,7*HEIGHT/8 , 5.5*WIDTH/10+kb , 7*HEIGHT/8+kb,
              5.5*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  3.75*WIDTH/10+kb , 7*HEIGHT/8+boy , 3.75*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
        back.setFill(Color.DARKGRAY);
        
        backT = new Text( 3.8*WIDTH/10+2*kb,7*HEIGHT/8+1.3*kb , backStr);
        backT.setScaleX(2);
        backT.setScaleY(2);
        
        back.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        back.setOnMouseClicked(e -> {
            stagesArray.clear();
            menuOluştur();
        });
        back.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });
        backT.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        backT.setOnMouseClicked(e -> {
            menuOluştur();
            stagesArray.clear();
        });
        backT.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });

        nextLevel = new Polygon(7.25*WIDTH/10 ,7*HEIGHT/8 , 9*WIDTH/10 ,7*HEIGHT/8 , 9*WIDTH/10+kb , 7*HEIGHT/8+kb,
              9*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  7.25*WIDTH/10+kb , 7*HEIGHT/8+boy , 7.25*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
            nextLevel.setFill(Color.DARKGRAY);

            nextLevelT = new Text(7.3*WIDTH/10+2*köşe , 7*HEIGHT/8+1.3*kb , nextLevelStr+" "+String.valueOf(bölüm+1));
            nextLevelT.setScaleX(2);
            nextLevelT.setScaleY(2);
            if(hasPassed.get(bölüm-1)){
                nextLevel.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevel.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    hamleSayısı=0;
                    level2Scene();
                });
                nextLevelT.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    hamleSayısı=0;
                    level2Scene();
                });
                nextLevel.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
                nextLevelT.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevelT.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
            }
            else{
                nextLevel.setFill(Color.DARKRED);
            }

        bp.getChildren().addAll(gp,back,backT,nextLevel,nextLevelT);
        Scene scene = new Scene(bp, WIDTH , HEIGHT);
        window.setScene(scene);
        
        bp.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                WIDTH = (double)newValue ;
                stagesArray.clear();
                secondsPassedint=secondsPassed;
                level1Scene();
            }
        });
        bp.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HEIGHT = (double)newValue ;
                stagesArray.clear();
                secondsPassedint=secondsPassed;
                level1Scene();
            }
        });
        
    }
    
    void level2Scene(){
        
        secondsPassed=secondsPassedint;
        try {
            Scanner level1txt = new Scanner(
                    new File("C:\\Users\\enesa\\NetBeansProjects\\ogıdeneme2\\src\\OGI\\level"+String.valueOf(bölüm)+".txt")   
            );     
            while (level1txt.hasNext()) {      
                String next = level1txt.next();
                String[] intypo = next.split(",");
                CreateBoxes st1 = new CreateBoxes(Integer.parseInt(intypo[0]), intypo[1], intypo[2]);
                stagesArray.add(st1);
            }
        } catch (FileNotFoundException ex) {
            ex.getStackTrace();
        }    
        
        double köşe = (WIDTH - HEIGHT)/12 ;
        double boy = (WIDTH-HEIGHT)/10 ;
        double kb = köşe/2 ; 

        back = new Polygon(3.75*WIDTH/10 ,7*HEIGHT/8 , 5.5*WIDTH/10 ,7*HEIGHT/8 , 5.5*WIDTH/10+kb , 7*HEIGHT/8+kb,
              5.5*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  3.75*WIDTH/10+kb , 7*HEIGHT/8+boy , 3.75*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
        back.setFill(Color.DARKGRAY);
        
        backT = new Text( 3.8*WIDTH/10+2*kb,7*HEIGHT/8+1.3*kb , backStr);
        backT.setScaleX(2);
        backT.setScaleY(2);
        
        back.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        back.setOnMouseClicked(e -> {
            menuOluştur();
            stagesArray.clear();
        });
        back.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });
        backT.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        backT.setOnMouseClicked(e -> {
            menuOluştur();
            stagesArray.clear();
        });
        backT.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });
        
        
        nextLevel = new Polygon(7.25*WIDTH/10 ,7*HEIGHT/8 , 9*WIDTH/10 ,7*HEIGHT/8 , 9*WIDTH/10+kb , 7*HEIGHT/8+kb,
              9*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  7.25*WIDTH/10+kb , 7*HEIGHT/8+boy , 7.25*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
            nextLevel.setFill(Color.DARKGRAY);

            nextLevelT = new Text(7.3*WIDTH/10+2*köşe , 7*HEIGHT/8+1.3*kb , nextLevelStr+" "+String.valueOf(bölüm+1));
            nextLevelT.setScaleX(2);
            nextLevelT.setScaleY(2);
            if(hasPassed.get(bölüm-1)){
                nextLevel.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevel.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    hamleSayısı=0;
                    level3Scene();
                });
                nextLevelT.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    hamleSayısı=0;
                    level3Scene();
                });
                nextLevel.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
                nextLevelT.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevelT.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
            }
            else{
                nextLevel.setFill(Color.DARKRED);
            }
        
            oldLevel = new Polygon(0.25*WIDTH/10 ,7*HEIGHT/8 , 2*WIDTH/10 ,7*HEIGHT/8 , 2*WIDTH/10+kb , 7*HEIGHT/8+kb,
              2*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  0.25*WIDTH/10+kb , 7*HEIGHT/8+boy , 0.25*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
            oldLevel.setFill(Color.DARKGRAY);

            oldLevelT = new Text(0.3*WIDTH/10+2*köşe , 7*HEIGHT/8+1.3*kb , oldLevelStr+" "+String.valueOf(bölüm-1));
            oldLevelT.setScaleX(2);
            oldLevelT.setScaleY(2);    
            
            oldLevel.setOnMouseClicked(e -> {
                bölüm--;
                stagesArray.clear();
                hamleSayısı=0;
                level1Scene();
            });
            oldLevelT.setOnMouseClicked(e -> {
                bölüm--;
                stagesArray.clear();
                hamleSayısı=0;
                level1Scene();
            });
            oldLevel.setOnMouseEntered(e->{
                oldLevel.setStroke(Color.WHITE);
            });
            oldLevel.setOnMouseExited(e ->{
                oldLevel.setStroke(Color.TRANSPARENT);
            });
            oldLevelT.setOnMouseEntered(e->{
                oldLevel.setStroke(Color.WHITE);
            });
            oldLevelT.setOnMouseExited(e ->{
                oldLevel.setStroke(Color.TRANSPARENT);
            });
            
        gp = paneOluştur();
        bp = new Pane();
        bp.getChildren().addAll(gp,back,backT,nextLevel,nextLevelT,oldLevel,oldLevelT);
        bp.setStyle("-fx-background-color:Gray");
        Scene scene = new Scene(bp, WIDTH , HEIGHT);
        window.setScene(scene);
        
        bp.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                WIDTH = (double)newValue ;
                stagesArray.clear();
                 secondsPassedint=secondsPassed;
                level2Scene();
            }
        });
        bp.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HEIGHT = (double)newValue ;
                stagesArray.clear();
                 secondsPassedint=secondsPassed;
                level2Scene();
            }
        });
        
    }
    
    void level3Scene(){
        
        secondsPassed=secondsPassedint;
        try {
            Scanner level1txt = new Scanner(
                    new File("C:\\Users\\enesa\\NetBeansProjects\\ogıdeneme2\\src\\OGI\\level"+String.valueOf(bölüm)+".txt")
            );     
            while (level1txt.hasNext()) {      
                String next = level1txt.next();
                String[] intypo = next.split(",");
                CreateBoxes st1 = new CreateBoxes(Integer.parseInt(intypo[0]), intypo[1], intypo[2]);
                stagesArray.add(st1);
            }
        } catch (FileNotFoundException ex) {
            ex.getStackTrace();
        } 
        
        double köşe = (WIDTH - HEIGHT)/12 ;
        double boy = (WIDTH-HEIGHT)/10 ;
        double kb = köşe/2 ; 
        
        back = new Polygon(3.75*WIDTH/10 ,7*HEIGHT/8 , 5.5*WIDTH/10 ,7*HEIGHT/8 , 5.5*WIDTH/10+kb , 7*HEIGHT/8+kb,
              5.5*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  3.75*WIDTH/10+kb , 7*HEIGHT/8+boy , 3.75*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
        back.setFill(Color.DARKGRAY);
        
        backT = new Text( 3.8*WIDTH/10+2*kb,7*HEIGHT/8+1.3*kb , backStr);
        backT.setScaleX(2);
        backT.setScaleY(2);
        back.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        back.setOnMouseClicked(e -> {
            menuOluştur();
            stagesArray.clear();
        });
        back.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });
        backT.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        backT.setOnMouseClicked(e -> {
            menuOluştur();
            stagesArray.clear();
        });
        backT.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });
       
        nextLevel = new Polygon(7.25*WIDTH/10 ,7*HEIGHT/8 , 9*WIDTH/10 ,7*HEIGHT/8 , 9*WIDTH/10+kb , 7*HEIGHT/8+kb,
              9*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  7.25*WIDTH/10+kb , 7*HEIGHT/8+boy , 7.25*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
            nextLevel.setFill(Color.DARKGRAY);

            nextLevelT = new Text(7.3*WIDTH/10+2*köşe , 7*HEIGHT/8+1.3*kb , nextLevelStr+" "+String.valueOf(bölüm+1));
            nextLevelT.setScaleX(2);
            nextLevelT.setScaleY(2);
            if(hasPassed.get(bölüm-1)){
                nextLevel.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevel.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    hamleSayısı=0;
                    level4Scene();
                });
                nextLevelT.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    hamleSayısı=0;
                    level4Scene();
                });
                nextLevel.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
                nextLevelT.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevelT.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
            }
            else{
                nextLevel.setFill(Color.DARKRED);
            }
        
            oldLevel = new Polygon(0.25*WIDTH/10 ,7*HEIGHT/8 , 2*WIDTH/10 ,7*HEIGHT/8 , 2*WIDTH/10+kb , 7*HEIGHT/8+kb,
              2*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  0.25*WIDTH/10+kb , 7*HEIGHT/8+boy , 0.25*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
            oldLevel.setFill(Color.DARKGRAY);

            oldLevelT = new Text(0.3*WIDTH/10+2*köşe , 7*HEIGHT/8+1.3*kb , oldLevelStr+" "+String.valueOf(bölüm-1));
            oldLevelT.setScaleX(2);
            oldLevelT.setScaleY(2);    
            
            oldLevel.setOnMouseClicked(e -> {
                bölüm--;
                stagesArray.clear();
                hamleSayısı=0;
                level2Scene();
            });
            oldLevelT.setOnMouseClicked(e -> {
                bölüm--;
                stagesArray.clear();
                hamleSayısı=0;
                level2Scene();
            });
            oldLevel.setOnMouseEntered(e->{
                oldLevel.setStroke(Color.WHITE);
            });
            oldLevel.setOnMouseExited(e ->{
                oldLevel.setStroke(Color.TRANSPARENT);
            });
            oldLevelT.setOnMouseEntered(e->{
                oldLevel.setStroke(Color.WHITE);
            });
            oldLevelT.setOnMouseExited(e ->{
                oldLevel.setStroke(Color.TRANSPARENT);
            });
            
        gp = paneOluştur();
        bp = new Pane();
        bp.getChildren().addAll(gp,back,backT,nextLevel,nextLevelT,oldLevel,oldLevelT);
        bp.setStyle("-fx-background-color:Gray");
        Scene scene = new Scene(bp, WIDTH , HEIGHT);
        window.setScene(scene);
        
        bp.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                WIDTH = (double)newValue ;
                stagesArray.clear();
                level3Scene();
            }
        });
        bp.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HEIGHT = (double)newValue ;
                stagesArray.clear();
                level3Scene();
            }
        });
        
    }
    
    void level4Scene(){
        
        secondsPassed=secondsPassedint;
        try {
            Scanner level1txt = new Scanner(
                    new File("C:\\Users\\enesa\\NetBeansProjects\\ogıdeneme2\\src\\OGI\\level"+String.valueOf(bölüm)+".txt")
            );     
            while (level1txt.hasNext()) {      
                String next = level1txt.next();
                String[] intypo = next.split(",");
                CreateBoxes st1 = new CreateBoxes(Integer.parseInt(intypo[0]), intypo[1], intypo[2]);
                stagesArray.add(st1);
            }
        } catch (FileNotFoundException ex) {
            ex.getStackTrace();
        }  
        
        double köşe = (WIDTH - HEIGHT)/12 ;
        double boy = (WIDTH-HEIGHT)/10 ;
        double kb = köşe/2 ; 
  
        back = new Polygon(3.75*WIDTH/10 ,7*HEIGHT/8 , 5.5*WIDTH/10 ,7*HEIGHT/8 , 5.5*WIDTH/10+kb , 7*HEIGHT/8+kb,
              5.5*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  3.75*WIDTH/10+kb , 7*HEIGHT/8+boy , 3.75*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
        back.setFill(Color.DARKGRAY);
        
        backT = new Text( 3.8*WIDTH/10+2*kb,7*HEIGHT/8+1.3*kb , backStr);
        backT.setScaleX(2);
        backT.setScaleY(2);
        
        back.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        back.setOnMouseClicked(e -> {
            menuOluştur();
            stagesArray.clear();
        });
        back.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });
        backT.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        backT.setOnMouseClicked(e -> {
            menuOluştur();
            stagesArray.clear();
        });
        backT.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });
        
        nextLevel = new Polygon(7.25*WIDTH/10 ,7*HEIGHT/8 , 9*WIDTH/10 ,7*HEIGHT/8 , 9*WIDTH/10+kb , 7*HEIGHT/8+kb,
              9*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  7.25*WIDTH/10+kb , 7*HEIGHT/8+boy , 7.25*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
            nextLevel.setFill(Color.DARKGRAY);

            nextLevelT = new Text(7.3*WIDTH/10+2*köşe , 7*HEIGHT/8+1.3*kb , nextLevelStr+" "+String.valueOf(bölüm+1));
            nextLevelT.setScaleX(2);
            nextLevelT.setScaleY(2);
            if(hasPassed.get(bölüm-1)){
                nextLevel.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevel.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    hamleSayısı=0;
                    level5Scene();
                });
                nextLevelT.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    hamleSayısı=0;
                    level5Scene();
                });
                nextLevel.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
                nextLevelT.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevelT.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
            }
            else{
                nextLevel.setFill(Color.DARKRED);
            }
        
        oldLevel = new Polygon(0.25*WIDTH/10 ,7*HEIGHT/8 , 2*WIDTH/10 ,7*HEIGHT/8 , 2*WIDTH/10+kb , 7*HEIGHT/8+kb,
              2*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  0.25*WIDTH/10+kb , 7*HEIGHT/8+boy , 0.25*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
            oldLevel.setFill(Color.DARKGRAY);

            oldLevelT = new Text(0.3*WIDTH/10+2*köşe , 7*HEIGHT/8+1.3*kb , oldLevelStr+" "+String.valueOf(bölüm-1));
            oldLevelT.setScaleX(2);
            oldLevelT.setScaleY(2);    
            
            oldLevel.setOnMouseClicked(e -> {
                bölüm--;
                stagesArray.clear();
                hamleSayısı=0;
                level3Scene();
            });
            oldLevelT.setOnMouseClicked(e -> {
                bölüm--;
                stagesArray.clear();
                hamleSayısı=0;
                level3Scene();
            });
            oldLevel.setOnMouseEntered(e->{
                oldLevel.setStroke(Color.WHITE);
            });
            oldLevel.setOnMouseExited(e ->{
                oldLevel.setStroke(Color.TRANSPARENT);
            });
            oldLevelT.setOnMouseEntered(e->{
                oldLevel.setStroke(Color.WHITE);
            });
            oldLevelT.setOnMouseExited(e ->{
                oldLevel.setStroke(Color.TRANSPARENT);
            });    
            
        gp = paneOluştur();
        bp = new Pane();
        bp.getChildren().addAll(gp,back,backT,nextLevel,nextLevelT,oldLevel,oldLevelT);
        bp.setStyle("-fx-background-color:Gray");
        Scene scene = new Scene(bp, WIDTH , HEIGHT);
        window.setScene(scene);
        
        bp.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                WIDTH = (double)newValue ;
                stagesArray.clear();
                level4Scene();
            }
        });
        bp.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HEIGHT = (double)newValue ;
                stagesArray.clear();
                level4Scene();
            }
        });
        
    }
    
    void level5Scene(){
        
        secondsPassed=secondsPassedint;
        try {
            Scanner level1txt = new Scanner(
                    new File("C:\\Users\\enesa\\NetBeansProjects\\ogıdeneme2\\src\\OGI\\level"+String.valueOf(bölüm)+".txt")
            );     
            while (level1txt.hasNext()) {      
                String next = level1txt.next();
                String[] intypo = next.split(",");
                CreateBoxes st1 = new CreateBoxes(Integer.parseInt(intypo[0]), intypo[1], intypo[2]);
                stagesArray.add(st1);
            }
        } catch (FileNotFoundException ex) {
            ex.getStackTrace();
        }  
        
        double köşe = (WIDTH - HEIGHT)/12 ;
        double boy = (WIDTH-HEIGHT)/10 ;
        double kb = köşe/2 ; 
        
        back = new Polygon(3.75*WIDTH/10 ,7*HEIGHT/8 , 5.5*WIDTH/10 ,7*HEIGHT/8 , 5.5*WIDTH/10+kb , 7*HEIGHT/8+kb,
              5.5*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  3.75*WIDTH/10+kb , 7*HEIGHT/8+boy , 3.75*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
        back.setFill(Color.DARKGRAY);
        
        backT = new Text( 3.8*WIDTH/10+2*kb,7*HEIGHT/8+1.3*kb , backStr);
        backT.setScaleX(2);
        backT.setScaleY(2);
        
        back.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        back.setOnMouseClicked(e -> {
            menuOluştur();
            stagesArray.clear();
        });
        back.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });
        backT.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        backT.setOnMouseClicked(e -> {
            menuOluştur();
            stagesArray.clear();
        });
        backT.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });

        nextLevel = new Polygon(7.25*WIDTH/10 ,7*HEIGHT/8 , 9*WIDTH/10 ,7*HEIGHT/8 , 9*WIDTH/10+kb , 7*HEIGHT/8+kb,
              9*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  7.25*WIDTH/10+kb , 7*HEIGHT/8+boy , 7.25*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
            nextLevel.setFill(Color.DARKGRAY);

            nextLevelT = new Text(7.3*WIDTH/10+2*köşe , 7*HEIGHT/8+1.3*kb , nextLevelStr+" "+String.valueOf(bölüm+1));
            nextLevelT.setScaleX(2);
            nextLevelT.setScaleY(2);
            if(hasPassed.get(bölüm-1)){
                nextLevel.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevel.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    hamleSayısı=0;
                    level6Scene();
                });
                nextLevelT.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    hamleSayısı=0;
                    level6Scene();
                });
                nextLevel.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
                nextLevelT.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevelT.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
            }
            else{
                nextLevel.setFill(Color.DARKRED);
            }
        
        oldLevel = new Polygon(0.25*WIDTH/10 ,7*HEIGHT/8 , 2*WIDTH/10 ,7*HEIGHT/8 , 2*WIDTH/10+kb , 7*HEIGHT/8+kb,
              2*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  0.25*WIDTH/10+kb , 7*HEIGHT/8+boy , 0.25*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
            oldLevel.setFill(Color.DARKGRAY);

            oldLevelT = new Text(0.3*WIDTH/10+2*köşe , 7*HEIGHT/8+1.3*kb , oldLevelStr+" "+String.valueOf(bölüm-1));
            oldLevelT.setScaleX(2);
            oldLevelT.setScaleY(2);    
            
            oldLevel.setOnMouseClicked(e -> {
                bölüm--;
                stagesArray.clear();
                hamleSayısı=0;
                level4Scene();
            });
            oldLevelT.setOnMouseClicked(e -> {
                bölüm--;
                stagesArray.clear();
                hamleSayısı=0;
                level4Scene();
            });
            oldLevel.setOnMouseEntered(e->{
                oldLevel.setStroke(Color.WHITE);
            });
            oldLevel.setOnMouseExited(e ->{
                oldLevel.setStroke(Color.TRANSPARENT);
            });
            oldLevelT.setOnMouseEntered(e->{
                oldLevel.setStroke(Color.WHITE);
            });
            oldLevelT.setOnMouseExited(e ->{
                oldLevel.setStroke(Color.TRANSPARENT);
            });    
            
        gp = paneOluştur();
        bp = new Pane();
        if(bölüm<hasPassed.size()){
            bp.getChildren().addAll(gp,back,backT,nextLevel,nextLevelT,oldLevel,oldLevelT);
        }
        else {
            bp.getChildren().addAll(gp,back,backT);
        }
        bp.setStyle("-fx-background-color:Gray");
        Scene scene = new Scene(bp,WIDTH , HEIGHT);
        window.setScene(scene);
        bp.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                WIDTH = (double)newValue ;
                stagesArray.clear();
                level5Scene();
            }
        });
        bp.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HEIGHT = (double)newValue ;
                stagesArray.clear();
                level5Scene();
            }
        });
        
    }
    
    void level6Scene(){
        
        secondsPassed=secondsPassedint;
        try {
            Scanner level1txt = new Scanner(
                   new File("C:\\Users\\enesa\\NetBeansProjects\\ogıdeneme2\\src\\OGI\\level"+String.valueOf(bölüm)+".txt")
            );     
            while (level1txt.hasNext()) {      
                String next = level1txt.next();
                String[] intypo = next.split(",");
                CreateBoxes st1 = new CreateBoxes(Integer.parseInt(intypo[0]), intypo[1], intypo[2]);
                stagesArray.add(st1);
            }
        } catch (FileNotFoundException ex) {
            ex.getStackTrace();
        }  
        
        double köşe = (WIDTH - HEIGHT)/12 ;
        double boy = (WIDTH-HEIGHT)/10 ;
        double kb = köşe/2 ;

        back = new Polygon(3.75*WIDTH/10 ,7*HEIGHT/8 , 5.5*WIDTH/10 ,7*HEIGHT/8 , 5.5*WIDTH/10+kb , 7*HEIGHT/8+kb,
              5.5*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  3.75*WIDTH/10+kb , 7*HEIGHT/8+boy , 3.75*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
        back.setFill(Color.DARKGRAY);
        
        backT = new Text( 3.8*WIDTH/10+2*kb,7*HEIGHT/8+1.3*kb , backStr);
        backT.setScaleX(2);
        backT.setScaleY(2);
        
        back.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        back.setOnMouseClicked(e -> {
            menuOluştur();
            stagesArray.clear();
        });
        back.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });
        backT.setOnMouseEntered(e -> {
            back.setStroke(Color.WHITE);
        });
        backT.setOnMouseClicked(e -> {
            menuOluştur();
            stagesArray.clear();
        });
        backT.setOnMouseExited(e -> {
            back.setStroke(Color.TRANSPARENT);
        });
       
        nextLevel = new Polygon(7.25*WIDTH/10 ,7*HEIGHT/8 , 9*WIDTH/10 ,7*HEIGHT/8 , 9*WIDTH/10+kb , 7*HEIGHT/8+kb,
              9*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  7.25*WIDTH/10+kb , 7*HEIGHT/8+boy , 7.25*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
            nextLevel.setFill(Color.DARKGRAY);

            nextLevelT = new Text(7.3*WIDTH/10+2*köşe , 7*HEIGHT/8+1.3*kb , nextLevelStr+" "+String.valueOf(bölüm+1));
            nextLevelT.setScaleX(2);
            nextLevelT.setScaleY(2);
            if(hasPassed.get(bölüm-1)){
                nextLevel.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevel.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    hamleSayısı=0;
                    //level7Scene();
                });
                nextLevelT.setOnMouseClicked(e -> {
                    bölüm++;
                    stagesArray.clear();
                    hamleSayısı=0;
                    //level7Scene();
                });
                nextLevel.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
                nextLevelT.setOnMouseEntered(e -> {
                    nextLevel.setStroke(Color.WHITE);
                });
                nextLevelT.setOnMouseExited(e -> {
                    nextLevel.setStroke(Color.TRANSPARENT);
                });
            }
            else{
                nextLevel.setFill(Color.DARKRED);
            }
        
            oldLevel = new Polygon(0.25*WIDTH/10 ,7*HEIGHT/8 , 2*WIDTH/10 ,7*HEIGHT/8 , 2*WIDTH/10+kb , 7*HEIGHT/8+kb,
              2*WIDTH/10+kb ,  7*HEIGHT/8+boy ,  0.25*WIDTH/10+kb , 7*HEIGHT/8+boy , 0.25*WIDTH/10 , 7*HEIGHT/8+boy-kb
        ) ;
            oldLevel.setFill(Color.DARKGRAY);

            oldLevelT = new Text(0.3*WIDTH/10+2*köşe , 7*HEIGHT/8+1.3*kb , oldLevelStr+" "+String.valueOf(bölüm-1));
            oldLevelT.setScaleX(2);
            oldLevelT.setScaleY(2);    
            
            oldLevel.setOnMouseClicked(e -> {
                bölüm--;
                stagesArray.clear();
                hamleSayısı=0;
                level5Scene();
            });
            oldLevelT.setOnMouseClicked(e -> {
                bölüm--;
                stagesArray.clear();
                hamleSayısı=0;
                level5Scene();
            });
            oldLevel.setOnMouseEntered(e->{
                oldLevel.setStroke(Color.WHITE);
            });
            oldLevel.setOnMouseExited(e ->{
                oldLevel.setStroke(Color.TRANSPARENT);
            });
            oldLevelT.setOnMouseEntered(e->{
                oldLevel.setStroke(Color.WHITE);
            });
            oldLevelT.setOnMouseExited(e ->{
                oldLevel.setStroke(Color.TRANSPARENT);
            });

        gp = paneOluştur();
        bp = new Pane();
        if(bölüm<hasPassed.size()){
            bp.getChildren().addAll(gp,back,backT,nextLevel,nextLevelT,oldLevel,oldLevelT);
        }
        else {
            bp.getChildren().addAll(gp,back,backT,oldLevel,oldLevelT);
        }
        bp.setStyle("-fx-background-color:Gray");
        Scene scene = new Scene(bp,WIDTH , HEIGHT);
        window.setScene(scene);
        bp.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                WIDTH = (double)newValue ;
                stagesArray.clear();
                level5Scene();
            }
        });
        bp.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HEIGHT = (double)newValue ;
                stagesArray.clear();
                level5Scene();
            }
        });
        
    }
    
    /**
    *   In this method , each tile is taken from stagesArray and placed on screen , after "NextLevel" and "back"
    *   polygons are placed , pressing these polygons , user go next level or back to the menu, also prints how many
    *   moves are done and how much time is passed.
    */
    void levelPassedScene(){
        
        Pane pane2 = new Pane() ;
        
        int x , y ;
        // create tile's and placed to the screen
        for(int i=0; i<stagesArray.size() ; i++){
            x = i%4 ;
            y = i/4 ;
            stagesArray.get(i).getIw().setLayoutX(x*(WIDTH/9) + WIDTH/4);
            stagesArray.get(i).getIw().setLayoutY(y*(HEIGHT/8) + HEIGHT/4);
            pane2.getChildren().addAll(stagesArray.get(i).getIw());
        }
        
        pane2.setStyle("-fx-background-color:Gray");
        pane2.setOpacity(0.3);
        
        double köşe = (WIDTH - HEIGHT)/12 ;
        double kb = köşe/2 ; 
        double boy = (WIDTH-HEIGHT)/10-kb ;
        
        // creates menu polygon depends on Wıdth and Heıght property
        menu = new Polygon(WIDTH/3.75 , HEIGHT/3.50 , WIDTH/1.48-köşe , HEIGHT/3.50 , WIDTH/1.48 , HEIGHT/3.50+köşe,
                WIDTH/1.48 , 5.75*HEIGHT/8 , WIDTH/3.75+köşe , HEIGHT*5.75/8 , WIDTH/3.75 , 5.75*HEIGHT/8-köşe );
        menu.setFill(Color.DARKGRAY);
        
            nextLevel = new Polygon( WIDTH/2 , 5.75*HEIGHT/8-2*kb-boy , WIDTH/1.48-2*kb ,5.75*HEIGHT/8-2*kb-boy ,
                WIDTH/1.48-kb , 5.75*HEIGHT/8-kb-boy , WIDTH/1.48-kb , 5.75*HEIGHT/8-kb , WIDTH/2+kb , 5.75*HEIGHT/8-kb,
                WIDTH/2, 5.75*HEIGHT/8-2*kb ) ;
            nextLevel.setFill(Color.GRAY);
            nextLevelT = new Text( WIDTH/2+3.5*kb , 5.75*HEIGHT/8-kb*0.6-boy , nextLevelStr+String.valueOf(bölüm+1));
            nextLevelT.setScaleX(3);
            nextLevelT.setScaleY(3);
            nextLevel.setOnMouseEntered(e -> {
                nextLevel.setStroke(Color.WHITE);
            });
            // the level that we are is determined , using switch case statement, load next level
            nextLevel.setOnMouseClicked(e -> {
                stagesArray.clear();
                hamleSayısı=0;
                    switch(bölüm){
                        case 1 : 
                            bölüm++;
                            level2Scene();
                            break ;
                        case 2 : 
                            bölüm++;
                            level3Scene();
                            break ;
                        case 3 : 
                            bölüm++;
                            level4Scene();
                            break ;
                        case 4 : 
                            bölüm++;
                            level5Scene();
                            break ;
                        case 5 : 
                            bölüm++;
                            level6Scene();
                            break ;    
                    }
            });
            nextLevelT.setOnMouseClicked(e -> {
                stagesArray.clear();
                hamleSayısı=0;
                switch(bölüm){
                        case 1 : 
                            bölüm++;
                            level2Scene();
                            break ;
                        case 2 : 
                            bölüm++;
                            level3Scene();
                            break ;
                        case 3 : 
                            bölüm++;
                            level4Scene();
                            break ;
                        case 4 : 
                            bölüm++;
                            level5Scene();
                            break ;
                        case 5 : 
                            bölüm++;
                            level6Scene();
                            break ;    
                    }
            });
            nextLevel.setOnMouseExited(e -> {
                nextLevel.setStroke(Color.TRANSPARENT);
            });
            nextLevelT.setOnMouseEntered(e -> {
                nextLevel.setStroke(Color.WHITE);
            });
            nextLevelT.setOnMouseExited(e -> {
                nextLevel.setStroke(Color.TRANSPARENT);
            });
        
        back = new Polygon( WIDTH/3.75+kb , 5.75*HEIGHT/8-2*kb-boy , WIDTH/2-3*kb ,5.75*HEIGHT/8-2*kb-boy ,
                WIDTH/2-2*kb , 5.75*HEIGHT/8-kb-boy , WIDTH/2-2*kb , 5.75*HEIGHT/8-kb , WIDTH/3.75+2*kb , 5.75*HEIGHT/8-kb,
                WIDTH/3.75+kb , 5.75*HEIGHT/8-2*kb ) ;
        back.setFill(Color.GRAY);
        backT = new Text( WIDTH/3.75+5*kb , 5.75*HEIGHT/8-0.6*kb-boy , backStr);
        backT.setScaleX(3);
        backT.setScaleY(3);
        // if user click back polygon , create menu 
            back.setOnMouseEntered(e -> {
                back.setStroke(Color.WHITE);
            });
            back.setOnMouseClicked(e -> {
                stagesArray.clear();
                hamleSayısı=0;
                menuOluştur();
            });
            backT.setOnMouseClicked(e -> {
                stagesArray.clear();
                hamleSayısı=0;
                menuOluştur();
            });
            back.setOnMouseExited(e -> {
                back.setStroke(Color.TRANSPARENT);
            });
            backT.setOnMouseEntered(e -> {
                back.setStroke(Color.WHITE);
            });
            backT.setOnMouseExited(e -> {
                back.setStroke(Color.TRANSPARENT);
            });
        
            // prints screen how many moves that user did
        Text moveMessageT = new Text(moveMessage + hamleSayısı) ;
        moveMessageT.setLayoutX(WIDTH/2.3);
        moveMessageT.setLayoutY(HEIGHT/2.6);
        moveMessageT.setScaleX(3);
        moveMessageT.setScaleY(3);
            // prints screen how much time is passed
        Text timeMessageT = new Text(timeMessage + secondsPassedint) ;
        timeMessageT.setLayoutX(WIDTH/2.2);
        timeMessageT.setLayoutY(HEIGHT/2.1);
        timeMessageT.setScaleX(3);
        timeMessageT.setScaleY(3);
        secondsPassedint=0;
        
        Pane panePassed = new Pane();
        panePassed.setStyle("-fx-background-color:Gray");
        if(bölüm<hasPassed.size()){ 
            panePassed.getChildren().addAll(pane2,menu,back,backT,nextLevel,nextLevelT,moveMessageT,timeMessageT);
        }
        else {
            panePassed.getChildren().addAll(pane2,menu,back,backT,moveMessageT,timeMessageT);
        }
        Scene scene = new Scene(panePassed,WIDTH , HEIGHT);
        window.setScene(scene);
        
        panePassed.widthProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                WIDTH = (double)newValue ;
                levelPassedScene();
            }
        });
        panePassed.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HEIGHT = (double)newValue ;
                levelPassedScene();
            }
        });
        
    }
    
    /**
    *   In this method , each tile is taken from stagesArray is placed on screen and polygons in levelScene's placed.
    *   This method is called everytime when tile is moved and screen updated, this method calls oynatma() method.
    */
    Pane paneOluştur(){
        
        gp = new Pane();    
        int x , y ;
        for(int i=0; i<stagesArray.size() ; i++){
            x = i%4 ;
            y = i/4 ;
            stagesArray.get(i).getIw().setLayoutX(x*(WIDTH/9) + WIDTH/4);
            stagesArray.get(i).getIw().setLayoutY(y*(HEIGHT/8) + HEIGHT/4);
            gp.getChildren().addAll(stagesArray.get(i).getIw());
        }
 
        double köşe = (WIDTH - HEIGHT)/12 ;
        double boy = (WIDTH-HEIGHT)/10 ;
        double kb = köşe/2 ;
        
        Text gameName = new Text(gameNameStr);
        gameName.setLayoutX(WIDTH/2);
        gameName.setLayoutY(HEIGHT/8);
        gameName.setScaleX(4);
        gameName.setScaleY(4);
        
        // creates move polygon set coordinates depends on WIDTH and HEIGHT properties
        move = new Polygon(7.25*WIDTH/10 ,3*HEIGHT/8 , 9*WIDTH/10 ,3*HEIGHT/8 , 9*WIDTH/10+kb , 3*HEIGHT/8+kb,
              9*WIDTH/10+kb ,  3*HEIGHT/8+boy ,  7.25*WIDTH/10+kb , 3*HEIGHT/8+boy , 7.25*WIDTH/10 , 3*HEIGHT/8+boy-kb
        ) ;
        move.setFill(Color.DARKGRAY);
        // creates moveLabel and prints how many move that user did
        moveLabel = new Label(moveStr+" "+String.valueOf(hamleSayısı),move) ;
        moveLabel.setContentDisplay(ContentDisplay.CENTER);
        moveLabel.setLayoutX(7.25*WIDTH/10);
        moveLabel.setLayoutY(3*HEIGHT/8);
        
        // creates path and sets start element .
        path2=new Path();
        for(int i=0 ; i<stagesArray.size() ; i++){
            if(stagesArray.get(i).getKutuTipi().equals("Starter")){
                double ballX = stagesArray.get(i).getIw().getLayoutX() + stagesArray.get(i).getIw().getFitWidth()/2-3;
                double ballY = stagesArray.get(i).getIw().getLayoutY() + stagesArray.get(i).getIw().getFitHeight()/2-3;
                ball = new Circle(ballX, ballY, 9 , Color.YELLOW);
                path2.getElements().add(new MoveTo(ballX,ballY));
            }
        }
        
        // create time polygon and set coordinates depends on WIDTH and HEIGHT properties
        time = new Polygon(7.25*WIDTH/10 ,4*HEIGHT/8 , 9*WIDTH/10 ,4*HEIGHT/8 , 9*WIDTH/10+kb , 4*HEIGHT/8+kb,
              9*WIDTH/10+kb ,  4*HEIGHT/8+boy ,  7.25*WIDTH/10+kb , 4*HEIGHT/8+boy , 7.25*WIDTH/10 , 4*HEIGHT/8+boy-kb
        ) ;
        time.setFill(Color.DARKGRAY);
        // create paneTimer and prints screen how much time passed (we know total time from secondsLabel.) 
        Pane paneTimer = new Pane();
        paneTimer.getChildren().add(secondsLabel);
        paneTimer.setLayoutX(7.1*WIDTH/10+5*kb);
        paneTimer.setLayoutY(4*HEIGHT/8+kb);
        
        gp.getChildren().addAll(gameName,moveLabel,ball,time,paneTimer);
        oynatma();
        return gp ;
 
    }
    
    /**
    *   In this method , tiles in stagesArray arranged by looking canMove property, if tile can move , the location
    *   in the array is rearranged and paneOluştur() method called.
    */
    void oynatma(){
         
        // These for loop look al of the elements in stagesArray and determines which tile's can move depends on the
        // rules that the tile can move none block and sets canMove variable depends on them.
        for(int i=0 ; i<stagesArray.size() ; i++){
            if(i+1 <= 15 && stagesArray.get(i+1).getYönü().equals("Free") && i%4!=3){
                stagesArray.get(i).canMove=true ;
                continue;
            }else {
                stagesArray.get(i).canMove=false ;
            }
            if(i-1 >=0 && stagesArray.get(i-1).getYönü().equals("Free") && i%4!=0){
                stagesArray.get(i).canMove=true ;
                continue;
            } 
            else {
                stagesArray.get(i).canMove=false ;
            }
            if(i-4 >=0 && stagesArray.get(i-4).getYönü().equals("Free")){
                stagesArray.get(i).canMove=true ;
                continue;
            } 
            else {
                stagesArray.get(i).canMove=false ;
            }
            if(i+4 <=15 && stagesArray.get(i+4).getYönü().equals("Free")){
                stagesArray.get(i).canMove=true ;
                continue;
            } 
            else {
                stagesArray.get(i).canMove=false ;
            }
            if(stagesArray.get(i).yönü.equals("Free")){
                stagesArray.get(i).canMove=true ; 
                continue;
            }
            else {
                stagesArray.get(i).canMove=false ;
            }
        }
        // this for loop looks array and determines which tile's is "End" , "Static" or "Starter" and sets their 
        // canMove variable 'false' , because these tiles can not move.
        for(int i=0 ; i<stagesArray.size() ; i++){
            if(stagesArray.get(i).getKutuTipi().equals("End") ||
                stagesArray.get(i).getKutuTipi().equals("PipeStatic") || 
                stagesArray.get(i).getKutuTipi().equals("Starter")){
                stagesArray.get(i).canMove=false ;
            }
        }
        
        // if user press and drags tile , this statement catch .
        gp.setOnMousePressed(e -> {
            // this if block determines which tile is pressed 
            if(e.getPickResult().getIntersectedNode() instanceof ImageView){
                // determines the tile and put it into variable siw.
                siw = new ImageView();
                siw = (ImageView)e.getPickResult().getIntersectedNode() ;
                for(int k=0 ; k<stagesArray.size() ; k++){
                    if(stagesArray.get(k).iw.equals(siw)){ 
                        basılan = k ;           // sets variable 'basılan' which element is pressed looking to the array
                    }
                }
            }
        });
        
        // where the user release the mouse , this statement catch
        gp.setOnMouseReleased(e -> {
            if(e.getPickResult().getIntersectedNode() instanceof ImageView){
                // determine the released item
                siw2=new ImageView();
                siw2 = (ImageView)e.getPickResult().getIntersectedNode() ;
                for(int k=0 ; k<stagesArray.size() ; k++){
                    // this statements replaced the pressed and released items , change their location in array and
                    // recall paneOluştur() method and reprint the screen
                    bırakılan = k ;
                    basıldı =  new CreateBoxes(stagesArray.get(basılan).getIndex()
                            , stagesArray.get(basılan).getKutuTipi(), stagesArray.get(basılan).getYönü());
                    bırakıldı = new CreateBoxes(stagesArray.get(bırakılan).getIndex()
                            , stagesArray.get(bırakılan).getKutuTipi(), stagesArray.get(bırakılan).getYönü());
                    if(stagesArray.get(k).iw.equals(siw2) && stagesArray.get(k).canMove==true && 
                            stagesArray.get(basılan).canMove == true && stagesArray.get(bırakılan).yönü.equals("Free")
                            && (basılan-bırakılan==1 || basılan-bırakılan==-1 || basılan-bırakılan==4 
                            || bırakılan-basılan==4) ){  
                                stagesArray.remove(basılan);
                                stagesArray.add(basılan, bırakıldı);
                                stagesArray.remove(bırakılan);
                                stagesArray.add(bırakılan,basıldı);  
                                hamleSayısı++ ;
                                gp = paneOluştur();  
                                bp.getChildren().addAll(gp);    
                    }
                }   
            }
        });
        // after every move , kontrolEt() method called and this method looks if the stage is passed or not
        kontrolEt();
    }
    
    /**
    *   In createArc method , looks stagesPassArray which has only appropriate tile's and check all of them by using
    *   switch-case statement, then determines pointX and pointy coordinates of tiles and add them into path, after 
    *   that call pathOynat().
    */
    void createArc(){
        
        // add starter tile into 0. index of the stagePassArray
        stagePassArray.add(0, newClassTile);
        // this for look creates path by determines pointX and pointY coordinates, also determines which way to swip.
        // left to the right or right to the left or bottom to up or up to the bottom
        for(int i=0 ; i<stagePassArray.size() ; i++){
            switch(stagePassArray.get(i).getYönü()){
                case "Vertical" :
                    if(stagePassArray.get(i).getKutuTipi().equals("End")){
                        pointX = stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth()/2 - 3 ;
                        pointY = stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight()/2 ;
                        path2.getElements().add(new LineTo(pointX, pointY));
                        break;
                    }
                    else{
                        if(i>0){
                            if(stagePassArray.get(i-1).getYönü().equals("Vertical")
                                    || stagePassArray.get(i-1).getYönü().equals("11")
                                    || stagePassArray.get(i-1).getYönü().equals("10")){
                                pointX = stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth()/2 - 3 ;
                                pointY = stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight() ;
                                path2.getElements().add(new LineTo(pointX, pointY));
                                break; 
                            }
                            else if(stagePassArray.get(i-1).getYönü().equals("Vertical")
                                    || stagePassArray.get(i-1).getYönü().equals("00")
                                    || stagePassArray.get(i-1).getYönü().equals("01")){
                                pointX = stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth()/2 - 3 ;
                                pointY = stagePassArray.get(i).getIw().getLayoutY();
                                path2.getElements().add(new LineTo(pointX, pointY));
                                break; 
                            }
                        }
                        else {
                            pointX = stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth()/2 - 3 ;
                            pointY = stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight() ;
                            path2.getElements().add(new LineTo(pointX, pointY));
                            break; 
                        }
                    }
                    
                case "Horizontal" :
                    if(stagePassArray.get(i).getKutuTipi().equals("End")){
                        pointX = stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth()/2 ;
                        pointY = stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight()/2+3 ;
                        path2.getElements().add(new LineTo(pointX, pointY));
                        break;
                    }
                    else{
                        if(i>0){
                            if(stagePassArray.get(i-1).getYönü().equals("Horizontal")
                                    || stagePassArray.get(i-1).getYönü().equals("11")
                                    || stagePassArray.get(i-1).getYönü().equals("01")){
                                pointX = stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth() ;
                                pointY = stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight()/2+3 ;
                                path2.getElements().add(new LineTo(pointX, pointY));
                                break;         
                            }
                        else if(stagePassArray.get(i-1).getYönü().equals("Horizontal")
                                    || stagePassArray.get(i-1).getYönü().equals("10")
                                    || stagePassArray.get(i-1).getYönü().equals("00")) {
                                pointX = stagePassArray.get(i).getIw().getLayoutX() ;
                                pointY = stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight()/2+3 ;
                                path2.getElements().add(new LineTo(pointX, pointY));
                                break;
                            }
                        }
                        else {
                            pointX = stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth() ;
                            pointY = stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight()/2+3 ;
                            path2.getElements().add(new LineTo(pointX, pointY));
                            break; 
                            } 
                    }
                    
                case "00" :
                    if(i>0){
                        if(stagePassArray.get(i-1).getYönü().equals("Horizontal")
                            || (stagePassArray.get(i-1).getYönü().equals("11") && stagePassArray.get(i-2).getYönü().equals("Vertical"))
                            || stagePassArray.get(i-1).getYönü().equals("01")){
                            arcTo = new ArcTo();
                            arcTo.setX(stagePassArray.get(i).getIw().getLayoutX()+ stagePassArray.get(i).getIw().getFitWidth()/2);
                            arcTo.setY(stagePassArray.get(i).getIw().getLayoutY());
                            arcTo.setRadiusX(stagePassArray.get(i).getIw().getFitWidth()/2);
                            arcTo.setRadiusY(stagePassArray.get(i).getIw().getFitHeight()/2);
                            path2.getElements().add(arcTo);
                            break;
                        }
                        else if (stagePassArray.get(i-1).getYönü().equals("Vertical")
                            || stagePassArray.get(i-1).getYönü().equals("11")
                            || stagePassArray.get(i-1).getYönü().equals("10")){
                            arcTo = new ArcTo();
                            arcTo.setX(stagePassArray.get(i).getIw().getLayoutX());
                            arcTo.setY(stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight()/2);
                            arcTo.setSweepFlag(true);
                            arcTo.setRadiusX(stagePassArray.get(i).getIw().getFitWidth()/2);
                            arcTo.setRadiusY(stagePassArray.get(i).getIw().getFitHeight()/2);
                            path2.getElements().add(arcTo);
                            break;
                            }
                    }
                    else {
                        arcTo = new ArcTo();
                        arcTo.setX(stagePassArray.get(i).getIw().getLayoutX()+ stagePassArray.get(i).getIw().getFitWidth()/2);
                        arcTo.setY(stagePassArray.get(i).getIw().getLayoutY());
                        arcTo.setRadiusX(stagePassArray.get(i).getIw().getFitWidth()/2);
                        arcTo.setRadiusY(stagePassArray.get(i).getIw().getFitHeight()/2);
                        path2.getElements().add(arcTo);
                        break;
                    }
                case "01" :
                    if(i>0){
                        if(stagePassArray.get(i-1).getYönü().equals("Vertical")
                            || stagePassArray.get(i-1).getYönü().equals("11")
                            || (stagePassArray.get(i-1).getYönü().equals("10") && stagePassArray.get(i-2).getYönü().equals("Horizontal")  ) ){
                            arcTo = new ArcTo();
                            arcTo.setX(stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth());
                            arcTo.setY(stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight()/2 );
                            arcTo.setRadiusX(stagePassArray.get(i).getIw().getFitWidth()/2);
                            arcTo.setRadiusY(stagePassArray.get(i).getIw().getFitHeight()/2);
                            path2.getElements().add(arcTo);
                            break;                             
                        }
                        else if (stagePassArray.get(i-1).getYönü().equals("Horizontal")
                            || stagePassArray.get(i-1).getYönü().equals("00")
                            || stagePassArray.get(i-1).getYönü().equals("10")){
                            arcTo = new ArcTo();
                            arcTo.setX(stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth()/2);
                            arcTo.setY(stagePassArray.get(i).getIw().getLayoutY());
                            arcTo.setSweepFlag(true);
                            arcTo.setRadiusX(stagePassArray.get(i).getIw().getFitWidth()/2);
                            arcTo.setRadiusY(stagePassArray.get(i).getIw().getFitHeight()/2);
                            path2.getElements().add(arcTo);
                            break; 
                        }
                    }
                    else {
                        arcTo = new ArcTo();
                        arcTo.setX(stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth());
                        arcTo.setY(stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight()/2 );
                        arcTo.setRadiusX(stagePassArray.get(i).getIw().getFitWidth()/2);
                        arcTo.setRadiusY(stagePassArray.get(i).getIw().getFitHeight()/2);
                        path2.getElements().add(arcTo);
                        break; 
                    }

                case "10" :
                    if(i>0){
                        if(stagePassArray.get(i-1).getYönü().equals("Vertical")
                                || (stagePassArray.get(i-1).getYönü().equals("01") && stagePassArray.get(i-2).getYönü().equals("Horizontal"))
                                || stagePassArray.get(i-1).getYönü().equals("00")){
                            arcTo = new ArcTo();
                            arcTo.setX(stagePassArray.get(i).getIw().getLayoutX());
                            arcTo.setY(stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight()/2 );
                            arcTo.setRadiusX(stagePassArray.get(i).getIw().getFitWidth()/2);
                            arcTo.setRadiusY(stagePassArray.get(i).getIw().getFitHeight()/2);
                            path2.getElements().add(arcTo);
                            break;                        
                        }
                        else if (stagePassArray.get(i-1).getYönü().equals("Horizontal")
                                || stagePassArray.get(i-1).getYönü().equals("11")
                                || stagePassArray.get(i-1).getYönü().equals("01")){
                            arcTo = new ArcTo();
                            arcTo.setX(stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth()/2);
                            arcTo.setY(stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight() );
                            arcTo.setSweepFlag(true);
                            arcTo.setRadiusX(stagePassArray.get(i).getIw().getFitWidth()/2);
                            arcTo.setRadiusY(stagePassArray.get(i).getIw().getFitHeight()/2);
                            path2.getElements().add(arcTo);
                            break; 
                        }
                    }
                    else {
                        arcTo = new ArcTo();
                        arcTo.setX(stagePassArray.get(i).getIw().getLayoutX());
                        arcTo.setY(stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight()/2 );
                        arcTo.setRadiusX(stagePassArray.get(i).getIw().getFitWidth()/2);
                        arcTo.setRadiusY(stagePassArray.get(i).getIw().getFitHeight()/2);
                        path2.getElements().add(arcTo);
                        break;  
                    }
                case "11" :
                    if(i>0){
                        if(stagePassArray.get(i-1).getYönü().equals("Vertical")
                                || stagePassArray.get(i-1).getYönü().equals("01")
                                || (stagePassArray.get(i-1).getYönü().equals("00") && stagePassArray.get(i-2).getYönü().equals("Horizontal") )){
                                arcTo = new ArcTo();
                                arcTo.setX(stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth());
                                arcTo.setY(stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight()/2 );
                                arcTo.setRadiusX(stagePassArray.get(i).getIw().getFitWidth()/2);
                                arcTo.setRadiusY(stagePassArray.get(i).getIw().getFitHeight()/2);
                                path2.getElements().add(arcTo);
                                break;
                        }
                        else if(stagePassArray.get(i-1).getYönü().equals("Horizontal")
                                || stagePassArray.get(i-1).getYönü().equals("10")
                                || stagePassArray.get(i-1).getYönü().equals("00")) {
                                arcTo = new ArcTo();
                                arcTo.setX(stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth()/2);
                                arcTo.setY(stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight());
                                arcTo.setSweepFlag(true);
                                arcTo.setRadiusX(stagePassArray.get(i).getIw().getFitWidth()/2);
                                arcTo.setRadiusY(stagePassArray.get(i).getIw().getFitHeight()/2);
                                path2.getElements().add(arcTo);
                                break;
                        }
                    }
                    else {
                        arcTo = new ArcTo();
                        arcTo.setX(stagePassArray.get(i).getIw().getLayoutX() + stagePassArray.get(i).getIw().getFitWidth());
                        arcTo.setY(stagePassArray.get(i).getIw().getLayoutY() + stagePassArray.get(i).getIw().getFitHeight()/2 );
                        arcTo.setRadiusX(stagePassArray.get(i).getIw().getFitWidth()/2);
                        arcTo.setRadiusY(stagePassArray.get(i).getIw().getFitHeight()/2);
                        path2.getElements().add(arcTo);
                        break;
                    }

            }
        }
        
        // create pane and add tile's and call pathOynat() method .
        pane = new Pane() ;
        int x , y ;
        for(int i=0; i<stagesArray.size() ; i++){
            x = i%4 ;
            y = i/4 ;
            stagesArray.get(i).getIw().setLayoutX(x*(WIDTH/9) + WIDTH/4);
            stagesArray.get(i).getIw().setLayoutY(y*(HEIGHT/8) + HEIGHT/4);
            pane.getChildren().addAll(stagesArray.get(i).getIw());
        }
        pane.getChildren().addAll(ball);
        pane.setStyle("-fx-background-color:Gray");
        Scene scene = new Scene(pane,WIDTH , HEIGHT);
        window.setScene(scene);
        pathOynat();
        
    }
    
    /**
    *   In this method , the path created is added into pathTransition and set the ball(Circle object) as a node 
    *   and play animation , after that  if user click to the screen , levelPassedScene() is called and finish scene.
    */
    void pathOynat(){

        pt = new PathTransition();
        pt.setDuration(Duration.millis(4000));
        pt.setPath(path2);
        pt.setNode(ball);
        pt.setOrientation(
        PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount(1);
        pt.setAutoReverse(false);
        pt.play();
        pane.setOnMouseClicked( e->{
            levelPassedScene();
        });
        
    }
    
    /**
    *   In kontrolEt() method , starterTile is found out by searching in stagesArray .
    */
    void kontrolEt(){    
        for(int i=0 ; i<stagesArray.size() ; i++){
            if(stagesArray.get(i).getKutuTipi().equals("Starter")){
                // index of the starter tile is found from this for loop and load the index to the variable 'nerdeyim'
                nerdeyim = i ;
                nerdeydim = i ;
                newClassTile = stagesArray.get(i) ;
            }
        }
        kontroleDevamEt();
    }
    
    /**
    *   In kontroleDevamEt() method , variable 'nerdeyim' and 'nerdeydim' is found and its direction is determined 
    *   after each move, this method check if this stage is passed or not, if passed createArc .  
    */
    void kontroleDevamEt(){
        
        // this statement checks if the stage is passed or not, if passed determine the stage which is passed and 
        // changes its boolean variable 'true'.
        if(stagesArray.get(nerdeyim).getKutuTipi().equals("End")){
            secondsPassedint=secondsPassed;
            switch(bölüm){
                case 1 : 
                    level1Passed = true ;
                    break ;
                case 2 : 
                    level2Passed = true ;
                    break ;
                case 3 : 
                    level3Passed = true ;
                    break ;
                case 4 : 
                    level4Passed = true ;
                    break ;    
                case 5 : 
                    level5Passed = true ;
                    break ;    
            }
            hasPassed.clear();
            hasPassed.add(level1Passed);
            hasPassed.add(level2Passed);
            hasPassed.add(level3Passed);
            hasPassed.add(level4Passed);
            hasPassed.add(level5Passed);
            hasPassed.add(level6Passed);
            createArc();
        }
        /* check all tiles from start to end, this check works like that , if starter tile index is 1 , variable 
        *   'nerdeyim' is 1 and 'nerdeydim' is 1 , after check its 'yön' variable and if vertical , add 4 to the 
        *   'nerdeyim' and continue while reached to the 'End' tile.
        */
        else {
            String k = stagesArray.get(nerdeyim).getYönü();
            switch(k){
                case "Vertical" : 
                    if(nerdeydim <= nerdeyim && (stagesArray.get(nerdeydim).getYönü().equals("Vertical")
                            || stagesArray.get(nerdeydim).getYönü().equals("10")
                            || stagesArray.get(nerdeydim).getYönü().equals("11")
                            )){
                        nerdeydim = nerdeyim;
                        nerdeyim+=4 ;           
                        stagePassArray.add(stagesArray.get(nerdeyim));
                        kontroleDevamEt(); 
                        break;
                    }
                    else if(nerdeydim > nerdeyim && (stagesArray.get(nerdeydim).getYönü().equals("Vertical")
                            || stagesArray.get(nerdeydim).getYönü().equals("01")
                            || stagesArray.get(nerdeydim).getYönü().equals("00")
                            )){
                        nerdeydim = nerdeyim ;
                        nerdeyim-=4 ;
                        stagePassArray.add(stagesArray.get(nerdeyim));
                    }
                    else {
                        stagePassArray.clear();
                        break ;
                    }
                    kontroleDevamEt(); 
                    break;
                case "Horizontal" :
                    if(nerdeydim <= nerdeyim && (stagesArray.get(nerdeydim).getYönü().equals("Horizontal")
                            || stagesArray.get(nerdeydim).getYönü().equals("01")
                            || stagesArray.get(nerdeydim).getYönü().equals("11")
                            )){
                        nerdeydim = nerdeyim;
                        nerdeyim+=1 ;
                        stagePassArray.add(stagesArray.get(nerdeyim));
                    }
                    else if(nerdeydim > nerdeyim && (stagesArray.get(nerdeydim).getYönü().equals("Horizontal")
                            || stagesArray.get(nerdeydim).getYönü().equals("10")
                            || stagesArray.get(nerdeydim).getYönü().equals("00")
                            )){
                        nerdeydim = nerdeyim ;
                        nerdeyim-=1 ;
                        stagePassArray.add(stagesArray.get(nerdeyim));
                    }
                    else {
                        stagePassArray.clear();
                        break ;
                    }
                    kontroleDevamEt(); 
                    break;    
                case "00" : 
                    if(nerdeydim == nerdeyim-4 && (stagesArray.get(nerdeydim).getYönü().equals("Vertical")
                            || stagesArray.get(nerdeydim).getYönü().equals("10")
                            || stagesArray.get(nerdeydim).getYönü().equals("11")
                            )){
                        nerdeydim = nerdeyim;
                        nerdeyim-=1 ;
                        stagePassArray.add(stagesArray.get(nerdeyim));
                    }
                    else if(nerdeydim == nerdeyim-1 && (stagesArray.get(nerdeydim).getYönü().equals("Horizontal")
                            || stagesArray.get(nerdeydim).getYönü().equals("01")
                            || stagesArray.get(nerdeydim).getYönü().equals("11")
                            )){
                        nerdeydim = nerdeyim ;
                        nerdeyim-=4 ;
                        stagePassArray.add(stagesArray.get(nerdeyim));
                    }
                    else {
                        stagePassArray.clear();
                        break ;
                    }
                    kontroleDevamEt(); 
                    break;
                case "01" :
                    if(nerdeydim <= nerdeyim && (stagesArray.get(nerdeydim).getYönü().equals("Vertical")
                            || stagesArray.get(nerdeydim).getYönü().equals("10")
                            || stagesArray.get(nerdeydim).getYönü().equals("11")
                            )){
                        nerdeydim = nerdeyim;
                        nerdeyim+=1 ;
                        stagePassArray.add(stagesArray.get(nerdeyim));
                    }
                    else if(nerdeydim > nerdeyim && (stagesArray.get(nerdeydim).getYönü().equals("Horizontal")
                            || stagesArray.get(nerdeydim).getYönü().equals("10")
                            || stagesArray.get(nerdeydim).getYönü().equals("00")
                            )){
                        nerdeydim = nerdeyim ;
                        nerdeyim-=4 ;
                        stagePassArray.add(stagesArray.get(nerdeyim));
                    }
                    else {
                        stagePassArray.clear();
                        break ;
                    }
                    kontroleDevamEt(); 
                    break;
                case "10" :
                    if(nerdeydim <= nerdeyim && (stagesArray.get(nerdeydim).getYönü().equals("Horizontal")
                            || stagesArray.get(nerdeydim).getYönü().equals("01")
                            || stagesArray.get(nerdeydim).getYönü().equals("11")
                            )){
                        nerdeydim = nerdeyim;
                        nerdeyim+=4 ;
                        stagePassArray.add(stagesArray.get(nerdeyim));
                    }
                    else if(nerdeydim > nerdeyim && (stagesArray.get(nerdeydim).getYönü().equals("Vertical")
                            || stagesArray.get(nerdeydim).getYönü().equals("01")
                            || stagesArray.get(nerdeydim).getYönü().equals("00")
                            )){
                        nerdeydim = nerdeyim ;
                        nerdeyim-=1 ;
                        stagePassArray.add(stagesArray.get(nerdeyim));
                    }
                    else {
                        stagePassArray.clear();
                        break ;
                    }
                    kontroleDevamEt(); 
                    break;
                case "11" :
                    if(nerdeydim == nerdeyim+1 && (stagesArray.get(nerdeydim).getYönü().equals("Horizontal")
                            || stagesArray.get(nerdeydim).getYönü().equals("10")
                            || stagesArray.get(nerdeydim).getYönü().equals("00")
                            )){
                        nerdeydim = nerdeyim;
                        nerdeyim+=4 ;
                        stagePassArray.add(stagesArray.get(nerdeyim));
                    }
                    else if(nerdeydim == nerdeyim+4 && (stagesArray.get(nerdeydim).getYönü().equals("Vertical")
                            || stagesArray.get(nerdeydim).getYönü().equals("01")
                            || stagesArray.get(nerdeydim).getYönü().equals("00")
                            )){
                        nerdeydim = nerdeyim ;
                        nerdeyim+=1 ;
                        stagePassArray.add(stagesArray.get(nerdeyim));
                    }
                    else {
                        stagePassArray.clear();
                        break ;
                    }
                    kontroleDevamEt(); 
                    break;     
                case "Free" : 
                    stagePassArray.clear();
                    break;
                case "None" : 
                    stagePassArray.clear();
                    break;              
            }
        }  
    }
    
    /**
    *   In TrString and EnString methods , string expressions are converted into Türkçe or English.
    */
    void TrString(){
        
        playStr = "OYNA" ;
        optionsStr = "AYARLAR" ;
        exitStr = "ÇIKIŞ" ;
        languageStr = "TÜRKÇE" ;
        backStr = "GERİ" ;
        yesStr = "ÇIK" ;
        noStr = "HAYIR BİRAZ DAHA" ;
        exit2Str = "OGİ'DEN ÇIKMAK\n    ÜZERESİNİZ" ;
        nextLevelStr = "LEVEL" ;
        oldLevelStr = "LEVEL" ;
        moveMessage= "HAMLE SAYISI : ";
        moveStr = "HAMLE : ";
        timeMessage = "SÜRE : " ;
        
    } 
    
    void EnString(){
        
        playStr = "PLAY" ;
        optionsStr = "OPTIONS" ;
        exitStr = "EXIT" ;
        languageStr = "ENGLISH" ;
        backStr = "BACK" ;
        yesStr = "YES" ;
        noStr = "NO" ;
        exit2Str = "ARE YOU SURE?" ;
        nextLevelStr = "STAGE" ;
        oldLevelStr = "STAGE" ;
        moveMessage = "NUMBER OF MOVES:";
        moveStr="MOVE : ";
        timeMessage = "TIME : ";
        
    }
    
    /**
    *   In dilDeğiş() method , string is checked and if it is English , TrString method is called and convert variable
    *   to Türkçe , if its Türkçe , convert to English.
    */
    void dilDeğiş(){
        if(languageStr.equals("TÜRKÇE")){
            EnString();
        }
        else {
            TrString();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
