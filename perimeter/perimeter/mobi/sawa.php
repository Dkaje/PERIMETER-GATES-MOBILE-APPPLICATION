<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $comment = $_POST["comment"];
    $payid = $_POST["payid"];
    $status = $_POST["status"];
    $query = mysqli_query($db, "UPDATE orders set finance='$status',remarks='$comment' where order_id='$payid'");
    if ($query) {
        $response["success"] = 1;
        $response["message"] = "Payment updated successfully";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = "Failed to update payment";
        echo json_encode($response);
        mysqli_close($db);
    }
}