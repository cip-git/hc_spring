package com.seeit.holycode.model.locations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHours {

    private Map<String, List<Schedule>> days;

    @JsonProperty("closed_on_holidays")
    private boolean closedOnHolidays;

    @JsonProperty("open_by_arrangement")
    private boolean openByArrangement;
}
