<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    //category,type,qty,pur_id,quantity,price,description,image,supplier
    $category = $_POST['category'];
    $description = $_POST['description'];
    $quantity = $_POST['quantity'];
    $qty = $_POST['qty'];
    $pur_id = $_POST['pur_id'];
    $supplier = $_POST['supplier'];
    $type = $_POST['type'];
    $price = $_POST['price'];
    $profile = $_POST['image'];
    $filename = "IMG" . rand() . ".jpg";
    if($quantity>$qty){
$response["success"] = 0;
            $response["message"] = "Your quantity is beyond";
            echo json_encode($response);
    }else{
    $select = mysqli_query($db, "SELECT * from stock where category='$category' and type='$type' and price='$price' and supplier='$supplier' and status='Pending'");
    if (mysqli_num_rows($select) > 0) {
        $cate = mysqli_query($db, "UPDATE stock set description='$description', image='$filename',quantity=(quantity+$quantity) where category='$category' and type='$type' and price='$price' and supplier='$supplier' and status='Pending'");
        if ($cate) {
            file_put_contents("images/" . $filename, base64_decode($profile));
            mysqli_query($db,"UPDATE purchase set quantity=(quantity+$quantity) where pur_id='$pur_id'");
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
        $sql = "INSERT INTO stock(category,type,pur_id,quantity,price,description,image,supplier)
        VALUES('$category','$type','$pur_id','$quantity','$price','$description','$filename','$supplier')";
        if (mysqli_query($db, $sql)) {
            file_put_contents("images/" . $filename, base64_decode($profile));
            mysqli_query($db,"UPDATE purchase set quantity=(quantity+$quantity) where pur_id='$pur_id'");
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
}