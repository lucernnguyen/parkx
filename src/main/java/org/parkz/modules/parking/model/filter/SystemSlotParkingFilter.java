package org.parkz.modules.parking.model.filter;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.filter.IFilter;

@Data
@SuperBuilder
@Jacksonized
public class SystemSlotParkingFilter implements IFilter {


}
