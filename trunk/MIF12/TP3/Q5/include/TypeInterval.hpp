#ifndef _TYPEINTERVAL_
#define _TYPEINTERVAL_

#include "Type.hpp"

/**
*	@brief Type d'un symbole.
*/
class TypeInterval : public Type {

	private:

		 int debut; /**< debut borne inférieure = où commence l'interval */
		 int fin; /**< fin borne supérieure = où se termine l'interval */

	public:
	
		/**
		* @brief Crée un type TypeInterval et stocke le nom "Interval"
		*/
		TypeInterval();

		/**
		* @brief Destructeur
		*/
		~TypeInterval();
		
		/**
		* @brief Crée un type TypeInterval, stock le nom "Interval", ainsi que la brone inférieure et supérieure
		* @param _debut borne inférieure de l'interval
		* @param _fin borne supérieure de l'interval
		*/
		TypeInterval(int _debut, int _fin);

		
		/**
		* @return retourne un entier qui représente le début de l'interval (borne inférieure)
		*/
		int getDebut();

		/**
		* @return retourne un entier qui représente la fin de l'interval (borne supérieure)
		*/
		int getFin();

};

#endif
