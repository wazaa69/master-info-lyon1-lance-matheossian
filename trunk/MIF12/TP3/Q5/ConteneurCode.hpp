#ifndef _CONTENEURCODE_
#define _CONTENEURCODE_

#include <iostream>
#include <vector>
#include "Instruction.hpp"

 class ConteneurCode {

	private:	
	
	int nombreInstruction;

	std::vector<Instruction*> tabInstruction;

	public:

	ConteneurCode();
	~ConteneurCode();

	void ajouterInstFinBlocCourant(Instruction* _instruction);

};

#endif


