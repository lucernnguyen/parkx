package org.parkz.modules.media.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.parkz.modules.media.model.PreSignUrl;

@Data
@NoArgsConstructor
public class UploadWithPreSignResponse extends PreSignUrl {

    private String previewUrl;

    public UploadWithPreSignResponse(String url, String previewUrl) {
        super(url);
        this.previewUrl = previewUrl;
    }
}
