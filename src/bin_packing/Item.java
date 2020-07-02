/*
 * Wojciech Grzyb
 * WCY19KB2S4
 */
package bin_packing;

/* Klasa reprezentująca pakowany przedmiot */
public class Item {
    private final double size; // Wielkość przedmiotu

    public Item(double size){
        this.size = size;
    }

    /* Zwraca wielkość przedmiotu */
    public double getSize(){
        return this.size;
    }
}
