package file;

import java.util.ArrayList;
import java.util.List;

public class FileGenerator {
	String type;
	String percent;
	String qty;
	List<String> prices;
	List<String> stores;
	List<String> skus;
	
	public FileGenerator() {
		prices = new ArrayList<>();
		stores = new ArrayList<>();
		skus = new ArrayList<>();
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public void setPrice(String price) {
		prices.add(price);
	}
	public void setStores(String store) {
		stores.add(store);
	}
	public void setSkus(String sku) {
		stores.add(sku);
	}
}
