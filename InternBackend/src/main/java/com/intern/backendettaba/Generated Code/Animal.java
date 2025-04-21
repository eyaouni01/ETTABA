/***********************************************************************
 * Module:  Animal.java
 * Author:  eyaou
 * Purpose: Defines the Class Animal
 ***********************************************************************/

import java.util.*;

/** @pdOid a4b579ea-5636-41fb-b2f1-c6d558406495 */
public abstract class Animal {
   /** @pdOid 0fbe775a-7c1d-430d-aad5-ee3e04a44dd3 */
   private int idAnimal;
   /** @pdOid 67c01384-07de-431b-b376-7a72c60187ff */
   private String name;
   /** @pdOid 7f5f3369-7957-4b5b-bea7-79a119dbf527 */
   private AnimalType type;
   /** @pdOid c8c604c6-a622-4f73-938d-8bc324a2fbd9 */
   private String description;
   /** @pdOid b2404ab8-faba-4016-8bb8-7a6df3f929f4 */
   private int age;
   /** @pdOid 248a614c-64d8-45c0-9a9d-3c829cf59c85 */
   private Float price;
   /** @pdOid e203cf00-bfac-4b5b-a3d3-07fef7e37492 */
   private Date creationDate;
   /** @pdOid d12907d6-5c32-4b6f-a8a2-d062b3b3c865 */
   private Date boughtDate;
   
   /** @pdOid acce8a78-d406-429a-855e-a7c551f7a7d6 */
   public Float calculateFeedingCost() {
      // TODO: implement
      return null;
   }

}