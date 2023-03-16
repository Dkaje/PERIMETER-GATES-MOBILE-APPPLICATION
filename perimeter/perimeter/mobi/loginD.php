<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $username = $_POST['username'];
    $password = md5($_POST['password']);
    $sql = "SELECT * FROM driver WHERE username='$username' AND password='$password'";
    $response = mysqli_query($db, $sql);
    if (mysqli_num_rows($response) === 1) {
        $row = mysqli_fetch_array($response);
        if ($row['status'] == 0) {
            $result['success'] = "0";
            $result['message'] = "Approval Pending";
            echo json_encode($result);
        } elseif ($row['status'] == 2) {
            $result = array();
            $result['login'] = array();
            $index['remarks'] = $row['remarks'];
            $result['success'] = "2";
            $result['message'] = "Access denied";
            array_push($result['login'], $index);
            echo json_encode($result);
        } else {
            $result = array(); //driver_id,fname,lname,username,contact,email,status,reg_date
            $result['login'] = array();
            $index['driver_id'] = $row['driver_id'];
            $index['fname'] = $row['fname'];
            $index['lname'] = $row['lname'];
            $index['username'] = $row['username'];
            $index['contact'] = $row['contact'];
            $index['email'] = $row['email'];
            if ($row['status'] == 1) {
                $index['status'] = 'Approved';
            }
            $index['reg_date'] = $row['date'];
            array_push($result['login'], $index);
            $result['success'] = "1";
            $result['message'] = "Login was successful";
            echo json_encode($result);
            mysqli_close($db);
        }
    } else {
        $result['success'] = "0";
        $result['message'] = "Invalid credentials";
        echo json_encode($result);
        mysqli_close($db);
    }
}