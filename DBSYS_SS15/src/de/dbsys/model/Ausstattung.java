package de.dbsys.model;

public class Ausstattung {

   private String bezeichung;

   public Ausstattung(final String bezeichung) {
      this.bezeichung = bezeichung;
   }

   public String getBezeichung() {
      return bezeichung;
   }

   public void setBezeichung(final String bezeichung) {
      this.bezeichung = bezeichung;
   }
}
