<?php
require_once('classes/sql.php');
$sql = new sql();

//user info
$name = $_GET['name'];
$address = $_GET['address'];
$email = $_GET['email'];
$gender = $_GET['gender'];
$phone = $_GET['phone'];

$sql ->registerUser($name, $address, $email, $gender, $phone);