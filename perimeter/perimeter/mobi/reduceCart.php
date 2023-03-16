<?php
include("../database/config.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $qty = $_POST["qty"];
    $product = $_POST["product"];
    $quantity = $_POST["quantity"];
    $reg = $_POST["reg"];
    $quota = $qty - $quantity;
    if ($quantity > $qty) {
        $response["success"] = 0;
        $response["message"] = "Your reducing quantity is slightly higher than your cart";
        echo json_encode($response);
    } elseif ($quantity == $qty) {
        $response["success"] = 0;
        $response["message"] = "Not allowed to reduce to zero. Click drop cart instead";
        echo json_encode($response);
    } else {
        $query = mysqli_query($db, "UPDATE cart set quantity='$quota' where reg='$reg'");
        if ($query) {
            mysqli_query($db, "UPDATE product set quantity=(quantity+$quantity) where id='$product'");
            $response["success"] = 1;
            $response["message"] = "Cart reduced successfully";
            echo json_encode($response);
            mysqli_close($db);
        } else {
            $response["success"] = 0;
            $response["message"] = " Failed to reduce cart";
            echo json_encode($response);
            mysqli_close($db);
        }
    }
}