#ifndef _INSTRUCTION_
#define _INSTRUCTION_

#include <iostream>
#include "Operande.hpp"
#include "Etiquette.hpp"

 class Instruction {

	private:	
	
	Operande* operande1;
	Operande* operande2;
	Operande* operande3;

	Etiquette* etiquette;

	int numeroInstruction;

	public:

	Instruction(Operande* _operande1, Operande* _operande2, Operande* _operande3, Etiquette* _etiquette);
	~Instruction();

	int getNumInstruction();

	Operande* getOperande(int numOperande);

	Etiquette* getEtiquette();

	void setOperande(int numOperande, Operande* _operande);

	void setEtiquette(Etiquette* _etiquette);
};

#endif


