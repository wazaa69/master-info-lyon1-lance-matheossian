#ifndef _TableDesIdentificateurs_
#define _TableDesIdentificateurs_

#include <vector>
#include <string>

class TableDesIdentificateurs {

	private :
		std::vector<std::string> tableId;

	public:
		TableDesIdentificateurs();
		~TableDesIdentificateurs();

		int ajouter(std::string elem);
		int TableDesIdentificateurs::getId(string elem);

		void afficherTable(int nbID);
};

#endif /* _TableDesIdentificateurs_ */
