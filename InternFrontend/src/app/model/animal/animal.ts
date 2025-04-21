import { FileHandle } from '../file/file';

export interface Animal {
  id?: number;
  name: string;
  description: string;
  age: number;
  price: number;
  animalType: string; // 'COW' ou 'CHICKEN' au lieu de 'type'
  creationDate?: Date;
  boughtDate?: Date;
  animalImages: FileHandle[];
  eggsPerWeek?: number; // Spécifique aux poules
  milkPerDay?: number; // Spécifique aux vaches
}
