<?php
include("../database/config.php");
function test_input($move)
{
    $move = trim($move);
    $move = stripslashes($move);
    $move = htmlspecialchars($move);
    return $move;
}
//cust_id,fname,lname,username,phone,email,password
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $fname = test_input($_POST["fname"]);
    $lname = test_input($_POST["lname"]);
    $username = $_POST['username'];
    $phone = $_POST['phone'];
    $email = $_POST['email'];
    $password = md5($_POST['password']);
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
            $select = "SELECT email FROM driver WHERE email='$email'";
            $query = mysqli_query($db, $select);
            if (mysqli_num_rows($query) > 0) {
                $response["success"] = 0;
                $response["message"] = "Email not available";
                echo json_encode($response);
                mysqli_close($db);
            } else {
                $select = "SELECT username FROM driver WHERE username='$username'";
                $query = mysqli_query($db, $select);
                if (mysqli_num_rows($query) > 0) {
                    $response["success"] = 0;
                    $response["message"] = "Username not available";
                    echo json_encode($response);
                    mysqli_close($db);
                } else {
                    $select = "SELECT contact FROM driver WHERE contact='$phone'";
                    $query = mysqli_query($db, $select);
                    if (mysqli_num_rows($query) > 0) {
                        $response["success"] = 0;
                        $response["message"] = "Contact not available";
                        echo json_encode($response);
                        mysqli_close($db);
                    } else {
                        $count_my_page = ("setting.txt");
                        $hits = file($count_my_page);
                        $hits[0]++;
                        $fp = fopen($count_my_page, "w");
                        fputs($fp, "$hits[0]");
                        fclose($fp);
                        $values = $hits[0];
                        $driver_id = $values . $year;
                        $sql = "INSERT INTO driver(driver_id,fname,lname,username,contact,email,password)
    VALUES('$driver_id','$fname','$lname','$username','$phone','$email','$password')";
                        if (mysqli_query($db, $sql)) {
                            $response["success"] = 1;
                            $response["message"] = "Your Entry is " . $driver_id . " Account cretaed successfully";
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
        }
    }
}