package model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements BaseEntity<Integer>{

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

}
