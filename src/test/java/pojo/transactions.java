package pojo;

import lombok.Data;

@Data
public class transactions {
    private int id;
    private int cost;
    private int quantity;
    private int productId;
    private int shipment;

}
