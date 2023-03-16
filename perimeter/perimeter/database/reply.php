<?php
include 'config.php';

$sql = "CREATE TABLE reply(
    id int auto_increment PRIMARY KEY,
    customer VARCHAR(50),
    phone VARCHAR(20),
    name varchar(100),
    reply varchar(250),
    reply_date varchar(100)
    )";
$result = mysqli_query($db, $sql);
if (!$result) {
    die("Connection failed: " . $db->connect_error);
} else {
    echo "table created";
}
mysqli_close($db);