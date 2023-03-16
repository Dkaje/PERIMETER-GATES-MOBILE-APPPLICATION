<?php
$host="localhost";
$user="root";
$pass="";
$data="perimeter";
$con=new mysqli($host,$user,$pass,$data);
if($con->connect_error){
    echo 'Failed to connect';
}