/***********************************************************************
 * Module:  LoggingObserver.java
 * Author:  eyaou
 * Purpose: Defines the Class LoggingObserver
 ***********************************************************************/

import java.util.*;

/** @pdOid b7cf8c97-c6cf-4157-9afb-abb7905ed93f */
public class LoggingObserver implements Observer {
   /** @pdOid 281d92a6-ad69-470c-9fae-3419e48ba5f0 */
   private String eventType;
   /** @pdOid 6cdc6c81-89a5-4660-9d6e-4338eabd1959 */
   private java.lang.Object data;
   
   /** @param eventType 
    * @param data
    * @pdOid 2c6dcc1e-102c-43a1-b233-34338387ba2f */
   public void update(String eventType, java.lang.Object data) {
      // TODO: implement
   }

}