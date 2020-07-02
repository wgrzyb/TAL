/*
 * Wojciech Grzyb
 * WCY19KB2S4
 */
package bin_packing.algorithms;

import bin_packing.Bin;
import bin_packing.Item;

import java.util.ArrayList;
import java.util.List;

/* Klasa reprezentująca algorytm rozwiązujący problem pakowania */
public abstract class Algorithm {
    List<Bin> bins;
    List<Item> items;
    double capacity;
    double startMemory;

    public Algorithm(List<Item> items, double capacity) {
        this.startMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/(1024d*1024d);
        bins = new ArrayList<>();
        this.items = items;
        this.capacity = capacity;
    }

    public abstract int compute();

    /* Wypisanie stanu pudełek */
    public void printBinsState(){
        System.out.println("---------- Stan pudełek ----------");
        for(Bin bin: bins) {
            System.out.println(bin.toString());
        }
        System.out.println("----------");
    }

    /* Wypisanie parametrów */
    public void printParams(){
        System.out.println("---------- Parametry problemu ----------");
        System.out.println("Pojemność pudełek: "+capacity);
        StringBuilder str_items = new StringBuilder("N: ");
        for(Item item: items) {
            str_items.append(item.getSize()).append(" ");
        }
        System.out.println(str_items);
    }

    /* Wypisanie używanej pamięci */
    public void printMemoryUsage(String str){
        double endMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/(1024d*1024d);
        System.out.println("Rozmiar używanej pamieci: "+startMemory+"MB (przed utworzeniem obiektu)");
        System.out.println("Rozmiar używanej pamieci: "+endMemory+"MB ("+str+")");
        System.out.println("Rozmiar zalokowanej pamięci: "+(endMemory-startMemory)+"MB");
    }
}
