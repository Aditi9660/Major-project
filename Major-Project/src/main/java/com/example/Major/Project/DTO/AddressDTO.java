package com.example.Major.Project.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "mobileNo cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits")
    private String mobileNo;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "postalCode cannot be blank")
    @Min(value = 100000, message = "Postal code must be at least 6 digits")
    @Max(value = 999999, message = "Postal code must be at most 6 digits")
    private int postalCode;

    @NotBlank(message = "state cannot be blank")
    private String state;

    @NotBlank(message = "city cannot be blank")
    private String city;
    private boolean senderAddress;
    private boolean receiverAddress;


}
