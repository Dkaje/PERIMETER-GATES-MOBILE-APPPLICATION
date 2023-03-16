<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST['id'];
    $sql = mysqli_query($db, "DELETE from product where id='$id'");
    if ($sql) {
        $response["success"] = 1;
        $response["message"] = "Product deleted successfully";
        echo json_encode($response);
        mysqli_close($db);
    } else {
        $response["success"] = 0;
        $response["message"] = " Failed to delete product";
        echo json_encode($response);
        mysqli_close($db);
    }
}