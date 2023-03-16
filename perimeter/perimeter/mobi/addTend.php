<?php
include("../database/config.php");//pur_id,category,type,quantity,reg_date,purchase
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $category = $_POST['category'];
    $quantity = $_POST['quantity'];
    $type = $_POST['type'];
    $select = mysqli_query($db, "SELECT * from purchase where category='$category' and type='$type'");
    if (mysqli_num_rows($select) > 0) {
        $cate = mysqli_query($db, "UPDATE purchase set quantity=(quantity+$quantity) where category='$category' and type='$type'");
        if ($cate) {
            $response["success"] = 1;
            $response["message"] = " Publish was succesfully updated";
            echo json_encode($response);
            mysqli_close($db);
        } else {
            $response["success"] = 0;
            $response["message"] = " Failed to update";
            echo json_encode($response);
            mysqli_close($db);
        }
    } else {
        $sql = "INSERT INTO purchase(category,type,quantity)
        VALUES('$category','$type','$quantity')";
        if (mysqli_query($db, $sql)) {
            $response["success"] = 1;
            $response["message"] = " Post published uploaded succesfully";
            echo json_encode($response);
            mysqli_close($db);
        } else {
            $response["success"] = 0;
            $response["message"] = "  Failed to upload post";
            echo json_encode($response);
            mysqli_close($db);
        }
    }
}