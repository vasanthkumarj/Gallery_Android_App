package vk.com.gallery_android_app.provider;

public class GalleryDataProvider {

    private Integer imageId;

    public GalleryDataProvider(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(final Integer imageId) {
        this.imageId = imageId;
    }
}
