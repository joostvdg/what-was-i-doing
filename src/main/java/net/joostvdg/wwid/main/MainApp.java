package net.joostvdg.wwid.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.commonmark.Extension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.Renderer;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.ext.gfm.tables.TablesExtension;

import java.util.List;

public class MainApp extends Application {

    private static Parser parser;
    private static Renderer renderer;

    @Override
    public void start(Stage primaryStage) {
        initParser();

        ComboBox<String> projectDropdown = new ComboBox<>();
        projectDropdown.getItems().add("Placeholder Project");

        ListView<String> notesList = new ListView<>();
        notesList.getItems().add("Sample Note");

        TextArea markdownEditor = new TextArea();
        markdownEditor.setText("# Sample Markdown");

        WebView markdownViewer = new WebView();
        markdownViewer.getEngine().loadContent(convertMarkdownToHtml(markdownEditor.getText()));

        markdownEditor.textProperty().addListener((observable, oldValue, newValue) -> {
            markdownViewer.getEngine().loadContent(convertMarkdownToHtml(newValue));
        });

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(notesList, markdownEditor, markdownViewer);
        splitPane.setDividerPositions(0.3, 0.6);

        SplitPane mainPane = new SplitPane();
        mainPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        mainPane.getItems().addAll(projectDropdown, splitPane);
        mainPane.setDividerPositions(0.1);

        Scene scene = new Scene(mainPane, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("What Was I Doing");
        primaryStage.show();
    }

    private String convertMarkdownToHtml(String markdown) {
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    private static void initParser() {
        List<Extension> extensions = List.of(TablesExtension.create(), TaskListItemsExtension.create());
        parser = Parser.builder()
                .extensions(extensions)
                .build();
        renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();
    }

    public static void main(String[] args) {
        launch(args);
    }
}