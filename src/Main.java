/*
 * Wojciech Grzyb
 * WCY19KB2S4
 */
import bin_packing.BinPacking;
import bin_packing.Item;

import java.util.List;

public class Main {

    public static void main(String[] args){
        /* Ustawienie parametrów */
        int n = 5; // Liczba przedmiotów do wygenerowania
        double weight_min = 10; // Minimalna wielkość generowanych przedmiotu
        double weight_max = 40; // Maksymalna wielkość generowanych przedmiotu
        double c = 50; // Pojemność pudełek
        String filename = "items5.txt"; // Nazwa pliku, z którego będą ładowane dane
        String separator = "\\s+"; // Regex określający symbole między kolejnymi wielkościami przedmiotów

//        BinPacking.simulateFirstFit(n, weight_min, weight_max, c);
//        BinPacking.simulateBruteForce(n, weight_min, weight_max, c);
//        BinPacking.simulateBruteForceImproved(n, weight_min, weight_max, c);

        List<Item> items = BinPacking.generateItems(n, weight_min, weight_max); // Wygenerowanie przedmiotów do spakowania
//        List<Item> items = BinPacking.generateItemsOfIntegerSize(n, (int) weight_min, (int) weight_max); // Wygenerowanie przedmiotów do spakowania o wielkościach całkowitych
//        List<Item> items = BinPacking.loadItemsFromFile(filename, separator); // Załadowanie przedmiotów do spakowania z pliku

        BinPacking.simulateFirstFit(items, c); // Symulacja algorytmu FirstFit
        BinPacking.simulateBruteForce(items, c); // Symulacja algorytmu silowego
        BinPacking.simulateBruteForceImproved(items, c); // Symulacja ulepszonego algorytmu silowego
//        BinPacking.simulate(items, c); // Symulacja do przeprowadzenia eksperymentów porównania algorytmu First Fit z algorytmem siłowym
    }
}
