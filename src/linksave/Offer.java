package linksave;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Offer{
	String offerId;
	String quantity;
	List<String> skuList = new ArrayList<String>();
	int counter = 0;
	
	public Offer(String[] list,String quantity) {
		this.quantity = quantity;
		for(int i=0;i<list.length;i++)
			skuList.add(list[i]);
		System.out.println("SkuList:"+skuList);
	}
	public void setOfferId(int count) {
		this.counter = count;
		offerId = Integer.toString(counter);
	}
	public String getOfferId() {
		return offerId;
	}
	public String getQuantity() {
		return quantity;
	}
	public String offerDetails() {
		StringBuilder str = new StringBuilder();
		str.append("id=");
		str.append(offerId);
		str.append(" quantity=");
		str.append("5");
		return str.toString();
	}
	
	public List<String> skuDetails() {
		return skuList;
	}
	
	public void print() {
		for(String sku : skuList) {
			System.out.print(sku+"\t");
		}
	}
}

