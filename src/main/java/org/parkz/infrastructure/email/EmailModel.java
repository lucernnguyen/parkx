package org.parkz.infrastructure.email;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EmailModel {
    private String templateName;
    private String from;
    private String[] to;
    private String subject;
    private String content;
    private boolean isHtml = false;
    private boolean hasAttachment;
    private Map<String, Object> parameterMap;
    private Map<String, Object> attachments;
}
