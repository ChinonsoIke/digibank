package proj.dtos;

import jakarta.validation.constraints.Positive;

public class DepositDto {
    public Long accountId;
    public Long userId;
    @Positive
    public double amount;
}
