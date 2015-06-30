package de.dbsys.model;

public class Adresse {

   int adressId;
   Land land;
   String PLZ;
   String strasze;
   String hausnummer;
   String ort;

   public Adresse(final int adressID, final String strasze, final String hausnummer,
         final String ort, final String plz, final Land land) {
      this.hausnummer = hausnummer;
      this.ort = ort;
      this.adressId = adressID;
      this.strasze = strasze;
      this.PLZ = plz;
      this.land = land;
   }

   public Adresse(final String strasze2, final String hausnr, final String ort2, final String plz2,
         final Land land2) {
      this(0, strasze2, hausnr, ort2, plz2, land2);
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

   public String getOrt() {
      return ort;
   }

   @Override
   public String toString() {
      return strasze + " " + hausnummer + "\n" + PLZ + " " + ort + "\n" + land.getLandesname();
   }
}
