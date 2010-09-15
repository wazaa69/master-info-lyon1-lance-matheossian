var aCreer = 1;

//Création de l'échiquié
function initEchec(){

	if(aCreer){

		var html='<table id="echiquier"><thead><tr><th></th>';
		var thead="";
		var content="";
		
		var lettres =["A","B","C","D","E","F","G","H"];
		
		var id=0;
		
		for(var i=7; i >= 0; i--){
			
			//génération des headers
			thead+='<th>'+ lettres[8-(i+1)] + '</th>';
			
			content+='<tr><td class="border-right">'+ (i+1) + '</td>';

			for(var j=7; j >= 0; j--){
					
					id = i * 8 + j; //calcul de l'id
					
					//condition pour ajouter des bordures
					if ((j == 0)&&(i == 0))content+='<td id="' + id + '" class="border-bottom border-right"></td>';
					else if ((j != 0)&&(i == 0)) content+='<td id="' + id + '" class="border-bottom"></td>';
					else if (j == 0) content+='<td id="' + id + '" class="border-right"></td>';
					else content+='<td id="' + id + '"></td>';
	
			}
			
			content+='</tr>';
		}
		
		html+=thead + '</tr></thead>';
		html+='<tbody>'+content+'</tbody></table>';
		
		document.getElementById('plateau').innerHTML += html;
		
		colorerCasesEtPlacerPions();
		
		redimensionnerTableau();
		
		aCreer=0;
		
	
	}
	
	else{alert("Echiquier déjà créé !");}
}

//Seulement après avoir créé le damier, on met les cases grises
function colorerCasesEtPlacerPions(){

	dossierImages='images/';
	var pieces =["Tour", "Canasson", "Fou", "Dame", "Roi", "Fou", "Canasson", "Tour"];
	var nomPiece = "";
	var chemin ="";
								
	var id=0;
	var color=1;

	for(var i=7; i >= 0; i--){
	
		for(var j=7; j >= 0; j--){

			id=i*8+j;
			
			nomPiece = pieces[7-j];
			chemin = dossierImages + nomPiece;
			
			if (i == 7) document.getElementById(id).innerHTML = '<img id="i'+ id +'" src="' + chemin + '_noir.png' + '" alt="' + nomPiece + '"/>';
			else if (i == 6) document.getElementById(id).innerHTML = '<img id="i'+ id +'" src="' + dossierImages + 'Pion_noir.png' + '" alt="' + nomPiece + '"/>';
			else if (i == 1) document.getElementById(id).innerHTML = '<img id="i'+ id +'" src="' + dossierImages + 'Pion_blanc.png' + '" alt="' + nomPiece + '"/>';
			else if (i == 0) document.getElementById(id).innerHTML = '<img id="i'+ id +'" src="' + chemin + '_blanc.png' + '" alt="' + nomPiece + '"/>';
			
			if(color%2 == 0) document.getElementById(id).style.backgroundColor = "#CCCCCC";
			
			color++;
			
		}
		
		color++;
	}
}

//Quand on redimenssione la fenêtre
window.onresize = function(event) {redimensionnerTableau();}

//Redimenssione l'échiquier en fonction de la taille de la fenêtre (-100 pour la première phrase)
function redimensionnerTableau(){

	var mesure = window.innerHeight-100; //taille de la fenêtre
	var mesureCase = mesure/9; //taille d'une cellule
	
	//modification de la taille du tableau
	document.getElementById('echiquier').style.height=mesure+'px';
	document.getElementById('echiquier').style.width=mesure+'px';
	
	
	for(var i=0; i < 64; i++){
	
		//modification de la taille des cellules
		document.getElementById(i).style.height = mesureCase + 'px';
		document.getElementById(i).style.width = mesureCase + 'px';
	
		//modification de la taille des images
		if(document.getElementById('i'+i)){
			document.getElementById('i'+i).style.height = mesureCase + 'px';
			document.getElementById('i'+i).style.width = mesureCase + 'px';
		}
		
	}
}