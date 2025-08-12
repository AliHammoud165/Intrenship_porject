package com.CMS.CMS.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status of an entity, indicating whether it is active or inactive")
public enum StatusType {
    @Schema(description = "Entity is active and operational")
    ACTIVE,

    @Schema(description = "Entity is inactive and not operational")
    INACTIVE
}
