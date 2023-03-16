<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $category = $_POST['category'];
    $description = $_POST['description'];
    $quantity = $_POST['quantity'];
    $type = $_POST['type'];
    $price = $_POST['price'];
    $stock_id = $_POST['stock_id'];
    $quantity = $_POST['quantity'];
    $pur_id = $_POST['pur_id'];
    $status = $_POST['status'];
    $image = mysqli_query($db, "SELECT image as img from stock where stock_id='$stock_id'");
        $photo = mysqli_fetch_assoc($image);

    $agela = mysqli_query($db, "UPDATE stock set status='$status' where stock_id='$stock_id'");
    if ($agela) {//id,category,type,image,quantity,price,reg_date
        mysqli_query($db,"UPDATE purchase set quantity=(quantity+$quantity) where pur_id='$pur_id'");
        $select = mysqli_query($db, "SELECT * from store where category='$category' and type='$type' and price='$price'");
         if (mysqli_num_rows($select) > 0) {
    $bock = mysqli_query($db, "UPDATE store set quantity=(quantity+$quantity) where category='$category' and type='$type' and price='$price'");
    if ($bock) {
        $select = mysqli_query($db, "SELECT * from product where category='$category' and type='$type' and price='$price'");
    if (mysqli_num_rows($select) > 0) {
        $kani = mysqli_query($db, "UPDATE product set description='$description', image='$photo[img]',qty=(qty+$quantity),quantity=(quantity+$quantity) where category='$category' and type='$type' and price='$price'");
        if ($kani) {
            $response["success"] = 1;
            $response["message"] = "  update was succesful";
            
        } else {
            $response["success"] = 0;
            $response["message"] = " Failed to update";
            
        }
    } else {
        $sql = "INSERT INTO product(category,type,description,image,qty,quantity,price)
        VALUES('$category','$type','$description','$photo[img]','$quantity','$quantity','$price')";
        if (mysqli_query($db, $sql)) {
            
            $response["success"] = 1;
            $response["message"] = "  Product uploaded succesfully";
            
        } else {
            $response["success"] = 0;
            $response["message"] = "  Failed to upload product";
            
        }
    }
    } else {
        $response["success"] = 0;
        $response["message"] = " Failed to update";
        echo json_encode($response);
        mysqli_close($db);
    }
} else {
    $sql = "INSERT INTO store(category,type,image,quantity,price)
    VALUES('$category','$type','$photo[img]','$quantity','$price')";
    if (mysqli_query($db, $sql)) {
        $select = mysqli_query($db, "SELECT * from product where category='$category' and type='$type' and price='$price'");
    if (mysqli_num_rows($select) > 0) {
        $kani = mysqli_query($db, "UPDATE product set description='$description', image='$photo[img]',qty=(qty+$quantity),quantity=(quantity+$quantity) where category='$category' and type='$type' and price='$price'");
        if ($kani) {
            $response["success"] = 1;
            $response["message"] = "  update was succesful";
            
        } else {
            $response["success"] = 0;
            $response["message"] = " Failed to update";
            
        }
    } else {
        $sql = "INSERT INTO product(category,type,description,image,qty,quantity,price)
        VALUES('$category','$type','$description','$photo[img]','$quantity','$quantity','$price')";
        if (mysqli_query($db, $sql)) {
            
            $response["success"] = 1;
            $response["message"] = "  Product uploaded succesfully";
            
        } else {
            $response["success"] = 0;
            $response["message"] = "  Failed to upload product";
            
        }
    }
    } else {
        $response["success"] = 0;
        $response["message"] = "  Failed to upload post";
        
    }
}
} else {
    $response["success"] = 0;
    $response["message"] = "Operation failed";
}
echo json_encode($response);
mysqli_close($db);
}