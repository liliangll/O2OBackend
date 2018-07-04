package cn.com.efuture.o2o.backend.mybatis.entity;

import org.springframework.data.annotation.Id;

public class ImageFile {

    @Id
    private String id;
    private String itemCode;
    private String size;
    private byte[] image;
    private String url;

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public ImageFile(String itemCode, String size, byte[] image) {
        this.itemCode = itemCode;
        this.size = size;
        this.image = image;
    }
}
