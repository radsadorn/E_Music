/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
import static UI_music.User_UI.playerStatus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author poomi
 */
abstract public class UI {

    //Stage Window Program
    private Stage stage;
    private int menuBtnClicked = 0;
    public static Account userAccount;
    public static TextField searchTextField;
    public static VBox vbox;

    //Abstract Class For User_UI & Admin_UI
    abstract public AnchorPane firstPagePane(String page);

    abstract public AnchorPane thirdPagePane();

    abstract public HBox searchBoxAll();

    abstract public HBox searchBoxMy();

    abstract public BorderPane myAccount();

    abstract public void userLogout();

    //Constuctor
    public UI() {
    }

    public UI(Stage stage, Account userAccount) {
        this.stage = stage;
        this.userAccount = userAccount;
        vbox = new VBox();
    }

    //Totally ALLpane On Scene
    public BorderPane allPane() {
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("bg-border");

        borderPane.setLeft(menu());
        borderPane.setCenter(mainBox());

        return borderPane;
    }

    public BorderPane allPane_Admin() {
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("bg-border");

        borderPane.setLeft(menu_Admin());
        borderPane.setCenter(mainBox_Admin());

        return borderPane;
    }

    private VBox mainBox() {

        vbox.getStyleClass().add("mainBox");
        vbox.getChildren().addAll(tilePane(), firstPagePane(""));

        return vbox;
    }

    private VBox mainBox_Admin() {

        vbox.getStyleClass().add("mainBox");
        vbox.getChildren().addAll(tilePane_Admin(), firstPagePane(""));

        return vbox;
    }

    private Button CreaButton(String text) {
        Button btn = new Button(text);
        btn.setPadding(new Insets(0, 0, 0, 70));
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.getStyleClass().add("menubtn");
        btn.setMinWidth(250);
        btn.setMinHeight(50);

        return btn;
    }

    private Button CreaButtonAdmin(String text) {
        Button btn = new Button(text);
        btn.setPadding(new Insets(0, 0, 0, 50));
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.getStyleClass().add("menuAdminbtn");
        btn.setMinWidth(250);
        btn.setMinHeight(50);

        return btn;
    }

//    private Button CreateStyleButton(String text) {
//        Button btn = new Button(text);
//        btn.getStyleClass().add("stylebtn");
//        btn.setMinWidth(250);
//        btn.setMinHeight(45);
//
//        return btn;
//    }
    private VBox menu() {
        VBox vBox = new VBox();
        vBox.getStyleClass().add("menu");
        vBox.setMaxWidth(200);
        vBox.setPadding(Insets.EMPTY);

        Label logoLabel = new Label("E-Music");
        logoLabel.getStyleClass().add("logoName");
        logoLabel.setMinWidth(250);
        logoLabel.setAlignment(Pos.CENTER);

        Button home = CreaButton("Home");
        home.setOnMouseClicked(e -> {
            menuBtnClicked = 0;

            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(firstPagePane(""));
        });

        Button browse = CreaButton("Browse");
        browse.setOnMouseClicked(e -> {

            if (!"guest".equals(UI.userAccount.getUserRole())) {
                this.vbox.getChildren().remove(1);
                this.vbox.getChildren().add(new BrowsePane().getBrowsePane());
            } else {
                AlertBox registerFirst = new AlertBox();
                registerFirst.displayAlert("Register First", "Register Free Account First");
                new Register("member");
            }
        });

        VBox mysong = new VBox();

        Button myLibrary = CreaButton("My Library");
        mysong.getChildren().add(myLibrary);
        myLibrary.setOnMouseClicked(e -> {
            menuBtnClicked = 1;
            if (!"guest".equals(UI.userAccount.getUserRole())) {

                this.vbox.getChildren().remove(1);
                this.vbox.getChildren().add(thirdPagePane());

            } else {
                AlertBox registerFirst = new AlertBox();
                registerFirst.displayAlert("Register First", "Register Free Account First");
                new Register("member");
            }
        });

        Button playerMusic = CreaButton("Spookify Player");
        playerMusic.setOnMouseClicked(e -> {
            if (!"Ready".equals(playerStatus)) {
                MusicPlayer musicPlayer = new MusicPlayer();
                playerStatus = "Ready";
            } else {
                AlertBox.displayAlert(playerStatus, "Music Player is Open");
            }
        });

        Button myAccount = CreaButton("My Account");
        myAccount.setOnMouseClicked(e -> {
            menuBtnClicked = 2;
            if (!"guest".equals(UI.userAccount.getUserRole())) {
                this.vbox.getChildren().remove(1);
                this.vbox.getChildren().add(myAccount());

            } else {
                AlertBox registerFirst = new AlertBox();
                registerFirst.displayAlert("Register First", "Register Free Account First");
                new Register("member");
                this.stage.close();
                Login.stage.show();
            }
        });
        Button logOut = CreaButton("Logout");
        logOut.setOnMouseClicked(e -> {
            this.stage.close();
            Login.stage.show();
        });

        VBox bottomVBox = new VBox(playerMusic, myAccount, logOut);
        bottomVBox.setAlignment(Pos.BOTTOM_CENTER);
        VBox.setVgrow(bottomVBox, Priority.ALWAYS);

        vBox.getChildren().addAll(logoLabel, home, browse, mysong, bottomVBox);

        return vBox;
    }

