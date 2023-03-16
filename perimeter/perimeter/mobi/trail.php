<?php
include("../database/config.php");
function test_input($move)
{
    $move = trim($move);
    $move = stripslashes($move);
    $move = htmlspecialchars($move);
    return $move;
} //loginC,loginB,loginD,loginF,loginI,loginS,updateD,updateB,updateC,updateF,updateI,updateS
//cust_id,fname,lname,username,phone,location,email,password
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $fname = test_input($_POST["fname"]);
    $lname = test_input($_POST["lname"]);
    $email = $_POST['email'];
    $year = date("Y");
    $naming = "/^[a-zA-Z]{2,}$/";
    if (!preg_match($naming, $fname)) {
        $response["success"] = 0;
        $response["message"] = "Enter a valid Firstname";
        echo json_encode($response);
    } else {
        if (!preg_match($naming, $lname)) {
            $response["success"] = 0;
            $response["message"] = "Enter a valid Lastname";
            echo json_encode($response);
        } else {
            $count_my_page = ("setting.txt");
            $hits = file($count_my_page);
            $hits[0]++;
            $fp = fopen($count_my_page, "w");
            fputs($fp, "$hits[0]");
            fclose($fp);
            $values = $hits[0];
            $cust_id = $values . $year;
            $sql = "INSERT INTO customer(cust_id,fname,lname,email)
    VALUES('$cust_id','$fname','$lname','$email')";
            if (mysqli_query($db, $sql)) {
                $response["success"] = 1;
                $response["message"] = "Your Entry is " . $cust_id . " Account cretaed successfully";
                echo json_encode($response);
                mysqli_close($db);
            } else {
                $response["success"] = 0;
                $response["message"] = " Failed to create account";
                echo json_encode($response);
                mysqli_close($db);
            }
        }
    }
}