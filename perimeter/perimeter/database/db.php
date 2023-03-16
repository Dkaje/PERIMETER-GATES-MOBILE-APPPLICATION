<?php
$host="localhost";
$username="root";
$password="";

$con=new mysqli($host, $username, $password);
if($con->connect_error){
  die("Connection failed");
}

$sql='CREATE Database perimeter';
if ($con->query($sql)==TRUE) {
  echo "Database created";
}else {
  echo "Error occurred:". $con->error;
}

$con->close();