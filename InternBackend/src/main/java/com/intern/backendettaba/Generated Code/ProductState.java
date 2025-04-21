/***********************************************************************
 * Module:  ProductState.java
 * Author:  eyaou
 * Purpose: Defines the Interface ProductState
 ***********************************************************************/

import java.util.*;

/** @pdOid c5cee706-cecb-4b3b-a088-ea077efe6753 */
public interface ProductState {
   /** @pdOid 18ed5ddd-b87c-4bac-bf00-c45bcf485851 */
   Boolean canUpdatate();
   /** @pdOid 8519ade0-b3cc-4aa6-b3ce-a0c7907d354f */
   Boolean canDelete();
   /** @pdOid ff445a64-3823-41df-b899-32f4dbd99653 */
   Etat getEtatEnum();

}