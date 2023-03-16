<?php
include 'config.php';
$sql = "CREATE TABLE disburse(
    id int auto_increment PRIMARY KEY,
    ind varchar(20),
    mpesa varchar(20),
    supplier varchar(50),
    amount float,
    reg_date varchar(50) 
    )";
//id,ind,mpesa,supplier,amount,reg_date//disburse
$result = mysqli_query($db, $sql);
if (!$result) {
    die("Connection failed: " . $db->connect_error);
} else {
    echo "table created";
}
mysqli_close($db);