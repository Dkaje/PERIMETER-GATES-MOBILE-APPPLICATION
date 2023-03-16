<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $payid = $_POST["payid"];
    $query = mysqli_query($db, "UPDATE payment set drive='Delivered' where payid='$payid'");
    if ($query) {
        $response["success"] = 1;
        $response["message"] = "Delivery was successful";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = "Failed to confirm delivery";
        echo json_encode($response);
        mysqli_close($db);
    }
}