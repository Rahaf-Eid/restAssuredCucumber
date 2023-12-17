package pojo;

import lombok.Data;

@Data
public class products {

    private int id;
    private String name;
    private int cost;
    private int quantity;
    private int locationId;
    private int familyId;

}
