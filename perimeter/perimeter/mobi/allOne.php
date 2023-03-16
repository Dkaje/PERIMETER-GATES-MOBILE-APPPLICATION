<?php
include("../database/config.php");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
   //id,quote_id,reg,category,type,description,image,quantity,price
    $quote_id = $_POST['quote_id'];
    $reg = $_POST['reg'];
    $category = $_POST['category'];
    $qty = $_POST['qty'];
    $type = $_POST['type'];
    $description = $_POST['description'];
    $quantity = $_POST['quantity'];
    $price = $_POST['price'];
    $image = $_POST['image'];
    $filename = "IMG" . rand() . ".jpg";
//quote_id,reg,category,type,description,image,quantity,price
if($quantity>$qty){
$response["success"] = 0;
            $response["message"] = " Your quantity is beyond the quoted quantity";
            echo json_encode($response);
}else{
$select = mysqli_query($con, "SELECT * from purchses where quote_id='$quote_id' and price='$price' and status='Pending'");
    if (mysqli_num_rows($select) > 0) {
        $cate = mysqli_query($con, "UPDATE purchses set description='$description', image='$filename',quantity=(quantity+$quantity) where quote_id='$quote_id' and price='$price' and status='Pending'");
        if ($cate) {
            mysqli_query($con, "UPDATE orders set quantity=(quantity-$quantity) where quote_id='$quote_id'");
            file_put_contents("images/" . $filename, base64_decode($image));
            $response["success"] = 1;
            $response["message"] = "  update was succesful";
            echo json_encode($response);
            mysqli_close($con);
        } else {
            $response["success"] = 0;
            $response["message"] = " Failed to update";
            echo json_encode($response);
            mysqli_close($con);
        }
    } else {
        $sql = "INSERT INTO purchses(quote_id,reg,category,type,description,image,quantity,price)
        VALUES('$quote_id','$reg','$category','$type','$description','$filename','$quantity','$price')";
        if (mysqli_query($con, $sql)) {
            mysqli_query($con, "UPDATE orders set quantity=(quantity-$quantity) where quote_id='$quote_id'");
            file_put_contents("images/" . $filename, base64_decode($image));
            $response["success"] = 1;
            $response["message"] = "  Product uploaded succesfully";
            echo json_encode($response);
            mysqli_close($con);
        } else {
            $response["success"] = 0;
            $response["message"] = "  Failed to upload product";
            echo json_encode($response);
            mysqli_close($con);
        }
    }
}

}