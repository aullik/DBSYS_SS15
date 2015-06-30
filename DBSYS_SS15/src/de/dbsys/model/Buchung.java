package de.dbsys.model;

import java.time.LocalDate;


public class Buchung {

   final int buchungsnummer;
   LocalDate buchungsdatum;
   LocalDate anreiseDatum;
   LocalDate abreiseDatum;
   int bewertung;
   Kunde kunde;
   Wohnung wohnung;

   public Buchung(final LocalDate anreiseDatum, final LocalDate abreiseDatum,
         final Wohnung wohnung) {
      this.anreiseDatum = anreiseDatum;
      this.abreiseDatum = abreiseDatum;
      this.wohnung = wohnung;
      this.buchungsnummer = 0;
   }

   public Buchung(final LocalDate anreiseDatum, final LocalDate abreiseDatum, final Wohnung wohnung,
         final Kunde kunde, final int buchungsnummer) {
      this.anreiseDatum = anreiseDatum;
      this.abreiseDatum = abreiseDatum;
      this.wohnung = wohnung;
      this.buchungsnummer = buchungsnummer;
      this.kunde = kunde;
      this.buchungsdatum = buchungsdatum;
   }

   public LocalDate getBuchungsdatum() {
      return buchungsdatum;
   }

   public void setBuchungsdatum(final LocalDate buchungsdatum) {
      this.buchungsdatum = buchungsdatum;
   }

   public LocalDate getAnreiseDatum() {
      return anreiseDatum;
   }

   public void setAnreiseDatum(final LocalDate anreiseDatum) {
      this.anreiseDatum = anreiseDatum;
   }

   public LocalDate getAbreiseDatum() {
      return abreiseDatum;
   }

   public void setAbreiseDatum(final LocalDate abreiseDatum) {
      this.abreiseDatum = abreiseDatum;
   }

   public int getBewertung() {
      return bewertung;
   }

   public void setBewertung(final int bewertung) {
      this.bewertung = bewertung;
   }

   public Kunde getKunde() {
      return kunde;
   }

   public void setKunde(final Kunde kunde) {
      this.kunde = kunde;
   }

   public Wohnung getWohnung() {
      return wohnung;
   }

   public void setWohnung(final Wohnung wohnung) {
      this.wohnung = wohnung;
   }

   public int getBuchungsnummer() {
      return buchungsnummer;
   }

}
