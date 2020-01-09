package com.example.tacocloud.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Order {

    private Long id;

    private Date placedAt;

    @NotBlank(message="{validation.name_not_empty}")
    @Size(max = 50)
    private String name;

    @NotBlank(message="Street is required")
    @Size(max = 50)
    private String street;

    @NotBlank(message="City is required")
    @Size(max = 50)
    private String city;

    @NotBlank(message="State is required")
    @Size(max = 2)
    private String state;

    @NotBlank(message="Zip code is required")
    @Size(max = 10)
    private String zip;

    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message="Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    @NotEmpty
    private final List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco){
        tacos.add(taco);
    }
}
