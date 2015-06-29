package de.dbsys.model;

public class Adresse {

   int AdressId;
   Land land;
   String PLZ;
   String Strasze;
   String Hausnummer;

   public Adresse(final String strasze, final String plz, final Land land) {
      this.AdressId = 0;
      this.Strasze = strasze;
      this.PLZ = plz;
      this.land = land;
   }

   public int getAdressId() {
      return AdressId;
   }

   public void setAdressId(final int adressId) {
      AdressId = adressId;
   }

   public Land getLand() {
      return land;
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
      return Strasze;
   }

   public void setStrasze(final String strasze) {
      Strasze = strasze;
   }

   public String getHausnummer() {
      return Hausnummer;
   }

   public void setHausnummer(final String hausnummer) {
      Hausnummer = hausnummer;
   }
}
