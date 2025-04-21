/***********************************************************************
 * Module:  Admin.java
 * Author:  eyaou
 * Purpose: Defines the Class Admin
 ***********************************************************************/

import java.util.*;

/** @pdOid ce8fcaf5-11df-47ca-9c1a-99f1bbe3b36d */
public class Admin {
   /** @pdOid 16148be6-2154-48a7-b9ff-39793b229b36 */
   private int idAdmin;
   /** @pdOid eaf0bd39-3faa-4c70-a7c6-a19b0e1aadb8 */
   private String firstName;
   /** @pdOid 35b8f6d8-3768-4ce8-95ec-e76f3b80c8a4 */
   private String lastName;
   /** @pdOid 56834f6e-9edf-417b-8a57-3287f38dc85e */
   private Date dob;
   /** @pdOid 78aab12e-3e83-43c6-bbc3-e9acd6700fae */
   private Date createdAt;
   /** @pdOid ffa378f9-26f1-4343-b010-87fedb7bc6de */
   private String email;
   /** @pdOid 2ec9f31a-0587-4086-9641-bc5ff621f954 */
   private String password;
   /** @pdOid 30d516c0-e966-44b8-8831-3aaa7b5affbe */
   private Boolean isEnabled;
   /** @pdOid 4d2bfde3-9e1c-4616-8f06-1e45ba5af70f */
   private String phoneNumber;
   
   /** @pdRoleInfo migr=no name=Product assc=gerer coll=java.util.Collection impl=java.util.HashSet mult=1..* */
   public java.util.Collection<Product> product;
   /** @pdRoleInfo migr=no name=RevenueContext assc=gere mult=1 */
   public RevenueContext revenueContext;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Product> getProduct() {
      if (product == null)
         product = new java.util.HashSet<Product>();
      return product;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorProduct() {
      if (product == null)
         product = new java.util.HashSet<Product>();
      return product.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newProduct */
   public void setProduct(java.util.Collection<Product> newProduct) {
      removeAllProduct();
      for (java.util.Iterator iter = newProduct.iterator(); iter.hasNext();)
         addProduct((Product)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newProduct */
   public void addProduct(Product newProduct) {
      if (newProduct == null)
         return;
      if (this.product == null)
         this.product = new java.util.HashSet<Product>();
      if (!this.product.contains(newProduct))
         this.product.add(newProduct);
   }
   
   /** @pdGenerated default remove
     * @param oldProduct */
   public void removeProduct(Product oldProduct) {
      if (oldProduct == null)
         return;
      if (this.product != null)
         if (this.product.contains(oldProduct))
            this.product.remove(oldProduct);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllProduct() {
      if (product != null)
         product.clear();
   }

}