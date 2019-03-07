package task.reader.core;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;

/**
 * Created by user on 3/1/2019.
 */
public class RecordView {

    private final SimpleStringProperty fileName;
    private final SimpleLongProperty size;
    private final SimpleStringProperty viewButton;
    private final String filePath;
    private boolean selected;

    public RecordView(File file) {
        fileName = new SimpleStringProperty(file.getName());
        size = new SimpleLongProperty(file.length());
        viewButton = new SimpleStringProperty();
        filePath = file.getAbsolutePath();
    }

    public String getFileName() {
        return fileName.get();
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName.set(fileName);
    }

    public long getSize() {
        return size.get();
    }

    public SimpleLongProperty sizeProperty() {
        return size;
    }

    public void setSize(final long size) {
        this.size.set(size);
    }

    public String getViewButton() {
        return viewButton.get();
    }

    public SimpleStringProperty viewButtonProperty() {
        return viewButton;
    }

    public void setViewButton(final String viewButton) {
        this.viewButton.set(viewButton);
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
    }
}
