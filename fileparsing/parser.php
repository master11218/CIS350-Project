iwjefoij


<?php

$myFile = "2.txt";

$s = file_get_contents($myFile);

//echo $s;


//get the provider name
$regexp = '/<td class=""provider_name_rd"">[^<]*<br>/';

$storage = Array();

$results = preg_match($regexp, $s, $storage);
echo '<br>'.$storage[0];



//get the address and metadata
$regexpAddr1 = '/<b>Address: <\/b><br>[^<]*<br>[^<]*<br>[^<]*<br>/';

$results = preg_match($regexpAddr1, $s, $storage);
echo '<br>'.$storage[0];




echo '<br>'.$results;
?>

