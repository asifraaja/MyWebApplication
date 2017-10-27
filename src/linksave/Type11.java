package linksave;

import java.util.List;

public class Type11 extends Type{
	String type;
	String buyA;
	String buyB;
	String priceA;
	String priceB;
	String price;
	String quantity;
	Offer[] offers;
	int counter = -1;
	int offerCount;
	
	public Type11() {
		
	}	
	
	/*@params(inputs) -- INPUT recieved
	 * 
	 * INPUT --> Type11;BuyItemA;BuyItemB;Price,PriceA,PriceB;Offer1(mdsFams)/Offer2(mdsFams)/OfferN(mdsFams);Q
	 * */
	public Type11(String[] inputs) {
		type = inputs[0];
		buyA = inputs[1];
		buyB = inputs[2];
		setPrices(inputs[3]);
		
		quantity = inputs[5];
		
		String[] offerList = inputs[4].split("/");
		offers = new Offer[offerList.length];
		
		for(int i = 0;i<offerList.length;i++) {
			System.out.println("OfferList:"+offerList[i]);
			String[] list = offerList[i].split(",");
			offers[i] = new Offer(list,quantity);
			offers[i].setOfferId(i+1);
		}
		System.out.println(quantity+"Quantity");
		
		offerCount = offerList.length;
	}
	
	public void setPrices(String input) {
		String temp[] = input.split(",");
		price = temp[0];
		priceA = temp[1]; priceB=temp[2];
	}
	
	public void setOfferType(String type) {
		this.type = type;
	}
	public void setBuyA(String buyA) {
		this.buyA = buyA;
	}
	public void setBuyB(String buyB) {
		this.buyB = buyB;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public void setQuantity(String quantity) {
		this.quantity = 	quantity;
	}
	public void setOffer(List<String[]> offerSkus) {
		offerCount = offerSkus.size();
		offers = new Offer[offerCount];
		for(int i=0;i<offerCount;i++) {
			offers[i] = new Offer(offerSkus.get(i),quantity);
		}
	}
	
	public String getPrice() {
		return price;
	}
	public int getOfferCount() {
		System.out.println("Offer "+offerCount);
		return offerCount;
	}
	public String getOfferId() {
		return offers[counter].getOfferId();
	}
	public String getQuantity() {
		return quantity;
	}
	public String getOfferType() {
		return "11";
	}
	public String getOfferName() {
		StringBuilder ret = new StringBuilder();
		ret.append(quantity);ret.append("a + ");
		ret.append(quantity);ret.append("b for £");
		ret.append(price);ret.append(" ");
		return ret.toString();
	}
	public String getOfferGroup() {
		return offers[counter].offerDetails();
	}
	public List<String> getOfferSkuList(){
		counter++;
		return offers[counter].skuDetails();
	}
	
	public void reset(int index) {
		counter = index;
	}
	public void print() {
		System.out.println("12 "+type + buyA + buyB + price);
		for(int i=0;i<offers.length;i++) {
			offers[i].print();
		}
	}
	public String getTypeName() {
		return "Offer Type 11";
	}
	public String getTypeNumber() {
		return "2";
	}
	public String getDiscountType() {
		return "amountOff";
	}
	public String getAdjuster() {
		Float A = Float.parseFloat(priceA);
		Float B = Float.parseFloat(priceB);
		Float OfferPrice = Float.parseFloat(price);
		Float Quantity = Float.parseFloat(quantity);
		
		Float adjuster = ((A * Quantity)+(B * Quantity))-OfferPrice;
		System.out.println(A + " "+ B +" "+OfferPrice +" "+ Quantity+" "+adjuster);
		return Float.toString(adjuster);
	}
	
	public Type11(String lstype, String prices, String skus, String qty) {
		type = lstype; setPrices(prices); quantity = qty;
		String[] offerList = skus.split("/");
		offers = new Offer[offerList.length];
		
		for(int i = 0;i<offerList.length;i++) {
			System.out.println("OfferList:"+offerList[i]);
			String[] list = offerList[i].split(",");
			offers[i] = new Offer(list,quantity);
			offers[i].setOfferId(i+1);
		}
		System.out.println(quantity+"Quantity");
		
		offerCount = offerList.length;
	}
}

