package DAO;

import DB.DBConnect;
import Entity.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class productDAO {

    Statement statement;
    PreparedStatement ps;
    ResultSet rs;

    // get all product
    public List<product> getAllProduct() {
        List<product> list = new ArrayList<>();
        String query = "SELECT * FROM product";
        try {
            statement = DBConnect.getInstall().get();
            if (statement != null) {
                ps = new DBConnect().getConnection().prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    product p = new product();
                    p.setId(rs.getInt(1));
                    p.setTitle(rs.getString(2));
                    p.setImage(rs.getString(3));
                    p.setPrice(rs.getInt(4));
                    p.setDiscount(rs.getInt(5));
                    p.setQuantity(rs.getInt(6));
                    p.setCompany(rs.getString(7));
                    list.add(p);
                }
                rs.close();
                ps.close();
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // get all product with category name
    public List<product> getAllProductWithCatName(String catName) {
        List<product> list = new ArrayList<>();
        String query = "SELECT * FROM product WHERE product.company = ?";
        try {
            statement = DBConnect.getInstall().get();
            if (statement != null) {
                ps = new DBConnect().getConnection().prepareStatement(query);
                ps.setString(1, catName);
                rs = ps.executeQuery();
                while (rs.next()) {
                    product p = new product();
                    p.setId(rs.getInt(1));
                    p.setTitle(rs.getString(2));
                    p.setImage(rs.getString(3));
                    p.setPrice(rs.getInt(4));
                    p.setDiscount(rs.getInt(5));
                    p.setQuantity(rs.getInt(6));
                    p.setCompany(rs.getString(7));
                    list.add(p);
                }
                rs.close();
                ps.close();
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int quantityInCart(List<product> list) {
        int result = 0;
        for (product p : list) {
            result += p.getQuantityInCart();
        }
        return result;
    }

    // get one product
    public product getOneProductWithId(int pid) {
        product p = new product();
        String query = "SELECT * FROM product WHERE product.id = ?";
        try {
            statement = DBConnect.getInstall().get();
            if (statement != null) {
                ps = new DBConnect().getConnection().prepareStatement(query);
                ps.setInt(1, pid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    p.setId(rs.getInt(1));
                    p.setTitle(rs.getString(2));
                    p.setImage(rs.getString(3));
                    p.setPrice(rs.getInt(4));
                    p.setDiscount(rs.getInt(5));
                    p.setQuantity(rs.getInt(6));
                    p.setCompany(rs.getString(7));
                }
                rs.close();
                ps.close();
                return p;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // total price
    public int totalPrice(int price, int discount, int quantity) {
        return (price - discount) * quantity;
    }

    // insert one product to order
    public void insertOneProduct(String fullName, String streetName, String subDistrict, String district,
                                 String city, String email, String phone, int pid) {
        String query = "INSERT INTO `order`(`order`.full_name, `order`.street_name, " +
                "`order`.sub_district, `order`.district, `order`.province, `order`.email, " +
                "`order`.phone, `order`.total_price, `order`.create_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String query_2 = "INSERT INTO order_details(order_details.order_id, " +
                "order_details.product_id, quantity) " +
                "VALUES (?, ?, ?)";
        String query_3 = "SELECT MAX(`order`.id) FROM `order`";
        int order_id = 0;
        try {
            statement = DBConnect.getInstall().get();
            if (statement != null) {
                ps = new DBConnect().getConnection().prepareStatement(query);
                PreparedStatement ps_2 = new DBConnect().getConnection().prepareStatement(query_2);
                PreparedStatement ps_3 = new DBConnect().getConnection().prepareStatement(query_3);

                ps.setString(1, fullName);
                ps.setString(2, streetName);
                ps.setString(3, subDistrict);
                ps.setString(4, district);
                ps.setString(5, city);
                ps.setString(6, email);
                ps.setString(7, phone);
                ps.setInt(8, 0);
                ps.setString(9, String.valueOf(LocalDateTime.now()));
                ps.executeUpdate();

                rs = ps_3.executeQuery();
                while (rs.next()) {
                    order_id = rs.getInt(1);
                }

                ps_2.setInt(1, order_id);
                ps_2.setInt(2, pid);
                ps_2.setInt(3, 1);

                ps_2.executeUpdate();

                System.out.println("oke");

                ps.close();
                ps_2.close();
                ps_3.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // check cart
    // create order

    public boolean checkProductExistInCart(List<product> list, product p) {
        for (product item : list) {
            if (item.getId() == p.getId()) {
                int quan = item.getQuantityInCart();
                item.setQuantityInCart(quan + 1);
                return true;
            }
        }
        return false;
    }

    public void insertOrder(String fullName, String streetName, String subDistrict, String district,
                            String city, String email, String phone, int totalPrice) {
        String query = "INSERT INTO `order`(`order`.full_name, `order`.street_name, " +
                "`order`.sub_district, `order`.district, `order`.province, `order`.email, " +
                "`order`.phone, `order`.total_price, `order`.create_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            statement = DBConnect.getInstall().get();
            if (statement != null) {
                ps = new DBConnect().getConnection().prepareStatement(query);

                ps.setString(1, fullName);
                ps.setString(2, streetName);
                ps.setString(3, subDistrict);
                ps.setString(4, district);
                ps.setString(5, city);
                ps.setString(6, email);
                ps.setString(7, phone);
                ps.setInt(8, totalPrice);
                ps.setString(9, String.valueOf(LocalDateTime.now()));
                ps.executeUpdate();

                System.out.println("Insert order success");

                ps.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //
    public void insertOrderDetails(int pid, int quantity) {
        String query_1 = "INSERT INTO order_details(order_details.order_id, " +
                "order_details.product_id, quantity) " +
                "VALUES (?, ?, ?)";
        String query_2 = "SELECT MAX(`order`.id) FROM `order`";
        String query_3 = "UPDATE product SET product.quantity = (product.quantity - ?) WHERE product.id = ?";
        int order_id = 0;
        try {
            statement = DBConnect.getInstall().get();
            if (statement != null) {
                ps = new DBConnect().getConnection().prepareStatement(query_1);
                PreparedStatement ps_2 = new DBConnect().getConnection().prepareStatement(query_2);
                PreparedStatement ps_3 = new DBConnect().getConnection().prepareStatement(query_3);
                rs = ps_2.executeQuery();
                while (rs.next()) {
                    order_id = rs.getInt(1);
                }
                ps.setInt(1, order_id);
                ps.setInt(2, pid);
                ps.setInt(3, quantity);
                ps.executeUpdate();

                ps_3.setInt(1, quantity);
                ps_3.setInt(2, pid);
                ps_3.executeUpdate();

                System.out.println("Insert order details / reduce quantity success");

                ps.close();
                rs.close();
                ps_2.close();
                ps_3.close();

            }
        } catch (Exception e) {

        }
    }

    public void reduceQuantityInDB(int pid, int quantityInCart) {
        String query = "UPDATE product SET product.quantity = (product.quantity - ?) WHERE product.id = ?";
        try {
            statement = DBConnect.getInstall().get();
            if (statement != null) {
                ps = new DBConnect().getConnection().prepareStatement(query);
                ps.setInt(1, quantityInCart);
                ps.setInt(2, pid);
                ps.executeUpdate();

                System.out.println("Reduce quantity in db success");

                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public int getQuantityWithID(int pid) {
        int result = 0;
        String query = "SELECT product.quantity FROM product WHERE product.id = ?";
        try {
            statement = DBConnect.getInstall().get();
            if (statement != null) {
                ps = new DBConnect().getConnection().prepareStatement(query);
                ps.setInt(1, pid);
                rs = ps.executeQuery();
                while (rs.next()) {
                    result = rs.getInt(1);
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void DeleteProductInCart(List<product> list, int pid) {
        for (product item : list) {
            if (item.getId() == pid) {
                list.remove(item);
                System.out.println("Delete product " + item.getId() + " success");
                break;
            }
        }
    }

    public List<product> searchProduct(String searchValue) {
        List<product> list = new ArrayList<>();
        String query = "SELECT * FROM product WHERE product.title LIKE ?";
        try {
            statement = DBConnect.getInstall().get();
            if (statement != null) {
                ps = new DBConnect().getConnection().prepareStatement(query);
                ps.setString(1, "%" + searchValue + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    product p = new product();
                    p.setId(rs.getInt(1));
                    p.setTitle(rs.getString(2));
                    p.setImage(rs.getString(3));
                    p.setPrice(rs.getInt(4));
                    p.setDiscount(rs.getInt(5));
                    p.setQuantity(rs.getInt(6));
                    p.setCompany(rs.getString(7));
                    list.add(p);
                }
                rs.close();
                ps.close();
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void removeProductAfterPay(List<product> cart, List<product> listBuy){
        for (product c : cart){
            for (product lb : listBuy){
                if (c.getId() == lb.getId()){
                    cart.remove(c);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {

//        System.out.println(new productDAO().getAllProduct());
//        System.out.println(new productDAO().getAllProductWithCatName("akko"));
//        System.out.println(new productDAO().getOneProductWithId(4));
//        List<product> list = new productDAO().getAllProduct();
//        new productDAO().DeleteProductInCart(list, 1);
//        System.out.println(list);
//        new productDAO().reduceQuantityInDB(2, 1);
        System.out.println(new productDAO().searchProduct("w"));
    }

}
