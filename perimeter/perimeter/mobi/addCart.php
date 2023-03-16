<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $product = $_POST['product'];
    $cust_id = $_POST['cust_id'];
    $price = $_POST['price'];
    $quantity = $_POST['quantity'];
    $type = $_POST['type'];
    $category = $_POST['category'];
    $quant = $_POST['quant'];
    if ($quantity > $quant) {
        $response["success"] = 9;
        $response["message"] = "Your Quantity is invalid";
        echo json_encode($response);
    } else {
        $select = "SELECT * FROM cart WHERE product='$product' and category='$category' and type='$type' and status='Pending' and serial='Pending' and cust_id='$cust_id'";
        $query = mysqli_query($db, $select);
        if (mysqli_num_rows($query) > 0) {
            $update = mysqli_query($db, "UPDATE cart set quantity=(quantity+'$quantity') where product='$product' and category='$category' and type='$type' and status='Pending' and serial='Pending' and cust_id='$cust_id'");
            if ($update) {
                $updat = "UPDATE product set quantity=(quantity-$quantity) where id='$product'";
                if (mysqli_query($db, $updat)) {
                    $response["success"] = 1;
                    $response["message"] = "Cart updated successfully";
                    echo json_encode($response);
                    mysqli_close($db);
                } else {
                    $response["success"] = 0;
                    $response["message"] = " Operation failed ";
                    echo json_encode($response);
                    mysqli_close($db);
                }
            }
        } else {
            $image = mysqli_query($db, "SELECT image as img from product where id='$product'");
            $photo = mysqli_fetch_assoc($image);
            $sql = "INSERT INTO cart(cust_id,product,category,type,price,quantity,image)
    VALUES('$cust_id','$product','$category','$type','$price','$quantity','$photo[img]')";
            if (mysqli_query($db, $sql)) {
                $nes = mysqli_query($db, "UPDATE product set quantity=(quantity-$quantity) where id='$product'");
                if ($nes) {
                    $response["success"] = 1;
                    $response["message"] = "Product insert was successful";
                    echo json_encode($response);
                    mysqli_close($db);
                } else {
                    $response["success"] = 0;
                    $response["message"] = "Could not insert cart";
                    echo json_encode($response);
                    mysqli_close($db);
                }
            } else {
                $response["success"] = 0;
                $response["message"] = "An error Occurred";
                echo json_encode($response);
                mysqli_close($db);
            }
        }
    }
}