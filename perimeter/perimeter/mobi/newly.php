<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $comment = $_POST["comment"];
    $payid = $_POST["payid"];
    $query = mysqli_query($db, "UPDATE orders set blacksmith='$comment' where order_id='$payid'");
    if ($query) {
        $response["success"] = 1;
        $response["message"] = "Update was successful successfully";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = "Failed to update";
        echo json_encode($response);
        mysqli_close($db);
    }
}