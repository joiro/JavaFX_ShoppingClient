package model;

public class ProductDescription {

	public String getProductDescription(String productName) {
		switch (productName) {
		case "Apple" :
			String apple = "-Very fruity taste\n-rich color\n-harvested from European trees";
			return apple;
		case "Banana" :
			String banana = "-Golden oriental fruits\n-familiar and rich taste\n-great for baking";
			return banana;
		case "Iphone" :
			String iPhone = "-Superior high-class Smartphone\n-Reliable Quality\n-Big 5'' Screen";
			return iPhone;
		case "Butter" :
			String butter = "-Creamy delicious spread\n-Great for baking\n-traditional recipe";
			return butter;
		case "Ipad" :
			String iPad = "-High-End Tablet Computer\n-Latest Technology\n-Rich choice of applications";
			return iPad;
		case "Milk" :
			String milk = "-Natural produced\n-Milk from happy cows\n-Unpolluted taste";
			return milk;
		case "Cheddar" :
			String cheddar = "-Strong, aged taste\n-Unique, rich taste\n-British Production";
			return cheddar;
		case "Tv" :
			String tv = "-Huge HD screen\n-Crystal-Clear picture\n-Rich Surround Sound";
			return tv;
		case "Bike" :
			String bike = "-26'' Wheels\n-18 Speed\n-Approximately 14kgs";
			return bike;
		default :
			String defaultString = "no description for this product";
			return defaultString;
		}
	}
}
