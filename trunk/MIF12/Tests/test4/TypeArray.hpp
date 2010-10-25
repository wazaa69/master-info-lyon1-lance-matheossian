#ifndef _TYPEARRAY_
#define _TYPEARRAY_

#include "Type.hpp"

/**
*	@brief Type d'un symbole.
*/
class TypeArray : public Type {

	private:


	public:
		TypeArray();
		~TypeArray();
		
		//TypeArray(TypeInterval* taille);
		TypeArray(int taille);
		

};

#endif
