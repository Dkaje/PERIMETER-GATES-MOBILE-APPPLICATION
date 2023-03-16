<?php
ob_start();
error_reporting(0);
session_start();
include 'database/config.php';
if (isset($_POST['submit'])) {

    $username = $_POST['user_id'];
    $password = md5($_POST['user_pass']);

    $query = "SELECT * FROM admin WHERE username='" . $username . "' and password='" . $password . "'";
    $result = mysqli_query($db, $query);
    if (!$result) {
        die("Connection failed: " . $db->connect_error);
        exit;
    }
    $count = mysqli_num_rows($result);
    if ($count) {
        $_SESSION['username'] = $username;
        echo "<script>location.href='index' </script>";
        die;
    } else {
        echo "<script>alert('username or password incorrect')</script>";
        echo "<script>location.href='log' </script>";
    }
}
ob_end_flush();
if (isset($_POST['regist'])) {
    $username = $_POST['user_id'];
    $password = md5($_POST['user_pass']);
    $cpassword = md5($_POST['user_pass_c']);
    $year = date("Y");
    if ($cpassword != $password) {
        echo "<script>alert('Your Passwords do not match')</script>";
        echo "<script>location.href='log' </script>";
    } else {
        $count_my_page = ("mobi/setting.txt");
        $hits = file($count_my_page);
        $hits[0]++;
        $fp = fopen($count_my_page, "w");
        fputs($fp, "$hits[0]");
        fclose($fp);
        $values = $hits[0];
        $admin = $values . $year;
        $pdoExec = mysqli_query($db, "INSERT into admin(admin_id,username,password)values('$admin','$username','$password')");
        if ($pdoExec) {
            echo "<script>alert('Account Created successfully')</script>";
            echo "<script>location.href='log' </script>";
        } else {
            echo "<script>alert('Failed to create account')</script>";
            echo "<script>location.href='log' </script>";
        }
    }
}

?>
<!DOCTYPE html>
<html lang="en" dir="ltr">

<head>
    <title></title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/style7.css">
</head>

<body style="background-color: #0ee978;">
    <form class="box" method="post" style="background-color: #0ee978;">

        <?php
        if (mysqli_num_rows(mysqli_query($db, "SELECT * from admin")) > 0) {
            echo '<h1>admin login</h1>
            <input type="text" name="user_id" placeholder="username" required>
            <input type="password" name="user_pass" placeholder="password" required>
            <input type="submit" name="submit" value="Login">';
        } else {
            echo '<h1>admin register</h1>
            <input type="text" name="user_id" placeholder="username" required>
            <input type="password" name="user_pass" placeholder="password" required>
            <input type="password" name="user_pass_c" placeholder="confirm Password" required>
            <input type="submit" name="regist" value="Register">';
        } ?>

    </form>
</body>

</html>