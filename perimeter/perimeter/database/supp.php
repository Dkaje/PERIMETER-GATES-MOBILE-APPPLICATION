<?php
include 'config.php';
$sql = "CREATE TABLE purchase(
  pur_id int AUTO_INCREMENT PRIMARY KEY,
  category TEXT NOT NULL,
  type TEXT NOT NULL,
  quantity FLOAT,
  reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)";
//pur_id,category,type,quantity,reg_date,purchase
$result = mysqli_query($db, $sql);
if (!$result) {
    die("Connection failed: " . $db->connect_error);
} else {
    echo "supplication created successfully";
}
mysqli_close($db);