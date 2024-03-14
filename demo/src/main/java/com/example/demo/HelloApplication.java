package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private final String[] thumbnailPaths = {
            "thumbnail1.jpg",
            "thumbnail2.jpg",
            "thumbnail3.jpg"
    };

    private final String[] fullSizePaths = {
            "image1.jpg",
            "image2.jpg",
            "image3.jpg"
    };

    private ImageView[] thumbnails;
    private ImageView[] fullSizeImages;
    private int currentIndex = -1;

    private StackPane fullSizeView;

    @Override
    public void start(Stage primaryStage) {
        GridPane thumbnailGrid = createThumbnailGrid();
        fullSizeView = new StackPane();
        fullSizeView.getStyleClass().add("full-size-view");

        HBox navigationControls = createNavigationControls();

        Text selectText = new Text("Please select thumbnail to display full size image");
        selectText.setFont(Font.font(14));
        selectText.getStyleClass().add("text-white");

        HBox topBar = new HBox(selectText);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #001F3F;");

        VBox root = new VBox(topBar, thumbnailGrid, fullSizeView, navigationControls);
        root.setSpacing(20);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("root");

        Scene scene = new Scene(root, 800, 800);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setTitle("Thumbnails and Images Gallery");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createThumbnailGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        thumbnails = new ImageView[thumbnailPaths.length];
        fullSizeImages = new ImageView[fullSizePaths.length];

        for (int i = 0; i < thumbnailPaths.length; i++) {
            ImageView thumbnail = createThumbnail(thumbnailPaths[i], i);
            thumbnails[i] = thumbnail;
            gridPane.add(thumbnail, i, 0);
        }

        return gridPane;
    }

    private ImageView createThumbnail(String path, int index) {
        ImageView thumbnail = new ImageView(new Image(getClass().getResourceAsStream(path)));
        thumbnail.setFitWidth(150);
        thumbnail.setFitHeight(150);
        thumbnail.getStyleClass().add("thumbnail");

        thumbnail.setOnMouseClicked(event -> {
            currentIndex = index;
            showFullSizeImage(index);
        });

        thumbnail.setOnMouseEntered(event -> thumbnail.getStyleClass().add("hover"));
        thumbnail.setOnMouseExited(event -> thumbnail.getStyleClass().remove("hover"));

        return thumbnail;
    }

    private HBox createNavigationControls() {
        ImageView prevButton = new ImageView(new Image(getClass().getResourceAsStream("prev.png")));
        prevButton.setFitWidth(30);
        prevButton.setFitHeight(30);
        prevButton.setOnMouseClicked(event -> showPreviousImage());

        ImageView nextButton = new ImageView(new Image(getClass().getResourceAsStream("next.png")));
        nextButton.setFitWidth(30);
        nextButton.setFitHeight(30);
        nextButton.setOnMouseClicked(event -> showNextImage());

        HBox navigationControls = new HBox(20, prevButton, nextButton);
        navigationControls.setPadding(new Insets(10));
        navigationControls.getStyleClass().add("navigation-controls");

        return navigationControls;
    }

    private void showFullSizeImage(int index) {
        if (index >= 0 && index < fullSizePaths.length) {
            ImageView fullSizeImage = fullSizeImages[index];
            if (fullSizeImage == null) {
                fullSizeImage = new ImageView(new Image(getClass().getResourceAsStream(fullSizePaths[index])));
                fullSizeImage.setFitWidth(600);
                fullSizeImage.setFitHeight(400);
                fullSizeImages[index] = fullSizeImage;
            }
            fullSizeView.getChildren().clear();
            fullSizeView.getChildren().add(fullSizeImage);
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid image index");
            alert.showAndWait();
        }
    }

    private void showNextImage() {
        if (currentIndex < fullSizePaths.length - 1) {
            currentIndex++;
            showFullSizeImage(currentIndex);
        }
    }

    private void showPreviousImage() {
        if (currentIndex > 0) {
            currentIndex--;
            showFullSizeImage(currentIndex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
