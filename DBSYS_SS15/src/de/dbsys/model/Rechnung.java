package de.dbsys.model;

import java.time.LocalDate;
import java.util.List;


public class Rechnung {

   int Rechnungsnummer;
   int betrag;
   LocalDate datum;
   List<Anzahlung> anzahlungen;
}
