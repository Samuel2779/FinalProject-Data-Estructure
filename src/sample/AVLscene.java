package sample;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import java.util.*;

public class AVLscene extends Scene {
    private Main main;
    private TextField input;
    private AVLTree<Integer> arbol = new AVLTree<>();
    private double vGap = 60;
    private double radius = 20;
    private Pane avl;

    public AVLscene(Main main) {
        super(new VBox());
        this.main = main;

        /***************
         * GUI Elements *
         ****************/
        VBox mainContent = new VBox(20);
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Options    ");
        MenuItem randomTree = new MenuItem("Random tree");
        MenuItem clear = new MenuItem("Clear");
        MenuItem exitOption = new MenuItem("Exit");
        FlowPane bottom = new FlowPane();

        avl = new Pane();
        input = new TextField();

        Label lblinput = new Label("Element:");
        Button insert = new Button("Insert");
        Button delete = new Button("Delete");

        /****************
         * Key shortcuts *
         *****************/
        randomTree.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        exitOption.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        input.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                insert();
            }
        });

        /***********
         *  Styles  *
         ************/
        avl.setMinSize(900, 450);
        avl.setPadding(new Insets(20, 20, 20, 20));
        avl.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");

        bottom.setHgap(15);
        bottom.setVgap(15);
        bottom.setAlignment(Pos.CENTER);
        bottom.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: black;");
        bottom.setPadding(new Insets(10, 10, 10, 10));
        mainContent.setStyle("-fx-background-image:url('/img/backG.jpg');");

        /*************************
         * Main content structure *
         **************************/
        menu.getItems().addAll(clear, randomTree, exitOption);
        menuBar.getMenus().add(menu);
        bottom.getChildren().addAll(lblinput, input, insert, delete);
        mainContent.getChildren().addAll(menuBar, avl, bottom);
        mainContent.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 7;" +
                "-fx-border-color: black;");


        super.setRoot(mainContent);

        /*****************
         * Event handlers *
         ******************/
        randomTree.setOnAction(e -> {
            randomTree();
        });
        clear.setOnAction(e -> {
            avl.getChildren().clear();
            arbol.setOrigin(null);
        });
        exitOption.setOnAction(e -> {
            main.closeStage();
        });
        insert.setOnAction(e -> {
            insert();
        });
        delete.setOnAction(e -> {
            delete();
        });
    }

    /*****************
     * Events methods *
     ******************/

    public void displayAVLTree() {
        avl.getChildren().clear(); // Clear the pane
        if (arbol.getOrigin() != null) {
            // Display tree recursively
            displayAVLTree(arbol.getOrigin(), avl.getWidth() / 2, vGap, avl.getWidth() / 4);
        }
    }

    private void displayAVLTree(Node root, double x, double y, double hGap) {
        if (root.getLeft() != null) {
            // Draw a line to the left node
            avl.getChildren().add(new Line(x - hGap, y + vGap, x, y));
            // Draw the left subtree recursively
            displayAVLTree(root.getLeft(), x - hGap, y + vGap, hGap / 2);
        }

        if (root.getRight() != null) {
            // Draw a line to the right node
            avl.getChildren().add(new Line(x + hGap, y + vGap, x, y));
            // Draw the right subtree recursively
            displayAVLTree(root.getRight(), x + hGap, y + vGap, hGap / 2);
        }
        // Display a node
        Circle circle = new Circle(x, y, radius);
        circle.setId("circle");
        circle.setStroke(Color.GRAY);
        circle.setStrokeWidth(1);
        //circle.setFill(Color.rgb((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256)));
        circle.setFill(Color.rgb(89, 217, 149));
        avl.getChildren().addAll(circle, new Text(x - 4, y + 4, root.getElement() + ""));
    }

    private void randomTree(){
        avl.getChildren().clear();
        arbol.setOrigin(null);
        Random randNum = new Random();
        //random array size from 3 to 15
        int setSize = (randNum.nextInt(15)+3);
        //Create a HashSet to get only unique elements
        Set<Integer> set = new LinkedHashSet<Integer>();
        while (set.size() < setSize) {
            set.add(randNum.nextInt(100)+1);
        }
        try{
            for(int x: set){
                arbol.insertElement(x);
            }
        }catch(DuplicateException de){
        }
        displayAVLTree();
    }

    private void insert(){
        try{
            int toInsert = Integer.parseInt(input.getText());
            try{
                arbol.insertElement(toInsert);
                displayAVLTree();
            }catch(DuplicateException de){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Duplicate");
                alert.setHeaderText(de.getMessage());
                alert.showAndWait();
            }
        }catch(Exception empty){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty field");
            alert.setHeaderText("Please introduce an element");
            alert.showAndWait();
        }
        input.setText("");
    }

    private void delete(){
        if(input.getText().equals(arbol.getOrigin().getElement())){
            arbol.deleteNode(Integer.parseInt(input.getText()));
        } else {
            arbol.deleteNode2(Integer.parseInt(input.getText()));
        }
        displayAVLTree();
        input.setText("");
    }

}