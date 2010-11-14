#ifndef _FACTORY_
#define _FACTORY_

#include <iostream>
#include "TDS.hpp"
#include "TDI.hpp"
#include "Etiquette.hpp"
#include "Temporaire.hpp"

 class Factory {

	public:
	
	/**
        *   @brief Constructeur de Factory
        */
	Factory();

	/**
        *   @brief Destructeur de Factory
        */
	~Factory();

	/**
	* @brief ajoute un temporaire dans la TDI et dans la TDS
	* @param _TDI TableDesIdentificateurs dans laquelle le temporaire sera ajouté
	* @param _TDS TableDesSymboles dans laquelle le temporaire sera ajouté
	* @param _nomTemporaire string contenant le nom du temporaire
	* @param _type type contenant le type du temporaire
	*/	
	static int ajouterTemporaire(TableDesIdentificateurs* _TDI, TableDesSymboles* _TDS, std::string* _nomTemporaire, Type* _type);
	
	/**
	* @brief ajoute une étiquette dans la TDI et dans la TDS
	* @param _TDI TableDesIdentificateurs dans laquelle l'étiquette sera ajoutée
	* @param _TDS TableDesSymboles dans laquelle l'étiquette sera ajoutée
	* @param _nomTemporaire string contenant le nom de l'étiquette
	*/	
	static int ajouterEtiquette(TableDesIdentificateurs* _TDI, TableDesSymboles* _TDS, std::string* _nomEtiquette);
	
	


};

#endif
