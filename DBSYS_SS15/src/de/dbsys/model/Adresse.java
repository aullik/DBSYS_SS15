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

   public int getAdressId() {
      return adressId;
   }

   public void setAdressId(final int adressId) {
      this.adressId = adressId;
   }

   public void setLand(final Land land) {
      this.land = land;
   }

   public String getPLZ() {
      return PLZ;
   }

   public void setPLZ(final String pLZ) {
      PLZ = pLZ;
   }

   public String getStrasze() {
      return strasze;
   }

   public void setStrasze(final String strasze) {
      this.strasze = strasze;
   }

   public String getHausnummer() {
      return hausnummer;
   }

   public void setHausnummer(final String hausnummer) {
      this.hausnummer = hausnummer;

   }

   @Override
   public String toString() {
      return strasze + " " + hausnummer + "\n" + PLZ + " " + ort + "\n" + land.getLandesname();
   }
}
