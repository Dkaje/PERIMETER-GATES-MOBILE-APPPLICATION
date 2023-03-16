<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $category = $_POST['category'];
    $description = $_POST['description'];
    $quantity = $_POST['quantity'];
    $type = $_POST['type'];
    $price = $_POST['price'];
    $profile = $_POST['image'];
    $filename = "IMG" . rand() . ".jpg";
    $select = mysqli_query($db, "SELECT * from product where category='$category' and type='$type' and price='$price'");
    if (mysqli_num_rows($select) > 0) {
        $cate = mysqli_query($db, "UPDATE product set description='$description', image='$filename',qty=(qty+$quantity),quantity=(quantity+$quantity) where category='$category' and type='$type' and price='$price'");
        if ($cate) {
            file_put_contents("images/" . $filename, base64_decode($profile));
            $response["success"] = 1;
            $response["message"] = "  update was succesful";
            echo json_encode($response);
            mysqli_close($db);
        } else {
            $response["success"] = 0;
            $response["message"] = " Failed to update";
            echo json_encode($response);
            mysqli_close($db);
        }
    } else {
        $sql = "INSERT INTO product(category,type,description,image,qty,quantity,price)
        VALUES('$category','$type','$description','$filename','$quantity','$quantity','$price')";
        if (mysqli_query($db, $sql)) {
            file_put_contents("images/" . $filename, base64_decode($profile));
            $response["success"] = 1;
            $response["message"] = "  Product uploaded succesfully";
            echo json_encode($response);
            mysqli_close($db);
        } else {
            $response["success"] = 0;
            $response["message"] = "  Failed to upload product";
            echo json_encode($response);
            mysqli_close($db);
        }
    }
}