#ifndef _TYPEINTERVAL_
#define _TYPEINTERVAL_

#include "Type.hpp"

/**
*	@brief Type d'un symbole.
*/
class TypeInterval : public Type {

	private:

		 int debut;
		 int fin;

	public:
		TypeInterval();
		~TypeInterval();
		
		TypeInterval(int _debut, int _fin);

		int getDebut();

		int getFin();

};

#endif
