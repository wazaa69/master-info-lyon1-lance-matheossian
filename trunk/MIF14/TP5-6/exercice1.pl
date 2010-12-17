/* http://pcaboche.developpez.com/article/prolog/presentation/?page=page_5
Si vous essayez de faire appel à un prédicat qui n'existe pas (ou de modifier les clauses d'un prédicat non dynamique),
Prolog retournera une erreur. Il est donc necessaire de déclarer au préalable les prédicats dynamiques */
:-dynamic vrai/1, faux/1, si/1, alors/1, non/1, marque/1, continuer/0.

/* On nettoie l'ensemble des faits */
nettoie :- 
	retractall(faux(_)), 
	retractall(vrai(_)), 
	retractall(marque(_)), 
	retractall(continuer).
	
/* Liste des règles */
regle(r1) :- si([fleur,graine]), alors([phanerogame]).
regle(r2) :- si([phanerogame,graine_nue]), alors([sapin,ombre]).
regle(r3) :- si([phanerogame,un_cotyledone]), alors([monocotyledone]).
regle(r4) :- si([phanerogame,deux_cotyledone]), alors([dicotyledone]).
regle(r5) :- si([monocotyledone,rhizome]), alors([muguet]).
regle(r6) :- si([dicotyledone]), alors([anemone]).
regle(r15) :- si([joli]), alors([non(rhizome)]).
regle(r7) :- si([monocotyledone,non(rhizome)]), alors([lilas]).
regle(r8) :- si([feuille, non(fleur)]), alors([cryptogame]).
regle(r9) :- si([cryptogame,non(racine)]), alors([mousse]).
regle(r10) :- si([cryptogame,racine]), alors([fougere]).
regle(r11) :- si([non(feuille),plante]), alors([thallophyte]).
regle(r12) :- si([thallophyte,chlorophylle]), alors([algue]).
regle(r13) :- si([thallophyte,non(chlorophylle)]), alors([champignon,non(bon)]).
regle(r14) :- si([non(feuille),non(fleur),non(plante)]), alors([colibacille]).


/*  Exercice 1 - Moteur en chaînage avant

Définition de la base de règles.  */
test_regle(Regle, Fait, Conclusion) :- clause(regle(Regle), (si(Fait), alors(Conclusion))).


/* Définition d'un prédicat permettant à l’utilisateur d’initialiser la base de faits
Exemple :
faits([fleur, graine, un_cotyledone, non(rhizome)]).
*/
faits(N) :- nettoie, init_faits(N).

/* Le prédicat assert est utilisé pour ajouter vrai(Fait) pour les faits positifs et faux(Fait) pour les faits négatifs.
Exemple : non(rhizome), X = rhizome => doit être marqué comme faux(X) */
init_faits([TeteDeFait|ResteDesFaits]) :- assert(vrai(TeteDeFait)), init_faits(ResteDesFaits), !.
init_faits([non(TeteDeFait)|ResteDesFaits]) :- assert(faux(TeteDeFait)), init_faits(ResteDesFaits), !.
init_faits([]) :- !.


/* Prédicat qui sature la base de règles (Moteur d’inférence (3)) */
moteur(Regle):-
	test_regle(Regle, Fait, Conclusion),
	not(marque(Regle)), /* si la règle n'est pas déjà marquée, on continue */
	dans_nosFaits(Fait),
	init_faits(Conclusion),
	assert(continuer), /* ajoute la clause en fin de prédicat */
	assert(marque(Regle)), /* on marque la règle */
	write(Regle),nl, /* et on l'affiche */
	affiche,nl. /* puis on affiche la liste des faits */

	
/*  On va vérifier chacun des fait en fonction de ceux que l'on doit évaluer */
dans_nosFaits([TeteDeFait|ResteDesFaits]):- vrai(TeteDeFait), dans_nosFaits(ResteDesFaits).
dans_nosFaits([non(TeteDeFait)|ResteDesFaits]):- faux(TeteDeFait), dans_nosFaits(ResteDesFaits).
dans_nosFaits([]):-!.
	
	
/* Trace du fonctionnement du prédicat qui sature la base de règles */
affiche:- affiche_vrai, affiche_faux.

affiche_vrai:- vrai(Fait), write(Fait), write(' '), fail.
affiche_vrai.

affiche_faux:- faux(Fait), write('non('), write(Fait), write(') '), fail.
affiche_faux.
	
	
saturer:- moteur(_), fail.
saturer:- continuer, retractall(continuer), saturer, !.
saturer:- not(continuer).

nettoie.