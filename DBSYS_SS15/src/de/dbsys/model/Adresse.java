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
}
