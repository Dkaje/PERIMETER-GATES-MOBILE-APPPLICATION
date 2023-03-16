<?php
include('sessionadm.php');
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
            <div class="row pad-botm">
                <div class="col-md-12">
                    <h5>
                        <a href="messages"> <button class="btn btn-wraning"> Customer Ratings</button></a>
                      </h5>
                </div>


            </div>
            <div class="row" style="color: #000;">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>customer</th>
                                            <th>phone</th>
                                            <th>name </th>
                                            <th>rating</th>
                                            <th>message</th>
                                            <th>reg_date</th>
                                            <th>reply</th>
                                            <th>reply_date</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php $sql = "SELECT * from feedback";
                                            $query = $con->prepare($sql);
                                            $query->execute();
                                            $results = $query->fetchAll(PDO::FETCH_OBJ);
                                            $cnt = 1;
                                            if ($query->rowCount() > 0) {
                                                foreach ($results as $result) {               ?>
                                        <tr class="odd gradeX">
                                            <td class="center"><?php echo htmlentities($cnt); ?></td>
                                            <td class="center"><?php echo htmlentities($result->customer); ?></td>
                                            <td class="center"><?php echo htmlentities($result->phone); ?></td>
                                            <td class="center"><?php echo htmlentities($result->name); ?></td>
                                            <td class="center"><?php echo htmlentities($result->rating); ?></td>
                                            <td class="center"><?php echo htmlentities($result->message); ?></td>
                                            <td class="center"><?php echo htmlentities($result->reg_date); ?></td>
                                            <td class="center"><?php echo htmlentities($result->reply); ?></td>
                                            <td class="center"><?php echo htmlentities($result->reply_date); ?></td>
                                        
                                        </tr>
                                        <?php $cnt = $cnt + 1;
                                                }
                                            } ?>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                </div>
            </div>



        </div>
    </div>
    <script src="assets/js/jquery-1.10.2.js"></script>
    <script src="assets/js/bootstrap.js"></script>
    <script src="assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="assets/js/dataTables/dataTables.bootstrap.js"></script>
    <script src="assets/js/custom.js"></script>
</body>

</html>
<?php } ?>