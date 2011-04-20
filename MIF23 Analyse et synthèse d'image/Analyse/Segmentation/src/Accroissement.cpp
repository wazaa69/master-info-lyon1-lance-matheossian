#include "../include/Accroissement.h"

using namespace std;

//variable de teste
int nombreAglomere = 0;
double tempsAlgo = 0;
double tempsColoration = 0;



//---------------------------------- Initialisations --------------------------------------->
//******************************************************************************************//



Accroissement::Accroissement(const IplImage* _img_src, const double _seuil, const double _occupationMin): img_src(_img_src), seuil(_seuil), occupationMin(_occupationMin)
{

    img_seg = cvCreateImage(cvGetSize(img_src), img_src->depth, 3);

    const unsigned int hauteur = img_src->height;
    const unsigned int largeur = img_src->width;

    nbPointsTotal = hauteur * largeur;
    nbPointsDansRegion = 0;

    for(unsigned int i = 0; i < largeur; i++)
    {
        imgIndexGrow.push_back(vector<int>());
        for(unsigned int j = 0; j < hauteur; j++) imgIndexGrow[i].push_back(-1);
    }
}

Accroissement::~Accroissement(){cvReleaseImage(&img_seg);}



void Accroissement::demarrer(vector<Graine>& graines)
{
    clock_t finColor, finAlgo, debutColor, debutAlgo = clock();

    deposerGraines(graines);
    contaminationPixelsVoisins();
    //contaminationEtendue(); //non fonctionnelle

    finAlgo = clock();
    tempsAlgo = ((double)finAlgo - debutAlgo) / CLOCKS_PER_SEC;

    debutColor = clock();
    coloration();
    finColor = clock();

    tempsColoration = ((double)finColor - debutColor) / CLOCKS_PER_SEC;

    afficherInformations();
}



void Accroissement::deposerGraines(vector<Graine> graines)
{
    for(unsigned int i = 0; i < graines.size(); i++)
    {
        CvPoint g = graines[i].getPtStart();

        //création de la région
        CvScalar color = cvGet2D(img_src, g.y, g.x);
        Region region = Region(graines[i], Couleur(color.val[2], color.val[1], color.val[0]));
        listeIndexRegions.push_back(region);

        //ajout de l'index et de la couleur sur l'image
        imgIndexGrow[g.x][g.y] = i;
        //cvSet2D(img_seg, g.y, g.x, region.getCouleurVisuelle().getCvScalar());
        listePointsVoisins.push(cvPoint(g.x, g.y)); //ajouts des graines dans la liste des voisins
    }
}




//---------------------------------- Début du growing --------------------------------------->
//******************************************************************************************//



void Accroissement::contaminationPixelsVoisins()
{
    //de cette façon, on fait grandire chacune des régions autour d'une graine
    while (!listePointsVoisins.empty())
    {
        CvPoint pointCentral = listePointsVoisins.front();
        listePointsVoisins.pop();
        const unsigned int x = pointCentral.x;
        const unsigned int y = pointCentral.y;


        Region region = listeIndexRegions[imgIndexGrow[x][y]]; //avec imgIndexGrow[x][y] >= 0

        if(x > 0 && x < img_src->width-1 && y > 0 && y < img_src->height-1)
        {
            contamination(cvPoint(x-1, y-1),region);
            contamination(cvPoint(x, y-1),region);
            contamination(cvPoint(x+1, y-1),region);
            contamination(cvPoint(x+1, y),region);
            contamination(cvPoint(x+1, y+1),region);
            contamination(cvPoint(x, y+1),region);
            contamination(cvPoint(x-1, y+1),region);
            contamination(cvPoint(x-1, y),region);
        }
        else if(x == 0 && y == 0)
        {
            contamination(cvPoint(x+1, y),region);
            contamination(cvPoint(x+1, y+1),region);
            contamination(cvPoint(x, y+1),region);
        }
        else  if(x > 0 && x < img_src->width-1 && y == 0)
        {
            contamination(cvPoint(x-1, 0),region);
            contamination(cvPoint(x-1, 1),region);
            contamination(cvPoint(x, 1),region);
            contamination(cvPoint(x+1, 1),region);
            contamination(cvPoint(x+1, 0),region);
        }
        else if(x == img_src->width-1 && y == 0)
        {
            contamination(cvPoint(x-1, 0),region);
            contamination(cvPoint(x-1, 1),region);
            contamination(cvPoint(x, 1),region);
        }
        else if(x == img_src->width-1 && y > 0 && y < img_src->height-1)
        {
            contamination(cvPoint(x, y-1),region);
            contamination(cvPoint(x-1, y-1),region);
            contamination(cvPoint(x-1, y),region);
            contamination(cvPoint(x-1, y+1),region);
            contamination(cvPoint(x, y+1),region);
        }
        else if(x == img_src->width-1 && y == img_src->height-1)
        {
            contamination(cvPoint(x, y-1),region);
            contamination(cvPoint(x-1, y-1),region);
            contamination(cvPoint(x-1, y),region);
        }
        else if(x > 0 && x < img_src->width-1 && y == img_src->height-1)
        {
            contamination(cvPoint(x-1, y),region);
            contamination(cvPoint(x-1, y-1),region);
            contamination(cvPoint(x, y-1),region);
            contamination(cvPoint(x+1, y-1),region);
            contamination(cvPoint(x+1, y),region);
        }
        else if(x == 0 && y == img_src->height-1)
        {
            contamination(cvPoint(0, y-1),region);
            contamination(cvPoint(1, y-1),region);
            contamination(cvPoint(1, y),region);
        }
        else if(x == 0 && y > 0  && y < img_src->height-1)
        {
            contamination(cvPoint(0, y-1),region);
            contamination(cvPoint(1, y-1),region);
            contamination(cvPoint(1, y),region);
            contamination(cvPoint(1, y+1),region);
            contamination(cvPoint(0, y+1),region);
        }
    }
}



