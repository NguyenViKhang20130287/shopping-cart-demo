package Entity;

public class product {

    private int id;
    private String title;
    private String image;
    private int price;
    private int discount;
    private int quantity;
    private String company;

    private int quantityInCart;

    public product() {
    }

    public product(int id, String title, String image, int price, int discount, int quantity, String company) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.company = company;
    }

    public product(int id, String title, String image, int price, int discount, int quantity, String company, int quantityInCart) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.company = company;
        this.quantityInCart = quantityInCart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getQuantityInCart() {
        return quantityInCart;
    }

    public void setQuantityInCart(int quantityInCart) {
        this.quantityInCart = quantityInCart;
    }

    @Override
    public String toString() {
        return "product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", quantity=" + quantity +
                ", company='" + company + '\'' +
                ", quantityInCart=" + quantityInCart +
                '}';
    }
}
