package de.dbsys.model;

import java.time.LocalDate;


public class Buchung {

   int Buchungsnummer;
   LocalDate Buchungsdatum;
   LocalDate AnreiseDatum;
   LocalDate AbreiseDatum;
   int bewertung;
   Kunde kunde;
   Wohnung wohnung;
}
