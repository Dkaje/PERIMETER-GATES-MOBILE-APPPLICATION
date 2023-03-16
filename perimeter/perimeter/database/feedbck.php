<?php
include 'config.php';
//id,customer,phone,name,rating,message,reg_date,reply,reply_date
$sql = "CREATE TABLE feedback(
    id int auto_increment PRIMARY KEY,
    customer VARCHAR(50),
    phone VARCHAR(20),
    name varchar(100),
    rating varchar(50),
    message VARCHAR(250),
    reg_date varchar(100),
    reply varchar(250) default 'Pending',
    reply_date timestamp default current_timestamp on update current_timestamp
    )";
$result = mysqli_query($db, $sql);
if (!$result) {
    die("Connection failed: " . $db->connect_error);
} else {
    echo "table created";
}
mysqli_close($db);