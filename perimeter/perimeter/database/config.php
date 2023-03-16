<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "perimeter";
// Create connection
$db = new mysqli($servername, $username, $password, $dbname);

if ($db->connect_error) {
  die("Connection failed: " . $db->connect_error);
}