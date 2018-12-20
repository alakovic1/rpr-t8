package ba.unsa.etf.rpr.tutorijal8;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileList {
    private ObservableList<String> putevi = FXCollections.observableArrayList();
    private StringProperty trenutni = new SimpleStringProperty();

    public ObservableList<String> getPutevi() {
        return putevi;
    }

    public void deletePutevi(){
        putevi.remove(0,putevi.size());
        trenutni.set(null);
    }

    public void addPut(String put){
        putevi.add(put);
    }
}
