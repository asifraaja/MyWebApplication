package linksave;

import java.util.List;

public class Type8 extends Type{
	String type;
	String offerPercent;
	Offer offer;
	final int offerCount = 1;
	String quantity;
	String buyA;
	
	/*@param(inputs) -- INPUT recieved
	 * 
	 * INPUT format --> Type8;BuyItemA;OfferPerc;MdsFamId1,MdsFamId2,MdsFamIdN;Quantity
	 * */
	public Type8(String[] inputs) {
		type = inputs[0];
		buyA = inputs[1];
		offerPercent = inputs[2];
		StringBuilder input = new StringBuilder();
		for(int i=0;i<inputs[3].length();i++) {
			char a = inputs[3].charAt(i);
			if(a != '/' && a!=' ')
				input.append(a);
		}
		System.out.println("Input.toString():"+input.toString());
		inputs[3] = input.toString();
		String[] list = inputs[3].split(",");
		quantity = "1";
		offer = new Offer(list,quantity);
		offer.setOfferId(offerCount);
	}
	
	public void setOffer(String[] skus) {
		offer = new Offer(skus,quantity);
	}
	
	public void setQuantity(String quantity) {
		this.quantity = "1";
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setOfferPercent(String percent) {
		this.offerPercent = percent;
	}
	
	public void setBuyA(String itemName) {
		this.buyA = itemName;
	}
	
	public String getOfferType() {		// returns type8
		return "8";
	}
	
	public String getOfferName() {		// return product has x% off
		return offerPercent+" % off";
	}
	
	public String getOfferGroup() {		// returns details of offer
		return offer.offerDetails();
	}
	
	public List<String> getOfferSkuList(){	// returns list of fam_ids
		return offer.skuDetails();
	}
	
	public int getOfferCount() {			// return offer count which is usually 1
		return offerCount;
	}
	
	public String getOfferId() {			// returns offerId like 1 mostly bcoz only one offer
		return offer.getOfferId();
	}
	
	public String getQuantity() {		// returns quantity to be purchased
		return offer.getQuantity();
	}
	public String getTypeName() {	
		return "Offer Type 8";
	}
	public String getTypeNumber() {
		return "1";
	}
	public String getDiscountType() {
		return "percentOff";
	}
	public String getAdjuster() {	// adjuster = offerPercentage
		float adjuster = Float.parseFloat(getOfferPercentage());
		return Float.toString(adjuster);
	}
	public String getOfferPercentage() {
		return offerPercent;
	}
	
	public void print() {
		System.out.println(type + offerPercent);
		offer.print();
	}
	
	public Type8(String lstype,String price,String skus,String qty) {
		setOfferType(lstype);
		setBuyA("A");
		setOfferPercent(price);
		setQuantity(qty);
		System.out.println("Skus "+skus);
		String[] list = skus.split(",");
		offer = new Offer(list,quantity);
		offer.setOfferId(offerCount);
	}
}