    private VBox menu_Admin() {
        VBox vBox = new VBox();
        vBox.getStyleClass().add("menu");
        vBox.setMaxWidth(200);
        vBox.setPadding(Insets.EMPTY);

        Label logoLabel = new Label("E-Music");
        logoLabel.getStyleClass().add("logoName");
        logoLabel.setMinWidth(250);
        logoLabel.setAlignment(Pos.CENTER);

        Button songManage = CreaButtonAdmin("Music Management");
        songManage.setOnMouseClicked(e -> {
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(firstPagePane(""));
        });

        Button artistManage = CreaButtonAdmin("Artist Management");
        artistManage.setOnMouseClicked(e -> {
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(new Admin_UI().ArtistPane(""));////
        });

        Button accountManage = CreaButtonAdmin("Account Management");
        accountManage.setOnMouseClicked(e -> {
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(thirdPagePane());
        });

        Button playerMusic = CreaButton("Spookify Player");
        playerMusic.setOnMouseClicked(e -> {
            if (!"Ready".equals(playerStatus)) {
                MusicPlayer musicPlayer = new MusicPlayer();
                playerStatus = "Ready";
            } else {
                AlertBox.displayAlert(playerStatus, "Music Player is Open");
            }
        });

        Button myAccount = CreaButton("My Account");
        myAccount.setOnMouseClicked(e -> {
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(myAccount());
        });

        Button logOut = CreaButton("Logout");
        logOut.setOnMouseClicked(e -> {
            this.stage.close();
            Login.stage.show();
        });

        VBox bottomVBox = new VBox(playerMusic, myAccount, logOut);
        bottomVBox.setAlignment(Pos.BOTTOM_CENTER);
        VBox.setVgrow(bottomVBox, Priority.ALWAYS);

        vBox.getChildren().addAll(logoLabel, songManage, artistManage, accountManage, bottomVBox);

        return vBox;
    }

    double mouse_x = 0, mouse_y = 0; // position mouse

    static AnchorPane titlepane;
    static Label premium;
    static Label upgradepremium;

    public AnchorPane tilePane() {

        titlepane = new AnchorPane();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("title");
        anchorPane.setPadding(new Insets(5));

        premium = new Label("PREMIUM");
        premium.setLayoutX(750);
        premium.setLayoutY(12);
        premium.setAlignment(Pos.CENTER);
        premium.setPrefSize(150, 30);
        premium.getStyleClass().add("showpremium");

        anchorPane.getChildren().addAll(searchBoxAll(), exitButton(), minimizeButton());

        if (userAccount.getUserRole().equals("premium")) {
            anchorPane.getChildren().add(premium);
        }

        upgradepremium = new Label("UPGRADE PREMIUM");
        upgradepremium.setLayoutX(650);
        upgradepremium.setLayoutY(12);
        upgradepremium.setAlignment(Pos.CENTER);
        upgradepremium.setPrefSize(250, 30);
        upgradepremium.getStyleClass().add("premiumbtn");

        upgradepremium.setOnMouseClicked(clicked -> {
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(myAccount());
        });

        if (userAccount.getUserRole().equals("member")) {
            anchorPane.getChildren().add(upgradepremium);
        }

        anchorPane.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
        });
        anchorPane.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - mouse_x);
            stage.setY(e.getScreenY() - mouse_y);
        });

        titlepane = anchorPane;

        return anchorPane;
    }

    public AnchorPane tilePane_Admin() {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("title");
        anchorPane.setPadding(new Insets(5));
        anchorPane.getChildren().addAll(exitButton(), minimizeButton());
        anchorPane.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
        });
        anchorPane.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - mouse_x);
            stage.setY(e.getScreenY() - mouse_y);
        });

        return anchorPane;
    }

    private Button exitButton() {

        Image exit_icon = new Image("/icon/close-512.png");
        Image exit_hover_icon = new Image("/icon/close-512_hover.png");

        Button exit = new Button("", new ImageView(exit_icon));
        exit.setOnMouseEntered(e -> {
            exit.setGraphic(new ImageView(exit_hover_icon));
        });
        exit.setOnMouseExited(e -> {
            exit.setGraphic(new ImageView(exit_icon));
        });
        exit.setOnMouseClicked(e -> {
            stage.close();
        });
        exit.setStyle("-fx-background-color : transparent;");
        exit.setPadding(Insets.EMPTY);
        exit.setLayoutX(974);
        exit.setLayoutY(10);

        return exit;
    }

    private Button minimizeButton() {
        Image minimize_icon = new Image("/icon/minimize-window.png");
        Image minimize_icon_hover_icon = new Image("/icon/minimize-window_hover.png");

        Button minimize = new Button("", new ImageView(minimize_icon));
        minimize.setOnMouseEntered(e -> {
            minimize.setGraphic(new ImageView(minimize_icon_hover_icon));
        });
        minimize.setOnMouseExited(e -> {
            minimize.setGraphic(new ImageView(minimize_icon));
        });

        minimize.setStyle("-fx-background-color : transparent;");
        minimize.setPadding(Insets.EMPTY);
        minimize.setLayoutX(974 - 36 - 20);
        minimize.setLayoutY(10);
        minimize.setOnMouseClicked(e -> {
            stage.setIconified(true);
        });

        return minimize;
    }

//    private AnchorPane AdPane() {
//        AnchorPane anchorpane = new AnchorPane();
//        anchorpane.setStyle("-fx-background-color : transparent");
//
//        ImageView imageview = new ImageView(new Image("/image/banner5.png"));
//        imageview.setLayoutX(190);
//        imageview.setLayoutY(330);
//
//        AnchorPane background = new AnchorPane();
//        background.setStyle("-fx-background-color: #222222;\n"
//                + "-fx-opacity: .6;");
//        background.setMinSize(1280, 960);
//
//        anchorpane.getChildren().addAll(background, imageview);
//
//        return anchorpane;
//    }
}
