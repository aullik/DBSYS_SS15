package de.dbsys.model;

import java.util.List;


public class Wohnung {

   int Wohnungsnummer;
   int PreisProTag;
   int Groezse;
   String Name;
   int AnzahlZimmer;
   Adresse adresse;
   List<Ausstattung> ausgestattet_mit;
   List<Attraktion> attraktionen;

   public int getWohnungsnummer() {
      return Wohnungsnummer;
   }

   public void setWohnungsnummer(final int wohnungsnummer) {
      Wohnungsnummer = wohnungsnummer;
   }

   public int getPreisProTag() {
      return PreisProTag;
   }

   public void setPreisProTag(final int preisProTag) {
      PreisProTag = preisProTag;
   }

   public int getGroezse() {
      return Groezse;
   }

   public void setGroezse(final int groezse) {
      Groezse = groezse;
   }

   public String getName() {
      return Name;
   }

   public void setName(final String name) {
      Name = name;
   }

   public int getAnzahlZimmer() {
      return AnzahlZimmer;
   }

   public void setAnzahlZimmer(final int anzahlZimmer) {
      AnzahlZimmer = anzahlZimmer;
   }

   public Adresse getAdresse() {
      return adresse;
   }

   public void setAdresse(final Adresse adresse) {
      this.adresse = adresse;
   }

   public List<Ausstattung> getAusgestattet_mit() {
      return ausgestattet_mit;
   }

   public void setAusgestattet_mit(final List<Ausstattung> ausgestattet_mit) {
      this.ausgestattet_mit = ausgestattet_mit;
   }

   public List<Attraktion> getAttraktionen() {
      return attraktionen;
   }

   public void setAttraktionen(final List<Attraktion> attraktionen) {
      this.attraktionen = attraktionen;
   }

}
