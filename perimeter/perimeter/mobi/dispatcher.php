<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $username = $_POST['username'];
    $sql = "SELECT * FROM staff WHERE username='$username' and role='Inventory'";
    $response = mysqli_query($db, $sql);
    if (mysqli_num_rows($response) === 1) {
        $row = mysqli_fetch_array($response);
        if ($row['status'] == 0) {
            $result['success'] = 0;
            $result['message'] = "Approval Pending";
            echo json_encode($result);
        } elseif ($row['status'] == 2) {
            $result['success'] = 0;
            $result['message'] = "Access denied";
            echo json_encode($result);
        } else {
            $result['success'] = 1;
            $result['message'] = "Login was successful";
            echo json_encode($result);
            mysqli_close($db);
        }
    } else {
        $result['success'] = 0;
        $result['message'] = "Username not recognized";
        echo json_encode($result);
        mysqli_close($db);
    }
}