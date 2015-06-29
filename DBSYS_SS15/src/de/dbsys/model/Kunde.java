package de.dbsys.model;

public class Kunde {

   int KundenId;
   String BIC;
   String IBAN;
   String email;
   String vorname;
   String nachname;
   String password;
   Adresse adresse;

   public Kunde() {
      // FIXME REMOVE
   }

   public Kunde(final String vorname, final String nachname, final String email, final String pw,
         final String iban, final String bic, final Adresse kundenAdresse) {
      this.KundenId = 0;
      this.vorname = vorname;
      this.nachname = nachname;
      this.email = email;
      password = pw;
      IBAN = iban;
      BIC = bic;
      adresse = kundenAdresse;
   }

}
