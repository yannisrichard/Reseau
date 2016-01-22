<?php
//Get
if (isset($_GET['phrase'])) {
	if (!empty($_GET['phrase'])) {
            //Compte le nombre de caractere
            $phrase = $_GET['phrase'];
            $phraseNoSpace = strtolower(str_replace(" ", "", $phrase));
            echo strlen($phraseNoSpace); 
	}
}

//POST
if (isset($_POST['phrase'])) {
	if (!empty($_POST['phrase'])) {
            //Compte la valeur d'une phrase a=1,b=2...z=26
            $val = 0;
            $phrase = $_POST['phrase'];
            $chars = str_split(strtolower(str_replace(" ", "", $phrase)));
            foreach($chars as $char){
                $val += ord($char)-96;
            } 
            echo $val;
	}
}