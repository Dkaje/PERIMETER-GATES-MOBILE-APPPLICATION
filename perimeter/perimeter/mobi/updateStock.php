<?php
include("../database/config.php");//stock_id,quantity,pur_id,status
if ($_SERVER['REQUEST_METHOD'] == 'POST') {//category,type,image,quantity,price
    $stock_id = $_POST['stock_id'];
    $quantity = $_POST['quantity'];
    $pur_id = $_POST['pur_id'];
    $status = $_POST['status'];
    $category = $_POST['category'];
    $type = $_POST['type'];
    $price = $_POST['price'];
        $cate = mysqli_query($db, "UPDATE stock set status='$status' where stock_id='$stock_id'");
        if ($cate) {//id,category,type,image,quantity,price,reg_date
            mysqli_query($db,"UPDATE purchase set quantity=(quantity+$quantity) where pur_id='$pur_id'");
            $select = mysqli_query($db, "SELECT * from store where category='$category' and type='$type' and price='$price'");
             if (mysqli_num_rows($select) > 0) {
        $cate = mysqli_query($db, "UPDATE store set quantity=(quantity+$quantity) where category='$category' and type='$type' and price='$price'");
        if ($cate) {
            $response["success"] = 1;
            $response["message"] = "Verification was sucessful";
            echo json_encode($response);
            mysqli_close($db);
        } else {
            $response["success"] = 0;
            $response["message"] = " Failed to update";
            echo json_encode($response);
            mysqli_close($db);
        }
    } else {
            $image = mysqli_query($db, "SELECT image as img from stock where stock_id='$stock_id'");
            $photo = mysqli_fetch_assoc($image);
        $sql = "INSERT INTO store(category,type,image,quantity,price)
        VALUES('$category','$type','$photo[img]','$quantity','$price')";
        if (mysqli_query($db, $sql)) {
            $response["success"] = 1;
            $response["message"] = "Succesful";
            echo json_encode($response);
            mysqli_close($db);
        } else {
            $response["success"] = 0;
            $response["message"] = "  Failed to upload post";
            echo json_encode($response);
            mysqli_close($db);
        }
    }
}
}