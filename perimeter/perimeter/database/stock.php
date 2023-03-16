<?php
include 'config.php';
$sql = "CREATE TABLE stock(
  stock_id int AUTO_INCREMENT PRIMARY KEY,
  pur_id int,
  category TEXT NOT NULL,
  type TEXT NOT NULL,
  quantity FLOAT,
  price FLOAT,
  description varchar(250),
  image varchar(250),
  supplier varchar(50),
  status varchar(20) default 'Pending',
  pay varchar(20) default 'Pending',
  reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)";//id,category,type,description,image,qty,quantity,price,reg_date
//stock_id,pur_id,category,type,quantity,price,description,image,supplier,status,pay,reg_date
$result = mysqli_query($db, $sql);
if (!$result) {
    die("Connection failed: " . $db->connect_error);
} else {
    echo "supplication created successfully";
}
mysqli_close($db);