<%----%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%----%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- lib -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://unpkg.com/tippy.js@6/animations/scale.css"/>
    <!-- css -->
    <link rel="stylesheet" href="./css/header.css"/>
    <link rel="stylesheet" href="./css/cart.css"/>
    <link rel="stylesheet" href="./css/snackbarToast.css">
    <title>Shop - Giỏ hàng</title>
</head>

<body>
<div class="container">

    <%@include file="header.jsp" %>

    <div class="main">
        <div class="main_wrapper">
            <form action="Checkout" method="post">
                <div class="cart_wrapper">
                    <div class="cart_content_header">
                        <div class="cart_chosse">
                            <input type="checkbox" name="checkAll" id="checkAll" value="1">
                        </div>
                        <div class="cart_product_info">Sản phẩm</div>
                        <div class="cart_product_price">
                            <span>Đơn giá</span>
                        </div>
                        <div class="cart_product_quantity">
                            <span>Số lượng</span>
                        </div>
                        <div class="cart_product_total">
                            <span>Số tiền</span>
                        </div>
                        <div class="cart_product_action">
                            <span>Thao tác</span>
                        </div>
                    </div>
                    <div class="cart_content">

                        <c:choose>
                            <c:when test="${sessionScope.cart != null}">
                                <c:forEach items="${sessionScope.cart}" var="p">
                                    <div class="cart_product">
                                        <div class="cart_chosse">
                                            <input type="checkbox" name="checkbox" id="" class="checkItem" value="${p.id}">
                                        </div>
                                        <div class="cart_product_info">
                                            <div class="product_image">
                                                <img src="${p.image}"
                                                     alt="">
                                            </div>
                                            <div class="cart_product_title">
                                                <span>${p.title}</span>
                                            </div>
                                        </div>

                                        <div class="cart_product_price">
                                            <span class="old_price">${p.price}₫</span>
                                            <span class="new_price">${p.price - p.discount}₫</span>
                                        </div>
                                        <div class="cart_product_quantity cart_quantity">
                                            <button class="down" type="button" onclick="reduce(${p.id})"><span>-</span>
                                            </button>
                                            <input type="text" value="${p.quantityInCart}" class="quantity_cart">
                                            <button class="up" type="button" onclick="increasing(${p.id})">
                                                <span>+</span>
                                            </button>
                                        </div>
                                        <div class="cart_product_total">
                                            <span>${p.price - p.discount}₫</span>
                                        </div>
                                        <div class="cart_product_action">
                                            <button type="button" class="cart_delete_btn" onclick="deleteProductInCart(${p.id})">Xoá
                                            </button>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:when test="${sessionScope.cart == null}">
                                <div style="font-size: 20px; color: #f39c12; font-weight: bold; text-align: center;">
                                    <span>Không có sản phẩm trong giỏ hàng</span></div>
                            </c:when>
                        </c:choose>


                    </div>

                    <div class="cart_total_price">
                        <div class="total_price">
                            <span>Tổng tiền: ${totalPriceInCart}₫</span>
                        </div>
                        <%--                        <a href="Checkout" class="pay">Thanh toán</a>--%>
                        <button type="submit" class="pay">Thanh toán</button>
                    </div>
                </div>
            </form>
        </div>
    </div>


</div>
<div id="snackbar"></div>
</body>
<script src="https://unpkg.com/@popperjs/core@2"></script>
<script src="https://unpkg.com/tippy.js@6"></script>
<script src="https://code.jquery.com/jquery-3.7.0.js"></script>
<script>
    var popper = document.querySelector('.popper_wrapper')
    tippy('#cart_btn', {
        interactive: true,
        content: popper.innerHTML,
        allowHTML: true,
        delay: [300, 200],
    });

    $('#checkAll').click(function (e) {
        if (this.checked) {
            $('.checkItem').each(function () {
                this.checked = true;
            })
        } else {
            $('.checkItem').each(function () {
                this.checked = false;
            })
        }
    })
</script>
<script>
    var downBtn = document.querySelectorAll('.down');
    var upBtn = document.querySelectorAll('.up');
    var quantity = document.querySelectorAll('.quantity_cart');

    var count = 0;

    function toastMessage(message) {
        var x = document.getElementById("snackbar");
        x.innerHTML = message
        x.className = "show";
        setTimeout(function () {
            x.className = x.className.replace("show", "");
        }, 3000);
    }

    //
    function reduce(pid) {
        $.ajax({
            url: 'ReduceQuantity',
            type: 'post',
            data: {
                pid: pid
            },
            success: function (data) {
                let quantity = document.querySelector(".cart_wrapper")
                quantity.innerHTML = `<div class="cart_content_header">
                        <div class="cart_chosse">
                            <input type="checkbox" name="" id="">
                        </div>
                        <div class="cart_product_info">Sản phẩm</div>
                        <div class="cart_product_price">
                            <span>Đơn giá</span>
                        </div>
                        <div class="cart_product_quantity">
                            <span>Số lượng</span>
                        </div>
                        <div class="cart_product_total">
                            <span>Số tiền</span>
                        </div>
                        <div class="cart_product_action">
                            <span>Thao tác</span>
                        </div>
                    </div>` + data

                // console.log(data)
            },
            error: function (error) {
                console.log(error)
            }
        })
    }

    //
    function increasing(pid) {
        $.ajax({
            url: 'IncreasingQuantity',
            type: 'post',
            data: {
                pid: pid
            },
            success: function (data) {
                let quantity = document.querySelector(".cart_wrapper")
                quantity.innerHTML = `<div class="cart_content_header">
                        <div class="cart_chosse">
                            <input type="checkbox" name="" id="">
                        </div>
                        <div class="cart_product_info">Sản phẩm</div>
                        <div class="cart_product_price">
                            <span>Đơn giá</span>
                        </div>
                        <div class="cart_product_quantity">
                            <span>Số lượng</span>
                        </div>
                        <div class="cart_product_total">
                            <span>Số tiền</span>
                        </div>
                        <div class="cart_product_action">
                            <span>Thao tác</span>
                        </div>
                    </div>` + data
                // console.log(data)
            },
            error: function (error) {
                console.log(error)
            }
        })
    }

    //
    function deleteProductInCart(pid) {
        $.ajax({
            url: 'DeleteProductInCart',
            type: 'post',
            data: {
                pid: pid
            },
            success: function (data) {
                let quantity = document.querySelector(".cart_wrapper")
                quantity.innerHTML = `<div class="cart_content_header">
                        <div class="cart_chosse">
                            <input type="checkbox" name="" id="">
                        </div>
                        <div class="cart_product_info">Sản phẩm</div>
                        <div class="cart_product_price">
                            <span>Đơn giá</span>
                        </div>
                        <div class="cart_product_quantity">
                            <span>Số lượng</span>
                        </div>
                        <div class="cart_product_total">
                            <span>Số tiền</span>
                        </div>
                        <div class="cart_product_action">
                            <span>Thao tác</span>
                        </div>
                    </div>` + data
                toastMessage("Xoá thành công")
                // console.log(data)
            },
            error: function (error) {
                console.log(error)
            }
        })
    }

    //
    function checkAll() {
        let value = $('#checkAll').val()
        $.ajax({
            url: 'Checkout',
            type: 'post',
            data: {
                value: value
            },
            success: function (data) {
                console.log(data)
            },
            error: function (error) {
                console.log(error)
            }
        })
    }

</script>

</html>