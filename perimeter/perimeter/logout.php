<?php
session_start();
unset($_SESSION['username']);
echo "<script>alert('You have Logged out Successfully')</script>";
echo "<script>location.href='log' </script>";