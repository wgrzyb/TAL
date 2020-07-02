/*
 * Wojciech Grzyb
 * WCY19KB2S4
 */
package bin_packing;

import bin_packing.algorithms.BruteForce;
import bin_packing.algorithms.FirstFit;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BinPacking {
    /* Funkcja do generowania pakowanych przedmiotów
    *  n - liczba przedmiotów do wygenerowania
    *  weight_min - minimalna wielkość generowanych przedmiotu
    *  weight_max - maksymalna wielkość generowanych przedmiotu
    *  */
    public static List<Item> generateItems(int n, double weight_min, double weight_max){
        /* Weryfikacja podanych parametrów */
        if(n<=0 || weight_min > weight_max) {
            throw new IllegalArgumentException();
        }

        /* Wygenerowanie przedmiotów */
        List<Item> items = new ArrayList<>();
        Random r = new Random();
        for(int i=0; i < n; i++){
            double size = weight_min + (weight_max - weight_min) * r.nextDouble();
            items.add(new Item(size));
        }
        return items;
    }

    /* Funkcja do generowania pakowanych przedmiotów
     *  n - liczba przedmiotów do wygenerowania
     *  weight_min - minimalna wielkość generowanych przedmiotu
     *  weight_max - maksymalna wielkość generowanych przedmiotu
     *  */
    public static List<Item> generateItemsOfIntegerSize(int n, int weight_min, int weight_max){
        /* Weryfikacja podanych parametrów */
        if(n<=0 || weight_min > weight_max) {
            throw new IllegalArgumentException();
        }

        /* Wygenerowanie przedmiotów */
        List<Item> items = new ArrayList<>();
        Random r = new Random();
        for(int i=0; i < n; i++){
            double size = r.nextInt((weight_max - weight_min) + 1) + weight_min;
            items.add(new Item(size));
        }
        return items;
    }

    /* Funkcja do załadowania pakowanych przedmiotów z pliku tekstowego
     *  filename - nazwa pliku, z którego będą ładowane dane
     *  separator - regex określający symbole między kolejnymi wielkościami przedmiotów
     *  */
    public static List<Item> loadItemsFromFile(String fileName, String separator) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
            List<Item> items = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(separator);
                for (String value : values) {
                    items.add(new Item(Double.parseDouble(value)));
                }
            }
            br.close();
            return items;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    /* Funkcja symulująca rozwiązanie problemu pakowania za pomocą zarówno algorytmu siłowego oraz algorytmu First Fit
     *  items - przedmioty do spakowania
     *  c - pojemność pudełek
     *  */
    public static void simulate(List<Item> items, double c){
        /* Weryfikacja podanych parametrów */
        if(items.size() <= 0 || c < Collections.max(items, new ItemComparator()).getSize()) {
            throw new IllegalArgumentException();
        }

        /* Przeprowadzenie symulacji */
        double startTime, stopTime, duration;
        /* Improved Brute Force */
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Symulacja rozwiązania problemu pakowania przy użyciu algorytmu siłowego:");
        BruteForce bruteForce = new BruteForce(items, c);
        startTime = System.nanoTime()/1e9d; // Zmierzenie czasu przed wywołaniem algorytmu
        int n_bins_opt = bruteForce.compute();
        stopTime = System.nanoTime()/1e9d; // Zmierzenie czasu po wykonaniu się algorytmu
        duration = stopTime - startTime; // Obliczenie czasu trwania algorytmu
        System.out.println("Czas trwania algorytmu: "+ duration + "s");
        System.out.println("Liczba wymaganych pudełek (brute force): "+n_bins_opt);
        /* First Fit */
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Symulacja rozwiązania problemu pakowania przy użyciu algorytmu First Fit:");
        FirstFit firstFit = new FirstFit(items, c);
        startTime = System.nanoTime()/1e9d; // Zmierzenie czasu przed wywołaniem algorytmu
        int n_bins_est = firstFit.compute();
        stopTime = System.nanoTime()/1e9d; // Zmierzenie czasu po wykonaniu się algorytmu
        duration = stopTime - startTime; // Obliczenie czasu trwania algorytmu
        System.out.println("Czas trwania algorytmu: "+ duration + "s");
        System.out.println("Liczba wymaganych pudełek (First Fit): "+n_bins_est);
        System.out.println("Rozwiązanie optymalne: "+n_bins_opt);
        double error = Math.abs((double)(n_bins_est-n_bins_opt)/n_bins_opt*100); // Wyznaczenie błędu szacowania
        System.out.println("Błąd: "+error+"%");
    }

    /* Funkcja symulująca rozwiązanie problemu pakowania za pomocą algorytmu First Fit
     *  n - liczba przedmiotów do wygenerowania
     *  weight_min - minimalna wielkość generowanych przedmiotu
     *  weight_max - maksymalna wielkość generowanych przedmiotu
     *  c - pojemność pudełek
     *  */
    public static void simulateFirstFit(int n, double weight_min, double weight_max, double c){
        /* Weryfikacja podanych parametrów */
        if(n<=0 || weight_min > weight_max || c < weight_max) {
            throw new IllegalArgumentException();
        }

        /* Przeprowadzenie symulacji */
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Symulacja rozwiązania problemu pakowania przy użyciu algorytmu First Fit:");
        List<Item> items = generateItems(n, weight_min, weight_max);
        FirstFit firstFit = new FirstFit(items, c);
        double startTime = System.nanoTime()/1e9d; // Zmierzenie czasu przed wywołaniem algorytmu
        int n_bins = firstFit.compute();
        double stopTime = System.nanoTime()/1e9d; // Zmierzenie czasu po wykonaniu się algorytmu
        double duration = stopTime - startTime; // Obliczenie czasu trwania algorytmu
        System.out.println("Czas trwania algorytmu: "+ duration + "s");
        System.out.println("Liczba wymaganych pudełek (First Fit): "+n_bins);
    }

    /* Funkcja symulująca rozwiązanie problemu pakowania za pomocą algorytmu First Fit
     *  items - przedmioty do spakowania
     *  c - pojemność pudełek
     *  */
    public static void simulateFirstFit(List<Item> items, double c){
        /* Weryfikacja podanych parametrów */
        if(items.size() <= 0 || c < Collections.max(items, new ItemComparator()).getSize()) {
            throw new IllegalArgumentException();
        }

        /* Przeprowadzenie symulacji */
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Symulacja rozwiązania problemu pakowania przy użyciu algorytmu First Fit:");
        FirstFit firstFit = new FirstFit(items, c);
        double startTime = System.nanoTime()/1e9d; // Zmierzenie czasu przed wywołaniem algorytmu
        int n_bins = firstFit.compute();
        double stopTime = System.nanoTime()/1e9d; // Zmierzenie czasu po wykonaniu się algorytmu
        double duration = stopTime - startTime; // Obliczenie czasu trwania algorytmu
        System.out.println("Czas trwania algorytmu: "+ duration + "s");
        System.out.println("Liczba wymaganych pudełek (First Fit): "+n_bins);
    }

    /* Funkcja symulująca rozwiązanie problemu pakowania za pomocą algorytmu siłowego
     *  n - liczba przedmiotów do wygenerowania
     *  weight_min - minimalna wielkość generowanych przedmiotu
     *  weight_max - maksymalna wielkość generowanych przedmiotu
     *  c - pojemność pudełek
     *  */
    public static void simulateBruteForce(int n, double weight_min, double weight_max, double c){
        /* Weryfikacja podanych parametrów */
        if(n<=0 || weight_min > weight_max || c < weight_max) {
            throw new IllegalArgumentException();
        }

        /* Przeprowadzenie symulacji */
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Symulacja rozwiązania problemu pakowania przy użyciu algorytmu siłowego:");
        List<Item> items = generateItems(n, weight_min, weight_max);
        BruteForce bruteForce = new BruteForce(items, c);
        double startTime = System.nanoTime()/1e9d; // Zmierzenie czasu przed wywołaniem algorytmu
        int n_bins = bruteForce.compute();
        double stopTime = System.nanoTime()/1e9d; // Zmierzenie czasu po wykonaniu się algorytmu
        double duration = stopTime - startTime; // Obliczenie czasu trwania algorytmu
        System.out.println("Czas trwania algorytmu: "+ duration + "s");
        System.out.println("Liczba wymaganych pudełek (brute force): "+n_bins);
    }

    /* Funkcja symulująca rozwiązanie problemu pakowania za pomocą algorytmu siłowego
     *  items - przedmioty do spakowania
     *  c - pojemność pudełek
     *  */
    public static void simulateBruteForce(List<Item> items, double c){
        /* Weryfikacja podanych parametrów */
        if(items.size() <= 0 || c < Collections.max(items, new ItemComparator()).getSize()) {
            throw new IllegalArgumentException();
        }

        /* Przeprowadzenie symulacji */
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Symulacja rozwiązania problemu pakowania przy użyciu algorytmu siłowego:");
        BruteForce bruteForce = new BruteForce(items, c);
        double startTime = System.nanoTime()/1e9d; // Zmierzenie czasu przed wywołaniem algorytmu
        int n_bins = bruteForce.compute();
        double stopTime = System.nanoTime()/1e9d; // Zmierzenie czasu po wykonaniu się algorytmu
        double duration = stopTime - startTime; // Obliczenie czasu trwania algorytmu
        System.out.println("Czas trwania algorytmu: "+ duration + "s");
        System.out.println("Liczba wymaganych pudełek (brute force): "+n_bins);
    }

    /* Funkcja symulująca rozwiązanie problemu pakowania za pomocą ulepszonego algorytmu siłowego
     *  n - liczba przedmiotów do wygenerowania
     *  weight_min - minimalna wielkość generowanych przedmiotu
     *  weight_max - maksymalna wielkość generowanych przedmiotu
     *  c - pojemność pudełek
     *  */
    public static void simulateBruteForceImproved(int n, double weight_min, double weight_max, double c){
        /* Weryfikacja podanych parametrów */
        if(n<=0 || weight_min > weight_max || c < weight_max) {
            throw new IllegalArgumentException();
        }

        /* Przeprowadzenie symulacji */
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Symulacja rozwiązania problemu pakowania przy użyciu ulepszonego algorytmu siłowego:");
        List<Item> items = generateItems(n, weight_min, weight_max);
        BruteForce bruteForce = new BruteForce(items, c);
        double startTime = System.nanoTime()/1e9d; // Zmierzenie czasu przed wywołaniem algorytmu
        int n_bins = bruteForce.computeImproved();
        double stopTime = System.nanoTime()/1e9d; // Zmierzenie czasu po wykonaniu się algorytmu
        double duration = stopTime - startTime; // Obliczenie czasu trwania algorytmu
        System.out.println("Czas trwania algorytmu: "+ duration + "s");
        System.out.println("Liczba wymaganych pudełek (ulepszony brute force): "+n_bins);
    }

    /* Funkcja symulująca rozwiązanie problemu pakowania za pomocą ulepszonego algorytmu siłowego
     *  items - przedmioty do spakowania
     *  c - pojemność pudełek
     *  */
    public static void simulateBruteForceImproved(List<Item> items, double c){
        /* Weryfikacja podanych parametrów */
        if(items.size() <= 0 || c < Collections.max(items, new ItemComparator()).getSize()) {
            throw new IllegalArgumentException();
        }

        /* Przeprowadzenie symulacji */
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Symulacja rozwiązania problemu pakowania przy użyciu ulepszonego algorytmu siłowego:");
        BruteForce bruteForce = new BruteForce(items, c);
        double startTime = System.nanoTime()/1e9d; // Zmierzenie czasu przed wywołaniem algorytmu
        int n_bins = bruteForce.computeImproved();
        double stopTime = System.nanoTime()/1e9d; // Zmierzenie czasu po wykonaniu się algorytmu
        double duration = stopTime - startTime; // Obliczenie czasu trwania algorytmu
        System.out.println("Czas trwania algorytmu: "+ duration + "s");
        System.out.println("Liczba wymaganych pudełek (ulepszony brute force): "+n_bins);
    }
}
