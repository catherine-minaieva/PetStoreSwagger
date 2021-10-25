package model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ApiResponse {

    @SerializedName("code")
    private Integer code;

    @SerializedName("type")
    private String type;

    @SerializedName("message")
    private String message;

}
