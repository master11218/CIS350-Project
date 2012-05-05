<?php
require_once('classes/sql.php');
$sql = new sql();

//name of the provider
$pid = $_GET['pid'];

echo $sql -> getProviderName($pid);