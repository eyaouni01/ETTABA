
import { Injectable } from '@angular/core';
import { Product } from '../model/product/product';
import { Event } from '../model/event/event';
import { Animal } from '../model/animal/animal';

@Injectable({ providedIn: 'root' })
export class DashboardStatsHelper {

  // Calcule les sommes mensuelles pour un tableau d'objets avec une date et un prix
  calculateMonthlySums(items: any[], priceField: string): number[] {
    const monthlySums = new Array(12).fill(0);
    const currentYear = new Date().getFullYear();

    items.forEach(item => {
      const date = new Date(item.boughtDate || item.date);
      if (date.getFullYear() === currentYear) {
        const month = date.getMonth(); // Janvier = 0
        monthlySums[month] += item[priceField];
      }
    });

    return monthlySums;
  }

  // Filtre les produits par catégorie (SEED, INPROGRESS, READY)
  filterProductsByCategory(products: Product[], category: string): Product[] {
    return products.filter(p => p.etat === category);
  }

  // Calcule le revenu total pour un tableau d'objets
  calculateTotalRevenue(items: any[], priceField: string): number {
    return items.reduce((sum, item) => sum + item[priceField], 0);
  }
}