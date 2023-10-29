package org.parkz.modules.media.model.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class BulkUploadPreSignPrivateRequest {

    @Size(min = 1, max = 128)
    private List<UploadWithPreSignRequest> fileMetadatas;
}
