import java.util.ArrayList;
import java.util.List;

class ProductOfNumbers {
    // Stores the cumulative prefix products
    private List<Integer> prefixProducts;

    public ProductOfNumbers() {
        prefixProducts = new ArrayList<>();
        // Base case: cumulative product before any elements is 1
        prefixProducts.add(1);
    }
    
    public void add(int num) {
        if (num == 0) {
            // Reset the stream because any product spanning across 0 will be 0
            prefixProducts = new ArrayList<>();
            prefixProducts.add(1);
        } else {
            // Multiply the last cumulative product by the new number
            int lastProduct = prefixProducts.get(prefixProducts.size() - 1);
            prefixProducts.add(lastProduct * num);
        }
    }
    
    public int getProduct(int k) {
        int size = prefixProducts.size();
        // If k is greater than or equal to size, it means a 0 was reset 
        // within the last k elements, making the product 0.
        if (k >= size) {
            return 0;
        }
        
        // Product of last k elements = (Total Product) / (Product before the last k elements)
        return prefixProducts.get(size - 1) / prefixProducts.get(size - 1 - k);
    }
}
