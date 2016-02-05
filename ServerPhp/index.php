<?php
//Get
if (isset($_GET['phrase'])) {
	if (!empty($_GET['phrase'])) {
            //Compte le nombre de caractere
            $phrase = $_GET['phrase'];
            $phraseNoSpace = str_split(strtolower(str_replace(" ", "", $phrase)));
            echo "=>".count($phraseNoSpace); 
	}
}

//POST
if (isset($_POST['phrase'])) {
	if (!empty($_POST['phrase'])) {
            //Compte la valeur d'une phrase a=1,b=2...z=26
            $val = 0;
            $phrase = $_POST['phrase'];
            $chars = str_split(str_replace(" ", "", $phrase));
            foreach($chars as $char){
                $val += (ord(strtolower($char)));
            } 
			$decalage = ((count($chars)-3)*96) + 33; //décalage str_split
            
            echo $val - $decalage;

	}
}
