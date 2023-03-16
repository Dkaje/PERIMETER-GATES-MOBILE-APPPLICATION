<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $driver = $_POST["driver"];
    $order_id = $_POST["order_id"];
    $query = mysqli_query($db, "UPDATE orders set driver='$driver',dispatcher='Assigned' where order_id='$order_id'");
    if ($query) {
        $response["success"] = 1;
        $response["message"] = "Attachment was successfully";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = "Failed to attach driver";
        echo json_encode($response);
        mysqli_close($db);
    }
}