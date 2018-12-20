package ba.unsa.etf.rpr.tutorijal8;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Controller implements Initializable {

    private FileList model;
    public Button traziBtn;
    public TextField unos;
    public ListView<String> listaPuteva;
    public File root = new File(System.getProperty("user.home"));
    public Button prekiniBtn;

    public Controller(FileList model) {
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaPuteva.setItems(model.getPutevi());

        listaPuteva.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends String> c) {
                Stage x =new Stage();
                x.setTitle("Slanje pošte");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("saljiProzor.fxml"));
                loader.setController(new SlanjeController());
                Parent root2 = null;
                try {
                    root2 = loader.load();
                    if(root2==null){
                        System.out.println("fakat jes null");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                x.setScene(new Scene( root2,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
                x.initOwner(Main.stage.getScene().getWindow());
                x.initModality(Modality.APPLICATION_MODAL);
                x.show();
            }
        });
    }

    public void trazi(ActionEvent actionEvent) {
        model.deletePutevi();
        traziBtn.setDisable(true);
        prekiniBtn.setDisable(false);
        Finder myFinder = new Finder();
        Thread myThread = new Thread(myFinder);
        prekidacZaPretrazivanje(true);
        myThread.start();

    }

    public void prekini(ActionEvent actionEvent) {
        prekiniBtn.setDisable(true);
        traziBtn.setDisable(false);
    }

    public void prekidacZaPretrazivanje(boolean vrijednost) {
        traziBtn.setDisable(vrijednost);
        prekiniBtn.setDisable(!vrijednost);
    }

    public class Finder implements Runnable{

        @Override
        public void run() {
            find(unos.getText(),root.getAbsolutePath());
        }

        public void find(String name, String parent){
            if(prekiniBtn.isDisabled()){
                try {
                    Thread.currentThread().join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            File[] child = new File(parent).listFiles();
            if (child != null) {
                if(child.length!=0){
                    for (File aChild : child) {
                        if (aChild.getName().contains(name) && aChild.isFile()) {
                            Platform.runLater(()-> {
                                model.addPut(aChild.getAbsolutePath()); });
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (aChild.isDirectory()) {
                            find(name, aChild.getAbsolutePath());
                        }
                    }
                }
            }
            if(parent.equals(root.getAbsolutePath())){
                traziBtn.setDisable(false);
            }
        }

    }
}
