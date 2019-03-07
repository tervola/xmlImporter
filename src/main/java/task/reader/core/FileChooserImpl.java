package task.reader.core;/**
 * Created by user on 2/27/2019.
 */

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileChooserImpl extends Application {
    private TableView tableView;
    private static ObservableList<RecordView> data;
    private Button buttonRemoveRecord;
    private Button buttonImport;
    private Label totalValue;

    static List<RecordView> openWindow() {
        launch();
        return data;
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Transaction Importer");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent event) {
                if (data != null) {
                    data.clear();
                }
            }
        });

        final FileChooser fileChooser = createFileChooser();
        final Button buttonOpen = createOpenButton(fileChooser, primaryStage);

        buttonImport = createImportButton(primaryStage);
        buttonRemoveRecord = createRemoveRecordButton();
        final Label totalFilesLabel = new Label("Chosen files: ");
        totalValue = new Label("0");

        tableView = createTableResult();

        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(buttonOpen, 0, 0);
        GridPane.setConstraints(buttonRemoveRecord, 1, 0);
        GridPane.setConstraints(buttonImport, 2, 0);
        GridPane.setConstraints(totalFilesLabel, 0, 1);
        GridPane.setConstraints(totalValue, 1, 1);

        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(buttonOpen, buttonImport, buttonRemoveRecord, totalFilesLabel, totalValue);

        final Pane rootGroup = new VBox(5);
        rootGroup.getChildren().addAll(inputGridPane, tableView);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));

        primaryStage.setScene(new Scene(rootGroup, 450, 400));
        primaryStage.show();

    }

    private Button createRemoveRecordButton() {
        return new Button("Remove Record") {
            {
                setDisable(true);
                setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(final ActionEvent e) {
                                data.remove(tableView.getSelectionModel().getSelectedIndex());
                                totalValue.setText(String.valueOf(data.size()));
                            }
                        });
            }
        };
    }

    private Button createImportButton(final Stage primaryStage) {
        return new Button("Import...") {
            {
                setDisable(true);
                setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(final ActionEvent e) {
                                primaryStage.close();
                            }
                        });
            }
        };
    }


    private TableView createTableResult() {
        return new TableView() {
            {
                setPrefSize(700, 400);

                final String fileNameColTitle = "FileName";
                final TableColumn fileNameColumn = new TableColumn(fileNameColTitle);
                fileNameColumn.setPrefWidth(200);
                fileNameColumn.setCellValueFactory(new PropertyValueFactory<RecordView, String>(fileNameColTitle));

                final String sizeColTitle = "Size";
                final TableColumn sizeColumn = new TableColumn(sizeColTitle);
                sizeColumn.setPrefWidth(100);
                sizeColumn.setCellValueFactory(new PropertyValueFactory<RecordView, Integer>(sizeColTitle));
                sizeColumn.setStyle( "-fx-alignment: CENTER-RIGHT;");

                final String preViewColTitle = "PreView";
                final TableColumn preViewColumn = new TableColumn(preViewColTitle);
                preViewColumn.setPrefWidth(100);
                Callback<TableColumn<RecordView, Void>, TableCell<RecordView, Void>> cellFactory = new Callback<TableColumn<RecordView, Void>, TableCell<RecordView, Void>>() {
                    @Override
                    public TableCell<RecordView, Void> call(final TableColumn<RecordView, Void> param) {
                        final TableCell<RecordView, Void> cell = new TableCell<RecordView, Void>() {

                            private final Button btn = new Button("Preview");
                            {
                                btn.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(final ActionEvent event) {
                                        RecordView record = getTableView().getItems().get(getIndex());
                                        try {
                                            Desktop.getDesktop().open(Paths.get(record.getFilePath()).toFile());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn);
                                    setAlignment(Pos.CENTER);
                                }
                            }
                        };
                        return cell;
                    }
                };

                preViewColumn.setCellFactory(cellFactory);

                getColumns().addAll(fileNameColumn, sizeColumn, preViewColumn);

                getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
                        if (selectedIndex >= 0) {
                            buttonRemoveRecord.setDisable(false);
                        } else {
                            buttonRemoveRecord.setDisable(true);
                            buttonImport.setDisable(true);
                        }
                    }
                });
                setScaleShape(true);
            }
        };
    }

    private Button createOpenButton(final FileChooser fileChooser, final Stage primaryStage) {
        return new Button("Open XML...") {
            {
                setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(final ActionEvent e) {
                                java.util.List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);
                                if (files != null) {
                                    buttonImport.setDisable(false);
                                    fillTable(files);
                                    totalValue.setText(String.valueOf(files.size()));
                                } else {
                                    totalValue.setText("0");
                                    buttonImport.setDisable(true);
                                }
                            }
                        });
            }
        };
    }

    private void fillTable(final List<File> files) {
        List<RecordView> records = new ArrayList<>();
        for (final File file : files) {
            records.add(new RecordView(file));
        }

        data = FXCollections.observableArrayList(records);
        tableView.setItems(data);

    }

    private FileChooser createFileChooser() {
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        return fileChooser;
    }
}
