/***********************************************************************
 * Module:  Subject.java
 * Author:  eyaou
 * Purpose: Defines the Class Subject
 ***********************************************************************/

import java.util.*;

/** @pdOid faa403b6-368d-4419-b330-1b3ddea64ea7 */
public abstract class Subject {
   /** @pdOid ef9dfea1-7d59-4b43-9084-c565ac057a6a */
   private Observer observers;
   
   /** @param eventType 
    * @param data
    * @pdOid 05385bee-b267-4c26-b7e0-f085abab4d92 */
   public void notifyObervers(String eventType, java.lang.Object data) {
      // TODO: implement
   }
   
   /** @param observer
    * @pdOid 7f782794-e996-4d55-9a2c-8fb00e859655 */
   public void addObserver(Observer observer) {
      // TODO: implement
   }
   
   /** @param observer
    * @pdOid 64e7d212-9ae4-42dd-af9f-f6e36d7272a0 */
   public void removeObserver(Observer observer) {
      // TODO: implement
   }

}