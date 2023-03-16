<?php
include 'config.php';

$sql = "CREATE TABLE driver(
  driver_id varchar(50) PRIMARY KEY,
  fname TEXT NOT NULL,
  lname text not null,
  username varchar(50),
  email varchar(50),
  contact varchar(50),
  password VARCHAR(250),
  status int default 0,
  remarks varchar(200),
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)";
//driver_id,fname,lname,email,contact,status,remarks,date
$result = mysqli_query($db, $sql);
if (!$result) {
  die("Connection failed: " . $db->connect_error);
} else {
  echo "created successfully";
}
mysqli_close($db);