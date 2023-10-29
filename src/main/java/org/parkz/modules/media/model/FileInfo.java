package org.parkz.modules.media.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.parkz.modules.media.enums.AccessType;
import org.parkz.modules.media.enums.FileType;
import org.parkz.modules.media.enums.MediaErrorCode;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileInfo {

    private static final String SERVER_REWRITE_PATH;
    private static final String SERVER_ENV;
    private static final boolean PREVIEW_DEFAULT_PRE_SIGNED;
    private static final String CONTROLLER_PATH = "/api/v1/media";

    static {
        if (System.getenv("SERVER_REWRITE_PATH") != null) {
            SERVER_REWRITE_PATH = System.getenv("SERVER_REWRITE_PATH");
        } else {
            SERVER_REWRITE_PATH = "";
        }
        String env = System.getenv("SERVER_ENV");
        if (env == null || env.isEmpty()) {
            env = "dev";
        }
        if (env.toLowerCase().startsWith("prod")) {
            SERVER_ENV = "production";
        } else if (env.toLowerCase().startsWith("stg") || env.toLowerCase().startsWith("staging")) {
            SERVER_ENV = "stg";
        } else if (env.toLowerCase().startsWith("dev")) {
            SERVER_ENV = "dev";
        } else {
            SERVER_ENV = "local";
        }
        env = System.getenv("PREVIEW_DEFAULT_PRE_SIGNED");
        if (env != null) {
            PREVIEW_DEFAULT_PRE_SIGNED = "true".equals(env) || "True".equals(env);
        } else {
            PREVIEW_DEFAULT_PRE_SIGNED = false;
        }
    }

    @JsonIgnore
    private String path;
    @JsonIgnore
    private String name;
    private String extension;
    private String md5;
    private AccessType accessType;
    @JsonIgnore
    private FileType fileType;
    @JsonIgnore
    private MultipartFile content;
    @JsonIgnore
    private MediaType contentTypeObject;

    public FileInfo(String fileType, String objectName, AccessType accessType) throws InvalidException {
        this(fileType, objectName, accessType, null);
    }

    public FileInfo(FileType fileType, String objectName, AccessType accessType, String extension) {
        setFileType(fileType);
        setName(objectName);
        setAccessType(accessType);
        setExtension(extension);
    }

    public FileInfo(String fileType, String objectName, AccessType accessType, MultipartFile content) throws InvalidException {
        this(FileType.valueFrom(fileType), objectName, accessType, content);
    }

    public FileInfo(FileType fileType, String objectName, AccessType accessType, MultipartFile content) throws InvalidException {
        if (fileType == null) {
            throw new InvalidException(MediaErrorCode.MEDIA_FILE_NOT_FOUND);
        }
        setFileType(fileType);
        setName(objectName);
        setAccessType(accessType);
        setContent(content);
    }

    @JsonIgnore
    public String getFullName() {
        if (StringUtils.isBlank(this.extension))
            return this.name;
        return String.format("%s.%s", this.name, this.extension);
    }

    public void setName(String name) {
        this.name = FilenameUtils.getBaseName(name);
        setExtension(FilenameUtils.getExtension(name));
    }

    public void setPath(String path) {
        if (!path.endsWith("/"))
            path = String.format("%s/", path);
        this.path = path.replaceFirst("^/", "");
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
        setPath(fileType.getPath());
    }

    @SneakyThrows
    public void setContent(MultipartFile content) {
        if (content == null) {
            return;
        }
        this.content = content;
        this.extension = FilenameUtils.getExtension(content.getOriginalFilename());
        this.contentTypeObject = MediaType.valueOf(Objects.requireNonNull(content.getContentType()));
    }

    @JsonProperty("contentType")
    public String getContentType() {
        if (this.contentTypeObject.equals(MediaType.ALL))
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        return MediaType.toString(List.of(this.contentTypeObject));
    }

    @JsonProperty("previewUrl")
    public String getPreviewUrl() {
        if (PREVIEW_DEFAULT_PRE_SIGNED) {
            return Path.of(SERVER_REWRITE_PATH, CONTROLLER_PATH, getAccessType().getNameLowerCase(), "preview", "pre-sign", getFileType().getPath(), getFullName())
                    .toString();
        }
        return Path.of(SERVER_REWRITE_PATH, CONTROLLER_PATH, getAccessType().getNameLowerCase(), "preview", getFileType().getPath(), getFullName())
                .toString();
    }

    @JsonIgnore
    public Path getUploadPath() {
        return Path.of(SERVER_ENV, this.accessType.getNameLowerCase(), this.path, getFullName());
    }

    public static String getStoragePath(String realPath) {
        return realPath.replace(SERVER_REWRITE_PATH + CONTROLLER_PATH, "");
    }

}

