package model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    @SerializedName("id")
    private Integer id;

    @SerializedName("category")
    private Category category;

    @SerializedName("name")
    private String name;

    @SerializedName("photoUrls")
    private List<String> photoUrls;

    @SerializedName("tags")
    private List<Tag> tags;

    @SerializedName("status")
    private PetStatus status;

}
