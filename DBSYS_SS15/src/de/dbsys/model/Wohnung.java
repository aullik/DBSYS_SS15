package de.dbsys.model;

import java.util.List;


public class Wohnung {

   int wohnungsnummer;
   int preisProTag;
   int groezse;
   String name;
   int anzahlZimmer;
   Adresse adresse;
   List<Ausstattung> ausgestattet_mit;
   List<Attraktion> attraktionen;

   public Adresse getAdresse() {
      return adresse;
   }

   public int getAnzahlZimmer() {
      return anzahlZimmer;
   }

   public List<Attraktion> getAttraktionen() {
      return attraktionen;
   }

   public List<Ausstattung> getAusgestattet_mit() {
      return ausgestattet_mit;
   }

   public int getGroezse() {
      return groezse;
   }

   public String getName() {
      return name;
   }

   public int getPreisProTag() {
      return preisProTag;
   }

   public String getWohnungsdetails() {
      String format = "" + "Name: %s" + "\nPreis pro Tag: %s €" + "\nAnz. Zimmer: %s"
            + "\nGröße: %s m²" + "\nAdresse:\n %s\n";

      StringBuilder sb = new StringBuilder();
      sb.append(String.format(format, getName(), getPreisProTag(), getAnzahlZimmer(), getGroezse(),
            getAdresse().toString()));

      if (!getAusgestattet_mit().isEmpty())
         sb.append("Ausstattungen: \n");
      for (Ausstattung a : getAusgestattet_mit())
         sb.append(a.getBezeichung()).append('\n');

      if (!getAttraktionen().isEmpty())
         sb.append("Attraktionen: \n");
      for (Attraktion a : getAttraktionen())
         sb.append(a.getName()).append(" in ").append(a.getEntfernung()).append(" km\n");

      return sb.toString();
   }

   public int getWohnungsnummer() {
      return wohnungsnummer;
   }

   public void setAdresse(final Adresse adresse) {
      this.adresse = adresse;
   }

   public void setAnzahlZimmer(final int anzahlZimmer) {
      this.anzahlZimmer = anzahlZimmer;
   }

   public void setAttraktionen(final List<Attraktion> attraktionen) {
      this.attraktionen = attraktionen;
   }

   public void setAusgestattet_mit(final List<Ausstattung> ausgestattet_mit) {
      this.ausgestattet_mit = ausgestattet_mit;
   }

   public void setGroezse(final int groezse) {
      this.groezse = groezse;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public void setPreisProTag(final int preisProTag) {
      this.preisProTag = preisProTag;
   }

   public void setWohnungsnummer(final int wohnungsnummer) {
      this.wohnungsnummer = wohnungsnummer;
   }

}
