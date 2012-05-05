<?php
require_once('classes/sql.php');
$sql = new sql();

//name of the provider
$pid = $_GET['pid'];

//do the call and get the name
echo $sql -> getProviderName($pid);