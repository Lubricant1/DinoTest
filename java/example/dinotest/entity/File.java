package example.dinotest.entity;

public class File {

    private String name;
    private String targetUri;
    private String fileMime;
    private String fileSize;
    private String base;

    public File() {
    }

    public File(String name, String targetUri, String fileMime, String fileSize, String base) {
        this.name = name;
        this.targetUri = targetUri;
        this.fileMime = fileMime;
        this.fileSize = fileSize;
        this.base = base;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetUri() {
        return targetUri;
    }

    public void setTargetUri(String targetUri) {
        this.targetUri = targetUri;
    }

    public String getFileMime() {
        return fileMime;
    }

    public void setFileMime(String fileMime) {
        this.fileMime = fileMime;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
