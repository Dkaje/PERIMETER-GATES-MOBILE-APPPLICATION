<?php
include 'config.php';

$sql = "CREATE TABLE cart(
    reg int auto_increment PRIMARY KEY,
    serial varchar(50) default 'Pending',
    cust_id VARCHAR(20),
    product int,
    category VARCHAR(250),
    type varchar(50),
    price float,
    quantity float,
    image VARCHAR(250),
    status varchar(20) default 'Pending',
    reg_date timestamp default current_timestamp 
    )";
$result = mysqli_query($db, $sql);
if (!$result) {
    die("Connection failed: " . $db->connect_error);
} else {
    echo "table created";
}
mysqli_close($db);