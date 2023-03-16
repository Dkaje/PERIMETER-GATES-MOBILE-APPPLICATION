<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $driver = $_POST["driver"];
    $payid = $_POST["payid"];
    $query = mysqli_query($db, "UPDATE payment set driver='$driver' where payid='$payid'");
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