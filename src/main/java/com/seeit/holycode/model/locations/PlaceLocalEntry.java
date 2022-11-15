package com.seeit.holycode.model.locations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceLocalEntry {

    @JsonProperty("displayed_what")
    private String displayedWhat;

    @JsonProperty("displayed_where")
    private String displayedWhere;

    @JsonProperty("opening_hours")
    private OpeningHours openingHours;
}
