<?php
include 'config.php';

$sql = "CREATE TABLE customer(
  cust_id varchar(50) PRIMARY KEY,
  fname TEXT NOT NULL,
  lname text not null,
  username varchar(50),
  email varchar(50),
  contact varchar(50),
  location varchar(250),
  password VARCHAR(250),
  status int default 0,
  remarks varchar(200),
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)";
//cust_id,fname,lname,email,contact,location,status,remarks,date
$result = mysqli_query($db, $sql);
if (!$result) {
  die("Connection failed: " . $db->connect_error);
} else {
  echo "created successfully";
}
mysqli_close($db);