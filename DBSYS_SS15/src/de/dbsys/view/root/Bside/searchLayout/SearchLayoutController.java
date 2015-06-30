package de.dbsys.view.root.Bside.searchLayout;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import de.dbsys.backend.Backend;
import de.dbsys.model.Ausstattung;
import de.dbsys.model.Land;
import de.dbsys.model.SideContainer;
import de.dbsys.model.Wohnung;
import de.dbsys.view.root.Bside.resultLayout.ResultLayoutLoader;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class SearchLayoutController implements Initializable {

   @FXML
   private DatePicker anreiseDP;

   @FXML
   private DatePicker abreiseDP;

   @FXML
   private ComboBox<Land> landCB;

   @FXML
   private TextField zimmerTF;

   @FXML
   private TextField preisMinTF;

   @FXML
   private TextField preisMaxTF;

   @FXML
   private ComboBox<Ausstattung> ausstattungCB;

   @FXML
   private ListView<Ausstattung> ausstattungslist;

   private SideContainer container;

   @Override
   public void initialize(final URL location, final ResourceBundle resources) {

      container = SideContainer.get();

      setToNumbersOnly(zimmerTF);
      setToNumbersOnly(preisMinTF);
      setToNumbersOnly(preisMaxTF);

      List<Land> lands = Backend.get().getAllLands();
      landCB.setCellFactory(ign -> new LandesCell());
      landCB.setButtonCell(new LandesCell());
      landCB.getItems().addAll(lands);

      List<Ausstattung> ausstattungen = Backend.get().getallAusstattungen();
      ausstattungCB.setCellFactory(ign -> new AusstattungsCell());
      ausstattungCB.setButtonCell(new AusstattungsCell());
      ausstattungCB.getItems().addAll(ausstattungen);

      ausstattungslist.setCellFactory(ign -> new AusstattungsCell());
   }

   private void setToNumbersOnly(final TextField tf) {
      tf.addEventFilter(KeyEvent.KEY_TYPED, event -> {
         if (!event.getCharacter().matches("\\d") || tf.getLength() >= 9)
            event.consume();
      });
   }

   @FXML
      void handleApartementSearch(final ActionEvent event) {

      LocalDate anreise = anreiseDP.getValue();
      LocalDate abreise = abreiseDP.getValue();

      if (anreise.isBefore(abreise)) {
         Alert warn = new Alert(AlertType.WARNING);
         warn.setContentText("Die Anreise muss vor der Abreise sein!");
         warn.show();
         return;
      }

      Land land = landCB.getValue();

      int zimmer;
      if (zimmerTF.getText().isEmpty())
         zimmer = 0;
      else
         zimmer = Integer.parseInt(zimmerTF.getText());

      if (anreise == null || abreise == null || zimmer == 0 || land == null) {
         Alert warn = new Alert(AlertType.WARNING);
         warn.setContentText(
               "Weder Anreise Datum, noch Abreise Datum, noch zimmer, noch Land dürfen leer sein!");
         warn.show();
         return;
      }

      int preisMin;
      if (preisMinTF.getText().isEmpty())
         preisMin = 0;
      else
         preisMin = Integer.parseInt(preisMinTF.getText());

      int preisMax;
      if (preisMaxTF.getText().isEmpty())
         preisMax = Integer.MAX_VALUE;
      else
         preisMax = Integer.parseInt(preisMaxTF.getText());

      ObservableList<Ausstattung> ausstattungen = ausstattungslist.getItems();

      List<Wohnung> list = Backend.get().searchApartments(anreise, abreise, land, zimmer, preisMin,
            preisMax, ausstattungen);

      container.setSideBLoader(
            new ResultLayoutLoader((SearchLayoutLoader) container.getSideBLoader(), list));
   }

   @FXML
      void ausstattungHinzufuegen(final ActionEvent event) {
      Ausstattung value = ausstattungCB.getValue();
      if (value == null)
         return;

      ObservableList<Ausstattung> items = ausstattungslist.getItems();
      if (!items.remove(value))
         items.add(value);
      Platform.runLater(() -> ausstattungCB.getSelectionModel().clearSelection());

   }

   private static class LandesCell extends ListCell<Land> {

      @Override
      protected void updateItem(final Land item, final boolean empty) {
         super.updateItem(item, empty);
         if (empty)
            setText("");
         else
            setText(item.getLandesname());
      }
   }

   private static class AusstattungsCell extends ListCell<Ausstattung> {

      @Override
      protected void updateItem(final Ausstattung item, final boolean empty) {
         super.updateItem(item, empty);
         if (empty)
            setText("");
         else
            setText(item.getBezeichung());
      }
   }

   public LocalDate getAnreiseDatum() {
      return anreiseDP.getValue();
   }

   public LocalDate getAbreiseDatum() {
      return abreiseDP.getValue();
   }

}
