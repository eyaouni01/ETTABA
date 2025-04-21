/***********************************************************************
 * Module:  UserService.java
 * Author:  eyaou
 * Purpose: Defines the Class UserService
 ***********************************************************************/

import java.util.*;

/** @pdOid 789d8813-54c0-4a88-86b7-41c5ce22c295 */
public class UserService extends Subject {
   /** @pdRoleInfo migr=no name=EmailNotificationObserver assc=uses coll=java.util.Collection impl=java.util.HashSet mult=0..* */
   public java.util.Collection<EmailNotificationObserver> emailNotificationObserver;
   /** @pdRoleInfo migr=no name=LoggingObserver assc=uses coll=java.util.Collection impl=java.util.HashSet mult=0..* */
   public java.util.Collection<LoggingObserver> loggingObserver;
   
   /** @pdOid 60facda9-9aa3-4946-bb1b-abc282cbbad7 */
   public User saveUser() {
      // TODO: implement
      return null;
   }
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<EmailNotificationObserver> getEmailNotificationObserver() {
      if (emailNotificationObserver == null)
         emailNotificationObserver = new java.util.HashSet<EmailNotificationObserver>();
      return emailNotificationObserver;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorEmailNotificationObserver() {
      if (emailNotificationObserver == null)
         emailNotificationObserver = new java.util.HashSet<EmailNotificationObserver>();
      return emailNotificationObserver.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newEmailNotificationObserver */
   public void setEmailNotificationObserver(java.util.Collection<EmailNotificationObserver> newEmailNotificationObserver) {
      removeAllEmailNotificationObserver();
      for (java.util.Iterator iter = newEmailNotificationObserver.iterator(); iter.hasNext();)
         addEmailNotificationObserver((EmailNotificationObserver)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newEmailNotificationObserver */
   public void addEmailNotificationObserver(EmailNotificationObserver newEmailNotificationObserver) {
      if (newEmailNotificationObserver == null)
         return;
      if (this.emailNotificationObserver == null)
         this.emailNotificationObserver = new java.util.HashSet<EmailNotificationObserver>();
      if (!this.emailNotificationObserver.contains(newEmailNotificationObserver))
         this.emailNotificationObserver.add(newEmailNotificationObserver);
   }
   
   /** @pdGenerated default remove
     * @param oldEmailNotificationObserver */
   public void removeEmailNotificationObserver(EmailNotificationObserver oldEmailNotificationObserver) {
      if (oldEmailNotificationObserver == null)
         return;
      if (this.emailNotificationObserver != null)
         if (this.emailNotificationObserver.contains(oldEmailNotificationObserver))
            this.emailNotificationObserver.remove(oldEmailNotificationObserver);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllEmailNotificationObserver() {
      if (emailNotificationObserver != null)
         emailNotificationObserver.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<LoggingObserver> getLoggingObserver() {
      if (loggingObserver == null)
         loggingObserver = new java.util.HashSet<LoggingObserver>();
      return loggingObserver;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorLoggingObserver() {
      if (loggingObserver == null)
         loggingObserver = new java.util.HashSet<LoggingObserver>();
      return loggingObserver.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newLoggingObserver */
   public void setLoggingObserver(java.util.Collection<LoggingObserver> newLoggingObserver) {
      removeAllLoggingObserver();
      for (java.util.Iterator iter = newLoggingObserver.iterator(); iter.hasNext();)
         addLoggingObserver((LoggingObserver)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newLoggingObserver */
   public void addLoggingObserver(LoggingObserver newLoggingObserver) {
      if (newLoggingObserver == null)
         return;
      if (this.loggingObserver == null)
         this.loggingObserver = new java.util.HashSet<LoggingObserver>();
      if (!this.loggingObserver.contains(newLoggingObserver))
         this.loggingObserver.add(newLoggingObserver);
   }
   
   /** @pdGenerated default remove
     * @param oldLoggingObserver */
   public void removeLoggingObserver(LoggingObserver oldLoggingObserver) {
      if (oldLoggingObserver == null)
         return;
      if (this.loggingObserver != null)
         if (this.loggingObserver.contains(oldLoggingObserver))
            this.loggingObserver.remove(oldLoggingObserver);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllLoggingObserver() {
      if (loggingObserver != null)
         loggingObserver.clear();
   }

}