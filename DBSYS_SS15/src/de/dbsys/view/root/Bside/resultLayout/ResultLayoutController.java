package de.dbsys.view.root.Bside.resultLayout;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import de.dbsys.model.Buchung;
import de.dbsys.model.SideContainer;
import de.dbsys.model.Wohnung;
import de.dbsys.view.root.Bside.searchLayout.SearchLayoutController;
import de.dbsys.view.root.Bside.searchLayout.SearchLayoutLoader;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;


public class ResultLayoutController implements Initializable {

   @FXML
   private ListView<Wohnung> gefundeneWohnungLV;

   @FXML
   private TextArea wohnungDetailTA;

   private final SideContainer container;

   private final SearchLayoutLoader searchLoader;

   private final List<Wohnung> list;

   public ResultLayoutController(final SearchLayoutLoader searchLoader, final List<Wohnung> list) {
      container = SideContainer.get();
      this.searchLoader = searchLoader;
      this.list = list;
   }

   @Override
   public void initialize(final URL location, final ResourceBundle resources) {

      gefundeneWohnungLV.setCellFactory(ign -> new WohnungsCell());

      gefundeneWohnungLV.getItems().addAll(list);
      gefundeneWohnungLV.getSelectionModel().selectedItemProperty()
            .addListener(this::handleWohnungSelected);
   }

   private void handleWohnungSelected(final Observable obs) {
      @SuppressWarnings("unchecked")
      ReadOnlyObjectProperty<Wohnung> selectedItem = (ReadOnlyObjectProperty<Wohnung>) obs;

      Wohnung wohnung = selectedItem.get();

      if (wohnung == null) {
         wohnungDetailTA.clear();
         return;
      }

      wohnungDetailTA.setText(wohnung.getWohnungsdetails());
   }

   @FXML
      void handleBackToSearch(final ActionEvent event) {
      container.setSideBLoader(searchLoader);
   }

   public Optional<Buchung> getSelectedBuchung() {

      Wohnung item = gefundeneWohnungLV.getSelectionModel().selectedItemProperty().get();

      if (item == null)
         return Optional.empty();

      SearchLayoutController controller = searchLoader.getController();
      LocalDate anreise = controller.getAnreiseDatum();
      LocalDate abreise = controller.getAbreiseDatum();
      return Optional.of(new Buchung(anreise, abreise, item));
   }

   private static class WohnungsCell extends ListCell<Wohnung> {

      @Override
      protected void updateItem(final Wohnung item, final boolean empty) {
         super.updateItem(item, empty);
         if (empty) {
            setText(null);
            return;
         }
         setText(item.getAdresse().getLand().getLandesname() + " " + item.getName());

      }

   }

}
