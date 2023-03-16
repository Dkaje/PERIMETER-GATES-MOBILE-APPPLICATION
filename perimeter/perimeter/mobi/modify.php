<?php
include("../database/config.php");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $quota = $_POST["quota"];//quota,quantity,product,reg
    $product = $_POST["product"];
    $quantity = $_POST["quantity"];
    $reg = $_POST["reg"];
    $image = mysqli_query($db, "SELECT quantity as img from product where id='$product'");
    $photo = mysqli_fetch_assoc($image);
    $older=round((($photo['img'])+$quantity),0);
    if($quota>$older){
        $response["status"] = 0;
        $response["message"] = "Quantity available is $older. Kindly reduce your desired quantity";
        echo json_encode($response);
    }else{
        $new=round(($older-$quota),0);
        $sql1=mysqli_query($db, "UPDATE cart set quantity='$quota' where reg='$reg'");
        $sql2=mysqli_query($db, "UPDATE product set quantity='$new' where id='$product'");
        if($sql1 & $sql2){
            $response["status"] = 1;
            $response["message"] = "Cart modified successfully";
        }else {
            $response["status"] = 0;
            $response["message"] = " Failed to modify cart";
        }
    }
    echo json_encode($response);
    mysqli_close($db);
}