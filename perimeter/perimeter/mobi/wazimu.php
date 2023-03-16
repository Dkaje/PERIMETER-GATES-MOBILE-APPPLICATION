<?php
include("../database/config.php");
function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}//mpesa,amount,ship,cost,cust_id,name,phone,location,category,type,material,coating,dimension,quantity,set_date,mode
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $mpesa = test_input($_POST["mpesa"]);
    $amount = $_POST['amount'];
    $ship = $_POST['ship'];
    $cost = $_POST['cost'];
    $cust_id = $_POST['cust_id'];
    $name = $_POST['name'];
    $phone = $_POST['phone'];
    $location = $_POST['location'];
    $category = $_POST['category'];
    $type = $_POST['type'];
    $material = $_POST['material'];
    $coating = $_POST['coating'];
    $dimension = $_POST['dimension'];
    $quantity = $_POST['quantity'];
    $unit=round($cost/$quantity,1);
    $set_date = $_POST['set_date'];
    $location = $_POST['location'];
    $profile = $_POST['image'];
    $filename = "IMG" . rand() . ".jpg";
    $mode = $_POST['mode'];
    $naming = "/^[A-Z0-9]{10,}$/";
    $caps = "/^[A-Z]{10,}$/";
    $nums = "/^[0-9]{10,}$/";
    if (preg_match($caps, $mpesa)) {
        $response["success"] = 0;
        $response["message"] = "Failed!! Your MPESA is invalid";
    } elseif (preg_match($nums, $mpesa)) {
        $response["success"] = 0;
        $response["message"] = "Oops!! Such MPESA code is invalid";
        
    } else {
        $select = "SELECT mpesa FROM orders WHERE mpesa='$mpesa'";
        $query = mysqli_query($db, $select);
        if (mysqli_num_rows($query) > 0) {
            $response["success"] = 0;
            $response["message"] = "MPESA code not accepted";
        } else {
            $select = "SELECT mpesa FROM payment WHERE mpesa='$mpesa'";
        $query = mysqli_query($db, $select);
        if (mysqli_num_rows($query) > 0) {
            $response["success"] = 0;
            $response["message"] = "MPESA code was suspicious";
        } else {
            $sql = "INSERT INTO orders(mpesa,amount,ship,cost,cust_id,name,phone,location,category,type,material,coating,dimension,quantity,unit,set_date,mode,image)
        VALUES('$mpesa','$amount','$ship','$cost','$cust_id','$name','$phone','$location','$category','$type','$material','$coating','$dimension','$quantity','$unit','$set_date','$mode','$filename')";
            if (mysqli_query($db, $sql)) {
                file_put_contents("images/" . $filename, base64_decode($profile));
                $response["success"] = 1;
                $response["message"] = "Order placed successfully";
            } else {
                $response["success"] = 0;
                $response["message"] = "Failed to place Order";
            }
        }
    }
}
    echo json_encode($response);
    mysqli_close($db);
}