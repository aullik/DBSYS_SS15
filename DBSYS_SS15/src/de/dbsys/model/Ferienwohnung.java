package de.dbsys.model;

import java.util.List;


public class Ferienwohnung {

   int Wohnungsnummer;
   int PreisProTag;
   int Groezse;
   String Name;
   int AnzahlZimmer;
   Adresse adresse;
   List<Ausstattung> ausgestattet_mit;
   List<Attraktion> attraktionen;

}
