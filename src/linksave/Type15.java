package linksave;

import java.util.List;

public class Type15 extends Type{
	String type;
	String buyA;
	String price;
	Offer offer;
	final int offerCount = 1;
	String quantity;
	
	/*@params(inputs) -- INPUT recieved
	 * 
	 * INPUT format --> Type15;BuyItemA;Price;mdsFam1,mdsFam2,mdsFamN;Quantity
	 * */
	public Type15(String[] inputs) {
		setOfferType(inputs[0]);
		setBuyA(inputs[1]);	
		setPrice(inputs[2]);
		StringBuilder input = new StringBuilder();
		for(int i=0;i<inputs[3].length();i++) {
			char a = inputs[3].charAt(i);
			if(a != '/' && a!=' ')
				input.append(a);
		}
		System.out.println("Input.toString():"+input.toString());
		inputs[3] = input.toString();
		String[] list = inputs[3].split(",");
		setQuantity(inputs[4]);
		offer = new Offer(list,quantity);
		offer.setOfferId(offerCount);
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public void setOfferType(String type) {
		this.type = type;
	}
	public void setBuyA(String buyA) {
		this.buyA = buyA;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public void setOffer(String[] skus) {
		offer = new Offer(skus,quantity);
	}
	
	public String getPrice() {
		return price;
	}
	public String getOfferType() {
		return "15";
	}
	public String getOfferName() {
		return "Buy "+quantity+" For £"+price;
	}
	public String getOfferGroup() {
		return offer.offerDetails();
	}
	public List<String> getOfferSkuList(){
		return offer.skuDetails();
	}
	public int getOfferCount() {
		return offerCount;
	}
	public String getOfferId() {
		return offer.getOfferId();
	}
	public String getQuantity() {
		return offer.getQuantity();
	}
	public String getTypeName() {
		return "Offer Type 15";
	}
	public String getTypeNumber() {
		return "3";
	}
	public String getDiscountType() {
		return "fixedPrice";
	}
	public String getAdjuster() {
		float adjuster = Float.parseFloat(getPrice())/Float.parseFloat(getQuantity());
		return Float.toString(adjuster);
	}
	
	public Type15(String lstype, String price,String skus, String qty) {
		setOfferType(lstype);
		setPrice(price);
		setQuantity(qty);
		String[] list = skus.split(",");
		offer = new Offer(list,quantity);
		offer.setOfferId(offerCount);
	}
}

