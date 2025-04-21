/***********************************************************************
 * Module:  EmailNotificationObserver.java
 * Author:  eyaou
 * Purpose: Defines the Class EmailNotificationObserver
 ***********************************************************************/

import java.util.*;

/** @pdOid 5444d48e-bdd4-4183-8b0a-fa48a988a4b4 */
public class EmailNotificationObserver implements Observer {
   /** @pdOid b08d8304-a334-4a4a-8160-dbfb441f53c0 */
   private EmailService emailService;
   
   /** @param eventType 
    * @param data
    * @pdOid 2bad910c-7781-4c6b-892f-6dff0a4044dd */
   public void update(String eventType, java.lang.Object data) {
      // TODO: implement
   }
   
   /** @param data
    * @pdOid 18a4c58b-a50d-429d-b0f2-f7de898ace89 */
   public User extractUserFromData(java.lang.Object data) {
      // TODO: implement
      return null;
   }
   
   /** @param user
    * @pdOid 3f4b972b-1312-44ea-9dfc-db35d0d96b1f */
   public void sendConfirmationEmail(User user) {
      // TODO: implement
   }

}