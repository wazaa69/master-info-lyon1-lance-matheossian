#ifndef _INSTRUCTION_
#define _INSTRUCTION_

#include <iostream>
#include "Operande.hpp"
#include "Etiquette.hpp"
#include <string>


 class Instruction {

	private:	
	
	Operande* operande1; /** première opérande de l'instruction */
	Operande* operande2; /** deuxième opérande de l'instruction */
	Operande* operande3; /** troisième opérande de l'instruction */

	Etiquette* etiquette; /** étiquette de l'instruction */

	int numeroInstruction; /** numéro correspondant à l'instruction */
	int operation; /** numéro correspondant à l'opération effectuée dans l'instruction */

	public:

	/**
	* @brief Constructeur de l'instruction
	* @param _operande1 première opérande de l'instruction 
	* @param _operande1 deuxième opérande de l'instruction 
	* @param _operande1 troisième opérande de l'instruction
	* @param _etiquette étiquette de l'instruction 
	*/
	Instruction(Operande* _operande1, Operande* _operande2, Operande* _operande3, int _operation,  Etiquette* _etiquette);

	/**
	* @brief Destructeur de l'instruction
	*/
	~Instruction();

	/**
	* @brief Accesseur
	* @return entier contenant le numéro d'instruction
	*/
	int getNumInstruction();

	/**
	* @brief Accesseur
	* @param numOperande entier correspondant au numéro de l'opérande dans l'instruction
	* @return opérandeX avec X numéro de l'opérande
	*/
	Operande* getOperande(int numOperande);

	/**
	* @brief Accesseur
	* @return etiquette 
	*/
	Etiquette* getEtiquette();
	
	/**
	* @brief Accesseur
	* @return string operation 
	*/
	std::string* getOperation();

	/**
	* @brief Mutateur définit une opérande
	* @param numOperande entier correspondant au numéro de l'opérande dans l'instruction
	* @param _operande opérande
	*/
	void setOperande(int numOperande, Operande* _operande);

	/**
	* @brief Mutateur
	* @param _etiquette etiquette
	*/
	void setEtiquette(Etiquette* _etiquette);
};

#endif


