<?php
include("../database/config.php");
$username = $_POST['username'];
$password = md5($_POST['password']);
$sql = "SELECT * FROM driver WHERE username='$username'";
$response = mysqli_query($db, $sql);
if (mysqli_num_rows($response) === 1) {
    mysqli_query($db, "UPDATE driver set password='$password' where username='$username'");
    $row = mysqli_fetch_assoc($response);
    $result['success'] = 1;
    $result['message'] =  "You have reset your password";
    echo json_encode($result);
    mysqli_close($db);
} else {
    $result['success'] = 0;
    $result['message'] = "Failed to recognize";
    echo json_encode($result);
    mysqli_close($db);
}