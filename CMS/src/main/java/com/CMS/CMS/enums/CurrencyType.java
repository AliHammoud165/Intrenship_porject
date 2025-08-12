package com.CMS.CMS.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Currency type enumeration")
public enum CurrencyType {
    @Schema(description = "United States Dollar")
    USD,

    @Schema(description = "Lebanese Lira")
    LL
}
