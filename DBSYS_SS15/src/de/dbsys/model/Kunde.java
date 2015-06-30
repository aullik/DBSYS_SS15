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

   public Kunde(final int kudenId, final String vorname, final String nachname, final String email,
         final String pw, final String iban, final String bic, final Adresse kundenAdresse) {
      this.kundenId = kudenId;
      this.vorname = vorname;
      this.nachname = nachname;
      this.email = email;
      password = pw;
      IBAN = iban;
      BIC = bic;
      adresse = kundenAdresse;
   }

   public Kunde(final String string, final String string2, final String string3,
         final String string4, final String string5, final String string6, final Adresse adr) {
      this(0, string, string2, string3, string4, string5, string6, adr);
      // TODO Auto-generated constructor stub
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
