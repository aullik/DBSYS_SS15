package de.dbsys.backend;

import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import de.dbsys.model.Adresse;
import de.dbsys.model.Ausstattung;
import de.dbsys.model.Buchung;
import de.dbsys.model.Kunde;
import de.dbsys.model.Land;
import de.dbsys.model.Wohnung;
import javafx.collections.ObservableList;


public final class Backend {

   private final static DateTimeFormatter DTF = DateTimeFormatter
         .ofLocalizedDate(FormatStyle.MEDIUM);

   private Connection con = getConnection();

   private Backend() {
      this.con = getConnection();
      Security.addProvider(new BouncyCastleProvider());
      // Treiber laden
      try {
         DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   private final static Backend INSTANCE = new Backend();

   public static Backend get() {
      return INSTANCE;
   }

   private static final String dbHost = "oracle12c.in.htwg-konstanz.de"; // Hostname
   private static final String dbPort = "1521"; // Port -- Standard: 3306
   private static final String dbName = "ora12c"; // Datenbankname
   private static final String dbUser = "dbsys15"; // Datenbankuser
   private static final String dbPass = "dbsys15"; // Datenbankpasswort

   private Connection getConnection() {
      try {
         if (con == null || con.isClosed())
            con = DriverManager.getConnection(
                  "jdbc:oracle:thin:@" + dbHost + ":" + dbPort + "/" + dbName, dbUser, dbPass);
         return con;
      } catch (SQLException t) {
         throw new RuntimeException(t);
      }
   }

   private final PreparedStatement loginQuery = createPreparedStatement(
         "Select * from dbsys20.Kunde where mailadresse = ? and passwort = ?");

   private PreparedStatement createPreparedStatement(final String sql) {
      try {
         return con.prepareStatement(sql);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   private Statement createStatement() {
      try {
         return getConnection().createStatement();
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   private void handleSQLException(final SQLException e) {
      try {
         con.close();
      } catch (SQLException ignore) {}
      System.err.println("SQLException: " + e.getMessage());
      System.err.println("SQLState: " + e.getSQLState());
      System.err.println("VendorError: " + e.getErrorCode());
      e.printStackTrace();
   }

   public Optional<Kunde> login(final String email, final String pw) {
      try {
         // loginQuery.setString(1, email);
         // loginQuery.setString(2, pw);
         // ResultSet res = loginQuery.executeQuery();

         Statement stm = createStatement();
         String select = "Select * from dbsys20.Kunde where mailadresse = '" + email
               + "' and passwort = '" + pw + "'";

         ResultSet res = stm.executeQuery(select);

         if (!res.next()) {
            System.err.println("no result"); // FIXME
            res.close();
            return Optional.empty();
         }

         Kunde kd = createKunde(res);
         stm.close();
         return Optional.ofNullable(kd);
      } catch (SQLException e) {
         System.err.println("Exception while executing login-statement");
         handleSQLException(e);
         throw new RuntimeException(e);
      }

      // return Optional.of(new Kunde());

   }

   public List<Land> getAllLands() {
      List<Land> list = new LinkedList<>();
      try {
         Statement stm = createStatement();
         // String mySearchQuery = "SELECT * FROM land";
         String mySearchQuery = "SELECT landesid, landesname FROM dbsys20.land ORDER BY LANDESNAME";
         ResultSet rset = stm.executeQuery(mySearchQuery);
         while (rset.next()) {
            Land tmp = new Land(rset.getInt("landesid"), rset.getString("landesname"));
            list.add(tmp);
         }
         stm.close();
         con.commit();

      } catch (SQLException e) {
         try {
            con.rollback();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         System.err.println("Exception while selecting * from land");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
      return list;
   }

   public List<Ausstattung> getallAusstattungen() {
      LinkedList<Ausstattung> list = new LinkedList<>();
      try {
         Statement stm = createStatement();
         String mySearchQuery = "SELECT * FROM dbsys20.ausstattung";
         ResultSet rset = stm.executeQuery(mySearchQuery);
         while (rset.next()) {
            Ausstattung tmp = new Ausstattung(rset.getString("bezeichnung"));
            list.add(tmp);
         }
         stm.close();
         con.commit();
      } catch (SQLException e) {
         try {
            con.rollback();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         System.err.println("Exception while selecting * from ausstattung");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
      return list;
   }

   public List<Wohnung> searchApartments(final LocalDate anreise, final LocalDate abreise,
         final Land land, final int zimmer, final int preisMin, final int preisMax,
         final ObservableList<Ausstattung> ausstattungen) {
      List<Wohnung> list = new LinkedList<>();
      try {
         Statement stm = createStatement();
         StringBuilder sb = new StringBuilder();
         /*
         sb.append(
               "SELECT * FROM dbsys20.Ferienwohnung f1 JOIN dbsys20.adresse a1 ON f1.adressid = a1.adressid "
                     + "JOIN dbsys20.land l1 ON l1.landesid = a1.landesid JOIN dbsys20.ausgestattetmit am1 ON f1.wohnungsnummer = am1.wohnungsnummer "
                     + "LEFT JOIN dbsys20.liegtinnaehe lin ON f1.wohnungsnummer = lin.wohnungsnummer JOIN dbsys20.attraktion at ON lin.attraktionsname = at.attraktionsname "
                     + "LEFT JOIN dbsys20.Buchung b1 ON f1.wohnungsnummer  = b1.wohnungsnummer");
         sb.append(" WHERE l1.landesname = '").append(land.getLandesname()).append("'");
         for (Ausstattung aus : ausstattungen)
            sb.append("AND am1.bezeichnung = '").append(aus.getBezeichung()).append("'");
         sb.append("AND b1.abreisedatum > to_date('").append(abreise.toString()).append("')");
         sb.append("AND b1.anreisedatum < to_date('").append(anreise.toString()).append("')");
         */
         sb.append(
               "SELECT * FROM dbsys20.Ferienwohnung f JOIN dbsys20.adresse a ON f.adressid = a.adressid JOIN dbsys20.land l ON l.landesid = a.landesid "
                     + "JOIN dbsys20.ausgestattetmit am ON f.wohnungsnummer = am.wohnungsnummer LEFT JOIN dbsys20.buchung b ON f.wohnungsnummer     = b.wohnungsnummer ");
         sb.append("WHERE l.landesname = '").append(land.getLandesname()).append("' ");
         for (Ausstattung aus : ausstattungen)
            sb.append("AND am.bezeichnung = '").append(aus.getBezeichung()).append("' ");
         sb.append(
               "AND f.wohnungsnummer Not IN ( SELECT f1.wohnungsnummer FROM dbsys20.Ferienwohnung f1 JOIN dbsys20.adresse a1 ON f1.adressid = a1.adressid "
                     + "JOIN dbsys20.land l1 ON l1.landesid = a1.landesid JOIN dbsys20.ausgestattetmit am1 ON f1.wohnungsnummer = am1.wohnungsnummer "
                     + "LEFT JOIN dbsys20.Buchung b1 ON f1.wohnungsnummer     = b1.wohnungsnummer ");

         sb.append("WHERE l1.landesname = '").append(land.getLandesname()).append("' ");
         for (Ausstattung aus : ausstattungen)
            sb.append("AND am1.bezeichnung = '").append(aus.getBezeichung()).append("' ");
         sb.append("AND b1.abreisedatum > to_date('").append(anreise.format(DTF)).append("') ");
         sb.append("AND b1.anreisedatum < to_date('").append(abreise.format(DTF)).append("') ");
         sb.append(")");

         String mySearchQuery = sb.toString();
         System.out.println(mySearchQuery);
         ResultSet rset = stm.executeQuery(mySearchQuery);

         HashMap<Integer, List<Ausstattung>> ausMap = new HashMap<>();
         // HashMap<Integer, List<Attraktion>> atMap = new HashMap<>();

         while (rset.next()) {
            Wohnung tmp = getWohnung(rset);
            if (!ausMap.containsKey(tmp.getWohnungsnummer())) {
               list.add(tmp);
               ausMap.put(tmp.getWohnungsnummer(), tmp.getAusgestattet_mit());
               // atMap.put(tmp.getWohnungsnummer(), tmp.getAttraktionen());
            } else
               ausMap.get(tmp.getWohnungsnummer()).add(tmp.getAusgestattet_mit().get(0));
            // atMap.get(tmp.getWohnungsnummer()).add(tmp.getAttraktionen().get(0));
         }

         for (Wohnung w : list)
            // w.setAttraktionen(atMap.get(w.getWohnungsnummer()));
            w.setAusgestattet_mit(ausMap.get(w.getWohnungsnummer()));

         stm.close();
         con.commit();
      } catch (SQLException e) {
         try {
            con.rollback();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         System.err.println("Exception while searching for apartements");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
      return new LinkedList<>();
   }

   public Optional<Buchung> getLastCompletedBooking(final Kunde kunde) {
      // nicht verändern, nicht gebraucht für aufgabe
      return Optional.empty();
   }

   public void evaluateBooking(final Buchung buchung, final int bewertung) {
      // nicht verändern, nicht gebraucht für aufgabe
      // bewertung zwischen 10 und 60
   }

   public Optional<Buchung> bookWohnung(final Buchung buchung) {
      Buchung book;
      try {
         Statement stm = createStatement();
         StringBuilder sb = new StringBuilder();
         sb.append("INSERT INTO dbsys20.buchung VALUES (");
         sb.append("dbsys20.sqBuchungsnummer.nextVal, ");
         sb.append("SYSDATE, ");
         sb.append("to_date('").append(buchung.getAnreiseDatum().format(DTF)).append("'), ");
         sb.append("to_date('").append(buchung.getAbreiseDatum().format(DTF)).append("'), ");
         sb.append("null, ");
         sb.append("null, ");
         sb.append("null, ");
         sb.append("null, ");
         sb.append("null, ");
         sb.append(buchung.getWohnung().getWohnungsnummer()).append(", ");
         sb.append(buchung.getKunde().getKundenId()).append(")");

         String myInsertQuery = sb.toString();
         System.out.println(myInsertQuery);
         stm.executeUpdate(myInsertQuery);

         stm.close();

         Statement stm2 = createStatement();
         String mySearchQuery = "Select dbsys20.sqBuchungsnummer.currval from dual";
         // String mySearchQuery = "SELECT buchungsnummer FROM dbsys20.buchung WHERE Kundenid = "
         // + buchung.getKunde().getKundenId()
         // + "AND buchungsdatum = SYSDATE AND wohnungsnummer = "
         // + buchung.getWohnung().getWohnungsnummer();

         ResultSet rest = stm2.executeQuery(mySearchQuery);
         if (!rest.next()) {
            stm2.close();
            con.commit();
            return Optional.empty();
         }

         book = new Buchung(buchung.getAnreiseDatum(), buchung.getAbreiseDatum(),
               buchung.getWohnung(), buchung.getKunde(), rest.getInt(1), LocalDate.now());

         stm2.close();
         con.commit();
         return Optional.of(book);

      } catch (SQLException e) {
         try {
            con.rollback();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         System.err.println("Exception while booking an apartement");
         handleSQLException(e);
         throw new RuntimeException(e);
      }

   }

   // Methode zum Erstellen von Kundenobjekten aus einem ResultSet
   private Kunde createKunde(final ResultSet set) {
      try {
         Statement stm = createStatement();
         String mySearchQuery = "SELECT * FROM dbsys20.kunde k Join dbsys20.adresse a ON k.adressid = a.adressid JOIN dbsys20.land l ON a.landesid = l.landesid WHERE k.kundenid = "
               + set.getInt("kundenid");

         ResultSet res = stm.executeQuery(mySearchQuery);

         if (!res.next())
            return null;

         Land la = new Land(res.getInt("landesid"), res.getString("landesname"));

         Adresse adr = new Adresse(res.getInt("adressId"), res.getString("strasze"),
               res.getString("hausnummer"), "testort"// FIXME: add ort res.getString("ort")
               , res.getString("plz"), la);

         Kunde kd = new Kunde(set.getString("vorname"), set.getString("nachname"),
               set.getString("mailadresse"), set.getString("passwort"), set.getString("IBAN"),
               set.getString("bic"), adr);
         stm.close();
         return kd;

      } catch (SQLException e) {
         System.err.println("Exception while creating 'kunde' from ResultSet");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
   }

   public Optional<Kunde> createNewUser(final Kunde newKunde) {
      try {
         int adressId = insertAdresse(newKunde.getAdresse());
         Statement stm = createStatement();
         StringBuilder sb = new StringBuilder();
         sb.append(
               "INSERT INTO dbsys20.kunde(kundenid, bic, iban, mailadresse, vorname, nachname, passwort, adressid) VALUES (");
         sb.append("dbsys20.sqKundenId.nextval").append(", ");
         sb.append("'").append(newKunde.getBIC()).append("', ");
         sb.append("'").append(newKunde.getIBAN()).append("', ");
         sb.append("'").append(newKunde.getEmail()).append("', ");
         sb.append("'").append(newKunde.getVorname()).append("', ");
         sb.append("'").append(newKunde.getNachname()).append("', ");
         sb.append("'").append(newKunde.getPassword()).append("', ");
         sb.append(Integer.toString(adressId)).append(")");

         String myInsertQuery = sb.toString();
         System.out.println(myInsertQuery); // FIXME REMOVE

         stm.executeQuery(myInsertQuery);
         stm.close();

         Optional<Kunde> login = login(newKunde.getEmail(), newKunde.getPassword());
         con.commit();
         return login;

      } catch (SQLException e) {
         try {
            con.rollback();
         } catch (SQLException se) {
            se.printStackTrace();
         }

         System.err.println("Exception while creating a new User");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
   }

   // Methode für den Insert einer Adresse
   private int insertAdresse(final Adresse adr) throws SQLException {
      try {
         Statement stm = createStatement();
         StringBuilder sb = new StringBuilder();
         sb.append(
               "INSERT INTO dbsys20.Adresse (AdressId, Landesid, plz, strasze, hausnummer) VALUES (");
         sb.append("dbsys20.SQAdressId.nextval").append(", ");
         sb.append(Integer.toString(adr.getLand().getLandesId())).append(", ");
         sb.append("'").append(adr.getPLZ()).append("', ");
         sb.append("'").append(adr.getStrasze()).append("', ");
         sb.append("'").append(adr.getHausnummer()).append("') ");

         String myInsertQuery = sb.toString();
         System.out.println(myInsertQuery); // FIXME REMOVE
         stm.executeQuery(myInsertQuery);

         String select = "Select dbsys20.SQAdressID.currVal from dual";

         ResultSet resultSet = stm.executeQuery(select);
         if (!resultSet.next())
            throw new SQLException("no result");

         int adressID = resultSet.getInt(1);

         // new Adresse(adressID, adr.getStrasze(), adr.getHausnummer(), adr.getOrt(), adr.getPLZ(),
         // adr.getLand());

         stm.close();
         return adressID;
         // con.commit(); must not commit. will be commited in createNewUser
      } catch (SQLException e) {
         System.err.println("Exception while inserting new adress");
         throw e;
      }
   }

   // Methode um Daten aus einem ResultSet in eine Wohnung umzuwandeln
   private Wohnung getWohnung(final ResultSet set) {

      Wohnung apt = new Wohnung();
      try {
         // Attraktion at = new Attraktion();
         // at.setBeschreibung(set.getString("beschreibung"));
         // at.setEntfernung(set.getInt("distanz"));
         // at.setName(set.getString("attraktionsname"));

         Land la = new Land(set.getInt("landesid"), set.getString("landesname"));
         Adresse adr = new Adresse(set.getInt("adressid"), set.getString("strasze"),
               set.getString("hausnummer"), "testort", // FIXME set.getString("ort"),
               set.getString("plz"), la);
         Ausstattung aus = new Ausstattung(set.getString("bezeichnung"));

         apt.setAnzahlZimmer(set.getInt("anzahlzimmer"));

         apt.setAusgestattet_mit(new LinkedList<>());
         apt.setAttraktionen(new LinkedList<>());
         // apt.getAttraktionen().add(at);
         apt.getAusgestattet_mit().add(aus);

         apt.setGroezse(set.getInt("groesze"));
         apt.setName(set.getString("name"));
         apt.setPreisProTag(set.getInt("preisprotag"));
         apt.setWohnungsnummer(set.getInt("wohnungsnummer"));
         apt.setAdresse(adr);

      } catch (SQLException e) {
         try {
            con.rollback();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         System.err.println("Exception while reading from ResultSet in 'getWohnung'");
         handleSQLException(e);
         throw new RuntimeException(e);
      }

      return apt;
   }

   // private LocalDate toLocalDate(final Date date) {
   // return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
   // }
}
