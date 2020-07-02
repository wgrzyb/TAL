/*
 * Wojciech Grzyb
 * WCY19KB2S4
 */
package bin_packing.algorithms;

import bin_packing.Bin;
import bin_packing.Item;

import java.util.List;

/* Klasa reprezentująca metodę bruteforce rozwiązującą problem pakowania */
public class BruteForce extends Algorithm {
    boolean isSolutionFound;
    int solutions;

    public BruteForce(List<Item> items, double capacity) {
        super(items, capacity);
        printMemoryUsage("po utworzeniu obiektu");
        printParams();
    }

    @Override
    public int compute() {
        /* Określenie minimalnej liczby pudełek */
        double sum = items.stream().mapToDouble(Item::getSize).sum();
        int lower_bound = (int) Math.ceil(sum/capacity);
        for(int i=0; i<lower_bound; i++) {
            bins.add(new Bin(capacity));
        }

        isSolutionFound = false;
        solutions = 0;
        while(true){
            recurse(0);
            if(isSolutionFound) {
                printMemoryUsage("tuż przed zakończeniem algorytmu");
                return bins.size();
            }
            bins.add(new Bin(capacity));
        }
    }

    private void recurse(int item_idx){
        for(Bin bin: bins) {
            Item item = items.get(item_idx);
            if(item.getSize() <= bin.getFreeSpace()) {
                bin.addItem(item);
                if(item_idx == items.size()-1) {
                    isSolutionFound = true;
                    System.out.println("#Rozwiązanie "+(++solutions));
                    printBinsState();
                } else {
                    recurse(item_idx+1);
                }
                bin.removeItem(item);
            }
        }
    }

    public int computeImproved() {
        /* Określenie minimalnej liczby pudełek */
        double sum = items.stream().mapToDouble(Item::getSize).sum();
        int lower_bound = (int) Math.ceil(sum/capacity);
        for(int i=0; i<lower_bound; i++) {
            bins.add(new Bin(capacity));
        }

        isSolutionFound = false;
        while(true){
            recurseImproved(0);
            if(isSolutionFound) {
                printMemoryUsage("tuż przed zakończeniem algorytmu");
                return bins.size();
            }
            bins.add(new Bin(capacity));
        }
    }

    private void recurseImproved(int item_idx){
        for(Bin bin: bins) {
            Item item = items.get(item_idx);
            if(item.getSize() <= bin.getFreeSpace()) {
                bin.addItem(item);
                if(item_idx == items.size()-1) {
                    isSolutionFound = true;
                    printBinsState();
                    return;
                } else {
                    recurseImproved(item_idx+1);
                    if(isSolutionFound) return;
                }
                bin.removeItem(item);
            }
        }
    }
}