void Accroissement::contamination(const CvPoint& pt, Region& uneRegion)
{
    //Recherche si il y a une redirection vers une autres région
    Region& region = listeIndexRegions[indexRedirection(uneRegion)];

    CvScalar color = cvGet2D(img_src, pt.y, pt.x); //couleur du pixel
    double coulPixel = (color.val[2]+color.val[1]+color.val[0])/3; //couleur moyenne du pixel
    double moyCoulRegion = region.getCouleurMoyenne().moyenne(); //couleur moyenne de la région voisinne

    // Extension de la region
    if((imgIndexGrow[pt.x][pt.y] == -1) && (abs(coulPixel - moyCoulRegion) <= seuil) )
    {
        imgIndexGrow[pt.x][pt.y] = region.getIndexRegion();
        region.setTailleRegion(region.getTailleRegion()+1);
        nbPointsDansRegion++;
        region.setNouvMoyenne(Couleur(color)); //moyenne entre la couleur de la région et celle du pixel
        cvSet2D(img_seg, pt.y, pt.x, region.getCouleurVisuelle().getCvScalar());
        listePointsVoisins.push(pt);
    }
    //Version 1 : Création d'une nouvelle région, on plante une nouvelle gaine pour la faire croître
//    else if(imgIndexGrow[pt.x][pt.y] == -1 )
//    {
//        Region nouvRegion = Region(Graine(pt), Couleur(color.val[2],color.val[1],color.val[0]));
//        imgIndexGrow[pt.x][pt.y] = nouvRegion.getIndexRegion();
//        listeIndexRegions.push_back(nouvRegion);
//        //cvSet2D(img_seg, pt.y, pt.x, nouvRegion.getCouleurVisuelle().getCvScalar());
//        listePointsVoisins.push(pt);
//    }
//    //Version 2 : le seuillage n'est pas respecté, on conserve le point pour plus tard (au cas où le growing n'occuperait pas N% de l'image)
//    else if(imgIndexGrow[pt.x][pt.y] == -1 )
//    {
//        listePointsVRejetes.push_back(pt);
//    }
    //Deux pixels voisins appartiennent une région différente : si leur couleur moyenne respectent le seuil, la plus grande région absorbe la plus petite
    else if(imgIndexGrow[pt.x][pt.y] != -1 )
    {
        //le point testé appartient à une région, on cherche sa redirection
        Region& regionDuPoint = listeIndexRegions[indexRedirection(listeIndexRegions[imgIndexGrow[pt.x][pt.y]])];

        if(region.getIndexRegion() != regionDuPoint.getIndexRegion() //les deux région sont différentes (cas obligatoire : si on est dans la région)
             && region.getTailleRegion() >= regionDuPoint.getTailleRegion()  //comparaison de taille
             && abs(regionDuPoint.getCouleurMoyenne().moyenne() - moyCoulRegion) <= seuil //comparaison des couleurs des régions
          )
        {
            changerProprietaireRegion(region, regionDuPoint);
            listePointsVoisins.push(pt);
        }
    }
}


