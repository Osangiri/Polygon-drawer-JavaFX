import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.shape.Polygon;

public class PolygonDrawer extends Application {
    public static Group root = new Group();
    public static TextField sidesInput, radiusInput;//the two fields for user input
    public static Text errorMessage = null;
    public static ColorPicker colorPicker;
    public static Pane drawingPane = new Pane();

    
    public void start(Stage stage) {
        // just decided to add title intro
        text("Welcome to Polygon Drawer", 100, 20);

        // Number of sides input
        text("Number of Sides:", 25, 60);
        sidesInput = createTextField(200, 50);

        // Radius Input
        text("Radius Length:", 25, 100);
        radiusInput = createTextField(200, 90);

        // Color Picker
        text("Pick a Color:", 25, 140);
        colorPicker = new ColorPicker(Color.BLUE);
        colorPicker.setLayoutX(200);
        colorPicker.setLayoutY(130);
        root.getChildren().add(colorPicker);

        // Draw Button
        Button drawButton = new Button("Draw Polygon");
        drawButton.setLayoutX(100);
        drawButton.setLayoutY(180);
        drawButton.setOnAction(this::handleDrawButton);
        root.getChildren().add(drawButton);

        // Drawing Pane
        drawingPane.setLayoutY(250);
        drawingPane.setPrefSize(500, 500);
        drawingPane.setStyle("-fx-background-color: aliceblue;");
        root.getChildren().add(drawingPane);

        // Error Message Placeholder
        errorMessage = text("", 25, 220);

        // Stage setup
        stage.setTitle("Polygon Drawer");
        stage.getIcons().add(new Image("file:/Users/dorcasosangiri/Downloads/pink.jpeg"));
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Event handler for drawing button

    private void handleDrawButton(ActionEvent e) {
        drawingPane.getChildren().clear();
        errorMessage.setText("");

        try {
            int sides = Integer.parseInt(sidesInput.getText());
            double radius = Double.parseDouble(radiusInput.getText());
            Color color = colorPicker.getValue();

            //check errors
            if (sides < 3) {
                errorMessage.setText("A polygon must have at least 3 sides.");
                return;
            }
            if (radius <= 0) {
                errorMessage.setText("Radius must be positive.");
                return;
            }
            if (radius * 2 > drawingPane.getWidth() || radius * 2 > drawingPane.getHeight()) {
                errorMessage.setText("Polygon too large for the drawing area.");
                return;
            }

            // Draw polygon
            Polygon polygon = new Polygon();
            double centerX = drawingPane.getWidth() / 2;
            double centerY = drawingPane.getHeight() / 2;

            for (int i = 0; i < sides; i++) {//formula for polygon from question
                double angle = 2 * Math.PI / sides;
                double x = centerX + radius * Math.cos(i * angle);
                double y = centerY + radius * Math.sin(i * angle);
                polygon.getPoints().addAll(x, y);
            }

            polygon.setFill(color);
            drawingPane.getChildren().add(polygon);
        } catch (NumberFormatException ex) {
            errorMessage.setText("Invalid input. Enter only numeric values.");
            errorMessage.setFill(Color.FIREBRICK);
        }
    }

    // Utility method for creating Text
    public Text text(String str, int x, int y) {
        Text text = new Text(str);
        text.setX(x);
        text.setY(y);
        text.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
        root.getChildren().add(text);
        return text;
    }

    // this method is used for creating TextField
    public TextField createTextField(int x, int y) {
        TextField textField = new TextField();
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setPrefWidth(100);
        root.getChildren().add(textField);
        return textField;
    }

    
}
//Did so much referencing on the Temperatute converter java
//https://stackoverflow.com/questions/34168628/gui-application-that-allows-the-user-to-choose-the-shape-and-color-of-a-drawing
//https://www.google.com/search?q=how+to+use+java+gui+to+get+user+input+to+draw+polygon&sca_esv=8d419a3d49aff387&rlz=1C5CHFA_enKE1040KE1045&udm=7&biw=1440&bih=783&sxsrf=ADLYWIKwfnfA1l9GLc-jLFpDc0ax6lEeDg%3A1732754019055&ei=Y7pHZ6SHA8-f0PEP8uX08A8&ved=0ahUKEwjkxKfe4_2JAxXPDzQIHfIyHf4Q4dUDCA8&uact=5&oq=how+to+use+java+gui+to+get+user+input+to+draw+polygon&gs_lp=EhZnd3Mtd2l6LW1vZGVsZXNzLXZpZGVvIjVob3cgdG8gdXNlIGphdmEgZ3VpIHRvIGdldCB1c2VyIGlucHV0IHRvIGRyYXcgcG9seWdvbjIIECEYoAEYwwRInExQoAhYqkZwAngAkAEAmAGDAaAB6w2qAQQyMS4xuAEDyAEA-AEBmAIYoAKCD8ICBxAjGCcYrgLCAggQABiiBBiJBcICCBAAGIAEGKIEwgIHECMYsAIYJ8ICChAhGKABGMMEGAqYAwCIBgGSBwQyMy4xoAewYA&sclient=gws-wiz-modeless-video#fpstate=ive&vld=cid:15edeab0,vid:Yemr-z4ZcYk,st:0
//https://www.google.com/search?sca_esv=49b6811cde61045f&rlz=1C5CHFA_enKE1040KE1045&sxsrf=ADLYWIJv5sJW6Hfbaw5McDiO9mq2T0VWdw:1732729573309&q=gui+java+code+for+user+to+draw+shapes&udm=7&fbs=AEQNm0Aa4sjWe7Rqy32pFwRj0UkWd8nbOJfsBGGB5IQQO6L3JyWp6w6_rxLPe8F8fpm5a57iruiBaetC-P1z8A1EgSEtGoKiI-tyuuiDuAjQZN76zaAbPytU70vrRXfg6Tgzjij5R_Re136YiAiZQmK01ZhFDaBKvuWzjRrVqF2bxrJnMYbpGsRQzdzMtgTRsg_T6B4z0T9loWGkBjDF7Xezy_v0ygoVag&sa=X&ved=2ahUKEwji1NXViP2JAxUYDjQIHR5tESkQtKgLegQIGhAB&biw=1440&bih=783&dpr=1#fpstate=ive&vld=cid:f264b2f5,vid:4kAfW9vsCgk,st:0
//https://chatgpt.com/c/67478278-9d3c-8008-b67b-1570ef3bdd46
//https://chatgpt.com/c/67478be0-43c8-8008-9318-9fa152261d20
