package sample;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField id;

    @FXML
    private TextField obaveza;

    @FXML
    private TextField datumObaveze;

    @FXML
    private TextField vrijemeObaveze;

    @FXML
    private TextArea opisObaveze;

    @FXML
    private Button upisiObavezu;

    @FXML
    private Button azurirajObavezu;

    @FXML
    private Button obrisiObavezu;

    @FXML
    private TableView<Obaveze> tablicaObaveza;

    @FXML
    private TableColumn<Obaveze, Integer> stupacID;

    @FXML
    private TableColumn<Obaveze, String> stupacObaveza;

    @FXML
    private TableColumn<Obaveze, String> stupacDatum;

    @FXML
    private TableColumn<Obaveze, String> stupacVrijeme;

    @FXML
    private TableColumn<Obaveze, String> stupacOpisObaveze;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if(event.getSource() == upisiObavezu){
            napisiObavezu();
        } else if(event.getSource() == azurirajObavezu){
            azurirajObavezu();
        }
        else if(event.getSource()== obrisiObavezu){
            obrisiObavezu();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prikazObaveza();
    }

    // Metoda za povezivanje s bazom
    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/obaveze", "root", "root");
            return conn;
        } catch(Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;

        }
    }

    // Metoda za dohvat popisa obaveza
    public ObservableList<Obaveze> getPopisObaveza() {
        ObservableList<Obaveze> popisObaveza = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM popisobaveza";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Obaveze obaveze;
            while(rs.next()) {
                obaveze = new Obaveze(rs.getInt("id"), rs.getString("obaveza"), rs.getString("datumObaveze"),
                        rs.getString("vrijemeObaveze"), rs.getString("opisObaveze"));
                popisObaveza.add(obaveze);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return popisObaveza;
    }

    // Metoda za prikaz popisa obaveza iz baze svaki put kad je pozove metoda initialize
    public void prikazObaveza() {
        ObservableList<Obaveze> popis = getPopisObaveza();
        stupacID.setCellValueFactory(new PropertyValueFactory<Obaveze, Integer>("id"));
        stupacObaveza.setCellValueFactory(new PropertyValueFactory<Obaveze, String>("obaveza"));
        stupacDatum.setCellValueFactory(new PropertyValueFactory<Obaveze, String>("datumObaveze"));
        stupacVrijeme.setCellValueFactory(new PropertyValueFactory<Obaveze, String>("vrijemeObaveze"));
        stupacOpisObaveze.setCellValueFactory(new PropertyValueFactory<Obaveze, String>("opisObaveze"));

        tablicaObaveza.setItems(popis);
    }

    // Metoda za popunjavanje polja (unos obaveza)


    private void napisiObavezu() {
        String query = "INSERT INTO popisobaveza VALUES (" + id.getText() + ",'" + obaveza.getText() + "','" + datumObaveze.getText() +
                "','" + vrijemeObaveze.getText() + "','" + opisObaveze.getText() + "')";
        executeQuery(query);
        prikazObaveza();
        id.clear();
        obaveza.clear();
        datumObaveze.clear();
        vrijemeObaveze.clear();
        opisObaveze.clear();
    }

    private void obrisiObavezu(){
        String query= "DELETE FROM popisobaveza WHERE id="  + id.getText()+ "";
        executeQuery(query);
        prikazObaveza();
    }

    private void azurirajObavezu(){

            String query = "UPDATE popisobaveza SET obaveza = '" + obaveza.getText() + "', datumObaveze = '" + datumObaveze.getText() + "', vrijemeObaveze = '" +
                    vrijemeObaveze.getText() + "', opisObaveze = '" + opisObaveze.getText() + "' WHERE id = " + id.getText() + "";
            executeQuery(query);
            prikazObaveza();
        }


    private void executeQuery(String query) {

        Connection conn= getConnection();
        Statement st;
        try{
            st= conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void handleMouseAction(javafx.scene.input.MouseEvent mouseEvent) {
        // odabrana obaveza iz tablice
        Obaveze o = tablicaObaveza.getSelectionModel().getSelectedItem();
        id.setText("" + o.getId());  //navodnike smo dodali kako bi se konkatenirao id i dobio string
        obaveza.setText(o.getObaveza());
        datumObaveze.setText(o.getDatumObaveze());
        vrijemeObaveze.setText(o.getVrijemeObaveze());
        opisObaveze.setText(o.getOpisObaveze());

    }
}

