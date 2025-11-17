package com.sulwork.cafe.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateTrouxeRequest(@NotNull Boolean trouxe) { }
