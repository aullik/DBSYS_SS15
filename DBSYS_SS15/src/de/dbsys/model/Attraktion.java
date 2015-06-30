package de.dbsys.model;

public class Attraktion {

   String Name;
   String Beschreibung;
   int entfernung;

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

   public int getEntfernung() {
      return entfernung;
   }

   public void setEntfernung(final int entfernung) {
      this.entfernung = entfernung;
   }
}
