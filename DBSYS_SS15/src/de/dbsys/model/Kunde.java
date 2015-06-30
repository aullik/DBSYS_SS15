package de.dbsys.model;

public class Kunde {

   final int kundenId;
   String BIC;
   String IBAN;
   String email;
   String vorname;
   String nachname;
   String password;
   Adresse adresse;

   public Kunde() {
      this.kundenId = 0;
      this.vorname = "Nicolas";
      this.nachname = "Wehrle";
      // FIXME REMOVE
   }

   public Kunde(final String vorname, final String nachname, final String email, final String pw,
         final String iban, final String bic, final Adresse kundenAdresse) {
      this.kundenId = 0;
      this.vorname = vorname;
      this.nachname = nachname;
      this.email = email;
      password = pw;
      IBAN = iban;
      BIC = bic;
      adresse = kundenAdresse;
   }

   public String getBIC() {
      return BIC;
   }

   public void setBIC(final String bIC) {
      BIC = bIC;
   }

   public String getIBAN() {
      return IBAN;
   }

   public void setIBAN(final String iBAN) {
      IBAN = iBAN;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(final String email) {
      this.email = email;
   }

   public String getVorname() {
      return vorname;
   }

   public void setVorname(final String vorname) {
      this.vorname = vorname;
   }

   public String getNachname() {
      return nachname;
   }

   public void setNachname(final String nachname) {
      this.nachname = nachname;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(final String password) {
      this.password = password;
   }

   public Adresse getAdresse() {
      return adresse;
   }

   public void setAdresse(final Adresse adresse) {
      this.adresse = adresse;
   }

   public int getKundenId() {
      return kundenId;

   }

}
