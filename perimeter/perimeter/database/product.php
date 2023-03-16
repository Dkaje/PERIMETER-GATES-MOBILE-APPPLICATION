<?php
include 'config.php';
$sql = "CREATE TABLE product(
 id int auto_increment PRIMARY KEY,
    category varchar(250),
    type varchar(250),
    description VARCHAR(250),
    image varchar(250),
    qty float,
    quantity float,
    price float,
    reg_date timestamp default current_timestamp 
    )";
//id,category,type,description,image,qty,quantity,price,reg_date
$result = mysqli_query($db, $sql);
if (!$result) {
  die("Connection failed: " . $db->connect_error);
} else {
  echo "product table created successfully";
}
mysqli_close($db);