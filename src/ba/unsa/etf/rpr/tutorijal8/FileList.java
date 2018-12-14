package ba.unsa.etf.rpr.tutorijal8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileList {
    private ObservableList<String> putevi = FXCollections.observableArrayList();

    public ObservableList<String> getPutevi() {
        return putevi;
    }

    public void deletePutevi(){
        putevi.remove(0,putevi.size());
    }

    public void addPut(String put){
        putevi.add(put);
    }
}
