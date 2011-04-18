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
void dispositionAutomatique(const unsigned int largeur, const unsigned int hauteur, vector<Graine>* graines);
void dispositionAleatoire(const unsigned int largeur, const unsigned int hauteur, vector<Graine>* graines, const unsigned int nombre);


int main()
{
    char* nomFenetre = "img";
    char* chemainImg ="images/ny.jpg";
    IplImage* img_src = cvLoadImage(chemainImg);
    if(img_src == NULL){cout << "Mauvaise chemin d'image !" << endl; exit(0);}

    std::vector<Graine>* graines = new std::vector<Graine>;

    double seuil = demanderSeuil(img_src);
    demanderDispoGraine(img_src, graines);

    if(graines->size())
    {
        Accroissement acc(img_src, seuil);
        acc.demarrer(*graines);

        cvNamedWindow(nomFenetre, CV_WINDOW_AUTOSIZE);
        cvMoveWindow(nomFenetre,0,0);
        cvShowImage( nomFenetre, acc.getImgSeg() );
        cvSaveImage("images/result.jpg",acc.getImgSeg());

        char key = '----';
        while(key != 'q')  key = cvWaitKey(10);
    }

    delete graines;
    cvDestroyWindow(nomFenetre);
    cvReleaseImage(&img_src);
}



double demanderSeuil(const IplImage* img_src)
{
    std::string rep ="y";
    double seuil = 15;

    std::cout << "Seuillage selon l'intensite la plus commune dans l'image (histogramme) : y/n" << std::endl;
    while(rep.compare("y") && rep.compare("n") && rep.compare("Y") && rep.compare("N")) cin >> rep;

    if(!rep.compare("y") || !rep.compare("Y"))
    {
        seuil = Histogramme(img_src, 3).getPicMax();
        std::cout << "Seuil deduit depuis l'histogramme : " << seuil << endl << endl;
    }
    else
    {
        std::cout << "Seuil actuel : " << seuil << ". Si vous desirez le conserver entrez la lettre 'y', sinon entrez un nombre : " << endl;
        rep.clear();
        cin >> rep;
        if(rep.compare("y") || rep.compare("Y")) seuil = atof(rep.c_str());
        std::cout << "Nouveau seuil : " << seuil << endl << endl;
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
        std::cout << "Disposition automatique des graines. " << std::endl;
        dispositionAutomatique(img_src->width, img_src->height, graines);
    }
    else
    {
        std::cout << "Disposition au hasard des graines. " << std::endl;
        dispositionAleatoire(img_src->width, img_src->height, graines, (img_src->width/100) * (img_src->height/100));
    }

    std::cout << std::endl;
}


void dispositionAutomatique(const unsigned int largeur, const unsigned int hauteur, vector<Graine>* graines)
{
    const unsigned int pasX = largeur/10;
    const unsigned int pasY = hauteur/10;

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
    for(unsigned int i = 0; i < nombre; i++)
    {
        unsigned int x = rand()%largeur;
        unsigned int y = rand()%hauteur;
        graines->push_back(Graine(x,y));
    }
}
