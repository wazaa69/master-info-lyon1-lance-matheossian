#ifndef _CONTENEURCODE_
#define _CONTENEURCODE_

#include <iostream>
#include <vector>
#include "Instruction.hpp"

 class ConteneurCode {

	private:	
	
	int nombreInstructions;/** @var nombreInstructions nombre d'instructions présentes dans le bloc de code */

	std::vector<Instruction*> tabInstruction; /** @var tabInstruction tableau d'instructions */

	public:
	
	/** 
	* @brief constructeur de ConteneurCode
	*/
	ConteneurCode();

	/** 
	* @brief destructeur de ConteneurCode 
	*/
	~ConteneurCode();
	
	/**
	* @brief ajoute une instruction à la fin du bloc courant
	* @param _instruction l'instruction à ajouter
	*/
	void ajouterInstFinBlocCourant(Instruction* _instruction);

	/**
	* @brief ajoute une suite d'instructions à la fin du bloc courant
	* @param _tabInstructions le tableau d'instructions à ajouter
	*/
	void ajouterLesInstrFinBlocCourant(std::vector <Instruction*> _tabInstructions);

	/**
	* @brief affiche le code 3@
	*/
	void affichageCode3AD();

	/**
	* @brief convertit l'entier en paramètre au string contenant l'opération correspondante
	* @param _operation l'entier contenant le numéro de l'opération
	*/
	std::string convOperation(int _operation);

	/**
	* @brief permet de récupérer la dernière affectation effectuée sur la variable en paramètre
	* @param nomVariable string contenant le nom de la variable
	* @return Retourne une opérande
	*/
	Operande* getDerniereAffectationVariable(std::string nomVariable);
};

#endif


