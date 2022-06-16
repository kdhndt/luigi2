package be.vdab.luigi2.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record NieuwePizza(@NotBlank String naam, @NotNull  @PositiveOrZero BigDecimal prijs) {
}