int Accroissement::indexRedirection(const Region& uneRegion)
{
    int guide = -1;
    map<int,int>::iterator it = carteRedirections.find(uneRegion.getIndexRegion());
    while(1)
    {
        if(it != carteRedirections.end())
        {
            guide = it->second;
            it++;
            it = carteRedirections.find(guide);
        }
        else break; //sinon on en fait rien et on conserve le "guide"
    }

    if(guide == -1) guide = uneRegion.getIndexRegion();
    else carteRedirections[uneRegion.getIndexRegion()] = guide;  //mise à jour de la map pour éviter de refaire la boucle

    return guide;
}


void Accroissement::changerProprietaireRegion(Region& r_grande, Region& r_petite)
{
    //on calcul la couleur moyenne des deux régions
    r_grande.setNouvMoyenne(r_petite.getCouleurMoyenne());

    carteRedirections.insert(pair<int,int>(r_petite.getIndexRegion(), r_grande.getIndexRegion()));

    r_grande.setTailleRegion(r_grande.getTailleRegion() + r_petite.getTailleRegion());
    r_petite.setTailleRegion(0);

    nombreAglomere++; //1 région aglomérée implique une redirection suplpémentaire
}


void Accroissement::coloration()
{
    for(unsigned x = 0; x < imgIndexGrow.size(); x++)
        for(unsigned y = 0; y < imgIndexGrow[x].size(); y++)
        {
            if(imgIndexGrow[x][y]  != -1) //au cas où l'on désactiverai le "if une région différente = nouvelle région"
            {
                //on modifie uniquement les pixels des régions redirigées
                int redirection = indexRedirection(listeIndexRegions[imgIndexGrow[x][y]]);
                cvSet2D(img_seg, y, x, listeIndexRegions[redirection].getCouleurVisuelle().getCvScalar());
            }
        }
}

void Accroissement::afficherInformations()
{

    cout << img_seg->height << " * " << img_seg->width << " = " << (img_seg->height * img_seg->width) << endl ;
    cout << "Taux d'occupation de l'image : " << (double) (nbPointsDansRegion*100/nbPointsTotal) << "%" << " sur les " << occupationMin << " % attendus." << endl << endl;

    cout << "Nombre de regions crees au total: " << Region::getCompteurRegions() << endl;
    cout << "Nombre de regions aglomerees : " << nombreAglomere << endl << endl;

    cout << "Temps d'execution pour le depot de graine + grow + merge = " << tempsAlgo << " secondes"<< endl;
    cout << "Temps d'execution pour la coloration = " << tempsColoration << " secondes" << endl;
    cout << "Total pour graine + grow + merge + color = " << tempsAlgo + tempsColoration << " secondes"<< endl << endl;
}



const IplImage* Accroissement::getImgSeg() const{return img_seg;}
const IplImage* Accroissement::getImgSrc() const {return img_src;}




//---------------------------------- Pour s'étendre d'avanage (non terminé) ------------------------------------------>
//********************************************************************************************************************//


void Accroissement::contaminationEtendue()
{
    if(occupationMin != -1)
    {
        //ex : pourcentage d'occupation de l'image < pourcentage minimal d'occupation attendu
        while( (double) (nbPointsDansRegion*100/nbPointsTotal) < occupationMin)
        {

           cvShowImage( "img", getImgSeg() );
           cvWaitKey(1000);

           cout << "Seuil : " << seuil << " --> Occupation : " << (double) (nbPointsDansRegion*100/nbPointsTotal) << "%  < "  << occupationMin << endl;

           /* on supprime les points qui appartiennent à une région (même si la région est redirigé),
           de cette façon on conserve les "points frontière". */
           for(unsigned int i = 0; i < listePointsVRejetes.size(); i++)
           {
               CvPoint p = listePointsVRejetes[i];
               if(imgIndexGrow[p.x][p.y] == -1) listePointsVoisins.push(CvPoint(p));
           }

            //on vide la liste des points rejetés
           listePointsVRejetes.clear();

           augmenteSeuil();

            //on relance la contamination sur les points frontières
           contaminationPixelsVoisins();
        }
    }
}

void Accroissement::augmenteSeuil()
{
    //Version 1 : % du seuil précédant
    seuil = seuil + seuil * 0.25;
    if(seuil > 255) seuil = 255;
}
