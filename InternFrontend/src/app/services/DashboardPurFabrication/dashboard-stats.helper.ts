
import { Injectable } from '@angular/core';
import { Product } from '../../model/product/product';
import { Event } from '../../model/event/event';
import { Animal } from '../../model/animal/animal';

type EntityWithDate =
  | { boughtDate: Date | string }
  | { date: Date | string }
  | { purchaseDate: Date | string };



@Injectable({ providedIn: 'root' })
export class DashboardStatsHelper {

  constructor() {}

  // Méthode générique pour calculer les sommes mensuelles
  calculateMonthlySums(
     items: (Product | Event | Animal)[],
     priceField: 'price' | 'boughtPrice' | 'soldPrice',
     dateType: 'creation' | 'bought' = 'bought'
   ): number[] {
      console.log('Items reçus:', items);
    const monthlySums = new Array(12).fill(0);

    items.forEach(item => {
      // 1. Extraction du prix
           const price = this.extractPrice(item, priceField);
           if (price <= 0) return;

           // 2. Extraction de la date
           const date = this.extractDate(item, dateType);
           if (!date) return;

           // 3. Cumul par mois
           monthlySums[date.getMonth()] += price;
         });

         return monthlySums;
  }

   private extractPrice(item: Product | Event | Animal, field: string): number {
     return Number(item[field as keyof typeof item]) || 0;
   }

   private extractDate(item: Product | Event | Animal, dateType: string): Date | null {
     const dateField = dateType === 'creation' ? 'creationDate' : 'boughtDate';
     const dateValue = item[dateField as keyof typeof item];

     if (!dateValue) return null;
     return dateValue instanceof Date ? dateValue : new Date(dateValue);
   }
}
