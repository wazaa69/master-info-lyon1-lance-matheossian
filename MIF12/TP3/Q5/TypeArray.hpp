#ifndef _TYPEARRAY_
#define _TYPEARRAY_

#include "Type.hpp"
#include "TypeInterval.hpp"

/**
*	@brief Type d'un symbole.
*/
class TypeArray : public Type {

	private:
	
		TypeInterval* intervalArray;
		Type* typeArray;
	
	public:
		TypeArray();

		TypeArray(TypeArray* _ta);
		TypeArray(TypeInterval* _interval, Type* _type);
		~TypeArray();

		Type* getTypeTab();
		TypeInterval* getInterval();
		
		
		//TypeArray(int taille);
		

};

#endif
