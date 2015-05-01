package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
public class OrderListWrapper {
	
	private List<Order> order;
	
	@XmlElement(name = "order")
	public List<Order> getOrder() {
		return order;
	}
	
    public void setOrder(List<Order> order) {
        this.order = order;
    }
}
