<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $payid = $_POST["payid"];
    $customer=$_POST["customer"];
    $query = mysqli_query($db, "UPDATE payment set customer='$customer' where payid='$payid'");
    if ($query) {
        $response["success"] = 1;
        $response["message"] = "Confirmed successfully";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = "Failed to confirm";
        echo json_encode($response);
        mysqli_close($db);
    }
}