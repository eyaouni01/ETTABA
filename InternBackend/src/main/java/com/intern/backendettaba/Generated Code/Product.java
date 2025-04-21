/***********************************************************************
 * Module:  Product.java
 * Author:  eyaou
 * Purpose: Defines the Class Product
 ***********************************************************************/

import java.util.*;

/** @pdOid d64fa3b1-8881-4d8e-83a8-1e521c95dfd5 */
public class Product {
   /** @pdOid 2cbbae61-afa7-450f-87b8-2e16127299e1 */
   private int idProduct;
   /** @pdOid 2fb12821-cee6-48d0-a319-637c032a003c */
   private String name;
   /** @pdOid 92f3a931-8cbc-4ae1-921b-3f6d8b1f6e60 */
   private Etat etat;
   /** @pdOid 8199f6d5-92ea-4a92-8b62-e6c1bff14f0a */
   private Float boughtPrice;
   /** @pdOid 378d2fe5-5a6d-48b5-9f79-275ead47b02e */
   private Float soldePrice;
   /** @pdOid 97ab0936-7fea-4e30-952c-a84318d2b58c */
   private Float weight;
   /** @pdOid 6edfa839-ad8b-4fbf-a21e-a37bc3e8cbf2 */
   private Date creationDate;
   /** @pdOid d4774a75-13cd-4097-8ab7-96863d2fa6a6 */
   private Date boughtDate;
   /** @pdOid f3789576-1baa-441e-81d9-b1e4e73724d5 */
   private ProductState currentState;
   
   /** @pdRoleInfo migr=no name=ProductState assc=utliser coll=java.util.Collection impl=java.util.HashSet mult=0..* type=Aggregation */
   public java.util.Collection<ProductState> productState;
   
   /** @pdOid ef0b5fb1-78ad-435d-944d-c8142f4ac0d8 */
   public Etat getCurrentState() {
      // TODO: implement
      return null;
   }
   
   /** @pdOid 9f74e593-99ee-488e-9234-0906786117a9 */
   public void setCurrentStateFromEnum() {
      // TODO: implement
   }
   
   /** @pdOid 535fa676-1dfe-4fc8-8887-c7c7a2619128 */
   public int createEttaba() {
      // TODO: implement
      return 0;
   }
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<ProductState> getProductState() {
      if (productState == null)
         productState = new java.util.HashSet<ProductState>();
      return productState;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorProductState() {
      if (productState == null)
         productState = new java.util.HashSet<ProductState>();
      return productState.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newProductState */
   public void setProductState(java.util.Collection<ProductState> newProductState) {
      removeAllProductState();
      for (java.util.Iterator iter = newProductState.iterator(); iter.hasNext();)
         addProductState((ProductState)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newProductState */
   public void addProductState(ProductState newProductState) {
      if (newProductState == null)
         return;
      if (this.productState == null)
         this.productState = new java.util.HashSet<ProductState>();
      if (!this.productState.contains(newProductState))
         this.productState.add(newProductState);
   }
   
   /** @pdGenerated default remove
     * @param oldProductState */
   public void removeProductState(ProductState oldProductState) {
      if (oldProductState == null)
         return;
      if (this.productState != null)
         if (this.productState.contains(oldProductState))
            this.productState.remove(oldProductState);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllProductState() {
      if (productState != null)
         productState.clear();
   }

}