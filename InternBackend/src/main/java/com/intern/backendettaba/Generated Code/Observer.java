/***********************************************************************
 * Module:  Observer.java
 * Author:  eyaou
 * Purpose: Defines the Interface Observer
 ***********************************************************************/

import java.util.*;

/** @pdOid 0bf123cc-4744-4bc5-99dd-a9e892f68bcf */
public interface Observer {
   /** @param eventType 
    * @param data
    * @pdOid df328f99-2e9b-4b32-8aa4-3f0548a6d2f4 */
   void update(String eventType, java.lang.Object data);

}