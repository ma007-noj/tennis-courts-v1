package com.tenniscourts.guests;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GuestMapper {
    GuestDTO map(Guest source);

    /*
    * Maps for two directions
    * */
    @InheritInverseConfiguration
    Guest map(GuestDTO source);
}
