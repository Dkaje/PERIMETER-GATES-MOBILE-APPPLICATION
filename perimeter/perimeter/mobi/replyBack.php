<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST['id'];
    $reply = $_POST['reply'];
    $sql = "UPDATE feedback set reply='$reply' where id='$id'";
        if (mysqli_query($db, $sql)) {
            $response["success"] = 1;
            $response["message"] = " Reply sent succesfully";
        } else {
            $response["success"] = 0;
            $response["message"] = "  Failed to send your reply";
        }
        echo json_encode($response);
        mysqli_close($db);
}