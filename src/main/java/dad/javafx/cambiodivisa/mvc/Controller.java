package dad.javafx.cambiodivisa.mvc;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dad.javafx.cambiodivisa.divisa.Divisa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;

public class Controller implements Initializable {

	@FXML
	private VBox root;

	@FXML
	private FlowPane origenFlowPane;

	@FXML
	private TextField origenTextField;

	@FXML
	private ComboBox<String> origenComboBox;

	@FXML
	private FlowPane destinoFlowPane;

	@FXML
	private TextField destinoTextField;

	@FXML
	private ComboBox<String> destinoComboBox;

	@FXML
	private Button cambiarButton;

	private Divisa euro = new Divisa("Euro", 1.0);
	private Divisa libra = new Divisa("Libra", 0.8873);
	private Divisa dolar = new Divisa("Dolar", 1.2007);
	private Divisa yen = new Divisa("Yen", 133.59);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		List<String> divisasList = new ArrayList<String>();

		divisasList.add(euro.getNombre());
		divisasList.add(libra.getNombre());
		divisasList.add(dolar.getNombre());
		divisasList.add(yen.getNombre());

		ObservableList<String> divisasObservableList = FXCollections.observableArrayList(divisasList);

		origenComboBox.setItems(divisasObservableList);
		destinoComboBox.setItems(divisasObservableList);
		
		cambiarButton.setOnAction(e -> onCambiarAction(e));
	}

	public Controller() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
		loader.setController(this);
		loader.load();
	}

	private void onCambiarAction(ActionEvent e) {

		Divisa origen = yen;
		Divisa destino = dolar;
		Double cantidad = 0.0;

		try {

			cantidad = Double.parseDouble(origenTextField.getText());

			switch (origenComboBox.getSelectionModel().getSelectedItem()) {
			case "Euro":
				origen = euro;
				break;
			case "Libra":
				origen = libra;
				break;
			case "Dolar":
				origen = dolar;
				break;
			case "Yen":
				origen = yen;
				break;
			}

			switch (destinoComboBox.getSelectionModel().getSelectedItem()) {
			case "Euro":
				destino = euro;
				break;
			case "Libra":
				destino = libra;
				break;
			case "Dolar":
				destino = dolar;
				break;
			case "Yen":
				destino = yen;
				break;
			}

			destinoTextField.setText(destino.fromEuro(origen.toEuro(cantidad)).toString());

		} catch (NumberFormatException numberFormatException) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Formato incorrecto");
			alert.setHeaderText("Número no válido");
			alert.setContentText("Introduce un número válido");

			alert.showAndWait();

		} catch (NullPointerException nullPointerException) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Seleccionar divisa");
			alert.setHeaderText("Divisa no seleccionada");
			alert.setContentText("Selecciona divisa de origen y/o destino");

			alert.showAndWait();

		}

	}

	public VBox getView() {
		return root;
	}

}
