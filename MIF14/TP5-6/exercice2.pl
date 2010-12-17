/* http://pcaboche.developpez.com/article/prolog/presentation/?page=page_5
Déclaration des prédicats dynamiques pour retractall et assert */
:-dynamic vrai/1, faux/1, si/1, alors/1.

/* On efface l'ensemble des faits */
nettoie :- 
	retractall(vrai(_)), 
	retractall(faux(_)).
	
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


/*  Exercice 2 - Moteur en chaînage arrière

Définition de la base de règles.  */
test_regle(Regle, Faits, Conclusions) :- clause(regle(Regle), (si(Faits), alors(Conclusions))).


/* Définition d'un prédicat permettant à l’utilisateur d’initialiser la base de faits
Exemple : c'est ce que l'on a en main !
faits([fleur,graine,un_cotyledone,joli]).
*/
faits(N) :- nettoie, init_faits(N).

/* Le prédicat assert est utilisé pour ajouter vrai(Fait) pour les faits positifs et faux(Fait) pour les faits négatifs.
Exemple : non(rhizome), X = rhizome => doit être marqué comme faux(X) */
init_faits([TeteDeFait|ResteDesFaits]) :- assert(vrai(TeteDeFait)), init_faits(ResteDesFaits), !.
init_faits([non(TeteDeFait)|ResteDesFaits]) :- assert(faux(TeteDeFait)), init_faits(ResteDesFaits), !.
init_faits([]) :- !.



/* CAS D'ARRET : il ne reste plus de fait à relier à une conclusion */
moteur([], ReglesEtBases, ElemNecessNonTrouve, Resultat, ResultatElemNonTrouve):- 
	append([], ElemNecessNonTrouve, ResultatElemNonTrouve),
	append([], ReglesEtBases, Resultat).

	
/* CAS 1 : si il existe une conclusion pour l'élément recherché */
moteur([X|Q], ReglesEtBases, ElemNecessNonTrouve, Resultat, ResultatElemNonTrouve) :- 
        test_regle(Regle, Faits, [X]), /* on récupère la conclusion et les faits */
        reverse(Faits, Tmp), /* un reverse pour récupérer correctement les faits par rapport à l'exemple de l'énoncé */
        append(Q, Tmp, L2), /* calcul de L2 nécéssaire pour la suite de l'analyse = liste des faits restants + les nouveaux */
        append(ReglesEtBases, [regle(X, Regle)], L3), /* L3 contient les résultats successifs, on ajoute la règle utilisée pour en déduire les nouveaux faits */
        moteur(L2, L3, ElemNecessNonTrouve, Resultat, ResultatElemNonTrouve), !. /* Tant que L2 contient des faits, on continu de chercher des conclusions qui peuvent donner ces faits (en parcourant L2) */

		
/* CAS 2 : Si X (la première "conclusion" de L2) n'est reliée à aucun fait, et qu'elle appartient déjà aux faits de base, on dit que c'est une "base". */		
moteur([X|Q], ReglesEtBases, ElemNecessNonTrouve, Resultat, ResultatElemNonTrouve) :-
		vrai(X), /* bien dans la base de faits */
		delete(ElemNecessNonTrouve, X, ElemNecessNonTrouveMoinsUn), /* on supprime de la liste des éléments nécéssaires */
        append(ReglesEtBases, [base(X)], L2),  /* ajout de la "base" */
        moteur(Q, L2, ElemNecessNonTrouveMoinsUn, Resultat, ResultatElemNonTrouve).

		
/* CAS 3 : Si X n'est pas une conclusion et n'existe pas dans la base de faits */
moteur([X|Q], ReglesEtBases, ElemNecessNonTrouve, Resultat, ResultatElemNonTrouve) :-
		append(ElemNecessNonTrouve, [X], ElemNecessNonTrouvePlusUn), /* on ajoute un élément à la liste de ceux nécessaires */
        moteur(Q, L2, ElemNecessNonTrouvePlusUn, Resultat, ResultatElemNonTrouve).


		
/* On affiche la façon de trouver les faits et l'enregistrement de règle selon l'exemple proposé*/
affichage([base(X)|Q]):- write(X), write(' dans la base de faits'), nl, affichage(Q).
affichage([regle(X, N)|Q]):- write(X), write(' satisfait grace a '), write(N), nl, affichage(Q).
affichage([]):-!.


/* Pour lancer le programme */
satisfait(X) :-
        moteur([X], [], ElemNecessNonTrouve, Resultat, ResultatElemNonTrouve),
        reverse(Resultat, Aff),
        affichage(Aff), 
		/* nl, write('Liste des éléments non trouvés pour former '), write(X), write(' : '), nl,
		afficher(ResultatElemNonTrouve),nl, */
		!, /* pas de retour */
		length(ResultatElemNonTrouve, Taille),
		Taille == 0. /* tous les éléments ont été trouvés */
		
		
/* En plus : Affichage ligne par ligne des éléments d'une liste */	
afficherLigne(X,[]):- write(X), nl.
afficherLigne(X, [T|Q]):- write(X), nl, afficherLigne(T,Q).
afficher([T|Q]):- afficherLigne(T,Q).