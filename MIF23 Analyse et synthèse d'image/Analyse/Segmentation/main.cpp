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

double demanderSeuil(const IplImage* img_src);
void demanderDispoGraine(const IplImage* img_src, std::vector<Graine>* graines);
void dispositionAutomatique(const unsigned int largeur, const unsigned int hauteur, vector<Graine>* graines, const unsigned int decoupe  = 100);
void dispositionAleatoire(const unsigned int largeur, const unsigned int hauteur, vector<Graine>* graines, const unsigned int nombre);


int main()
{
    string nomImage, cheminImg, nomFenetre, tempCheminImage;
    char key = '----';

    while (key != 'q'){

        cheminImg = "images/";


        bool bonChemin = 0;
        IplImage* img_src;

        while (!bonChemin)
        {
            cout << endl << "Choix de l'image (repertoire " << cheminImg << " ) :" << endl;
            cin >> nomImage;
            tempCheminImage = cheminImg;
            tempCheminImage += nomImage;

        //    char* nomFenetre = "img";

            img_src = cvLoadImage(tempCheminImage.c_str());
            if(img_src == NULL){cout << "Mauvaise chemin d'image !" << endl;}
            else {bonChemin = true; cheminImg = tempCheminImage;}
        }

        double occupationMin = 70; /* taux d'occupation minimal des régions */

        std::vector<Graine>* graines = new std::vector<Graine>;

        double seuil = demanderSeuil(img_src);
        demanderDispoGraine(img_src, graines);

        if(graines->size())
        {
            cvNamedWindow(nomFenetre.c_str(), CV_WINDOW_AUTOSIZE);
            cvMoveWindow(nomFenetre.c_str(),0,0);

            Accroissement acc(img_src, seuil, occupationMin);
            acc.demarrer(*graines);

            cvShowImage( nomFenetre.c_str(), acc.getImgSeg() );
            cvSaveImage("images/result.jpg",acc.getImgSeg());
            cout << "Image segmentee sauvegardee: images/result.jpg" << endl;



            cout << "(q) pour pour quitter (s) pour segmenter une nouvelle image: " << endl;
            while(key != 'q' && key != 's' )  key = cvWaitKey(10);
    //       cin >> key;
            cvDestroyWindow(nomFenetre.c_str());
        }


        delete graines;
        cvReleaseImage(&img_src);

    }
}


char* demanderImage()
{
    std::cout << "Entrez le numero de l'image à segmenter : " << std::endl;
    std::cout << "deuxcarre.pgm" << endl;
    std::cout << "deuxcarre3d.pgm" << endl;
    std::cout << "cle.pgm" << endl;
}


double demanderSeuil(const IplImage* img_src)
{
    std::string rep;
    double seuil = 10;

    std::cout << "Seuillage selon l'intensite la plus commune dans l'image (histogramme) : y/n" << std::endl;
    while(rep.compare("y") && rep.compare("n") && rep.compare("Y") && rep.compare("N")) cin >> rep;

    if(!rep.compare("y") || !rep.compare("Y"))
    {
        seuil = Histogramme(img_src, 3).getPicMax();
        std::cout << "Seuil deduit depuis l'histogramme : " << seuil << endl << endl;
    }
    else
    {
        rep.clear();
        std::cout << "Seuil actuel : " << seuil << "." << endl << endl << "Si vous desirez le conserver entrez la lettre 'y', sinon entrez un nombre : " << endl;
        cin >> rep;
        if(!rep.compare("y") || !rep.compare("Y")){}
        else
        {
                seuil = atof(rep.c_str());
                std::cout << "Nouveau seuil : " << seuil << endl << endl;
        }

    }

    return seuil;
}



void demanderDispoGraine(const IplImage* img_src, std::vector<Graine>* graines)
{

    std::string rep;
    char* nomFenetre = "Image source";

    std::cout << "Choix de disposition des graines (entrez la lettre entre parenthese) :" << endl <<
    "- (a)utomatique (cadrillage)" << endl <<
    "- au (h)asard" << endl <<
    "- au (c)hoix (selection a la souris)" << std::endl;

    char key = '---';
    while(rep.compare("a") && rep.compare("h") && rep.compare("c")) cin >> rep;

    if(!rep.compare("c"))
    {
        cvNamedWindow(nomFenetre, CV_WINDOW_AUTOSIZE);
        cvMoveWindow(nomFenetre,0,0);
        cvShowImage(nomFenetre, img_src);

        std::cout << "Positionnez la souris sur l'image et appuyez sur 't' pour deposer une graines." << std::endl;

        while(key != 'q')
        {
            cvSetMouseCallback(nomFenetre, onLClick);

            if(key == 't')
            {
                graines->push_back(Graine(tmp->x,tmp->y));
                std::cout << "Graine " << graines->size() << " : (" << tmp->x << "," << tmp->y << ")." << std::endl;
                theFlag = 0;
                key = '---';
            }

            key = cvWaitKey(20);
        }

        cvDestroyWindow(nomFenetre);

    }
    else if(!rep.compare("a"))
    {
        std::cout << "Disposition automatique des graines." << std::endl;

        bool nMax = img_src->width/10 > img_src->height/10;

        cout << "Cadrillage en N * N graines, donner une valeur pour N (avec N <= " << (nMax?ceil(img_src->height/10):ceil(img_src->width/10)) << ") : " << std::endl;
        cin >> rep;
        dispositionAutomatique(img_src->width, img_src->height, graines, atof(rep.c_str()));
    }
    else
    {
        std::cout << "Disposition au hasard des graines. " << std::endl;

        bool nMax = img_src->height/3 > img_src->width/3;

        cout << "Nombre de graine N a rependre (avec N <= " << (nMax?ceil(img_src->width/3)*2:ceil(img_src->height/3)*2) << ") : " << std::endl;

        cin >> rep;

        dispositionAleatoire(img_src->width, img_src->height, graines, atof(rep.c_str()));
    }

    std::cout << std::endl;
}


void dispositionAutomatique(const unsigned int largeur, const unsigned int hauteur, vector<Graine>* graines, const unsigned int decoupe)
{
    unsigned int pasX = largeur/decoupe;
    unsigned int pasY = hauteur/decoupe;

    if(pasX < 10 || pasY < 10)
    {
        pasX = 10;
        pasY = 10;
    }

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
