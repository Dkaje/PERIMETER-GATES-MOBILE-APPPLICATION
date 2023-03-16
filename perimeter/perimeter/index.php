<?php
session_start();
error_reporting(0);
include('includes/config.php');
include 'includes/conn.php';
if (strlen($_SESSION['username']) == 0) {
    header('location:log');
} else {
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Perimeter Gates Bypass</title>
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    <link href="assets/css/style.css" rel="stylesheet" />
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body style="height: 100%;" class="bg-primary">
    <?php include('includes/header.php'); ?>
    <div class="content-wrapper">
        <div class="container">
            <div class="col-md-3 col-sm-3 col-xs-6">
                <div class="alert alert-info back-widget-set text-center" style="color:green">
                    <i class="fa fa-shopping-cart fa-5x"></i><i class="fa fa-cart fa-5x"></i>
                    <h3><span class=""><b id="" style="color: #000;font: size 20px;"><?php echo mysqli_num_rows(mysqli_query($con,"SELECT * from customer where status='0'")); ?></b></span></h3>
                    New Customers
                </div> 
            </div>

            <div class="col-md-3 col-sm-3 col-xs-6">
                <div class="alert alert-warning back-widget-set text-center" style="color:blue">
                    <i class="fa fa-car fa-5x"></i>
                    <h3><span class="text-muted"><b id="" style="color: #000;font: size 20px;"><?php echo mysqli_num_rows(mysqli_query($con,"SELECT * from driver where status='0'")); ?></b></span></h3>
                    New Drivers
                </div>
            </div>
            <div class="col-md-3 col-sm-3 col-xs-6">
                <div class="alert alert-warning back-widget-set text-center" style="color:white">
                    <i class="fa fa-truck fa-5x"></i>
                    <h3><span class="text-muted"><b id="" style="color: #000;font: size 20px;"><?php echo mysqli_num_rows(mysqli_query($con,"SELECT * from supplier where status='0'")); ?></b></span></h3>
                    New Suppliers
                </div>
            </div>
            <div class="col-md-3 col-sm-3 col-xs-6">
                <div class="alert alert-warning back-widget-set text-center" style="color:red">
                    <i class="fa fa-group fa-5x"></i>
                    <h3><span class="text-muted"><b id="" style="color: #000;font: size 20px;"><?php echo mysqli_num_rows(mysqli_query($con,"SELECT * from staff where status='0'")); ?></b></span></h3>
                    New Staff
                </div>
            </div>
            <div class="col-md-3 col-sm-3 col-xs-6">
                <div class="alert alert-info back-widget-set text-center" style="color:black">
                    <i class="fa fa-star checked fa-5x"></i>
                    <h3><span class="text-muted"><b id="" style="color: #fff;font: size 20px;"><?php echo mysqli_num_rows(mysqli_query($con,"SELECT * from feedback")); ?></b></span></h3>
                    Ratings
                </div>
            </div>
        </div>
    </div>
    </div>
    <script src="assets/js/jquery-1.10.2.js"></script>
    <script src="assets/js/bootstrap.js"></script>
    <script src="assets/js/custom.js"></script>
    <script type="text/javascript">
    function loadDoc() {
        setInterval(function() {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("cust_no").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "test_count", true);
            xhttp.send();
        }, 200);

    }
    loadDoc();
    </script>
    <script type="text/javascript">
    function loadDoc() {
        setInterval(function() {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("feed").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "count_rate", true);
            xhttp.send();
        }, 200);

    }
    loadDoc();
    </script>
    <script type="text/javascript">
    function loadDoc() {
        setInterval(function() {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("sta").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "staff_count", true);
            xhttp.send();
        }, 200);

    }
    loadDoc();
    </script>
    <script type="text/javascript">
    function loadDoc() {
        setInterval(function() {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("sup").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "supp_count", true);
            xhttp.send();
        }, 200);

    }
    loadDoc();
    </script>
    <script type="text/javascript">
    function loadDoc() {
        setInterval(function() {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("driv").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "drive_count", true);
            xhttp.send();
        }, 200);

    }
    loadDoc();
    </script>
</body>

</html>
<?php } ?>