package model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @SerializedName("id")
    private Integer id;

    @SerializedName("petId")
    private Integer petId;

    @SerializedName("quantity")
    private Integer quantity;

    @SerializedName("shipDate")
    private String shipDate;

    @SerializedName("Order Status")
    private OrderStatus status;

    @SerializedName("complete")
    private boolean complete = false;

}
