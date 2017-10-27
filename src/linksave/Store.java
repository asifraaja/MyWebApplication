package linksave;

import java.util.ArrayList;
import java.util.List;

public class Store{
	String storeId;
	List<String> stores;
	
	public Store() {
		stores = new ArrayList<String>();
	}
	
	public void setStoreId(String id) {
		this.storeId = id;
	}
	
	public void setStoreNumber(String input) {
		String[] inputs = input.split(",");
		for(int i=0;i<inputs.length;i++)
			stores.add(inputs[i]);
	}
	
	public List<String> getStores(){
		return stores;
	}
	
	public String getStoreId() {
		return storeId;
	}
}
