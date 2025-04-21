/***********************************************************************
 * Module:  RevenueContext.java
 * Author:  eyaou
 * Purpose: Defines the Class RevenueContext
 ***********************************************************************/

import java.util.*;

/** @pdOid 769154c2-e1e3-4a45-b1f0-ef92cecf73ad */
public class RevenueContext {
   /** @pdOid 08908cbf-12f9-412a-ba5a-c24fb0427de1 */
   private RevenueStrategy strategy;
   
   /** @pdRoleInfo migr=no name=RevenueStrategy assc=association14 mult=0..1 type=Aggregation */
   public RevenueStrategy revenueStrategy;
   
   /** @param strategy
    * @pdOid 3fc6e5b4-13da-4a8c-a054-67167c7912f5 */
   public void setStrategy(RevenueStrategy strategy) {
      // TODO: implement
   }
   
   /** @pdOid c7aa3cae-924c-4e3c-8056-0752310d235a */
   public int calculerRevenue() {
      // TODO: implement
      return 0;
   }

}