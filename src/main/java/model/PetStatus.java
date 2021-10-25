package model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum PetStatus {
    AVAILABLE("available"),PENDING("pending"),SOLD("sold");

    @SerializedName("status")
    private final String status;

    public static Optional<PetStatus> getPetStatus(String status){
        return Arrays.stream(PetStatus.values())
                .filter(enumValue->enumValue.getStatus().equals(status))
                .findAny();
    }

}