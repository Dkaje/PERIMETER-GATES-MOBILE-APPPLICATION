<?php
$timer=$_POST['timer'];
$date=date("Y-m-d");
if(date(strtotime($date))>(strtotime($timer))){
    $result["status"]=0;
    $result["message"]="Please select a valid date";
    echo json_encode($result);
  }elseif(date(strtotime($timer. ' - 10 days'))<(strtotime($date))){
    $result["status"]=0;
    $result["message"]="Please allow at least 10 days";
    echo json_encode($result);
  }else {
    $result["status"]=1;
    $result["message"]="Your Timing is good";
    echo json_encode($result);
  }?>