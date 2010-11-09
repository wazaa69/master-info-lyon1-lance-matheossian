#include "ConteneurCode.hpp"

using namespace std;

ConteneurCode::ConteneurCode(){

	nombreInstructions = 0;
	
}

ConteneurCode::~ConteneurCode(){}

void ConteneurCode::ajouterInstFinBlocCourant(Instruction* _instruction)
{
	

	//if(_instruction != NULL){
	
	tabInstruction.push_back(_instruction);
	nombreInstructions++;

	//}

}

void ConteneurCode::ajouterLesInstrFinBlocCourant(vector <Instruction*> _tabInstructions)
{
	for (unsigned int i = 0; i < _tabInstructions.size() ; i++)
	{
		tabInstruction.push_back(_tabInstructions[i]);
		nombreInstructions++;
	}
}


void ConteneurCode::affichageCode3AD()
{
	string temp = "";
	
	cout << "Code 3@" << endl;

	for (unsigned int i = 0 ; i < tabInstruction.size(); i++)
	{
		if ( (tabInstruction[i]->getOperande(1) != NULL) && (tabInstruction[i]->getOperande(2) != NULL) && (tabInstruction[i]->getOperande(3) != NULL) ){
		cout << tabInstruction[i]->getOperande(1)->getValInteger() << " := "<< tabInstruction[i]->getOperande(2)->getValInteger() << " " << *tabInstruction[i]->getOperation()  << " " << tabInstruction[i]->getOperande(3)->getValInteger()  << endl;
		}
	}

}
