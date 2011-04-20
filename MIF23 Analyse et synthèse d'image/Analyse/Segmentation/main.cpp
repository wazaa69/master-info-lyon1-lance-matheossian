#include <opencv/cv.h>
#include <opencv/highgui.h>
// -lcv -lhighgui -lcvaux -lml -lcxcore
#include <vector>
#include <string>

#include "include/Histogramme.h"

#include "include/Accroissement.h"
#include "include/Graine.h"


int theFlag = 0;
CvPoint* tmp = (CvPoint*) malloc(sizeof( CvPoint ) );
void onLClick(int event, int x, int y, int flags, void* param);
void onLClick(int event, int x, int y, int flags, void* param){
    theFlag = flags;
    tmp->x = x;
    tmp->y = y;
}

void afficherImage(const string nomImage, const IplImage* img);
string demanderImage();
double demanderSeuil(const IplImage* img_src);

/**
* @brief Disposition manuelle des graines (les coordonnées seront visibles dans la console).
* Il faut :
* 1- focus la fenêtre de l'image source
* 2- placer la souris sur le point désiré
* 3- appuyer sur la lettre 't' pour sauvgarder la graine
* 4- réitérer le procédé autant de fois que vous le voullez
*/
void demanderDispoGraine(const IplImage* img_src, std::vector<Graine>* graines);

/**
* @brief Dispose automatiquement les graines.
* On divise la largeur et hauteur de l'image par "decoupe" pour obtenir un pasX et pasY.
* Si un des deux pas est trop petit, il est fixé à 10.
*/
void dispositionAutomatique(const unsigned int largeur, const unsigned int hauteur, vector<Graine>* graines, const unsigned int decoupe);

/**
* @brief Dispose aléatoirement les graines.
*
*/
void dispositionAleatoire(const unsigned int largeur, const unsigned int hauteur, vector<Graine>* graines, const unsigned int nombre);


int main()
{
    string nomImage, cheminImg, cheminImage, rep;
    string nomFenetreSrc  = "Image Source";
    string nomFenetreSeg  = "Image Segmentée";

    IplImage* redimImgSrc;
    IplImage* redimImgSeg;

    cvNamedWindow(nomFenetreSrc.c_str(), CV_WINDOW_AUTOSIZE);
    cvResizeWindow(nomFenetreSrc.c_str(), 1,1);
    cvMoveWindow(nomFenetreSrc.c_str(),10,0);

    cvNamedWindow(nomFenetreSeg.c_str(), CV_WINDOW_AUTOSIZE);
    cvResizeWindow(nomFenetreSeg.c_str(), 1,1);
    cvMoveWindow(nomFenetreSeg.c_str(),665,0);

    while (rep.compare("q")){

        cheminImg = "images/";

        bool bonChemin = 0;
        IplImage* img_src;

        //------------------------ Demande l'image à segmenter ------>

        while (!bonChemin)
        {
            cheminImage = cheminImg;
            cheminImage += demanderImage().c_str();

            img_src = cvLoadImage(cheminImage.c_str());
            if(img_src == NULL){cout << "Mauvais chemin d'image !" << endl;}
            else {bonChemin = true;}
        }

        //--------- Affichage ------>

        afficherImage(nomFenetreSrc, img_src);

        //------------------------ Crée et récuère les graines, modifie le seuil et le % d'occupation ------>

        double occupationMin = 70; /* taux d'occupation minimal des régions */
        std::vector<Graine>* graines = new std::vector<Graine>;
        double seuil = demanderSeuil(img_src);
        demanderDispoGraine(img_src, graines);

        //------------------------ Lance l'algorithme si il y a des graines ------>

        if(graines->size())
        {

            //--------- Accroissement  ---->

            Accroissement acc(img_src, seuil, occupationMin);
            acc.demarrer(*graines);

            //--------- Affichage  ------>

            afficherImage(nomFenetreSeg, acc.getImgSeg());

            //------------------------ Sauvegarde de l'image segmentée ----------->

            cvSaveImage("images/resultat.jpg",acc.getImgSeg());
            cout << "Image segmentee sauvegardee: images/resultat.jpg" << endl;

            rep.clear();
            cout << "(q) pour pour quitter, (s) pour segmenter une nouvelle image: " << endl;
            while(rep.compare("q") && rep.compare("s"))  cin >> rep;

        }

        delete graines;
        cvReleaseImage(&img_src);

        cout << endl << "-----------------------------------------------------------------" << endl << endl;
    }

    cvDestroyWindow(nomFenetreSeg.c_str());
}


