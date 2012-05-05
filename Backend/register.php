<?php
require_once('classes/users.php');
$users = new users();

//user info
$name = $_GET['name'];
$address = $_GET['address'];
$email = $_GET['email'];
$gender = $_GET['gender'];
$phone = $_GET['phone'];

//register the user
$users -> registerUser($name, $address, $email, $gender, $phone);