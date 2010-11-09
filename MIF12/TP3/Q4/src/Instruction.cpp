#include "Instruction.hpp"

using namespace std;

Instruction::Instruction(Operande* _operande1, Operande* _operande2, Operande* _operande3, Etiquette* _etiquette){

static int numInstruction = 0;

operande1 = _operande1;
operande2 = _operande2;
operande3 = _operande3;

etiquette = _etiquette;

numInstruction++;
numeroInstruction = numInstruction;

}

Instruction::~Instruction(){}

int Instruction::getNumInstruction(){ return numeroInstruction;}

Operande* Instruction::getOperande(int numOperande)
{
	switch (numOperande){
	case 1: { return operande1; }
	break;
	case 2: { return operande2; }
	break;
	case 3: { return operande3; }
	break;
	default:
	return NULL;
	break;
	}
}

Etiquette* Instruction::getEtiquette(){ return etiquette;}


void Instruction::setOperande(int numOperande, Operande* _operande)
{
	switch (numOperande){
	case 1: {  operande1 = _operande; }
	break;
	case 2: {  operande2 = _operande; }
	break;
	case 3: {  operande3 = _operande; }
	break;
	default:
	break;
	}
}

void Instruction::setEtiquette(Etiquette* _etiquette){ etiquette = _etiquette;}