//-------------- Listing des images disponibles ----------------->
//**************************************************************//


string demanderImage()
{

    //<-----------------------------------------------------AJOUTER LES IMAGES DE TESTS ICI
    std::vector<string> images;
    images.push_back("deuxcarre.pgm (256*256)");
    images.push_back("deuxcarre3d.pgm (256*256)");

    images.push_back("notre-dame-de-paris.jpg (500*336)");
    images.push_back("opera-lyon.jpg (540*360)");
    images.push_back("cellules.jpg (768*512)");

    images.push_back("paysage-colore.jpg (1024*768)");
    images.push_back("paysage-de-riziere.jpg (1280*857)");
    images.push_back("NewYork.jpg (1468*1101)");
    //------------------------------------------------------------------------------------<


    cout << "Entrez le numero de l'image a segmenter : " << endl;

    for(unsigned int i = 0; i < images.size(); i++) cout << i << " - " << images[i] << endl;

    while(true)
    {
        string tmp;
        cin >> tmp;
        int choix = atof(tmp.c_str());
        if(choix < images.size() && choix >= 0)
        {
            tmp = images[choix]; //on récupère le contenu
            tmp = tmp.substr(0,tmp.find(" ")); //on récupère l'image sans le (taille*taille)
            cout << tmp << endl;
            return tmp;
        }
        cout << "Le numero d'image choisit n'existe pas. " << tmp << " Reeseyez : " << endl;
    }

}

//-------------- Affichage des images ----------------->
//****************************************************//

void afficherImage(const string nomFenetreSeg, const IplImage* img)
{

    IplImage* tmp;

    if(img->width > 640)
    {
        tmp = cvCreateImage(cvSize(640, img->height * ((double) 640/(double) img->width)), IPL_DEPTH_8U, 3);
        cvResize(img, tmp);
        cvShowImage( nomFenetreSeg.c_str(),tmp);
    }
    else cvShowImage( nomFenetreSeg.c_str(), img);
    cvWaitKey(100);
}


//--------------------- Seuillage ------------------------------->
//***************************************************************//


double demanderSeuil(const IplImage* img_src)
{
    std::string rep;
    double tmpSeuil, seuil = 10;

    cout << "Seuillage selon l'intensite la plus commune dans l'image (histogramme) : y/n" << endl;
    while(rep.compare("y") && rep.compare("n") && rep.compare("Y") && rep.compare("N")) cin >> rep;

    if(!rep.compare("y") || !rep.compare("Y"))
    {
        tmpSeuil = Histogramme(img_src, 3).getPicMax();

        cout << "Seuil deduit depuis l'histogramme : " << tmpSeuil << endl;

        if(tmpSeuil == 0)
        {
            cout << "Le seuil n'est pas exploitable. " << seuil << endl;
            rep = "n"; // pour passer au if ci-dessous
        }
        else
            seuil = tmpSeuil;

        cout << endl;
    }

    if(!rep.compare("n") || !rep.compare("N"))
    {
        rep.clear();
        cout << "Seuil actuel : " << seuil << "." << endl << endl << "Si vous desirez le conserver entrez la lettre 'y', sinon entrez un nombre : " << endl;
        cin >> rep;
        if(!rep.compare("y") || !rep.compare("Y")){}
        else
        {
            seuil = atof(rep.c_str());
            cout << "Nouveau seuil : " << seuil << endl << endl;
        }
    }

    return seuil;
}



