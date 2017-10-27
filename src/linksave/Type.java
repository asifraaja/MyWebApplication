package linksave;

import java.util.List;

abstract public class Type{
	public void setOfferType(String type) {}
	public void setPrice(String price) {}
	public void setBuyA(String buyA) {}
	public void setBuyB(String buyB) {}
	public void setQuantity(String quantity) {}
	public void setOffer(String[] skus) {}
	public void setOffer(List<String[]> offerSkus) {}

	public String getPrice() {return null;}				// Price
	public String getOfferType() {return null;}			// getOfferType
	public String getOfferName() {return null;}			// OfferName
	public String getOfferGroup() {return null;}			// OfferGroup for type 11 and type 12
	public List<String> getOfferSkuList() {return null;}	// FamIds
	public int getOfferCount() {return 1;}				// Total number of offers
	public String getOfferId() {return null;}			// offerIds as type 11 and 12 has multiple offers
	public String getQuantity() {return null;}			// Quantity to be purchased
	public String getTypeName() {return null;}			// Type Name
	public String getTypeNumber() {return null;}			// Type Number
	public String getDiscountType() {return null;}		// Discount Type
	public String getOfferPercentage() {return null;}		// Percentage for offer 8
	public String getAdjuster() {return null;}			// Adjuster
	
	public void reset(int a) {}							// reads particular offer such as offer[a]
	public void print() {}
	
}