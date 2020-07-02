/*
 * Wojciech Grzyb
 * WCY19KB2S4
 */
package bin_packing.algorithms;

import bin_packing.Bin;
import bin_packing.Item;

import java.util.List;

/* Klasa reprezentująca algorytm First Fit rozwiązujący problem pakowania */
public class FirstFit extends Algorithm {

    public FirstFit(List<Item> items, double c) {
        super(items, c);
        printMemoryUsage("po utworzeniu obiektu");
        printParams();
    }

    @Override
    public int compute() {
        bins.add(new Bin(capacity)); // Dodanie pierwszego pudełka
        for(Item item: items) {
            if(item.getSize()<capacity) {
                boolean isItemAdded = false; // Czy przedmiot został dodany do pudełka?
                for(Bin bin: bins){
                    if(item.getSize() <= bin.getFreeSpace()){
                        bin.addItem(item);
                        isItemAdded = true;
                        break;
                    }
                }
                // Jeśli przedmiot nie zmieścił się w żadnym z obecnych pudełek
                if(!isItemAdded){
                    Bin bin = new Bin(capacity);
                    bins.add(bin);
                    bin.addItem(item);
                }
            }
        }

        printBinsState();
        printMemoryUsage("tuż przed zakończeniem algorytmu");
        return bins.size();
    }
}
