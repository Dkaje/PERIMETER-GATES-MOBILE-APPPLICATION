<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {//payid,customer,phone,name,rating,message
    $customer = $_POST['customer'];
    $phone = $_POST['phone'];
    $name = $_POST['name'];
    $rating = $_POST['rating'];
    $message = $_POST['message']; 
    $currentDateTime = date('Y-m-d H:i:s');
    $sql = "INSERT INTO feedback(customer,phone,name,rating,message,reg_date)
        VALUES('$customer','$phone','$name','$rating','$message','$currentDateTime')";
        if (mysqli_query($db, $sql)) {
            $response["success"] = 1;
            $response["message"] = " Feedback sent succesfully";
        } else {
            $response["success"] = 0;
            $response["message"] = "  Failed to send your feedback";
        }
        echo json_encode($response);
        mysqli_close($db);
}