//--------------------- Disposition des graines  ------------------------------->
//*****************************************************************************//


void demanderDispoGraine(const IplImage* img_src, std::vector<Graine>* graines)
{

    std::string rep;
    char* nomFenetre = "Deposez vos graines";

    cout << "Choix de disposition des graines (entrez la lettre entre parenthese) :" << endl <<
    "- (a)utomatique (cadrillage)" << endl <<
    "- au (h)asard" << endl <<
    "- au (c)hoix (selection a la souris)" << endl;

    char key = '---';
    while(rep.compare("a") && rep.compare("h") && rep.compare("c")) cin >> rep;

    if(!rep.compare("c"))
    {
        cvNamedWindow(nomFenetre, CV_WINDOW_AUTOSIZE);
        cvMoveWindow(nomFenetre,340,0);
        cvShowImage(nomFenetre, img_src);

        cout << endl << "1- Selectionnez la nouvelle fenetre" << endl;
        cout << "2- Positionnez la souris ou vous desirez poser la graine" << endl;
        cout << "3- Appuyez sur 't' pour sauvegarder la graines" << endl;
        cout << "4- Reiterez le proceder." << endl;
        cout << "Quand vous aurez termine, appuyez sur 'q' en restant dans la fenetre de l'image." << endl;

        while(key != 'q')
        {
            cvSetMouseCallback(nomFenetre, onLClick);

            if(key == 't')
            {
                graines->push_back(Graine(tmp->x,tmp->y));
                cout << "Graine " << graines->size() << " : (" << tmp->x << "," << tmp->y << ")." << endl;
                theFlag = 0;
                key = '---';
            }

            key = cvWaitKey(20);
        }

        cvDestroyWindow(nomFenetre);

    }
    else if(!rep.compare("a"))
    {
        cout << "Disposition automatique des graines." << endl;

        bool nMax = img_src->width/10 > img_src->height/10;

        cout << "Cadrillage en N * N graines, donner une valeur pour N (avec N <= " << (nMax?ceil(img_src->height/10):ceil(img_src->width/10)) << ") : " << endl;
        cin >> rep;
        dispositionAutomatique(img_src->width, img_src->height, graines, atof(rep.c_str()));
    }
    else
    {
        cout << "Disposition au hasard des graines. " << endl;

        bool nMax = img_src->height/3 > img_src->width/3;

        cout << "Nombre de graine N a rependre (avec N <= " << (nMax?ceil(img_src->width/3)*2:ceil(img_src->height/3)*2) << ") : " << endl;

        cin >> rep;

        dispositionAleatoire(img_src->width, img_src->height, graines, atof(rep.c_str()));
    }

    cout << endl;
}


void dispositionAutomatique(const unsigned int largeur, const unsigned int hauteur, vector<Graine>* graines, const unsigned int decoupe)
{
    unsigned int pasX = largeur/decoupe;
    unsigned int pasY = hauteur/decoupe;

    if(pasX < 10) pasX = 10;
    if(pasY < 10) pasY = 10;


    for(unsigned int i = 0; i < largeur; i++)
    {
        for(unsigned int j = 0; j < hauteur; j++)
        {
            graines->push_back(Graine(i,j));
            j += pasY;
        }
        i += pasX;
    }
}

void dispositionAleatoire(const unsigned int largeur, const unsigned int hauteur, vector<Graine>* graines, const unsigned int nombre)
{

    unsigned int nb = nombre;

    //comme c'est de l'aléatoire, on ajoute une facteur *2
    unsigned int pasX = 2*largeur/nb;
    unsigned int pasY = 2*hauteur/nb;

    //on conserve le côté le plus petit
    if(pasX < 3 || pasY < 3)
        if((double) (hauteur/3) > (double) (largeur/3))
            nb = ceil(largeur/3);
        else
            nb = ceil(hauteur/3);

    for(unsigned int i = 0; i < nb; i++)
    {
        unsigned int x = rand()%largeur;
        unsigned int y = rand()%hauteur;
        graines->push_back(Graine(x,y));
    }
}
