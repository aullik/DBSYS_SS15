package de.dbsys.model;

public class Land {

   private int landesId;
   private String landesname;

   public Land(final int landesId, final String landesname) {
      this.setLandesId(landesId);
      this.setLandesname(landesname);
   }

   public int getLandesId() {
      return landesId;
   }

   public void setLandesId(int landesId) {
      this.landesId = landesId;
   }

   public String getLandesname() {
      return landesname;
   }

   public void setLandesname(String landesname) {
      this.landesname = landesname;
   }

}
