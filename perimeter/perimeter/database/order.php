<?php
include 'config.php';

$sql = "CREATE TABLE orders(
  order_id bigint auto_increment PRIMARY KEY,
  mpesa varchar(15),
  amount float,
  ship float,
  cost float,
  quantity float,
  unit float,
  cust_id TEXT NOT NULL,
  name text not null,
  phone varchar(50),
  location varchar(250),
  category text,
  type varchar(50),
  material varchar(50),
  coating VARCHAR(250),
  dimension VARCHAR(250),
  set_date text,
  finance varchar(50) default 'Pending',
  remarks text,
  mode varchar(50),
  image VARCHAR(250) default 'Pending',
  blacksmith varchar(250) default 'Pending',
  dispatcher varchar(50) default 'Pending',
  driver varchar(50),
  drive varchar(50) default 'Pending',
  customer varchar(50) default 'Pending',
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)";
//order_id,mpesa,amount,ship,cost,quantity,unit,cust_id,name,phone,location,category,type,material,coating,dimension,set_date,mode,
//finance,remarks,blacksmith,dispatcher,driver,drive,customer,date
$result = mysqli_query($db, $sql);
if (!$result) {
    die("Connection failed: " . $db->connect_error);
} else {
    echo "created successfully";
}
mysqli_close($db);