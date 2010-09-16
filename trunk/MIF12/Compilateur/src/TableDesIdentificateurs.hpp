#ifndef _TableDesIdentificateurs_
#define _TableDesIdentificateurs_

#include <vector>
#include <string>

class TableDesIdentificateurs {

	private :
		std::vector<std::string> identificateurs;

	public:
		TableDesIdentificateurs();
		~TableDesIdentificateurs();

		void ajouter(char* elem);
		void afficherTable();

};

#endif _TableDesIdentificateurs_
