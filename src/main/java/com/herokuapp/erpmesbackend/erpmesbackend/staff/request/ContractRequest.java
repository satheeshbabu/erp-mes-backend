package com.herokuapp.erpmesbackend.erpmesbackend.staff.request;

import com.herokuapp.erpmesbackend.erpmesbackend.staff.model.Contract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractRequest {

    @NotNull
    private String accountNumber;

    @NotNull
    private int daysOffPerYear;

    @NotNull
    private double salary;

    Contract extractContract() {
        return new Contract(accountNumber, daysOffPerYear, salary);
    }

}
