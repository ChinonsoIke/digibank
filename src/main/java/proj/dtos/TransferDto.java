package proj.dtos;

import jakarta.validation.constraints.Positive;

public class TransferDto {
    public Long accountId;
    public Long userId;
    @Positive
    public double amount;
    public String recipient;
}
