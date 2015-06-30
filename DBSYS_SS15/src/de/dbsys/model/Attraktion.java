package de.dbsys.model;

public class Attraktion {

   String Name;
   String Beschreibung;
   double entfernung;

   public String getName() {
      return Name;
   }

   public void setName(final String name) {
      Name = name;
   }

   public String getBeschreibung() {
      return Beschreibung;
   }

   public void setBeschreibung(final String beschreibung) {
      Beschreibung = beschreibung;
   }

   public double getEntfernung() {
      return entfernung;
   }

   public void setEntfernung(final int entfernung) {
      this.entfernung = entfernung;
   }

}
