/*
 * Wojciech Grzyb
 * WCY19KB2S4
 */
package bin_packing;

import java.util.ArrayList;
import java.util.List;

/* Klasa reprezentująca pudełko */
public class Bin {
    static int n_bins = 0;
    private final int id; // Identyfikator pudełka
    List<Item> items; // Lista przedmiotów w pudełku
    private double freeSpace; // Wolna przestrzeń w pudełku

    public Bin(double capacity) {
        this.id = ++n_bins;
        this.items = new ArrayList<>();
        this.freeSpace = capacity;
    }

    /* Dodanie przedmiotu do pudełka */
    public void addItem(Item item) {
        if(item.getSize() > freeSpace) {
            throw new IllegalArgumentException();
        }

        items.add(item);
        freeSpace -= item.getSize();
    }

    /* Usunięcie przedmiotu z pudełka */
    public void removeItem(Item item) {
        if(!items.contains(item)) {
            throw new IllegalArgumentException();
        }

        items.remove(item);
        freeSpace += item.getSize();
    }

    /* Zwraca wolną przestrzeń w pudełku */
    public double getFreeSpace(){
        return freeSpace;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Pudełko (ID=" + id + "): ");
        for(Item item: items) {
            str.append(item.getSize()).append(" ");
        }
        return str.toString();
    }
}
