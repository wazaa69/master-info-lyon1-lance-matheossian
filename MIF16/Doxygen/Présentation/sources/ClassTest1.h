
/*!
*
*
 * \class ClassTest1
 *
 * \brief Classe d'exemple test1
 * \brief Code d'exemple pour presenter l'utilisation de doxygen
 * dans un code de classe C++.
 *
 * \author Florian LANCE <lance.florian@gmail.com>
 * \author Dimitri MATHEOSSIAN <anima876@htomail.fr>
 * \see http://www.stack.nl/~dimitri/doxygen/manual.html
 *
 * \note Partie libre qui peut accueillir n'importe quoi ou bien une licence.
 *
 * \since 2010/09/20
 * \version 2010/09/27
 *
 */

/*! \mainpage Page d'introduction du programme d'exemple d'utilisation de Doxygen
 *
 *
 * Ceci est l'introduction du document.
 * <img src="http://www.bo.infn.it/~medinace/figures/doxygen.png">
 */


#ifndef CLASSTEST1_H
#define CLASSTEST1_H



/** \brief Voici une macro. */
#define MACRO_1 11

/** \brief Voici une deuxième macro. */
/** \param <MACRO_2> { Macro numéro 2} */
#define MACRO_2 56

/* Inclusion de la classe ClassTest2.h, ce commentaire n'est pas genere.*/
#include "ClassTest2.h"

class ClassTest1
{
    public:

        /*! \brief Constructeur par défaut. */
        ClassTest1();

        /*!  
        * \pre arg1 non nul 
        * \post Construit un objet de type ClassTest1. 
        * \param [in] <arg1> Argument utilisé lors de l'initialisation d'un attribut de la classe.*/ 
        ClassTest1(int arg1);

        /*! \brief Destructeur par défaut. */
        virtual ~ClassTest1();

        /*!
 	    * \return Retourne le résultat de la division de MACRO_1 et MACRO_2
 	    * \retval MACRO_1/MACRO_2 valeur de retour
    	* \throw Exception Retourne une exception si MACRO_2 est nul
 	    */
        float testDivision();

    private:

        /*! \brief Constante de classe. */
        static const int RESULTAT = MACRO_1 + MACRO_2;

        /*! Une variable privée. */
        int attribut1;

        /*! \brief Une énumération de trois valeurs sur une échelle logarithmique 10. */
        enum enum1 {
            value1 = 1, /*!< Première valeur de l'énum (1) */
            value2 = 2, /*!< Deuxième valeur de l'énum (2) */
            value3 = 3,  /*!< Troisième valeur de l'énum (3) */
            value4 = 4  /*!< Quatrième valeur de l'énum (4) */
        };


        /*!\brief Une structure de données .*/
        struct struct1 {
            /*! \brief Premier attribut de la structure. */
            int attr1;
            /*! \brief Second attribut de la structure. */
            char attr2;
        };

			ClassTest2 * test2; /*!< \brief pointeur vers instance de test2. */
};

#endif // CLASSTEST1_H
