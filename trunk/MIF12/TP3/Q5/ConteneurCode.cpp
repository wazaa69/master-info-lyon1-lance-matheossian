#include "ConteneurCode.hpp"

using namespace std;

ConteneurCode::ConteneurCode(){

	nombreInstruction = 0;
}

ConteneurCode::~ConteneurCode(){}

void ConteneurCode::ajouterInstFinBlocCourant(Instruction* _instruction)
{
	if(_instruction != NULL){
	tabInstruction.push_back(_instruction);
	nombreInstruction++;
	}
}

/*
int blocCourant;
std::vector<BlocCode*> tabBlocs;

ajouterInstructionFinBlocCourant(instruction)
ajouterInstructionsFinblocCourant( std::vector<Instruction*> tabInstructions) OU
ajouterInstructionsFinblocCourant( blocCode) après avoir défini son bloc avec la liste d'instructions

std::vector<Instruction*> tabInstruction;
*/
