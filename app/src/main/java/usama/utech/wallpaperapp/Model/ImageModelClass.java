package usama.utech.wallpaperapp.Model;

public class ImageModelClass {

    String image_id, image_url, verified, category;

    public ImageModelClass() {
    }

    public ImageModelClass(String image_id, String image_url, String verified, String category) {
        this.image_id = image_id;
        this.image_url = image_url;
        this.verified = verified;
        this.category = category;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
