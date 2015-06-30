package de.dbsys.model;

public class Adresse {

   int adressId;
   Land land;
   String PLZ;
   String strasze;
   String hausnummer;
   String ort;

   public Adresse(final String strasze, final String hausnummer, final String ort, final String plz,
         final Land land) {
      this.hausnummer = hausnummer;
      this.ort = ort;
      this.adressId = 0;
      this.strasze = strasze;
      this.PLZ = plz;
      this.land = land;
   }

   public Land getLand() {
      return land;
   }

   @Override
   public String toString() {
      return strasze + " " + hausnummer + "\n" + PLZ + " " + ort + "\n" + land.getLandesname();
   }
}
