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

   public int getKundenId() {
      return KundenId;
   }

   public String getBIC() {
      return BIC;
   }

   public String getIBAN() {
      return IBAN;
   }

   public String getEmail() {
      return email;
   }

   public String getVorname() {
      return vorname;
   }

   public String getNachname() {
      return nachname;
   }

   public String getPassword() {
      return password;
   }

   public Adresse getAdresse() {
      return adresse;
   }

   public void setKundenId(final int kundenId) {
      KundenId = kundenId;
   }

   public void setBIC(final String bIC) {
      BIC = bIC;
   }

   public void setIBAN(final String iBAN) {
      IBAN = iBAN;
   }

   public void setEmail(final String email) {
      this.email = email;
   }

   public void setVorname(final String vorname) {
      this.vorname = vorname;
   }

   public void setNachname(final String nachname) {
      this.nachname = nachname;
   }

   public void setPassword(final String password) {
      this.password = password;
   }

   public void setAdresse(final Adresse adresse) {
      this.adresse = adresse;
   }

}
