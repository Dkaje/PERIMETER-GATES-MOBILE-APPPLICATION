<?php
include("../database/config.php");
function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $mpesa = test_input($_POST["mpesa"]);
    $amount = $_POST['amount'];
    $orders = $_POST['orders'];
    $ship = $_POST['ship'];
    $cust_id = $_POST['cust_id'];
    $name = $_POST['name'];
    $phone = $_POST['phone'];
    $mode = $_POST['mode'];
    $location = $_POST['location'];
    $room = date("Y");
    $per = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $newS = substr(str_shuffle($per), 0, 4);
    $naming = "/^[A-Z0-9]{10,}$/";
    $caps = "/^[A-Z]{10,}$/";
    $nums = "/^[0-9]{10,}$/";
    if (preg_match($caps, $mpesa)) {
        $response["success"] = 0;
        $response["message"] = "Failed!! Your MPESA is invalid";
        echo json_encode($response);
    } elseif (preg_match($nums, $mpesa)) {
        $response["success"] = 0;
        $response["message"] = "Oops!! Such MPESA code is invalid";
        echo json_encode($response);
    } else {
        $select = "SELECT mpesa FROM payment WHERE mpesa='$mpesa'";
        $query = mysqli_query($db, $select);
        if (mysqli_num_rows($query) > 0) {
            $response["success"] = 0;
            $response["message"] = "MPESA code not accepted";
            echo json_encode($response);
            mysqli_close($db);
        } else {
            $count_my_page = ("setting.txt");
            $hits = file($count_my_page);
            $hits[0]++;
            $fp = fopen($count_my_page, "w");
            fputs($fp, "$hits[0]");
            fclose($fp);
            $reg = $hits[0];
            $serial = $reg . $newS . $room;
            $sql = "INSERT INTO payment(serial,mpesa,amount,orders,ship,cust_id,name,phone,mode,location)
        VALUES('$serial','$mpesa','$amount','$orders','$ship','$cust_id','$name','$phone','$mode','$location')";
            if (mysqli_query($db, $sql)) {
                mysqli_query($db, "UPDATE cart set status='Ordered',serial='$serial' where cust_id='$cust_id' and status='Pending' and serial='Pending'");
                $response["success"] = 1;
                $response["message"] = "Payment was successfully";
                echo json_encode($response);
                mysqli_close($db);
            } else {
                $response["success"] = 0;
                $response["message"] = "Failed to submit payment";
                echo json_encode($response);
                mysqli_close($db);
            }
        }
    }
}