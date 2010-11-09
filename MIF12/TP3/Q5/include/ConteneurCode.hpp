#ifndef _CONTENEURCODE_
#define _CONTENEURCODE_

#include <iostream>
#include <vector>
#include "Instruction.hpp"

 class ConteneurCode {

	private:	
	
	int nombreInstructions;

	std::vector<Instruction*> tabInstruction;

	public:

	ConteneurCode();
	~ConteneurCode();

	void ajouterInstFinBlocCourant(Instruction* _instruction);

	void ajouterLesInstrFinBlocCourant(std::vector <Instruction*> _tabInstructions);

	void affichageCode3AD();

};

#endif


