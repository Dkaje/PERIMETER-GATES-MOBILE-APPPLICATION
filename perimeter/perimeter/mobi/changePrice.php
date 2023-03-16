<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST['id'];
    $price = $_POST['price'];
    $quantity = $_POST['quantity'];
    $sql = mysqli_query($db, "UPDATE product set price='$price',quantity='$quantity' where id='$id'");
    if ($sql) {
        $response["success"] = 1;
        $response["message"] = "Product updated successfully";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = " Failed to update product";
        echo json_encode($response);
        mysqli_close($db);
    }
}